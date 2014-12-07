package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Bid;
import edu.uga.dawgtrades.model.ObjectModel;

/**
 * Bid Manager
 * @author Justin
 *
 */

class BidManager
{
    private ObjectModel objectModel = null;
    private Connection  conn = null;
    
    public BidManager( Connection conn, ObjectModel objectModel )
    {
        this.conn = conn;
        this.objectModel = objectModel;
    }
    
    public void save( Bid bid ) 
            throws DTException
    {
        String               insertBidSql = "insert into bid ( user_id, auction_id, bid_value, bid_date) values ( ?, ?, ?, ?, ? )";              
        String               updateBidSql = "update bid set user_id = ?, auction_id = ?, bid_value = ?, bid_date= ? where bid_id = ?";              
        PreparedStatement    stmt;
        int                  numUpdated;
        long                 bidId;
        
        try {
            
            if( !bid.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertBidSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateBidSql );

            if( bid.getRegisteredUser() != null )
                stmt.setLong( 1, bid.getRegisteredUser().getId() );
            else
                throw new DTException( "BidManager.save: can't save a bid: user undefined" );
				
            if( bid.getAuction() != null )
                stmt.setLong( 2, bid.getAuction().getId() );
            else
                throw new DTException( "BidManager.save: can't save a bid: auction undefined");				
            
            stmt.setFloat( 3, bid.getAmount() );

            if( bid.getDate() != null ) {
                java.util.Date jDate = bid.getDate();
                java.sql.Date sDate = new java.sql.Date( jDate.getTime() );
                stmt.setDate( 4, sDate );
            }
            else
                stmt.setNull(4, java.sql.Types.DATE);
            
            if( bid.isPersistent() )
                stmt.setLong( 5, bid.getId() );
            
            numUpdated = stmt.executeUpdate();

            if( !bid.isPersistent() ) {
                // in case this this object is stored for the first time,
                // we need to establish its persistent identifier (primary key)
                if( numUpdated == 1 ) {
                    String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) { // statement returned a result
                        // retrieve the result
                        ResultSet r = stmt.getResultSet();
                        // we will use only the first row!
                        while( r.next() ) {
                            // retrieve the last insert auto_increment value
                            bidId = r.getLong( 1 );
                            if( bidId > 0 )
                                bid.setId( bidId ); // set this bid's db id (proxy object)
                        }
                    }
                }
            }
            else {
                if( numUpdated < 1 )
                    throw new DTException( "BidManager.save: failed to save a bid" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new DTException( "BidManager.save: failed to save a bid: " + e );
        }
    }

    public Iterator<Bid> restore( Bid modelBid ) 
            throws DTException
    {
        String       selectBidSql = "select b.bid_id, b.user_id, b.auction_id, b.bid_value, b.bid_date, a.item_id, a.min_price, "
        		+ "a.expiration_dt, u.uname, u.first_name, u.last_name, u.upassword, u.is_admin, u.email, u.phone, "
        		+ "u.can_text from bid b, auction a, registered_user u where a.auction_id = b.auction_id and b.user_id = u.user_id"; 
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );

        condition.setLength( 0 );
        
        // form the query based on the given Bid object instance
        query.append( selectBidSql );
        
        if( modelBid != null ) {
            if( modelBid.getId() >= 0 ) // id is unique, so it is sufficient to get a Bid
                query.append( " and bid_id = " + modelBid.getId() );
            else {

                    if( modelBid.getAuction() != null ) {
                        condition.append( " and b.auction_id = " + modelBid.getAuction().getId() );
                    }

                    if( modelBid.getRegisteredUser() != null ) {
                        condition.append( " and b.user_id = " + modelBid.getRegisteredUser().getId() );
                    }

                if( condition.length() > 0 ) {
                    query.append( condition );
                }
            }
        }
        
        try {

            stmt = conn.createStatement();

            // retrieve the persistent Bid object
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet r = stmt.getResultSet();
                return new BidIterator( r, objectModel );
            }
        }
        catch( Exception e ) {      // just in case...
            throw new DTException( "BidManager.restore: Could not restore persistent Bid object; Root cause: " + e );
        }
        
        // if we get to this point, it's an error
        throw new DTException( "BidManager.restore: Could not restore persistent Bid object" );
    }
    
    
    
    public void delete( Bid bid ) 
            throws DTException
    {
        String               deleteBidSql = "delete from bid where bid_id = ?";              
        PreparedStatement    stmt = null;
        int                  numUpdated;
        
        // form the query based on the given bid object instance
        if( !bid.isPersistent() ) // is the bid object persistent?  If not, nothing to actually delete
            return;
        
        try {
            
            //DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
            //DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
            stmt = (PreparedStatement) conn.prepareStatement( deleteBidSql );
            
            stmt.setLong( 1, bid.getId() );
            
            numUpdated = stmt.executeUpdate();
            
            if( numUpdated == 0 ) {
                throw new DTException( "BidManager.delete: failed to delete this bid" );
            }
        }
        catch( SQLException e ) {
            throw new DTException( "BidManager.delete: failed to delete this bid: " + e.getMessage() );
        }
    }
}