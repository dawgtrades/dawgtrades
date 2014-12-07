//
// A control class to implement the 'Create ExperienceReport' use case
//
//

package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ExperienceReport;
import edu.uga.dawgtrades.model.impl;


public class CtrlCreateExperienceReport 
{
    
    private ObjectModel objectModel = null;
    
    public CtrlCreateExperienceReport( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    
    public long createExperienceReport(int rating, String report, long reviewerId, long reviewedId)    { 
        ExperienceReport               experienceReport  = null;
        ExperienceReport               modelExperienceReport  = null;
        Iterator<ExperienceReport>     experienceReportIter  = null;

        // check if the experienceReport already exists
        modelExperienceReport = objectModel.createExperienceReport();
		modelExperienceReport.setReviewer( reviewerId );
		modelExperienceReport.setReviewed( reviewedId );		
        experienceReportIter = objectModel.findExperienceReport( modelExperienceReport );
        while( experienceReportIter.hasNext() ) {
            experienceReport = experienceReportIter.next();
        }
        
        // check if the ExperienceReport actually exists, and if so, throw an exception
        if( experienceReport != null )
            throw new DTException( "An ExperienceReport with this reviewer and reviewed already exists" );
			
        // create experienceReport object
        experienceReport = objectModel.createExperienceReport(rating, report, reviewerId, reviewedId);
		experienceReport.setReviewer( reviewerId );
		experienceReport.setReviewed( reviewedId );
 
		// save experienceReport data
        objectModel.storeExperienceReport( experienceReport );

        return experienceReport.getId();
    }
}

