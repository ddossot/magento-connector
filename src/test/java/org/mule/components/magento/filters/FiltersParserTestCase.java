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

import static org.junit.Assert.assertEquals;
import Magento.AssociativeEntity;
import Magento.ComplexFilter;
import Magento.Filters;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
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

    @Test(expected = ParseException.class)
    public void testParseUnaryInsteadOfBinaryExpression() throws Exception
    {
        parse("eq(customer_id)");
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

    @Test
    public void testFilterCreationWithBinary() throws Exception
    {
        assertEquals(parse("eq(customer_name, 900)"), new Filters(null,
            new ComplexFilter[]{new ComplexFilter("customer_name", new AssociativeEntity("eq", "900"))}));
    }

    @Test
    public void testFilterCreationWithUnary() throws Exception
    {
        assertEquals(parse("notnull(customer_name)"), new Filters(null,
            new ComplexFilter[]{new ComplexFilter("customer_name", new AssociativeEntity("notnull", ""))}));
    }

    @Test
    public void testFilterCreationWithAnd() throws Exception
    {
        assertEquals(parse("notnull(customer_name), lt(customer_city_code, 56)"), // 
            new Filters(null, new ComplexFilter[]{
                new ComplexFilter("customer_name", new AssociativeEntity("notnull", "")),
                new ComplexFilter("customer_city_code", new AssociativeEntity("lt", "56"))}));
    }

    public Filters parse(String expression) throws ParseException
    {
        return new FiltersParser(new ByteArrayInputStream(expression.getBytes())).start();
    }

}
