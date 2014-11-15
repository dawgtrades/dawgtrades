package edu.uga.dawgtrades.model;

/**
 * This interface represents a Category.  It has a collection of methods to read/write Category's attributes.
 * Category (optionally) participates in an association with another Category (parent), where the class at 
 * the other end has a multiplicity of 1. Getters/setters for the identifiers of the one side objects are included, as well.
 * It also extends the Persistable interface, since objects of this class will be stored in the persistent data store.
 * 
 *  @author Krys J. Kochut
 *
 */
public interface Category
    extends Persistable
{
    /**
     * Return the name of this Category.
     * @return this Category's name
     */
    String getName();
    
    /**
     * Set the new name of this Category.
     * @param name the new name
     */
    void setName(String name);
    
    // participates in associations with the other side having multiplicity of one
    /**
     * Return the identifier of the parent Category of this Category. 
     * @return the identifier of the parent Category
     */
    long   getParentId();
    
    /**
     * Set the new identifier of the parent Category of this Category. 
     * @param parentId the new identifier of the parent Category
     */
    void   setParentId( long parentId );
}
