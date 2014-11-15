package edu.uga.dawgtrades.model;

import java.util.Date;

/**
 * This interface represents a Bid placed by a RegisteredUser on an Auction.  The It has a collection of 
 * methods to read/write Bid's attributes.
 * It also extends the Persistable interface, since objects of this class will be stored in the persistent data store.
 * 
 *  @author Krys J. Kochut
 *
 */
public interface Bid
    extends Persistable
{
    /**
     * Return the Bid's amount.
     * @return the amount of this Bid
     */
    float getAmount();
    
    /**
     * Set the new amount of this Bid.
     * @param amount the new amount
     */
    void setAmount(float amount);
    
    /**
     * Return the date of this Bid.
     * @return this Bid's date
     */
    Date getDate();
    
    /**
     * Set the new date of this Bid.
     * @param date the new date
     */
    void setDate(Date date);
    
    /**
     * Return true if this Bid is the winning Bid for the Auction.
     * @return true if this Bid is the winning Bid
     */
    boolean isWinning();
    
    /**
     * Return the Auction of this Bid.
     * @return the Auction of this Bid
     */
    Auction getAuction();
    
    /**
     * Return the RegisteredUser of this Bid.
     * @return this Bid's RegisteredUser
     */
    RegisteredUser getRegisteredUser();
}
