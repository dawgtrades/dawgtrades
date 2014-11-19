package edu.uga.dawgtrades.model.impl;

import java.util.Date;
import java.util.Iterator;

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
	Auction auc = new AuctionImpl(-1, 0, 0, null, false);
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
	Bid bid = null;
	try {
		bid = new BidImpl(0, null, false, null, null);
	} catch (DTException e) {
		e.printStackTrace();
	}
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
	ExperienceReport erep = null;
	try {
		erep = new ExperienceReportImpl(0, null, null, null, null);
	} catch (DTException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
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
	Item item = new ItemImpl(-1, -1, null, null, null);
	item.setId(-1);
	return item;
    }
    @Override
    public Item createItem(Category category, RegisteredUser user, String identifier, String name, String description) throws DTException {
	Item item = new ItemImpl(user, category, identifier, name, description);
	return item;
    }
    @Override
    public Membership createMembership() {
	Membership member = null;
	try {
		member = new MembershipImpl(0, null);
	} catch (DTException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	member.setId(-1);
	return member;
    }
    @Override
    public Membership createMembership(float price, Date date) throws DTException {
	Membership member = new MembershipImpl(price, date);
	return member;
    }
    @Override
    public RegisteredUser createRegisteredUser() {
	RegisteredUser user = null;
	try {
		user = new RegisteredUserImpl(null, null, null, null, false, null, null, false);
	} catch (DTException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	user.setId(-1);
	return user;
    }
    @Override
    public RegisteredUser createRegisteredUser(String name, String firstName, String lastName, String password, boolean isAdmin, String email, String phone, boolean canText) throws DTException {
	RegisteredUser user = new RegisteredUserImpl(name, firstName, lastName, password, isAdmin, email, phone, canText);
	return user;
    }

    @Override
    public void deleteAttribute(Attribute attribute) throws DTException {
	pers.deleteAttribute(attribute);
    }
    @Override
    public void deleteAttributeType(AttributeType attributeType) throws DTException {
	pers.deleteAttributeType(attributeType);
    }
    @Override
    public void deleteAuction(Auction auction) throws DTException {
	pers.deleteAuction(auction);
    }
    @Override
    public void deleteBid(Bid bid) throws DTException {
	pers.deleteBid(bid);
    }
    @Override
    public void deleteCategory(Category category) throws DTException {
	pers.deleteCategory(category);
    }
    @Override
    public void deleteExperienceReport(ExperienceReport experienceReport) throws DTException {
	pers.deleteExperienceReport(experienceReport);
    }
    @Override
    public void deleteItem(Item item) throws DTException {
	pers.deleteItem(item);
    }
    @Override
    public void deleteRegisteredUser(RegisteredUser registeredUser) throws DTException {
	pers.deleteRegisteredUser(registeredUser);
    }

    public Iterator<Auction> findAuction(Auction modelAuction) throws DTException {
	return pers.restoreAuction(modelAuction);
    }
    public Iterator<Bid> findBid(Bid modelBid) throws DTException {
	return pers.restoreBid(modelBid);
    }
    public Iterator<Category> findCategory(Category modelCategory) throws DTException {
	return pers.restoreCategory(modelCategory);
    }
    public Iterator<ExperienceReport> findExperienceReport(ExperienceReport modelExperienceReport) throws DTException {
	return pers.restoreExperienceReport(modelExperienceReport);
    }
    public Iterator<Item> findItem(Item modelItem) throws DTException {
	return pers.restoreItem(modelItem);
    }
    public Membership findMembership(Membership modelMembership) throws DTException {//needs to be different?
	return pers.restoreMembership(modelMembership);
    }
    public Iterator<RegisteredUser> findRegisteredUser(RegisteredUser modelRegisteredUser) throws DTException {
	return pers.restoreRegisteredUser(modelRegisteredUser);
    }
    public Iterator<Attribute> getAttribute(Item item) throws DTException {
    	return pers.restoreAttributes(item);
    }
    
    public java.util.Iterator<AttributeType> getAttributeType( Category category ) throws DTException {
    	return pers.restoreAttributeTypes(category);
    }
    
    public Category getCategory( Item item ) throws DTException {
    	return pers.restoreCategoryOfItem(item);
    }
    
    public java.util.Iterator<Item> getItem( Category category ) throws DTException {
    	return pers.restoreItemsInCategory(category);
    }
    
    public Item getItem( Attribute attribute ) throws DTException {
    	return pers.restoreItemWithAttribute(attribute);
    }
    
    public RegisteredUser getRegisteredUser( Item item ) throws DTException {
    	return pers.restoreOwner(item);
    }
    
    public java.util.Iterator<Item> getItem( RegisteredUser registeredUser ) throws DTException {
    	return pers.restoreItemsOwned(registeredUser);
    }
    
    public Item getItem( Auction auction ) throws DTException {
    	return pers.restoreItemForAuction(auction);
    }
    
    public Auction getAuction( Item item ) throws DTException {
    	return pers.restoreAuctionForItem(item);
    }
    
    public AttributeType getAttributeType( Attribute attribute ) throws DTException {
    	return pers.restoreTypeOfAttribute(attribute);
    }
	@Override
	public void storeCategory(Category category) throws DTException {
		pers.saveCategory(category);
		
	}
	@Override
	public void storeAttributeType(AttributeType attributeType)
			throws DTException {
		pers.saveAttributeType(attributeType);
		
	}
	@Override
	public void storeItem(Item item) throws DTException {
		pers.saveItem(item);
		
	}
	@Override
	public void storeAttribute(Attribute attribute) throws DTException {
		pers.saveAttribute(attribute);
		
	}
	@Override
	public void storeAuction(Auction auction) throws DTException {
		pers.saveAuction(auction);
	}
	@Override
	public void storeRegisteredUser(RegisteredUser registeredUser)
			throws DTException {
		pers.saveRegisteredUser(registeredUser);
	}
	@Override
	public void storeBid(Bid bid) throws DTException {
		pers.saveBid(bid);
		
	}
	@Override
	public void storeExperienceReport(ExperienceReport experienceReport)
			throws DTException {
		pers.saveExperienceReport(experienceReport);
		
	}
	@Override
	public Membership findMembership() throws DTException {
		
		return pers.
	}
	@Override
	public void storeMembership(Membership membership) throws DTException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Category getParent(Category category) throws DTException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Iterator<Category> getChild(Category category) throws DTException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Category getCategory(AttributeType attributeType) throws DTException {
		// TODO Auto-generated method stub
		return null;
	}

}