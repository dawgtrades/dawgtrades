package edu.uga.dawgtrades.model;

/**
 * This interface represents an Item of some Category and owner by a RegisteredUser. It has a collection of 
 * methods to read/write Item's attributes. The inherited getId() and setId() methods (from the Persistable 
 * interface) should be used in lieu of unique identifier of an Item.
 * Item participates in an association with RegisteredUser, where the class at the other end has a multiplicity of 1.
 * Getters/setters for the identifiers of the one side objects are included, as well.
 * It also extends the Persistable interface, since objects of this class will be stored in the persistent data store.
 * 
 *  @author Krys J. Kochut
 *
 */
public interface Item
    extends Persistable
{
    /**
     * @return
     */
    //String getIdentifier();
    
    /**
     * @param identifier
     */
    //void setIdentifier( String identifier );
    
    /**
     * Return the name of this Item.
     * @return the Item's name
     */
    String getName();
    
    /**
     * Set the name of this Item.
     * @param name the new name
     */
    void setName( String name );
    
    /**
     * Return the description of this Item.
     * @return the Item's description
     */
    String getDescription();
    
    /**
     * Set the description of this Item.
     * @param description the new description
     */
    void setDescription( String description );
    
    // participates in associations with the other side having multiplicity of one
    /**
     * Return the identifier of the RegisteredUser who is the owner of this Item.
     * @return the Item owner's identifier
     */
    long getOwnerId();
    
    /**
     * Set the identifier of the RegisteredUser who is the owner of this Item.
     * @param ownerId the new owner's identifier
     */
    void setOwnerId( long ownerId );
}
