package edu.uga.dawgtrades.logic.impl;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.Membership;

/**
 * A control class to update the membership price
 * @author Justin Rector
 */

public class CtrlUpdateMembershipPrice
{
    
    private ObjectModel objectModel = null;
    
    public CtrlUpdateMembershipPrice( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    
    public void updateMembershipPrice( float newPrice ) throws DTException
    { 
        Membership               membership  = null;

        // retrieve the Membership
        membership = objectModel.findMembership();
        
        // make sure the membership actually exists
        if( membership == null )
            throw new DTException( "The membership does not exist" );
        
        if ( newPrice < 0)
        	throw new DTException( "Invalid price");
        
        membership.setPrice(newPrice);

        objectModel.storeMembership( membership );

    }
}
