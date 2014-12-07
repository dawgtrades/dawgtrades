//
// A control class to implement the 'Create Item' use case
//
//

package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;

import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;


public class CtrlCreateItem 
{
    
    private ObjectModel objectModel = null;
    
    public CtrlCreateItem( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    
    public long createItem( long categoryId, long userId, String identifier, String name, String description) throws DTException   { 
        Item               item  = null;
        Category category =  null;
        RegisteredUser user = null;
        RegisteredUser modelRegisteredUser = null;
        Category modelCategory = null;
        Iterator<Category> categoryIter = null;
        Iterator<RegisteredUser> userIter = null;

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
        
        // retrieve the RegisteredUser
        modelRegisteredUser = objectModel.createRegisteredUser();
        modelRegisteredUser.setId(userId);
        userIter = objectModel.findRegisteredUser( modelRegisteredUser );
        while( userIter.hasNext() ) {
            user = userIter.next();
        }
        
        // make sure the RegisteredUser actually exists
        if( user == null )
            throw new DTException( "The user does not exist" );
			
        // create item object
        item = objectModel.createItem(category, user, identifier, name, description);

		// save item data
        objectModel.storeItem( item );

        return item.getId();
    }
}

