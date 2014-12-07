//
// A control class to implement the 'List all auction' use case
//
//
package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.ObjectModel;



public class CtrlFindAllAuctions {
    
    private ObjectModel objectModel = null;
    
    public CtrlFindAllAuctions( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }

    public List<Auction> findAllAuctions() 
            throws DTException
    {
        List<Auction>      auctions  = null;
        Iterator<Auction>  auctionIter = null;
        Auction            auction = null;

        auctions = new LinkedList<Auction>();
        
        // retrieve all Auction objects
        //
        auctionIter = objectModel.findAuction( null );
        while( auctionIter.hasNext() ) {
            auction = auctionIter.next();
            System.out.println( auction );
            auctions.add( auction );
        }

        return auctions;
    }
}
