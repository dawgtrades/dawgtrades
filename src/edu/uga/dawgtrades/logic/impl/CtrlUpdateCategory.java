package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.Category;

/**
 * A control class to update a Category's name & parent ID
 * @author Justin Rector
 */

public class CtrlUpdateCategory 
{
    
    private ObjectModel objectModel = null;
    
    public CtrlUpdateCategory( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    
    public void updateCategory( long categoryId, long parentId, String name ) throws DTException
    { 
        Category               category  = null;
        Category parentCategory = null;
        Category               modelCategory  = null;
        Iterator<Category>     categoryIter  = null;
        
        // check if the category name already exists
        modelCategory = objectModel.createCategory();
        modelCategory.setName( name );
        categoryIter = objectModel.findCategory( modelCategory );
        while( categoryIter.hasNext() ) {
            category = categoryIter.next();
        }
        
        // check if the Category name actually exists, and if so, throw an exception
        if( category != null )
            throw new DTException( "A Category with this name already exists" );
        
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
        
        category.setName(name);
        category.setParentId(parentId);

        objectModel.storeCategory( category );

    }
}
