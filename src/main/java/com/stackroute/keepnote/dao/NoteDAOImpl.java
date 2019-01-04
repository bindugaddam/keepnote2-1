package com.stackroute.keepnote.dao;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.stackroute.keepnote.model.Note;

/*
 * This class is implementing the NoteDAO interface. This class has to be annotated with @Repository
 * annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, thus 
 * 				 clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */
@Repository("noteDAO")
@Transactional
public class NoteDAOImpl implements NoteDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.
	 */
	@Autowired
	private SessionFactory sessionFactory;

	public NoteDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;

	}

	/*
	 * Save the note in the database(note) table.
	 */
	// @Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false)
	public boolean saveNote(Note note) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			int count=(Integer)session.save(note);
			if (count > 0) {
				return true;
			}
			/*
			 * Query query = session.createSQLQuery(
			 * "insert into Note (createdAt, noteContent, noteStatus, noteTitle) values (:createdAt, :noteContent, :noteStatus, :noteTitle)"
			 * ); Instant instant =
			 * note.getCreatedAt().toInstant(ZoneOffset.UTC);
			 * query.setParameter("createdAt", Date.from(instant));
			 * query.setParameter("noteContent", note.getNoteContent());
			 * query.setParameter("noteStatus", note.getNoteStatus());
			 * query.setParameter("noteTitle", note.getNoteTitle()); int count =
			 * query.executeUpdate();
			 */

			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {

			if (session != null && session.isOpen()) { // session.flush();
				session.clear();
				session.close();
			}

		}
		return false;

	}

	/*
	 * Remove the note from the database(note) table.
	 */

	public boolean deleteNote(int noteId) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			int count = session.createQuery("DELETE FROM Note WHERE noteId = " + noteId).executeUpdate();
			session.getTransaction().commit();
			if (count > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				// session.flush();
				session.clear();
				session.close();
			}
		}
		return false;

	}

	/*
	 * retrieve all existing notes sorted by created Date in descending
	 * order(showing latest note first)
	 */
	@SuppressWarnings("unchecked")
	public List<Note> getAllNotes() {
		return (List<Note>) sessionFactory.getCurrentSession().createCriteria(Note.class).list();

	}

	/*
	 * retrieve specific note from the database(note) table
	 */
	// @Transactional(propagation=Propagation.REQUIRES_NEW,isolation=Isolation.REPEATABLE_READ)
	public Note getNoteById(int noteId) {
		System.out.println(noteId + "check all the level");
		Note note = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			note = (Note) session.get(Note.class, noteId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			  if (session != null && session.isOpen()) { //session.flush();
			  session.clear(); session.close(); }
			 
		}
		return note;

	}

	/* Update existing note */
	@Transactional
	public boolean UpdateNote(Note note) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.saveOrUpdate(note);
			tx.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
