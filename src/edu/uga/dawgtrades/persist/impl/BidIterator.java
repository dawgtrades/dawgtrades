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
    	float amount;
    	Date date;
    	boolean isWinning;
    	//auction info
    	long auctionId;
    	long itemId;
    	float minPrice;
    	Date expiration;
    	//user info
    	long userId;
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
                amount = rs.getFloat( 2 );
                date = rs.getDate( 3 );
                isWinning = rs.getBoolean(4 );
                auctionId = rs.getLong( 5 );
            	itemId = rs.getLong( 6 );
            	minPrice = rs.getFloat(7);
            	expiration = rs.getDate(8);
            	userId = rs.getLong(9);
            	name = rs.getString(10);
            	firstName = rs.getString(11);
            	lastName = rs.getString(12);
            	password = rs.getString(13);
            	isAdmin = rs.getBoolean(14);
            	email = rs.getString(15);
            	phone = rs.getString(16);
            	canText = rs.getBoolean(17);

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
