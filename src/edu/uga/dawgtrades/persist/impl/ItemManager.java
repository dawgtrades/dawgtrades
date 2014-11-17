package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import com.mysql.jdbc.Driver;
import com.mysql.jdbc.PreparedStatement;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;

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
        String               insertItemSql = "insert into item ( name, description, category_id, owner_id) values ( ?, ?, ?, ?)";
        String               updateItemSql; //TODO Later
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
            
            if( item.getCategoryId() > 0 ) {
            	stmt.setLong( 3,  item.getCategoryId() );
            }
            else {
            	throw new DTException( "ItemManager.save: can't save a Item: category undefined" );
            }
            if ( item.getOwnerId() > 0) {
            stmt.setLong( 4, item.getOwnerId() );
            }
            else {
            	throw new DTException( "ItemManager.save: can't save a Item: owner undefined" );
            }

            if( item.isPersistent() )
                stmt.setLong( 8, item.getId() );

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
            String       selectItemSql = "select name, description, category_id, owner_id";
            Statement    stmt = null;
            StringBuffer query = new StringBuffer( 100 );
            StringBuffer condition = new StringBuffer( 100 );

            condition.setLength( 0 );

            // form the query based on the given Item object instance
            query.append( selectItemSql );

            if( modelItem != null ) {
                if( modelItem.getId() >= 0 ) // id is unique, so it is sufficient to get a Item
                    query.append( " where id = " + modelItem.getId() );
                else if( modelItem.getName() != null ) // userName is unique, so it is sufficient to get a person
                    query.append( " where name = '" + modelItem.getName() + "'" );
                else {
                    if( modelItem.getDescription() != null )
                        condition.append( " description = '" + modelItem.getDescription() + "'" );

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
        
        /**TODO Later I do NOT understand what this method does
        public Iterator<Club> restoreEstablishedBy( Item person )
                throws DTException
        {
            String       selectItemSql = "select c.id, c.name, c.address, c.established, c.founderid from person p, club c where c.founderid = p.id";
            Statement    stmt = null;
            StringBuffer query = new StringBuffer( 100 );
            StringBuffer condition = new StringBuffer( 100 );

            condition.setLength( 0 );

            // form the query based on the given Item object instance
            query.append( selectPersonSql );

            if( person != null ) {
                if( person.getId() >= 0 ) // id is unique, so it is sufficient to get a person
                    query.append( " and p.id = " + person.getId() );
                else if( person.getUserName() != null ) // userName is unique, so it is sufficient to get a person
                    query.append( " and p.username = '" + person.getUserName() + "'" );
                else {
                    if( person.getPassword() != null )
                        condition.append( " p.password = '" + person.getPassword() + "'" );

                    if( person.getEmail() != null && condition.length() == 0 )
                        condition.append( " p.email = '" + person.getEmail() + "'" );
                    else
                        condition.append( " AND p.email = '" + person.getEmail() + "'" );

                    if( person.getFirstName() != null && condition.length() == 0 )
                        condition.append( " p.firstname = '" + person.getFirstName() + "'" );
                    else
                        condition.append( " AND p.firstname = '" + person.getFirstName() + "'" );

                    if( person.getLastName() != null && condition.length() == 0 )
                        condition.append( " p.lastname = '" + person.getLastName() + "'" );
                    else
                        condition.append( " AND p.lastname = '" + person.getLastName() + "'" );

                    if( person.getAddress() != null && condition.length() == 0 )
                        condition.append( " p.address = '" + person.getAddress() + "'" );
                    else
                        condition.append( " AND p.address = '" + person.getAddress() + "'" );

                    if( person.getPhone() != null && condition.length() == 0 )
                        condition.append( " p.phone = '" + person.getPhone() + "'" );
                    else
                        condition.append( " AND p.phone = '" + person.getPhone() + "'" );

                    if( condition.length() > 0 ) {
                        query.append( condition );
                    }
                }
            }

            try {

                stmt = conn.createStatement();

                // retrieve the persistent Person object
                //
                if( stmt.execute( query.toString() ) ) { // statement returned a result
                    ResultSet r = stmt.getResultSet();
                    return new ClubIterator( r, objectModel );
                }
            }
            catch( Exception e ) {      // just in case...
                throw new DTException( "PersonManager.restoreEstablishedBy: Could not restore persistent Club objects; Root cause: " + e );
            }

            throw new DTException( "PersonManager.restoreEstablishedBy: Could not restore persistent Club objects" );
        }
		**/
        
        public void delete( Item item )
                throws DTException
        {
            String               deleteItemSql = "delete from item where id = ?";
            PreparedStatement    stmt = null;
            int                  numUpdated;

            // form the query based on the given Person object instance
            if( !item.isPersistent() ) // is the Person object persistent?  If not, nothing to actually delete
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
