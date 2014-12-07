package edu.uga.dawgtrades.model.impl;

import java.util.Date;

import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;

/**
 * Implementation of Auction
 * @author Justin
 *
 */

//NOT WORKING UNTIL USER IS WORKING

public class AuctionImpl extends Persistent implements Auction {
	
	private long itemId;
	private float minPrice;
	private Date expiration;
	
        public AuctionImpl (Item item, float minPrice, Date expiration) throws DTException {
		super(-1);
		if (item == null)
			throw new DTException("The item related to the auction is null");
		if (!item.isPersistent())
			throw new DTException("The item related to the auction is not persistent");
		this.itemId = item.getId();
		this.minPrice = minPrice;
		this.expiration = expiration;
		
	}
	
	public AuctionImpl (long itemId, float minPrice, Date expiration) {
	        super(-1);
		this.itemId = itemId;
		this.minPrice = minPrice;
		this.expiration = expiration;
	}
	
	public long getItemId() {
		return itemId;
	}
	
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	
	public float getMinPrice() {
		return minPrice;
	}
	
	public void setMinPrice(float minPrice) {
		this.minPrice = minPrice;
	}
	
	//Returns minPrice if auction is finished, 0 otherwise.
	public float getSellingPrice() {
		if (getIsClosed())
			return minPrice;
		else
			return 0;
	}
	
	
	public Date getExpiration() {
		return expiration;
	}
	
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	
	public boolean getIsClosed() {
		return (new Date()).after(expiration);
	}
	
	
	public String toString() {
		String result =  "Auction[" + getId() + "]: Item[" + getItemId() + "] MinPrice[$" + getMinPrice() + "] Expiration["
				+ getExpiration() + "] This auction is ";
				if (!getIsClosed())
					result += "not ";
				result += "closed";
				return result;
	}
	
	public boolean equals(Object auction) {
		if (auction == null)
			return false;
		if (auction instanceof Auction) {
			return (getItemId() == (((Auction)auction).getItemId()));
		}
		return false;		
	}

}
