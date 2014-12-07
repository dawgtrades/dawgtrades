//
// A control class to implement the 'List all item' use case
//
//
package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;



public class CtrlFindAllItems {
    
    private ObjectModel objectModel = null;
    
    public CtrlFindAllItems( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }

    public List<Item> findAllItems() 
            throws DTException
    {
        List<Item>      items  = null;
        Iterator<Item>  itemIter = null;
        Item            item = null;

        items = new LinkedList<Item>();
        
        // retrieve all Item objects
        //
        itemIter = objectModel.findItem( null );
        while( itemIter.hasNext() ) {
            item = itemIter.next();
            System.out.println( item );
            items.add( item );
        }

        return items;
    }
}
