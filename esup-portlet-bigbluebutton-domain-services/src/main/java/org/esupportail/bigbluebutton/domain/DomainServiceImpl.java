/**
 * esup-portlet-bigbluebutton - Copyright (c) 2012 ESUP-Portail consortium.
 */
package org.esupportail.bigbluebutton.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.esupportail.bigbluebutton.dao.DaoService;
import org.esupportail.bigbluebutton.domain.beans.Invitation;
import org.esupportail.bigbluebutton.domain.beans.Meeting;
import org.esupportail.bigbluebutton.domain.beans.User;
import org.esupportail.bigbluebutton.utils.WebUtils;
import org.esupportail.commons.services.ldap.LdapUser;
import org.esupportail.commons.services.ldap.LdapUserService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;



/**
 * @author Franck Bordinat (CUFR Jean François Champollion Albi) - 2012
 * Part of this code is provided by BigBlueButton under the terms of the GNU Lesser General Public License
 * in API sample codes  
 * 
 */
public class DomainServiceImpl implements DomainService, InitializingBean {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = 5562208937407153456L;

	/**
	 * For Logging.
	 */
	private final Logger logger = new LoggerImpl(this.getClass());

	
	/**
	 * DaoService
	 */
	private DaoService daoService;
	
	/**
	 * BigBlueButton server url
	 */
	private String BBBServerUrl;

	/**
	 * BigBlueButton security salt
	 */
	private String BBBSecuritySalt;
	
	/**
	 * BigBlueButton logout url
	 */
	private String BBBLogoutUrl;

	private User chosenUser;
	private List<User> resultSearch;
	private LdapUserService ldapUserService;

	/**
	 * Constructor.
	 */
	public DomainServiceImpl() {
		super();
	}

	///////////////////////////
	// Accessors
	///////////////////////////
	
	/**
	 * @param bBBServerUrl
	 */
	@Override
	public void setBBBServerUrl(String bBBServerUrl) {
		BBBServerUrl = bBBServerUrl;
	}

	/**
	 * @return String the BBB Server Url
	 */
	@Override
	public String getBBBServerUrl() {
		return BBBServerUrl;
	}

	/**
	 * @param bBBSecuritySalt
	 */
	public void setBBBSecuritySalt(String bBBSecuritySalt) {
		BBBSecuritySalt = bBBSecuritySalt;
	}

	/**
	 * @return String the BBB Server security salt
	 */
	public String getBBBSecuritySalt() {
		return BBBSecuritySalt;
	}

	/**
	 * @return String the BBB Server logout url
	 */
	public String getBBBLogoutUrl() {
		return BBBLogoutUrl;
	}

	/**
	 * @param bBBLogoutUrl
	 */
	public void setBBBLogoutUrl(String bBBLogoutUrl) {
		BBBLogoutUrl = bBBLogoutUrl;
	}
	
	/**
	 * @return the daoservice
	 */
	public DaoService getDaoService() {
		return daoService;
	}

	/**
	 * @param daoService
	 */
	public void setDaoService(DaoService daoService) {
		this.daoService = daoService;
	}


	/**
	 * @return the ldapuserservice
	 */
	public LdapUserService getLdapUserService() {
		return ldapUserService;
	}

	/**
	 * @param ldapUserService
	 */
	public void setLdapUserService(LdapUserService ldapUserService) {
		this.ldapUserService = ldapUserService;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.daoService, 
				"property daoService of class " + this.getClass().getName() + " can not be null");
		Assert.notNull(this.BBBServerUrl,
				"property BBBServerUrl of class " + this.getClass().getName() + " can not be null. Check your configuration files.");
		Assert.notNull(this.BBBSecuritySalt,
				"property BBBSecuritySalt of class " + this.getClass().getName() + " can not be null. Check your configuration files.");
	
	}
	
	///////////////////////////
	// User
	///////////////////////////
	
	@Override
	public List<User> searchUserInLdap(String searchUser){
		
		try {
			List<LdapUser> listOfLdapUser = ldapUserService.getLdapUsersFromToken(searchUser);
			resultSearch=new ArrayList<User>();
			for (LdapUser ldapUser : listOfLdapUser) {
				User user=new User();
				user.setLogin(ldapUser.getAttribute("uid"));
				user.setDisplayName(ldapUser.getAttribute("displayName"));
				user.setEmailAdress(ldapUser.getAttribute("mail"));
				resultSearch.add(user);
			}
			return resultSearch;
		} catch (Exception e) {
			logger.error("Could not do ldap request...", e);
			return null;
		}
		
		
	}

	///////////////////////////
	// Meeting
	///////////////////////////

	
	@Override
	public Meeting getMeeting(Integer id) {
		return daoService.getMeeting(id);
	}

	@Override
	public Meeting getMeeting(int id) {
		return daoService.getMeeting(Integer.valueOf(id));
	}

	//
	// return all meetings
	//
	@Override
	public List<Meeting> getAllMeetings() {
		return daoService.getMeetings();
	}
	
	//
	// return the meetings list created by a user
	//
	@Override
	public List<Meeting> getMeetingsForUser(String login) {
		return daoService.getMeetingsForUser(login);
	}
	
	//
	// Add a meeting instance
	//
	@Override
	public int addMeeting(Meeting meeting) {
		daoService.addMeeting(meeting);
		return meeting.getId();
	}
	
	//
	// Add a meeting instance
	//
	@Override
	public int addMeeting(String name, String welcome, String attendeePW,
			String moderatorPW, Integer voiceBridge, Date meetingDate, String meetingDuration,
			String owner, List<Invitation> invitations, Date creationDate) {
		Meeting meeting = new Meeting(name, welcome, attendeePW,
				moderatorPW, voiceBridge, meetingDate, meetingDuration,
				owner, invitations, creationDate);
		return addMeeting(meeting);
	}

	//
	// update current meeting
	//
	@Override
	public void updateMeeting(Meeting meeting){
		daoService.updateMeeting(meeting);
	}

	//
	// delete current meeting
	//
	@Override
	public void deleteMeeting(Integer id) {
		daoService.deleteMeeting(getMeeting(id));
	}

	//
	// delete current meeting
	//
	@Override
	public void deleteMeeting(Meeting meeting) {
		daoService.deleteMeeting(meeting);
	}

	//
	// delete current meeting
	//
	@Override
	public void deleteMeeting(int id) {
		deleteMeeting(Integer.valueOf(id));
	}
	
	//
	//Create a meeting on BBB server and return a URL to join it as moderator
	//
	@Override
	public String createMeeting(String meetingID, String meetingName, String welcome, String viewerPassword, String moderatorPassword, Integer voiceBridge, String username) {
		
		String base_url_create = BBBServerUrl + "api/create?";
		String base_url_join = BBBServerUrl + "api/join?";

		String welcome_param = "";
		String checksum = "";

		String attendee_password_param = "&attendeePW=" + viewerPassword;
		String moderator_password_param = "&moderatorPW=" + moderatorPassword;
		String voice_bridge_param = "";
		String logoutURL_param = "";
		WebUtils utils = new WebUtils();

		if ((welcome != null) && !welcome.equals("")) {
			welcome_param = "&welcome=" + utils.urlEncode(welcome);
		}

		if ((moderatorPassword != null) && !moderatorPassword.equals("")) {
			moderator_password_param = "&moderatorPW=" + utils.urlEncode(moderatorPassword);
		}

		if ((viewerPassword != null) && !viewerPassword.equals("")) {
			attendee_password_param = "&attendeePW=" + utils.urlEncode(viewerPassword);
		}

		if ((voiceBridge != null) && !voiceBridge.equals("")) {
			voice_bridge_param = "&voiceBridge=" + voiceBridge;
		}
		
		if ((BBBLogoutUrl != null) && !BBBLogoutUrl.equals("")) {
			logoutURL_param = "&logoutURL=" + utils.urlEncode(BBBLogoutUrl);
		}

		//
		// Now create the URL
		//

		String create_parameters = "name=" + utils.urlEncode(meetingName)
			+ "&meetingID=" + utils.urlEncode(meetingID) + welcome_param
			+ attendee_password_param + moderator_password_param
			+ voice_bridge_param + logoutURL_param;

		Document doc = null;

		try {
			// Attempt to create a meeting using meetingID
			String xml = utils.getURL(base_url_create + create_parameters
				+ "&checksum="
				+ utils.checksum("create" + create_parameters + BBBSecuritySalt));
			doc = utils.parseXml(xml);
		} catch (Exception e) {
			logger.error("Could not complete request...", e);
			logger.error("url : " + BBBServerUrl + base_url_create + create_parameters
					+ "&checksum="
					+ utils.checksum("create" + create_parameters + BBBSecuritySalt));
		}
    	
		if (doc.getElementsByTagName("returncode").item(0).getTextContent().trim().equals("SUCCESS")) {
			//
			// Looks good, now return a URL to join that meeting
			//  

			String join_parameters = "meetingID=" + utils.urlEncode(meetingID)
				+ "&fullName=" + utils.urlEncode(username) + "&password=" + moderatorPassword;

			return base_url_join + join_parameters + "&checksum="
				+ utils.checksum("join" + join_parameters + BBBSecuritySalt);
		}
		
		logger.info("BBB Server Error "
    			+ doc.getElementsByTagName("messageKey").item(0).getTextContent().trim()
    			+ ": "
    			+ doc.getElementsByTagName("message").item(0).getTextContent()
    		.trim());
		return "Error "
			+ doc.getElementsByTagName("messageKey").item(0).getTextContent().trim()
			+ ": "
			+ doc.getElementsByTagName("message").item(0).getTextContent()
		.trim();
	}
	
	
	//
	//return Create meeting url on BBB server
	//
	@Override
	public String createMeetingUrl(String meetingID, String meetingName, String welcome, String viewerPassword, String moderatorPassword, Integer voiceBridge, String username) {
		
		String base_url_create = BBBServerUrl + "api/create?";
		String base_url_join = BBBServerUrl + "api/join?";

		String welcome_param = "";
		String checksum = "";

		String attendee_password_param = "&attendeePW=" + viewerPassword;
		String moderator_password_param = "&moderatorPW=" + moderatorPassword;
		String voice_bridge_param = "";
		String logoutURL_param = "";
		WebUtils utils = new WebUtils();

		if ((welcome != null) && !welcome.equals("")) {
			welcome_param = "&welcome=" + utils.urlEncode(welcome);
		}

		if ((moderatorPassword != null) && !moderatorPassword.equals("")) {
			moderator_password_param = "&moderatorPW=" + utils.urlEncode(moderatorPassword);
		}

		if ((viewerPassword != null) && !viewerPassword.equals("")) {
			attendee_password_param = "&attendeePW=" + utils.urlEncode(viewerPassword);
		}

		if ((voiceBridge != null) && !voiceBridge.equals("")) {
			voice_bridge_param = "&voiceBridge=" + voiceBridge;
		}
		
		if ((BBBLogoutUrl != null) && !BBBLogoutUrl.equals("")) {
			logoutURL_param = "&logoutURL=" + utils.urlEncode(BBBLogoutUrl);
		}

		//
		// Now create the URL
		//

		String create_parameters = "name=" + utils.urlEncode(meetingName)
			+ "&meetingID=" + utils.urlEncode(meetingID) + welcome_param
			+ attendee_password_param + moderator_password_param
			+ voice_bridge_param + logoutURL_param;



		String url = base_url_create + create_parameters
				+ "&checksum="
				+ utils.checksum("create" + create_parameters + BBBSecuritySalt);
		
		return url;
	}
	
	//
	// getJoinMeetingURL() -- get join meeting URL for both viewer and moderator -- depend on password
	//
	@Override
	public String getJoinMeetingURL(String username, String meetingID, String password) {
		WebUtils utils = new WebUtils();
		String base_url_join = BBBServerUrl + "api/join?";
		String join_parameters = "meetingID=" + utils.urlEncode(meetingID)
			+ "&fullName=" + utils.urlEncode(username) + "&password="
			+ utils.urlEncode(password);

		return base_url_join + join_parameters + "&checksum="
			+ utils.checksum("join" + join_parameters + BBBSecuritySalt);
	}

	//
	// getURLisMeetingRunning() -- return a URL that the client can use to poll for whether the given meeting is running
	//
	@Override
	public String getURLisMeetingRunning(String meetingID) {
		WebUtils utils = new WebUtils();
		String meetingParameters = "meetingID=" + utils.urlEncode(meetingID);
		return BBBServerUrl + "api/isMeetingRunning?" + meetingParameters
			+ "&checksum="
			+ utils.checksum("isMeetingRunning" + meetingParameters + BBBSecuritySalt);	
	}
	
	//
	// isMeetingRunning() -- check the BigBlueButton server to see if the meeting is running (i.e. there is someone in the meeting)
	//
	@Override
	public String isMeetingRunning(String meetingID) {
		Document doc = null;
		WebUtils utils = new WebUtils();
		try {
			doc = utils.parseXml( utils.getURL(getURLisMeetingRunning(meetingID) ));
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (doc.getElementsByTagName("returncode").item(0).getTextContent()
				.trim().equals("SUCCESS")) {
			return doc.getElementsByTagName("running").item(0).getTextContent()
			.trim();
		}

		return "Error "
			+ doc.getElementsByTagName("messageKey").item(0)
			.getTextContent().trim()
			+ ": "
			+ doc.getElementsByTagName("message").item(0).getTextContent()
			.trim();
	}

	

	//
	// getendMeetingURL() -- get the end meeting url
	//
	@Override
	public String getendMeetingURL(String meetingID, String moderatorPassword) {
		WebUtils utils = new WebUtils();
		String end_parameters = "meetingID=" + utils.urlEncode(meetingID) + "&password="
			+ utils.urlEncode(moderatorPassword);
		return BBBServerUrl + "api/end?" + end_parameters + "&checksum="
			+ utils.checksum("end" + end_parameters + BBBSecuritySalt);
	}

	//
	// endMeeting() -- end the meeting using the end meetingurl
	//
	@Override
	public String endMeeting(String meetingID, String moderatorPassword) {

		Document doc = null;
		WebUtils utils = new WebUtils();
		try {
			String xml = utils.getURL(getendMeetingURL(meetingID, moderatorPassword));
			doc = utils.parseXml(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (doc.getElementsByTagName("returncode").item(0).getTextContent()
				.trim().equals("SUCCESS")) {
			return "true";
		}

		return "Error "
			+ doc.getElementsByTagName("messageKey").item(0)
			.getTextContent().trim()
			+ ": "
			+ doc.getElementsByTagName("message").item(0).getTextContent()
			.trim();
	}

	///////////////////////////
	// Invitation
	///////////////////////////

	
	@Override
	public Invitation getInvitation(Integer id) {
		return daoService.getInvitation(id);
	}

	@Override
	public Invitation getInvitation(int id) {
		return daoService.getInvitation(Integer.valueOf(id));
	}

	//
	// return all invitations
	//
	@Override
	public List<Invitation> getAllInvitations() {
		return daoService.getInvitations();
	}
	
	//
	// return the invitations list created by a user
	//
	@Override
	public List<Invitation> getInvitationsForMeeting(Meeting meeting) {
		return daoService.getInvitationsForMeeting(meeting);
	}
	
	//
	// Add a invitation instance
	//
	@Override
	public int addInvitation(Invitation invitation) {
		daoService.addInvitation(invitation);
		return invitation.getId();
	}
	
	//
	// Add a invitation instance
	//
	@Override
	public int addInvitation(String displayName, String emailAdress, Meeting meeting, Date creationDate) {
		Invitation invitation = new Invitation(displayName, emailAdress, meeting, creationDate);
		return addInvitation(invitation);
	}

	//
	// update current invitation
	//
	@Override
	public void updateInvitation(Invitation invitation){
		daoService.updateInvitation(invitation);
	}

	//
	// delete current invitation
	//
	@Override
	public void deleteInvitation(Integer id) {
		daoService.deleteInvitation(getInvitation(id));
	}

	//
	// delete current invitation
	//
	@Override
	public void deleteInvitation(Invitation invitation) {
		daoService.deleteInvitation(invitation);
	}

	//
	// delete current invitation
	//
	@Override
	public void deleteInvitation(int id) {
		deleteInvitation(Integer.valueOf(id));
	}
	
	
}
