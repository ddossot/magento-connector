/*
 * $Id: $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.magento;

import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.module.magento.filters.FiltersParser;
import org.mule.tools.cloudconnect.annotations.Connector;
import org.mule.tools.cloudconnect.annotations.Operation;
import org.mule.tools.cloudconnect.annotations.Parameter;
import org.mule.tools.cloudconnect.annotations.Property;

import Magento.AssociativeEntity;
import Magento.Filters;
import Magento.Mage_Api_Model_Server_V2_HandlerPortType;
import Magento.MagentoServiceLocator;
import Magento.OrderItemIdQty;
import Magento.SalesOrderEntity;
import Magento.SalesOrderInvoiceEntity;
import Magento.SalesOrderShipmentEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
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

    protected Mage_Api_Model_Server_V2_HandlerPortType getPort() throws Exception
    {
        MagentoServiceLocator serviceLocator = new MagentoServiceLocator();
        serviceLocator.setMage_Api_Model_Server_V2_HandlerPortEndpointAddress(this.getAddress());
        Mage_Api_Model_Server_V2_HandlerPortType port = serviceLocator.getMage_Api_Model_Server_V2_HandlerPort();
        return port;
    }

    /**
     * Need to authenticate before every call
     * 
     * @return Magento session ID
     * @throws Exception
     */
    protected String login() throws Exception
    {
        String sessionId = this.getPort().login(getUsername(), getPassword());
        return sessionId;
    }

    public void initialise() throws InitialisationException
    {
        // TODO Auto-generated method stub

    }

    /**
     * Returns list of Magento sales orders
     * 
     * @param filters optional list of filters
     * @return list of sales orders
     * @throws Exception
     */
    @Operation
    public SalesOrderEntity[] salesOrdersList(@Parameter String filter) throws Exception
    {
        String sessionId = this.login();
        return this.getPort().salesOrderList(sessionId, FiltersParser.parse(filter));
    }

    /**
     * Retrieves order information
     * 
     * @param Order ID
     * @return sales order information
     * @throws Exception
     */
    @Operation
    public SalesOrderEntity salesOrderInfo(@Parameter String orderIncrementId) throws Exception
    {
        String sessionId = this.login();
        return this.getPort().salesOrderInfo(sessionId, orderIncrementId);
    }

    /**
     * Puts order on hold
     * 
     * @param order id
     * @return
     * @throws Exception
     */
    @Operation
    public int salesOrderHold(@Parameter String orderIncrementId) throws Exception
    {
        String sessionId = this.login();
        return this.getPort().salesOrderHold(sessionId, orderIncrementId);
    }

    /**
     * Releases order
     * 
     * @param order id
     * @return
     * @throws Exception
     */
    @Operation
    public int salesOrderUnhold(@Parameter String orderIncrementId) throws Exception
    {
        String sessionId = this.login();
        return this.getPort().salesOrderUnhold(sessionId, orderIncrementId);
    }

    /**
     * Cancels order
     * 
     * @param order id
     * @return sales order information
     * @throws Exception
     */
    @Operation
    public int salesOrderCancel(@Parameter String orderIncrementId) throws Exception
    {
        String sessionId = this.login();
        return this.getPort().salesOrderCancel(sessionId, orderIncrementId);
    }

    /**
     * @param orderIncrementId
     * @param status
     * @param comment
     * @param notify
     * @return
     * @throws Exception
     */
    @Operation
    public int salesOrderComment(@Parameter String orderIncrementId,
                                 @Parameter String status,
                                 @Parameter String comment,
                                 @Parameter(optional = true) String notify) throws Exception
    {
        String sessionId = this.login();
        return this.getPort().salesOrderAddComment(sessionId, orderIncrementId, status, comment, notify);
    }

    /**
     * Returns list of Magento sales order shipments
     * 
     * @param filters optional list of filters
     * @return list of sales order shipments
     * @throws Exception
     */
    @Operation
    public SalesOrderShipmentEntity[] salesOrderShipmentsList(@Parameter(optional = true) String filter)
        throws Exception
    {
        String sessionId = this.login();
        return this.getPort().salesOrderShipmentList(sessionId, FiltersParser.parse(filter));
    }

    // TODO revise optionals starting from here
    // TODO revise parameter names
    // TODO revise method names
    // TODO Checkstyle
    // TODO consider extract api
    // TODO array-in-parameters issue

    /**
     * Retrieves order shipment information
     * 
     * @param Order shipment ID
     * @return sales order shipment information
     * @throws Exception
     */
    @Operation
    public SalesOrderShipmentEntity salesOrderShipmentInfo(@Parameter String shipmentIncrementId)
        throws Exception
    {
        String sessionId = this.login();
        return this.getPort().salesOrderShipmentInfo(sessionId, shipmentIncrementId);
    }

    /**
     * @param shipmentIncrementId
     * @param comment
     * @param notify
     * @param includeInEmail
     * @return
     * @throws Exception
     */
    @Operation
    public int salesOrderShipmentComment(@Parameter String shipmentIncrementId,
                                         @Parameter String comment,
                                         @Parameter String notify,
                                         @Parameter String includeInEmail) throws Exception
    {
        String sessionId = this.login();
        return this.getPort().salesOrderShipmentAddComment(sessionId, shipmentIncrementId, comment, notify,
            includeInEmail);
    }

    /**
     * Returns list of carriers for the order
     * 
     * @param order id
     * @return list of carriers
     * @throws Exception
     */
    @Operation
    public AssociativeEntity[] salesOrderShipmentGetCarriers(@Parameter String orderIncrementId)
        throws Exception
    {
        String sessionId = this.login();
        return this.getPort().salesOrderShipmentGetCarriers(sessionId, orderIncrementId);
    }

    /**
     * @param shipmentIncrementId
     * @param carrier
     * @param title
     * @param trackNumber
     * @return track ID
     * @throws Exception
     */
    @Operation
    public int salesOrderShipmentAddTrack(@Parameter String shipmentIncrementId,
                                          @Parameter String carrier,
                                          @Parameter String title,
                                          @Parameter String trackNumber) throws Exception
    {
        String sessionId = this.login();
        return this.getPort().salesOrderShipmentAddTrack(sessionId, shipmentIncrementId, carrier, title,
            trackNumber);
    }

    /**
     * @param shipmentIncrementId
     * @param trackId
     * @return
     * @throws Exception
     */
    @Operation
    public int salesOrderShipmentRemoveTrack(@Parameter String shipmentIncrementId, @Parameter String trackId)
        throws Exception
    {
        String sessionId = this.login();
        return this.getPort().salesOrderShipmentRemoveTrack(sessionId, shipmentIncrementId, trackId);
    }

    @Operation
    public String salesOrderShipmentCreate(@Parameter String orderIncrementId,
                                           @Parameter OrderItemIdQty[] itemsQty,
                                           @Parameter String comment,
                                           @Parameter String email,
                                           @Parameter String includeInEmail) throws Exception
    {
        String sessionId = this.login();
        return this.getPort().salesOrderShipmentCreate(sessionId, orderIncrementId, itemsQty, comment,
            ("true".equals(email) ? 1 : 0), ("true".equals(includeInEmail) ? 1 : 0));
    }

    /**
     * Returns list of Magento sales order invoices
     * 
     * @param filters optional list of filters
     * @return list of sales order invoices
     * @throws Exception
     */
    @Operation
    public SalesOrderInvoiceEntity[] salesOrderInvoicesList(@Parameter(optional = true) String filter)
        throws Exception
    {
        String sessionId = this.login();
        return this.getPort().salesOrderInvoiceList(sessionId, FiltersParser.parse(filter));
    }

    /**
     * Retrieves order invoice information
     * 
     * @param Order invoice ID
     * @return sales order invoice information
     * @throws Exception
     */
    @Operation
    public SalesOrderInvoiceEntity salesOrderInvoiceInfo(@Parameter String invoiceIncrementId)
        throws Exception
    {
        String sessionId = this.login();
        return this.getPort().salesOrderInvoiceInfo(sessionId, invoiceIncrementId);
    }

    // string orderIncrementId - order increment id
    // array itemsQty - items qty to invoice
    // string comment - invoice comment (optional)
    // boolean email - send invoice on e-mail (optional)
    // boolean includeComment - include comments in e-mail (optional)

    @Operation
    public String salesOrderInvoiceCreate(@Parameter String orderIncrementId,
                                          @Parameter OrderItemIdQty[] itemsQty,
                                          @Parameter String comment,
                                          @Parameter String email,
                                          @Parameter String includeInEmail) throws Exception
    {
        String sessionId = this.login();
        return this.getPort().salesOrderInvoiceCreate(sessionId, orderIncrementId, itemsQty, comment, email,
            includeInEmail);
    }

    // string invoiceIncrementId - invoice increment id
    // string comment - invoice comment
    // boolean email - send invoice on e-mail (optional)
    // boolean includeComment - include comments in e-mail (optional)

    /**
     * @param invoiceIncrementId
     * @param comment
     * @param notify
     * @param includeInEmail
     * @return
     * @throws Exception
     */
    @Operation
    public String salesOrderInvoiceComment(@Parameter String invoiceIncrementId,
                                           @Parameter String comment,
                                           @Parameter String notify,
                                           @Parameter String includeInEmail) throws Exception
    {
        String sessionId = this.login();
        return this.getPort().salesOrderInvoiceAddComment(sessionId, invoiceIncrementId, comment, notify,
            includeInEmail);
    }

    /**
     * Captures invoice
     * 
     * @param Order invoice ID
     * @return
     * @throws Exception
     */
    @Operation
    public String salesOrderInvoiceCapture(@Parameter String invoiceIncrementId) throws Exception
    {
        String sessionId = this.login();
        return this.getPort().salesOrderInvoiceCapture(sessionId, invoiceIncrementId);
    }

    /**
     * Voids invoice
     * 
     * @param Order invoice ID
     * @return
     * @throws Exception
     */
    @Operation
    public String salesOrderInvoiceVoid(@Parameter String invoiceIncrementId) throws Exception
    {
        String sessionId = this.login();
        return this.getPort().salesOrderInvoiceVoid(sessionId, invoiceIncrementId);
    }

    /**
     * Cancel invoice
     * 
     * @param Order invoice ID
     * @return
     * @throws Exception
     */
    @Operation
    public String salesOrderInvoiceCancel(@Parameter String invoiceIncrementId) throws Exception
    {
        String sessionId = this.login();
        return this.getPort().salesOrderInvoiceCancel(sessionId, invoiceIncrementId);
    }
}
