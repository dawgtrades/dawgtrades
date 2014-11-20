package edu.uga.dawgtrades.test;

import java.sql.Connection;
import java.util.Date;
import java.util.Iterator;

import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persist.Persistence;
import edu.uga.dawgtrades.persist.impl.DbUtils;
import edu.uga.dawgtrades.persist.impl.PersistenceImpl;
 

/**
 * Test class to read out items from the database
 * @author Justin
 *
 */

public class ObjectModelRead
{
    public static void main(String[] args) throws DTException
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
             
	     // Retrieve all existing Item objects and list their founders and members
             System.out.println( "Item objects:" );
             Iterator<Item> itemIter = objectModel.findItem( null );
             while( itemIter.hasNext() ) {
                 Item i = itemIter.next();
                 System.out.println( i );
                 Iterator<Attribute> attributeIter = objectModel.getAttribute(i);
                 System.out.println( "   Attributes: ");
                 while( attributeIter != null && attributeIter.hasNext() ) {
                     Attribute a = attributeIter.next();
                     System.out.println( "       " + a );
                 }
                 Auction a = objectModel.getAuction(i);
                 System.out.println( "   Auction: " + a);
                 Category c = objectModel.getCategory(i);
                 System.out.println( "   Category: " + c);
                 RegisteredUser u = objectModel.getRegisteredUser(i);
                 System.out.println("   Owner: " + u);
             }
        

         }
         catch( DTException de)
         {
             System.err.println( "DTException: " + de );
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
