package org.esupportail.bigbluebutton.web.springmvc;


import java.util.Date;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.esupportail.bigbluebutton.domain.DomainService;
import org.esupportail.bigbluebutton.domain.beans.Invitation;
import org.esupportail.bigbluebutton.domain.beans.Meeting;
import org.esupportail.bigbluebutton.domain.beans.User;
import org.esupportail.bigbluebutton.domain.validators.InvitationValidator;
import org.esupportail.bigbluebutton.services.auth.Authenticator;
import org.esupportail.bigbluebutton.utils.WebUtils;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.services.smtp.SmtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.portlet.bind.PortletRequestDataBinder;



/**
 * @author Franck Bordinat
 *
 */
@Controller
@RequestMapping("VIEW")
@SessionAttributes(types = Invitation.class)
public class InvitationController extends AbstractExceptionController {

	/**
	 * logger
	 */
	private final Logger logger = new LoggerImpl(this.getClass());
	
	
	/**
	 * Validator
	 */
	private InvitationValidator invitationValidator = new InvitationValidator();

    /**
     * Authenticator
     */
	@Autowired
	private Authenticator authenticator;
    
    /**
     * 
     */
	@Autowired
	private SmtpService smtpService;
	
	/**
	 * I18nService
	 */
	@Autowired
	private I18nService i18nService;
    
	/**
	 * 
	 */
	private DomainService domainService;
    
	/**
	 * @param domainService
	 */
	@Autowired
	public InvitationController(DomainService domainService) {
		this.domainService = domainService;
	}

	
	/**
	 * setup binder for adding/editing invitations
	 * @param binder
	 * @throws Exception 
	 */
	@SuppressWarnings("nls")
	@InitBinder
	protected void initBinder(PortletRequestDataBinder binder) throws Exception {
	    binder.setAllowedFields(new String[] {"displayName","emailAdress"});
	}

	
	/**
	 * delete a invitation
	 * @param response
	 * @param id
	 * @throws Exception 
	 */
	@RequestMapping(params="action=deleteInvitation")
	public void deleteInvitation(ActionResponse response,
			@RequestParam("invitation") Integer id) throws Exception {
		//redirection before deleting
		response.setRenderParameter("action", "viewMeeting");
		response.setRenderParameter("meeting", domainService.getInvitation(id).getMeeting().getId().toString());
		domainService.deleteInvitation(id);
	}

	
	/**
	 * add a invitation - Action phase
	 * @param invitation
	 * @param bindingResult
	 * @param mid 
	 * @param request
	 * @param response
	 * @param sessionStatus
	 * @throws Exception 
	 */
	@SuppressWarnings("nls")
	@RequestMapping(params = "action=addInvitation") // action phase
	public void addInvitation(@ModelAttribute Invitation invitation,
			BindingResult bindingResult, @RequestParam("meeting") Integer mid, ActionRequest request, ActionResponse response, SessionStatus sessionStatus) throws Exception {
		invitationValidator.validate(invitation, bindingResult);
		
		// Logs
    	if (logger.isDebugEnabled()){
	    	logger.debug("Inside add invitation...........");
    	}
		
		if (request.getParameter("_finish") != null) {

			if (!bindingResult.hasErrors()) {

				// add invitation
				Integer insertedId = domainService.addInvitation(invitation.getDisplayName(), invitation.getEmailAdress(), domainService.getMeeting(mid), new Date());
				Invitation insertedInvitation = domainService.getInvitation(insertedId);
				
				// Logs
		    	if (logger.isDebugEnabled()){
			    	logger.debug("Invitation added...........");
			    	logger.debug("Invitation id : " +insertedId);
			    	logger.debug("getDisplayName : " +invitation.getDisplayName());
			    	logger.debug("invitation.getMeeting : " +insertedInvitation.getMeeting().getId().toString());
			    	logger.debug("getAttendeePW : " +insertedInvitation.getMeeting().getAttendeePW());
		    	}
				
				//Send email notification
				sendInvitation(insertedInvitation);
				
				
			} 
			//redirection
			response.setRenderParameter("action", "viewMeeting");
			response.setRenderParameter("meeting", mid.toString());
		}
	}

	/**
	 * send an invitation - Render phase
	 * @param response 
	 * @param id
	 * @throws Exception 
	 */
	@RequestMapping(params="action=sendInvitation")
	public void sendInvitation(ActionResponse response,
			@RequestParam("invitation") Integer id) throws Exception{
		Invitation invitation = domainService.getInvitation(id);
		
		//Send email notification
		sendInvitation(invitation);
		
		//update creationdate
		invitation.setCreationDate(new Date());
		domainService.updateInvitation(invitation);
		
		//redirection
		response.setRenderParameter("action", "viewMeeting");
		response.setRenderParameter("meeting", invitation.getMeeting().getId().toString());
	}

	/**
	 * edit a invitation - Action phase
	 * @param request
	 * @param response
	 * @param invitation
	 * @param result
	 * @param sessionStatus
	 * @throws Exception 
	 */
	@SuppressWarnings("nls")
	@RequestMapping(params="action=editInvitation") // action phase
	public void submitEditInvitationForm(ActionRequest request, ActionResponse response,
			@ModelAttribute("invitation") Invitation invitation, BindingResult result, SessionStatus sessionStatus) throws Exception {

		if (request.getParameter("_finish") != null) {
		
			invitationValidator.validate(invitation, result);
			if (result.hasErrors()) {
				response.setRenderParameter("action", "editInvitation");
				response.setRenderParameter("invitation", invitation.getId().toString());
			}
			else {
				
						
				//update invitation
				domainService.updateInvitation(invitation);
				
				//Send email notification
				sendInvitation(invitation);
				
				//redirection
				response.setRenderParameter("action", "viewMeeting");
				response.setRenderParameter("meeting", invitation.getMeeting().getId().toString());
				
				//--set the session status as complete to cleanup the model attributes
				//--stored using @SessionAttributes				
				sessionStatus.setComplete();
			}
		}
	}
	
	/**
	 * Search user in ldap directory - render phase
	 * @param response
	 * @param searchUser 
	 * @return searchLdapUsers
	 * @throws Exception 
	 */
	@RequestMapping(params="action=ldapSearch")
	public String ldapSearch(@RequestParam("meeting") Integer id,
			Model model) throws Exception {
		
		
		if (!model.containsAttribute("meeting"))
			model.addAttribute("meeting", id);
		
		if (!model.containsAttribute("ldapsearch"))
			model.addAttribute("ldapsearch", new User());
		
		return "searchLdapUsers";

	}
	
	/**
	 * Search user in ldap directory - action phase
	 * @param response
	 * @param searchUser 
	 * @return searchLdapUsers
	 * @throws Exception 
	 */
	@RequestMapping(params="action=ldapSearch")
	public void ldapSearch(@RequestParam("meeting") Integer id,
			@ModelAttribute("ldapsearch") User ldapsearch, BindingResult result, Model model, ActionRequest request, ActionResponse response) throws Exception {
		
		
    	if (ldapsearch.getDisplayName()!=null){
			if (ldapsearch.getDisplayName().length() > 2) {
				// Logs
		    	if (logger.isDebugEnabled()){
			    	logger.debug("Search : " + ldapsearch.getDisplayName());
		    	}
				List<User> ldapusers = domainService.searchUserInLdap(ldapsearch.getDisplayName());
				model.addAttribute("ldapusers", ldapusers);
			} else
				model.addAttribute("message", "Vous devez saisir au moins 3 caractères");
    	}
    	
		if (!model.containsAttribute("meeting")) {
			// Logs
	    	if (logger.isDebugEnabled()){
		    	logger.debug("Search 4");
	    	}
			model.addAttribute("meeting", id);
		}
		//redirection
		response.setRenderParameter("action", "ldapSearch");
		response.setRenderParameter("meeting", id.toString());
	}
	
	/**
	 * send an invitation - Render phase
	 * @param response 
	 * @param id
	 * @throws Exception 
	 */
	@RequestMapping(params="action=addInvitationFromLdap")
	public void addInvitationFromLdap(ActionResponse response,
			@RequestParam("meeting") Integer id, @RequestParam("displayName") String displayName, @RequestParam("emailAdress") String emailAdress) throws Exception{
		

		Invitation invitation = new Invitation();
		invitation.setDisplayName(displayName);
		invitation.setEmailAdress(emailAdress);
		invitation.setMeeting(domainService.getMeeting(id));
		invitation.setCreationDate(new Date());
		domainService.addInvitation(invitation);
		sendInvitation(invitation);
		
		//redirection
		response.setRenderParameter("action", "viewMeeting");
		response.setRenderParameter("meeting", id.toString());
	}
	
	/**
	 * Send email notification
	 * @param invitation
	 * @throws Exception
	 */
	public void sendInvitation(Invitation invitation) throws Exception{

		String joinUrl = domainService.getJoinMeetingURL(invitation.getDisplayName(),
				invitation.getMeeting().getId().toString(),
				invitation.getMeeting().getAttendeePW());
		try {
			// Logs
	    	if (logger.isDebugEnabled()){
		    	logger.debug("Sending email...........");
		    	logger.debug("email : " +invitation.getEmailAdress());
	    	}
			
			smtpService.send(new InternetAddress(invitation.getEmailAdress()),
					i18nService.getString("invitation.email.subject"),
					i18nService.getString("invitation.email.htmlcontent", invitation.getDisplayName(), invitation.getMeeting().getName(), WebUtils.formatDate(invitation.getMeeting().getMeetingDate()),invitation.getMeeting().getVoiceBridge()
							, invitation.getMeeting().getWelcome(), joinUrl), 
							i18nService.getString("invitation.email.normalcontent", invitation.getDisplayName(), invitation.getMeeting().getName(), WebUtils.formatDate(invitation.getMeeting().getMeetingDate()),invitation.getMeeting().getVoiceBridge()
									, invitation.getMeeting().getWelcome(), joinUrl));
		
		
		} catch (AddressException e1) {
			logger.error("AddressException sending email ...", e1);
			
		} catch (Exception e2) {
			logger.error("Error retrieving user ...", e2);
		}
	}
	
	
}


