package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.ExperienceReport;
import edu.uga.dawgtrades.model.RegisteredUser;

/**
 * A control class to find the experience reports of a particular user
 * @author Joey
 *
 */

public class CtrlFindExperienceReportsOfUser {
    
    private ObjectModel objectModel = null;
    
    public CtrlFindExperienceReportsOfUser( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
	
    public List<Auction> findExperienceReportsOfUser( long userId ) throws DTException
    {
        ExperienceReport                 modelExperienceReport = null;
        RegisteredUser modelRegisteredUser = null;
        RegisteredUser user = null;
        Iterator<ExperienceReport>       experienceReportIter = null;
        Iterator<RegisteredUser> userIter = null;
		Auction              modelAuction = null;
        List<Auction>        auctions  = null;		
        
        auctions = new LinkedList<Auction>();


        // retrieve the RegisteredUser
        modelRegisteredUser = objectModel.createRegisteredUser();
        modelRegisteredUser.setId(userId);
        userIter = objectModel.findRegisteredUser( modelRegisteredUser );
        while( userIter.hasNext() ) {
            user = userIter.next();
        }
        
        // make sure the RegisteredUser actually exists
        if( user == null )
            throw new DTException( "The RegisteredUser does not exist" );
        
        //retrieve the ExperienceReports of the user
        experienceReportIter = objectModel.g
        
        while (experienceReportIter.hasNext()) {
        	modelExperienceReport = experienceReportIter.next();
        	modelAuction = objectModel.getAuction(modelExperienceReport);
        	auctions.add(modelAuction);
        }
        
        return auctions;
    }
}
