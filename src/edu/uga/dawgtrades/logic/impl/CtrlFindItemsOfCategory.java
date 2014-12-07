package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;

public class CtrlFindItemsOfCategory {
    
    private ObjectModel objectModel = null;
    
    public CtrlFindItemsOfCategory( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    public List<Item> findItemsOfCategory( long categoryId ) throws DTException {
    	List<Item>      items  = null;
        Iterator<Item>  itemIter = null;
        Iterator<Category> categoryIter = null;
        Category            category = null;
        Item item = null;
        Category modelCategory = null;

        items = new LinkedList<Item>();
        
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
        
        // retrieve all Item objects
        itemIter = objectModel.getItem(category);
        while( itemIter.hasNext() ) {
            item = itemIter.next();
            System.out.println( item );
            items.add( item );
        }

        return items;
    }
}
