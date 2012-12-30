package org.esupportail.bigbluebutton.domain.validators;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import org.esupportail.bigbluebutton.domain.beans.Meeting;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

/**
 * @author Franck Bordinat
 *
 */
public class MeetingValidator implements Validator {

	
	/**
	 * logger
	 */
	private final Logger logger = new LoggerImpl(this.getClass());
	
	/**
	 * regexp validator
	 */
	private Time24HoursValidator time24HoursValidator = new Time24HoursValidator();
	
	@Override
	public boolean supports(Class clazz) {
        return Meeting.class.isAssignableFrom(clazz);
    }

    @Override
	public void validate(Object obj, Errors errors) {
        Meeting meeting = (Meeting)obj;
        validateName(meeting, errors);
        validateWelcome(meeting, errors);
        validateAttendeePW(meeting, errors);
        validateModeratorPW(meeting, errors);
        validateVoiceBridge(meeting, errors);
        validateMeetingDate(meeting, errors);
        validateMeetingDuration(meeting, errors);
    }

	/**
	 * validate meeting name input
	 * @param meeting
	 * @param errors
	 */
	public void validateName(Meeting meeting, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "name", "meeting.validate.name.required", "Name is required.");
		
		if (meeting.getName()!=null) {
    		if (meeting.getName().length()>255)
    			errors.rejectValue("name", "meeting.validate.name.toolong", "Name too long (max 255 caracters)");
    	}
	}

	/**
	 * validate Welcome message input
	 * @param meeting
	 * @param errors
	 */
	public void validateWelcome(Meeting meeting, Errors errors) {
		
		if (meeting.getWelcome()!=null) {
    		if (meeting.getWelcome().length()>255)
    			errors.rejectValue("welcome", "meeting.validate.welcome.toolong", "Message too long (max 255 caracters)");
    	}
	}
	
	/**
	 * validate attendeePW input
	 * @param meeting
	 * @param errors
	 */
	public void validateAttendeePW(Meeting meeting, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "attendeePW", "meeting.validate.attendeePW.required", "Attendee Password is required.");
		
		if (meeting.getAttendeePW()!=null) {
    		if (meeting.getAttendeePW().length()>255)
    			errors.rejectValue("attendeePW", "meeting.validate.password.toolong", "Password too long (max 255 caracters)");
    	}
	}

	/**
	 * validate moderatorPW input
	 * @param meeting
	 * @param errors
	 */
	public void validateModeratorPW(Meeting meeting, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "moderatorPW", "meeting.validate.moderatorPW.required", "Moderator Password is required.");
		
		if (meeting.getModeratorPW()!=null) {
    		if (meeting.getModeratorPW().length()>255)
    			errors.rejectValue("moderatorPW", "meeting.validate.password.toolong", "Password too long (max 255 caracters)");
    	}
	}

	/**
	 * validate VoiceBridge input
	 * @param meeting
	 * @param errors
	 */
	public void validateVoiceBridge(Meeting meeting, Errors errors) {
	}

	/**
	 * validate meetingDate input
	 * @param meeting
	 * @param errors
	 */
	public void validateMeetingDate(Meeting meeting, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "meetingDate", "meeting.validate.meetingDate.required", "Meeting date is required.");
		// Logs
    	if (logger.isDebugEnabled()){
			
	    	logger.debug("Validating date...........");
	    	logger.debug("Meeting date : " + meeting.getMeetingDate());
	    	
    	}
    	if (meeting.getMeetingDate()!=null) {
    		if (meeting.getMeetingDate().before(new Date()))
    			errors.rejectValue("meetingDate", "meeting.validate.meetingDate.passed", "Meeting date is passed.");
    	}
	}
	
	/**
	 * validate dDate input
	 * @param meeting
	 * @param errors
	 */
	public void validateMeetingDuration(Meeting meeting, Errors errors) {
		
		// Logs
    	if (logger.isDebugEnabled()){
			
	    	logger.debug("Validating duration...........");
	    	logger.debug("Meeting duration : " + meeting.getMeetingDuration());
	    	
    	}
		
		boolean valid = time24HoursValidator.validate(meeting.getMeetingDuration());
		if (valid==false){
			errors.rejectValue("meetingDuration", "meeting.validate.meetingDuration.format", "Duration format HH:MM.");
		}
			
		
	}

}


