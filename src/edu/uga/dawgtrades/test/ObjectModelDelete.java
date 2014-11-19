package edu.uga.dawgtrades.test;

import java.sql.Connection;
import java.util.Date;

import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Bid;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.ExperienceReport;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.Membership;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persist.Persistence;
import edu.uga.dawgtrades.persist.impl.DbUtils;
import edu.uga.dawgtrades.persist.impl.PersistenceImpl;

// Testing the deletion of the entity classes
// and associations for DawgTrades
public class ObjectModelDelete
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
             System.err.println( "ObjectModelDelete: Unable to obtain a database connection" );
         }
         
         // obtain a reference to the ObjectModel module      
         objectModel = new ObjectModelImpl();

         // obtain a reference to Persistence module and connect it to the ObjectModel        
         persistence = new PersistenceImpl( conn, objectModel ); 

         // connect the ObjectModel module to the Persistence module
         objectModel.setPersistence( persistence ); 
         
         Iterator<RegisteredUser> userIter = null;
                  
         try {
             
	         // Delete the RegisteredUser object
             // First: find the RegisteredUser club
             RegisteredUser registeredUser = null;
             RegisteredUser modelUser = objectModel.createRegisteredUser();
             modelUser.setName( "FirstUser" );
             userIter = objectModel.findRegisteredUser( modelUser );
             while( userIter.hasNext() ) {
                 registeredUser = userIter.next();
                 System.out.println( registeredUser );
             }
             // Second: delete the Registered User
             if( registeredUser != null ) {
                 objectModel.deleteClub( registeredUser );
                 System.out.println( "Deleted the registered User" );
             }
             else
                 System.out.println( "Failed to retrieve the registered User object" );
				 
          }   
         catch( DTException ce ) {
             System.err.println( "DTException: " + ce );
         }
         catch( Exception e ) {
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
