
package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.Item;

/**
 * A control class to delete an Item
 * @author Justin Rector
 */

public class CtrlDeleteItem 
{
    
    private ObjectModel objectModel = null;
    
    public CtrlDeleteItem( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    
    public void deleteItem( long itemId ) throws DTException
    { 
        Item               item  = null;
        Item               modelItem  = null;
        Iterator<Item>     itemIter  = null;

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

        objectModel.deleteItem(item);

    }
}
