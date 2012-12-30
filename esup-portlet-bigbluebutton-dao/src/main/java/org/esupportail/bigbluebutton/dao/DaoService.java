package org.esupportail.bigbluebutton.dao;

import java.io.Serializable;
import java.util.List;

import org.esupportail.bigbluebutton.domain.beans.Invitation;
import org.esupportail.bigbluebutton.domain.beans.Meeting;
import org.esupportail.bigbluebutton.domain.beans.User;

public interface DaoService extends Serializable {

	
	//////////////////////////////////////////////////////////////
	// Meeting
	//////////////////////////////////////////////////////////////
	/**
	 * Get all meeting.
	 */
	public List<Meeting> getMeetings();
	
	/**
	 * Add a meeting.
	 * @param meeting
	 */
	void addMeeting(Meeting meeting);

	/**
	 * Delete a meeting.
	 * @param meeting
	 */
	void deleteMeeting(Meeting meeting);

	/**
	 * Update a meeting.
	 * @param meeting
	 */
	Meeting updateMeeting(Meeting meeting);
	/**
	 * @param id
	 * @return the Meeting instance that corresponds to an id.
	 */
	Meeting getMeeting(Integer id);
	
	public List<Meeting> getMeetingsForUser(String login);
	
	
	//////////////////////////////////////////////////////////////
	// Invitation
	//////////////////////////////////////////////////////////////

	/**
	 * @param invitation
	 */
	void addInvitation(Invitation invitation);

	/**
	 * @param invitation
	 */
	void deleteInvitation(Invitation invitation);

	/**
	 * @param invitation
	 * @return updated invitation
	 */
	Invitation updateInvitation(Invitation invitation);

	/**
	 * @param id
	 * @return invitation
	 */
	Invitation getInvitation(Integer id);

	/**
	 * @return all invitations
	 */
	List<Invitation> getInvitations();

	/**
	 * @param meeting
	 * @return invitations for the current meeting
	 */
	List<Invitation> getInvitationsForMeeting(Meeting meeting);
	
	
}
