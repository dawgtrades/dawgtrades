package edu.uga.dawgtrades.model.impl;

import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.RegisteredUser;

/**
 * Implementation of Item
 * @author Justin
 *
 */

//NOT WORKING UNTIL USER IS WORKING

public class ItemImpl extends Persistent implements Item {
	
	private long ownerId;
	private long categoryId;
	private String identifier;
	private String name;
	private String description;
	
	public ItemImpl (RegisteredUser owner, Category category, String identifier,
			String name, String description) throws DTException {
		super(-1);
		if (owner == null)
		    throw new DTException("The owner is null");
		//if (!owner.isPersistent())
		//	throw new DTException("The owner is not persistent");
		if (category == null)
			throw new DTException("The category is null");
	        //if (!category.isPersistent())
		//	throw new DTException("The category is not persistent");
		this.ownerId = owner.getId();
		this.categoryId = category.getId();
		this.identifier = identifier;
		this.name = name;
		this.description = description;
		
	}
	
	public ItemImpl (long ownerId, long categoryId, String identifier, String name, String description) {
		super (-1);
		this.ownerId = ownerId;
		this.categoryId = categoryId;
		this.identifier = identifier;
		this.name = name;
		this.description = description;
	}
	
	public long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
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
		return "Item[" + getId() + "]: Owner[" + getOwnerId() + "] Category[" + getCategoryId() + "] " + getIdentifier() + " " + getName() + " " + getDescription();
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
