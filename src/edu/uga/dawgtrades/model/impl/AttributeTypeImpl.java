package edu.uga.dawgtrades.model.impl;

/**
 * Implementation of AttributeType
 * @author William C Spivey
 */

public class AttributeTypeImpl implements AttributeType {
    private String name;
    private long categoryId;

    public AttributeTypeImpl(String name, long categoryId) {
	this.name = name;
	this.categoryId = categoryId;
    }
    public AttributeTypeImpl() {}

    public String getName() {
	return name;
    }
    public setName(String name) {
	this.name = name;
    }
    public long getCategoryId() {// need db stuff here
	return categoryId;
    }
    public void setCategoryId(long categoryId) {
	this.categoryId = categoryId;
    }
}