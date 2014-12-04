package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import com.mysql.jdbc.Driver;
import com.mysql.jdbc.PreparedStatement;

import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.AttributeType;
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
        String               updateCategorySql = "update category set category_name = ?, parent_id = ? where category_name = ?";
        PreparedStatement	 stmt = null;
        int                  numUpdated;
        long                 categoryId;
       
        try {

		System.out.println("category name==" + category.getName());
		
            //if( !category.isPersistent() )
			if (category.getName() == null)
                stmt = (PreparedStatement) conn.prepareStatement( insertCategorySql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateCategorySql );

            if( category.getName() != null )  {
                stmt.setString( 1, category.getName() );
                stmt.setString( 3, category.getName() );				
			}
            else
                throw new DTException( "CategoryManager.save: can't save a Category: name undefined" );

	    //            if( category.getParentId() >=0 )
            stmt.setLong( 2, category.getParentId() );
		//            else
		//                throw new DTException( "CategoryManager.save: can't save a Category: parentId undefined" );
		//  if(category.isPersistent())
		//    stmt.setLong(3, category.getId());
		
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
                            categoryId = r.getLong( 1 );
                            if( categoryId > 0 )
                                category.setId( categoryId ); // set this item's db id (proxy object)
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

            String       selectCategorySql = "select category_id, category_name, parent_id from category";

            Statement    stmt = null;
            StringBuffer query = new StringBuffer( 100 );
            StringBuffer condition = new StringBuffer( 100 );

            condition.setLength( 0 );

            // form the query based on the given Category object instance
            query.append( selectCategorySql );

//			System.out.println( "CAT QUERY ==" + query.toString() );
			
            if( modelCategory != null ) {
		
                if( modelCategory.getId() >= 0 ) // id is unique, so it is sufficient to get a Category
                    query.append( " where category_id = " + modelCategory.getId() );
                else {
				
		    if( modelCategory.getName() != null )
                		condition.append( " where category_name = '" + modelCategory.getName() + "'" );
						
		    if( modelCategory.getParentId() >= 0 ) {
                    	if( condition.length() > 0 )
                            condition.append( " and" );
                        condition.append( " parent_id = '" + modelCategory.getParentId() + "'" );
		    }
		    if( condition.length() > 0 ) {
                        query.append(  " where " );
                        query.append( condition );
		    }    
				
                }
            }

			//System.out.println( "CAT QUERY ==" + query.toString() );
			
            try {

                stmt = conn.createStatement();

                // retrieve the persistent Category object
                //
                if( stmt.execute( query.toString() ) ) { // statement returned a result
                    ResultSet r = stmt.getResultSet();
					
					System.out.println( "results set ==" + r );
					
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

	//System.out.println("DELETE isPersistent == " + category.isPersistent());
			
            // form the query based on the given Category object instance
            if( !category.isPersistent() ) { 
			    //System.out.println("nothing to DELETE" );

			    // If category not persistent, nothing to actually delete
                return;
            }
			
            try {

                //DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
                //DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
                stmt = (PreparedStatement) conn.prepareStatement( deleteCategorySql );

                stmt.setLong( 1, category.getId() );

				System.out.println("stmt == " + stmt.toString());
				
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
                    return new ItemIterator( r, objectModel);
                    
                    
                }
            }
            catch( Exception e ) {      // just in case...
                throw new DTException( "CategoryManager.restoreItemsInCategory: Could not restore persistent category objects; Root cause: " + e );
            }
            throw new DTException( "CategoryManager.restoreItemsInCategory: Could not restore persistent category objects" );
    	}

		
        public Iterator<AttributeType> restoreAttributeTypes(Category category) throws DTException  {
        	String       selectAttributeSql = "select a.attribute_id, a.attribute_value, a.attribute_type_id, a.item_id "
        			+ "from category c, attribute_type a "
        			+ "where a.category_id = c.category_id";

            Statement    stmt = null;
            StringBuffer query = new StringBuffer( 100 );
            StringBuffer condition = new StringBuffer( 100 );
            condition.setLength( 0 );
            // form the query based on the given Item object instance
            query.append( selectAttributeSql );

            if( category != null ) {
                if( category.getId() >= 0 ) // id is unique, so it is sufficient to get a category
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

                // retrieve the persistent Item object
                //
                if( stmt.execute( query.toString() ) ) { // statement returned a result
                    ResultSet r = stmt.getResultSet();
                    return new AttributeTypeIterator(r, objectModel);
                }
                
            }
            catch( Exception e ) {      // just in case...
                throw new DTException( "CategoryManager.restoreAttributeTypes: Could not restore persistent restoreAttributeTypes objects; Root cause: " + e );
            }

            throw new DTException( "CategoryManager.restoreAttributeTypes: Could not restore persistent Attribute objects" );					
	    }
	
	
	    public Iterator<Category> restoreChildren(Category category) throws DTException  {
		    String       selectChildrenSql = "select c.category_id, c.category_name, c.parent_id from category c, category c2 where c.parent_id = c2.category_id";
            Statement    stmt = null;
            StringBuffer query = new StringBuffer( 100 );
            StringBuffer condition = new StringBuffer( 100 );

            condition.setLength( 0 );

            // form the query based on the given Item object instance
            query.append( selectChildrenSql );

            if( category != null ) {
                if( category.getId() >= 0 ) // id is unique, so it is sufficient to get a item
                    query.append( " and c.category_id = " + category.getId() );
                else {
                    if( category.getParentId() >= 0 )
                        condition.append( " c.parent_id = '" + category.getParentId() + "'" );

                    if( category.getName() != null && condition.length() == 0 )
                        condition.append( " c.category_name = '" + category.getName() + "'" );
                    else
                        condition.append( " AND c.category_name = '" + category.getName() + "'" );

                    if( condition.length() > 0 ) {
                        query.append( condition );
                    }
                }
            }

            try {

                stmt = conn.createStatement();

                // retrieve the persistent category object
                //
                if( stmt.execute( query.toString() ) ) { // statement returned a result
                    ResultSet r = stmt.getResultSet();
                    return new CategoryIterator(r, objectModel);
                }
                
            }
            catch( Exception e ) {      // just in case...
                throw new DTException( "categoryManager.restoreAttributes: Could not restore persistent Attribute objects; Root cause: " + e );
            }

            throw new DTException( "categoryManager.restoreAttributes: Could not restore persistent Attribute objects" );
    	}			
			
		
	    public Category restoreParent(Category category) throws DTException  {
		    String       selectParentSql = "select c.category_id, c.category_name, c.parent_id from category c, category c2 where c.category_id = c2.parent_id";
            Statement    stmt = null;
            StringBuffer query = new StringBuffer( 100 );
            StringBuffer condition = new StringBuffer( 100 );

            condition.setLength( 0 );

            // form the query based on the given Item object instance
            query.append( selectParentSql );

            if( category != null ) {
                if( category.getId() >= 0 ) // id is unique, so it is sufficient to get a item
                    query.append( " and c.category_id = " + category.getId() );
                else {
                    if( category.getParentId() >= 0 )
                        condition.append( " c.parent_id = '" + category.getParentId() + "'" );

                    if( category.getName() != null && condition.length() == 0 )
                        condition.append( " c.category_name = '" + category.getName() + "'" );
                    else
                        condition.append( " AND c.category_name = '" + category.getName() + "'" );

                    if( condition.length() > 0 ) {
                        query.append( condition );
                    }
                }
            }

            try {

                stmt = conn.createStatement();

                // retrieve the persistent category object
                //
                if( stmt.execute( query.toString() ) ) { // statement returned a result
                	ResultSet r = stmt.getResultSet();
                	Iterator<Category> catIter = new CategoryIterator( r, objectModel );
                    if( catIter != null && catIter.hasNext() ) {
                        return catIter.next();
                    }
                    else
                        return null;
                }
                
            }
            catch( Exception e ) {      // just in case...
                throw new DTException( "categoryManager.restoreParent: Could not restore persistent Attribute objects; Root cause: " + e );
            }

            throw new DTException( "categoryManager.restoreParent: Could not restore persistent Attribute objects" );
    	}			
		
}
