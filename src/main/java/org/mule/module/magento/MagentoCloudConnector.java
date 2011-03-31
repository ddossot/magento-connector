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

import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.module.magento.api.AxisFaultExceptionHandler;
import org.mule.module.magento.api.DefaultAxisPortProvider;
import org.mule.module.magento.api.MagentoException;
import org.mule.module.magento.api.order.AxisMagentoOrderClient;
import org.mule.module.magento.api.order.MagentoOrderClient;
import org.mule.module.magento.api.order.model.Carrier;
import org.mule.tools.cloudconnect.annotations.Connector;
import org.mule.tools.cloudconnect.annotations.Operation;
import org.mule.tools.cloudconnect.annotations.Parameter;
import org.mule.tools.cloudconnect.annotations.Property;

import java.util.List;
import java.util.Map;

/**
 * A Cloud Connector for the Magento Order Sales API.
 * 
 * @author eberman
 */
@Connector(namespacePrefix = "magento", namespaceUri = "http://www.mulesoft.org/schema/mule/magento")
public class MagentoCloudConnector implements Initialisable
{
    // private Logger log = LoggerFactory.getLogger(MagentoCloudConnector.class);

    @Property
    private String username;
    @Property
    private String password;
    @Property
    private String address;

    private MagentoOrderClient<MagentoException> orderClient;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void initialise() throws InitialisationException
    {
        if (orderClient == null)
        {
            setOrderClient(AxisFaultExceptionHandler.handleFaults(MagentoOrderClient.class,
                new AxisMagentoOrderClient(new DefaultAxisPortProvider(username, password, address))));
        }
    }

    @Operation
    public int addOrderShipmentComment(@Parameter String shipmentId,
                                       @Parameter(optional = true) String comment,
                                       @Parameter(optional = true, defaultValue = "false") boolean sendEmail,
                                       @Parameter(optional = true, defaultValue = "false") boolean includeCommentInEmail)

    {
        return orderClient.addShipmentComment(shipmentId, comment, sendEmail, includeCommentInEmail);
    }

    @Operation
    public int addOrderShipmentTrack(@Parameter String shipmentId,
                                     @Parameter String carrier,
                                     @Parameter String title,
                                     @Parameter String trackId)
    {
        return orderClient.addShipmentTrack(shipmentId, carrier, title, trackId);
    }

    @Operation
    public boolean cancelOrder(@Parameter String orderId)
    {
        return orderClient.cancel(orderId);
    }

    @Operation
    public String createOrderShipment(@Parameter String orderId,
                                      @Parameter Map<Integer, Double> itemsQuantities,
                                      @Parameter(optional = true) String comment,
                                      @Parameter(optional = true, defaultValue = "false") boolean sendEmail,
                                      @Parameter(optional = true, defaultValue = "false") boolean includeCommentInEmail)

    {
        return orderClient.createShipment(orderId, itemsQuantities, comment, sendEmail, includeCommentInEmail);
    }

    @Operation
    public Map<String, Object> getOrderInfo(@Parameter String orderId)
    {
        return orderClient.getInfo(orderId);
    }

    @Operation
    public Map<String, Object> getOrderInvoiceInfo(@Parameter String invoiceId)
    {
        return orderClient.getInvoiceInfo(invoiceId);
    }

    @Operation
    public List<Carrier> getOrderShipmentCarriers(@Parameter String orderId)
    {
        return orderClient.getShipmentCarriers(orderId);
    }

    @Operation
    public Map<String, Object> getOrderShipmentInfo(@Parameter String shipmentId)
    {
        return orderClient.getShipmentInfo(shipmentId);
    }

    @Operation
    public boolean holdOrder(@Parameter String orderId)
    {
        return orderClient.hold(orderId);
    }

    @Operation
    public List<Map<String, Object>> listOrders(@Parameter(optional = true) String filter)
    {
        return orderClient.list(filter);
    }

    @Operation
    public List<Map<String, Object>> listOrdersInvoices(@Parameter(optional = true) String filter)

    {
        return orderClient.listInvoices(filter);
    }

    @Operation
    public List<Map<String, Object>> listOrdersShipments(@Parameter(optional = true) String filter)

    {
        return orderClient.listShipments(filter);
    }

    @Operation
    public int removeOrdersShipmentTrack(@Parameter String shipmentId, @Parameter String trackId)

    {
        return orderClient.removeShipmentTrack(shipmentId, trackId);
    }

    @Operation
    public boolean addOrderComment(@Parameter String orderId,
                                   @Parameter String status,
                                   @Parameter String comment,
                                   @Parameter(optional = true, defaultValue = "false") boolean sendEmail)

    {
        return orderClient.addComment(orderId, status, comment, sendEmail);
    }

    @Operation
    public boolean unholdOrder(@Parameter String orderId)
    {
        return orderClient.unhold(orderId);
    }

    @Operation
    public String createOrderInvoice(@Parameter String orderId,
                                     @Parameter Map<Integer, Double> itemsQuantities,
                                     @Parameter(optional = true) String comment,
                                     @Parameter(optional = true, defaultValue = "false") boolean sendEmail,
                                     @Parameter(optional = true, defaultValue = "false") boolean includeCommentInEmail)

    {
        return orderClient.createInvoice(orderId, itemsQuantities, comment, sendEmail, includeCommentInEmail);
    }

    @Operation
    public String addOrderInvoiceComment(@Parameter String invoiceId,
                                         @Parameter(optional = true) String comment,
                                         @Parameter(optional = true, defaultValue = "false") boolean sendEmail,
                                         @Parameter(optional = true, defaultValue = "false") boolean includeCommentInEmail)

    {
        return orderClient.addInvoiceComment(invoiceId, comment, sendEmail, includeCommentInEmail);
    }

    @Operation
    public boolean captureOrderInvoice(@Parameter String invoiceId)
    {
        return orderClient.captureInvoice(invoiceId);
    }

    @Operation
    public String voidOrderInvoice(@Parameter String invoiceId)
    {
        return orderClient.voidInvoice(invoiceId);
    }

    @Operation
    public String cancelOrderInvoice(@Parameter String invoiceId)
    {
        return orderClient.cancelInvoiceOrder(invoiceId);
    }

    @SuppressWarnings("unchecked")
    public void setOrderClient(MagentoOrderClient<?> magentoOrderClient)
    {
        // hack for softening exceptions
        this.orderClient = (MagentoOrderClient<MagentoException>) magentoOrderClient;
    }

    // TODO ids shoudl be integrals
}
