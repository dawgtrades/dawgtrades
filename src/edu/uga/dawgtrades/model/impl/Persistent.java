package edu.uga.dawgtrades.model.impl;

import edu.uga.dawgtrades.model.Persistable;

/**
 * Implementation of Persistent
 * @author Justin
 *
 */

public abstract class Persistent
    implements Persistable
{
    private long id;

    public Persistent()
    {
        this.id = -1;
    }

    public Persistent( long id )
    {
        this.id = id;
    }

    @Override
    public long getId()
    {
        return id;
    }

    @Override
    public void setId( long id )
    {
        this.id = id;
    }

    @Override
    public boolean isPersistent()
    {
        return id >= 0;
    }
}
