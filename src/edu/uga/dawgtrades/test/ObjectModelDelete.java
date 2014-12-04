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