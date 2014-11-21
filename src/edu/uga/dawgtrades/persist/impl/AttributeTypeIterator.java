package edu.uga.dawgtrades.persist.impl;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;
/**
 * Iterator for AttributeTypes.
 * @author Mark Hoefer
 *
 */
public class AttributeTypeIterator implements Iterator<AttributeType> {
    private ResultSet rs = null;
    private ObjectModel objectModel = null;
    private boolean more = false;
    
    public AttributeTypeIterator( ResultSet rs, ObjectModel objectModel ) throws DTException {
        this.rs = rs;
        this.objectModel = objectModel;
        try {
            more = rs.next();
        }
        catch( Exception e ) {
            throw new DTException( "AttributeTypeIterator: Cannot create AttributeType iterator; cause: " + e );
        }
        
    }
    public boolean hasNext() {
        return more;
    }
    public AttributeType next() {
    	long id;
        String name;
        long categoryId;
        AttributeType attributeType = null;
        Category category = null;
        if( more ) {
            try {
                
                categoryId = rs.getLong(1);
            	name = rs.getString(2);
            	id = rs.getLong(3);
                
                more = rs.next();
            }
            catch( Exception e ) {
                throw new NoSuchElementException( "AttributeIterator: No next AttributeObject; cause: " + e );
            }
            
            try {
                category = objectModel.createCategory();
                category.setId(categoryId);
                attributeType = objectModel.createAttributeType(category, name);
                attributeType.setId(id);
            } catch (DTException e) {
                e.printStackTrace();
            }
        }
        else {
            throw new NoSuchElementException( "AttributeIterator: No next Attribute object" );
        }
        
        return attributeType;
    }
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
