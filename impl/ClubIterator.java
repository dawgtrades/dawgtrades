// Gnu Emacs C++ mode:  -*- Java -*-
//
// Class:	ClubIteratorImpl
//
// K.J. Kochut
//
//
//

package edu.uga.clubs.persistence.impl;




import java.sql.ResultSet;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.clubs.ClubsException;
import edu.uga.clubs.model.Club;
import edu.uga.clubs.model.ObjectModel;
import edu.uga.clubs.model.Person;


public class ClubIterator
    implements Iterator<Club>
{
    private ResultSet    rs = null;
    private ObjectModel  objectModel = null;
    private boolean      more = false;

    // these two will be used to create a new object
    //
    public ClubIterator( ResultSet rs, ObjectModel objectModel )
            throws ClubsException
    { 
        this.rs = rs;
        this.objectModel = objectModel;
        try {
            more = rs.next();
        }
        catch( Exception e ) {  // just in case...
            throw new ClubsException( "ClubIterator: Cannot create Club iterator; root cause: " + e );
        }
    }

    public boolean hasNext() 
    { 
        return more; 
    }

    public Club next() 
    {
        long   id;
        String name;
        String address;
        Date   establishedOn;
        long   founderId;
        Club   club = null;

        if( more ) {

            try {
                id = rs.getLong( 1 );
                name = rs.getString( 2 );
                address = rs.getString( 3 );
                establishedOn = rs.getDate( 4 );
                founderId = rs.getLong( 5 );

                more = rs.next();
            }
            catch( Exception e ) {      // just in case...
                throw new NoSuchElementException( "PersonIterator: No next Person object; root cause: " + e );
            }
            Person founder = objectModel.createPerson( null, null, null, null, null, null, null );
            founder.setId( founderId );
            try {
                club = objectModel.createClub( name, address, establishedOn, founder );
                club.setId( id );
                club.setFounderId( founderId );
            }
            catch( ClubsException ce ) {
                // safe to ignore: we explicitly set the persistent id of the founder Person object above!
            }
            
            return club;
        }
        else {
            throw new NoSuchElementException( "PersonIterator: No next Club object" );
        }
    }

    public void remove()
    {
        throw new UnsupportedOperationException();
    }

};
