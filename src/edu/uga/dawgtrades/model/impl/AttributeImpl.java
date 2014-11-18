package edu.uga.dawgtrades.model.impl;

import edu.uga.dawgtrades.model.*;

public class AttributeImpl extends Persistent implements Attribute {
    private long attributeTypeId;
	private long itemId;
    private String value;

    //proxy cons
    public AttributeImpl(long attributeTypeId, long itemId, String value) {
	super(-1);
	this.attributeTypeId = attributeTypeId;
	this.itemId = itemId;
	this.value = value;
    }
    //new obj cons
    public AttributeImpl(AttributeType attrt, Item item, String value) throws DTException {
	super(-1);
	if(attrt == null)
	    throw new DTException("Attribute is null");
	if(!attrt.isPersistent())
	    throw new DTException("Attribute is not persistent");
	if(item == null)
	    throw new DTException("Item is null");
	if(!item.isPersistent())
	    throw new DTException("Item is not persistent");
	this.attributeTypeId = attrt.getId();
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
	return attributeTypeId;
    }
    public void setAttributeType(long attributeId) {
	this.attributeTypeId = attributeId;
    }

}