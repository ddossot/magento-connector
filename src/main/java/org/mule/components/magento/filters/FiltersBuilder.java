/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.components.magento.filters;

import Magento.AssociativeEntity;
import Magento.ComplexFilter;
import Magento.Filters;

import java.util.LinkedList;
import java.util.List;

public class FiltersBuilder
{
    private List<ComplexFilter> complexFilters = new LinkedList<ComplexFilter>();

    public void addBinaryExpression(String operation, String variable, String value)
    {
        complexFilters.add(new ComplexFilter(variable, new AssociativeEntity(operation, value)));
    }

    public void addUnaryExpression(String operation, String variable)
    {
        addBinaryExpression(operation, variable, "");
    }

    public Filters build()
    {
        return new Filters(null, complexFilters.toArray(new ComplexFilter[complexFilters.size()]));
    }

}
