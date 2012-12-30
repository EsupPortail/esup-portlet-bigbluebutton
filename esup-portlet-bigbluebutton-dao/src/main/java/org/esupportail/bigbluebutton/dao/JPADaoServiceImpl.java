/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-blank
 */
package org.esupportail.bigbluebutton.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.esupportail.commons.dao.AbstractGenericJPADaoService;
import org.esupportail.bigbluebutton.domain.beans.Meeting;
import org.esupportail.bigbluebutton.domain.beans.Invitation;;

/**
 * The Hiberate implementation of the DAO service.
 */
public class JPADaoServiceImpl extends AbstractGenericJPADaoService implements DaoService {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 3152554337896617315L;
	
	/**
	 * JPA entity manager
	 */
	EntityManager entityManager;

	/**
	 * Bean constructor.
	 */
	public JPADaoServiceImpl() {
		super();
	}
	
	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		
	}

	//////////////////////////////////////////////////////////////
	// EntityManager
	//////////////////////////////////////////////////////////////

	/**
	 * @param em the em to set
	 */
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	/**
	 * @see org.esupportail.commons.dao.AbstractGenericJPADaoService#getEntityManager()
	 */
	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	/**
	 * @see org.esupportail.todolist.dao.DaoService#getUser(java.lang.String)
	 */
	

	//////////////////////////////////////////////////////////////
	// Meeting
	//////////////////////////////////////////////////////////////
	

	@SuppressWarnings("unchecked")
	public List<Meeting> getMeetings() {
		Query q = entityManager.createNamedQuery("allMeetings");
		List<Meeting> ret = (List<Meeting>)q.getResultList();
		return ret;
	}
	@SuppressWarnings("unchecked")
	public List<Meeting> getMeetingsForUser(String login) {
		Query q = entityManager.createNamedQuery("meetingsForUser");
		q.setParameter("Owner", login);
		List<Meeting> ret = (List<Meeting>)q.getResultList();
		return ret;
	} 

	@Override
	public void addMeeting(Meeting meeting) {
		entityManager.persist(meeting);
		
	}

	@Override
	public void deleteMeeting(Meeting meeting) {
		Meeting t = entityManager.find(Meeting.class, meeting.getId());
		entityManager.remove(t);
		
	}

	@Override
	public Meeting updateMeeting(Meeting meeting) {
		return entityManager.merge(meeting);
	}

	@Override
	public Meeting getMeeting(Integer id) {
		Meeting meeting = entityManager.find(Meeting.class, id);
		return meeting;
	}

	//////////////////////////////////////////////////////////////
	// Invitation
	//////////////////////////////////////////////////////////////
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Invitation> getInvitations() {
		Query q = entityManager.createNamedQuery("allInvitations");
		List<Invitation> ret = q.getResultList();
		return ret;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Invitation> getInvitationsForMeeting(Meeting meeting) {
		Query q = entityManager.createNamedQuery("invitationsForMeeting");
		q.setParameter("Meeting", meeting);
		List<Invitation> ret = q.getResultList();
		return ret;
	} 

	@Override
	public void addInvitation(Invitation invitation) {
		entityManager.persist(invitation);
		
	}

	@Override
	public void deleteInvitation(Invitation invitation) {
		Invitation t = entityManager.find(Invitation.class, invitation.getId());
		entityManager.remove(t);
		
	}

	@Override
	public Invitation updateInvitation(Invitation invitation) {
		return entityManager.merge(invitation);
	}

	@Override
	public Invitation getInvitation(Integer id) {
		Invitation invitation = entityManager.find(Invitation.class, id);
		return invitation;
	}

	
}
