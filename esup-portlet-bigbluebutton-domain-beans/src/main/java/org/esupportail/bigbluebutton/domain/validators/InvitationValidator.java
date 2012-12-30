package org.esupportail.bigbluebutton.domain.validators;

import org.esupportail.bigbluebutton.domain.beans.Invitation;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Franck Bordinat
 *
 */
public class InvitationValidator implements Validator{
	
	@Override
	public boolean supports(Class clazz) {
        return Invitation.class.isAssignableFrom(clazz);
    }
	
	/**
	 * logger
	 */
	private final Logger logger = new LoggerImpl(this.getClass());
	
	/**
	 * regexp validator
	 */
	private EmailValidator emailValidator = new EmailValidator();
	
	@Override
	public void validate(Object obj, Errors errors) {
        Invitation invitation = (Invitation)obj;
        validateDisplayName(invitation, errors);
        validateEmailAdress(invitation, errors);
    }

	/**
	 * validate guest display name input
	 * @param invitation 
	 * @param errors
	 */
	public void validateDisplayName(Invitation invitation, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "displayName", "invitation.validate.name.required", "Name is required.");
	}

	/**
	 * validate guest email input
	 * @param invitation 
	 * @param errors
	 */
	public void validateEmailAdress(Invitation invitation, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "emailAdress", "invitation.validate.email.required", "Email Adress is required.");
		
		boolean valid = emailValidator.validate(invitation.getEmailAdress());
		if (valid==false){
			errors.rejectValue("emailAdress", "invitation.validate.email.format", "Bad format in Email Adress.");
		}
	}
	
}
