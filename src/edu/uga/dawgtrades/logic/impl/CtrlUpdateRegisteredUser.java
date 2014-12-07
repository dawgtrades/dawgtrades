
package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;

/**
 * A control class to implement the update of a RegisteredUser's info
 * @author Justin Rector
 */

public class CtrlUpdateRegisteredUser 
{
    
    private ObjectModel objectModel = null;
    
    public CtrlUpdateRegisteredUser( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    
    public void updateRegisteredUser( long userId, String name, String firstName, String lastName, String password, boolean isAdmin, String email, String phone, boolean canText ) throws DTException
    { 
        RegisteredUser               registeredUser  = null;
        RegisteredUser               modelRegisteredUser  = null;
        Iterator<RegisteredUser>     registeredUserIter  = null;

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
        
        registeredUser = objectModel.createRegisteredUser(name, firstName, lastName, password, isAdmin, email, phone, canText);
        registeredUser.setId(userId);

        objectModel.storeRegisteredUser( registeredUser );

    }
}
