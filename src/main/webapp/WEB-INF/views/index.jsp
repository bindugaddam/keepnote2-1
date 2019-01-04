<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<!DOCTYPE html>
<html lang="en">

<head>
<title>Keep-Board</title>
</head>

<body>
	<!-- Create a form which will have text boxes for Note title, content and status along with a Add 
		 button. Handle errors like empty fields.  (Use dropdown-list for NoteStatus) -->
<form:form id="keepNoteForm" modelAttribute="Note" action="saveNote"
		method="post">
		<table align="center">
			<tr>
				<td><form:label path="noteId">Note ID: </form:label></td>
				<td><form:input path="noteId" name="noteId" id="noteId" /></td>
			</tr>
			<tr>
				<td><form:label path="noteTitle">Title:</form:label></td>
				<td><form:password path="noteTitle" name="noteTitle"
						id="noteTitle" /></td>
			</tr>
			<tr>
				<td><form:label path="noteContent">Content: </form:label></td>
				<td><form:input path="noteContent" name="noteContent"
						id="noteContent" /></td>
			</tr>
			<tr>
				<td><form:label path="noteStatus">Status: </form:label></td>
				<td><form:input path="noteStatus" name="noteStatus"
						id="noteStatus" /></td>
			</tr>
			<tr>
				<td></td>
				<td align="left"><form:button id="save" name="save">save</form:button>
				</td>
			</tr>
			<tr></tr>
		</table>
	</form:form>

	<!-- display all existing notes in a tabular structure with Title,Content,Status, Created Date and Action -->
		<table>
	<tr>
		<th>Id.</th>
		<th>Title</th>
		<th>Content</th>
		<th>Status</th>
		<th>Created Date</th>
		<th>Action</th>
	</tr>
	 <c:forEach var="note" items="${allnotes}">   
   <tr>  
   <td>${note.noteId}</td>  
   <td>${note.noteTitle}</td>  
   <td>${note.noteContent}</td>  
   <td>${note.noteStatus}</td>  
   <td>${note.createdAt}</td>
   <%-- <td><a href="editemp/${emp.id}">Edit</a></td>   --%>
   <td><a href="delete/${note.noteId}">Delete</a></td>
   <td><a href="update/${note}">update</a></td>  
   </tr>  
   </c:forEach>  
</table>
</body>

</html>