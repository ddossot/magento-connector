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

import static org.junit.Assert.*;
import Magento.AssociativeEntity;
import Magento.ComplexFilter;
import Magento.Filters;

import java.io.ByteArrayInputStream;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Test for {@link FiltersParser}
 */
public class FiltersParserTestCase
{

    @Test(expected = ParseException.class)
    public void testParseBadExpression() throws Exception
    {
        parse("lalalal");
    }

    @Test
    public void testParseSimpleUnaryExpressionIntegerType() throws Exception
    {
        parse("null(customer_id)");
        parse("notnull(customer_id)");
    }

    @Test
    public void testParseSimpleBinaryExpressionIntegerType() throws Exception
    {
        parse("eq(customer_id, 500)");
        parse("neq(customer_id, 500)");
    }

    @Ignore
    @Test
    public void testParseSimpleBinaryExpressionStringValue() throws Exception
    {
        parse("like(customer_name, '% DOE')");
    }

    @Test
    public void testParseSimpleExpressionAnd() throws Exception
    {
        parse("lt(customer_id, 156), gt(customer_id, 100)");
    }

    @Test
    public void testParseSimpleExpressionAndAnd() throws Exception
    {
        parse("lt(customer_id, 156), gt(customer_id, 100), gteq(customer_city_code, 9986)");
    }

    @Ignore
    @Test
    public void testFilterCreation() throws Exception
    {
        assertEquals(parse("like(customer_name, '% DOE')"), new Filters(new AssociativeEntity[]{},
            new ComplexFilter[]{new ComplexFilter("customer_name", new AssociativeEntity("like", "% DOE"))}));
    }

    public Object parse(String expression) throws ParseException
    {
        new FiltersParser(new ByteArrayInputStream(expression.getBytes())).start();
        return null;
    }

}
