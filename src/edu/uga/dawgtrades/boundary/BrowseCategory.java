package edu.uga.dawgtrades.boundary;

import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import edu.uga.dawgtrades.authentication.*;
import edu.uga.dawgtrades.logic.Logic;
import edu.uga.dawgtrades.logic.impl.LogicImpl;
import edu.uga.dawgtrades.model.*;
import freemarker.template.*;

public class BrowseCategory extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static String templateDir = "WEB-INF/templates";
    static String resultTemplateName = "CategoryWindow.ftl";

    private Configuration cfg;

    public void init() {
	cfg = new Configuration();
	cfg.setServletContextForTemplateLoading(getServletContext(), "WEB-INF/templates");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	Template resultTemplate = null;
	HttpSession httpSession = null;
	BufferedWriter toClient = null;
	String category = null;
	String ssid = null;
	//Session session = null;
	//ObjectModel objectModel = null;
	//Logic logic = null;
	//RegisteredUser user = null;
	//List<RegisteredUser> users = null;
	//List<Auction> auctions = null;
	//List<Bid> bids = null;
    }
}