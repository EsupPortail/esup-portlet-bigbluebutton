package org.esupportail.bigbluebutton.web.springmvc;


import javax.portlet.ActionResponse;

import org.esupportail.bigbluebutton.domain.DomainService;
import org.esupportail.bigbluebutton.domain.beans.Recording;
import org.esupportail.bigbluebutton.services.auth.Authenticator;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.services.smtp.SmtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;



/**
 * @author Franck Bordinat
 *
 */
@Controller
@RequestMapping("VIEW")
@SessionAttributes(types = Recording.class)
public class RecordingController extends AbstractExceptionController {

	/**
	 * logger
	 */
	private final Logger logger = new LoggerImpl(this.getClass());


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
	public RecordingController(DomainService domainService) {
		this.domainService = domainService;
	}

	
	/**
	 * delete a recording
	 * @param response
	 * @param id
	 * @throws Exception 
	 */
	@RequestMapping(params="action=deleteRecording")
	public void deleteRecording(ActionResponse response,
			@RequestParam("recordID") String recordID, @RequestParam("meeting") String id) throws Exception {
		//redirection before deleting
		response.setRenderParameter("action", "viewRecordings");
		response.setRenderParameter("meeting", id);
		domainService.deleteRecordings(recordID);
	}

	
	
	/**
	 * see a recording on BBB server
	 * @param response 
	 * @param id
	 * @throws Exception 
	 */
	@RequestMapping(params="action=playRecording")
	public void playRecording(ActionResponse response,
			@RequestParam("playback") String playback) throws Exception{
		
		response.sendRedirect(playback);
	}

	
}


