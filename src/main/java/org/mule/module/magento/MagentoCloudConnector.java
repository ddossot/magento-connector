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

import org.apache.commons.lang.Validate;
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
        return serviceLocator.getMage_Api_Model_Server_V2_HandlerPort();
    }

    /**
     * Need to authenticate before every call
     * 
     * @return Magento session ID
     * @throws Exception
     */
    protected String login() throws Exception
    {
        return this.getPort().login(getUsername(), getPassword());
    }

    public void initialise() throws InitialisationException
    {
        Validate.notNull(username);
        Validate.notNull(password);
        Validate.notNull(address);
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
        String sessionId = getSessionId();
        return this.getPort().salesOrderList(sessionId, FiltersParser.parse(filter));
    }

    private String getSessionId() throws Exception
    {
        return this.login();
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
        return this.getPort().salesOrderInfo(getSessionId(), orderIncrementId);
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
        return this.getPort().salesOrderHold(getSessionId(), orderIncrementId);
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
        return this.getPort().salesOrderUnhold(getSessionId(), orderIncrementId);
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
        return this.getPort().salesOrderCancel(getSessionId(), orderIncrementId);
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
    public int salesOrderAddComment(@Parameter String orderIncrementId,
                                    @Parameter String status,
                                    @Parameter String comment,
                                    @Parameter(optional = true) String notify) throws Exception
    {
        return this.getPort().salesOrderAddComment(getSessionId(), orderIncrementId, status, comment, notify);
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
        return this.getPort().salesOrderShipmentList(getSessionId(), FiltersParser.parse(filter));
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
        return this.getPort().salesOrderShipmentInfo(getSessionId(), shipmentIncrementId);
    }

    /**
     * @param shipmentIncrementId
     * @param comment
     * @param notify
     * @param includeInEmail
     * @return TODO what?
     * @throws Exception
     */
    @Operation
    public int salesOrderShipmentComment(@Parameter String shipmentIncrementId,
                                         @Parameter String comment,
                                         @Parameter(optional = true, defaultValue = "false") String notify,
                                         @Parameter String includeInEmail) throws Exception
    {
        return this.getPort().salesOrderShipmentAddComment(getSessionId(), shipmentIncrementId, comment,
            notify, includeInEmail);
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
        return this.getPort().salesOrderShipmentGetCarriers(getSessionId(), orderIncrementId);
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
        return this.getPort().salesOrderShipmentAddTrack(getSessionId(), shipmentIncrementId, carrier, title,
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
        return this.getPort().salesOrderShipmentRemoveTrack(getSessionId(), shipmentIncrementId, trackId);
    }

    @Operation
    public String salesOrderShipmentCreate(@Parameter String orderIncrementId,
                                           @Parameter OrderItemIdQty[] itemsQty,
                                           @Parameter String comment,
                                           @Parameter String email,
                                           @Parameter String includeInEmail) throws Exception
    {
        return this.getPort().salesOrderShipmentCreate(getSessionId(), orderIncrementId, itemsQty, comment,
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
        return this.getPort().salesOrderInvoiceList(getSessionId(), FiltersParser.parse(filter));
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
        return this.getPort().salesOrderInvoiceInfo(getSessionId(), invoiceIncrementId);
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
        return this.getPort().salesOrderInvoiceCreate(getSessionId(), orderIncrementId, itemsQty, comment,
            email, includeInEmail);
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
        return this.getPort().salesOrderInvoiceAddComment(getSessionId(), invoiceIncrementId, comment,
            notify, includeInEmail);
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
        return this.getPort().salesOrderInvoiceCapture(getSessionId(), invoiceIncrementId);
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
        return this.getPort().salesOrderInvoiceVoid(getSessionId(), invoiceIncrementId);
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
        return this.getPort().salesOrderInvoiceCancel(getSessionId(), invoiceIncrementId);
    }
}
