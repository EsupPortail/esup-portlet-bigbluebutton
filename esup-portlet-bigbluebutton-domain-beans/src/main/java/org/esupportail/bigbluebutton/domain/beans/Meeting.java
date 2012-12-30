package org.esupportail.bigbluebutton.domain.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The class that represent meetings.
 * @author Franck Bordinat
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(
		    name="allMeetings",
		    query="SELECT m FROM Meeting m"
		    ),
	@NamedQuery(
		    	    name="meetingsForUser",
		    	    query="SELECT m FROM Meeting m WHERE m.owner = :Owner ORDER BY m.creationDate"
		    ) 
})
public class Meeting {
	
	/**
	 * The serialization id.
	 */
	@Transient
	private static final long serialVersionUID = 5576556657584434915L;
	
	/**
	 * Id of the meeting.
	 */
	@Id
	@GeneratedValue
	private Integer id;

	/**
	 * Name of the meeting.
	 */
	@Column(nullable = false)
    @NotNull
    @Size(max = 255)
	private String name;
	
	/**
	 * welcome message.
	 */
	private String welcome;

	/**
	 * Attendee password.
	 */
	@Column(nullable = false)
    @NotNull
    @Size(max = 255)
	private String attendeePW;

	/**
	 * moderator password.
	 */
	@Column(nullable = false)
    @NotNull
    @Size(max = 255)
	private String moderatorPW;
	
	/**
	 * voiceBridge to join the meeting.
	 */
	@Column(nullable = false)
    @NotNull
	private Integer voiceBridge;
	
	/**
	 * Meeting's date.
	 */
    @Column(nullable = false)
    @NotNull
	private Date meetingDate;
	
    /**
	 * Meeting's duration in seconds.
	 */
	private String meetingDuration;
	
	/**
	 * Is the meeting running
	 * not persisted
	 */
	@Transient
	private boolean isrunning;
	

	/**
     * User who create the meeting
     */
	@Column(nullable = false)
    @NotNull
    private String owner;
	
	/**
	 * Invitations to the meeting
	 */
	@OneToMany(mappedBy="meeting", cascade=CascadeType.ALL)
	private List<Invitation> invitations;

	/**
	 * Date of creation.
	 */
    @Column(nullable = false)
    @NotNull
	private Date creationDate;

	/* ****************** Constructors ************ */
	
	/**
	 * Bean constructor.
	 */
	public Meeting() {
		invitations = new ArrayList<Invitation>();
	}

	/**
	 * @param m
	 */
	public Meeting(Meeting m) {
		super();
		this.id = m.id;
		this.name = m.name;
		this.welcome = m.welcome;
		this.attendeePW = m.attendeePW;
		this.moderatorPW = m.moderatorPW;
		this.voiceBridge = m.voiceBridge;
		this.meetingDate = m.meetingDate;
		this.owner = m.owner;
		this.creationDate = m.creationDate;
	}
	
	/**
	 * @param name
	 * @param welcome
	 * @param attendeePW
	 * @param moderatorPW
	 * @param voiceBridge
	 * @param meetingDate
	 * @param meetingDuration
	 * @param owner
	 * @param creationDate
	 */
	public Meeting(String name, String welcome, String attendeePW,
			String moderatorPW, Integer voiceBridge, Date meetingDate, String meetingDuration,
			String owner, List<Invitation> invitations, Date creationDate) {
		super();
		this.name = name;
		this.welcome = welcome;
		this.attendeePW = attendeePW;
		this.moderatorPW = moderatorPW;
		this.voiceBridge = voiceBridge;
		this.meetingDate = meetingDate;
		this.meetingDuration = meetingDuration;
		this.owner = owner;
		this.invitations = invitations;
		this.creationDate = creationDate;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return "Meeting#" + hashCode() + "[Name=["
				+ name + "], welcome=["
				+ welcome + "], attendeePW=[" + attendeePW + "], moderatorPW=[" + moderatorPW + "], voiceBridge=["
				+ voiceBridge + "], meetingDate=[" + meetingDate + "], meetingDuration=["
				+ meetingDuration + "], owner=[" + owner + "],  date=[" + creationDate + "]]";
	}

	/* ******************* Accessors ******************* */
	/**
	 * @return meeting id
	 */
	public Integer getId() {
		return id;
	}


	/**
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * @return meeting name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return meeting welcome message
	 */
	public String getWelcome() {
		return welcome;
	}

	/**
	 * @param welcome
	 */
	public void setWelcome(String welcome) {
		this.welcome = welcome;
	}
	
	/**
	 * @return meeting attendee password
	 */
	public String getAttendeePW() {
		return attendeePW;
	}


	/**
	 * @param attendeePW
	 */
	public void setAttendeePW(String attendeePW) {
		this.attendeePW = attendeePW;
	}


	/**
	 * @return meeting moderator password
	 */
	public String getModeratorPW() {
		return moderatorPW;
	}


	/**
	 * @param moderatorPW
	 */
	public void setModeratorPW(String moderatorPW) {
		this.moderatorPW = moderatorPW;
	}


	/**
	 * @return meeting voice bridge
	 */
	public Integer getVoiceBridge() {
		return voiceBridge;
	}

	/**
	 * @param voiceBridge
	 */
	public void setVoiceBridge(Integer voiceBridge) {
		this.voiceBridge = voiceBridge;
	}

	/**
	 * @return meeting date
	 */
	public Date getMeetingDate() {
		return meetingDate;
	}

	/**
	 * @param meetingDate
	 */
	public void setMeetingDate(Date meetingDate) {
		this.meetingDate = meetingDate;
	}

	/**
	 * @return meeting duration
	 */
	public String getMeetingDuration() {
		return meetingDuration;
	}

	/**
	 * @param meetingDuration
	 */
	public void setMeetingDuration(String meetingDuration) {
		this.meetingDuration = meetingDuration;
	}

	/**
	 * @return true if the meeting is running
	 */
	public boolean isIsrunning() {
		return isrunning;
	}

	/**
	 * @param isrunning
	 */
	public void setIsrunning(boolean isrunning) {
		this.isrunning = isrunning;
	}
	
	/**
	 * @return user login who added the meeting
	 */
	public String getOwner() {
		return owner;
	}


	/**
	 * @param owner
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	/**
	 * @return invitations
	 */
	public List<Invitation> getInvitations() {
		return invitations;
	}

	/**
	 * @param invitations
	 */
	public void setInvitations(List<Invitation> invitations) {
		this.invitations = invitations;
	}

	/**
	 * @return meeting creation date
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	
}
