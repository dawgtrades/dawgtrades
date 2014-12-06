//
// A control class to implement the 'Create Attribute' use case
//
//

package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.impl;


public class CtrlCreateAttribute 
{
    
    private ObjectModel objectModel = null;
    
    public CtrlCreateAttribute( ObjectModel objectModel )
    {
        this.objectModel = objectModel;
    }
    
    public long createAttribute( long attributeTypeId, long itemId, String value ) throws DTException    { 
        Attribute               attribute  = null;
        Attribute               modelAttribute  = null;
        Iterator<Attribute>     attributeIter  = null;

        // check if the attribute already exists
        modelAttribute = objectModel.createAttribute();
        modelAttribute.setValue( value );
		modelAttribute.setItemId( itemId);
		modelAttribute.setAttributeType( attributeTypeId );
        attributeIter = objectModel.findAttribute( modelAttribute );
        while( attributeIter.hasNext() ) {
            attribute = attributeIter.next();
        }
        
        // check if the Attribute actually exists, and if so, throw an exception
        if( attribute != null )
            throw new DTException( "An Attribute with this value already exists" );
        
        attribute = objectModel.createAttribute(attributeTypeId, itemId, value);

        objectModel.storeAttribute( attribute );

        return attribute.getId();
    }
}

