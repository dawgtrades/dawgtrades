package edu.uga.dawgtrades.model.impl;

import java.util.Date;

import edu.uga.dawgtrades.model.*;
import edu.uga.dawgtrades.persist.Persistence;

public class ObjectModelImpl implements ObjectModel {
    Persistence pers = null;

    public ObjectModelImpl() {
	this.pers = null;
    }
    public ObjectModelImpl(Persistence pers) {
	this.pers = pers;
    }

    @Override
    public Attribute createAttribute() {
	Attribute attr = new AttributeImpl(-1, -1, null);
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
	AttributeType attrt = new AttributeTypeImpl(-1, null);
	attrt.setId(-1);
	return attrt;
    }
    @Override
    public AttributeType createAttributeType(Category cat, String name) {
	AttributeType attrt = new AttributeTypeImpl(cat, name);
	return attrt;
    }
    @Override
    public Auction createAuction() {
	Auction auc = new AuctionImpl(-1, null, null, null, null);
	auc.setId(-1);
	return auc;
    }
    @Override
    public Auction createAuction(Item item, float minPrice, Date expiration) {
	Auction auc = new AuctionImpl(item, minPrice, minPrice, expiration, false);
	return auc;
    }
    public Bid createBid() {
	Bid bid = new BidImpl(null, null, null, null, null);
	bid.setId(-1);
	return bid;
    }
    public Bid createBid(Auction auction, RegisteredUser user, float price) {
	Bid bid = new BidImpl(price, new Date(), true, auction, user);
	return bid;
    }
    public Category createCategory() {
	Category cat = new CategoryImpl(null, -1);
	cat.setId(-1);
	return cat;
    }
    public Category createCategory(Category parent, String name) {
	Category cat = new CategoryImpl(name, parent);
	return cat;
    }
    public ExperienceReport createExperienceReport() {
	ExperienceReport erep = new ExperienceReportImpl(
}