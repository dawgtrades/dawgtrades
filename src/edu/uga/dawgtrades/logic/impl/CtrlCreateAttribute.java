//
// A control class to implement the 'Create Attribute' use case
//
//

package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;

import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;


public class CtrlCreateAttribute 
{
    
    private ObjectModel objectModel = null;
    
    public CtrlCreateAttribute( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    
    public long createAttribute( long attributeTypeId, long itemId, String value ) throws DTException    { 
        long categoryId = 0;
    	Attribute               attribute  = null;
        Item item = null;
        Category category = null;
        AttributeType attributeType = null;
        Attribute               modelAttribute  = null;
        Item modelItem = null;
        Category modelCategory = null;
        AttributeType modelAttributeType = null;
        Iterator<Attribute>     attributeIter  = null;
        Iterator<Item> itemIter = null;
        Iterator<Category> categoryIter = null;
        Iterator<AttributeType> attributeTypeIter = null;
  
        
        // retrieve the Item
        modelItem = objectModel.createItem();
        modelItem.setId(itemId);
        itemIter = objectModel.findItem( modelItem );
        while( itemIter.hasNext() ) {
            item = itemIter.next();
        }
        
        // make sure the Item actually exists
        if( item == null )
            throw new DTException( "The Item does not exist" );
        
        categoryId = item.getCategoryId();
        
        // retrieve the Category
        modelCategory = objectModel.createCategory();
        modelCategory.setId(categoryId);
        categoryIter = objectModel.findCategory( modelCategory );
        while( categoryIter.hasNext() ) {
            category = categoryIter.next();
        }
        
        // make sure the Category actually exists
        if( category == null )
            throw new DTException( "The category does not exist" );
        
        // retrieve the AttributeType
        modelAttributeType = objectModel.createAttributeType();
        modelAttributeType.setId(attributeTypeId);
        attributeTypeIter = objectModel.getAttributeType( category );
        while( attributeTypeIter.hasNext() ) {
            modelAttributeType = attributeTypeIter.next();
            if (modelAttributeType.getId() == attributeTypeId)
            	attributeType = modelAttributeType;
        }
        
        // make sure the AttributeType actually exists
        if( attributeType == null )
            throw new DTException( "The attributeType does not exist" );
        
        // check if the attribute already exists
        attributeIter = objectModel.getAttribute(item);
        while( attributeIter.hasNext() ) {
            modelAttribute = attributeIter.next();
            if (modelAttribute.getAttributeType() == attributeTypeId)
            	throw new DTException("The attribute already exists");
        }
       
        
        attribute = objectModel.createAttribute(attributeType, modelItem, value);
		
        objectModel.storeAttribute( attribute );

        return attribute.getId();
    }
}

