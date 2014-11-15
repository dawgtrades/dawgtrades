package edu.uga.dawgtrades.model.impl;

import edu.uga.dawgtrades.model.Attribute;

public class AttributeImpl implements Attribute {
    private long attributeType;
    private long itemId;
    private String value;


    public AttributeImpl(long attributeId, long itemId, String value) {
	this.attributeType = attributeId;
	this.itemId = itemId;
	this.value = value;
    }
    
    public AttributeImpl() {}

    public String getValue() {
	return value;
    }
    public void setValue(String value) {
	this.value = value;
    }
    public long getItemId() {// db stuff??
	return itemId;
    }
    public void setItemId(long itemId) {
	this.itemId = itemId;
    }
    public long getAttributeType() {// ???
	return attributeType;
    }
    public void setAttributeType(long attributeId) {
	this.attributeType = attributeId;
    }
}