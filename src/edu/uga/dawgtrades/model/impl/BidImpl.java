package edu.uga.dawgtrades.model.impl;

import java.util.Date;

import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Bid;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.RegisteredUser;

/**
 * Implementation of Bid
 * @author Justin
 *
 */

public class BidImpl extends Persistent implements Bid {
	
	private long id;
	private float amount;
	private Date date;
	private boolean isWinning;
	private Auction auction;
	private RegisteredUser registeredUser;
	
	public BidImpl (long id, float amount, Date date, boolean isWinning, Auction auction, RegisteredUser registeredUser) throws DTException {
		super(-1);
		this.id = id;
		this.amount = amount;
		this.date = date;
		this.isWinning = isWinning;
		this.auction = auction;
		this.registeredUser = registeredUser;
		
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public boolean isWinning() {
		return isWinning;
	}
	public Auction getAuction() {
		return auction;
	}
	public RegisteredUser getRegisteredUser() {
		return registeredUser;
	}
	
	public String toString() {
		return "implement later"; //TODO
	}
	
	public boolean equals(Object bid) {
		if (bid == null)
			return false;
		if (bid instanceof Bid) {
			boolean result = getAuction().equals((((Bid)bid).getAuction()));
			if (result == true)
				result = getRegisteredUser().equals((((Bid)bid).getRegisteredUser()));
			return result;
		}
		return false;		
	}
}
