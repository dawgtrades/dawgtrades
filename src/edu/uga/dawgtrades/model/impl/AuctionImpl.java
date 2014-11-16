package edu.uga.dawgtrades.model.impl;

import java.util.Date;

import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Persistable;
import edu.uga.dawgtrades.model.Item;

/**
 * Implementation of Auction
 * @author Justin
 *
 */

//NOT WORKING UNTIL USER IS WORKING

public class AuctionImpl extends Persistent implements Auction {
	
	private long id;
	private long itemId;
	private float minPrice;
	private float sellingPrice;
	private Date expiration;
	private boolean isClosed;
	
	public AuctionImpl (long id, Item item, float minPrice, float sellingPrice, Date expiration, boolean isClosed) throws DTException {
		super(-1);
		if (item == null)
			throw new DTException("The item related to the auction is null");
		if (!item.isPersistent())
			throw new DTException("The item related to the auction is not persistent");
		this.id = id;
		this.id = item.getId();
		this.minPrice = minPrice;
		this.sellingPrice = sellingPrice;
		this.expiration = expiration;
		this.isClosed = isClosed;
		
	}
	
	public AuctionImpl (long id, long itemId, float minPrice, float sellingPrice, Date expiration, boolean isClosed) {
		this.id = id;
		this.itemId = itemId;
		this.minPrice = minPrice;
		this.sellingPrice = sellingPrice;
		this.expiration = expiration;
		this.isClosed = isClosed;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
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
	
	public float getSellingPrice() {
		return sellingPrice;
	}
	
	
	public Date getExpiration() {
		return expiration;
	}
	
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	
	public boolean getIsClosed() {
		return isClosed;
	}
	
	
	public String toString() {
		return "Auction[" + getId() + "]: Item[" + getItemId() + "] MinPrice[$" + getMinPrice() + "] SellingPrice[$" + getSellingPrice() + "] Expiration["
				+ getExpiration() + "] This item ";
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
