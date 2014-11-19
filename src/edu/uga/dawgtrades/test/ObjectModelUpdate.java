package edu.uga.clubs.test;


import java.sql.Connection;
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


// A class to illustrate how to perform an update of entity objects
//
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
             System.err.println( "ObjectModelDelete: Unable to obtain a database connection" );
         }
         
         // obtain a reference to the ObjectModel module      
         objectModel = new ObjectModelImpl();

         // obtain a reference to Persistence module and connect it to the ObjectModel        
         persistence = new PersistenceImpl( conn, objectModel ); 

         // connect the ObjectModel module to the Persistence module
         objectModel.setPersistence( persistence ); 
         
         Iterator<Club> clubIter = null;
                  
         try {
             
	     // modify the name of the "Tennis" club to "Advanced Tennis"
	     // First: locate the Tennis club
             Club tennisClub = null;
             Club modelClub = objectModel.createClub();
             modelClub.setName( "Tennis" );
             clubIter = objectModel.findClub( modelClub );
             while( clubIter.hasNext() ) {
                 tennisClub = clubIter.next();
                 System.out.println( tennisClub );
             }
             
	     // Second: modify the name of the "Tennis" club to "Advanced Tennis"
	     // and store the updated club
	     if( tennisClub != null ) {
		 tennisClub.setName( "Advanced Tennis" );
		 objectModel.storeClub( tennisClub );
		 System.out.println( "Updated the name of the Tenis club to Advanced Tennis" );
	     }
	     else
		 System.out.println( "Failed to retrieve the Tennis Club object" );
             
	     // modify the phone number of Mary Swift
	     // First: locate Mary Swift
             Person marySwift = null;
             Person modelPerson = objectModel.createPerson();
             modelPerson.setFirstName( "Mary" );
             modelPerson.setLastName( "Swift" );
             Iterator<Person> personIter = objectModel.findPerson( modelPerson );
             while( personIter.hasNext() ) {
                 marySwift = personIter.next();
                 System.out.println( marySwift );
             }
             
             // Second: modify the name of the Tennis club to "Advanced Tennis"
	     // and store the updated Person object
             if( marySwift != null ) {
		 marySwift.setPhone( "(111) 123-4567" );
		 objectModel.storePerson( marySwift );
		 System.out.println( "Updated the phone number of Mary Swift to (111) 123-4567" );
	     }
	     else
                 System.out.println( "Failed to retrieve the Mary Swift Person object" );

         }
         catch( ClubsException ce) {
             System.err.println( "ClubsException: " + ce );
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
