package edu.uga.dawgtrades.model.impl;

import edu.uga.dawgtrades.model.*;
import edu.uga.dawgtrades.DTException;
/**
 * Implementation of Category
 * @author William C Spivey
 */

public class CategoryImpl extends Persistent implements Category {
    private String name;
    private long parentId;

    public CategoryImpl(String name, long parentId) {
	this.name = name;
	this.parentId = parentId;
    }
    public CategoryImpl(String name, Category parent) throws DTException {
	if(parent == null)
	    throw new DTException("Parent category is null");
	if(!parent.isPersistent())
	    throw new DTException("Parent category is not persistent");
	this.name = name;
	this.parentId = parent.getId();
    }
    public CategoryImpl() {}

    public String getName() {
	return name;
    }
    public void setName(String name) {
	this.name = name;
    }
    public long getParentId() {// this probably needs to be different
	return parentId;
    }
    public void setParentId(long parentId) {
	this.parentId = parentId;
    }
}