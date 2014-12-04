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
		 System.out.println( "Updated the phone number of batman to (111) 123-4567" );
	     }
	     else
                 System.out.println( "Failed to retrieve the batman Person object" );

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
