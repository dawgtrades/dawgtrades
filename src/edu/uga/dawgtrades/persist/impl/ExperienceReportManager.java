package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ExperienceReport;
import edu.uga.dawgtrades.model.ObjectModel;


public class ExperienceReportManager {
    private ObjectModel objectModel = null;
    private Connection  conn = null;
    
    public ExperienceReportManager( Connection conn, ObjectModel objectModel )
    {
        this.conn = conn;
        this.objectModel = objectModel;
    }
    
    public void save( ExperienceReport report ) 
            throws DTException
    {
        String               insertReportSql = "insert into experience_report ( reviewer_id, reviewed_id, rating, report, rating_date) values ( ?, ?, ?, ?, ? )";              
        String               updateReportSql = "update experience_report set reviewer_id = ?, reviewed_id = ?, rating = ?, report = ?, rating_date = ? where report_id = ?";              
        PreparedStatement    stmt;
        int                  inscnt;
        long                 reportId;
        
        try {
            
            if( !report.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertReportSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateReportSql );

            if( report.getReviewer() != null )
                stmt.setLong( 1, report.getReviewer().getId() );
            else
                throw new DTException( "ExperienceReportManager.save: can't save a report: reviewer undefined" );
				
            if( report.getReviewed() != null )
                stmt.setLong( 2, report.getReviewed().getId() );
            else
                throw new DTException( "ExperienceReportManager.save: can't save a report: reviewed undefined");				
            
            stmt.setLong( 3, report.getRating() );

            if( report.getReport() != null )
                stmt.setString( 4, report.getReport() );
            else
                throw new DTException( "ExperienceReportManager.save: can't save a report: report undefined" );

            if( report.getDate() != null ) {
                java.util.Date jDate = report.getDate();
                java.sql.Date sDate = new java.sql.Date( jDate.getTime() );
                stmt.setDate( 5, sDate );
            }
            else
                stmt.setNull(5, java.sql.Types.DATE);
			
            if(report.isPersistent())
            	stmt.setLong(6, report.getId());
            
            inscnt = stmt.executeUpdate();

            if( !report.isPersistent() ) {
                // in case this this object is stored for the first time,
                // we need to establish its persistent identifier (primary key)
                if( inscnt == 1 ) {
                    String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) { // statement returned a result
                        // retrieve the result
                        ResultSet r = stmt.getResultSet();
                        // we will use only the first row!
                        while( r.next() ) {
                            // retrieve the last insert auto_increment value
                            reportId = r.getLong( 1 );
                            if( reportId > 0 )
                                report.setId( reportId ); // set this report's db id (proxy object)
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new DTException( "ExperienceReportManager.save: failed to save a report" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new DTException( "ExperienceReportManager.save: failed to save a report: " + e );
        }
    }

    public Iterator<ExperienceReport> restore( ExperienceReport modelReport ) 
            throws DTException
    {
        String       selectReportSql = "select er.report_id, er.reviewer_id, er.reviewed_id, er.rating, er.report, er.rating_date, "
        		+ "ur.uname, ur.first_name, ur.last_name, ur.upassword, ur.is_admin, ur.email, ur.phone, "
        		+ "ur.can_text, ud.uname, ud.first_name, ud.last_name, ud.upassword, ud.is_admin, ud.email, ud.phone, "
        		+ "ud.can_text from experience_report er, registered_user ur, registered_user ud where ur.user_id = er.reviewer_id and ud.user_id = er.reviewed_id"; 
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );

        condition.setLength( 0 );
        
        // form the query based on the given report object instance
        query.append( selectReportSql );
        
        if( modelReport != null ) {
            if( modelReport.getId() >= 0 ) // id is unique, so it is sufficient to get a report
                query.append( " and report_id = " + modelReport.getId() );
            else {
                if( modelReport.getReviewer() != null ) {
                    condition.append( " and reviewer_id = '" + modelReport.getReviewer().getId() + "'" );
                }
				
                if( modelReport.getReviewed() != null ) {
                    condition.append( " and reviewed_id = '" + modelReport.getReviewed().getId() + "'" );
                }				
				
                condition.append( " and rating = " + modelReport.getRating());
                
				
                if( modelReport.getReport() != null ) {
                    condition.append( " and report = '" + modelReport.getReport() + "'" );
                }       
				
                //MAY NEED TO FIX DATE CONVERSION
                if( modelReport.getDate() != null ) {
                    condition.append( " and rating_date = '" + modelReport.getDate() + "'" );  
                }
					
                if( condition.length() > 0 ) {
                    query.append( condition );
                }
            }
        }
        
        try {

            stmt = conn.createStatement();

            // retrieve the persistent report object
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet r = stmt.getResultSet();
                return new ExperienceReportIterator( r, objectModel );
            }
        }
        catch( Exception e ) {      // just in case...
            throw new DTException( "reportManager.restore: Could not restore persistent report object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new DTException( "reportManager.restore: Could not restore persistent report object" );
    }
    
    
    
    public void delete( ExperienceReport report ) 
            throws DTException
    {
        String               deleteReportSql = "delete from experience_report where report_id = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
        
        // form the query based on the given report object instance
        if( !report.isPersistent() ) // is the report object persistent?  If not, nothing to actually delete
            return;
        
        try {
            
            //DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
            //DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
            stmt = (PreparedStatement) conn.prepareStatement( deleteReportSql );
            
            stmt.setLong( 1, report.getId() );
            
            inscnt = stmt.executeUpdate();
            
            if( inscnt == 0 ) {
                throw new DTException( "ExperienceReportManager.delete: failed to delete this report" );
            }
        }
        catch( SQLException e ) {
            throw new DTException( "ExperienceReportManager.delete: failed to delete this Report: " + e.getMessage() );
        }
    }
}
