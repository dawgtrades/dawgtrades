package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.AttributeType;

/**
 * A control class to delete the AttributeTypes of a Category
 * @author Justin Rector
 */

public class CtrlDeleteAttributeTypes 
{
    
    private ObjectModel objectModel = null;
    
    public CtrlDeleteAttributeTypes( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    
    public void deleteAttributeTypes( long categoryId ) throws DTException
    { 
        AttributeType               attributeType  = null;
        Category category = null;
        Category modelCategory = null;
        Iterator<AttributeType>     attributeTypeIter  = null;
        Iterator<Category> categoryIter = null;

        // retrieve the category
        modelCategory = objectModel.createCategory();
        modelCategory.setId(categoryId);
        categoryIter = objectModel.findCategory(modelCategory);
        while( categoryIter.hasNext() ) {
            category = categoryIter.next();
        }
        
        // make sure the Category actually exists
        if( category == null )
            throw new DTException( "The Category does not exist" );
        
        // retrieve & delete the AttributeTypes
        attributeTypeIter = objectModel.getAttributeType(modelCategory);
        while( attributeTypeIter.hasNext() ) {
            attributeType = attributeTypeIter.next();
            objectModel.deleteAttributeType(attributeType);         
        }
        

    }
}
