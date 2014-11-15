package edu.uga.dawgtrades.model;

/**
 * This interface represents an Attribute describing an Item.  It has a collection of methods to 
 * read/write Attribute's attributes. Attribute participates in 2 associations, where the class at 
 * the other end has a multiplicity of 1. Getters/setters for the identifiers of the one-side objects 
 * are included, as well.
 * It also extends the Persistable interface, since objects of this class will be stored in the persistent data store.
 * 
 *  @author Krys J. Kochut
 *
 */
public interface Attribute
    extends Persistable
{
    /**
     * Return the value of this Attribute.
     * @return the attribute's value
     */
    String getValue();
    
    /**
     * Set the new value of this Attribute.
     * @param value the new value of the attribute
     */
    void setValue(String value);
    
    // participates in associations with the other side having multiplicity of one
    /**
     * Return the identifier of the Item which is described by this Attribute.
     * @return the Item's identifier
     */
    long getItemId();
    
    /**
     * Set the identifier of the Item which is described by this Attribute.
     * @param itemId the new identifier value of the Item which this Attribute describes
     */
    void setItemId( long itemId );
    
    /**
     * Return the identifier of the AttributeType of this Attribute.
     * @return the AttributeType's identifier of this Attribute
     */
    long getAttributeType();
    
    /**
     * Set the new identifier of the AttributeType of this Attribute.
     * @param attributeId the new identifier of this Attribute's AttributeType
     */
    void setAttributeType( long attributeId );
}
