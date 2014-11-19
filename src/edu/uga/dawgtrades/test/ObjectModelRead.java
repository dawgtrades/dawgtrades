package edu.uga.clubs.test;

import java.sql.Connection;
import java.util.Date;
import java.util.Iterator;

import edu.uga.clubs.ClubsException;
import edu.uga.clubs.model.Club;
import edu.uga.clubs.model.Membership;
import edu.uga.clubs.model.ObjectModel;
import edu.uga.clubs.model.Person;
import edu.uga.clubs.model.impl.ObjectModelImpl;
import edu.uga.clubs.persistence.Persistence;
import edu.uga.clubs.persistence.impl.DbUtils;
import edu.uga.clubs.persistence.impl.PersistenceImpl;


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
             System.err.println( "ObjectModelDelete: Unable to obtain a database connection" );
         }
         
         // obtain a reference to the ObjectModel module      
         objectModel = new ObjectModelImpl();

         // obtain a reference to Persistence module and connect it to the ObjectModel        
         persistence = new PersistenceImpl( conn, objectModel ); 

         // connect the ObjectModel module to the Persistence module
         objectModel.setPersistence( persistence );   
                  
         try {
             
	     // Retrieve all existing Club objects and list their founders and members
             System.out.println( "Club objects:" );
             Iterator<Club> clubIter = objectModel.findClub( null );
             while( clubIter.hasNext() ) {
                 Club c = clubIter.next();
                 System.out.println( c );
                 Person founder = objectModel.findEstablishedBy( c );
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
