//
// A control class to implement the 'Create Item' use case
//
//

package edu.uga.dawgtrades.logic.impl;

import java.util.Date;
import java.util.Calendar;
import java.util.Iterator;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.impl;


public class CtrlCreateItem 
{
    
    private ObjectModel objectModel = null;
    
    public CtrlCreateItem( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    
    public long createItem( long categoryId, long userId, String identifier, String name, String description) throws DTException   { 
        Item               item  = null;
        Item               modelItem  = null;
        Iterator<Item>     itemIter  = null;

        // check if the item already exists
        modelItem = objectModel.createItem();
		modelItem.setCategoryId(categoryId);
		modelItem.setName(name);
        itemIter = objectModel.findItem( modelItem );
        while( itemIter.hasNext() ) {
            item = itemIter.next();
        }
        
        // check if the Item actually exists, and if so, throw an exception
        if( item != null )
            throw new DTException( "An Item with this ID already exists" );
			
        // create item object
        item = objectModel.createItem(itemId, minPrice);
        // set minimum price
	    item.setCategoryId(categoryId);
		item.setName(name);
		item.setOwnerId(userId);
        item.setIdentifier(identifier);
		item.setDescription(description);

		// save item data
        objectModel.storeItem( item );

        return item.getId();
    }
}

