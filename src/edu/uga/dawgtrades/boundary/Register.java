package edu.uga.dawgtrades.boundary;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uga.dawgtrades.authentication.Session;
import edu.uga.dawgtrades.authentication.SessionManager;
import edu.uga.dawgtrades.logic.Logic;
import edu.uga.dawgtrades.logic.impl.LogicImpl;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Bid;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ExperienceReport;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Register 
    extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    static  String  templateDir = "WEB-INF/templates";
    static  String  resultTemplateName = "RegisteredUserWindow.ftl";

    private Configuration  cfg; 

    public void init() 
    {
        // Prepare the FreeMarker configuration;
        // - Load templates from the WEB-INF/templates directory of the Web app.
        //
        cfg = new Configuration();
        cfg.setServletContextForTemplateLoading(
                getServletContext(), 
                "WEB-INF/templates"
                );
    }

    public void doPost( HttpServletRequest req, HttpServletResponse res )
            throws ServletException, IOException
    {
        Template       resultTemplate = null;
        HttpSession    httpSession = null;
        BufferedWriter toClient = null;
		String         lastname = null;
        String         firstname = null;
        String         phone = null;
        String         email = null;
        String         username = null;
        String         password = null;
		boolean		   cantext = false;
		boolean        isAdmin = false;
        String         ssid = null;
        Session        session = null;
        ObjectModel    objectModel = null;
        Logic          logic = null;
        RegisteredUser user = null;
        // get the ftl template
        try {
            resultTemplate = cfg.getTemplate( resultTemplateName );
        } 
        catch (IOException e) {
            throw new ServletException( "Register.doPost: Can't load template in: " + templateDir + ": " + e.toString());
        }
        // setup the http session
        httpSession = req.getSession();
        ssid = (String) httpSession.getAttribute( "ssid" );
        if( ssid != null ) {
            System.out.println( "Already have ssid: " + ssid );
            session = SessionManager.getSessionById( ssid );
            System.out.println( "Connection: " + session.getConnection() );
        }
        else
            System.out.println( "ssid is null" );

        // Prepare the HTTP response:
        // - Use the charset of template for the output
        // - Use text/html MIME-type
        //
        toClient = new BufferedWriter( new OutputStreamWriter( res.getOutputStream(), resultTemplate.getEncoding() ) );
        res.setContentType("text/html; charset=" + resultTemplate.getEncoding());

        // Get the parameters
		lastname = req.getParameter( "lastname");
        firstname = req.getParameter( "firstname");
        phone = req.getParameter( "phone");
        email = req.getParameter( "email");
        username = req.getParameter( "username" );
        password = req.getParameter( "password" );
		//cantext = req.getParameter( "cantext" );
        // validate the input data
        if( username == null || password == null || lastname == null ||	firstname  == null || email == null ) {
            DTError.error( cfg, toClient, "Last name, first name, user name, email and password cannot be null" );
            return;
        }

        // Setup the data-model
        Map<String, Object> root = new HashMap<String, Object>();
        logic = new LogicImpl( objectModel );

		// call the create registered user method
        try {
            //resultTemplate = cfg.getTemplate( resultTemplateName );
			long cr = logic.createRegisteredUser(username, firstname, lastname, password, isAdmin, email, phone, cantext);
        } 
        catch (DTException e) {
            throw new ServletException( "Error attempting to create RegisteredUser method from Register servlet" + e.toString());
        }  
		
        // Build the data model
		root.put( "lastname", lastname );
		root.put( "firstname", firstname );
		root.put( "phone", phone );
		root.put( "", email );
		root.put( "username", username );
		root.put( "password", password );
		root.put( "cantext", cantext );
		
        // Merge the data-model and the template
        try {
            resultTemplate.process( root, toClient );
            toClient.flush();
        } 
        catch (TemplateException e) {
            throw new ServletException( "Error while processing FreeMarker template", e);
        }

        toClient.close();
    }
}

