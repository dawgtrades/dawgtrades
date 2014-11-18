package edu.uga.dawgtrades.persist;

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
import edu.uga.dawgtrades.model.RegisteredUser;

/**
 * Persistent interface
 * @author Justin
 *
 */

public interface Persistence {
	
	public void saveAttribute(Attribute attribute) throws DTException;
	public Iterator<Attribute> restoreAttribute(Attribute attribute) throws DTException;
	public void deleteAttribute(Attribute attribute) throws DTException;
	
	public void saveAttributeType(AttributeType attributeType) throws DTException;
	public AttributeType restoreAttributeType(AttributeType attributeType) throws DTException;
	public void deleteAttributeType(AttributeType attributeType) throws DTException;
	
	public void saveAuction(Auction auction) throws DTException;
	public Iterator<Auction> restoreAuction(Auction auction) throws DTException;
	public void deleteAuction(Auction auction) throws DTException;
	
	public void saveBid(Bid bid) throws DTException;
	public Iterator<Bid> restoreBid(Bid bid) throws DTException;
	public void deleteBid(Bid bid) throws DTException;
	
	public void saveCategory(Category category) throws DTException;
	public Iterator<Category> restoreCategory(Category category) throws DTException;
	public void deleteCategory(Category category) throws DTException;
	
	public void saveExperienceReport(ExperienceReport experienceReport) throws DTException;
	public Iterator<ExperienceReport> restoreExperienceReport(ExperienceReport experienceReport) throws DTException;
	public void deleteExperienceReport(ExperienceReport experienceReport) throws DTException;
	
	public void saveItem(Item item) throws DTException;
	public Iterator<Item> restoreItem(Item item) throws DTException;
	public void deleteItem(Item item) throws DTException;
	
	public void saveMembership(Membership membership) throws DTException;
	public Membership restoreMembership(Membership membership) throws DTException;
	public void deleteMembership(Membership membership) throws DTException;
	
	public void saveRegisteredUser(RegisteredUser registeredUser) throws DTException;
	public Iterator<RegisteredUser> restoreRegisteredUser(RegisteredUser registeredUser) throws DTException;
	public void deleteRegisteredUser(RegisteredUser registeredUser) throws DTException;
	
	//user
	public Iterator<Item> restoreItemsOwned(RegisteredUser registeredUser) throws DTException;
	
	//item
	public RegisteredUser restoreOwner(Item item) throws DTException;
	public Category restoreCategoryOfItem(Item item) throws DTException;
	public Iterator<Attribute> restoreAttributes(Item item) throws DTException;
	public Auction restoreAuctionForItem(Item item) throws DTException;
	
	//category
	public Iterator<Item> restoreItemsInCategory(Category category) throws DTException;
	public Iterator<AttributeType> restoreAttributeTypes(Category category) throws DTException;
	public Iterator<Category> restoreChildren(Category category) throws DTException;
	public Category restoreParent(Category category) throws DTException;
	
	//attribute
	public AttributeType restoreTypeOfAttribute(Attribute attribute) throws DTException;
	public Item restoreItemWithAttribute(Attribute attribute) throws DTException;
	
	//attribute type
	public Category restoreCategoryWithType(AttributeType attributeType) throws DTException;
	
	//auction
	public Item restoreItemForAuction(Auction auction) throws DTException;
	
	
	
	
	
};
