package org.ladle.webapp.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.jboss.logging.Logger;
import org.ladle.dao.hibernate.object.Region;

/**
 * Servlet implementation class Index
 */
@WebServlet("")
public class Index extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static SessionFactory factory;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Index() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	/** ===================================================================================== **/
    	try {

    		factory = new Configuration().configure().buildSessionFactory();

    	} catch (Throwable ex) { 
    		System.err.println("Failed to create sessionFactory object." + ex);
    		throw new ExceptionInInitializerError(ex); 
    	}

    	List<Region> regionsToSend = new ArrayList<>();
    	Session session = null;
    	Transaction tx = null;

    	try {
    		session = factory.openSession();
    		tx = session.beginTransaction();
    		
			@SuppressWarnings("unchecked")
			List<Region> regions = session.createQuery("FROM Region").list();
			
			regionsToSend.addAll(regions);

    		for (Iterator<Region> iterator = regions.iterator(); iterator.hasNext();){
    			Region region = iterator.next(); 
    			System.out.print("ID:" + region.getRegionID()); 
    			System.out.print(" Nom:" + region.getNom()); 
    			System.out.println(" Soundex:" + region.getSoundex());
    		}
    		tx.commit();
    	} catch (HibernateException e) {
    		if (tx!=null) tx.rollback();
    		e.printStackTrace(); 
    	} finally {
    		session.close(); 
    	}
    	

    	/** ===================================================================================== **/

	   try {
		   
		   Logger logger = Logger.getLogger(Index.class);
		   logger.info("Servlet : index");
		   
		   request.setAttribute("myList", regionsToSend);
		   
		   this.getServletContext().getRequestDispatcher( "/WEB-INF/index.jsp" ).forward( request, response );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			doGet(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
