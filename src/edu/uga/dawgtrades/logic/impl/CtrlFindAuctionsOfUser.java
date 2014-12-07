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
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;


public class CtrlFindAuctionsOfUser {
    
    private ObjectModel objectModel = null;
    
    public CtrlFindAuctionsOfUser( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
	
    public List<Auction> findAuctionsOfUser( long userId ) throws DTException
    {
        Item                 modelItem = null;
        RegisteredUser modelRegisteredUser = null;
        RegisteredUser user = null;
        Iterator<Item>       itemIter = null;
        Iterator<RegisteredUser> userIter = null;
		Auction              modelAuction = null;
        List<Auction>        auctions  = null;		
        
        auctions = new LinkedList<Auction>();


        // retrieve the RegisteredUser
        modelRegisteredUser = objectModel.createRegisteredUser();
        modelRegisteredUser.setId(userId);
        userIter = objectModel.findRegisteredUser( modelRegisteredUser );
        while( userIter.hasNext() ) {
            user = userIter.next();
        }
        
        // make sure the RegisteredUser actually exists
        if( user == null )
            throw new DTException( "The RegisteredUser does not exist" );
        
        //retrieve the items of the user
        itemIter = objectModel.getItem(user);
        
        while (itemIter.hasNext()) {
        	modelItem = itemIter.next();
        	modelAuction = objectModel.getAuction(modelItem);
        	auctions.add(modelAuction);
        }
        
        return auctions;
    }
}
