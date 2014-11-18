package edu.uga.dawgtrades.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ExperienceReportImpl;


class experienceReportManager
{
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
                stmt.setString( 1, report.getReviewer() );
            else
                throw new DTException( "ExperienceReportManager.save: can't save a report: reviewer undefined" );
				
            if( report.getReviewed() != null )
                stmt.setString( 2, report.getReviewed() );
            else
                throw new DTException( "ExperienceReportManager.save: can't save a report: reviewed undefined");				
            if( report.getRating() != null )
                stmt.setString( 3, report.getRating() );
            else
                throw new DTException( "ExperienceReportManager.save: can't save a report: rating undefined" );

            if( report.getReport() != null )
                stmt.setString( 4, report.getReport() );
            else
                throw new DTException( "ExperienceReportManager.save: can't save a report: report undefined" );

            if( report.getDate() != null )
                stmt.setString( 5, report.getDate() );
            else
                throw new DTException( "ExperienceReportManager.save: can't save a report: date undefined" );				
							
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

    public Iterator<experienceReport> restore( report modelReport ) 
            throws DTException
    {
        String       selectReportSql = "select reviewer_id, reviewed_id, rating, report, rating_date from experience_report"; 
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );

        condition.setLength( 0 );
        
        // form the query based on the given report object instance
        query.append( selectReportSql );
        
        if( modelReport != null ) {
            if( modelReport.getId() >= 0 ) // id is unique, so it is sufficient to get a report
                query.append( " where report_id = " + modelReport.getId() );
            else {
                if( modelReport.getReviewer() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " reviewer_id = '" + modelReport.getReviewer() + "'" );
                }
				
                if( modelReport.getReviewed() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " reviewed_id = '" + modelReport.getReviewed() + "'" );
                }				
				
                if( modelReport.getRating() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " rating = '" + modelReport.getRating() + "'" );
                }
				
                if( modelReport.getReport() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " report = '" + modelReport.getReport() + "'" );
                }       
				
                if( modelReport.getDate() != null )
                    condition.append( " rating_date = '" + modelReport.getDate() + "'" );                				
					
                if( condition.length() > 0 ) {
                    query.append(  " where " );
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
