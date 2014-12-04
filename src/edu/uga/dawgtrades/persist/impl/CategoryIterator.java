package edu.uga.dawgtrades.persist.impl;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;

/**
 * Iterator for Categories.
 * @author Mark Hoefer
 *
 */
public class CategoryIterator implements Iterator<Category> {
    private ResultSet rs = null;
    private ObjectModel objectModel = null;
    private boolean more = false;
    
    public CategoryIterator( ResultSet rs, ObjectModel objectModel ) throws DTException {
        this.rs = rs;
        this.objectModel = objectModel;
        try {
            more = rs.next();
        }
        catch( Exception e ) {
            throw new DTException( "CategoryIterator: Cannot create Category iterator; cause: " + e );
        }
    }
    public boolean hasNext() {
        return more;
    }
    public Category next() {
        long id;
    	String name;
        long parentId;
        Category parent = null;
        Category category = null;
        if( more ) {

            try {
		try {
		    id = rs.getLong(1);
		}
		catch(Exception e) {
		    id = -1;
		}
		name = rs.getString(2);
		parentId = rs.getLong(3);

            	more = rs.next();
            }
            catch( Exception e ) {
                throw new NoSuchElementException( "CategoryIterator: No next CategoryObject; cause: " + e );
            }
            
            try {
                parent = objectModel.createCategory();
                parent.setId(parentId);
                
                category = objectModel.createCategory(parent, name);
                category.setId(id);
                category.setParentId(parentId);
            } catch (DTException e) {
                e.printStackTrace();
            }
        }
        else {
            throw new NoSuchElementException( "CategoryIterator: No next Category object" );
        }

        return category;
    }
    public void remove() {
        throw new UnsupportedOperationException();
    }
}