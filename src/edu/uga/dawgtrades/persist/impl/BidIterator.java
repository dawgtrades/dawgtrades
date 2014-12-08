package edu.uga.dawgtrades.persist.impl;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Bid;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;

/**
 * Iterator for Bids. 
 * @author Justin
 *
 */

public class BidIterator implements Iterator<Bid> {
	
	private ResultSet   rs = null;
    private ObjectModel objectModel = null;
    private boolean     more = false;
    
    public BidIterator( ResultSet rs, ObjectModel objectModel )
            throws DTException
    {
        this.rs = rs;
        this.objectModel = objectModel;
        try {
            more = rs.next();
        }
        catch( Exception e ) {  
            throw new DTException( "BidIterator: Cannot create Bid iterator; cause: " + e );
        }
    }

    public boolean hasNext()
    {
        return more;
    }
    
    public Bid next()
    {
    	//bid info
    	long id;
    	long userId;
    	long auctionId;
    	float amount;
    	Date date;
    	//auction info
    	
    	long itemId;
    	float minPrice;
    	Date expiration;
    	//user info
    	
    	String name;
    	String firstName;
    	String lastName;
    	String password;
    	boolean isAdmin;
    	String email;
    	String phone;
    	boolean canText;
    	Bid bid = null;
    	Auction auction = null;
    	RegisteredUser user = null;

        if( more ) {

            try {
            	
                id = rs.getLong( 1 );
                userId = rs.getLong(2);
                auctionId = rs.getLong( 3 );
                amount = rs.getFloat( 4 );
                
                java.sql.Date sDate = rs.getDate( 5 );
                date = new java.util.Date( sDate.getTime());
                
            	itemId = rs.getLong( 6 );
            	minPrice = rs.getFloat(7);
            	
            	sDate = rs.getDate( 8 );
                expiration = new java.util.Date( sDate.getTime());
            	
            	name = rs.getString(9);
            	firstName = rs.getString(10);
            	lastName = rs.getString(11);
            	password = rs.getString(12);
            	isAdmin = rs.getBoolean(13);
            	email = rs.getString(14);
            	phone = rs.getString(15);
            	canText = rs.getBoolean(16);

                more = rs.next();
            }
            catch( Exception e ) {
                throw new NoSuchElementException( "BidIterator: No next Bidobject; cause: " + e );
            }
            
            Item item = objectModel.createItem();
            item.setId(itemId);
            try {
				auction = objectModel.createAuction(item, minPrice, expiration);
				auction.setId(auctionId);
				user = objectModel.createRegisteredUser(name, firstName, lastName, password, isAdmin, email, phone, canText);
	            user.setId(userId);
				bid = objectModel.createBid(auction, user, amount);
	            bid.setId( id );
			} catch (DTException e) {
				e.printStackTrace();
			}
            

            return bid;
        }
        else {
            throw new NoSuchElementException( "BidIterator: No next Bid object" );
        }
    }
    
    public void remove()
    {
        throw new UnsupportedOperationException();
    }

	
}
