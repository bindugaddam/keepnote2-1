package com.stackroute.keepnote.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * The class "Note" will be acting as the data model for the note Table in the database. Please
 * note that this class is annotated with @Entity annotation. Hibernate will scan all package for 
 * any Java objects annotated with the @Entity annotation. If it finds any, then it will begin the 
 * process of looking through that particular Java object to recreate it as a table in your database.
 */
@Entity
@Table(name = "Note")
public class Note implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*private static final long serialVersionUID = 44L;*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "noteId")
	private int noteId;
	@Column(name = "noteTitle")
	private String noteTitle;
	@Column(name = "noteContent")
	private String noteContent;
	@Column(name = "noteStatus")
	private String noteStatus;
	@Column(name = "createdAt")
	private LocalDateTime createdAt;

	public Note() {

	}

	public Note(int noteId, String noteTitle, String noteContent, String noteStatus, LocalDateTime createdAt) {
		this.noteId = noteId;
		this.noteTitle = noteTitle;
		this.noteContent = noteContent;
		this.noteStatus = noteStatus;
		this.createdAt = createdAt;
	}

	public int getNoteId() {

		return noteId;
	}

	public String getNoteTitle() {

		return noteTitle;
	}

	public String getNoteContent() {

		return noteContent;
	}

	public String getNoteStatus() {

		return noteStatus;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;

	}

	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;

	}

	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;

	}

	public void setNoteStatus(String noteStatus) {
		this.noteStatus = noteStatus;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;

	}
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) return false;
	    if (!(obj instanceof Note))
	        return false;
	    if (obj == this)
	        return true;
	    return this.getNoteId() == ((Note) obj).getNoteId();
	}

}
