package edu.uga.dawgtrades.persist.impl;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;

//import edu.uga.clubs.model.Club;
//import edu.uga.clubs.model.ObjectModel;


class RegisteredUserManager
{
    private ObjectModel objectModel = null;
    private Connection  conn = null;
    
    public RegisteredUserManager( Connection conn, ObjectModel objectModel )
    {
        this.conn = conn;
        this.objectModel = objectModel;
    }
    
    public void save( RegisteredUser user ) 
            throws DTException
    {
        String               insertUserSql = "insert into registered_user ( last_name, first_name, phone, email, uname, upassword, is_admin, can_text) values ( ?, ?, ?, ?, ?, ?, ?, ?)";              
        String               updateUserSql = "update registered_user  set last_name = ?, first_name = ?, phone = ?, email = ?, uname = ?, upassword = ?, is_admin = ?, can_text = ? where id = ?";              
        PreparedStatement    stmt;
        int                  inscnt;
        long                 userId;
        
        try {
            
            if( !user.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertUserSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateUserSql );

            if( user.getLastName() != null )
                stmt.setString( 1, user.getLastName() );
            else
                throw new DTException( "RegisteredUserManager.save: can't save a user: last name undefined" );
				
            if( user.getFirstName() != null )
                stmt.setString( 2, user.getFirstName() );
            else
                throw new DTException( "RegisteredUserManager.save: can't save a user: first name undefined" );

            if( user.getPhone() != null )
                stmt.setString( 3,  user.getPhone() );
            else
                throw new DTException( "RegisteredUserManager.save: can't save a user: phone undefined" );
				
            if( user.getEmail() != null )
                stmt.setString( 4,  user.getEmail() );
            else
                throw new DTException( "RegisteredUserManager.save: can't save a user: email undefined" );
				
            if( user.getName() != null ) // user is unique, so it is sufficient to get a user
                stmt.setString( 5, user.getName() );
            else 
                throw new DTException( "RegisteredUserManager.save: can't save a user: userName undefined" );

            if( user.getPassword() != null )
                stmt.setString( 6, user.getPassword() );
            else
                throw new DTException( "RegisteredUserManager.save: can't save a user: password undefined" );           
						
            stmt.setBoolean( 7,  user.getIsAdmin() );

            stmt.setBoolean( 8,  user.getCanText() );
							
            inscnt = stmt.executeUpdate();

            if( !user.isPersistent() ) {
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
                            userId = r.getLong( 1 );
                            if( userId > 0 )
                                user.setId( userId ); // set this user's db id (proxy object)
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new DTException( "RegisteredUserManager.save: failed to save a user" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new DTException( "RegisteredUserManager.save: failed to save a user: " + e );
        }
    }

    public Iterator<RegisteredUser> restore( RegisteredUser modelUser ) 
            throws DTException
    {
        String       selectUserSql = "select last_name, first_name, phone, email, uname, upassword, is_admin, can_text from registered_user"; 
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );

        condition.setLength( 0 );
        
        // form the query based on the given user object instance
        query.append( selectUserSql );
        
        if( modelUser != null ) {
            if( modelUser.getId() >= 0 ) // id is unique, so it is sufficient to get a user
                query.append( " where user_id = " + modelUser.getId() );
            else if( modelUser.getName() != null ) // userName is unique, so it is sufficient to get a user
                query.append( " where uname = '" + modelUser.getName() + "'" );
            else {
                if( modelUser.getLastName() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " last_name = '" + modelUser.getLastName() + "'" );
                }
				
                if( modelUser.getFirstName() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " first_name = '" + modelUser.getFirstName() + "'" );
                }				
				
                if( modelUser.getPhone() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " phone = '" + modelUser.getPhone() + "'" );
                }
				
                if( modelUser.getEmail() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " email = '" + modelUser.getEmail() + "'" );
                }       
				
                if( modelUser.getPassword() != null )
                    condition.append( " upassword = '" + modelUser.getPassword() + "'" );
					
                if( condition.length() > 0 )
                	condition.append( " and" );
                condition.append( " is_admin = '" + modelUser.getIsAdmin() + "'" );
               					
					
                if( condition.length() > 0 )
                    condition.append( " and" );
                condition.append( " can_text = '" + modelUser.getCanText() + "'" );
                					
					
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
                return new RegisteredUserIterator( r, objectModel );
            }
        }
        catch( Exception e ) {      // just in case...
            throw new DTException( "userManager.restore: Could not restore persistent user object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new DTException( "userManager.restore: Could not restore persistent user object" );
    }
    
    //public Iterator<User> restoreEstablishedBy( User user ) 
    //        throws DTException
    public Iterator<Item> restoreItemsOwned(RegisteredUser user) throws DTException
    {
        String       selectUserSql = 
		"select i.item_id, i.name, i.category_id, i.identifier, i.description, i.owner_id from registered_user u, item i where u.user_id = i.owner_id";
		
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );

        condition.setLength( 0 );
        
        // form the query based on the given user object instance
        query.append( selectUserSql );
        
        if( user != null ) {
            if( user.getId() >= 0 ) // id is unique, so it is sufficient to get a user
                query.append( " and u.user_id = " + user.getId() );
            else if( user.getName() != null ) // userName is unique, so it is sufficient to get a user
                query.append( " and u.uname = '" + user.getName() + "'" );
            else {
			
                if( user.getLastName() != null && condition.length() == 0 )
                    condition.append( " u.last_name = '" + user.getLastName() + "'" );
                else
                    condition.append( " AND u.last_name = '" + user.getLastName() + "'"); 										
                if( user.getFirstName() != null && condition.length() == 0 )
                    condition.append( " u.first_name = '" + user.getFirstName() + "'" );
                else
                    condition.append( " AND u.first_name = '" + user.getFirstName() + "'" );

                if( user.getPhone() != null && condition.length() == 0 )
                    condition.append( " u.phone = '" + user.getPhone() + "'" );
                else
                    condition.append( " AND u.phone = '" + user.getPhone() + "'" );					
					
                if( user.getEmail() != null && condition.length() == 0 )
                    condition.append( " u.email = '" + user.getEmail() + "'" );
                else
                    condition.append( " AND u.email = '" + user.getEmail() + "'" );
					
                if( user.getPassword() != null && condition.length() == 0 )
                    condition.append( " u.upassword = '" + user.getPassword() + "'" );
                else
                    condition.append( " AND u.upassword = '" + user.getPassword() + "'" );

                if( condition.length() == 0 )
                    condition.append( " u.is_admin = '" + user.getIsAdmin() + "'" );
                else
                    condition.append( " AND u.is_admin = '" + user.getIsAdmin() + "'" );   					
                if( condition.length() == 0 )
                    condition.append( " u.can_text = '" + user.getCanText() + "'" );
                else
                    condition.append( " AND u.can_text = '" + user.getCanText() + "'" );         
                
                if( condition.length() > 0 ) {
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
                return new ItemIterator( r, objectModel );
            }
        }
        catch( Exception e ) {      // just in case...
            throw new DTException( "RegisteredUserManager.restoreItemsOwned: Could not restore persistent user objects; Root cause: " + e );
        }

        throw new DTException( "RegisteredUserManager.restoreItemsOwned: Could not restore persistent user objects" );
    }
    
    public void delete( RegisteredUser user ) 
            throws DTException
    {
        String               deleteUserSql = "delete from registered_user where user_id = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        // form the query based on the given User object instance
        if( !user.isPersistent() ) // is the User object persistent?  If not, nothing to actually delete
            return;
        
        try {
            
            //DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
            //DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
            stmt = (PreparedStatement) conn.prepareStatement( deleteUserSql );
            
            stmt.setLong( 1, user.getId() );
            
            inscnt = stmt.executeUpdate();
            
            if( inscnt == 0 ) {
                throw new DTException( "RegisteredUserManager.delete: failed to delete this user" );
            }
        }
        catch( SQLException e ) {
            throw new DTException( "RegisteredUserManager.delete: failed to delete this User: " + e.getMessage() );
        }
    }
}
