package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.ObjectModel;

/**
* ******** NOT FINISHED AT ALL YET
* Auction Manager
* @author Justin
*
*/

public class AuctionManager {
	
	private ObjectModel objectModel = null;
    private Connection  conn = null;

    public AuctionManager( Connection conn, ObjectModel objectModel )
    {
        this.conn = conn;
        this.objectModel = objectModel;
    }

    public void save( Auction auction )
            throws DTException
    {
        String               insertAuctionSql = "insert into auction ( item_id, status, high_bid, expiration_dt, min_price) values ( ?, ?, ?, ?, ?)";
        String               updateAuctionSql = "update auction set item_id = ?, status = ?, high_bid = ?, expiration_dt = ?, min_price = ? where auction_id = ?";
        PreparedStatement	 stmt = null;
        int                  numUpdated;
        long                 auctionId;
       
        try {

            if( !auction.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertAuctionSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateAuctionSql );
            
            if( auction.getItemId() >= 0)
            	stm.setLong(1, auction.getItemId());
            else
            	throw new DTException( "AuctionManager.save: can't save a Auction: item id undefined" );
            
            stmt.setBoolean( 2, auction.getIsClosed() );

            if( auction.getSellingPrice() != null )
                stmt.setString( 2, auction.getDescription() );
            else
                throw new DTException( "AuctionManager.save: can't save a Auction: description undefined" );
            
            if(auction.getIdentifier() != null )
            	stmt.setString(3, auction.getIdentifier());
            else
            	throw new DTException("AuctionManager.save: can't save a Auction: identifier undefined");
            
            if( auction.getCategoryId() > 0 ) {
            	stmt.setLong( 4,  auction.getCategoryId() );
            }
            else {
            	throw new DTException( "AuctionManager.save: can't save a Auction: category undefined" );
            }
            if ( auction.getOwnerId() > 0) {
            stmt.setLong( 5, auction.getOwnerId() );
            }
            else {
            	throw new DTException( "AuctionManager.save: can't save a Auction: owner undefined" );
            }

            if( auction.isPersistent() )
                stmt.setLong( 6, auction.getId() );

            numUpdated = stmt.executeUpdate();

            if( !auction.isPersistent() ) {
                // in case this this object is stored for the first time,
                // we need to establish its persistent identifier (primary key)
                if( numUpdated >= 1 ) {
                    String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) { 
                        // retrieve the result
                        ResultSet r = stmt.getResultSet();
                        // we will use only the first row!
                        while( r.next() ) {
                            // retrieve the last insert auto_increment value
                            auctionId = r.getLong( 1 );
                            if( auctionId > 0 )
                                auction.setId( auctionId ); // set this auction's db id (proxy object)
                        }
                    }
                    }
                }
                else {
                    if( numUpdated < 1 )
                        throw new DTException( "AuctionManager.save: failed to save a Auction" );
                }
            }
            catch( SQLException e ) {
                e.printStackTrace();
                throw new DTException( "AuctionManager.save: failed to save a Auction: " + e );
            }
        }

        public Iterator<Auction> restore( Auction modelAuction )
                throws DTException
        {
            String       selectAuctionSql = "select name, description, identifier, category_id, owner_id";
            Statement    stmt = null;
            StringBuffer query = new StringBuffer( 100 );
            StringBuffer condition = new StringBuffer( 100 );

            condition.setLength( 0 );

            // form the query based on the given Auction object instance
            query.append( selectAuctionSql );

            if( modelAuction != null ) {
                if( modelAuction.getId() >= 0 ) // id is unique, so it is sufficient to get a Auction
                    query.append( " where id = " + modelAuction.getId() );
                else if( modelAuction.getName() != null ) // userName is unique, so it is sufficient to get a Auction
                    query.append( " where name = '" + modelAuction.getName() + "'" );
                else {
                    if( modelAuction.getDescription() != null )
                        condition.append( " description = '" + modelAuction.getDescription() + "'" );
                    if( modelAuction.getIdentifier() != null ) {
                    	if( condition.length() > 0 )
                            condition.append( " and" );
                        condition.append( " identifier = '" + modelAuction.getDescription() + "'" );
                    }
                    if( modelAuction.getCategoryId() > 0 ) {
                        if( condition.length() > 0 )
                            condition.append( " and" );
                        condition.append( " categoryId = '" + modelAuction.getCategoryId() + "'" );
                    }

                    if( modelAuction.getOwnerId() > 0 ) {
                        if( condition.length() > 0 )
                            condition.append( " and" );
                        condition.append( " ownerId = '" + modelAuction.getOwnerId() + "'" );
                    }

                    if( condition.length() > 0 ) {
                        query.append(  " where " );
                        query.append( condition );
                    }
                }
            }

            try {

                stmt = conn.createStatement();

                // retrieve the persistent Auction object
                //
                if( stmt.execute( query.toString() ) ) { // statement returned a result
                    ResultSet r = stmt.getResultSet();
                    return new AuctionIterator( r, objectModel );
                }
            }
            catch( Exception e ) {      // just in case...
                throw new DTException( "AuctionManager.restore: Could not restore persistent Auction object; Root cause: " + e );
            }

            // if we get to this point, it's an error
            throw new DTException( "AuctionManager.restore: Could not restore persistent Auction object" );
        }
        
        public void delete( Auction auction )
                throws DTException
        {
            String               deleteAuctionSql = "delete from auction where id = ?";
            PreparedStatement    stmt = null;
            int                  numUpdated;

            // form the query based on the given Auction object instance
            if( !auction.isPersistent() ) // is the Auction object persistent?  If not, nothing to actually delete
                return;

            try {

                //DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
                //DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
                stmt = (PreparedStatement) conn.prepareStatement( deleteAuctionSql );

                stmt.setLong( 1, auction.getId() );

                numUpdated = stmt.executeUpdate();

                if( numUpdated == 0 ) {
                    throw new DTException( "AuctionManager.delete: failed to delete this Auction" );
                }
            }
            catch( SQLException e ) {
                throw new DTException( "AuctionManager.delete: failed to delete this Auction: " + e.getMessage() );
            }
        }                               
}
