package edu.uga.clubs.authentication;


import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import edu.uga.clubs.ClubsException;
import edu.uga.clubs.model.ObjectModel;
import edu.uga.clubs.model.Person;
import edu.uga.clubs.model.impl.ObjectModelImpl;
import edu.uga.clubs.persistence.Persistence;
import edu.uga.clubs.persistence.impl.DbUtils;
import edu.uga.clubs.persistence.impl.PersistenceImpl;




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
            throws ClubsException 
    {
        Connection conn = null;
        ObjectModel objectModel = null;
        Persistence persistence = null;
        Person loginPerson = null;
        Person knownPerson = null;
        Session s = null;
        
        try {
            conn = DbUtils.connect();
        } catch (Exception seq) {
            throw new ClubsException( "SessionManager.login: Unable to get a database connection" );
        }
        
        s = new Session( conn );
        
        objectModel = new ObjectModelImpl();
        persistence = new PersistenceImpl( conn, objectModel );
        
        loginPerson = objectModel.createPerson();
        loginPerson.setUserName( username );
        loginPerson.setPassword( password );
        
        Iterator<Person> persons = persistence.restorePerson( loginPerson );
        if( persons.hasNext() ) {
             knownPerson = persons.next();
             System.out.println( "Have person!" );
             System.out.println( "id: " + knownPerson.getId() );
             System.out.println( "userName: " + knownPerson.getUserName() );
             System.out.println( "password: " + knownPerson.getPassword() );
             System.out.println( "email: " + knownPerson.getEmail() ); 
             loginPerson = null;
             return createSession( s, knownPerson );
        } 
        else {
            log.error( "SessionManager.login: Invalid UserName or Password for: " + username );
            throw new ClubsException( "SessionManager.login: Invalid User Name or Password" );
        }
        
    }
    
    /****************************************************
     * Create a new session for the current user
     * @param session Session to be created
     * @param person Current User logged In
     * @return  the new Session Id
     * @throws GVException
     */
    private static String createSession( Session session, Person person ) 
            throws ClubsException
    {
        if( person == null ) {
            if( session.getConnection() != null ) {
                try { 
                    session.getConnection().close();
                } 
                catch( SQLException sqlEx ) {
                    log.error( "SessionManager.createSession: No user found" + sqlEx );
                }
            }
            log.error( "SessionManager.createSession: Bad username or password" );
            throw new ClubsException( "SessionManager.createSession: Bad username or password" );
        }
        
        if( loggedIn.containsKey(person.getUserName()) ) {
            Session qs = loggedIn.get(person.getUserName());
            qs.setUser(person);
            return qs.getSessionId();
        }
        session.setUser(person);
        
        String ssid = secureHash( "CLUBS" + System.nanoTime() );
        session.setSessionId( ssid );
        
        sessions.put( ssid, session );
        loggedIn.put( person.getUserName(), session );
        session.start();
        return ssid;
    }
    
    /****************************************************
     * Logout of the current session (based on session)
     * @param  the session being used
     * @throws GVException
     */
    public static void logout(Session s) 
            throws ClubsException
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
            throws ClubsException
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
            throws ClubsException
    {
        try { 
            s.getConnection().close();
        } 
        catch( SQLException sqe ) { 
            log.error( "SessionManager.removeSession: cannot close connection", sqe );
            throw new ClubsException( "SessionManager.removeSession: Cannot close connection" );
        } // try
        sessions.remove( s.getSessionId() );
        loggedIn.remove( s.getUser().getUserName() );
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
            throws ClubsException
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
            throw new ClubsException(
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
