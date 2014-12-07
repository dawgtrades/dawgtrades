//
// A control class to implement the 'Create Membership' use case
//
//

package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Membership;
import edu.uga.dawgtrades.model.impl;


public class CtrlCreateMembership 
{
    
    private ObjectModel objectModel = null;
    
    public CtrlCreateMembership( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    
    public long createMembership( float price ) throws DTException    { 
        Membership               membership  = null;
        membership = objectModel.createMembership(price);
		// set new membership price
        membership.setPrice(price);
		// save new membership price
        objectModel.storeMembership( membership );

        return membership.getId();
    }
}

