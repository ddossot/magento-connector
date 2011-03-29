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

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MagentoCloudConnectorUnitTest
{

    private MagentoCloudConnector connector;

    @Before
    public void setup() throws Exception
    {
        connector = new MagentoCloudConnector();
        // connector.setSalesClient(new MagentoSalesClientAxisImpl());
        connector.initialise();
    }

    @Test
    public void testSalesOrdersList()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSalesOrderInfo()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSalesOrderHold()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSalesOrderUnhold()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSalesOrderCancel()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSalesOrderAddComment()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSalesOrderShipmentsList()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSalesOrderShipmentInfo()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSalesOrderShipmentComment()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSalesOrderShipmentGetCarriers()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSalesOrderShipmentAddTrack()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSalesOrderShipmentRemoveTrack()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSalesOrderShipmentCreate()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSalesOrderInvoicesList()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSalesOrderInvoiceInfo()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSalesOrderInvoiceCreate()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSalesOrderInvoiceComment()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSalesOrderInvoiceCapture()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSalesOrderInvoiceVoid()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSalesOrderInvoiceCancel()
    {
        fail("Not yet implemented");
    }

}
