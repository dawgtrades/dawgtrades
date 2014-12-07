//
// A control class to implement the 'Create Membership' use case
// Should ONLY be done once at start. Then should just be modified.
package edu.uga.dawgtrades.logic.impl;

import java.util.Date;
import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Membership;
import edu.uga.dawgtrades.model.ObjectModel;


public class CtrlCreateMembership 
{
    
    private ObjectModel objectModel = null;
    
    public CtrlCreateMembership( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    
    public long createMembership( float price ) throws DTException    { 
        Membership               membership  = null;
        
        if (price < 0)
        	throw new DTException("Price for membership can't be negative");
 
        membership = objectModel.createMembership(price, new Date());
		
		// save new membership price
        objectModel.storeMembership( membership );

        return membership.getId();
    }
}

