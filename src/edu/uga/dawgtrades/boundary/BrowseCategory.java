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
	Session session = null;
	ObjectModel objectModel = null;
	Logic logic = null;
	//RegisteredUser user = null;
	List<Category> categories = null;
	//List<RegisteredUser> users = null;
	//List<Auction> auctions = null;
	//List<Bid> bids = null;

	try {
	    resultTemplate = cfg.getTemplate(resultTemplateName);
	}
	catch(IOException e) {
	    throw new ServletException("BrowseCategory.doGet: can't load template in: " + templateDir + ": " + e.toString());
	}

	httpSession = req.getSession(false);
	if(httpSession != null) {
	    ssid = (String) httpSession.getAttribute("ssid");
	    if(ssid != null) {
		session = SessionManager.getSessionById(ssid);
	    }
	    else
		System.out.println("ssid is null");
	}
	else
	    System.out.println("no http session");

	toClient = new BufferedWriter(new OutputStreamWriter(res.getOutputStream(), resultTemplate.getEncoding()));

	res.setContentType("text/html; charset=" + resultTemplate.getEncoding());

	category = req.getParameter("category");

	if(category == null) {
	    DTError.error(cfg, toClient, "invalid category");
	    return;
	}

	objectModel = session.getObjectModel();
	if(objectModel == null) {
	    DTError.error(cfg, toClient, "Session expired or illegal, please log in");
	    return;
	}

	HashMap<Integer, Object> root = new HashMap<Integer, Object>();

	logic = new LogicImpl(objectModel);

	try {
	    categories = logic.findSubcategoriesOfCategory(0);
	    for(int i = 0; i < categories.size(); i++) {
		root.put(i, categories.get(i));
	    }
	}
	catch(DTException e) {
	    e.printStackTrace();
	}

	try {
	    resultTemplate.process(root, toClient);
	    toClient.flush();
	}
	catch(TemplateException e) {
	    throw new ServletException("Error while processing FreeMarker template", e);
	}
	toClient.close();
    }
}