package edu.uga.dawgtrades.persistence.impl;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.dawgtrades.model.DTException;
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
        String               insertUserSql = "insert into registered_user ( last_name, first_name, phone, email, uname, upassword, status, is_admin, can_text, membership_fee_id ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";              
        String               updateUserSql = "update registered_user  set last_name = ?, first_name = ?, phone = ?, email = ?, uname = ?, upassword = ?, status = ?, is_admin = ?, can_text = ?, , membership_fee_id = ? where id = ?";              
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
						
			if( user.getStatus() != null )
                stmt.setString( 7,  user.getStatus() );
            else
                stmt.setNull(7, ;

			if( user.getIsAdmin() != null )
                stmt.setString( 8,  user.getIsAdmin() );
            else
                stmt.setNull(8, java.sql.Types.TINYINT);
				
			if( user.getCanText() != null )
                stmt.setString( 9,  user.getCanText() );
            else
                stmt.setNull(9, java.sql.Types.boolean);
							
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

    public Iterator<registeredUser> restore( user modeluser ) 
            throws DTException
    {
        String       selectUserSql = "select 	last_name, first_name, phone, email, status, uname, upassword, is_admin, can_text, membership_fee_id from registered_user"; 
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );

        condition.setLength( 0 );
        
        // form the query based on the given user object instance
        query.append( selectUserSql );
        
        if( modelUser != null ) {
            if( modelUser.getId() >= 0 ) // id is unique, so it is sufficient to get a user
                query.append( " where id = " + modelUser.getId() );
            else if( modelUser.getUserName() != null ) // userName is unique, so it is sufficient to get a user
                query.append( " where username = '" + modeluser.getUserName() + "'" );
            else {
                if( modeluser.getPassword() != null )
                    condition.append( " password = '" + modeluser.getPassword() + "'" );

                if( modeluser.getEmail() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " email = '" + modeluser.getEmail() + "'" );
                }

                if( modeluser.getFirstName() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " firstName = '" + modeluser.getFirstName() + "'" );
                }

                if( modeluser.getLastName() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " lastName = '" + modeluser.getLastName() + "'" );
                }

                if( modeluser.getAddress() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " address = '" + modeluser.getAddress() + "'" );
                }        

                if( modeluser.getPhone() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " phone = '" + modeluser.getPhone() + "'" );
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
                return new RegisteredUserIterator( r, objectModel );
            }
        }
        catch( Exception e ) {      // just in case...
            throw new DTException( "userManager.restore: Could not restore persistent user object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new DTException( "userManager.restore: Could not restore persistent user object" );
    }
    
    public Iterator<User> restoreEstablishedBy( User user ) 
            throws DTException
    {
        String       selectUserSql = 
		"select 
		c.id, c.name, c.address, c.established, c.founderid 
		from registered_user p, club c 
		where c.founderid = p.id"; 
		
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );

        condition.setLength( 0 );
        
        // form the query based on the given user object instance
        query.append( selectUserSql );
        
        if( user != null ) {
            if( user.getId() >= 0 ) // id is unique, so it is sufficient to get a user
                query.append( " and p.id = " + user.getId() );
            else if( user.getUserName() != null ) // userName is unique, so it is sufficient to get a user
                query.append( " and p.username = '" + user.getUserName() + "'" );
            else {
                if( user.getPassword() != null )
                    condition.append( " p.password = '" + user.getPassword() + "'" );

                if( user.getEmail() != null && condition.length() == 0 )
                    condition.append( " p.email = '" + user.getEmail() + "'" );
                else
                    condition.append( " AND p.email = '" + user.getEmail() + "'" );

                if( user.getFirstName() != null && condition.length() == 0 )
                    condition.append( " p.firstname = '" + user.getFirstName() + "'" );
                else
                    condition.append( " AND p.firstname = '" + user.getFirstName() + "'" );

                if( user.getLastName() != null && condition.length() == 0 )
                    condition.append( " p.lastname = '" + user.getLastName() + "'" );
                else
                    condition.append( " AND p.lastname = '" + user.getLastName() + "'" );

                if( user.getAddress() != null && condition.length() == 0 )
                    condition.append( " p.address = '" + user.getAddress() + "'" );
                else
                    condition.append( " AND p.address = '" + user.getAddress() + "'" );         

                if( user.getPhone() != null && condition.length() == 0 )
                    condition.append( " p.phone = '" + user.getPhone() + "'" );
                else
                    condition.append( " AND p.phone = '" + user.getPhone() + "'" );
                
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
                return new RegisteredUserIterator( r, objectModel );
            }
        }
        catch( Exception e ) {      // just in case...
            throw new DTException( "RegisteredUserManager.restoreEstablishedBy: Could not restore persistent user objects; Root cause: " + e );
        }

        throw new DTException( "RegisteredUserManager.restoreEstablishedBy: Could not restore persistent user objects" );
    }
    
    public void delete( User user ) 
            throws DTException
    {
        String               deleteUserSql = "delete from registered_user where id = ?";              
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
