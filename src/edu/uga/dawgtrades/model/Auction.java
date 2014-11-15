package edu.uga.dawgtrades.model;


import java.util.Date;

/**
 * This interface represents an Auction of an Item.  It has a collection of methods to read/write Auction's attributes.
 * Auction participates in an association with Item, where the class at the other end has a multiplicity of 1.
 * Getters/setters for the identifiers of the one side objects are included, as well.
 * It also extends the Persistable interface, since objects of this class will be stored in the persistent data store.
 * 
 *  @author Krys J. Kochut
 *
 */
public interface Auction 
    extends Persistable
{
    /**
     * Return the minimum price of this Auction.
     * @return this Auction's minimum price
     */
    float getMinPrice();
    
    /**
     * Set the new minimum price of this Auction.
     * @param minPrice the new minimum price
     */
    void setMinPrice( float minPrice );
    
    /**
     * Return the expiration date of this Auction.
     * @return this Auction's expiration date
     */
    Date getExpiration();
    
    /**
     * Set the new expiration date of this Auction.
     * @param expiration the new expiration date
     */
    void setExpiration( Date expiration );
    
    /**
     * Return true is this Auction is closed, false otherwise.
     * @return true if this Auction is closed, false otherwise
     */
    boolean getIsClosed();
    
    /**
     * Return this Auction's selling price.
     * @return the selling price of this Auction
     */
    float getSellingPrice();
    
    // participates in associations with the other side having multiplicity of one
    /**
     * Return the identifier of the Item being sold at this Auction.
     * @return the identifier of this Auction's Item
     */
    long getItemId();
    
    /**
     * Set the identifier of the Item being sold at this Auction.
     * @param itemId the new identifier of this Auction's Item
     */
    void setItemId( long itemId );
}
