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
import edu.uga.dawgtrades.model.impl;
import edu.uga.dawgtrades.model.Auction;


public class CtrlCreateBid 
{
    
    private ObjectModel objectModel = null;
    
    public CtrlCreateBid( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    
    public long createBid(float amount, long auctionId, long registeredUserId)    { 
        Bid               bid  = null;
        Bid               modelBid  = null;
        Iterator<Bid>     bidIter  = null;
		
        // check if the auction already exists
        modelAuction = objectModel.createAuction();
		modelAuction.setItemId( itemId);
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
			
        // create bid object
        bid = objectModel.createBid(itemId, minPrice);
		bid.setAmount( amount );
		// starts with today's date and time
        Calendar c = Calendar.getInstance(); 
        // advances it 3 days
        c.add(Calendar.DAY_OF_YEAR, 3);  
        // gets modified time
        Date expiration = c.getTime(); 
		bid.setDate( expiration );
		// save bid data
        objectModel.storeBid( bid );

		// update the auction sellingprice
        auction.setExpiration(expiration);
		auction.setItemId(itemId);
		auction.setMinPrice( amount);
        objectModel.storeAuction( auction );		
		
        return bid.getId();
    }
}

