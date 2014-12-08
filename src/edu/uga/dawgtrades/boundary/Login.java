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

public class Login 
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
        String         username = null;
        String         password = null;
        String         ssid = null;
        Session        session = null;
        ObjectModel objectModel = null;
        Logic logic = null;
        RegisteredUser user = null;
        List<RegisteredUser> users = null; //users to be reviewed
        List<Auction> auctions = null; //auctions the user sold items in
        List<Bid> bids = null;

        // Load templates from the WEB-INF/templates directory of the Web app.
        //
        try {
            resultTemplate = cfg.getTemplate( resultTemplateName );
        } 
        catch (IOException e) {
            throw new ServletException( "Login.doPost: Can't load template in: " + templateDir + ": " + e.toString());
        }

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
        //
        username = req.getParameter( "username" );
        password = req.getParameter( "password" );

        if( username == null || password == null ) {
            DTError.error( cfg, toClient, "Unknown user name or password" );
            return;
        }

        try {
            ssid = SessionManager.login( username, password );
            System.out.println( "Obtained ssid: " + ssid );
            httpSession.setAttribute( "ssid", ssid );
            session = SessionManager.getSessionById( ssid );
            System.out.println( "Connection: " + session.getConnection() );
        } 
        catch ( Exception e ) {
            DTError.error( cfg, toClient, e );
            return;
        }
        
        objectModel = session.getObjectModel();
        if( objectModel == null ) {
            DTError.error( cfg, toClient, "Session expired or illegal; please log in" );
            return;
        }

        
        // Setup the data-model
        //
        Map<String, Object> root = new HashMap<String, Object>();
        
        logic = new LogicImpl( objectModel );
        
        
        try {
        	
			auctions = logic.findAuctionsOfUser(session.getUser().getId());
			users = new LinkedList<RegisteredUser>();
			root.put( "username", username );
	        root.put("users", users);
			//adds users who won auctions from the reviewer
	        for (int i = 0; i < auctions.size(); i++) {
	        	if (auctions.get(i).getIsClosed()) {
	        		bids = logic.findAllBids();
	        		for (int b = 0; b < bids.size(); b++) {
	        			if (bids.get(b).getAuction().getId() == auctions.get(i).getId()) {
	        				if (bids.get(b).getAmount() == auctions.get(i).getSellingPrice()) {
	        					user = bids.get(b).getRegisteredUser();
	        					users.add(user);
	        				}
	        			}
	        		}
	        	}
	        		
	        }
        }
        catch (DTException e1) {
			e1.printStackTrace();
		}
        // Build the data-model
        //
        
        
        

        // Merge the data-model and the template
        //
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

