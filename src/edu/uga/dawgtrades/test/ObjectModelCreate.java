package edu.uga.dawgtrades.test;

import java.sql.Connection;
import java.util.Date;

import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Bid;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ExperienceReport;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.Membership;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persist.Persistence;
import edu.uga.dawgtrades.persist.impl.DbUtils;
import edu.uga.dawgtrades.persist.impl.PersistenceImpl;


/** Testing the creation of the entity classes and associations for DawgTrades
 * 
 * @author Justin
 *
 */
public class ObjectModelCreate
{
    public static void main(String[] args) throws DTException
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
             System.err.println( "ObjectModelCreate: Unable to obtain a database connection" );
         }
         
         // obtain a reference to the ObjectModel module      
         objectModel = new ObjectModelImpl();
         // obtain a reference to Persistence module and connect it to the ObjectModel        
         persistence = new PersistenceImpl( conn, objectModel ); 
         // connect the ObjectModel module to the Persistence module
         objectModel.setPersistence( persistence);  

         try {
             
             //users
             batman = objectModel.createRegisteredUser("bman", "Bruce", "Wayne", "batmobile", true, "bman@yahoo.com", "678-938-2342", true);
             objectModel.storeRegisteredUser(batman);
             
             System.out.println(batman.getId());
             
             superman = objectModel.createRegisteredUser("sman", "Clark", "Kent", "kryptonite", false, "sman@gmail.com", "706-234-1212", false);
             objectModel.storeRegisteredUser(superman);
             
             //membership
             mship = objectModel.createMembership(15, new Date());
             objectModel.storeMembership(mship);
             
             //categories
             electronics = objectModel.createCategory();
             electronics.setName("electronics");
             objectModel.storeCategory(electronics);
             computers = objectModel.createCategory(electronics, "Computers");
             objectModel.storeCategory(computers);
             televisions = objectModel.createCategory(electronics, "Televisions");
             objectModel.storeCategory(televisions);
             
             //items
             computer = objectModel.createItem(computers, batman, "PC5407", "Apple Macbook", "Used for two years");
             objectModel.storeItem(computer);
             tv = objectModel.createItem(televisions, superman, "TV649", "Samsung TV", "Brand new in box");
             objectModel.storeItem(tv);
             
             //attribute types
             screenSize = objectModel.createAttributeType(computers, "Screen Size");
             objectModel.storeAttributeType(screenSize);
             screenSize = objectModel.createAttributeType(televisions, "Screen Size");
             objectModel.storeAttributeType(screenSize);
             resolution = objectModel.createAttributeType(computers, "Resolution");
             objectModel.storeAttributeType(resolution);
             resolution = objectModel.createAttributeType(televisions, "Resolution");
             objectModel.storeAttributeType(resolution);
             
             //attributes
             small = objectModel.createAttribute(screenSize, computer, "small");
             objectModel.storeAttribute(small);
             large = objectModel.createAttribute(screenSize, tv, "large");
             objectModel.storeAttribute(large);
             SD = objectModel.createAttribute(resolution, tv, "SD");
             objectModel.storeAttribute(SD);
             HD = objectModel.createAttribute(resolution, computer, "HD");
             objectModel.storeAttribute(HD);
             
             //auctions
             computerAuction = objectModel.createAuction(computer, 400, new Date());
             objectModel.storeAuction(computerAuction);
             tvAuction = objectModel.createAuction(tv, 350, new Date());
             objectModel.storeAuction(tvAuction);
             
             //bids
             bid1 = objectModel.createBid(computerAuction, batman, 400);
             objectModel.storeBid(bid1);
             bid2 = objectModel.createBid(computerAuction, superman, 450);
             objectModel.storeBid(bid2);
             bid3 = objectModel.createBid(tvAuction, superman, 350);
             objectModel.storeBid(bid3);
             bid4 = objectModel.createBid(tvAuction, batman, 500);
             objectModel.storeBid(bid4);
             
             //reports
             report1 = objectModel.createExperienceReport(batman, superman, 5, "5/5 would buy again", new Date());
             report2 = objectModel.createExperienceReport(superman, batman, 1, "Never sold me the item", new Date());
             
             
             System.out.println( "Entity objects created and saved in the persistence module" );
             
         }
         catch( DTException de) {
             System.err.println( "Exception: " + de );
             de.printStackTrace();
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
