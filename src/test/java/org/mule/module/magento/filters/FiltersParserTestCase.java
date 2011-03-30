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

import static org.junit.Assert.assertEquals;

import org.mule.module.magento.api.internal.AssociativeEntity;
import org.mule.module.magento.api.internal.ComplexFilter;
import org.mule.module.magento.api.internal.Filters;

import org.junit.Test;

/**
 * Test for {@link FiltersParser}
 * 
 * @author flbulgarelli
 */
public class FiltersParserTestCase
{

    /***
     * Tests that a bad formed expression is rejected by parser
     */
    @Test(expected = ParseException.class)
    public void testParseBadExpression() throws Exception
    {
        parse("lalalal");
    }

    /***
     * Tests that a well formed expression but that pass less parameters than needed
     * is rejected by parser
     */
    @Test(expected = ParseException.class)
    public void testParseUnaryInsteadOfBinaryExpression() throws Exception
    {
        parse("eq(customer_id)");
    }

    /**
     * Tests that unary expressions are accepted
     */
    @Test
    public void testParseSimpleUnaryExpressionIntegerType() throws Exception
    {
        parse("null(customer_id)");
        parse("notnull(customer_id)");
    }

    /**
     * Tests that binary expressions are accepted
     */
    @Test
    public void testParseSimpleBinaryExpressionIntegerType() throws Exception
    {
        parse("eq(customer_id, 500)");
        parse("neq(customer_id, 500)");
    }

    /**
     * Tests that expressions that use string literals are accepted
     */
    @Test
    public void testParseSimpleBinaryExpressionStringValue() throws Exception
    {
        parse("like(customer_name, '% DOE')");
        parse("nlike(customer_name, '% DOE')");
    }

    /**
     * Tests that expressions that use simple "and" conjunction are accepted
     */
    @Test
    public void testParseSimpleExpressionAnd() throws Exception
    {
        parse("lt(customer_id, 156), gt(customer_id, 100)");
    }

    /**
     * Tests that expressions that use multiple "and" conjunction are accepted
     */
    @Test
    public void testParseSimpleExpressionAndAnd() throws Exception
    {
        parse("lteq(customer_id, 156), gt(customer_id, 100), gteq(customer_city_code, 9986)");
    }

    /**
     * Tests that expressions once parsed can be interpreted
     */
    @Test
    public void testFilterCreationWithBinary() throws Exception
    {
        assertEquals(parse("eq(customer_name, 900)"), new Filters(null,
            new ComplexFilter[]{new ComplexFilter("customer_name", new AssociativeEntity("eq", "900"))}));
    }

    /**
     * Tests that expressions once parsed can be interpreted
     */
    @Test
    public void testFilterCreationWithUnary() throws Exception
    {
        assertEquals(parse("notnull(customer_name)"), new Filters(null,
            new ComplexFilter[]{new ComplexFilter("customer_name", new AssociativeEntity("notnull", ""))}));
    }

    /**
     * Tests that expressions once parsed can be interpreted
     */
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
        return FiltersParser.parse(expression);
    }

}
