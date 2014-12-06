package edu.uga.dawgtrades.logic;

import java.util.Date;
import java.util.List;

import edu.uga.dawgtrades.logic.Logic;
import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.Membership;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;



public class LogicImpl implements Logic
{
	private ObjectModel objectModel = null;

    public LogicImpl( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
	
    //FIND ALL
	public List<RegisteredUser> findAllRegisteredUsers() throws DTException {
		CtrlFindAllRegisteredUsers ctrlFindAllRegisteredUsers = new CtrlFindAllRegisteredUsers( objectModel );
        return ctrlFindAllRegisteredUsers.findAllRegisteredUsers();
	}
    public List<Auction> findAllAuctions() throws DTException {
    	CtrlFindAllAuctions ctrlFindAllAuctions = new CtrlFindAllAuctions( objectModel );
        return ctrlFindAllAuctions.findAllAuctions();
    }
    public List<Category> findAllCategories() throws DTException {
    	CtrlFindAllCategories ctrlFindAllCategories = new CtrlFindAllCategories( objectModel );
        return ctrlFindAllCategories.findAllCategories();
    }
    public List<Item> findAllItems() throws DTException {
    	CtrlFindAllItems ctrlFindAllItems = new CtrlFindAllItems( objectModel );
        return ctrlFindAllItems.findAllItems();
    }
    
    
    //CREATE
    public long createRegisteredUser( String name, String firstName, String lastName, String password,
    		boolean isAdmin, String email, String phone, boolean canText ) throws DTException {
    	CtrlCreateRegisteredUser ctrlCreateRegisteredUser = new CtrlCreateRegisteredUser( objectModel );
        return ctrlCreateRegisteredUser.createRegisteredUser( name, firstName, lastName, password,
        		isAdmin, email, phone, canText  );
    }
    public long createAttribute( long attributeTypeId, long itemId, String value ) throws DTException {
    	CtrlCreateAttribute ctrlCreateAttribute = new CtrlCreateAttribute( objectModel );
        return ctrlCreateAttribute.createAttribute( attributeTypeId, itemId, value  );
    }
    public long createCategory( long parentId, String name ) throws DTException {
    	CtrlCreateCategory ctrlCreateCategory = new CtrlCreateCategory( objectModel );
        return ctrlCreateCategory.createCategory( parentId, name );
    }
    public long createAttributeType( long categoryId, String name ) throws DTException {
    	CtrlCreateAttributeType ctrlCreateAttributeType = new CtrlCreateAttributeType( objectModel );
        return ctrlCreateAttributeType.createAttributeType( categoryId, name);
    }
    public long createAuction( long itemId, float minPrice) throws DTException {
    	CtrlCreateAuction ctrlCreateAuction = new CtrlCreateAuction( objectModel );
        return ctrlCreateAuction.createAuction( itemId, minPrice );
    }
    public long createItem( long categoryId, long userId, String identifier, String name, String description) throws DTException {
    	CtrlCreateItem ctrlCreateItem = new CtrlCreateItem( objectModel );
        return ctrlCreateItem.createItem( categoryId, userId, identifier );
    }
    public long createMembership( float price ) throws DTException {
    	CtrlCreateMembership ctrlCreateMembership = new CtrlCreateMembership( objectModel );
        return ctrlCreateMembership.createMembership( price );
    }
    public long createExperienceReport(int rating, String report, long reviewerId, long reviewedId) {
    	CtrlCreateExperienceReport ctrlCreateExperienceReport = new CtrlCreateExperienceReport( objectModel );
        return ctrlCreateExperienceReport.createExperienceReport( rating, report, reviewerId, reviewedId );
    }
    public long createBid(float amount, long auctionId, long registeredUserId) {
    	CtrlCreateBid ctrlCreateBid = new CtrlCreateBid( objectModel );
        return ctrlCreateBid.createBid( amount, auctionId, registeredUserId );
    }
    
    //UPDATE
    public void updateCategory( long categoryId, long parentId, String name ) {
    	CtrlUpdateCategory ctrlUpdateCategory = new CtrlUpdateCategory( objectModel );
    	return ctrlUpdateCategory.updateCategory( categoryId, parentId, name);
    }
    public void updateMembershipPrice( float newPrice ) {
    	CtrlUpdateMembershipPrice ctrlUpdateMembershipPrice = new CtrlUpdateMembershipPrice( objectModel );
    	return ctrlUpdateMembershipPrice.updateMembershipPrice( newPrice );
    }
    public void updateRegisteredUser( long userId, String name, String firstName, String lastName, String password,
    		boolean isAdmin, String email, String phone, boolean canText ) {
    	CtrlUpdateRegisteredUser ctrlUpdateRegisteredUser = new CtrlUpdateRegisteredUser( objectModel );
    	return ctrlUpdateRegisteredUser.updateRegisteredUser( userId, name, firstName, lastName, password,
        		isAdmin, email, phone, canText);
    }
    public void updateAttributeType( long attributeTypeId, String name ) {
    	CtrlUpdateAttributeType ctrlUpdateAttributeType = new CtrlUpdateAttributeType( objectModel );
    	return ctrlUpdateAttributeType.updateAttributeType( attributeTypeId, name );
    }
    public void updateAuction( long auctionId, boolean isClosed ) {
    	CtrlUpdateAuction ctrlUpdateAuction = new CtrlUpdateAuction( objectModel );
    	return ctrlUpdateAuction.updateAuction( auctionId, isClosed);
    }
    public void updateBid( long bidId, boolean isWinning ) {
    	CtrlUpdateBid ctrlUpdateBid = new CtrlUpdateBid( objectModel );
    	return ctrlUpdateBid.updateBid( bidId, isWinning);
    }
    
    //DELETE
    public void deleteCategory( long categoryId ) {
    	CtrlDeleteCategory ctrlDeleteCategory = new CtrlDeleteCategory( objectModel );
    	return ctrlDeleteCategory.deleteCategory( categoryId );
    }
    public void deleteItem( long itemId ) {
    	CtrlDeleteItem ctrlDeleteItem = new CtrlDeleteItem( objectModel );
    	return ctrlDeleteItem.deleteItem( itemId );
    }
    public void deleteAuction( long auctionId ) {
    	CtrlDeleteAuction ctrlDeleteAuction = new CtrlDeleteAuction( objectModel );
    	return ctrlDeleteAuction.deleteAuction( auctionId );
    }
    public void deleteRegisteredUser( long userId ) {
    	CtrlDeleteRegisteredUser ctrlDeleteRegisteredUser = new CtrlDeleteRegisteredUser( objectModel );
    	return ctrlDeleteRegisteredUser.deleteRegisteredUser( userId );
    }
    public void deleteAttributeType( long attributeTypeId ) {
    	CtrlDeleteAttributeType ctrlDeleteAttributeType = new CtrlDeleteAttributeType( objectModel );
    	return ctrlDeleteAttributeType.deleteAttributeType( attributeTypeId );
    }
    public void deleteAttribute( long attributeId ) {
    	CtrlDeleteAttribute ctrlDeleteAttribute = new CtrlDeleteAttribute( objectModel );
    	return ctrlDeleteAttribute.deleteAttribute( attributeId );
    }
    
    //FIND RELATED TO
    public List<Category> findSubcategoriesOfCategory( long categoryId ) throws DTException {
    	CtrlFindSubcategoriesOfCategory ctrlFindSubcategoriesOfCategory = new CtrlFindSubcategoriesOfCategory( objectModel );
    	return ctrlFindSubcategoriesOfCategory.findSubcategoriesOfCategory( categoryId );
    }
    public List<Item> findItemsOfCategory( long categoryId ) throws DTException {
    	CtrlFindSubcategoriesOfCategory ctrlFindSubcategoriesOfCategory = new CtrlFindSubcategoriesOfCategory( objectModel );
    	return ctrlFindSubcategoriesOfCategory.findSubcategoriesOfCategory( categoryId );
    }
    public List<Attribute> findAttributesOfItem( long itemId ) throws DTException {
    	CtrlFindSubcategoriesOfCategory ctrlFindSubcategoriesOfCategory = new CtrlFindSubcategoriesOfCategory( objectModel );
    	return ctrlFindSubcategoriesOfCategory.findSubcategoriesOfCategory( itemId );
    }
    public List<Auction> findAuctionsOfUser( long userId ) throws DTException {
    	CtrlFindSubcategoriesOfCategory ctrlFindSubcategoriesOfCategory = new CtrlFindSubcategoriesOfCategory( objectModel );
    	return ctrlFindSubcategoriesOfCategory.findSubcategoriesOfCategory( userId );
    }
    
    //TODO: Not sure about singular use cases such us finding the data about a particular auction.
    //We may or may not be able to do this already without adding to the logic layer.
    
    
    //MISC:
 
    //Emails a link
    public void resetPassword( long userId, String email) {
    	CtrlResetPassword ctrlResetPassword = new CtrlResetPassword( objectModel );
    	return ctrlResetPassword.resetPassword( userId, email );
    }
    
    
    //TODO: Not sure about pay membership
    //TODO: The system timer use cases. HOW are we implementing these?? Chron job to run a deamon might work. Logout already in session.
    
    
}
