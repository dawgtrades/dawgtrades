package edu.uga.dawgtrades.model;

/**
 * This interface represents an AttributeType describing a Category.  It has a collection of methods 
 * to read/write AttributeType's attributes. AttributeType participates in an association to Category, 
 * where the class at the other end has a multiplicity of 1. Getters/setters for the identifiers of the 
 * one side objects are included, as well.
 * It also extends the Persistable interface, since objects of this class will be stored in the persistent data store.
 * 
 *  @author Krys J. Kochut
 *
 */
public interface AttributeType
    extends Persistable
{
    /** Return the name of this AttributeType.
     * @return this AttributeType's name
     */
    String getName();
    
    /**
     * Set the new name of this AttributeType.
     * @param name the new name of this AttributeType
     */
    void setName(String name);
    
    // participates in associations with the other side having multiplicity of one
    /**
     * Return the identifier of the Category which this AttributeType describes.
     * @return the identifier of the Category to which this AttributeType belongs
     */
    long getCategoryId();
    
    /**
     * Set the new identifier of the Category which is described by this AttributeType.
     * @param categoryId the new identifier of the Category which this AttributeType describes
     */
    void setCategoryId( long categoryId );
}
