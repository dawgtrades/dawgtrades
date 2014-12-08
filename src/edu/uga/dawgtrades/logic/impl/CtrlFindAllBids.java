//
// A control class to implement the 'List all bids' use case
//
//
package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Bid;
import edu.uga.dawgtrades.model.ObjectModel;



public class CtrlFindAllBids {
    
    private ObjectModel objectModel = null;
    
    public CtrlFindAllBids( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }

    public List<Bid> findAllBids() 
            throws DTException
    {
        List<Bid>      bids  = null;
        Iterator<Bid>  bidIter = null;
        Bid            bid = null;

        bids = new LinkedList<Bid>();
        
        // retrieve all Bid objects
        //
        bidIter = objectModel.findBid( null );
        while( bidIter.hasNext() ) {
            bid = bidIter.next();
            System.out.println( bid );
            bids.add( bid );
        }

        return bids;
    }
}
