/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.magento.filters;

import org.mule.module.magento.api.internal.AssociativeEntity;
import org.mule.module.magento.api.internal.ComplexFilter;
import org.mule.module.magento.api.internal.Filters;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple builder for {@link Filters}
 * 
 * @author flbulgarelli
 */
public class FiltersBuilder
{
    private List<ComplexFilter> complexFilters = new LinkedList<ComplexFilter>();

    /**
     * Adds a binary expression as a new {@link ComplexFilter} to the built
     * {@link Filters}
     * 
     * @param operation the operator
     * @param variable the first operand
     * @param value the second operand
     */
    public void addBinaryExpression(String operation, String variable, String value)
    {
        complexFilters.add(new ComplexFilter(variable, new AssociativeEntity(operation, value)));
    }

    /**
     * Adds a unary expression as a new {@link ComplexFilter} to the built
     * {@link Filters}
     * 
     * @param operation the operator
     * @param variable the operand
     */
    public void addUnaryExpression(String operation, String variable)
    {
        addBinaryExpression(operation, variable, "");
    }

    /**
     * Answers the built filters
     * 
     * @return a new {@link Filters} object
     */
    public Filters build()
    {
        return new Filters(null, complexFilters.toArray(new ComplexFilter[complexFilters.size()]));
    }

}
