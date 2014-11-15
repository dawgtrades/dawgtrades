package edu.uga.dawgtrades.model.impl;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.RegisteredUser;

//NOT WORKING UNTIL USER IS WORKING

public class ItemImpl extends Persistent implements Item {
	
	private long id;
	private long ownerId;
	private String name;
	private String description;
	
	public ItemImpl (long id, RegisteredUser owner, String name, String description) throws DTException {
		super(-1);
		if (owner == null)
			throw new DTException("The owner is null");
		if (!owner.isPersistent())
			throw new DTException("The owner is not persistent");
		this.id = id;
		this.id = owner.getId();
		this.name = name;
		this.description = description;
		
	}
	
	public ItemImpl (long id, long ownerId, String name, String description) {
		super (-1);
		this.id = id;
		this.ownerId = ownerId;
		this.name = name;
		this.description = description;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString() {
		return "Item[" + getId() + "]: Owner[" + getOwnerId() + "] " + getName() + " " + getDescription();
	}
	
	public boolean equals(Object item) {
		if (item == null)
			return false;
		if (item instanceof Item) {
			boolean result = getName().equals(((Item)item).getName());
			if (result == true)
				result = (getOwnerId() == ((Item)item).getOwnerId());
			return result;
		}
		return false;		
	}
}
