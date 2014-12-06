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
    public void updateCategory( long categoryId, long parentId, String name );
    public void updateMembershipPrice( float newPrice );
    public void updateRegisteredUser( long userId, String name, String firstName, String lastName, String password,
    		boolean isAdmin, String email, String phone, boolean canText );
    public void updateAttributeType( long attributeTypeId, String name );
    public void updateAuction( long auctionId, boolean isClosed ); // for reauction and close
    public void updateBid( long bidId, boolean isWinning ); //when a new bid is placed, old one should be updating to losing
    
    //DELETE
    public void deleteCategory( long categoryId );
    public void deleteItem( long itemId );
    public void deleteAuction( long auctionId );
    public void deleteRegisteredUser( long userId );
    public void deleteAttributeType( long attributeTypeId );
    public void deleteAttribute( long itemId );
    
    //FIND RELATED TO
    public List<Category> findSubcategoriesOfCategory( long categoryId ) throws DTException;
    public List<Item> findItemsOfCategory( long categoryId ) throws DTException;
    public List<Attribute> findAttributesOfItem( long itemId ) throws DTException;
    public List<Auction> findAuctionsOfUser( long userId ) throws DTException;
    
    //TODO: Not sure about singular use cases such us finding the data about a particular auction.
    //We may or may not be able to do this already without adding to the logic layer.
    
    
    //MISC:
 
    //Emails a link
    public void resetPassword( long userId, String email);
    
    public void reauctionItem( long auctionId );
    
    //TODO: Not sure about pay membership
    //TODO: The system timer use cases. HOW are we implementing these?? Chron job to run a deamon might work. Logout already in session.
    
    
}
