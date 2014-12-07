//
// A control class to implement the 'Create RegisteredUser' use case
//
//

package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;


public class CtrlCreateRegisteredUser 
{
    
    private ObjectModel objectModel = null;
    
    public CtrlCreateRegisteredUser( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    
    public long createRegisteredUser( String name, String firstName, String lastName, String password, boolean isAdmin, String email, String phone, boolean canText	) throws DTException
    { 
        RegisteredUser               registeredUser  = null;
        RegisteredUser               modelRegisteredUser  = null;
        Iterator<RegisteredUser>     registeredUserIter  = null;

        // check if the userName already exists
        modelRegisteredUser = objectModel.createRegisteredUser();
        modelRegisteredUser.setName( name );
        registeredUserIter = objectModel.findRegisteredUser( modelRegisteredUser );
        while( registeredUserIter.hasNext() ) {
            registeredUser = registeredUserIter.next();
        }
        
        // check if the RegisteredUser actually exists, and if so, throw an exception
        if( registeredUser != null )
            throw new DTException( "A RegisteredUser with this user name already exists" );
        
        registeredUser = objectModel.createRegisteredUser(name,firstName, lastName, password, isAdmin, email, phone, canText);

		registeredUser.setName( name );
		registeredUser.setFirstName( firstName );
		registeredUser.setLastName( lastName );
		registeredUser.setPassword( password );
		registeredUser.setIsAdmin( isAdmin );
		registeredUser.setEmail( email );
		registeredUser.setPhone( phone );
		registeredUser.setCanText( canText );
        objectModel.storeRegisteredUser( registeredUser );

        return registeredUser.getId();
    }
}

