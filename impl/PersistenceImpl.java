package edu.uga.clubs.persistence.impl;

import java.sql.Connection;
import java.util.Iterator;

import edu.uga.clubs.ClubsException;
import edu.uga.clubs.model.Club;
import edu.uga.clubs.model.Membership;
import edu.uga.clubs.model.ObjectModel;
import edu.uga.clubs.model.Person;
import edu.uga.clubs.persistence.Persistence;

public class PersistenceImpl 
    implements Persistence
{
    private PersonManager personManager = null;
    private ClubManager clubManager = null;
    private MembershipManager membershipManager = null;
    
    public PersistenceImpl( Connection conn, ObjectModel objectModel )
    {
        personManager = new PersonManager( conn, objectModel );
        clubManager = new ClubManager( conn, objectModel );
        membershipManager = new MembershipManager( conn, objectModel );
    }
    
    @Override
    public void saveClub(Club club) throws ClubsException
    {
        clubManager.save( club );
    }

    @Override
    public Iterator<Club> restoreClub(Club club) throws ClubsException
    {
        return clubManager.restore( club );
    }

    @Override
    public void deleteClub(Club club) throws ClubsException
    {
        clubManager.delete( club );
    }

    @Override
    public void savePerson(Person person) throws ClubsException
    {
        personManager.save( person );
    }

    @Override
    public Iterator<Person> restorePerson(Person person) 
            throws ClubsException
    {
        return personManager.restore( person );
    }

    @Override
    public void deletePerson(Person person) throws ClubsException
    {
        personManager.delete( person );
    }

    @Override
    public void saveMembership(Membership membership) throws ClubsException
    {
        membershipManager.save( membership );
    }

    @Override
    public Iterator<Membership> restoreMembership(Membership membership)
            throws ClubsException
    {
        return membershipManager.restore( membership );
    }

    @Override
    public void deleteMembership(Membership membership) throws ClubsException
    {
        membershipManager.delete( membership );
    }

    @Override
    public Person restoreEstablishedBy(Club club) throws ClubsException
    {
        return clubManager.restoreEstablishedBy( club );
    }

    @Override
    public Iterator<Club> restoreEstablishedBy(Person person)
            throws ClubsException
    {
        return personManager.restoreEstablishedBy( person );
    }
}
