//
// A control class to implement the 'List all Auction Of a user' UC
//
//
package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.impl;
import edu.uga.dawgtrades.model.ObjectModel;


public class CtrlFindAuctionsOfUser {
    
    private ObjectModel objectModel = null;
    
    public CtrlFindAuctionsOfUser( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
	
    public List<Auction> findAuctionsOfUser( long userId ) throws DTException
    {
        Item                 modelItem = null;
		Item                 item = null;
        Iterator<Item>       itemIter = null;
	    int i = 0;
        List<Item>           itemsOfUser = null;
		Auction              modelAuction = null;
        List<Auction>        auctionsOfUser  = null;		
        auctionsOfUser = new LinkedList<Auction>();


		// Loop to get all items for that user
		// items = itemIter.getItem(userID);
        // loop to get all auctions for those items	
		// loop Iter to get auctions for all items
		// set the item id: setItemId(itemId) getAuction(itemID)
        // find the item object
		
        modelItem = objectModel.createItem();
		// set user to get their items
		modelItem.setId( userId );
        itemIter = objectModel.findItem( modelItem );
        while( itemIter.hasNext() ) {
            item = itemIter.next();
			itemsOfUser.add(item.getId());
        }
        if( items == null )
            throw new DTException( "A item with this seller id does not exist: " + userId );

		
		// loop for all items in the array
		while (itemsOfUser[i] != null) {
          // create the model auction
          modelAuction = objectModel.createItem();

		  // create auction iterator		  
          Iterator<Auction> auctionIter = objectModel.findAuction( auctions );
	
		  // set the auction itemid to each item in the array
  		  modelAuction.setItemId(itemsOfUser[i] );
		  // find the 
		  auctionIter = objectModel.findItem( modelItem );
          while( auctionIter != null && auctionIter.hasNext() ) {		
		    Auction m = auctionIter.next();
            auctionsOfUser.add( m.getId() );
          }
		  i+=1;
		}

        return auctionsOfUser;
    }
}
