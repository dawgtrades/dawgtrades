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


// Testing the creation of the entity classes
// and associations for DawgTrades
public class ObjectModelCreate
{
    public static void main(String[] args)
    {
         Connection conn = null;
         ObjectModel objectModel = null;
         Persistence persistence = null;
         
         RegisteredUser batman;
         RegisteredUser superman;
         Membership mship;
         Item computer;
         Item tv;
         AttributeType screenSize;
         AttributeType resolution;
         Attribute small;
         Attribute large;
         Attribute SD;
         Attribute HD;
         Auction computerAuction;
         Auction tvAuction;
         Category electronics;
         Category computers;
         Category televisions;
         ExperienceReport report1;
         ExperienceReport report2;
         Bid bid1;
         Bid bid2;
         Bid bid3;
         Bid bid4;
         
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
             
             // create a few people
             joe = objectModel.createPerson( "joe", "joepass", "joe@mail.com", "Joe", "Doe", 
					     "133 Maple St., Big Town, AZ. 87888", "333-4456" );
             objectModel.storePerson( joe );

             mary = objectModel.createPerson( "mary", "marypass", "mary@mail.com", "Mary", "Swift", 
					      "14 Oak Dr., Small Town, TX. 77888", "444-9876" );
             objectModel.storePerson( mary );

             bob = objectModel.createPerson( "bob", "bobpass", "bob@mail.com", "Robert", "Wilson", 
					     "33 Cedar Cr., Middle Town, NV. 81888", "567-7788" );
	     objectModel.storePerson( bob );

             julie = objectModel.createPerson( "julie", "juliepass", "julie@mail.com", "Julie", "Hart", 
					       "99 Magnolia St., Splash Town, NY. 21888", "364-7592" );
	     objectModel.storePerson( julie );

             heather = objectModel.createPerson( "heather", "heatherpass", "julie@mail.com", "Heather", "Brooks", 
						 "1 Pine Ave., Boom Town, GA. 30688", "339-9923" );
	     objectModel.storePerson( heather );
             

             bridge = objectModel.createClub( "Bridge", "33 Leaf St., Blossom, OR. 88888", new Date(), joe );
	     objectModel.storeClub( bridge );
             
             chess = objectModel.createClub( "Chess", "734 Pine Straw Dr., Bloom, KY. 48878", new Date(), mary );
             objectModel.storeClub( chess );
             
             tennis = objectModel.createClub( "Tennis", "333 Wide St., Flower, RI. 17345", new Date(), mary );
             objectModel.storeClub( tennis );
             
             running = objectModel.createClub( "Running", "445 Pace St., Quicker, Wy. 77546", new Date(), bob );
             objectModel.storeClub( running );
             

             membership = objectModel.createMembership( joe, bridge, new Date() );
             objectModel.storeMembership( membership );
             
             membership = objectModel.createMembership( bob, bridge, new Date() );
             objectModel.storeMembership( membership );
             
             membership = objectModel.createMembership( heather, bridge, new Date() );
             objectModel.storeMembership( membership );
             
             membership = objectModel.createMembership( mary, chess, new Date() );
             objectModel.storeMembership( membership );
             
             membership = objectModel.createMembership( mary, tennis, new Date() );
             objectModel.storeMembership( membership );
             
             membership = objectModel.createMembership( julie, tennis, new Date() );
             objectModel.storeMembership( membership );
             
             membership = objectModel.createMembership( bob, tennis, new Date() );
             objectModel.storeMembership( membership );
             
             membership = objectModel.createMembership( joe, chess, new Date() );
             objectModel.storeMembership( membership );
             
             System.out.println( "Entity objects created and saved in the persistence module" );
             
         }
         catch( ClubsException ce) {
             System.err.println( "Exception: " + ce );
             ce.printStackTrace();
         }
         catch( Exception e ) {
             e.printStackTrace();
         }
         finally {
             // close the connection
             try {
                 conn.close();
             }
             catch( Exception e ) {
                 System.err.println( "Exception: " + e );
		 e.printStackTrace();
             }
         }
    }  
}
