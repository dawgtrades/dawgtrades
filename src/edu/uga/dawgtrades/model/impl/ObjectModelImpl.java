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
    public Attribute createAttribute(AttributeType attrType, Item item, String val) throws DTException {
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
    public AttributeType createAttributeType(Category cat, String name) throws DTException {
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
    public Auction createAuction(Item item, float minPrice, Date expiration) throws DTException {
	Auction auc = new AuctionImpl(item, minPrice, minPrice, expiration, false);
	return auc;
    }
    @Override
    public Bid createBid() {
	Bid bid = new BidImpl(null, null, null, null, null);
	bid.setId(-1);
	return bid;
    }
    @Override
    public Bid createBid(Auction auction, RegisteredUser user, float price) throws DTException {
	Bid bid = new BidImpl(price, new Date(), true, auction, user);
	return bid;
    }
    @Override
    public Category createCategory() {
	Category cat = new CategoryImpl(null, -1);
	cat.setId(-1);
	return cat;
    }
    @Override
    public Category createCategory(Category parent, String name) throws DTException {
	Category cat = new CategoryImpl(name, parent);
	return cat;
    }
    @Override
    public ExperienceReport createExperienceReport() {
	ExperienceReport erep = new ExperienceReportImpl(null, null, null, -1, -1);
	erep.setId(-1);
	return erep;
    }
    @Override
    public ExperienceReport createExperienceReport(RegisteredUser reviewer, RegisteredUser reviewed, int rating, String report, Date date) throws DTException {
	ExperienceReport erep = new ExperienceReportImpl(rating, report, date, reviewer, reviewed);
	return erep;
    }
    @Override
    public Item createItem() {
	Item item = new item(-1, -1, null, null, null);
	item.setId(-1);
	return item;
    }
    @Override
    public Item createItem(Category category, RegisteredUser user, String identifier, String name, String description) throws DTException {
	Item item = new item(user, category, identifier, name, description);
	return item;
    }
    @Override
    public Membership createMembership() {
	Membership member = MembershipImpl(null, null);
	member.setId(-1);
	return member;
    }
    @Override
    public Membership createMembership(float price, Date date) throws DTException {
	Membership member = MembershipImpl(price, date);
	return member;
    }
    @Override
    public RegisteredUser createRegisteredUser() {
	RegisteredUser user = RegisteredUserImpl(null, null, null, null, null, null, null, null);
	user.setId(-1);
	return user;
    }
    @Override
    public RegisteredUser createRegisteredUser(String name, String firstName, String lastName, String password, boolean isAdmin, String email, String phone, boolean canText) throws DTException {
	RegisteredUser user = RegisteredUserImpl(name, firstName, lastName, password, isAdmin, email, phone, canText);
	return user;
    }

    @Override
    public void deleteAttribute(Attribute attribute) throws DTException {
	persistence.deleteAttribute(attribute);
    }
    @Override
    public void deleteAttributeType(AttributeType attributeType) throws DTException {
	persistence.deleteAttributeType(attributeType);
    }
    @Override
    public void deleteAuction(Auction auction) throws DTException {
	persistence.deleteAuction(auction);
    }
    @Override
    public void deleteBid(Bid bid) throws DTException {
	persistence.deleteBid(bid);
    }
    @Override
    public void deleteCategory(Category category) throws DTException {
	persistence.deleteCategory(category);
    }
    @Override
    public void deleteExperienceReport(ExperienceReport experienceReport) throws DTException {
	persistence.deleteExperienceReport(experienceReport);
    }
    @Override
    public void deleteItem(Item item) throws DTException {
	persistence.deleteItem(item);
    }
    @Override
    public void deleteRegisteredUser(RegisteredUser registeredUser) throws DTException {
	persistence.deleteRegisteredUser(registeredUser);
    }

    public Iterator<Auction> findAuction(Auction modelAuction) throws DTException {
	return persistence.restoreAuction(modelAuction);
    }
    public Iterator<Bid> findBid(Bid modelBid) throws DTException {
	return persistence.restoreBid(modelBid);
    }
    public Iterator<Category> findCategory(Category modelCategory) throws DTException {
	return persistence.restoreCategory(modelCategory);
    }
    public Iterator<ExperienceReport> findExperienceReport(ExperienceReport modelExperienceReport) throws DTException {
	return persistence.restoreExperienceReport(modelExperienceReport);
    }
    public Iterator<Item> findItem(Item modelItem) {
	return persistence.restoreItem(modelItem);
    }
    public Membership findMembership() {//needs to be different?
	return persistence.restoreMembership();
    }
    public Iterator<RegisteredUser> findRegisteredUser(RegisteredUser modelRegisteredUser) throws DTException {
	return persistence.restoreRegisteredUser(modelRegisteredUser);
    }
    public Iterator<Attribute> getAttribute(Item item) throws DTException {

}