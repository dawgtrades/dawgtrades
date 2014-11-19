package edu.uga.dawgtrades.persist.impl;

import java.sql.*;
import java.util.iterator;
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.PreparedStatement;
import edu.uga.dawgtrades.model.*;

/**
 * AttributeManager
 * @author William C Spivey
 */

public class AttributeManager {
    private ObjectModel objMod = null;
    private Connection conn = null;

    public void save(Attribute attr) throws DTException {
	String insertAttributeSql = "insert into attribute ( attribute_value, attribute_type_id, item_id) values (?, ?, ?)";
	String updateAttributeSql = "update attribute set attribute_value = ?, attribute_type_id = ?, item_id = ? where attribute_id = ?";
	PreparedStatement stmt = null;
	int numUpdated;
	long attributeId;

	try {

	    if(!attr.isPersistent())
		stmt = (PreparedStatement) conn.prepareStatement(insertAttributeSql);
	    else
		stmt = (PreparedStatement) conn.prepareStatement(updateAttributeSql);

	    if(attr.getValue() != null)
		stmt.setString(1, attr.getValue());
	    else
		throw new DTException("AttributeManager.save: can't save Attribute: value undefined");

	    if(attr.getAttributeType() > 0)
		stmt.setLong(2, attr.getAttributeType());
	    else
		throw new DTException("AttributeManager.save: can't save Attribute: attributeType undefined");

	    if(attr.getItemId() > 0)
		stmt.setLong(3, attr.getItemId());
	    else
		throw new DTException("AttributeManager.save: can't save Attribute: item undefined");

	    if(item.isPersistent())
		stmt.setLong(4, attr.getId());

	    numUpdated = stmt.executeUpdate();

	    if(!attr.isPersistent()) {
		if(numUpdated >= 1) {
		    String sql = "select last_insert_id()";
		    if(stmt.execute(sql)) {
			ResultSet r = stmt.getResultSet();
			while(r.next()) {
			    attributeId = r.getLong(1);
			    if(attributeId > 0)
				attr.setId(attributeId);
			}
		    }
		}
	    }
	    else {
		if(numUpdated < 1)
		    throw new DTException("AttributeManager.save: failed to save attribute");
	    }
	}
	catch(SQLException e) {
	    e.printStackTrace();
	    throw new DTException("AttributeManager.save: failed to save attribute: " + e);
	}
    }

    public Iterator<Attribute> restore(Attribute modelAttribute) throws DTException {
	String selectAttributeSql = "select attribute_value, attribute_type_id, item_id";
	Statement stmt = null;
	StringBuffer query = new StringBuffer(100);
	StringBuffer condition = new StringBuffer(100);

	condition.setLength(0);

	query.append(selectAttributeSql);

	if(modelAttribute != null) {
	    if(modelAttribute.getId() >= 0)
		query.append(" where id = " + modelAttribute.getId());
	    else {
		if(modelAttribute.getValue() != null)
		    condition.append(" value = '" + modelAttribute.getValue() + "'");
		if(modelAttribute.getAttributeType() > 0) {
		    if(condition.length() > 0)
			condition.append(" and");
		    condition.append(" attribute_type_id = '" + modelAttribute.getAttributeType() + "'");
		}
		if(modelAttribute.getItemId() > 0) {
		    if(condition.length() > 0)
			condition.append(" and");
		    condition.append(" item_id = '" + modelAttribute.getItemId() + "'");
		}
		if(condition.length() > 0) {
		    query.append(" where ");
		    query.append(condition);
		}
	    }
	}
	try {

	    stmt = conn.createStatement();

	    if(stmt.execute(query.toString())) {
		ResultSet r = stmt.getResultSet();
		return new ItemIterator(r, objectModel);
}