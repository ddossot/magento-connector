/**
 * Mule Magento Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.magento.api.util;

import static org.junit.Assert.assertEquals;

import org.mule.module.magento.api.internal.AssociativeEntity;
import org.mule.module.magento.api.internal.CatalogCategoryEntity;
import org.mule.module.magento.api.internal.CatalogProductCreateEntity;
import org.mule.module.magento.api.internal.SalesOrderEntity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class MagentoBeanUnitTest
{
    @DataPoints
    public static Object[] entities = new Object[]{
        new SalesOrderEntity(),
        createEntity(),
        new CatalogCategoryEntity(10, 45, "hello", 0, 0, 0, null),
        new CatalogCategoryEntity(10, 45, "hello", 0, 0, 0, new CatalogCategoryEntity[]{}),
        new CatalogCategoryEntity(10, 45, "hello", 0, 0, 0,
            new CatalogCategoryEntity[]{new CatalogCategoryEntity(5, 99, "bye", 0, 0, 0, null)})};

    private static CatalogProductCreateEntity createEntity()
    {
        CatalogProductCreateEntity entity = new CatalogProductCreateEntity();
        entity.setAdditional_attributes(new AssociativeEntity[]{new AssociativeEntity("baz", "foobar"),
            new AssociativeEntity("foo", "bar")});
        return entity;
    }

    /**
     * Test that for any magento object,
     * {@link MagentoObject#fromMap(Class, java.util.Map)} is the inverse operation
     * of {@link MagentoMap#toMap(Object)}. - elements order of AssociativeEntities
     * is not considered
     */
    @Theory
    public void testIdempotenceOnMapConversion(Object magentoObject)
    {
        assertEquals(magentoObject, MagentoObject.fromMap(magentoObject.getClass(),
            MagentoMap.toMap(magentoObject)));
    }
}
