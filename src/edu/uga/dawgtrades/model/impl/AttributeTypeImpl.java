package edu.uga.dawgtrades.model.impl;

import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;

/**
 * Implementation of AttributeType
 * @author William C Spivey
 */

public class AttributeTypeImpl extends Persistent implements AttributeType {
    private String name;
    private long categoryId;

    //proxy cons
    public AttributeTypeImpl(long categoryId, String name) {
	super(-1);
	this.categoryId = categoryId;
	this.name = name;
    }
    //new obj cons
    public AttributeTypeImpl(Category cat, String name) throws DTException{
	super(-1);
	if(cat == null)
	    throw new DTException("Category is null");
	if(!cat.isPersistent())
	    throw new DTException("Category is not persistent");
	this.categoryId = cat.getId();
	this.name = name;
    }
    public AttributeTypeImpl() {
	super(-1);
    }

    public String getName() {
	return name;
    }
    public void setName(String name) {
	this.name = name;
    }
    public long getCategoryId() {// need db stuff here
	return categoryId;
    }
    public void setCategoryId(long categoryId) {
	this.categoryId = categoryId;
    }
    
    public String toString() {
		return "AttributeType[" + getId() + "]: Category[" + getCategoryId() + "] Name[" + getName() + "]";
	}
}