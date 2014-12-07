//
// A control class to implement the 'List all category' use case
//
//
package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.impl;
import edu.uga.dawgtrades.model.ObjectModel;



public class CtrlFindAllCategories {
    
    private ObjectModel objectModel = null;
    
    public CtrlFindAllCategories( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }

    public List<Category> findAllCategories() 
            throws DTException
    {
        List<Category>      category  = null;
        Iterator<Category>  categoryIter = null;
        Category            category = null;

        category = new LinkedList<Category>();
        
        // retrieve all Category objects
        //
        categoryIter = objectModel.findCategory( null );
        while( categoryIter.hasNext() ) {
            category = categoryIter.next();
            System.out.println( category );
            category.add( category );
        }

        return category;
    }
}
