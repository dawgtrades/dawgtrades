//
// A control class to implement the 'List all Attributes Of an item' UC
//
//
package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.impl;
import edu.uga.dawgtrades.model.ObjectModel;


public class CtrlFindAttributesOfItems {
    
    private ObjectModel objectModel = null;
    
    public CtrlFindAttributesOfItems( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
	
    public List<Attribute> findAttributesOfItem( long itemId ) throws DTException
    {
        Item                item = null;
        Item                modelItem = null;
        Iterator<Item>      itemIter = null;
        List<Attribute>        attributesOfItem  = null;

        attributesOfItem = new LinkedList<Attribute>();

        // find the item object
        modelItem = objectModel.createItem();
        modelItem.setId( itemId );
        itemIter = objectModel.findItem( modelItem );
        while( itemIter.hasNext() ) {
            item = itemIter.next();
        }
        if( item == null )
            throw new ItemsException( "A item with this name does not exist: " + itemId );

        Iterator<Attribute> attributeIter = objectModel.findAttribute( item );
        while( attributeIter != null && attributeIter.hasNext() ) {
            Attribute m = attributeIter.next();
            attributesOfItem.add( m.getAttribute() );
        }

        return attributesOfItem;
    }
}
