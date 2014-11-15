package edu.uga.dawgtrades.model;

import java.util.Date;

/**
 * This interface represents an ExperienceReport filed by a RegisteredUser (reviewer) on a different 
 * RegisteredUser (reviewed).  It has a collection of methods to read/write ExperienceReport's attributes.
 * It also extends the Persistable interface, since objects of this class will be stored in the persistent data store.
 * 
 *  @author Krys J. Kochut
 *
 */
public interface ExperienceReport 
    extends Persistable
{
    /**
     * Return the rating in this ExperienceReport.
     * @return the rating value
     */
    int getRating();
    
    /**
     * Set the new rating for this ExperienceReport.
     * @param rating the new rating, which must be a value 1 (worst) through 5 (best)
     * @throws DTException if the new value is illegal
     */
    void setRating( int rating ) throws DTException;
    
    /**
     * Return the report (comments) of this ExperienceReport.
     * @return the report comments
     */
    String getReport();
    
    /**
     * Set the report (comments) of this ExperienceReport.
     * @param report the new report
     */
    void setReport( String report );
    
    /**
     * Return the date when this ExperienceReport has been created.
     * @return the date of the report
     */
    Date getDate();
    
    /**
     * Set the new date of this ExperienceReport.
     * @param date the new date of the report
     */
    void setDate( Date date );
    
    /**
     * Return the RegisteredUser who created this ExperienceReport (the reviewer).
     * @return the reviewer of this report
     */
    RegisteredUser getReviewer();
    
    /**
     * Set the reviewer of this ExperienceReport.
     * @param reviewer the new reviewer
     */
    void setReviewer( RegisteredUser reviewer );
    
    /**
     * Return the RegisteredUser who is the subject of this ExperienceReport (the reviewed).
     * @return the reviewed (the subject) of this report
     */
    RegisteredUser getReviewed();
    
    /**
     * Set the RegisteredUser who is the subject of this ExperienceReport (the reviewed).
     * @param reviewed the reviewed (the subject) of this report
     */
    void setReviewed( RegisteredUser reviewed );
}
