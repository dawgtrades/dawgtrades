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
        String name1;
        String firstName1;
        String lastName1;
        String password1;
        boolean isAdmin1;
        String email1;
        String phone1;
        boolean canText1;
        String name2;
        String firstName2;
        String lastName2;
        String password2;
        boolean isAdmin2;
        String email2;
        String phone2;
        boolean canText2;
        
        ExperienceReport experienceReport = null;
        RegisteredUser reviewer = null;
        RegisteredUser reviewed = null;
        if( more ) {
            try {
            	id = rs.getLong(1);
            	reviewerId = rs.getLong(2);
                reviewedId = rs.getLong(3);
            	rating = rs.getInt(4);
                report = rs.getString(5);
                date = rs.getDate(6);
                name1 = rs.getString(7);
                firstName1 = rs.getString(8);
                lastName1 = rs.getString(9);
                password1 = rs.getString(10);
                isAdmin1 = rs.getBoolean(11);
                email1 = rs.getString(12);
                phone1 = rs.getString(13);
                canText1 = rs.getBoolean(14);
                name2 = rs.getString(15);
                firstName2 = rs.getString(16);
                lastName2 = rs.getString(17);
                password2 = rs.getString(18);
                isAdmin2 = rs.getBoolean(19);
                email2 = rs.getString(20);
                phone2 = rs.getString(21);
                canText2 = rs.getBoolean(22);
                
                more = rs.next();
            }
            catch( Exception e ) {
                throw new NoSuchElementException( "AttributeIterator: No next AttributeObject; cause: " + e );
            }
            
            try {
                reviewer = objectModel.createRegisteredUser(name1, firstName1, lastName1, password1, isAdmin1, email1, phone1, canText1);
                reviewer.setId(reviewerId);
                reviewed = objectModel.createRegisteredUser(name2, firstName2, lastName2, password2, isAdmin2, email2, phone2, canText2);
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
