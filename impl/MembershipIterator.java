package edu.uga.clubs.persistence.impl;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.clubs.ClubsException;
import edu.uga.clubs.model.Club;
import edu.uga.clubs.model.Membership;
import edu.uga.clubs.model.ObjectModel;
import edu.uga.clubs.model.Person;


public class MembershipIterator
    implements Iterator<Membership>
{
    private ResultSet   rs = null;
    private ObjectModel modelFactory = null;
    private boolean     more = false;

    // these two will be used to create a new object
    //
    public MembershipIterator( ResultSet rs, ObjectModel modelFactory )
            throws ClubsException
    { 
        this.rs = rs;
        this.modelFactory = modelFactory;
        try {
            more = rs.next();
        }
        catch( Exception e ) {  // just in case...
            throw new ClubsException( "MembershipIterator: Cannot create an iterator; root cause: " + e );
        }
    }

    public boolean hasNext() 
    { 
        return more; 
    }

    public Membership next() 
    {
        long   id;
        Date   joined;
        long   personid;
        String userName;
        String password;
        String email;
        String firstName;
        String lastName;
        String personaddress;
        String phone;
        long   clubid;
        String clubname;
        String clubaddress;
        Date   establishedOn;
        long   clubFounderId;
        Person person = null;
        Person founder = null;

        if( more ) {

            try {
                clubname = rs.getString( 1 );
                clubaddress = rs.getString( 2 );
                establishedOn = rs.getDate( 3 );
                clubFounderId = rs.getLong( 4 );
                id = rs.getLong( 5 );
                clubid = rs.getLong( 6 );
                personid = rs.getLong( 7 );
                joined = rs.getDate( 8 );
                userName = rs.getString( 9 );
                password = rs.getString( 10 );
                email = rs.getString( 11 );
                firstName = rs.getString( 12 );
                lastName = rs.getString( 13 );
                personaddress = rs.getString( 14 );
                phone = rs.getString( 15 );

                more = rs.next();
            }
            catch( Exception e ) {      // just in case...
                throw new NoSuchElementException( "PersonIterator: No next Person object; root cause: " + e );
            }
            
            person = modelFactory.createPerson( userName, password, email, firstName, lastName, personaddress, phone );
            person.setId( personid );
            founder = modelFactory.createPerson( null, null, null, null, null, null, null );
            founder.setId( clubFounderId );
            Club club = null;
            try {
                club = modelFactory.createClub( clubname, clubaddress, establishedOn, founder );
                club.setId( clubid );
            }
            catch( ClubsException ce ) {
                ce.printStackTrace();
                System.out.println( ce );
            }
            
            Membership membership = modelFactory.createMembership( person, club, joined );
            membership.setId( id );
            
            return membership;
        }
        else {
            throw new NoSuchElementException( "PersonIterator: No next Person object" );
        }
    }

    public void remove()
    {
        throw new UnsupportedOperationException();
    }

}
