
package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.Auction;

/**
 * A control class to delete an Auction
 * @author Justin Rector
 */

public class CtrlDeleteAuction 
{
    
    private ObjectModel objectModel = null;
    
    public CtrlDeleteAuction( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    
    public void deleteAuction( long auctionId ) throws DTException
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

        objectModel.deleteAuction(auction);

    }
}
