package com.stackroute.keepnote.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.stackroute.keepnote.dao.NoteDAO;
import com.stackroute.keepnote.model.Note;

/*
 * Annotate the class with @Controller annotation.@Controller annotation is used to mark 
 * any POJO class as a controller so that Spring can recognize this class as a Controller
 */
@Controller
public class NoteController {
	/*
	 * From the problem statement, we can understand that the application
	 * requires us to implement the following functionalities.
	 * 
	 * 1. display the list of existing notes from the persistence data. Each
	 * note should contain Note Id, title, content, status and created date. 2.
	 * Add a new note which should contain the note id, title, content and
	 * status. 3. Delete an existing note 4. Update an existing note
	 * 
	 */

	/*
	 * Autowiring should be implemented for the NoteDAO. Create a Note object.
	 * 
	 */
	@Autowired
	private NoteDAO noteDAO;

	public NoteController(NoteDAO noteDAO) {
		// TODO Auto-generated constructor stub
		this.noteDAO = noteDAO;
	}
	/*
	 * Define a handler method to read the existing notes from the database and
	 * add it to the ModelMap which is an implementation of Map, used when
	 * building model data for use with views. it should map to the default URL
	 * i.e. "/index"
	 */
	//@RequestMapping("/")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getAllNotes(HttpServletRequest request, HttpServletResponse response,ModelMap map) {

		List<Note> notes = noteDAO.getAllNotes();
		map.addAttribute("allnotes", notes);

		return "index";
	}
	/*
	 * Define a handler method which will read the NoteTitle, NoteContent,
	 * NoteStatus from request parameters and save the note in note table in
	 * database. Please note that the CreatedAt should always be auto populated
	 * with system time and should not be accepted from the user. Also, after
	 * saving the note, it should show the same along with existing messages.
	 * Hence, reading note has to be done here again and the retrieved notes
	 * object should be sent back to the view using ModelMap This handler method
	 * should map to the URL "/add".
	 */
	@RequestMapping(value = "/Note", method = RequestMethod.GET)
	public ModelAndView showNote(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("Note", new Note());
		return mav;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addNote(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("Note") Note note, ModelMap map) {
		LocalDateTime today = LocalDateTime.now();
		note.setCreatedAt(today);
		boolean status =noteDAO.saveNote(note);
		
		if (status) {
			//response.setStatus(302);
		    map.addAttribute("allnotes", note);
			return "redirect:"+"/";

		}
		else{
			//response.setStatus(200);
			System.out.println(status+"please check the codre"+response.getStatus());
			return "index";
		}
		//return "";
		
	}

	/*
	 * Define a handler method which will read the NoteId from request
	 * parameters and remove an existing note by calling the deleteNote() method
	 * of the NoteRepository class.This handler method should map to the URL
	 * "/delete".
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@RequestParam("noteId") String noteId,HttpServletRequest request,
			HttpServletResponse response) {
		try {
			
         noteDAO.deleteNote(Integer.parseInt(noteId));
         response.setStatus(302);
         System.out.println(response.getStatus()+"<<<<>>>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:"+"/";
	}

	/*
	 * Define a handler method which will update the existing note. This handler
	 * method should map to the URL "/update".
	 */
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@ModelAttribute("Note") Note note,HttpServletRequest request,
			HttpServletResponse response) {
		try {
			//System.out.println(note.getNoteContent()+"update check.....");
			noteDAO.UpdateNote(note);
			 response.setStatus(302);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:"+"/";
	}

}
