package edu.uga.dawgtrades.authentication;


import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persist.Persistence;
import edu.uga.dawgtrades.persist.impl.DbUtils;
import edu.uga.dawgtrades.persist.impl.PersistenceImpl;






/**
 * Based on the modified code from Matthew Eavenson
 * 
 * @author Matthew Eavenson
 */

/** This class provides different operations for the Sessions such as 
 *  creating new sessions, removing, login and logout.
 */
public class SessionManager
{
    /** 
     * Map for the existing sessions
     */
    private static Map<String, Session> sessions;
    
    /** 
     * Map for the currently logged-in users
     */
    private static Map<String, Session> loggedIn;
    
    private static Logger log = Logger.getLogger( SessionManager.class );
    
    static{
        sessions = new HashMap<String, Session>();
        loggedIn = new HashMap<String, Session>();
    } 
    
    /****************************************************
     * Logs the user into the system, create a database connection, and create session.
     * @param username the user name for the user.
     * @param password the password for the user.
     * @return         the new session ID created
     * @throws GVException
     */
    public static String login( String username, String password ) 
            throws DTException 
    {
        Connection conn = null;
        ObjectModel objectModel = null;
        Persistence persistence = null;
        RegisteredUser loginUser = null;
        RegisteredUser knownUser = null;
        Session s = null;
        
        try {
            conn = DbUtils.connect();
        } catch (Exception seq) {
            throw new DTException( "SessionManager.login: Unable to get a database connection" );
        }
        
        s = new Session( conn );
        
        objectModel = new ObjectModelImpl();
        persistence = new PersistenceImpl( conn, objectModel );
        
        loginUser = objectModel.createRegisteredUser();
        loginUser.setName( username );
        loginUser.setPassword( password );
        
        Iterator<RegisteredUser> users = persistence.restoreRegisteredUser( loginUser );
        if( users.hasNext() ) {
             knownUser = users.next();
             System.out.println( "Have user!" );
             System.out.println( "id: " + knownUser.getId() );
             System.out.println( "userName: " + knownUser.getName() );
             System.out.println( "password: " + knownUser.getPassword() );
             System.out.println( "email: " + knownUser.getEmail() ); 
             loginUser = null;
             return createSession( s, knownUser );
        } 
        else {
            log.error( "SessionManager.login: Invalid UserName or Password for: " + username );
            throw new DTException( "SessionManager.login: Invalid User Name or Password" );
        }
        
    }
    
    /****************************************************
     * Create a new session for the current user
     * @param session Session to be created
     * @param person Current User logged In
     * @return  the new Session Id
     * @throws GVException
     */
    private static String createSession( Session session, RegisteredUser user ) 
            throws DTException
    {
        if( user == null ) {
            if( session.getConnection() != null ) {
                try { 
                    session.getConnection().close();
                } 
                catch( SQLException sqlEx ) {
                    log.error( "SessionManager.createSession: No user found" + sqlEx );
                }
            }
            log.error( "SessionManager.createSession: Bad username or password" );
            throw new DTException( "SessionManager.createSession: Bad username or password" );
        }
        
        if( loggedIn.containsKey(user.getName()) ) {
            Session qs = loggedIn.get(user.getName());
            qs.setUser(user);
            return qs.getSessionId();
        }
        session.setUser(user);
        
        String ssid = secureHash( "DAWGTRADES" + System.nanoTime() );
        session.setSessionId( ssid );
        
        sessions.put( ssid, session );
        loggedIn.put( user.getName(), session );
        session.start();
        return ssid;
    }
    
    /****************************************************
     * Logout of the current session (based on session)
     * @param  the session being used
     * @throws GVException
     */
    public static void logout(Session s) 
            throws DTException
    {
        s.setExpiration(new Date());
        s.interrupt();
        removeSession(s);
    }
    
    /****************************************************
     * Logout of the current session (based on session)
     * @param  ssid the session being used
     * @throws GVException
     */
    public static void logout(String ssid) 
            throws DTException
    {
        Session s = getSessionById(ssid);
        s.setExpiration(new Date());
        s.interrupt();
        removeSession(s);
    }
    
    /****************************************************
     * Remove the session
     * @param s the current session
     * @throws GVException
     */
    protected static void removeSession( Session s ) 
            throws DTException
    {
        try { 
            s.getConnection().close();
        } 
        catch( SQLException sqe ) { 
            log.error( "SessionManager.removeSession: cannot close connection", sqe );
            throw new DTException( "SessionManager.removeSession: Cannot close connection" );
        } // try
        sessions.remove( s.getSessionId() );
        loggedIn.remove( s.getUser().getName() );
    }
    
    /****************************************************
     * Get the session by session id
     * @param ssid the current session id
     */
    public static Session getSessionById( String ssid ){
        return sessions.get( ssid );
    }
    
    /*********************************************************************
     * Hashes the string input using the SHA1 algorithm.
     * @param   input   string to hash.
     * @return  SHA hash of the string.
     * @throws ClubsException 
     */
    public static String secureHash( String input ) 
            throws DTException
    {
        StringBuilder output = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance( "SHA" );
            md.update( input.getBytes() );
            byte[] digest = md.digest();
            for( int i = 0; i < digest.length; i++ ) {
                String hex = Integer.toHexString( digest[i] );
                if( hex.length() == 1 )
                    hex = "0" + hex;
                hex = hex.substring( hex.length() - 2 );
                output.append( hex );
            }
        }
        catch( Exception e ) {
            log.error(
                    "SessionManager.secureHash: Invalid Encryption Algorithm", e );
            throw new DTException(
                    "SessionManager.secureHash: Invalid Encryption Algorithm" );
        }
        return output.toString();
    }
    
    /**********************************************************************
     * Return the logger object. 
     * @return Logger object. 
     * @author Arsham Mesbah
     */
    public static Logger getLog()
    {
        return log; 
    }

}
