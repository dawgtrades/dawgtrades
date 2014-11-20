package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import edu.uga.dawgtrades.model.DTException;


/**
 * This class performs database operations.
 * @author durandal, Haseeb Yousaf
 */
public class DbUtils {
 

    /**
     * Disables the auto commit for the current connection  
     * @param conn  the open connection
     */
    public static void disableAutoCommit( Connection conn )
            throws DTException
    {
        try {
            conn.setAutoCommit(false);
        }
        catch( SQLException ex ) {
            throw new DTException( "DbUtils.disableAutoCommit: Transaction error. " + ex.getMessage() );
        }
    }
    
    /**
     * Enable the auto commit for the current connection  
     * @param conn  the open connection
     */
    public static void enableAutoCommit( Connection conn )
            throws DTException
    {
        try {
            conn.setAutoCommit(true);
        }
        catch( SQLException ex ) {
            throw new DTException( "DbUtils.enableAutoCommit: Transaction error. " + ex.getMessage() );
        }
    }

    /**
     * Commit the transactions for the current connection  
     * @param conn  the open connection
     */
    public static void commit(Connection conn)
            throws DTException
    {
        try {
            conn.commit();
        } catch (SQLException ex) {
            throw new DTException( "DbUtils.commit: SQLException on commit " + ex.getMessage() );
        }
    }

    /**
     * rollback the transactions for the current connection  
     * @param conn  the open connection
     */
    public static void rollback(Connection conn)
            throws DTException
    {
        try {
            conn.rollback();
        } catch (SQLException ex) {
            throw new DTException( "DbUtils.rollback: Unable to rollback transaction. " + ex.getMessage() );
        }
    }

    /**
     * Establish a JDBC connection to the database.
     * @return  a database connection object
     * @throws  GVException
     */
    public static Connection connect()
            throws DTException
    {
	System.out.println(DbAccessConfig.DB_DRIVE_NAME);
	System.out.println(DbAccessConfig.DB_CONNECTION_URL);
        try {
            Class.forName(DbAccessConfig.DB_DRIVE_NAME);
        }
        catch (ClassNotFoundException ex) {
            throw new DTException( "DbUtils.connect: Unable to find Driver" );
        }
        try {
            return DriverManager.getConnection( DbAccessConfig.DB_CONNECTION_URL,
                                                DbAccessConfig.DB_CONNECTION_USERNAME,
                                                DbAccessConfig.DB_CONNECTION_PWD );
        }
        catch (SQLException ex) {
            throw new DTException( "DbUtils.connect: Unable to connect to database " + ex.getMessage() );
        }
    }

}
