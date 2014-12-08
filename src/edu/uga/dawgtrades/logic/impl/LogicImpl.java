package edu.uga.dawgtrades.logic.impl;

import java.util.Date;
import java.util.List;

import edu.uga.dawgtrades.logic.Logic;
import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ExperienceReport;
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
        return ctrlCreateItem.createItem( categoryId, userId, identifier, name, description );
    }
    public long createMembership( float price ) throws DTException {
    	CtrlCreateMembership ctrlCreateMembership = new CtrlCreateMembership( objectModel );
        return ctrlCreateMembership.createMembership( price );
    }
    public long createExperienceReport(int rating, String report, long reviewerId, long reviewedId) throws DTException {
    	CtrlCreateExperienceReport ctrlCreateExperienceReport = new CtrlCreateExperienceReport( objectModel );
        return ctrlCreateExperienceReport.createExperienceReport( rating, report, reviewerId, reviewedId );
    }
    public long createBid(float amount, long auctionId, long registeredUserId) throws DTException {
    	CtrlCreateBid ctrlCreateBid = new CtrlCreateBid( objectModel );
        return ctrlCreateBid.createBid( amount, auctionId, registeredUserId );
    }
    
    //UPDATE
    public void updateCategory( long categoryId, long parentId, String name ) throws DTException {
    	CtrlUpdateCategory ctrlUpdateCategory = new CtrlUpdateCategory( objectModel );
    	ctrlUpdateCategory.updateCategory( categoryId, parentId, name);
    }
    public void updateMembershipPrice( float newPrice ) throws DTException {
    	CtrlUpdateMembershipPrice ctrlUpdateMembershipPrice = new CtrlUpdateMembershipPrice( objectModel );
    	ctrlUpdateMembershipPrice.updateMembershipPrice( newPrice );
    }
    public void updateRegisteredUser( long userId, String name, String firstName, String lastName, String password,
    		boolean isAdmin, String email, String phone, boolean canText ) throws DTException {
    	CtrlUpdateRegisteredUser ctrlUpdateRegisteredUser = new CtrlUpdateRegisteredUser( objectModel );
    	ctrlUpdateRegisteredUser.updateRegisteredUser( userId, name, firstName, lastName, password,
        		isAdmin, email, phone, canText);
    }
    public void updateAuction( long auctionId ) throws DTException {
    	CtrlUpdateAuction ctrlUpdateAuction = new CtrlUpdateAuction( objectModel );
    	ctrlUpdateAuction.updateAuction( auctionId );
    }
    
    //DELETE
    public void deleteCategory( long categoryId ) throws DTException {
    	CtrlDeleteCategory ctrlDeleteCategory = new CtrlDeleteCategory( objectModel );
    	ctrlDeleteCategory.deleteCategory( categoryId );
    }
    public void deleteItem( long itemId ) throws DTException {
    	CtrlDeleteItem ctrlDeleteItem = new CtrlDeleteItem( objectModel );
    	ctrlDeleteItem.deleteItem( itemId );
    }
    public void deleteAuction( long auctionId ) throws DTException {
    	CtrlDeleteAuction ctrlDeleteAuction = new CtrlDeleteAuction( objectModel );
    	ctrlDeleteAuction.deleteAuction( auctionId );
    }
    public void deleteRegisteredUser( long userId ) throws DTException {
    	CtrlDeleteRegisteredUser ctrlDeleteRegisteredUser = new CtrlDeleteRegisteredUser( objectModel );
    	ctrlDeleteRegisteredUser.deleteRegisteredUser( userId );
    }
    public void deleteAttributeTypes( long categoryId ) throws DTException {
    	CtrlDeleteAttributeTypes ctrlDeleteAttributeTypes = new CtrlDeleteAttributeTypes( objectModel );
    	ctrlDeleteAttributeTypes.deleteAttributeTypes(  categoryId );
    }
    public void deleteAttributes( long itemId ) throws DTException {
    	CtrlDeleteAttributes ctrlDeleteAttributes = new CtrlDeleteAttributes( objectModel );
    	ctrlDeleteAttributes.deleteAttributes(  itemId );
    }
    
    //FIND RELATED TO
    public List<Category> findSubcategoriesOfCategory( long categoryId ) throws DTException {
    	CtrlFindSubcategoriesOfCategory ctrlFindSubcategoriesOfCategory = new CtrlFindSubcategoriesOfCategory( objectModel );
    	return ctrlFindSubcategoriesOfCategory.findSubcategoriesOfCategory( categoryId );
    }
    public List<Item> findItemsOfCategory( long categoryId ) throws DTException {
    	CtrlFindItemsOfCategory ctrlFindItemsOfCategory = new CtrlFindItemsOfCategory( objectModel );
    	return ctrlFindItemsOfCategory.findItemsOfCategory( categoryId );
    }
    public List<Attribute> findAttributesOfItem( long itemId ) throws DTException {
    	CtrlFindAttributesOfItem ctrlFindAttributesOfItem = new CtrlFindAttributesOfItem( objectModel );
    	return ctrlFindAttributesOfItem.findAttributesOfItem( itemId );
    }
    public List<Auction> findAuctionsOfUser( long userId ) throws DTException {
    	CtrlFindAuctionsOfUser ctrlFindAuctionsOfUser = new CtrlFindAuctionsOfUser( objectModel );
    	return ctrlFindAuctionsOfUser.findAuctionsOfUser( userId );
    }

	@Override
	public List<ExperienceReport> findExperienceReportsOfUser(long userId)
			throws DTException {
		CtrlFindExperienceReportsOfUser ctrlFindExperienceReportsOfUser = new CtrlFindExperienceReportsOfUser( objectModel );
    	return ctrlFindExperienceReportsOfUser.findExperienceReportsOfUser( userId );
	}

    
    //TODO: Not sure about singular use cases such us finding the data about a particular auction.
    //We may or may not be able to do this already without adding to the logic layer.
    
 
    /**TODO: Later if we have time
    public void resetPassword( long userId, String email) {
    	CtrlResetPassword ctrlResetPassword = new CtrlResetPassword( objectModel );
    	ctrlResetPassword.resetPassword( userId, email );
    }
    **/
    
    //TODO: Not sure about pay membership    
    
}
