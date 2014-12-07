//
// A control class to implement the 'List all registeredUser' use case
//
//
package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.impl;
import edu.uga.dawgtrades.model.ObjectModel;



public class CtrlFindAllRegisteredUsers {
    
    private ObjectModel objectModel = null;
    
    public CtrlFindAllRegisteredUsers( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }

    public List<RegisteredUser> findAllRegisteredUsers() 
            throws DTException
    {
        List<RegisteredUser>      registeredUser  = null;
        Iterator<RegisteredUser>  registeredUserIter = null;
        RegisteredUser            registeredUser = null;

        registeredUser = new LinkedList<RegisteredUser>();
        
        // retrieve all Registered User objects
        //
        registeredUserIter = objectModel.findRegisteredUser( null );
        while( registeredUserIter.hasNext() ) {
            registeredUser = registeredUserIter.next();
            System.out.println( registeredUser );
            registeredUser.add( registeredUser );
        }

        return registeredUser;
    }
}
