package com.san.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private StudentDbUtil studentDbUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource; 
	
	

	@Override
	public void init() throws ServletException {		
		super.init();
		
		//create our student db util .. and pass in the conn pool/ datasource
		try {
			studentDbUtil= new StudentDbUtil(dataSource); 
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
		
	}



	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// list the students .. in MVC fashion
		try {
			listStudent(request,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ServletException(e);
		}
	}



	private void listStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// get students from db util
		List<Student> students = studentDbUtil.getStudents();
		
		// add students to the request
		request.setAttribute("STUDENT_LIST", students);
		
		//send to JSP page
		RequestDispatcher dispatcher =request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
	}



}
