package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.util.Iterator;

import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Bid;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ExperienceReport;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.Membership;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.persist.Persistence;

/**
 * Implementation of persistent interface
 * @author Justin
 *
 */

public class PersistenceImpl implements Persistence {
	
	//private AttributeManager attributeManager = null;
    private AttributeTypeManager attributeTypeManager = null;
    private AuctionManager auctionManager = null;
    private BidManager bidManager = null;
    private CategoryManager categoryManager = null;
    private ExperienceReportManager experienceReportManager = null;
    private ItemManager itemManager = null;
    private MembershipManager membershipManager = null;
    private RegisteredUserManager registeredUserManager = null;

    public PersistenceImpl( Connection conn, ObjectModel objectModel )
    {
        //attributeManager = new AttributeManager( conn, objectModel );
        attributeTypeManager = new AttributeTypeManager( conn, objectModel );
        auctionManager = new AuctionManager( conn, objectModel );
        bidManager = new BidManager( conn, objectModel );
        categoryManager = new CategoryManager( conn, objectModel );
        experienceReportManager = new ExperienceReportManager( conn, objectModel );
        itemManager = new ItemManager( conn, objectModel );
        membershipManager = new MembershipManager( conn, objectModel );
        registeredUserManager = new RegisteredUserManager( conn, objectModel );
    }
    
    public void saveAttribute(Attribute attribute) throws DTException {
    	attributeManager.save(attribute);
    }
	public Iterator<Attribute> restoreAttribute(Attribute attribute) throws DTException {
		return attributeManager.restore(attribute);
	}
	public void deleteAttribute(Attribute attribute) throws DTException {
		attributeManager.delete(attribute);
	}
	
	public void saveAttributeType(AttributeType attributeType) throws DTException {
		attributeTypeManager.save(attributeType);
	}
	public Iterator<AttributeType> restoreAttributeType(AttributeType attributeType) throws DTException {
		return attributeTypeManager.restore(attributeType);
	}
	public void deleteAttributeType(AttributeType attributeType) throws DTException {
		attributeTypeManager.delete(attributeType);
	}
	
	public void saveAuction(Auction auction) throws DTException {
		auctionManager.save(auction);
	}
	public Iterator<Auction> restoreAuction(Auction auction) throws DTException {
		return auctionManager.restore(auction);
	}
	public void deleteAuction(Auction auction) throws DTException {
		auctionManager.delete(auction);
	}
	
	public void saveBid(Bid bid) throws DTException {
		bidManager.save(bid);
	}
	public Iterator<Bid> restoreBid(Bid bid) throws DTException {
		return bidManager.restore(bid);
	}
	public void deleteBid(Bid bid) throws DTException {
		bidManager.delete(bid);
	}
	
	public void saveCategory(Category category) throws DTException {
		categoryManager.save(category);
	}
	public Iterator<Category> restoreCategory(Category category) throws DTException {
		return categoryManager.restore(category);
	}
	public void deleteCategory(Category category) throws DTException {
		categoryManager.delete(category);
	}
	
	public void saveExperienceReport(ExperienceReport experienceReport) throws DTException {
		experienceReportManager.save(experienceReport);
	}
	public Iterator<ExperienceReport> restoreExperienceReport(ExperienceReport experienceReport) throws DTException {
		return experienceReportManager.restore(experienceReport);
	}
	public void deleteExperienceReport(ExperienceReport experienceReport) throws DTException {
		experienceReportManager.delete(experienceReport);
	}
	
	public void saveItem(Item item) throws DTException {
		itemManager.save(item);
	}
	public Iterator<Item> restoreItem(Item item) throws DTException {
		return itemManager.restore(item);
	}
	public void deleteItem(Item item) throws DTException {
		itemManager.delete(item);
	}
	
	public void saveMembership(Membership membership) throws DTException {
		membershipManager.save(membership);
	}
	public Membership restoreMembership() throws DTException {
		return membershipManager.restore();
	}
	public void deleteMembership(Membership membership) throws DTException {
		membershipManager.delete(membership);
	}
	
	public void saveRegisteredUser(RegisteredUser registeredUser) throws DTException {
		registeredUserManager.save(registeredUser);
	}
	public Iterator<RegisteredUser> restoreRegisteredUser(RegisteredUser registeredUser) throws DTException {
		return registeredUserManager.restore(registeredUser);
	}
	public void deleteRegisteredUser(RegisteredUser registeredUser) throws DTException {
		registeredUserManager.delete(registeredUser);
	}
	
	//user
	public Iterator<Item> restoreItemsOwned(RegisteredUser registeredUser) throws DTException {
		return registeredUserManager.restoreItemsOwned(registeredUser);
	}
		
	//item
	public RegisteredUser restoreOwner(Item item) throws DTException {
		return itemManager.restoreOwner(item);
	}
	public Category restoreCategoryOfItem(Item item) throws DTException {
		return itemManager.restoreCategoryOfItem(item);
	}
	public Iterator<Attribute> restoreAttributes(Item item) throws DTException {
		return itemManager.restoreAttributes(item);
	}
	public Auction restoreAuctionForItem(Item item) throws DTException {
		return itemManager.restoreAuctionForItem(item);
	}
		
	//category
	public Iterator<Item> restoreItemsInCategory(Category category) throws DTException {
		return categoryManager.restoreItemsInCategory(category);
	}
	public Iterator<AttributeType> restoreAttributeTypes(Category category) throws DTException {
		return categoryManager.restoreAttributeTypes(category);
	}
	public Iterator<Category> restoreChildren(Category category) throws DTException {
		return categoryManager.restoreChildren(category);
	}
	public Category restoreParent(Category category) throws DTException {
		return categoryManager.restoreParent(category);
	}
		
	
	public AttributeType restoreTypeOfAttribute(Attribute attribute) throws DTException {
		attributeManager.restoreTypeOfAttribute(attribute);
	}
	public Item restoreItemWithAttribute(Attribute attribute) throws DTException {
		attributeManager.restoreTypeOfAttribute(attribute);
	}
		
	//attribute type
	public Category restoreCategoryWithType(AttributeType attributeType) throws DTException {
		attributeTypeManager.restoreCategoryWithType(attributeType);
	}
	
	//auction
	public Item restoreItemForAuction(Auction auction) throws DTException {
		return auctionManager.restoreItemForAuction(auction);
	}
	
	
}
