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


// A class to illustrate how to delete entity objects and associations
//
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
         
         Iterator<Club> clubIter = null;
                  
         try {
             
	     // Delete the Running club object
             // First: find the Running club
             Club runningClub = null;
             Club modelClub = objectModel.createClub();
             modelClub.setName( "Running" );
             clubIter = objectModel.findClub( modelClub );
             while( clubIter.hasNext() ) {
                 runningClub = clubIter.next();
                 System.out.println( runningClub );
             }
             
             // Second: delete the Running club
             if( runningClub != null ) {
                 objectModel.deleteClub( runningClub );
                 System.out.println( "Deleted the Running club" );
             }
             else
                 System.out.println( "Failed to retrieve the Running Club object" );
             
	     // Delete Heather Brooks Person object
             // First: find Heather Brooks
             Person heatherBrooks = null;
             Person modelPerson = objectModel.createPerson();
             modelPerson.setFirstName( "Heather" );
             modelPerson.setLastName( "Brooks" );
             Iterator<Person> personIter = objectModel.findPerson( modelPerson );
             while( personIter.hasNext() ) {
                 heatherBrooks = personIter.next();
                 System.out.println( heatherBrooks );
             }
             
             // Second: delete Heather Brooks
             if( heatherBrooks != null ) {
                 objectModel.deletePerson( heatherBrooks );
                 System.out.println( "Deleted the Heather Brooks Person object" );
             }
             else
                 System.out.println( "Failed to find Heather Brooks" );
             
             // Delete the membership of Robert Wilson in the Bridge club
             // First: locate the Bridge club
             Club bridgeClub = null;
             modelClub = objectModel.createClub();
             modelClub.setName( "Bridge" );
             clubIter = objectModel.findClub( modelClub );
             while( clubIter.hasNext() ) {
                 bridgeClub = clubIter.next();
                 System.out.println( bridgeClub );
             }
             
             if( bridgeClub == null )
                 System.out.println( "Failed to retrieve the Bridge club object" );
             
             // Second: locate Robert Wilson
             Person robertWilson = null;
             modelPerson = objectModel.createPerson();
             modelPerson.setFirstName( "Robert" );
             modelPerson.setLastName( "Wilson" );
             personIter = objectModel.findPerson( modelPerson );
             while( personIter.hasNext() ) {
                 robertWilson = personIter.next();
                 System.out.println( robertWilson );
             }
             
             if( robertWilson == null )
                 System.out.println( "Failed to retrieve Robert Wilson person object" );
             
             // Third: locate the Membership record of Robert Wilson in the Bridge club
             if( bridgeClub != null && robertWilson != null ) {
                 Membership robertInbridgeMembership = null;
                 Membership modelMembership = objectModel.createMembership();
                 modelMembership.setClub( bridgeClub );
                 modelMembership.setPerson( robertWilson );
                 Iterator<Membership> membershipIter = objectModel.findMembership( modelMembership );
                 while( membershipIter.hasNext() ) {
                     robertInbridgeMembership = membershipIter.next();
                     System.out.println( robertInbridgeMembership );
                 }

                 // delete the Membership record of Robert Wilson in the Bridge club
                 if( robertInbridgeMembership != null ) {
                     objectModel.deleteMembership( robertInbridgeMembership );
                     System.out.println( "Deleted the membership of Robert Wilson in the Bridge club" );
                 }
                 else
                     System.out.println( "Failed to retrieve the membership of Robert Wilson in the Bridge club" );
             }

         }
         catch( ClubsException ce ) {
             System.err.println( "ClubsException: " + ce );
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
