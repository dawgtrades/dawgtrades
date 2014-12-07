
package edu.uga.dawgtrades.logic.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.Auction;

/**
 * A control class to update an Auction for purposes of reauctioning
 * @author Justin Rector
 */

public class CtrlUpdateAuction 
{
    
    private ObjectModel objectModel = null;
    
    public CtrlUpdateAuction( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    
    public void updateAuction( long auctionId ) throws DTException
    { 
        Auction               auction  = null;
        Auction               modelAuction  = null;
        Iterator<Auction>     auctionIter  = null;

        // retrieve the auction
        modelAuction = objectModel.createAuction();
        modelAuction.setId(auctionId);
        auctionIter = objectModel.findAuction( modelAuction );
        while( auctionIter.hasNext() ) {
            auction = auctionIter.next();
        }
        
        // make sure the auction actually exists
        if( auction == null )
            throw new DTException( "The auction does not exist" );
        
        // starts with today's date and time
        Calendar c = Calendar.getInstance();
        // advances it 3 days
        c.add(Calendar.DAY_OF_YEAR, 3);
        // gets modified time
        Date expiration = c.getTime();
        
        auction.setExpiration(expiration);

        objectModel.storeAuction( auction );

    }
}