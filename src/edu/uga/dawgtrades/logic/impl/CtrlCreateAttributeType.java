//
// A control class to implement the 'Create AttributeType' use case
//
//

package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.impl;


public class CtrlCreateAttributeType 
{
    
    private ObjectModel objectModel = null;
    
    public CtrlCreateAttributeType( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    
    public long createAttributeType( long categoryId, String name ) throws DTException  { 
        AttributeType               attributeType  = null;
        AttributeType               modelAttributeType  = null;
        Iterator<AttributeType>     attributeIter  = null;

        // check if the attributeType already exists
        modelAttributeType = objectModel.createAttributeType();
        modelAttributeType.setName(name);
		modelAttributeType.setCategoryId(categoryId);
        attributeTypeIter = objectModel.findAttributeType( modelAttributeType );
        while( attributeTypeIter.hasNext() ) {
            attributeType = attributeTypeIter.next();
        }
        
        // check if the AttributeType actually exists, and if so, throw an exception
        if( attributeType != null )
            throw new DTException( "An AttributeType with this name and category ID already exists" );
        
        attributeType = objectModel.createAttributeType( categoryId, name);

        objectModel.storeAttributeType( attributeType );

        return attributeType.getId();
    }
}

