/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.magento;

import static org.junit.Assert.assertNotNull;
import Magento.SalesOrderEntity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Before;
import org.junit.Test;

public class MagentoCloudConnectorTestDriver
{
    private MagentoCloudConnector connector;

    @Before
    public void setup() throws Exception
    {
        connector = new MagentoCloudConnector();
        connector.setAddress("http://magento.it.zauber.com.ar/index.php/api/v2_soap");
        connector.setPassword(System.getenv("magento.password"));
        connector.setUsername(System.getenv("magento.username"));
        connector.initialise();
    }

    @Test
    public void testSalesOrdersList() throws Exception
    {
        SalesOrderEntity[] salesOrdersList = connector.salesOrdersList(null);
        assertNotNull(salesOrdersList);
    }

    @Test
    public void testSalesOrderInvoiceCancel() throws Exception
    {
        assertNotNull(connector.salesOrderInvoicesList(null));
    }

    @Test
    public void testSalesOrderInfoInexistent()
    {
        try
        {
            assertNotNull(connector.salesOrderInfo("899966"));
        }
        catch (Exception e)
        {
            System.out.println(e);
            System.err.println(e.getClass());
            System.err.println(e.getCause());
            System.out.println(ToStringBuilder.reflectionToString(e));
        }
    }

}
