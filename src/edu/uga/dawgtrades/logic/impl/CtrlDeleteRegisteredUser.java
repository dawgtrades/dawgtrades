
package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;

import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;

/**
 * A control class to delete a RegisteredUser
 * @author Justin Rector
 */

public class CtrlDeleteRegisteredUser 
{
    
    private ObjectModel objectModel = null;
    
    public CtrlDeleteRegisteredUser( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    
    public void deleteRegisteredUser( long userId ) throws DTException
    { 
        RegisteredUser               registeredUser  = null;
        RegisteredUser               modelRegisteredUser  = null;
        Auction modelAuction = null;
        Item modelItem = null;
        Iterator<RegisteredUser>     registeredUserIter  = null;
        Iterator<Item> itemIter = null;

        // retrieve the user
        modelRegisteredUser = objectModel.createRegisteredUser();
        modelRegisteredUser.setId(userId);
        registeredUserIter = objectModel.findRegisteredUser( modelRegisteredUser );
        while( registeredUserIter.hasNext() ) {
            registeredUser = registeredUserIter.next();
        }
        
        // make sure the user actually exists
        if( registeredUser == null )
            throw new DTException( "The user does not exist" );
       
        //check to see if user has auctions
        itemIter = objectModel.getItem(modelRegisteredUser);
        while( itemIter.hasNext() ) {
            modelItem = itemIter.next();
            if (modelItem != null) {
	            modelAuction = objectModel.getAuction(modelItem);
	            if (modelAuction != null) {
		            if (!modelAuction.getIsClosed())
		            	throw new DTException( "The user currently has auctions running");
		            }
            }
        }

        objectModel.deleteRegisteredUser( registeredUser );

    }
}

