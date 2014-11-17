package edu.uga.dawgtrades.model.impl;

import edu.uga.dawgtrades.model.*;
import edu.uga.dawgtrades.DTException;
import edu.uga.dawgtrades.persist.Persistence;

public class ObjectModelImpl implements ObjectModel {
    Persistence pers = null;

    public ObjectModelImpl() {
	this.pers = null;
    }
    public ObjectModelImpl(Persistence pers) {
	this.pers = persistence;
    }

@Override
    public Attribute createAttribute() {
	Attribute attr = new AttributeImpl();
	attr.setId(-1);
	return attr;
    }
@Override
    public Attribute createAttribute(AttributeType attrType, Item item, String val) {
	Attribute attr = new AttributeImpl(attrType, item, val);
	return attr;
    }
@Override
    public AttributeType createAttributeType() {
	AttributeType attrt = new AttributeType();
	attrt.setId(-1);
	return attrt;
    }
@Override
    public AttributeType createAttributeType(Category cat, String name) {
	AttributeType attrt = new AttributeType(cat, name);
	return attrt;
    }
    public Auction createAuction() {

}