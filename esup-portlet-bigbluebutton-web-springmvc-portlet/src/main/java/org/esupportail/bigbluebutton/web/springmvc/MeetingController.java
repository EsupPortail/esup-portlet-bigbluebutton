package org.esupportail.bigbluebutton.web.springmvc;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.esupportail.bigbluebutton.domain.DomainService;
import org.esupportail.bigbluebutton.domain.beans.Invitation;
import org.esupportail.bigbluebutton.domain.beans.Meeting;
import org.esupportail.bigbluebutton.domain.validators.MeetingValidator;
import org.esupportail.bigbluebutton.domain.beans.User;
import org.esupportail.bigbluebutton.services.auth.Authenticator;
import org.esupportail.bigbluebutton.utils.WebUtils;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.services.smtp.SmtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.PortletRequestDataBinder;


/**
 * @author Franck Bordinat
 *
 */
@Controller
@RequestMapping("VIEW")
@SessionAttributes(types = {Meeting.class, Invitation.class})
public class MeetingController extends AbstractExceptionController {

	/**
	 * logger
	 */
	private final Logger logger = new LoggerImpl(this.getClass());
	
	/**
	 * Validator
	 */
	private MeetingValidator meetingValidator = new MeetingValidator();

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
	public MeetingController(DomainService domainService) {
		this.domainService = domainService;
	}

	

	/**
	 * setup binder for adding/editing meetings
	 * @param binder
	 * @throws Exception 
	 */
	@SuppressWarnings("nls")
	@InitBinder
	protected void initBinder(PortletRequestDataBinder binder) throws Exception {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.FRANCE);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	    binder.setAllowedFields(new String[] {"name","welcome","attendeePW","moderatorPW","voiceBridge","meetingDate","meetingDuration", "record"});
	}

	/**
	 * default: show list of meetings for remote user
	 * @param model
	 * @param request
	 * @param sessionStatus
	 * @return MV
	 * @throws Exception 
	 */
	@SuppressWarnings("nls")
	@RequestMapping
	public String listMeetings(Model model, RenderRequest request, SessionStatus sessionStatus) throws Exception {
		
		List<Meeting> l = domainService.getMeetingsForUser(request.getRemoteUser());
		
		// add current status to each meeting
		for (Meeting m : l) {
			String isrunning = domainService.isMeetingRunning(m.getId().toString());
			m.setIsrunning(Boolean.valueOf(isrunning).booleanValue());
		}
		model.addAttribute("meetings", l);
		sessionStatus.setComplete();
        return "meetings";
	}


	/**
	 * view details of a meeting and list of invitations
	 * @param id
	 * @param model
	 * @return MV
	 * @throws Exception 
	 */
	@SuppressWarnings("nls")
	@RequestMapping(params="action=viewMeeting")
	public String viewMeeting(@RequestParam("meeting") Integer id, Model model) throws Exception {
		model.addAttribute("meeting", domainService.getMeeting(id));
		model.addAttribute("invitations", domainService.getInvitationsForMeeting(domainService.getMeeting(id)));
		if (!model.containsAttribute("invitation")) {
			model.addAttribute("invitation", new Invitation());
		}
		
		return "meetingView";
	}

	
	/**
	 * view details of a meeting and list of recordings
	 * @param id
	 * @param model
	 * @return MV
	 * @throws Exception 
	 */
	@SuppressWarnings("nls")
	@RequestMapping(params="action=viewRecordings")
	public String viewRecordings(@RequestParam("meeting") Integer id, Model model) throws Exception {
		model.addAttribute("meeting", domainService.getMeeting(id));
		model.addAttribute("invitations", domainService.getInvitationsForMeeting(domainService.getMeeting(id)));
		if (!model.containsAttribute("invitation")) {
			model.addAttribute("invitation", new Invitation());
		}
		
		model.addAttribute("recordings", domainService.getRecordings(domainService.getMeeting(id).getId().toString()));
		
		return "meetingRecords";
	}

	
	/**
	 * delete a meeting
	 * @param response
	 * @param id
	 * @throws Exception 
	 */
	@RequestMapping(params="action=deleteMeeting")
	public void deleteMeeting(ActionResponse response,
			@RequestParam("meeting") Integer id) throws Exception {
		domainService.deleteMeeting(id);
	}

	/**
	 * join the meeting as moderator
	 * @param request
	 * @param response
	 * @param id
	 * @throws Exception 
	 * @throws IOException
	 */
	@SuppressWarnings("nls")
	@RequestMapping(params="action=moderateMeeting")
	public void moderateMeeting(ActionRequest request, ActionResponse response,
			@RequestParam("meeting") Integer id) throws Exception {
			Meeting meeting = domainService.getMeeting(id);
			String url;
			if ((domainService.isMeetingRunning(meeting.getId().toString())!="SUCCESS")) {
				
				String welcome = "";
				if ((meeting.getWelcome() != null) && !meeting.getWelcome().equals("")) {
					welcome = meeting.getWelcome();
				}
				
				if (meeting.getRecord()==true){
					welcome = welcome + i18nService.getString("meeting.recording.message");
				}
				
				url = domainService.createMeeting(meeting.getId().toString(), meeting.getName(), 
						welcome, meeting.getAttendeePW(), meeting.getModeratorPW(), meeting.getVoiceBridge(), meeting.getRecord(), meeting.getOwner());
			} else {
				url = domainService.getJoinMeetingURL(request.getRemoteUser(), meeting.getId().toString(), meeting.getModeratorPW());
			}

			response.sendRedirect(url);
	}
	
	/**
	 * join the meeting as attendee
	 * @param request
	 * @param response
	 * @param id
	 * @param model
	 * @throws Exception 
	 */
	@SuppressWarnings("nls")
	@RequestMapping(params="action=attendMeeting")
	public void attendMeeting(ActionRequest request, ActionResponse response,
			@RequestParam("meeting") Integer id, Model model) throws Exception {
			Meeting meeting = domainService.getMeeting(id);
			String isrunning = domainService.isMeetingRunning(meeting.getId().toString());
			String url = domainService.getJoinMeetingURL(request.getRemoteUser(), meeting.getId().toString(), meeting.getAttendeePW());
			
			if (!isrunning.toString().equals("true")){
				response.setRenderParameter("action", "waitMeeting");
				response.setRenderParameter("meeting", meeting.getId().toString());
			} else {
				response.sendRedirect(url);
			}
	}
	
	/**
	 * add a meeting - render phase
	 * @param model
	 * @return MV
	 * @throws Exception 
	 */
	@SuppressWarnings("nls")
	@RequestMapping(params="action=addMeeting") 
	public String showAddMeetingForm(Model model) throws Exception {
		if (!model.containsAttribute("meeting")) {
			model.addAttribute("meeting", new Meeting());
		}
		return "meetingAdd";
	}
	
	/**
	 * add a meeting - action phase
	 * @param meeting
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @param sessionStatus
	 * @throws Exception 
	 */
	@SuppressWarnings("nls")
	@RequestMapping(params = "action=addMeeting") // action phase
	public void addMeeting(@ModelAttribute Meeting meeting,
			BindingResult bindingResult, ActionRequest request, ActionResponse response, SessionStatus sessionStatus) throws Exception {
		meetingValidator.validate(meeting, bindingResult);
		
		if (request.getParameter("_cancel") != null) {
			response.setRenderParameter("action", "meetings");
			sessionStatus.setComplete();
		}
		else if (request.getParameter("_finish") != null) {
			if (!bindingResult.hasErrors()) {

				// generate a random voicebridge for this meeting
				Random random = new Random();
				Integer n = 70000 + random.nextInt(9999);
				
				// generate a random moderator PW
				String mPW = UUID.randomUUID().toString();
				
				// generate a random attendee PW
				String aPW = UUID.randomUUID().toString();
				
				// add meeting
				Integer insertedId = domainService.addMeeting(meeting.getName(), meeting.getWelcome(), aPW, mPW,
						n, meeting.getMeetingDate(), meeting.getMeetingDuration(), meeting.getRecord(), request.getRemoteUser(), null, new Date());
				
				// Logs
		    	if (logger.isDebugEnabled()){
			    	logger.debug("Meeting added...........");
			    	logger.debug("Meeting id : " +insertedId);
		    	}
				
				//Send email notification
				

				String joinUrl = domainService.getJoinMeetingURL("Invité", insertedId.toString(), aPW);
				try {
					User currentUser = authenticator.getUser();
					// Logs
			    	if (logger.isDebugEnabled()){
				    	logger.debug("Sending email...........");
				    	logger.debug("email : " +currentUser.getEmailAdress());
			    	}
					
					smtpService.send(new InternetAddress(currentUser.getEmailAdress()),
							i18nService.getString("meeting.email.add.subject"),//"Création d'une conférence", 
							i18nService.getString("meeting.email.add.htmlcontent", currentUser.getDisplayName(), meeting.getName(), WebUtils.formatDate(meeting.getMeetingDate()),n
									, meeting.getWelcome(), joinUrl), 
									i18nService.getString("meeting.email.add.htmlcontent", currentUser.getDisplayName(), meeting.getName(), WebUtils.formatDate(meeting.getMeetingDate()),n
											, meeting.getWelcome(), joinUrl));
				
				
				} catch (AddressException e1) {
					logger.error("AddressException sending email ...", e1);
					
				} catch (Exception e2) {
					logger.error("Error retrieving user ...", e2);
				}
				
				//redirection
				response.setRenderParameter("action", "meetings");
				
				//--set the session status as complete to cleanup the model attributes
				//--stored using @SessionAttributes
				sessionStatus.setComplete();
				
			} else {
				response.setRenderParameter("action", "addMeeting");
			}
		}
	}

	/**
	 * edit a meeting - Render phase
	 * @param id
	 * @param model
	 * @return MV
	 * @throws Exception 
	 */
	@SuppressWarnings("nls")
	@RequestMapping(params="action=editMeeting")
	public String showEditMeetingForm(@RequestParam("meeting") Integer id, Model model) throws Exception{
		if (!model.containsAttribute("meeting")) {
			model.addAttribute("meeting", domainService.getMeeting(id));
		}
		return "meetingEdit";
	}

	/**
	 * edit a meeting - Action phase
	 * @param request
	 * @param response
	 * @param meeting
	 * @param result
	 * @param sessionStatus
	 * @throws Exception 
	 */
	@SuppressWarnings("nls")
	@RequestMapping(params="action=editMeeting") // action phase
	public void submitEditMeetingForm(ActionRequest request, ActionResponse response,
			@ModelAttribute("meeting") Meeting meeting, BindingResult result, SessionStatus sessionStatus) throws Exception {

		if (request.getParameter("_cancel") != null) {
			response.setRenderParameter("action", "meetings");
			sessionStatus.setComplete();
		}
		else if (request.getParameter("_finish") != null) {
		
			meetingValidator.validate(meeting, result);
			if (result.hasErrors()) {
				response.setRenderParameter("action", "editMeeting");
				response.setRenderParameter("meeting", meeting.getId().toString());
			}
			else {
				
				String isrunning=domainService.isMeetingRunning(meeting.getId().toString());
				String endmeeting;
				// Logs
		    	if (logger.isDebugEnabled()){
					
			    	logger.debug("Updating Meeting...........");
			    	logger.debug("Meeting isrunning : " + isrunning);
			    	logger.debug("Meeting endmeeting url : " + isrunning);
			    	
		    	}
				// check if meeting is running and close it
				if (isrunning.equalsIgnoreCase("true")){
					endmeeting=domainService.endMeeting(meeting.getId().toString(), meeting.getModeratorPW());
				
				if (logger.isDebugEnabled())
			    	logger.debug("Meeting endmeeting url : " + endmeeting);
			    	
		    	}
				
				//update meeting
				domainService.updateMeeting(meeting);
				
				//Send email notification
				String joinUrl = domainService.getJoinMeetingURL("Invité", meeting.getId().toString(), meeting.getAttendeePW());
				try {
					User currentUser = authenticator.getUser();
					
					// Logs
			    	if (logger.isDebugEnabled()){
				    	logger.debug("Sending email...........");
				    	logger.debug("email : " +currentUser.getEmailAdress());
			    	}
				
					smtpService.send(new InternetAddress(currentUser.getEmailAdress()),
							i18nService.getString("meeting.email.update.subject"),//"Modification d'une conférence", 
							i18nService.getString("meeting.email.update.htmlcontent", currentUser.getDisplayName(), meeting.getName(), WebUtils.formatDate(meeting.getMeetingDate()),meeting.getVoiceBridge()
									, meeting.getWelcome(), joinUrl), 
									i18nService.getString("meeting.email.update.normalcontent", currentUser.getDisplayName(), meeting.getName(), WebUtils.formatDate(meeting.getMeetingDate()),meeting.getVoiceBridge()
											, meeting.getWelcome(), joinUrl));
				
				
				} catch (AddressException e1) {
					logger.error("AddressException sending email ...", e1);
					
				} catch (Exception e2) {
					logger.error("Error retrieving user ...", e2);
				}
				
				//redirection
				response.setRenderParameter("action", "viewMeeting");
				response.setRenderParameter("meeting", meeting.getId().toString());
				
				//--set the session status as complete to cleanup the model attributes
				//--stored using @SessionAttributes				
				sessionStatus.setComplete();
			}
		}
	}
	
	/**
	 * @param request
	 * @param id
	 * @param model
	 * @return MV
	 * @throws Exception
	 */
	@SuppressWarnings("nls")
	@RequestMapping(params="action=waitMeeting")
	public String waitMeeting(RenderRequest request, @RequestParam("meeting") Integer id, Model model) throws Exception{
		Meeting meeting = domainService.getMeeting(id);
		model.addAttribute("meeting", meeting);
		model.addAttribute("JoinMeetingURL", domainService.getJoinMeetingURL(request.getRemoteUser(), meeting.getId().toString(), meeting.getAttendeePW()));
		model.addAttribute("isMeetingRunningURL", domainService.getURLisMeetingRunning(meeting.getId().toString()));
		return "meetingWait";
	}
	
	
	
	/**
	 * @param request
	 * @param response
	 * @return MV
	 * @throws Exception
	 */
	@SuppressWarnings("nls")
	@RequestMapping("ABOUT")
	public ModelAndView renderAboutView(RenderRequest request, RenderResponse response) throws Exception {
		ModelMap model = new ModelMap();
		return new ModelAndView("about", model);
	}
	
}


