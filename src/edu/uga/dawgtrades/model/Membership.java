package edu.uga.dawgtrades.model;

import java.util.Date;

/**
 * This interface represents a Membership in DawgTrades.  It has a collection of methods to read/write 
 * Membership's attributes.  There must be only one Membership object (singleton).
 * It also extends the Persistable interface, since objects of this class will be stored in the persistent data store.
 * 
 *  @author Krys J. Kochut
 *
 */
public interface Membership 
    extends Persistable
{
    /**
     * Get the price of the membership in DawgTrades.
     * @return the current membership price
     */
    float getPrice();
    
    /**
     * Set the new price of the membership in DawgTrades.
     * @param price the new price
     */
    void setPrice( float price );
    
    /**
     * Return the date when the current membership price was set.
     * @return the date when the current price was set
     */
    Date getDate();
    
}
