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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mule.module.magento.api.MagentoClientAdaptor;
import org.mule.module.magento.api.AxisPortProvider;
import org.mule.module.magento.api.internal.AssociativeEntity;
import org.mule.module.magento.api.internal.ComplexFilter;
import org.mule.module.magento.api.internal.Filters;
import org.mule.module.magento.api.internal.Mage_Api_Model_Server_V2_HandlerPortType;
import org.mule.module.magento.api.internal.SalesOrderEntity;
import org.mule.module.magento.api.internal.SalesOrderShipmentEntity;
import org.mule.module.magento.api.order.AxisMagentoOrderClient;
import org.mule.module.magento.api.order.MagentoOrderClient;
import org.mule.module.magento.api.order.model.Carrier;

import java.rmi.RemoteException;

import edu.emory.mathcs.backport.java.util.Collections;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class MagentoCloudConnectorUnitTest
{
    private static final String ORDER_ID = "10001";
    private MagentoCloudConnector connector;
    private AxisPortProvider portProvider;
    private Mage_Api_Model_Server_V2_HandlerPortType port;

    @Before
    public void setup() throws Exception
    {
        connector = new MagentoCloudConnector();
        portProvider = mock(AxisPortProvider.class);
        port = mock(Mage_Api_Model_Server_V2_HandlerPortType.class);
        connector.setOrderClient(MagentoClientAdaptor.adapt(MagentoOrderClient.class,
            new AxisMagentoOrderClient(portProvider)));
        connector.initialise();
        when(portProvider.getPort()).thenReturn(port);
    }

    @Test
    public void testSalesOrdersListNoFilters() throws Exception
    {
        when(port.salesOrderList(anyString(), eq(new Filters()))).thenReturn(
            new SalesOrderEntity[]{new SalesOrderEntity()});
        assertEquals(1, connector.listOrders(null).size());
    }

    @Test
    public void testSalesOrdersList() throws Exception
    {
        when(port.salesOrderList(anyString(), // 
            eq(new Filters(null, new ComplexFilter[]{ //
                new ComplexFilter("customer_id", new AssociativeEntity("eq", "500"))})))) //
        .thenReturn(new SalesOrderEntity[]{new SalesOrderEntity()});
        assertEquals(1, connector.listOrders("eq(customer_id, 500)").size());
    }

    @Test
    public void testSalesOrderInfo() throws Exception
    {
        when(port.salesOrderInfo(anyString(), eq(ORDER_ID))).thenReturn(new SalesOrderEntity());
        connector.getOrder(ORDER_ID);
    }

    @Test
    public void testSalesOrderHold() throws Exception
    {
        connector.holdOrder(ORDER_ID);
        verify(port).salesOrderHold(anyString(), eq(ORDER_ID));
    }

    @Test
    public void testSalesOrderUnhold() throws Exception
    {
        connector.unholdOrder(ORDER_ID);
        verify(port).salesOrderUnhold(anyString(), eq(ORDER_ID));
    }

    @Test
    public void testSalesOrderCancel() throws Exception
    {
        connector.cancelOrder(ORDER_ID);
        verify(port).salesOrderCancel(anyString(), eq(ORDER_ID));
    }

    @Test
    public void testSalesOrderAddComment() throws RemoteException
    {
        connector.addOrderComment(ORDER_ID, "status", "A comment", false);
        verify(port).salesOrderAddComment(anyString(), eq(ORDER_ID), eq("status"), eq("A comment"), eq("0"));
    }

    @Test
    public void testSalesOrderShipmentsList() throws RemoteException
    {
        SalesOrderShipmentEntity shipment = new SalesOrderShipmentEntity();
        shipment.setIs_active("1");
        when(port.salesOrderShipmentList(anyString(), eq(new Filters()))).thenReturn(
            new SalesOrderShipmentEntity[]{shipment});
        assertEquals(1, connector.listOrdersShipments("").size());
    }

    @Ignore
    @Test
    public void testSalesOrderShipmentInfo()
    {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void testSalesOrderShipmentComment()
    {
        fail("Not yet implemented");
    }

    @Test
    public void testSalesOrderShipmentGetCarriers() throws RemoteException
    {
        when(port.salesOrderShipmentGetCarriers(anyString(), eq(ORDER_ID))) //
        .thenReturn(new AssociativeEntity[]{new AssociativeEntity("FDX", "Fedex Express")});
        assertEquals(Collections.singletonList(new Carrier("FDX", "Fedex Express")),
            connector.getOrderShipmentCarriers(ORDER_ID));
    }

    @Ignore
    @Test
    public void testSalesOrderShipmentAddTrack()
    {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void testSalesOrderShipmentRemoveTrack()
    {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void testSalesOrderShipmentCreate()
    {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void testSalesOrderInvoicesList()
    {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void testSalesOrderInvoiceInfo()
    {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void testSalesOrderInvoiceCreate()
    {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void testSalesOrderInvoiceComment()
    {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void testSalesOrderInvoiceCapture()
    {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void testSalesOrderInvoiceVoid()
    {
        fail("Not yet implemented");
    }

    @Ignore
    @Test
    public void testSalesOrderInvoiceCancel()
    {
        fail("Not yet implemented");
    }
    
    //TODO reflectively test all methods. It is a nonses to write individual tests for them all

}
