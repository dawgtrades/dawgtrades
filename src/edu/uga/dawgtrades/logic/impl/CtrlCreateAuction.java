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
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;


public class CtrlCreateAuction 
{
    
    private ObjectModel objectModel = null;
    
    public CtrlCreateAuction( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    
    public long createAuction( long itemId, float minPrice) throws DTException    { 
        Auction               auction  = null;
        Item item = null;
        Auction               modelAuction  = null;
        Item modelItem = null;
        Iterator<Auction>     auctionIter  = null;
        Iterator<Item> itemIter = null;

        // retrieve the Item
        modelItem = objectModel.createItem();
        modelItem.setId(itemId);
        itemIter = objectModel.findItem( modelItem );
        while( itemIter.hasNext() ) {
            item = itemIter.next();
        }
        
        // make sure the item actually exists
        if( item == null )
            throw new DTException( "The Item does not exist" );
        
        // check if the auction already exists
        modelAuction = objectModel.createAuction();
		modelAuction.setItemId( itemId);
        auctionIter = objectModel.findAuction( modelAuction );
        while( auctionIter.hasNext() ) {
            auction = auctionIter.next();
        }
        
        // check if the Auction actually exists, and if so, throw an exception
        if( auction != null )
            throw new DTException( "An Auction with this itemID already exists" );
		
        if (minPrice < 0)
        	throw new DTException("This auction has an invalid price");
        
        // starts with today's date and time
        Calendar c = Calendar.getInstance(); 
        // advances it 3 days
        c.add(Calendar.DAY_OF_YEAR, 3);  
        // gets modified time
        Date expiration = c.getTime(); 
        
        // create auction object
        auction = objectModel.createAuction(item, minPrice, expiration);
        
		// save auction data
        objectModel.storeAuction( auction );

        return auction.getId();
    }
}

