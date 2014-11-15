package edu.uga.dawgtrades.model;

/**
 * This class represents a DTException, a general exception within the DawgTrades system.
 * 
 *  @author Krys J. Kochut
 *
 */
public class DTException 
    extends Exception
{
    private static final long serialVersionUID = 1L;

    /**
     * Create a new DTException object.
     * @param message the message explaining the exception
     */
    public DTException( String message )
    {
      super( message );
    }

    /**
     * Create a new DTException object.
     * @param cause the cause of the exception
     */
    public DTException( Throwable cause )
    {
      super( cause );
    }
}
