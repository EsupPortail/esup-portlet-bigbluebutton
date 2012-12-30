package org.esupportail.bigbluebutton.domain.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
 
/**
 * @author esup
 *
 */
public class EmailValidator{
 
	/**
	 * logger
	 */
	private final Logger logger = new LoggerImpl(this.getClass());
	/**
	 * pattern validator
	 */
	private Pattern pattern;
	/**
	 * matcher validator
	 */
	private Matcher matcher;
 
	/**
	 * regexp
	 */
	private static final String EMAIL_PATTERN = 
                   "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
 
	  /**
	 * 
	 */
	public EmailValidator(){
		  pattern = Pattern.compile(EMAIL_PATTERN);
	  }
 
	  /**
	   * Validate hex with regular expression
	   * @param hex hex for validation
	   * @return true valid hex, false invalid hex
	   */
	  public boolean validate(final String hex){
 
	    	try {
	    		matcher = pattern.matcher(hex);
	    		return matcher.matches();
	    	} catch (Exception e) {
	    		logger.error("Matcher error in EmailValidator : ", e);
	    		return false;
			}
 
	  }
}