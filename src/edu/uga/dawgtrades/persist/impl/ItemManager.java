package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import com.mysql.jdbc.Driver;
import com.mysql.jdbc.PreparedStatement;

import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;

/**
 * UNTESTED
 * Item Manager
 * @author Justin
 *
 */

public class ItemManager {
	private ObjectModel objectModel = null;
    private Connection  conn = null;

    public ItemManager( Connection conn, ObjectModel objectModel )
    {
        this.conn = conn;
        this.objectModel = objectModel;
    }

    public void save( Item item )
            throws DTException
    {
        String               insertItemSql = "insert into item ( name, description, identifier, category_id, owner_id) values ( ?, ?, ?, ?, ?)";
        String               updateItemSql = "update item set name = ?, description = ?, identifier = ?, category_id = ?, owner_id = ? where item_id = ?";
        PreparedStatement	 stmt = null;
        int                  numUpdated;
        long                 itemId;
       
        try {

            if( !item.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertItemSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateItemSql );

            if( item.getName() != null )
                stmt.setString( 1, item.getName() );
            else
                throw new DTException( "ItemManager.save: can't save a Item: name undefined" );

            if( item.getDescription() != null )
                stmt.setString( 2, item.getDescription() );
            else
                throw new DTException( "ItemManager.save: can't save a Item: description undefined" );
            
            if(item.getIdentifier() != null )
            	stmt.setString(3, item.getIdentifier());
            else
            	throw new DTException("ItemManager.save: can't save a Item: identifier undefined");
            
            if( item.getCategoryId() > 0 ) {
            	stmt.setLong( 4,  item.getCategoryId() );
            }
            else {
            	throw new DTException( "ItemManager.save: can't save a Item: category undefined" );
            }
            if ( item.getOwnerId() > 0) {
            stmt.setLong( 5, item.getOwnerId() );
            }
            else {
            	throw new DTException( "ItemManager.save: can't save a Item: owner undefined" );
            }

            if( item.isPersistent() )
                stmt.setLong( 6, item.getId() );

            numUpdated = stmt.executeUpdate();

            if( !item.isPersistent() ) {
                // in case this this object is stored for the first time,
                // we need to establish its persistent identifier (primary key)
                if( numUpdated >= 1 ) {
                    String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) { 
                        // retrieve the result
                        ResultSet r = stmt.getResultSet();
                        // we will use only the first row!
                        while( r.next() ) {
                            // retrieve the last insert auto_increment value
                            itemId = r.getLong( 1 );
                            if( itemId > 0 )
                                item.setId( itemId ); // set this item's db id (proxy object)
                        }
                    }
                    }
                }
                else {
                    if( numUpdated < 1 )
                        throw new DTException( "ItemManager.save: failed to save a Item" );
                }
            }
            catch( SQLException e ) {
                e.printStackTrace();
                throw new DTException( "ItemManager.save: failed to save a Item: " + e );
            } 
        }

        public Iterator<Item> restore( Item modelItem )
                throws DTException
        {
            String       selectItemSql = "select name, description, identifier, category_id, owner_id from item";
            Statement    stmt = null;
            StringBuffer query = new StringBuffer( 100 );
            StringBuffer condition = new StringBuffer( 100 );

            condition.setLength( 0 );

            // form the query based on the given Item object instance
            query.append( selectItemSql );

            if( modelItem != null ) {
                if( modelItem.getId() >= 0 ) // id is unique, so it is sufficient to get a Item
                    query.append( " where id = " + modelItem.getId() );
                else {
                	if( modelItem.getName() != null )
                		condition.append( " name = '" + modelItem.getName() + "'" );
                    if( modelItem.getDescription() != null )
                    	if( condition.length() > 0 )
                            condition.append( " and" );
                        condition.append( " description = '" + modelItem.getDescription() + "'" );
                    if( modelItem.getIdentifier() != null ) {
                    	if( condition.length() > 0 )
                            condition.append( " and" );
                        condition.append( " identifier = '" + modelItem.getDescription() + "'" );
                    }
                    if( modelItem.getCategoryId() > 0 ) {
                        if( condition.length() > 0 )
                            condition.append( " and" );
                        condition.append( " categoryId = '" + modelItem.getCategoryId() + "'" );
                    }

                    if( modelItem.getOwnerId() > 0 ) {
                        if( condition.length() > 0 )
                            condition.append( " and" );
                        condition.append( " ownerId = '" + modelItem.getOwnerId() + "'" );
                    }

                    if( condition.length() > 0 ) {
                        query.append(  " where " );
                        query.append( condition );
                    }
                }
            }

            try {

                stmt = conn.createStatement();

                // retrieve the persistent Item object
                //
                if( stmt.execute( query.toString() ) ) { // statement returned a result
                    ResultSet r = stmt.getResultSet();
                    return new ItemIterator( r, objectModel );
                }
            }
            catch( Exception e ) {      // just in case...
                throw new DTException( "ItemManager.restore: Could not restore persistent Item object; Root cause: " + e );
            }

            // if we get to this point, it's an error
            throw new DTException( "ItemManager.restore: Could not restore persistent Item object" );
        }
        
        public RegisteredUser restoreOwner(Item item) throws DTException {
        	String       selectItemSql = "select u.user_id, u.last_name, u.first_name, u.phone, u.email, u.uname, u.upassword, u,is_admin, u.can_text from item i, registered_user u where i.owner_id = u.user_id";
            Statement    stmt = null;
            StringBuffer query = new StringBuffer( 100 );
            StringBuffer condition = new StringBuffer( 100 );

            condition.setLength( 0 );

            // form the query based on the given Item object instance
            query.append( selectItemSql );

            if( item != null ) {
                if( item.getId() >= 0 ) // id is unique, so it is sufficient to get a item
                    query.append( " and i.item_id = " + item.getId() );
                else {
                    if( item.getOwnerId() >= 0 )
                        condition.append( " i.owner_id = '" + item.getOwnerId() + "'" );

                    if( item.getCategoryId() >= 0 && condition.length() == 0 )
                        condition.append( " i.category_id = '" + item.getCategoryId() + "'" );
                    else
                        condition.append( " AND i.category_id = '" + item.getCategoryId() + "'" );

                    if( item.getIdentifier() != null && condition.length() == 0 )
                        condition.append( " i.identifier = '" + item.getIdentifier() + "'" );
                    else
                        condition.append( " AND i.identifier = '" + item.getIdentifier() + "'" );

                    if( item.getName() != null && condition.length() == 0 )
                        condition.append( " i.name = '" + item.getName() + "'" );
                    else
                        condition.append( " AND i.name = '" + item.getName() + "'" );

                    if( item.getDescription() != null && condition.length() == 0 )
                        condition.append( " i.description = '" + item.getDescription() + "'" );
                    else
                        condition.append( " AND i.description= '" + item.getDescription() + "'" );

                    if( condition.length() > 0 ) {
                        query.append( condition );
                    }
                }
            }

            try {

                stmt = conn.createStatement();

                // retrieve the persistent Item object
                //
                if( stmt.execute( query.toString() ) ) { // statement returned a result
                    ResultSet r = stmt.getResultSet();
                    Iterator<RegisteredUser> userIter = new RegisteredUserIterator( r, objectModel );
                    if( userIter != null && userIter.hasNext() ) {
                        return userIter.next();
                    }
                    else
                        return null;
                }
            }
            catch( Exception e ) {      // just in case...
                throw new DTException( "ItemManager.restoreOwner: Could not restore persistent User objects; Root cause: " + e );
            }

            throw new DTException( "ItemManager.restoreOwner: Could not restore persistent User objects" );
    	}
        
    	public Category restoreCategoryOfItem(Item item) throws DTException {
    		String       selectItemSql = "select c.category_id, c.category_name, c.parent_id from item i, category c where i.category_id = c.category_id";
            Statement    stmt = null;
            StringBuffer query = new StringBuffer( 100 );
            StringBuffer condition = new StringBuffer( 100 );

            condition.setLength( 0 );

            // form the query based on the given Item object instance
            query.append( selectItemSql );

            if( item != null ) {
                if( item.getId() >= 0 ) // id is unique, so it is sufficient to get a item
                    query.append( " and i.item_id = " + item.getId() );
                else {
                    if( item.getOwnerId() >= 0 )
                        condition.append( " i.owner_id = '" + item.getOwnerId() + "'" );

                    if( item.getCategoryId() >= 0 && condition.length() == 0 )
                        condition.append( " i.category_id = '" + item.getCategoryId() + "'" );
                    else
                        condition.append( " AND i.category_id = '" + item.getCategoryId() + "'" );

                    if( item.getIdentifier() != null && condition.length() == 0 )
                        condition.append( " i.identifier = '" + item.getIdentifier() + "'" );
                    else
                        condition.append( " AND i.identifier = '" + item.getIdentifier() + "'" );

                    if( item.getName() != null && condition.length() == 0 )
                        condition.append( " i.name = '" + item.getName() + "'" );
                    else
                        condition.append( " AND i.name = '" + item.getName() + "'" );

                    if( item.getDescription() != null && condition.length() == 0 )
                        condition.append( " i.description = '" + item.getDescription() + "'" );
                    else
                        condition.append( " AND i.description= '" + item.getDescription() + "'" );

                    if( condition.length() > 0 ) {
                        query.append( condition );
                    }
                }
            }

            try {

                stmt = conn.createStatement();

                // retrieve the persistent Item object
                //
                if( stmt.execute( query.toString() ) ) { // statement returned a result
                    ResultSet r = stmt.getResultSet();
                    Iterator<Category> categoryIter = new CategoryIterator( r, objectModel );
                    if( categoryIter != null && categoryIter.hasNext() ) {
                        return categoryIter.next();
                    }
                    else
                        return null;
                }
            }
            catch( Exception e ) {      // just in case...
                throw new DTException( "ItemManager.restoreCategoryOfItem: Could not restore persistent Category objects; Root cause: " + e );
            }

            throw new DTException( "ItemManager.restoreCategoryOfItem: Could not restore persistent Category objects" );
    	}
    	
    	
    	public Auction restoreAuctionForItem(Item item) throws DTException {
    		String       selectItemSql = "select a.auction_id, a.item_id, a.status, a.high_bid, a.expiration_dt, a.min_price from item i, auction a where i.item_id = a.item_id";
            Statement    stmt = null;
            StringBuffer query = new StringBuffer( 100 );
            StringBuffer condition = new StringBuffer( 100 );

            condition.setLength( 0 );

            // form the query based on the given Item object instance
            query.append( selectItemSql );

            if( item != null ) {
                if( item.getId() >= 0 ) // id is unique, so it is sufficient to get a item
                    query.append( " and i.item_id = " + item.getId() );
                else {
                    if( item.getOwnerId() >= 0 )
                        condition.append( " i.owner_id = '" + item.getOwnerId() + "'" );

                    if( item.getCategoryId() >= 0 && condition.length() == 0 )
                        condition.append( " i.category_id = '" + item.getCategoryId() + "'" );
                    else
                        condition.append( " AND i.category_id = '" + item.getCategoryId() + "'" );

                    if( item.getIdentifier() != null && condition.length() == 0 )
                        condition.append( " i.identifier = '" + item.getIdentifier() + "'" );
                    else
                        condition.append( " AND i.identifier = '" + item.getIdentifier() + "'" );

                    if( item.getName() != null && condition.length() == 0 )
                        condition.append( " i.name = '" + item.getName() + "'" );
                    else
                        condition.append( " AND i.name = '" + item.getName() + "'" );

                    if( item.getDescription() != null && condition.length() == 0 )
                        condition.append( " i.description = '" + item.getDescription() + "'" );
                    else
                        condition.append( " AND i.description= '" + item.getDescription() + "'" );

                    if( condition.length() > 0 ) {
                        query.append( condition );
                    }
                }
            }

            try {

                stmt = conn.createStatement();

                // retrieve the persistent Item object
                //
                if( stmt.execute( query.toString() ) ) { // statement returned a result
                    ResultSet r = stmt.getResultSet();
                    Iterator<Auction> auctionIter = new AuctionIterator( r, objectModel );
                    if( auctionIter != null && auctionIter.hasNext() ) {
                        return auctionIter.next();
                    }
                    else
                        return null;
                }
            }
            catch( Exception e ) {      // just in case...
                throw new DTException( "ItemManager.restoreAuctionForItem: Could not restore persistent Auction objects; Root cause: " + e );
            }

            throw new DTException( "ItemManager.restoreAuctionForItem: Could not restore persistent Auction objects" );
    	}
    	
        public Iterator<Attribute> restoreAttributes( Item item )
                throws DTException
        {
            String       selectItemSql = "select a.attribute_id, a.attribute_value, a.attribute_type_id, a.item_id from item i, attribute a where i.item_id = a.item_id";
            Statement    stmt = null;
            StringBuffer query = new StringBuffer( 100 );
            StringBuffer condition = new StringBuffer( 100 );

            condition.setLength( 0 );

            // form the query based on the given Item object instance
            query.append( selectItemSql );

            if( item != null ) {
                if( item.getId() >= 0 ) // id is unique, so it is sufficient to get a item
                    query.append( " and i.item_id = " + item.getId() );
                else {
                    if( item.getOwnerId() >= 0 )
                        condition.append( " i.owner_id = '" + item.getOwnerId() + "'" );

                    if( item.getCategoryId() >= 0 && condition.length() == 0 )
                        condition.append( " i.category_id = '" + item.getCategoryId() + "'" );
                    else
                        condition.append( " AND i.category_id = '" + item.getCategoryId() + "'" );

                    if( item.getIdentifier() != null && condition.length() == 0 )
                        condition.append( " i.identifier = '" + item.getIdentifier() + "'" );
                    else
                        condition.append( " AND i.identifier = '" + item.getIdentifier() + "'" );

                    if( item.getName() != null && condition.length() == 0 )
                        condition.append( " i.name = '" + item.getName() + "'" );
                    else
                        condition.append( " AND i.name = '" + item.getName() + "'" );

                    if( item.getDescription() != null && condition.length() == 0 )
                        condition.append( " i.description = '" + item.getDescription() + "'" );
                    else
                        condition.append( " AND i.description= '" + item.getDescription() + "'" );

                    if( condition.length() > 0 ) {
                        query.append( condition );
                    }
                }
            }

            try {

                stmt = conn.createStatement();

                // retrieve the persistent Item object
                //
                if( stmt.execute( query.toString() ) ) { // statement returned a result
                    ResultSet r = stmt.getResultSet();
                    return new AttributeIterator(r, objectModel);
                }
                
            }
            catch( Exception e ) {      // just in case...
                throw new DTException( "ItemManager.restoreAttributes: Could not restore persistent Attribute objects; Root cause: " + e );
            }

            throw new DTException( "ItemManager.restoreAttributes: Could not restore persistent Attribute objects" );
        }
        
        public void delete( Item item )
                throws DTException
        {
            String               deleteItemSql = "delete from item where item_id = ?";
            PreparedStatement    stmt = null;
            int                  numUpdated;

            // form the query based on the given Item object instance
            if( !item.isPersistent() ) // is the Item object persistent?  If not, nothing to actually delete
                return;

            try {

                //DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
                //DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
                stmt = (PreparedStatement) conn.prepareStatement( deleteItemSql );

                stmt.setLong( 1, item.getId() );

                numUpdated = stmt.executeUpdate();

                if( numUpdated == 0 ) {
                    throw new DTException( "ItemManager.delete: failed to delete this Item" );
                }
            }
            catch( SQLException e ) {
                throw new DTException( "ItemManager.delete: failed to delete this Item: " + e.getMessage() );
            }
        }                                                                                     
}
