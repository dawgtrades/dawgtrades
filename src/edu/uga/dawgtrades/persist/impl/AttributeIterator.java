package edu.uga.dawgtrades.persist.impl;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;
/**
 * Iterator for Attributes.
 * @author Mark Hoefer
 *
 */
public class AttributeIterator implements Iterator<Attribute> {
    private ResultSet rs = null;
    private ObjectModel objectModel = null;
    private boolean more = false;
    
    public AttributeIterator( ResultSet rs, ObjectModel objectModel ) throws DTException {
        this.rs = rs;
        this.objectModel = objectModel;
        try {
            more = rs.next();
        }
        catch( Exception e ) {
            throw new DTException( "AttributeIterator: Cannot create Attribute iterator; cause: " + e );
        }
    }
    
    public boolean hasNext() {
        return more;
    }
    
    public Attribute next() {
        long id;
        long attributeTypeId;
        long itemId;
        String value;
        Item item = null;
        AttributeType attributeType = null;
        Attribute attribute = null;
        if( more ) {
            try {
		id = rs.getLong(1);
        value = rs.getString(2);
		attributeTypeId = rs.getLong(3);
		itemId = rs.getLong(4);
                
                
                
                more = rs.next();
            }
            catch( Exception e ) {
                throw new NoSuchElementException( "AttributeIterator: No next AttributeObject; cause: " + e );
            }
            
            try {
                item = objectModel.createItem();
                item.setId(itemId);
                attributeType = objectModel.createAttributeType();
                attributeType.setId(attributeTypeId);
                attribute = objectModel.createAttribute(attributeType, item, value);
                attribute.setId(id);
                attribute.setItemId(itemId);
                attribute.setAttributeType(attributeTypeId);
            } catch (DTException e) {
                e.printStackTrace();
            }
            return attribute;
        }
        else {
            throw new NoSuchElementException( "AttributeIterator: No next Attribute object" );
        }
    }
    
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
