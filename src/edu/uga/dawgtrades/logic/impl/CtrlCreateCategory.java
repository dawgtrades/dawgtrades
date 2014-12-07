//
// A control class to implement the 'Create Category' use case
//
//

package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.ObjectModel;


public class CtrlCreateCategory 
{
    
    private ObjectModel objectModel = null;
    
    public CtrlCreateCategory( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    
    public long createCategory( long parentId, String name ) throws DTException
    { 
        Category               category  = null;
        Category parentCategory = null;
        Category               modelCategory  = null;
        Iterator<Category>     categoryIter  = null;
        
        // retrieve the parent category
        modelCategory = objectModel.createCategory();
        modelCategory.setId(parentId);
        categoryIter = objectModel.findCategory( modelCategory );
        while( categoryIter.hasNext() ) {
            parentCategory = categoryIter.next();
        }
        
        // make sure the ParentCategory actually exists
        if( parentCategory == null )
            throw new DTException( "The parent category does not exist" );
        
        // check if the category already exists
        modelCategory = objectModel.createCategory();
		modelCategory.setName(name);
		modelCategory.setParentId(parentId);
        categoryIter = objectModel.findCategory( modelCategory );
        while( categoryIter.hasNext() ) {
            category = categoryIter.next();
        }
        
        // check if the Category actually exists, and if so, throw an exception
        if( category != null )
            throw new DTException( "A Category with this name and parent id already exists" );
        
        category = objectModel.createCategory(parentCategory, name);
        
        objectModel.storeCategory( category );

        return category.getId();
    }
}

