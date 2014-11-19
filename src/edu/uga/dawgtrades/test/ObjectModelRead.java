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


// A simple class to read (restore) entity objects from the persistent
// data store.
public class ObjectModelRead
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
             System.err.println( "ObjectModelRead: Unable to obtain a database connection" );
         }
         
         // obtain a reference to the ObjectModel module      
         objectModel = new ObjectModelImpl();

         // obtain a reference to Persistence module and connect it to the ObjectModel        
         persistence = new PersistenceImpl( conn, objectModel ); 

         // connect the ObjectModel module to the Persistence module
         objectModel.setPersistence( persistence );   
                  
         try {
             
	     // Retrieve all existing user objects
             System.out.println( "user objects:" );
             Iterator<RegisteredUser> userIter = objectModel.findRegisteredUser( null );
             while( userIter.hasNext() ) {
 
				 RegisteredUser registeredUser = userIter.next();
                 System.out.println( registeredUser );
                 Person founder = objectModel.findEstablishedBy( registeredUser );
                 System.out.println( "   Founded by: " + founder );
                 System.out.println( "   Members: " );
                 Iterator<Membership> membershipIter = objectModel.findMembership( c );
                 while( membershipIter != null && membershipIter.hasNext() ) {
                     Membership m = membershipIter.next();
                     System.out.println( "      " + m.getPerson() );
                 }
             }
             
	     // Retrieve all existing Person objects and list clubs they founded 
	     // and in which they are members
             System.out.println( "Person objects:" );
             Iterator<Person> personIter = objectModel.findPerson( null );
             while( personIter.hasNext() ) {
                 Person p = personIter.next();
                 System.out.println( p );
                 System.out.print( "   Founder of: " );
                 clubIter = objectModel.findEstablishedBy( p );
                 while( clubIter != null && clubIter.hasNext() ) {
                     Club c = clubIter.next();
                     System.out.print( c + " " );
                 }
                 System.out.println();
                 System.out.println( "   Member of: " );
                 Iterator<Membership> membershipIter = objectModel.findMembership( p );
                 while( membershipIter != null && membershipIter.hasNext() ) {
                     Membership m = membershipIter.next();
                     System.out.println( "      " + m.getClub() );
                 }
             }

         }
         catch( ClubsException ce)
         {
             System.err.println( "ClubsException: " + ce );
         }
         catch( Exception e)
         {
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
