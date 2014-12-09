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
    static String resultTemplateName = "BrowseCategory-Result.ftl";

    private Configuration cfg;

    public void init() {
	cfg = new Configuration();
	cfg.setServletContextForTemplateLoading(getServletContext(), "WEB-INF/templates");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	Template resultTemplate = null;
	HttpSession httpSession = null;
	BufferedWriter toClient = null;
	String category_name = null;
	String ssid = null;
	Session session = null;
	ObjectModel objectModel = null;
	Logic logic = null;
	Category cat = null;
	Category realCat = null;
	//RegisteredUser user = null;
	List<Category> catBuf = null;
	List<List<Object>> categories = null;
	List<Object> category = null;
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

	category_name = req.getParameter("category");

	if(category == null) {
	    DTError.error(cfg, toClient, "invalid category");
	    return;
	}

	objectModel = session.getObjectModel();
	if(objectModel == null) {
	    DTError.error(cfg, toClient, "Session expired or illegal, please log in");
	    return;
	}

	Map<String, Object> root = new HashMap<String, Object>();

	logic = new LogicImpl(objectModel);

	root.put("category_name", category_name);

	try {
	    cat = objectModel.createCategory();
	    cat.setName(category_name);
	    Iterator<Category> catIter = objectModel.findCategory(cat);
	    while(catIter.hasNext())
		realCat = catIter.next();
	    catBuf = logic.findSubcategoriesOfCategory(realCat.getId());
	}
	catch(DTException e) {
	    e.printStackTrace();
	}

	categories = new LinkedList<List<Object>>();
	root.put("categories", categories);
	for(int i = 0; i < catBuf.size(); i++) {
	    cat = (Category)catBuf.get(i);
	    category = new LinkedList<Object>();
	    category.add( new Long(cat.getId()));
	    category.add(cat.getName());
	    category.add(new Long(cat.getParentId()));
	    categories.add(category);
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