// Gnu Emacs C++ mode:  -*- Java -*-
//
// Class:	RegisteredUserIteratorImpl
//
// Vic Lawson
//
//
//

package edu.uga.dawgtrades.persist.impl;



import java.sql.ResultSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.dawgtrades.DTException;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;


public class RegisteredUserIterator 
    implements Iterator<RegisteredUser>
{
    private ResultSet   rs = null;
    private ObjectModel objectModel = null;
    private boolean     more = false;

    // these two will be used to create a new object
    //
    public RegisteredUserIterator( ResultSet rs, ObjectModel objectModel )
            throws DTException
    { 
        this.rs = rs;
        this.objectModel = objectModel;
        try {
            more = rs.next();
        }
        catch( Exception e ) {	// just in case...
            throw new DTException( "RegisteredUserIterator: Cannot create user iterator; root cause: " + e );
        }
    }

    public boolean hasNext() 
    { 
        return more; 
    }

    public Person next() 
    {
        long   id;
        String userName;
        String password;
        String email;
        String firstName;
        String lastName;
        String address;
        String phone;

        if( more ) {

            try {
                id = rs.getLong( 1 );
                userName = rs.getString( 2 );
                password = rs.getString( 3 );
                email = rs.getString( 4 );
                firstName = rs.getString( 5 );
                lastName = rs.getString( 6 );
                address = rs.getString( 7 );
                phone = rs.getString( 8 );

                more = rs.next();
            }
            catch( Exception e ) {	// just in case...
                throw new NoSuchElementException( "PersonIterator: No next Person object; root cause: " + e );
            }
            
            Person person = objectModel.createPerson( userName, password, email, firstName, lastName, address, phone );
            person.setId( id );
            
            return person;
        }
        else {
            throw new NoSuchElementException( "PersonIterator: No next Person object" );
        }
    }

    public void remove()
    {
        throw new UnsupportedOperationException();
    }

};
