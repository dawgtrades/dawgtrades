package edu.uga.dawgtrades.persist.impl;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;

/**
 * Iterator for Auctions. 
 * @author Justin
 *
 */

public class AuctionIterator implements Iterator<Auction> {
	
	private ResultSet   rs = null;
    private ObjectModel objectModel = null;
    private boolean     more = false;
    
    public AuctionIterator( ResultSet rs, ObjectModel objectModel )
            throws DTException
    {
        this.rs = rs;
        this.objectModel = objectModel;
        try {
            more = rs.next();
        }
        catch( Exception e ) {  
            throw new DTException( "AuctionIterator: Cannot create Auction iterator; cause: " + e );
        }
    }

    public boolean hasNext()
    {
        return more;
    }
    
    public Auction next()
    {
    	long id;
    	long itemId;
    	float minPrice;
    	float sellingPrice;
    	Date expiration;
    	boolean isClosed;

        if( more ) {

            try {
            	//TODO: Must get item information also. Not sure on order
                id = rs.getLong( 1 );
                itemId = rs.getLong( 2 );
                minPrice = rs.getFloat( 3 );
                sellingPrice = rs.getFloat( 4 );
                expiration = rs.getDate( 5 ); //TODO: See if have to convert sql to java here
                isClosed = rs.getBoolean( 6 );

                more = rs.next();
            }
            catch( Exception e ) {
                throw new NoSuchElementException( "AuctionIterator: No next Auctionobject; cause: " + e );
            }
            
            //TODO: fix
            Auction auction = objectModel.createAuction( itemId, email, firstName, lastName, address, phone );
            auction.setId( id );

            return auction;
        }
        else {
            throw new NoSuchElementException( "AuctionIterator: No next Auction object" );
        }
    }
    
    public void remove()
    {
        throw new UnsupportedOperationException();
    }

	
}
