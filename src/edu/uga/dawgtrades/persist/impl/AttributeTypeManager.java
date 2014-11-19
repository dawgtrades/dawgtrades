package edu.uga.dawgtrades.persist.impl;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import com.mysql.jdbc.Driver;
import com.mysql.jdbc.PreparedStatement;

import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;
/**
 * UNTESTED
 * Attribute Type Manager
 * @author Mark Hoefer
 *
 */
public class AttributeTypeManager {
    private ObjectModel objectModel = null;
    private Connection conn = null;
    public AttributeTypeManager( Connection conn, ObjectModel objectModel )
    {
        this.conn = conn;
        this.objectModel = objectModel;
    }
    public void save( AttributeType attributeType )
    throws DTException
    {
        String insertAttributeTypeSql = "insert into attribute_type ( category_id, attribute_type_name ) values ( ?, ? )";
        String updateAttributeTypeSql = "update attribute_type set category_id = ?, attribute_type_name = ? where attribute_type_id = ?";
        PreparedStatement stmt = null;
        int inscnt;
        long attributeTypeId;
        
        try {
            if( !attributeType.isPersistent() ) {
                stmt = (PreparedStatement) conn.prepareStatement( insertAttributeTypeSql );
                
                if( attributeType.getCategoryId() >= 0 ){
                    stmt.setLong( 1, attributeType.getCategoryId() );
                } else {
                    throw new DTException( "AttributeTypeManager.save: can't save a AttributeType: categoryId undefined" );
                }
                if( attributeType.getName() != null ) {
                    stmt.setString( 2, attributeType.getName() );
                } else {
                    throw new DTException( "AttributeTypeManager.save: can't save a AttributeType: name undefined" );
                }
            } 
            
            inscnt = stmt.executeUpdate();
            if( !attributeType.isPersistent() ) {
                // in case this this object is stored for the first time,
                // we need to establish its persistent identifier (primary key)
                if( inscnt == 1 ) {
                    String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) { // statement returned a result
                        // retrieve the result
                        ResultSet r = stmt.getResultSet();
                        // we will use only the first row!
                        while( r.next() ) {
                            // retrieve the last insert auto_increment value
                            attributeTypeId = r.getLong( 1 );
                            if( attributeTypeId > 0 ) {
                                attributeType.setId( attributeTypeId );
                            }
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new DTException( "AttributeTypeManager.save: failed to save a report" );
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new DTException( "AttributeTypeManager.save: failed to save an attributeType: " + e );
        }
    }
    
    public Iterator<AttributeType> restore( AttributeType modelAttributeType ) 
            throws DTException
    {
        String       selectUserSql = "select category_id, attribute_type_name from attribute_type"; 
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );

        condition.setLength( 0 );
        
        // form the query based on the given user object instance
        query.append( selectUserSql );
        
        if( modelAttributeType != null ) {
            if( modelAttributeType.getId() >= 0 ) // id is unique, so it is sufficient to get a user
                query.append( " where attribute_id = " + modelAttributeType.getId() );
            else {
                if( modelAttributeType.getCategoryId() >= 0 ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " category_id = '" + modelAttributeType.getCategoryId() + "'" );
                }
				
                if( modelAttributeType.getName() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " name = '" + modelAttributeType.getName() + "'" );
                }					
					
                if( condition.length() > 0 ) {
                    query.append(  " where " );
                    query.append( condition );
                }
            }
        }
        
        try {

            stmt = conn.createStatement();

            // retrieve the persistent user object
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet r = stmt.getResultSet();
                return new AttributeTypeIterator( r, objectModel );
            }
        }
        catch( Exception e ) {      // just in case...
            throw new DTException( "userManager.restore: Could not restore persistent user object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new DTException( "userManager.restore: Could not restore persistent user object" );
    }
    
    public Category restoreCategoryWithType(AttributeType attributeType){
        String selectAttributeTypeSql = "select categoryId, name from attributeType where name = ?";
        Statement stmt = null;
        stmt = (PreparedStatement) conn.prepareStatement( selectAttributeTypeSql );
        if( attributeType.getName() != null ) {
            stmt.setString( 1, attributeType.getName() );
        } else {
            throw new DTException( "AttributeTypeManager.restoreCategoryWithType: can't restoreCategoryWithType: attributeType.getName() undefined" );
        }
        try {
            stmt = conn.createStatement();
            // retrieve the persistent Category object
            //
            if( stmt.execute() ) { // statement returned a result
                ResultSet r = stmt.getResultSet();
                if( r.hasNext() ) {
                    return r.next();
                }
                else
                    return null;
            }
        }
        catch( Exception e ) { // just in case...
            throw new DTException( "AttributeTypeManager.restoreCategoryWithType: Could not restore persistent AttributeType objects; Root cause: " + e );
        }
        throw new DTException( "AttributeTypeManager.restoreCategoryWithType: Could not restore persistent AttributeType objects" );
    }
    
    public void delete( AttributeType attributeType )
    throws DTException
    {
        String deleteAttributeTypeSql = "delete from attribute_type where name = ?";
        PreparedStatement stmt = null;
        int inscnt;
        // form the query based on the given User object instance
        if( !attributeType.isPersistent() )
            return;
        try {
            
            stmt = (PreparedStatement) conn.prepareStatement( deleteAttributeTypeSql );
            stmt.setString( 1, attributeType.getName() );
            inscnt = stmt.executeUpdate();
            if( inscnt == 0 ) {
                throw new DTException( "AttributeTypeManager.delete: failed to delete this AttributeType" );
            }
        }
        catch( SQLException e ) {
            throw new DTException( "AttributeTypeManager.delete: failed to delete this AttributeType: " + e.getMessage() );
        }
    }
}
