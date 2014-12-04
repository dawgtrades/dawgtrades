package edu.uga.dawgtrades.test;

import java.sql.Connection;
import java.util.Date;
import java.util.Iterator;

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


/** Testing the deletion of the entity classes and associations for DawgTrades
 * 
 * @author Vic
 *
 */
public class ObjectModelDelete
{
    public static void main(String[] args) throws DTException
    {
         Connection conn = null;
         ObjectModel objectModel = null;
         Persistence persistence = null;
         
         //RegisteredUser batman;
         RegisteredUser superman;
         Membership mship;
         //Item computer;
         //Item tv;
         AttributeType screenSize = null;
         AttributeType resolution = null;
         Attribute small = null;
         Attribute large = null;
         Attribute SD = null;
         Attribute HD = null;
         Auction computerAuction;
         Auction tvAuction;
         Category electronics = null;
         //Category computers = null;
         Category televisions = null;
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

         try {         
            // obtain a reference to the ObjectModel module      
            objectModel = new ObjectModelImpl();
            // obtain a reference to Persistence module and connect it to the ObjectModel        
            persistence = new PersistenceImpl( conn, objectModel ); 
            // connect the ObjectModel module to the Persistence module
            objectModel.setPersistence( persistence);  
		 }
         catch (Exception seq) {
             System.err.println( "ObjectModelDelete: Unable to obtain persistence module" );
         }

         try { 
/*		 
              //users 
             RegisteredUser batman = null;
             RegisteredUser modelRegUser = objectModel.createRegisteredUser();
	         modelRegUser.setFirstName( "Bruce" );
             modelRegUser.setLastName( "Wayne" );
             Iterator<RegisteredUser> regUserIter = objectModel.findRegisteredUser( modelRegUser );
             while( regUserIter.hasNext() ) {
                 batman = regUserIter.next();
                 System.out.println( batman );
             }
             if( batman != null )
                 objectModel.deleteRegisteredUser(batman);
             else
                 System.out.println( "Failed to retrieve the batman object" );
			
             //membership
	         // mship = objectModel.createMembership(15, new Date());
		     
			 //categories
	         // First: locate the Computers cat
             Category computers = null;
             Category modelCat = objectModel.createCategory();
             modelCat.setName( "Computers" );
             Iterator<Category> catIter = objectModel.findCategory( modelCat );
             while( catIter.hasNext() ) {
		         computers = catIter.next();
				 } 
             if( computers != null )
                 objectModel.deleteCategory(computers);
             else
                 System.out.println( "Failed to retrieve the computer Catelog object" );
        
             //computer = objectModel.createItem(computers, batman, "PC5407", "Apple Macbook", "Used for two years");
             //tv = objectModel.createItem(televisions, superman, "TV649", "Samsung TV", "Brand new in box");
			 		 	
			 //items
			 Item computer = null;
             Item modelItem = objectModel.createItem();
             modelItem.setName( "Apple Macbook");
			 modelItem.setId(12);
             Iterator<Item> itemIter = objectModel.findItem( modelItem );
             while( itemIter.hasNext() ) {
		         computer = itemIter.next();
				 } 
             if( computer != null ) {
			   try {
                 objectModel.deleteItem(computer);
				}
			   catch( DTException de) {
                System.err.println( "Delete ITEM Exception: " + de );
                de.printStackTrace();
                }
              }
			 else
                System.out.println( "Failed to retrieve the computer ITEM object" );
*/			 
				 
			try {
             //attribute types
             screenSize = objectModel.createAttributeType(computers, "Screen Size");
             screenSize = objectModel.createAttributeType(televisions, "Screen Size");
             resolution = objectModel.createAttributeType(computers, "Resolution");
             resolution = objectModel.createAttributeType(televisions, "Resolution");
        	}
             catch( DTException de) {
                System.err.println( "Create Attribute type Exception: " + de );
                de.printStackTrace();
            }

/*			
			try {
             //attributes
             small = objectModel.createAttribute(screenSize, computer, "small");
             large = objectModel.createAttribute(screenSize, tv, "large");     
             SD = objectModel.createAttribute(resolution, tv, "SD");
             HD = objectModel.createAttribute(resolution, computer, "HD");
			}
             catch( DTException de) {
                System.err.println( "Create Attributes Exception: " + de );
                de.printStackTrace();
            }
			
             //auctions
             computerAuction = objectModel.createAuction(computer, 400, new Date());
             tvAuction = objectModel.createAuction(tv, 350, new Date());

             //bids
             bid1 = objectModel.createBid(computerAuction, batman, 400);
             bid2 = objectModel.createBid(computerAuction, superman, 450);
             bid3 = objectModel.createBid(tvAuction, superman, 350);
             bid4 = objectModel.createBid(tvAuction, batman, 500);
			 
            //reports
             report1 = objectModel.createExperienceReport(batman, superman, 5, "5/5 would buy again", new Date());
             report2 = objectModel.createExperienceReport(superman, batman, 1, "Never sold me the item", new Date());

			 
			 
			 //  GO AWAY.......
	 	  try {	 	  
			 objectModel.deleteExperienceReport(report1);
			 objectModel.deleteExperienceReport(report2); 
			 objectModel.deleteBid(bid1);
			 objectModel.deleteBid(bid2);
			 objectModel.deleteBid(bid3);
             objectModel.deleteBid(bid4);			 
             objectModel.deleteAuction(computerAuction);
             objectModel.deleteAuction(tvAuction);
			 
             objectModel.deleteAttribute(small);			 
			 objectModel.deleteAttribute(large);
             objectModel.deleteAttribute(SD);
             objectModel.deleteAttribute(HD);			 
             objectModel.deleteAttributeType(screenSize);			 
             objectModel.deleteAttributeType(screenSize);			 
             objectModel.deleteAttributeType(resolution);			 
             objectModel.deleteAttributeType(resolution);			 
             objectModel.deleteItem(computer);			 
             objectModel.deleteItem(tv);
	         objectModel.deleteCategory(electronics);             objectModel.deleteCategory(computers);        objectModel.deleteCategory(televisions);			 
			 
             
			 // objectModel.deleteMembership(mship);

			System.out.println( "Before Delete" );
			 
             objectModel.deleteRegisteredUser(batman);			 
             //objectModel.deleteRegisteredUser(superman);
			 
             System.out.println( "Entity objects deleted and saved in the persistence module" );
             
            }
             catch( DTException de) {
                System.err.println( "DELETE Exception: " + de );
                de.printStackTrace();
             }
		*/	 
		 }
         catch( DTException de) {
             System.err.println( "ObjectModelDelete Exception: " + de );
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
