package edu.uga.dawgtrades.model.impl;

import java.util.Date;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Membership;

/**
 * Implementation of Membership
 * @author Vic Lawson
 *
 */
public class MembershipImpl extends Persistent implements Membership {
	private float price;
	private Date date;
	
	// constructor for new objects
	public MembershipImpl (float price, Date date) throws DTException {
 	    this.price = price;
	    this.date = date;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public Date getDate() {
		return date;
	}	
	public String toString() {
		return "Membership[" + getId() + "]: price[" + getPrice() + "] date[" + getDate() + "]";
	}
}
