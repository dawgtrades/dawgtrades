package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;

import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.Category;

/**
 * A control class to delete a category
 * @author Justin Rector
 */

public class CtrlDeleteCategory 
{
    
    private ObjectModel objectModel = null;
    
    public CtrlDeleteCategory( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    
    public void deleteCategory( long categoryId ) throws DTException
    { 
        Category               category  = null;
        Category               modelCategory  = null;
        Item modelItem = null;
        Auction modelAuction = null;
        Iterator<Category>     categoryIter  = null;
        Iterator<Item> itemIter = null;
        
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
        
        //check to see if category has item being auctioned
        itemIter = objectModel.getItem(modelCategory);
        while( itemIter.hasNext() ) {
            modelItem = itemIter.next();
            if (modelItem != null) {
	            modelAuction = objectModel.getAuction(modelItem);
	            if (modelAuction != null) {
		            if (!modelAuction.getIsClosed())
		            	throw new DTException( "The category currently has auctions running");
		            }
            }
        }

        objectModel.deleteCategory( category );

    }
}