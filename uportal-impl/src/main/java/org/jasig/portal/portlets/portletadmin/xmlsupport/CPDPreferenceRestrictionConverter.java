/**
 * Copyright (c) 2000-2009, Jasig, Inc.
 * See license distributed with this file and available online at
 * https://www.ja-sig.org/svn/jasig-parent/tags/rel-10/license-header.txt
 */
package org.jasig.portal.portlets.portletadmin.xmlsupport;

import java.util.ArrayList;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class CPDPreferenceRestrictionConverter extends AbstractCollectionConverter {

	public CPDPreferenceRestrictionConverter(Mapper mapper) {
		super(mapper);
	}

	public void marshal(Object arg0, HierarchicalStreamWriter arg1,
			MarshallingContext arg2) {
		// TODO Auto-generated method stub

	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		CPDPreferenceTypeRestriction restriction = new CPDPreferenceTypeRestriction();
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("defaultValue".equals(reader.getNodeName())) {
				if (restriction.getDefaultValues() == null) {
					restriction.setDefaultValues(new ArrayList<String>());
				}
				restriction.getDefaultValues().add(reader.getValue());
			} else if ("max".equals(reader.getNodeName())) {
				restriction.setMax(reader.getValue());
			} else if ("min".equals(reader.getNodeName())) {
				restriction.setMin(reader.getValue());
			} else if ("type".equals(reader.getNodeName())) {
				restriction.setType(reader.getValue());
			} else if ("value".equals(reader.getNodeName())) {
				CPDParameterTypeRestrictionValue val = new CPDParameterTypeRestrictionValue();
				val.setDisplay(reader.getAttribute("display"));
				val.setValue(reader.getValue());
				restriction.addValue(val);
			}
			reader.moveUp();
		}
		return restriction;
	}

	public boolean canConvert(Class clazz) {
		return CPDPreferenceTypeRestriction.class == clazz;
	}

}
