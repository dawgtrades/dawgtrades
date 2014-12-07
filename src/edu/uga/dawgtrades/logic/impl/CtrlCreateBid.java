//
// A control class to implement the 'Create Bid' use case
//
//

package edu.uga.dawgtrades.logic.impl;

import java.util.Date;
import java.util.Calendar;
import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Bid;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.RegisteredUser;


public class CtrlCreateBid 
{
    
    private ObjectModel objectModel = null;
    
    public CtrlCreateBid( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    
    public long createBid(float amount, long auctionId, long registeredUserId) throws DTException    { 
        Bid               bid  = null;
        Auction auction = null;
        RegisteredUser user = null;
        Bid               modelBid  = null;
        Auction modelAuction = null;
        RegisteredUser modelRegisteredUser;
        Iterator<Bid>     bidIter  = null;
        Iterator<Auction> auctionIter = null;
        Iterator<RegisteredUser> userIter = null;
        
        // retrieve the user
        modelRegisteredUser = objectModel.createRegisteredUser();
        modelRegisteredUser.setId(registeredUserId);
        userIter = objectModel.findRegisteredUser( modelRegisteredUser );
        while( userIter.hasNext() ) {
            user = userIter.next();
        }
        
        // make sure the user actually exists
        if( user == null )
            throw new DTException( "The user does not exist" );
        
        
        // check if the auction exists
        modelAuction = objectModel.createAuction();
		modelAuction.setId(auctionId);
        auctionIter = objectModel.findAuction( modelAuction );
        while( auctionIter.hasNext() ) {
            auction = auctionIter.next();
        }
		// verify auction exists, and else throw an exception
        if( auction == null )
            throw new DTException( "Auction does not exist." );
        
        // check if the bid > existing highest bid
        if ( amount <= auction.getSellingPrice() )
            throw new DTException( "The Bid is too low for this auction item." );
        if (auction.getIsClosed())
        	throw new DTException( "This auction is closed.");
		
        // update the auction minprice
        auction.setMinPrice(amount);
        
        objectModel.storeAuction( auction );	
        
        // create bid object
        bid = objectModel.createBid(auction, user, amount); 
		bid.setDate( new Date() );
		
		// save bid data
        objectModel.storeBid( bid );

        	
		
        return bid.getId();
    }
}

