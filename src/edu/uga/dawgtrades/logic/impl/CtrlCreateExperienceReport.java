//
// A control class to implement the 'Create ExperienceReport' use case
//
//

package edu.uga.dawgtrades.logic.impl;

import java.util.Date;
import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ExperienceReport;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;


public class CtrlCreateExperienceReport 
{
    
    private ObjectModel objectModel = null;
    
    public CtrlCreateExperienceReport( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    
    public long createExperienceReport(int rating, String report, long reviewerId, long reviewedId) throws DTException    { 
        ExperienceReport               experienceReport  = null;
        RegisteredUser reviewer = null;
        RegisteredUser reviewed = null;
        ExperienceReport               modelExperienceReport  = null;
        RegisteredUser modelReviewer = null;
        RegisteredUser modelReviewed = null;
        Iterator<ExperienceReport>     experienceReportIter  = null;
        Iterator<RegisteredUser> userIter;
        
        // retrieve the Reviewer
        modelReviewer = objectModel.createRegisteredUser();
        modelReviewer.setId(reviewerId);
        userIter = objectModel.findRegisteredUser( modelReviewer );
        while( userIter.hasNext() ) {
            reviewer = userIter.next();
        }
        
        // make sure the Reviewer actually exists
        if( reviewer == null )
            throw new DTException( "The reviewer does not exist" );
        
        // retrieve the Reviewed
        modelReviewed = objectModel.createRegisteredUser();
        modelReviewed.setId(reviewedId);
        userIter = objectModel.findRegisteredUser( modelReviewed );
        while( userIter.hasNext() ) {
            reviewed = userIter.next();
        }
        
        // make sure the Reviewed actually exists
        if( reviewed == null )
            throw new DTException( "The reviewed does not exist" );
        
        // check if the experienceReport already exists
        modelExperienceReport = objectModel.createExperienceReport();
		modelExperienceReport.setReviewer( reviewer );
		modelExperienceReport.setReviewed( reviewed );		
        experienceReportIter = objectModel.findExperienceReport( modelExperienceReport );
        while( experienceReportIter.hasNext() ) {
            experienceReport = experienceReportIter.next();
        }
        
        // check if the ExperienceReport actually exists, and if so, throw an exception
        if( experienceReport != null )
            throw new DTException( "An ExperienceReport with this reviewer and reviewed already exists" );
			
        // create experienceReport object
        experienceReport = objectModel.createExperienceReport(reviewer, reviewed, rating, report, new Date());
 
		// save experienceReport data
        objectModel.storeExperienceReport( experienceReport );

        return experienceReport.getId();
    }
}

