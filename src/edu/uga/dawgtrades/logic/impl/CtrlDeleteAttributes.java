
package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.Attribute;

/**
 * A control class to delete the Attributes of an Item
 * @author Justin Rector
 */

public class CtrlDeleteAttributes 
{
    
    private ObjectModel objectModel = null;
    
    public CtrlDeleteAttributes( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    
    public void deleteAttributes( long itemId ) throws DTException
    { 
        Attribute               attribute  = null;
        Item item = null;
        Item modelItem = null;
        Iterator<Attribute>     attributeIter  = null;
        Iterator<Item> itemIter = null;

        // retrieve the item
        modelItem = objectModel.createItem();
        modelItem.setId(itemId);
        itemIter = objectModel.findItem(modelItem);
        while( itemIter.hasNext() ) {
            item = itemIter.next();
        }
        
        // make sure the item actually exists
        if( item == null )
            throw new DTException( "The Item does not exist" );
        
        // retrieve & delete the attributes
        attributeIter = objectModel.getAttribute(modelItem);
        while( attributeIter.hasNext() ) {
            attribute = attributeIter.next();
            objectModel.deleteAttribute(attribute);         
        }
        

    }
}
