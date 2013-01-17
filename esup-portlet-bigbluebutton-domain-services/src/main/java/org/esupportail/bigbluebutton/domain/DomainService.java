/**
 * esup-portlet-bigbluebutton - Copyright (c) 2012 ESUP-Portail consortium.
 */
package org.esupportail.bigbluebutton.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.esupportail.bigbluebutton.domain.beans.Invitation;
import org.esupportail.bigbluebutton.domain.beans.Meeting;
import org.esupportail.bigbluebutton.domain.beans.Recording;
import org.esupportail.bigbluebutton.domain.beans.User;


/**
 * @author Franck Bordinat (CUFR Jean François Champollion Albi) - 2012
 * 
 */
public interface DomainService extends Serializable {
	

	
	/**
	 * @param id
	 * @return meeting id (Integer)
	 */
	public Meeting getMeeting(Integer id);

	/**
	 * @param id
	 * @return meeting id (int)
	 */
	public Meeting getMeeting(int id);

	/**
	 * @return the list of all meetings
	 */
	public List<Meeting> getAllMeetings();
	
	/**
	 * @param login
	 * @return the list of meetings for the user
	 */
	public List<Meeting> getMeetingsForUser(String login);

	/**
	 * @param meeting
	 * @return the id of the inserted meeting
	 */
	public int addMeeting(Meeting meeting);
	
	/**
	 * @param name
	 * @param welcome
	 * @param attendeePW
	 * @param moderatorPW
	 * @param voiceBridge
	 * @param meetingDate
	 * @param meetingDuration
	 * @param owner
	 * @param invitations 
	 * @param creationDate
	 * @return the id of the inserted meeting
	 */
	public int addMeeting(String name, String welcome, String attendeePW,
			String moderatorPW, Integer voiceBridge, Date meetingDate, String meetingDuration, Boolean record,
			String owner, List<Invitation> invitations, Date creationDate);
	
	/**
	 * update meeting
	 * @param meeting
	 */
	public void updateMeeting(Meeting meeting);

	/**
	 * delete meeting
	 * @param id
	 */
	public void deleteMeeting(Integer id);

	/**
	 * delete meeting
	 * @param meeting
	 */
	public void deleteMeeting(Meeting meeting);

	/**
	 * delete meeting
	 * @param id
	 */
	public void deleteMeeting(int id);
	
	/**
	 * Create a meeting on BBB server and return a URL to join it as moderator
	 * @param meetingID
	 * @param meetingName
	 * @param welcome
	 * @param viewerPassword
	 * @param moderatorPassword
	 * @param voiceBridge
	 * @param username
	 * @return join meeting url
	 */
	public String createMeeting(String meetingID, String meetingName, String welcome, String viewerPassword, String moderatorPassword, Integer voiceBridge, Boolean record, String username);
	
	/**
	 * return Create meeting url on BBB server
	 * @param meetingID
	 * @param meetingName
	 * @param welcome
	 * @param viewerPassword
	 * @param moderatorPassword
	 * @param voiceBridge
	 * @param username
	 * @return create meeting url on BBB server
	 */
	public String createMeetingUrl(String meetingID, String meetingName, String welcome, String viewerPassword, String moderatorPassword, Integer voiceBridge, Boolean record, String username);

	
	/**
	 * getJoinMeetingURL() -- get join meeting URL for both viewer and moderator -- depend on password
	 * @param username
	 * @param meetingID
	 * @param password
	 * @return string url
	 */
	public String getJoinMeetingURL(String username, String meetingID, String password);
	
	/**
	 * getURLisMeetingRunning() -- return a URL that the client can use to poll for whether the given meeting is running
	 * @param meetingID
	 * @return URL
	 */
	public String getURLisMeetingRunning(String meetingID);
	
	/**
	 * isMeetingRunning() -- check the BigBlueButton server to see if the meeting is running (i.e. there is someone in the meeting)
	 * @param meetingID
	 * @return true if the meeting is running
	 */
	public String isMeetingRunning(String meetingID);
	
	/**
	 * getendMeetingURL() -- get the end meeting url
	 * @param meetingID
	 * @param moderatorPassword
	 * @return the end meeting url
	 */
	public String getendMeetingURL(String meetingID, String moderatorPassword);
	
	/**
	 * endMeeting() -- end the meeting using the end meetingurl
	 * @param meetingID
	 * @param moderatorPassword
	 * @return true or error
	 */
	public String endMeeting(String meetingID, String moderatorPassword);

	/**
	 * @param id
	 * @return Invitation
	 */
	Invitation getInvitation(Integer id);

	/**
	 * @param id
	 * @return Invitation
	 */
	Invitation getInvitation(int id);

	/**
	 * @return all invitations
	 */
	List<Invitation> getAllInvitations();

	/**
	 * @param meeting
	 * @return invitations for the current meeting
	 */
	List<Invitation> getInvitationsForMeeting(Meeting meeting);

	/**
	 * @param invitation
	 * @return inserted id invitation
	 */
	int addInvitation(Invitation invitation);

	/**
	 * @param displayName
	 * @param emailAdress
	 * @param meeting
	 * @param creationDate
	 * @return added id invitation
	 */
	int addInvitation(String displayName, String emailAdress, Meeting meeting,
			Date creationDate);

	/**
	 * @param invitation
	 */
	void updateInvitation(Invitation invitation);

	/**
	 * @param id
	 */
	void deleteInvitation(Integer id);

	/**
	 * @param invitation
	 */
	void deleteInvitation(Invitation invitation);

	/**
	 * @param id
	 */
	void deleteInvitation(int id);

	/**
	 * @param bBBServerUrl
	 */
	void setBBBServerUrl(String bBBServerUrl);

	/**
	 * @return the BBB server url
	 */
	String getBBBServerUrl();

	/**
	 * @param searchUser
	 * @return ldap users
	 */
	List<User> searchUserInLdap(String searchUser);
	
	/**
	 * @param meetingID
	 * @return the getRecordings URL
	 */
	public String getRecordingsURL(String meetingID);
	
	/**
	 * @param meetingID
	 * @return list of recording for the meeting
	 */
	public List<Recording> getRecordings(String meetingID);
	
	/**
	 * @param publish
	 * @param recordID
	 * @return the getPublishRecordings URL
	 */
	public String getPublishRecordingsURL(boolean publish, String recordID);
	
	
	/**
	 * @param publish
	 * @param recordID
	 * @return string (response)
	 * set the publish status to the recording
	 */
	public String setPublishRecordings(boolean publish, String recordID);
	
	
	/**
	 * @param recordID
	 * @return the getDeleteRecordings URL
	 */
	public String getDeleteRecordingsURL(String recordID);
	
	
	/**
	 * @param recordID
	 * @return string (response)
	 * delete the recording
	 */
	public String deleteRecordings(String recordID);

	Recording addRecording(String recordID, String name, String description,
			String startTime, String published, String playback, String length);
	
	
}
