//
// A control class to implement the 'Create Auction' use case
//
//

package edu.uga.dawgtrades.logic.impl;

import java.util.Date;
import java.util.Calendar;
import java.util.Iterator;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.impl;


public class CtrlCreateAuction 
{
    
    private ObjectModel objectModel = null;
    
    public CtrlCreateAuction( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    
    public long createAuction( long itemId, float minPrice) throws DTException    { 
        Auction               auction  = null;
        Auction               modelAuction  = null;
        Iterator<Auction>     auctionIter  = null;

        // check if the auction already exists
        modelAuction = objectModel.createAuction();
		modelAuction.setItemId( itemId);
		modelAuction.setMinPrice(minPrice);
        // starts with today's date and time
        Calendar c = Calendar.getInstance(); 
        // advances it 3 days
        c.add(Calendar.DAY_OF_YEAR, 3);  
        // gets modified time
        Date expiration = c.getTime(); 
		modelAuction.setExpiration(expiration);
		
        auctionIter = objectModel.findAuction( modelAuction );
        while( auctionIter.hasNext() ) {
            auction = auctionIter.next();
        }
        
        // check if the Auction actually exists, and if so, throw an exception
        if( auction != null )
            throw new DTException( "An Auction with this itemID and price already exists" );
        
        auction = objectModel.createAuction(itemId, minPrice);

        objectModel.storeAuction( auction );

        return auction.getId();
    }
}

