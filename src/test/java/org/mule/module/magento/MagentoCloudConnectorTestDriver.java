/**
 * Mule Magento Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.magento;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.mule.module.magento.api.MagentoException;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * Integration test of the {@link MagentoCloudConnector}
 */
@SuppressWarnings("serial")
public class MagentoCloudConnectorTestDriver
{
    private static final String ORDER_ID = "100000001";
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
    public void listOrders() throws Exception
    {
        assertNotNull(connector.listOrders(null));
    }

    /**
     * Tests listing order invoices
     */
    @Test
    public void listOrdersInvoices() throws Exception
    {
        assertNotNull(connector.listOrdersInvoices(null));
    }

    /**
     * Tests getting information of an existent order
     */
    @Test
    public void getOrder() throws Exception
    {
        Map<String, Object> orderInfo = connector.getOrder(ORDER_ID);
        assertNotNull(orderInfo);
        System.out.println(ToStringBuilder.reflectionToString(orderInfo));
    }

    /**
     * Tests adding a comment to an existent order
     */
    @Test
    public void addOrderComment() throws Exception
    {
        connector.addOrderComment(ORDER_ID, "status", "A comment", false);
    }

    @Test
    public void getShipmentCarriers() throws Exception
    {
        assertFalse(connector.getOrderShipmentCarriers(ORDER_ID).isEmpty());
    }

    /**
     * Tests getting an order that does not exists
     */
    @Test(expected = MagentoException.class)
    public void getOrderInexistent()
    {
        connector.getOrder("899966");
    }

    @Test
    public void updateStockItem() throws Exception
    {
        connector.updateStockItem("1", new HashMap<String, Object>()
        {
            {
                put("manage_stock", "0");
                put("use_config_manage_stock", "0");
            }
        });
    }

}
