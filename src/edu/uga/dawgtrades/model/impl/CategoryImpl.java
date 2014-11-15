package edu.uga.dawgtrades.model.impl;

/**
 * Implementation of Category
 * @author William C Spivey
 */

public CategoryImpl
    extends Persistent
    implements Category
{
    private String name;
    private long parentId;

    public CategoryImpl(String name, long parentId) {
	this.name = name;
	this.parentId = parentId;
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