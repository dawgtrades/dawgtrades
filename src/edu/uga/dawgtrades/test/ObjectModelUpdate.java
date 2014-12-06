package edu.uga.dawgtrades.test;


import java.sql.Connection;
import java.util.Iterator;

import edu.uga.dawgtrades.model.*;
import edu.uga.dawgtrades.model.impl.*;
import edu.uga.dawgtrades.persist.*;
import edu.uga.dawgtrades.persist.impl.*;

public class ObjectModelUpdate
{
    public static void main(String[] args)
    {
         Connection  conn = null;
         ObjectModel objectModel = null;
         Persistence persistence = null;

         // get a database connection
         try {
             conn = DbUtils.connect();
         } 
         catch (Exception seq) {
             System.err.println( "ObjectModelUpdate: Unable to obtain a database connection" );
         }
         
         // obtain a reference to the ObjectModel module      
         objectModel = new ObjectModelImpl();

         // obtain a reference to Persistence module and connect it to the ObjectModel        
         persistence = new PersistenceImpl( conn, objectModel ); 

         // connect the ObjectModel module to the Persistence module
	 try {
         objectModel.setPersistence( persistence ); 
	 }
	 catch(DTException e) {
	     System.err.println("ObjectModelUpdate: something something something");
	 }

         
         Iterator<Category> catIter = null;
                  
         try {
             
	     // modify the name of the "Computers" cat to "Advanced Computers"
	     // First: locate the Computers cat
             Category computers = null;
             Category modelCat = objectModel.createCategory();
             modelCat.setName( "Computers" );
             catIter = objectModel.findCategory( modelCat );
             while( catIter.hasNext() ) {
		         computers = catIter.next();
             }
             
	     // Second: modify the name of the "Computers cat to "Advanced Computers"
	     // and store the updated cat
	     if( computers != null ) {
		 computers.setName( "Advanced Computers" );
		 objectModel.storeCategory( computers );
		 System.out.println( "Updated the name of the Computers cat to Advanced Computers" );
	     }
	     else
		 System.out.println( "Failed to retrieve the Computers cat object" );
             
	     // modify the phone number of batman
	     // First: locate batman
             RegisteredUser batman = null;
             RegisteredUser modelRegUser = objectModel.createRegisteredUser();
	     //             modelRegUser.setFirstName( "Bruce" );
             modelRegUser.setLastName( "Wayne" );
             Iterator<RegisteredUser> regUserIter = objectModel.findRegisteredUser( modelRegUser );
             while( regUserIter.hasNext() ) {
                 batman = regUserIter.next();
             }
             
             // Second: modify the phone number of batman to (111) 123-4567
	     // and store the updated Person object
             if( batman != null ) {
		 batman.setPhone( "777-777-7777" );
		 objectModel.storeRegisteredUser( batman );
		 System.out.println( "Updated the phone number of batman to (777) 777-7777" );
             }
		 else
             System.out.println( "Failed to retrieve the batman Person object" );
		 
		 
		 //Finding item
         Item macbook = null;
	     Item modelItem = objectModel.createItem();
	     modelItem.setName("Apple Macbook");
	     Iterator<Item> itemIter = objectModel.findItem(modelItem);
	     while( itemIter.hasNext() ) {
	         macbook = itemIter.next();
	     }
	     
             
	     //Updating Item
	     if( macbook != null ) {
		 macbook.setName( "Dell" );
		 macbook.setDescription("Great condition, used just over a month");
		 objectModel.storeItem( macbook );
		 System.out.println( "Updated the name of the macbook item to Dell & its description" );
	     }
	     else
		 System.out.println( "Failed to retrieve the macbook item object" );
	     
         
         //Finding ExperienceReport
         ExperienceReport report = null;
	     ExperienceReport modelExperienceReport = objectModel.createExperienceReport();
	     modelExperienceReport.setReport("5/5 would buy again");
	     Iterator<ExperienceReport> reportIter = objectModel.findExperienceReport(report);
	     while( reportIter.hasNext() ) {
	         report = reportIter.next();
	     }
             
	     //Updating ExperienceReport
	     if( report != null ) {
		 report.setRating(3);
		 report.setReport("It was an okay experience");
		 objectModel.storeExperienceReport( report );
		 System.out.println( "Updated the rating and report of the ExperienceReport" );
	     }
	     else
	     System.out.println( "Failed to retrieve the ExperienceReport object" );
	     
	     //Finding Membership
	     Membership mship = objectModel.findMembership();
             
	     //Updating Membership
	     if( mship != null ) {
		 mship.setPrice(50);
		 objectModel.storeMembership( mship );
		 System.out.println( "Updated the price of the Membership" );
	     }
	     else
	     System.out.println( "Failed to retrieve the Membership object" );
	     
         }
         
         
         
         catch( DTException ce) {
             System.err.println( "DTException: " + ce );
         }
         catch( Exception e) {
             System.err.println( "Exception: " + e );
         }
         finally {
             // close the connection
             try {
                 conn.close();
             }
             catch( Exception e ) {
                 System.err.println( "Exception: " + e );
             }
         }        
    }    
}
