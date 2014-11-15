package edu.uga.dawgtrades.model;

/**
 * This is the interface to the Object Model subsystem.  It includes operations to create, store, 
 * retrieve, and delete all entity classes in the DawgTrades data model.  For some of the classes, 
 * certain operations are not necessary and they have been commented out.  The interface includes 
 * the operations of the factory pattern for the entity classes.
 * 
 * @author Krys J. Kochut
 *
 */
public interface ObjectModel
{
    /**
     * Create a new Category object and link it to its parent Category, if provided. The parent Category, 
     * if provided, must be a persistent object, i.e., the value returned by the isPersistent() method must be true. 
     * 
     * @param parent a parent Category; it must be persistent. If this argument is null, the new Category will be a root (top-level) category.
     * @param name a name of the new Category
     * @return a new Category object.
     * @throws DTException if there is a problem with the new object creation (e.g., the parent Category is not persistent).
     */
    public Category createCategory( Category parent, String name ) throws DTException;
    
    /**
     * Create an empty Category object.  This method can be used to create an "empty" modelCategory and also can be 
     * used to create a "shell" object by the persistence subsystem when creating a proxy object.
     *  
     */
    public Category createCategory();
    
    /**
     * Return Categories satisfying the search criterion abstracted by the modelCategory. For example, 
     * if the modelCategory object isPersistent(), only one object with the given identifier is 
     * returned in the iterator. If the modelCategory is not persistent, but has a name, the category 
     * with the same name will be returned.
     *  
     * @param modelCategory a Category object used as the search criterion;  if null is given, all categories will be retrieved
     * @return an Iterator Category objects satisfying the criteria of the argument modelCategory
     * @throws DTException if there is a problem with locating the requested Categories
     */
    public java.util.Iterator<Category> findCategory( Category modelCategory ) throws DTException;
    
    /**
     * Store a Category object in the persistent data store.  If the object is already persistent, it will be
     * updated in the data store.  Otherwise, it will be stored for the first time.
     * 
     * @param category a Category object to be stored in the persistent data store
     * @throws DTException if there is a problem with the process of storing the object in the data store
     */
    public void storeCategory( Category category ) throws DTException;
    
    /**
     * Delete a Category object. If the object is persistent, it will be removed from persistent data store.
     * 
     * @param category a Category object to be deleted
     * @throws DTException if there is a problem with the process of deleting the object
     */
    public void deleteCategory( Category category ) throws DTException;
    
    /**
     * Create a new AttributeType object and link it to a given Category object. The given Category must be 
     * a persistent object, i.e. the value returned by the isPersistent() method must be true.
     * 
     * @param category a Category to which the new AttributeType must be linked; it must be persistent
     * @param name a name of the AttributeType
     * @return a new AttributeType object
     * @throws DTException if there is a problem with the new object creation (e.g., the Category is not persistent)
     */
    public AttributeType createAttributeType( Category category, String name ) throws DTException;
 
    /**
     * Create an empty AttributeType object.  This method can be used to create an "empty" modelAttributeType and also can be 
     * used to create a "shell" object by the persistence subsystem when creating a proxy object.
     *  
     */
    public AttributeType createAttributeType();
    
    //public java.util.Iterator<AttributeType> findAttributeType( AttributeType modelAttributeType ) throws DTException;
    
    /**
     * Store an AttributeType object in the persistent data store. If the object is already persistent, 
     * it will be updated in the data store.  Otherwise, it will be stored for the first time.
     * 
     * @param attributeType an AttributeType object to be stored in the persistent data store
     * @throws DTException if there is a problem with the process of storing the object in the data store
     */
    public void storeAttributeType( AttributeType attributeType ) throws DTException;
    
    /**
     * Delete an AttributeType object.  If the object is persistent, it will be removed from persistent data store.
     * 
     * @param attributeType an AttributeType object to be deleted
     * @throws DTException if there is a problem with the process of deleting the object
     */
    public void deleteAttributeType( AttributeType attributeType ) throws DTException;

    /**
     * Create a new Item object, classified into a given Category and owned by a given RegisteredUser. 
     * The given Category and RegisteredUser objects must be persistent, i.e. the value returned 
     * by the isPersistent() method must be true for both objects.
     * 
     * @param category a Category of the new Item; it must be persistent
     * @param user a RegisteredUser owner of the new Item; it must be persistent
     * @param identifier a unique identifier of the Item
     * @param name a name of the Item
     * @param description a description of the Item
     * @return a new Item object, classified into the given category and owned by the given user
     * @throws DTException if there is a problem with the new object creation (e.g., the category or user is not persistent)
     */
    public Item createItem( Category category, RegisteredUser user, String identifier, String name, String description ) throws DTException;

    /**
     * Create an empty Item object.  This method can be used to create an "empty" modelItem and also can be 
     * used to create a "shell" object by the persistence subsystem when creating a proxy object.
     *  
     */
    public Item createItem();
 
    /**
     * Return Items satisfying the criteria of the modelItem. If the modeItem has a defined identifier (isPersistent), 
     * only the specific item is returned.  The modelItem may specify a category and/or owner identifier, in which
     * case all items in the given category and/or owned by the given user are returned.
     * 
     * @param modelItem a model item to use in locating items.  If null is given, all items will be retrieved.
     * @return an iterator of the located Item objects
     * @throws DTException if there is a problem with locating the requested items
     */
    public java.util.Iterator<Item> findItem( Item modelItem ) throws DTException;
    
    /**
     * Store an Item object in the persistent data store.  If the object is already persistent, 
     * it will be updated in the data store.  Otherwise, it will be stored for the first time.
     * 
     * @param item an Item object to be stored in the persistent data store
     * @throws DTException if there is a problem with the process of storing the object in the data store
     */
    public void storeItem( Item item ) throws DTException;
    
    /**
     * Delete an Item object.  If the object is persistent, it will be removed from persistent data store.
     * 
     * @param item an Item object to be deleted
     * @throws DTException if there is a problem with the process of deleting the object
     */
    public void deleteItem( Item item ) throws DTException;
    
    /**
     * Create a new Attribute object linked to a given AttributeType and a given Item. The AttributeType of 
     * the Attribute and the Item it describes must be persistent objects, i.e. the value returned by the 
     * isPersistent() method must be true for both. 
     * 
     * @param attributeType an AttributeType of the new Attribute object; it must be persistent
     * @param item an Item, which is described by the new Attribute; it must be persistent
     * @param value a value of the new Attribute
     * @return a new Attribute, whose type is the given AttributeType and which describes the given Item
     * @throws DTException if there is a problem with the new object creation (e.g., the attributeType or item are not persistent).
     */
    public Attribute createAttribute( AttributeType attributeType, Item item, String value ) throws DTException;
    
    /**
     * Create an empty Attribute object.  This method can be used to create a "shell" object 
     * by the persistence subsystem when creating a proxy object.
     *  
     */
    public Attribute createAttribute();

    //public Iterator<Attribute> findAttribute( Attribute modelAttribute ) throws DTException;
    
    /**
     * Store an Attribute object in the persistent data store.  If the object is already persistent, it will be
     * updated in the data store.  Otherwise, it will be stored for the first time.
     * 
     * @param attribute an Attribute object to be stored in the persistent data store
     * @throws DTException if there is a problem with the process of storing the object in the data store
     */
    public void storeAttribute( Attribute attribute ) throws DTException;
    
    /**
     * Delete an Attribute object.  If the object is persistent, it will be removed from persistent data store.
     * 
     * @param attribute an Attribute object to be deleted
     * @throws DTException if there is a problem with the process of deleting the object
     */
    public void deleteAttribute( Attribute attribute ) throws DTException;

    /**
     * Create a new Auction object and link to a given Item. The Item for which the Auction is created must be a persistent objects, i.e. the 
     * value returned by the isPersistent() method on the Item must be true. 
     * 
     * @param item an Item for which the new auction is being created; it must be persistent
     * @param minPrice a minimum price set for the new Auction
     * @param expiration a deadline for the new Auction
     * @return a new auction object linked to the item which is being auctioned
     * @throws DTException if there is a problem with the new object creation (e.g., the item is not persistent, or the minPrice is negative).
     */
    public Auction createAuction( Item item, float minPrice, java.util.Date expiration ) throws DTException;
    
    /**
     * Create an empty Auction object.  This method can be used to create an "empty" modelAuction and also can be 
     * used to create a "shell" object by the persistence subsystem when creating a proxy object.
     *  
     */
    public Auction createAuction();

    /**
     * Return Auctions satisfying the search criterion abstracted by the modelAuction.  For example, 
     * if the modelAuction object isPersistent(), only one object with the given identifier is 
     * returned in the iterator. If the modelAuction is not persistent, but has a defined expiration, the auctions 
     * with the same expiration will be returned.
     *  
     * @param modelAuction an Auction object used as the search criterion.  If null is given, all Auctions will be retrieved.
     * @return an Iterator Category objects satisfying the criteria of the argument modelCategory
     * @throws DTException if there is a problem with locating Categories
     */
    public java.util.Iterator<Auction> findAuction( Auction modelAuction ) throws DTException;
    
    /**
     * Store an Auction object in the persistent data store.  If the object is already persistent, it will be
     * updated in the data store.  Otherwise, it will be stored for the first time.
     * 
     * @param auction an Auction object to be stored in the persistent data store
     * @throws DTException if there is a problem with the process of storing the object in the data store
     */
    public void storeAuction( Auction auction ) throws DTException;
    
    /**
     * Delete an Auction object.  If the object is persistent, it will be removed from persistent data store.
     * 
     * @param auction an Auction object to be deleted
     * @throws DTException if there is a problem with the process of deleting the object
     */
    public void deleteAuction( Auction auction ) throws DTException;
    
    /**
     * Create a new RegisteredUser object. 
     *  
     * @param name a user name of the new user (login name)
     * @param firstName a first name of the user
     * @param lastName a last name of the user
     * @param password a password of the user
     * @param isAdmin an indication if the new user is an administrator
     * @param email an email address of the user
     * @param phone a phone number of the user
     * @param canText an indication if the user's phone can accept text messages
     * @return a new RegisteredUser object
     * @throws DTException if there is a problem with the new object creation.
     */
    public RegisteredUser createRegisteredUser( String name, String firstName, String lastName, String password,
                                                boolean isAdmin, String email, String phone, boolean canText) throws DTException;
    
    /**
     * Create an empty RegisteredUser object.  This method can be used to create an "empty" modelRegisteredUser 
     * and also can be used to create a "shell" object by the persistence subsystem when creating a proxy object.
     *  
     */
    public RegisteredUser createRegisteredUser();

    /**
     * Return RegisteredUsers satisfying the criteria of the modelRegisteredUser. If the modelRegisteredUser has a 
     * defined identifier (isPersistent), only the specific registeredUser is returned. The modelRegisteredUser may 
     * specify various attributes of needed RegisteredUsers.
     * 
     * @param modelRegisteredUser a model RegisteredUser to use in locating users.  If null is given, all users will be retrieved.
     * @return an iterator of the located RegisteredUser objects
     * @throws DTException if there is a problem with locating the requested user objects
     */
    public RegisteredUser findRegisteredUser( RegisteredUser modelRegisteredUser ) throws DTException;
    
    /**
     * Store a RegisteredUser object in the persistent data store. If the object is already persistent, it will be
     * updated in the data store.  Otherwise, it will be stored for the first time.
     * 
     * @param registeredUser a RegisteredUser object to be stored in the persistent data store
     * @throws DTException if there is a problem with the process of storing the object in the data store
     */
    public void storeRegisteredUser( RegisteredUser registeredUser ) throws DTException;
    
    /**
     * Delete a RegisteredUser object. If the object is persistent, it will be removed from persistent data store.
     * 
     * @param registeredUser a RegisteredUser object to be deleted
     * @throws DTException if there is a problem with the process of deleting the object
     */
    public void deleteRegisteredUser( RegisteredUser registeredUser ) throws DTException;

    /**
     * Create a new Bid object and link it to a given Auction and a given RegisteredUser. The Auction for which the Bid is 
     * made and the RegisteredUser who places the Bid must be persistent objects, i.e. the value returned by 
     * the isPersistent() method must be true for both.
     *  
     * @param auction an Auction for which the new Bid is placed; it must be persistent
     * @param user a user who places the Bid; it must be persistent
     * @param price a price of the new Bid
     * @return a new Bid object, linked to the Item of the Bid and user who placed the bid
     * @throws DTException if there is a problem with the new object creation (e.g., the item or the user are not persistent, or the price is negative).
     */
    public Bid createBid( Auction auction, RegisteredUser user, float price ) throws DTException;
    
    /**
     * Create an empty Bid object.  This method can be used to create an "empty" modelBid 
     * and also can be used to create a "shell" object by the persistence subsystem when creating a proxy object.
     *  
     */
    public Bid createBid();

    /**
     * Return Bids satisfying the search criterion abstracted by the modelBid.  For example, 
     * if the modelBid object isPersistent(), only one object with the given identifier is 
     * returned in the iterator. If the modelBid is not persistent, but has a defined user and/or item, the bids 
     * with the same user and/or item will be returned.
     *  
     * @param modelBid a Bid object used as the search criterion.  If null is given, all Bids will be retrieved.
     * @return an Iterator of Bid objects satisfying the criteria of the argument modelBid
     * @throws DTException if there is a problem with locating the requested Bids
     */
    public java.util.Iterator<Bid> findBid( Bid modelBid ) throws DTException;
    
    /**
     * Store a Bid object in the persistent data store. If the object is already persistent, it will be
     * updated in the data store.  Otherwise, it will be stored for the first time.
     * 
     * @param bid a Bid object to be stored in the persistent data store
     * @throws DTException if there is a problem with the process of storing the object in the data store
     */
    public void storeBid( Bid bid ) throws DTException;
    
    /**
     * Delete a Bid object. If the object is persistent, it will be removed from persistent data store.
     * 
     * @param bid a Bid object to be deleted
     * @throws DTException if there is a problem with the process of deleting the object
     */
    public void deleteBid( Bid bid ) throws DTException;

    /**
     * Create a new ExperienceReport object and link it to RegisteredUser objects, given as a reviewer and a reviewed. The given reviewer and reviewed objects, the two RegisteredUsers for 
     * which the ExperienceReport is recorded, must both be persistent, i.e. the value returned by 
     * the isPersistent() method must be true for both.
     * 
     * @param reviewer a RegisteredUser who is recording the report; it must be persistent
     * @param reviewed a RegisteredUser on whom the report is recorded; it must be persistent
     * @param rating a rating of the reviewed RegisteredUser, which must be within 1 and 5 (best), inclusive
     * @param report comments on the reviewed RegisteredUser and the transaction
     * @param date a date of the report
     * @return the new ExperienceReport linked to the two involved RegisteredUsers
     * @throws DTException if there is a problem with the new object creation (e.g., either the reviewer or reviewed are not persistent, or the rating value is illegal).
     */
    public ExperienceReport createExperienceReport( RegisteredUser reviewer, RegisteredUser reviewed, 
                                                    int rating, String report, java.util.Date date ) throws DTException;
    
    /**
     * Create an empty ExperienceReport object.  This method can be used to create an "empty" modelExperienceReport 
     * and also can be used to create a "shell" object by the persistence subsystem when creating a proxy object.
     *  
     */
    public ExperienceReport createExperienceReport(); 

    /**
     * Return ExperienceReports satisfying the search criterion abstracted by the modelExperienceReport.  For example, 
     * if the modelExperienceReport object isPersistent(), only one object with the given identifier is 
     * returned in the iterator. If the modelExperienceReport is not persistent, but has a defined reviewer and/or reviewed
     * RegisteredUsers, the reports with the same reviewer and/or reviewed RegisteredUsers will be returned.
     *  
     * @param modelExperienceReport an ExperienceReport object used as the search criterion.  If null is given, all ExperienceReports will be retrieved.
     * @return an Iterator of ExperienceReport objects satisfying the criteria of the argument modelExperienceReport
     * @throws DTException if there is a problem with locating the requested ExperienceReports
     */
    public java.util.Iterator<ExperienceReport> findExperienceReport( ExperienceReport modelExperienceReport ) throws DTException;
    
    /**
     * Store an ExperienceReport object in the persistent data store. If the object is already persistent, it will be
     * updated in the data store.  Otherwise, it will be stored for the first time.
     * 
     * @param experienceReport an ExperienceReport object to be stored in the persistent data store
     * @throws DTException if there is a problem with the process of storing the object in the data store
     */
    public void storeExperienceReport( ExperienceReport experienceReport ) throws DTException;
    
    /**
     * Delete an ExperienceReport object.  If the object is persistent, it will be removed from persistent data store.
     * 
     * @param experienceReport an ExperienceReport object to be deleted
     * @throws DTException if there is a problem with the process of deleting the object
     */
    public void deleteExperienceReport( ExperienceReport experienceReport ) throws DTException;
    
    /**
     * Create a new Membership object.  This should be a singleton object.
     * 
     * @param price a yearly price of the membership in Dawg Trades
     * @param date a date of this price assignment
     * @return a new Membership object
     * @throws DTException if there is a problem with the process of creating the object
     */
    public Membership createMembership( float price, java.util.Date date ) throws DTException;
    
    /**
     * Create an empty Membership object.  This method can be used to create a "shell" object 
     * by the persistence subsystem when creating a proxy object.
     *  
     */
    public Membership createMembership();

    /**
     * Return the current Membership object.  This should be a singleton object.
     *  
     * @return a Membership object which is the current description of a membership in Dawg Trades
     * @throws DTException if there is a problem with locating the requested Membership
     */
     public Membership findMembership() throws DTException;
    
    /**
     * Store a Membership object in the persistent data store. If the object is already persistent, it will be
     * updated in the data store.  Otherwise, it will be stored for the first time.
     * 
     * @param membership a Membership object to be stored in the persistent data store
     * @throws DTException if there is a problem with the process of storing the object in the data store
     */
    public void storeMembership( Membership membership ) throws DTException;
    
    //public void deleteMembership( Membership membership ) throws DTException;
 
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Associations (excluding many-to-many associations, which are handled by their own classes)
    //
    
    // hasParent: child Category (zero or more) to parent Category (optional)
    /**
     * Return a parent Category of a Category object, traversing the <i>hasParent</i> association. The category for which the parent category
     * is requested must be persistent.
     * 
     * @param category a category whose parent Category object is requested; it must be persistent
     * @return a parent Category object or null if the parent of the argument category does not exist
     * @throws DTException if there is a problem with locating the parent object, or if the argument is not persistent
     */
    public Category getParent( Category category ) throws DTException;
    
    /**
     * Return children (sub-categories) Category objects of a Category object, traversing the <i>hasParent</i> association. 
     * The category for which the children categories are requested must be persistent.
     * 
     * @param category a Category whose children Category objects are requested; it must be persistent
     * @return an iterator of child (sub-) Category objects
     * @throws DTException if there is a problem with locating the children objects, or if the argument is not persistent
     */
    public java.util.Iterator<Category> getChild( Category category ) throws DTException;
    
    // isDescribedBy: Category (one) to AttributeType (zero or more)
    /**
     * Return a Category of an AttributeType, that is a Category which is described by an AttributeType, traversing 
     * the <i>isDescribedBy</i> association.  The AttributeType must be persistent.
     * 
     * @param attributeType an AttributeType whose Category is requested; it must be persistent
     * @return a Category, which the AttributeType describes
     * @throws DTException if there is a problem with locating the Category object, or if the argument is not persistent
     */
    public Category getCategory( AttributeType attributeType ) throws DTException;
    
    /**
     * Return AttributeTypes which describe a Category, traversing the <i>isDescribedBy</i> association.  The Category must 
     * be persistent.
     * 
     * @param category a Category whose AttributeTypes are requested; it must be persistent
     * @return an iterator of AttributeTypes, which are describing the Category
     * @throws DTException if there is a problem with locating the AttributeType objects, or if the argument is not persistent
     */
    public java.util.Iterator<AttributeType> getAttributeType( Category category ) throws DTException;
     
    // isOfType: Item (zero or more) to Category (one)
    /** 
     * Return a Category of a given Item, traversing the isOfType association.  The Item must be persistent.
     * 
     * @param item an Item whose Category is requested; it must be persistent
     * @return a Category of the Item (its classification)
     * @throws DTException if there is a problem with locating the <i>Category</i> object, or if the argument is not persistent
     */
    public Category getCategory( Item item ) throws DTException;
    
    /** 
     * Return Items belonging to a given Category, traversing the <i>isOfType</i> association.  The Category must be persistent.
     * 
     * @param category a Category whose Items are requested; it must be persistent
     * @return an iterator of Items in the Category (classified in this Category)
     * @throws DTException if there is a problem with locating the Item objects, or if the argument is not persistent
     */
    public java.util.Iterator<Item> getItem( Category category ) throws DTException;
    
    // hasAttribute: Item (one) to Attribute (zero or more)
    /** 
     * Return an Item to which an Attribute belongs, traversing the <i>hasAttribute</i> association. The Attribute must be persistent.
     * 
     * @param attribute an Attribute for which its Item is requested; it must be persistent
     * @return an Item to which the Attribute belongs
     * @throws DTException if there is a problem with locating the Item object, or if the argument is not persistent
     */
    public Item getItem( Attribute attribute ) throws DTException;
    
    /** 
     * Return Attributes of a given Item, traversing the <i>isOfType</i> association.  The Item must be persistent.
     * 
     * @param item an Item whose Attributes are requested; it must be persistent
     * @return an iterator of Attributes of the Item
     * @throws DTException if there is a problem with locating the Attribute objects, or if the argument is not persistent
     */
    public java.util.Iterator<Attribute> getAttribute( Item item ) throws DTException;
    
    // owns: RegisteredUser (one) to Item (zero or more)
    /**
     * Return a RegisteredUser who owns an Item, traversing the <i>owns</i> association. The Item must be persistent.
     * 
     * @param item an Item whose owner is requested; it must be persistent
     * @return a RegisteredUser who owns the Item 
     * @throws DTException if there is a problem with locating the RegisteredUser object, or if the argument is not persistent
     */
    public RegisteredUser getRegisteredUser( Item item ) throws DTException;
    
    /**
     * Return Items owned by a RegisteredUser, traversing the <i>owns</i> association. The RegisteredUser must be persistent.
     * 
     * @param registeredUser a RegisteredUser whose Items are requested; it must be persistent
     * @return an iterator of Items owned by the RegisteredUser 
     * @throws DTException if there is a problem with locating the Item objects, or if the argument is not persistent
     */
    public java.util.Iterator<Item> getItem( RegisteredUser registeredUser ) throws DTException;
    
    // isSoldAt: Item (one) to Auction (optional)
    /**
     * Return an Item which is being sold at an Auction, traversing the <i>isSoldAt</i> association.  The Auction must be persistent.
     * 
     * @param auction an Auction whose Item being sold is requested; it must be persistent
     * @return an Item being sold at the given Auction
     * @throws DTException if there is a problem with locating the Item object, or if the argument is not persistent
     */
    public Item getItem( Auction auction ) throws DTException;
    
    /**
     * Return an Auction at which an Item is being sold, traversing the <i>isSoldAt</i> association.  The Item must be persistent.
     * 
     * @param item an Item for which its Auction is requested; it must be persistent
     * @return an Auction where the given Item is being sold or null, if the Item is not being auctioned
     * @throws DTException if there is a problem with locating the Auction object, or if the argument is not persistent
     */
    public Auction getAuction( Item item ) throws DTException;
    
    // hasType: Attribute (zero or more) to AttributeType (one)
    // (one direction needed only!)
    /**
     * Return an AttributeType of an Attribute, traversing the <i>hasType</i> association.  The Attribute must be persistent.
     * 
     * @param attribute an Attribute whose AttributeType is requested; it must be persistent
     * @return an AttributeType of the given Attribute
     * @throws DTException if there is a problem with locating the AttributeType object, or if the argument is not persistent
     */
    public AttributeType getAttributeType( Attribute attribute ) throws DTException;
    
    //public Iterator<Attribute>         getHasType( AttributeType attributeType ) throws DTException;   

}
