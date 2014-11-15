// Gnu Emacs C++ mode:  -*- Java -*-
//
// Interface:	Persistable
//
// K.J. Kochut
//
//
//

package edu.uga.dawgtrades.model;


/**
 * This interface represents the root interface implemented by all entity classes.  It has methods to read/write 
 * the persistent identifier of an entity object. Typically, it is a database key value of the 
 * corresponding row, representing the entity object. Initially, the identifier is set to -1, but 
 * once the entity object has been stored in the persistent data store, this value should be set to 
 * the actual identifier (key).
 * 
 *  * @author Krys J. Kochut
 *
 */
public interface Persistable {
    
    /**
     * Access the persistent identifier of an entity object instance. The value of -1 indicates that 
     * the object has not been stored in the persistent data store, yet. 
     * 
     * @return persistent identifier of an entity object instance.
     */
    public long    getId();

    /**
     * Set the persistent identifier for this entity object.  This method is typically used by the persistence
     * subsystem when creating proxy objects for entity object already residing in the persistent data store.
     * 
     * @param id the persistent object key
     */
    public void    setId( long id );
  
    /**
     * Check if this entity object has been stored in the the persistent data store (for the first time).
     * Note that the value is isPersistent() may be true, even though the entity object may need to be saved 
     * in the persistent data store again, after an update to its state.
     * 
     * @return true if this entity object has already been stored in the persistent data store.
     */
    public boolean isPersistent();

};
