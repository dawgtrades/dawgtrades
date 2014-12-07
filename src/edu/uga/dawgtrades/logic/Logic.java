package edu.uga.dawgtrades.logic;

import java.util.Date;
import java.util.List;

import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.Membership;
import edu.uga.dawgtrades.model.RegisteredUser;



public interface Logic
{
    //FIND ALL
	public List<RegisteredUser> findAllRegisteredUsers() throws DTException;
    public List<Auction> findAllAuctions() throws DTException;
    public List<Category> findAllCategories() throws DTException;
    public List<Item> findAllItems() throws DTException;
    
    
    //CREATE
    public long createRegisteredUser( String name, String firstName, String lastName, String password,
    		boolean isAdmin, String email, String phone, boolean canText ) throws DTException;
    public long createAttribute( long attributeTypeId, long itemId, String value ) throws DTException;
    public long createCategory( long parentId, String name ) throws DTException;
    public long createAttributeType( long categoryId, String name ) throws DTException;
    public long createAuction( long itemId, float minPrice) throws DTException;
    public long createItem( long categoryId, long userId, String identifier, String name, String description) throws DTException;
    public long createMembership( float price ) throws DTException; //Not sure on this as not in the UC's
    public long createExperienceReport(int rating, String report, long reviewerId, long reviewedId);
    public long createBid(float amount, long auctionId, long registeredUserId);
    
    //UPDATE
    public void updateCategory( long categoryId, long parentId, String name );
    public void updateMembershipPrice( float newPrice );
    public void updateRegisteredUser( long userId, String name, String firstName, String lastName, String password,
    		boolean isAdmin, String email, String phone, boolean canText );
    public void updateAttributeType( long attributeTypeId, long categoryId, String name );
    public void updateAuction( long auctionId ); // for reauction and close
    public void updateBid( long bidId, boolean isWinning ); //when a new bid is placed, old one should be updating to losing
    
    //DELETE
    public void deleteCategory( long categoryId );
    public void deleteItem( long itemId );
    public void deleteAuction( long auctionId );
    public void deleteRegisteredUser( long userId );
    public void deleteAttributeType( long attributeTypeId, long categoryId );
    public void deleteAttribute( long attributeId, long itemId );
    
    //FIND RELATED TO
    public List<Category> findSubcategoriesOfCategory( long categoryId ) throws DTException;
    public List<Item> findItemsOfCategory( long categoryId ) throws DTException;
    public List<Attribute> findAttributesOfItem( long itemId ) throws DTException;
    public List<Auction> findAuctionsOfUser( long userId ) throws DTException;
    
    //TODO: Not sure about singular use cases such us finding the data about a particular auction.
    //We may or may not be able to do this already without adding to the logic layer.
    
    //Emails a link
    public void resetPassword( long userId, String email);
    
    
    //TODO: Not sure about pay membership
    //TODO: The system timer use cases. HOW are we implementing these?? Chron job to run a deamon might work. Logout already in session.
    
    
}

