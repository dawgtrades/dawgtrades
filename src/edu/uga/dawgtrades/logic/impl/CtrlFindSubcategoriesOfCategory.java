package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;

public class CtrlFindSubcategoriesOfCategory {
    
    private ObjectModel objectModel = null;
    
    public CtrlFindSubcategoriesOfCategory( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }

    public List<Category> findSubcategoriesOfCategory( long categoryId ) throws DTException {
    	List<Category>      categories  = null;
        Iterator<Category>  categoryIter = null;
        Category            category = null;
        Category modelCategory = null;

        categories = new LinkedList<Category>();
        
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
        
        // retrieve all Category objects
        //
        categoryIter = objectModel.getChild(category);
        while( categoryIter.hasNext() ) {
            category = categoryIter.next();
            System.out.println( category );
            categories.add( category );
        }

        return categories;
    }
}
