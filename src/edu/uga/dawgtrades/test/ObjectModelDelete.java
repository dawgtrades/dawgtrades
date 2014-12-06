package edu.uga.dawgtrades.test;

import java.sql.Connection;
import java.util.Iterator;

import edu.uga.dawgtrades.model.*;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persist.Persistence;
import edu.uga.dawgtrades.persist.impl.*;

public class ObjectModelDelete {
    public static void main(String[] args) {
	Connection conn = null;
	ObjectModel objMod = null;
	Persistence pers = null;

	try {
	    conn = DbUtils.connect();
	}
	catch(Exception e) {
	    System.err.println("ObjectModelDelete: unable to obtain db connection");
	}

	objMod = new ObjectModelImpl();

	pers = new PersistenceImpl(conn, objMod);

	try {
	    objMod.setPersistence(pers);
	}
	catch(DTException e) {
	    System.out.println("can't set persistence");
	}

	Iterator<Category> catIter = null;
	Iterator<RegisteredUser> userIter = null;
	try {
		/**
	    //find electronics cat
	    Category electronics = null;
	    Category modCat = objMod.createCategory();
	    modCat.setName("electronics");
	    catIter = objMod.findCategory(modCat);
	    while(catIter.hasNext()) {
		electronics = catIter.next();
		System.out.println(electronics);
	    }
	    //delete electronics
	    if(electronics != null) {
		objMod.deleteCategory(electronics);
		System.out.println("Deleted electronics");
	    }
	    else
		System.out.println("Failed to retrieve electronics obj");
	    //find advanced computers
	    Category advancedComputers = null;
	    modCat.setName("Advanced Computers");
	    catIter = objMod.findCategory(modCat);
	    while(catIter.hasNext()) {
		advancedComputers = catIter.next();
		System.out.println(advancedComputers);
	    }
	    //delete advanced computers
	    if(advancedComputers != null) {
		objMod.deleteCategory(advancedComputers);
		System.out.println("Deleted advanced computers");
	    }
	    else
		System.out.println("Failed to retrieve advanced computers obj");
	    //find televisions
	    Category televisions = null;
	    modCat.setName("Televisions");
	    catIter = objMod.findCategory(modCat);
	    while(catIter.hasNext()) {
		televisions = catIter.next();
		System.out.println(televisions);
	    }
	    //delete televisions
	    if(televisions != null) {
		objMod.deleteCategory(televisions);
		System.out.println("Deleted televisions");
	    }
	    else
		System.out.println("Failed to retrieve televisions");
		**/
	    //find batman
	    RegisteredUser batman = null;
	    RegisteredUser modUser = objMod.createRegisteredUser();
	    modUser.setName("bman");
	    userIter = objMod.findRegisteredUser(modUser);
	    while(userIter.hasNext()) {
		batman = userIter.next();
		System.out.println(batman);
	    }
	    //delete batman
	    if(batman != null) {
		objMod.deleteRegisteredUser(batman);
		System.out.println("Deleted batman");
	    }
	    else
		System.out.println("Failed to retrieve batman obj");
	    //find superman
	    RegisteredUser superman = null;
	    modUser.setName("sman");
	    userIter = objMod.findRegisteredUser(modUser);
	    while(userIter.hasNext()) {
		superman = userIter.next();
		System.out.println(superman);
	    }
	    //delete superman
	    if(superman != null) {
		objMod.deleteRegisteredUser(superman);
		System.out.println("Deleted superman");
	    }
	    else
		System.out.println("Failed to retrieve superman obj");
	    
	    //CATEGORIES & ATTRIBUTE TYPES
	    Category modelCategory = objMod.createCategory();
	    AttributeType modelAttrType = objMod.createAttributeType();
	    Iterator<Category> categoryIter = objMod.findCategory(modelCategory);
	    Iterator<AttributeType> attrTypeIter = null;
	    while(categoryIter.hasNext()) {
	    	modelCategory = categoryIter.next();
	    	attrTypeIter = objMod.getAttributeType(modelCategory);
	    	System.out.println(modelCategory);
	    	while (attrTypeIter.hasNext()) {
	    		modelAttrType = attrTypeIter.next();
	    		System.out.println(modelAttrType);
	    		if (modelAttrType != null) {
	    			objMod.deleteAttributeType(modelAttrType);
	    			System.out.println("Deleted AttributeType");
	    		}
	    		else
	    			System.out.println("Failed to retrieve Attributetype obj");
	    	}
	    	if (modelCategory != null) {
	    		objMod.deleteCategory(modelCategory);
	    		System.out.println("Deleted Category");
	    	}
	    	else
	    		System.out.println("Failed to retrieve Category obj");
	    }
	    
	    //ITEMS & ATTRIBUTES
	    Item modelItem = objMod.createItem();
	    Attribute modelAttr = objMod.createAttribute();
	    Iterator<Item> itemIter = objMod.findItem(modelItem);
	    Iterator<Attribute> attrIter = null;
	    while(itemIter.hasNext()) {
	    	modelItem = itemIter.next();
	    	attrIter = objMod.getAttribute(modelItem);
	    	System.out.println(modelItem);
	    	while (attrIter.hasNext()) {
	    		modelAttr = attrIter.next();
	    		System.out.println(modelAttr);
	    		if (modelAttr != null) {
	    			objMod.deleteAttribute(modelAttr);
	    			System.out.println("Deleted attribute");
	    		}
	    		else
	    			System.out.println("Failed to retrieve attribute");
	    	}
	    	if (modelItem != null) {
	    		objMod.deleteItem(modelItem);
	    		System.out.println("Deleted item");
	    	}
	    	else
	    		System.out.println("Failed to retrieve itemj");
	    }
	    
	    //AUCTIONS
	    Auction modelAuction = objMod.createAuction();
	    Iterator<Auction> auctionIter = objMod.findAuction(modelAuction);
	    while (auctionIter.hasNext()) {
	    	modelAuction = auctionIter.next();
	    	System.out.println(modelAuction);
	    	if (modelAuction != null) {
	    		objMod.deleteAuction(modelAuction);
	    		System.out.println("Deleted Auction");
	    	}
	    	else
	    		System.out.println("Failed to retrieve auction");
	    }
	    
	    //BIDS
	    Bid modelBid = objMod.createBid();
	    Iterator<Bid> bidIter = objMod.findBid(modelBid);
	    while (bidIter.hasNext()) {
	    	modelBid = bidIter.next();
	    	System.out.println(modelBid);
	    	if (modelBid != null) {
	    		objMod.deleteBid(modelBid);
	    		System.out.println("Deleted Bid");
	    	}
	    	else
	    		System.out.println("Failed to retrieve bid");
	    }
	    
	    //REPORTS
	    ExperienceReport modelExperienceReport = objMod.createExperienceReport();
	    Iterator<ExperienceReport> experienceReportIter = objMod.findExperienceReport(modelExperienceReport);
	    while (experienceReportIter.hasNext()) {
	    	modelExperienceReport = experienceReportIter.next();
	    	System.out.println(modelExperienceReport);
	    	if (modelExperienceReport != null) {
	    		objMod.deleteExperienceReport(modelExperienceReport);
	    		System.out.println("Deleted ExperienceReport");
	    	}
	    	else
	    		System.out.println("Failed to retrieve report");
	    }
	    
	    //MEMBERSHIP
	    Membership modelMembership = objMod.findMembership();
	    System.out.println(modelMembership);
	    if (modelMembership != null) {
	    	objMod.deleteMembership(modelMembership);
	    	System.out.println("Deleted Membership");
	    }
	    else
			System.out.println("Failed to retrieve membership");
	    
	}
	catch(DTException e) {
	    System.err.println("DTException: " + e);
	}
	catch(Exception e) {
	    System.err.println("Exception: " + e);
	}
	finally {
	    try {
		conn.close();
	    }
	    catch(Exception e) {
		System.err.println("Exception: " + e);
	    }
	}
    }
}