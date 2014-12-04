package edu.uga.dawgtrades.persist.impl;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.uga.dawgtrades.model.ExperienceReport;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Persistable;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.ObjectModel;

/**
 * Iterator for AttributeTypes.
 * @author Mark Hoefer
 *
 */
public class ExperienceReportIterator implements Iterator<ExperienceReport> {
    private ResultSet rs = null;
    private ObjectModel objectModel = null;
    private boolean more = false;
    
    public ExperienceReportIterator( ResultSet rs, ObjectModel objectModel ) throws DTException {
        this.rs = rs;
        this.objectModel = objectModel;
        try {
            more = rs.next();
        }
        catch( Exception e ) {
            throw new DTException( "ExperienceReportIterator: Cannot create ExperienceReport iterator; cause: " + e );
        }
    }
    public boolean hasNext() {
        return more;
    }
    public ExperienceReport next() {
        long id;
    	int rating;
        String report;
        Date date;
        long reviewerId;
        long reviewedId;
        ExperienceReport experienceReport = null;
        RegisteredUser reviewer = null;
        RegisteredUser reviewed = null;
        if( more ) {
            try {
            	reviewerId = rs.getLong(1);
                reviewedId = rs.getLong(2);
            	rating = rs.getInt(3);
                report = rs.getString(4);
                date = rs.getDate(5);
                id = rs.getLong(6);
                more = rs.next();
            }
            catch( Exception e ) {
                throw new NoSuchElementException( "AttributeIterator: No next AttributeObject; cause: " + e );
            }
            
            try {
                reviewer = objectModel.createRegisteredUser();
                reviewer.setId(reviewerId);
                reviewed = objectModel.createRegisteredUser();
                reviewed.setId(reviewedId);
                experienceReport = objectModel.createExperienceReport(reviewer, reviewed, rating, report, date);
                experienceReport.setId(id);
            } catch (DTException e) {
                e.printStackTrace();
            }
        }
        else {
            throw new NoSuchElementException( "AttributeIterator: No next Attribute object" );
        }
        
        return experienceReport;
    }
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
