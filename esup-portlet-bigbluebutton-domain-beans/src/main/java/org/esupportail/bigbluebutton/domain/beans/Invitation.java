package org.esupportail.bigbluebutton.domain.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * The class that represent invitations.
 * @author Franck Bordinat
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(
		    name="allInvitations",
		    query="SELECT i FROM Invitation i"
		    ),
	@NamedQuery(
		    	    name="invitationsForMeeting",
		    	    query="SELECT i FROM Invitation i WHERE i.meeting = :Meeting ORDER BY i.creationDate"
		    ) 
})
public class Invitation {
	
	/**
	 * Invitation id
	 */
	@Id
	@GeneratedValue
	private Integer id;
	
	/**
	 * Display Name of the guest.
	 */
	@Column(nullable = false)
	private String displayName;
	
	/**
	 * Email address of the guest.
	 */
	@Column(nullable = false)
	private String emailAdress;
	
	/**
	 * concerned meeting
	 */
	@ManyToOne
    Meeting meeting;
	
	/**
	 * Date of creation.
	 */
    @Column(nullable = false)
	private Date creationDate;

	/* ****************** Constructors ************ */
	/**
	 * Bean constructor.
	 */
	public Invitation() {
		super();
	}

	/**
	 * Bean constructor.
	 * @param displayName 
	 * @param emailAdress 
	 * @param meeting 
	 * @param creationDate 
	 */
	public Invitation(String displayName, String emailAdress,
			Meeting meeting, Date creationDate) {
		super();
		this.displayName = displayName;
		this.emailAdress = emailAdress;
		this.meeting = meeting;
		this.creationDate = creationDate;
	}
	
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return "Invitation#" + hashCode() + "[displayName=["
				+ displayName + "], emailAdress=["
				+ emailAdress + "], meeting=[" + meeting.getId() + "], date=[" + creationDate + "]]";
	}

	/* ******************* Accessors ******************* */
	
	/**
	 * @return long invitation id
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
	 * @return DispayName of the guest.
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return emailAdress of the guest.
	 */
	public String getEmailAdress() {
		return emailAdress;
	}

	/**
	 * @param emailAdress
	 */
	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}

	/**
	 * @return invitation's meeting
	 */
	public Meeting getMeeting() {
		return meeting;
	}

	/**
	 * @param meeting
	 */
	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	/**
	 * @return invitation creation date
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
