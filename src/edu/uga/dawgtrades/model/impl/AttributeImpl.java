package edu.uga.dawgtrades.model.impl;

import edu.uga.dawgtrades.model.*;
import edu.uga.dawgtrades.DTException;

public class AttributeImpl extends Persistent implements Attribute {
    private long attributeType;
    private long itemId;
    private String value;

    //proxy cons
    public AttributeImpl(long attributeId, long itemId, String value) {
	super(-1);
	this.attributeType = attributeId;
	this.itemId = itemId;
	this.value = value;
    }
    //new obj cons
    public AttributeImpl(AttributeType attrt, Item item, String value) throws DTExpception {
	super(-1);
	if(attr == null)
	    throw new DTException("Attribute is null");
	if(!attr.isPersistent())
	    throw new DTException("Attribute is not persistent");
	if(item == null)
	    throw new DTException("Item is null");
	if(!item.isPersistent())
	    throw new DTException("Item is not persistent");
	this.attributeType = attrt.getId();
	this.itemId = item.getId();
	this.value = value;
    }
    public AttributeImpl() {
	super(-1);
    }

    public String getValue() {
	return value;
    }
    public void setValue(String value) {
	this.value = value;
    }
    public long getItemId() {
	return itemId;
    }
    public void setItemId(long itemId) {
	this.itemId = itemId;
    }
    public long getAttributeType() {
	return attributeType;
    }
    public void setAttributeType(long attributeId) {
	this.attributeType = attributeId;
    }
}