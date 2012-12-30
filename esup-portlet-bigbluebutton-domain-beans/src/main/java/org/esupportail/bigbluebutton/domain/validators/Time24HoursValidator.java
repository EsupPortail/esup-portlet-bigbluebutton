package org.esupportail.bigbluebutton.domain.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author mkyong
 *
 */
public class Time24HoursValidator {
	
	
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
	@SuppressWarnings("nls")
	private static final String TIME24HOURS_PATTERN = 
	           "([01]?[0-9]|2[0-3]):[0-5][0-9]";
	
	/**
	 * 
	 */
	public Time24HoursValidator(){
		pattern = Pattern.compile(TIME24HOURS_PATTERN);
	}

	/**
	* Validate time in 24 hours format with regular expression
	* @param time time address for validation
	* @return true valid time format, false invalid time format
	*/
	public boolean validate(final String time){

    	try {
    		matcher = pattern.matcher(time);
    		return matcher.matches();
    	} catch (Exception e) {
    		logger.error("Matcher error in Time24HoursValidator : ", e);
    		return false;
		}
		

  }

}
