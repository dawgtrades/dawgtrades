package edu.uga.dawgtrades.persist.impl;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;

/**
 * Iterator for Items. 
 * @author Justin
 *
 */

public class ItemIterator implements Iterator<Item> {
	
	private ResultSet   rs = null;
    private ObjectModel objectModel = null;
    private boolean     more = false;
    
    public ItemIterator( ResultSet rs, ObjectModel objectModel )
            throws DTException
    {
        this.rs = rs;
        this.objectModel = objectModel;
        try {
            more = rs.next();
        }
        catch( Exception e ) {  
            throw new DTException( "ItemIterator: Cannot create Item iterator; cause: " + e );
        }
    }

    public boolean hasNext()
    {
        return more;
    }
    
    public Item next()
    {
    	long id;
    	long ownerId;
    	long categoryId;
    	String identifier;
    	String name;
    	String description;
    	Item item = null;

        if( more ) {

            try {
                name = rs.getString( 1 );
                description = rs.getString( 2 );
                identifier = rs.getString (3 );
                categoryId = rs.getLong( 4 );
                ownerId = rs.getLong( 5 );
				try {
		           id = rs.getLong( 6 );
		        }
		        catch(Exception e) {
		          id = -1;
		        }
                more = rs.next();
            }
            catch( Exception e ) {
                throw new NoSuchElementException( "ItemIterator: No next Itemobject; cause: " + e );
            }
            
            RegisteredUser owner = objectModel.createRegisteredUser(); //Not 100% sure about this
            owner.setId(ownerId);
            Category category = objectModel.createCategory();
            category.setId(categoryId);
            try {
				item = objectModel.createItem(category, owner, identifier, name, description);
				item.setId( id );
				item.setOwnerId(ownerId);
				item.setCategoryId(categoryId);
            } 
            catch (DTException e) {
				e.printStackTrace();
			}
            

            return item;
        }
        else {
            throw new NoSuchElementException( "ItemIterator: No next Item object" );
        }
    }
    
    public void remove()
    {
        throw new UnsupportedOperationException();
    }

	
}
