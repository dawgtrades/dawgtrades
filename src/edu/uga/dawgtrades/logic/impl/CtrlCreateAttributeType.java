//
// A control class to implement the 'Create AttributeType' use case
//
//

package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;

import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.ObjectModel;


public class CtrlCreateAttributeType 
{
    
    private ObjectModel objectModel = null;
    
    public CtrlCreateAttributeType( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    
    public long createAttributeType( long categoryId, String name ) throws DTException  { 
        AttributeType               attributeType  = null;
        Category category = null;
        Category modelCategory = null;
        Iterator<Category> categoryIter = null;
        
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
        
        attributeType = objectModel.createAttributeType( category, name);
   
        objectModel.storeAttributeType( attributeType );

        return attributeType.getId();
    }
}

