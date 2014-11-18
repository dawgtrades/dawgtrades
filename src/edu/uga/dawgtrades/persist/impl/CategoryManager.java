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
 * Category Manager
 * @author Vic Lawson
 *
 */

public class CategoryManager {
	private ObjectModel objectModel = null;
    private Connection  conn = null;

    public CategoryManager( Connection conn, ObjectModel objectModel )
    {
        this.conn = conn;
        this.objectModel = objectModel;
    }

    public void save( Category category )
            throws DTException
    {
        String               insertCategorySql = "insert into category ( category_name, parent_id) values ( ?, ?)";
        String               updateCategorySql = "update category set category_name = ?, parent_id = ? where category_id = ?";
        PreparedStatement	 stmt = null;
        int                  numUpdated;
        long                 itemId;
       
        try {

            if( !category.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertCategorySql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateCategorySql );

            if( category.getName() != null )
                stmt.setString( 1, category.getName() );
            else
                throw new DTException( "CategoryManager.save: can't save a Category: name undefined" );

            if( category.getParentId() != null )
                stmt.setString( 2, category.getParentId() );
            else
                throw new DTException( "CategoryManager.save: can't save a Category: parentId undefined" );
            
            numUpdated = stmt.executeUpdate();

            if( !category.isPersistent() ) {
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
                        throw new DTException( "CategoryManager.save: failed to save a Category" );
                }
            }
            catch( SQLException e ) {
                e.printStackTrace();
                throw new DTException( "CategoryManager.save: failed to save a Category: " + e );
            }
        }

        public Iterator<Category> restore( Category modelCategory )
                throws DTException
        {
            String       selectCategorySql = "select  category_name, parent_id from category";
            Statement    stmt = null;
            StringBuffer query = new StringBuffer( 100 );
            StringBuffer condition = new StringBuffer( 100 );

            condition.setLength( 0 );

            // form the query based on the given Category object instance
            query.append( selectCategorySql );

            if( modelCategory != null ) {
                if( modelCategory.getId() >= 0 ) // id is unique, so it is sufficient to get a Category
                    query.append( " where category_id = " + modelCategory.getId() );
                else {
                	if( modelCategory.getName() != null )
                		condition.append( " category_name = '" + modelCategory.getName() + "'" );
						
                    if( modelCategory.getParentId() != null )
                    	if( condition.length() > 0 )
                            condition.append( " and" );
                        condition.append( " parent_id = '" + modelCategory.getParentId() + "'" );

                    if( condition.length() > 0 ) {
                        query.append(  " where " );
                        query.append( condition );
                    }
                }
            }

            try {

                stmt = conn.createStatement();

                // retrieve the persistent Category object
                //
                if( stmt.execute( query.toString() ) ) { // statement returned a result
                    ResultSet r = stmt.getResultSet();
                    return new CategoryIterator( r, objectModel );
                }
            }
            catch( Exception e ) {      // just in case...
                throw new DTException( "CategoryManager.restore: Could not restore persistent Category object; Root cause: " + e );
            }

            // if we get to this point, it's an error
            throw new DTException( "CategoryManager.restore: Could not restore persistent Category object" );
        }
        
        public void delete( Category category )
                throws DTException
        {
            String               deleteCategorySql = "delete from category where category_id = ?";
            PreparedStatement    stmt = null;
            int                  numUpdated;

            // form the query based on the given Category object instance
            if( !category.isPersistent() ) // is the Category object persistent?  If not, nothing to actually delete
                return;

            try {

                //DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
                //DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
                stmt = (PreparedStatement) conn.prepareStatement( deleteCategorySql );

                stmt.setLong( 1, category.getId() );

                numUpdated = stmt.executeUpdate();

                if( numUpdated == 0 ) {
                    throw new DTException( "CategoryManager.delete: failed to delete this Category" );
                }
            }
            catch( SQLException e ) {
                throw new DTException( "CategoryManager.delete: failed to delete this Category: " + e.getMessage() );
            }
        }		
		
        public Iterator<Item> restoreItemsInCategory(Category category)  throws DTException {
        	String       selectCategorySql = "select i.item_id, i.name, i.category_id, i.identifier, i.description, i.owner_id from category c, item i where c.category_id = i.category_id";
            Statement    stmt = null;
            StringBuffer query = new StringBuffer( 100 );
            StringBuffer condition = new StringBuffer( 100 );

            condition.setLength( 0 );

            // form the query based on the given Category object instance
            query.append( selectCategorySql );

            if( category != null ) {
                if( category.getId() >= 0 ) // id is unique, so it is sufficient to get a item
                    query.append( " and c.category_id = " + category.getId() );
                else {

                    if( category.getName() != null && condition.length() == 0 )
                        condition.append( " c.category_name = '" + category.getName() + "'" );
                    else
                        condition.append( " AND c.category_name = '" + category.getName() + "'" );

                    if( category.getParentId() >= 0 && condition.length() == 0 )
                        condition.append( " c.parent_id = '" + category.getParentId() + "'" );
                    else
                        condition.append( " AND c.parent_id = '" + category.getParentId() + "'" );
						
                    if( condition.length() > 0 ) {
                        query.append( condition );
                    }
                }
            }

            try {

                stmt = conn.createStatement();

                // retrieve the persistent Category object
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
                throw new DTException( "CategoryManager.restoreOwner: Could not restore persistent User objects; Root cause: " + e );
            }
            throw new DTException( "CategoryManager.restoreOwner: Could not restore persistent User objects" );
    	}

        public Iterator<AttributeType> restoreAttributeTypes(Category category) throws DTException  {
        	String       selectAttributeSql = "select ... from .... where ....";
            Statement    stmt = null;
            StringBuffer query = new StringBuffer( 100 );
            StringBuffer condition = new StringBuffer( 100 );

        }
		
	    public Iterator<Category> restoreChildren(Category category) throws DTException  {
		    String       selectChildrenSql = "select ... from .... where ....";
		    Statement    stmt = null;
            StringBuffer query = new StringBuffer( 100 );
            StringBuffer condition = new StringBuffer( 100 );
		}
		
	    public Category restoreParent(Category category) throws DTException {
		    String       selectCategorySql = "select ... from .... where ....";
            Statement    stmt = null;
            StringBuffer query = new StringBuffer( 100 );
            StringBuffer condition = new StringBuffer( 100 );

		
		}

		
}
