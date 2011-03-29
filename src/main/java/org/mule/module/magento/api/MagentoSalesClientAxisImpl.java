/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.magento.api;

import static org.apache.commons.lang.BooleanUtils.*;
import org.mule.module.magento.filters.FiltersParser;

import Magento.AssociativeEntity;
import Magento.OrderItemIdQty;
import Magento.SalesOrderEntity;
import Magento.SalesOrderInvoiceEntity;
import Magento.SalesOrderShipmentEntity;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.constraints.NotNull;

import org.apache.axis.AxisFault;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;

public class MagentoSalesClientAxisImpl
{
    private final PortProvider provider;

    private MagentoSalesClientAxisImpl(PortProvider provider)
    {
        this.provider = provider;
    }

    private String getSessionId() throws Exception
    {
        return provider.getSessionId();
    }

    /**
     * Returns list of Magento sales orders
     * 
     * @param filters optional list of filters
     * @return list of sales orders
     * @throws Exception
     */
    public List<SalesOrderEntity> list(String filter) throws Exception
    {
        return Arrays.asList(provider.getPort().salesOrderList(getSessionId(), FiltersParser.parse(filter)));
    }

    /**
     * Retrieves order information
     * 
     * @param Order ID
     * @return sales order information
     * @throws Exception
     */

    public SalesOrderEntity getInfo(String orderIncrementId) throws Exception
    {
        return provider.getPort().salesOrderInfo(getSessionId(), orderIncrementId);
    }

    /**
     * Puts order on hold
     * 
     * @param order id
     * @return
     * @throws Exception
     */

    public int hold(String orderIncrementId) throws Exception
    {
        return provider.getPort().salesOrderHold(getSessionId(), orderIncrementId);
    }

    /**
     * Releases order
     * 
     * @param order id
     * @return
     * @throws Exception
     */
    public int unhold(String orderIncrementId) throws Exception
    {
        return provider.getPort().salesOrderUnhold(getSessionId(), orderIncrementId);
    }

    /**
     * Cancels order
     * 
     * @param order id
     * @return sales order information
     * @throws Exception
     */
    public int cancel(String orderIncrementId) throws Exception
    {
        return provider.getPort().salesOrderCancel(getSessionId(), orderIncrementId);
    }

    /**
     * @param orderIncrementId
     * @param status
     * @param comment
     * @param notify
     * @return
     * @throws Exception
     */
    public int salesOrderAddComment(String orderIncrementId, String status, String comment, String notify)
        throws Exception
    {
        return provider.getPort().salesOrderAddComment(getSessionId(), orderIncrementId, status, comment,
            notify);
    }

    /**
     * Returns list of Magento sales order shipments
     * 
     * @param filters optional list of filters
     * @return list of sales order shipments
     * @throws Exception
     */
    @NotNull
    public List<SalesOrderShipmentEntity> listShipments(String filter) throws Exception
    {
        return Arrays.asList(provider.getPort().salesOrderShipmentList(getSessionId(),
            FiltersParser.parse(filter)));
    }

    // TODO revise parameter names
    // TODO Checkstyle

    /**
     * Retrieves order shipment information
     * 
     * @param Order shipment ID
     * @return sales order shipment information
     * @throws Exception
     */

    public SalesOrderShipmentEntity getShipmentInfo(String shipmentIncrementId) throws Exception
    {
        return provider.getPort().salesOrderShipmentInfo(getSessionId(), shipmentIncrementId);
    }

    /**
     * Adds a comment to the shipment
     * 
     * @param shipmentIncrementId
     * @param comment
     * @param sendEmail if an email must be sent after shipment creation
     * @param includeCommentInEmail if the comment must be sent in the email
     * @return TODO
     * @throws Exception
     */
    public int addShipmentComment(@NotNull String shipmentIncrementId,
                                  String comment,
                                  boolean sendEmail,
                                  boolean includeCommentInEmail) throws Exception
    {
        return provider.getPort() //
            .salesOrderShipmentAddComment(getSessionId(), shipmentIncrementId, comment,
                toIntegerString(sendEmail), toIntegerString(includeCommentInEmail));
    }

    /**
     * Returns list of carriers for the order
     * 
     * @param order id
     * @return list of carriers
     * @throws Exception
     */
    @NotNull
    public List<AssociativeEntity> getShipmentCarriers(@NotNull String orderIncrementId) throws Exception
    {
        Validate.notNull(orderIncrementId);
        return Arrays.asList(provider.getPort().salesOrderShipmentGetCarriers(getSessionId(),
            orderIncrementId));
    }

    /**
     * @param shipmentIncrementId
     * @param carrier
     * @param title
     * @param trackNumber
     * @return track ID
     * @throws Exception
     */
    public int addShipmentTrack(@NotNull String shipmentIncrementId,
                                String carrier,
                                String title,
                                String trackNumber) throws Exception
    {
        Validate.notNull(shipmentIncrementId);
        return provider.getPort().salesOrderShipmentAddTrack(getSessionId(), shipmentIncrementId, carrier,
            title, trackNumber);
    }

    /**
     * @param shipmentIncrementId
     * @param trackId
     * @return
     * @throws Exception
     */
    public int removeShipmentTrack(@NotNull String shipmentIncrementId, @NotNull String trackId)
        throws Exception
    {
        Validate.notNull(shipmentIncrementId);
        Validate.notNull(trackId);
        return provider.getPort().salesOrderShipmentRemoveTrack(getSessionId(), shipmentIncrementId, trackId);
    }

    /**
     * Creates a shipment for order
     * 
     * @param orderIncrementId the order increment id
     * @param itemsQuantities a map containing an entry per each (orderItemId,
     *            quantity) pair
     * @param comment an optional comment
     * @param sendEmail if an email must be sent after shipment creation
     * @param includeCommentInEmail if the comment must be sent in the email
     * @return
     * @throws Exception
     */
    public String createShipment(@NotNull String orderIncrementId,
                                 @NotNull Map<Integer, Double> itemsQuantities,
                                 String comment,
                                 boolean sendEmail,
                                 boolean includeCommentInEmail) throws Exception
    {
        return provider.getPort().salesOrderShipmentCreate(getSessionId(), orderIncrementId,
            fromMap(itemsQuantities), comment, toInteger(sendEmail), toInteger(includeCommentInEmail));
    }

    /**
     * Returns list of Magento sales order invoices
     * 
     * @param filters optional list of filters
     * @return list of sales order invoices
     * @throws Exception
     */
    public List<SalesOrderInvoiceEntity> listInvoices(String filter) throws Exception
    {
        return Arrays.asList(provider.getPort().salesOrderInvoiceList(getSessionId(),
            FiltersParser.parse(filter)));
    }

    /**
     * Retrieves order invoice information
     * 
     * @param Order invoice ID
     * @return sales order invoice information
     * @throws Exception
     */
    public SalesOrderInvoiceEntity getInvoiceInfo(@NotNull String invoiceIncrementId) throws Exception
    {
        Validate.notNull(invoiceIncrementId);
        return provider.getPort().salesOrderInvoiceInfo(getSessionId(), invoiceIncrementId);
    }

    /**
     * Creates an invoice for the given order
     * 
     * @param orderIncrementId
     * @param itemsQuantities a map containing an entry per each (orderItemId,
     *            quantity) pair
     * @param comment an optional comment
     * @param sendEmail if an email must be sent after shipment creation
     * @param includeCommentInEmail if the comment must be sent in the email
     * @return TODO
     * @throws Exception
     */
    public String createInvoice(@NotNull String orderIncrementId,
                                @NotNull Map<Integer, Double> itemsQuantities,
                                String comment,
                                boolean sendEmail,
                                boolean includeCommentInEmail) throws Exception
    {
        Validate.notNull(orderIncrementId);
        return provider.getPort() //
            .salesOrderInvoiceCreate(getSessionId(), orderIncrementId, fromMap(itemsQuantities), comment,
                toIntegerString(sendEmail), toIntegerString(includeCommentInEmail));
    }

    /**
     * @param invoiceIncrementId
     * @param comment
     * @param sendEmail if an email must be sent after shipment creation
     * @param includeCommentInEmail if the comment must be sent in the email
     * @return
     * @throws Exception
     */
    public String addInvoiceComment(@NotNull String invoiceIncrementId,
                                    String comment,
                                    boolean sendEmail,
                                    boolean includeCommentInEmail) throws Exception
    {
        return provider.getPort().salesOrderInvoiceAddComment(getSessionId(), invoiceIncrementId, comment,
            toIntegerString(sendEmail), toIntegerString(includeCommentInEmail));
    }

    /**
     * Captures and invoice
     * 
     * @param invoiceIncrementId
     * @return TODO
     * @throws Exception
     */
    public String captureInvoice(@NotNull String invoiceIncrementId) throws Exception
    {
        Validate.notNull(invoiceIncrementId);
        return provider.getPort().salesOrderInvoiceCapture(getSessionId(), invoiceIncrementId);
    }

    /**
     * Voids an invoice
     * 
     * @param invoiceIncrementId the invoice id
     * @return TODO
     * @throws Exception
     */
    public String voidInvoiceOrder(@NotNull String invoiceIncrementId) throws Exception
    {
        Validate.notNull(invoiceIncrementId);
        return provider.getPort().salesOrderInvoiceVoid(getSessionId(), invoiceIncrementId);
    }

    /**
     * Cancels an invoice
     * 
     * @param invoiceIncrementId the invoice id
     * @return TODO
     * @throws Exception
     */
    public String cancelInvoiceOrder(@NotNull String invoiceIncrementId) throws Exception
    {
        Validate.notNull(invoiceIncrementId);
        return provider.getPort().salesOrderInvoiceCancel(getSessionId(), invoiceIncrementId);
    }

    private OrderItemIdQty[] fromMap(Map<Integer, Double> map)
    {
        OrderItemIdQty[] quantities = new OrderItemIdQty[map.size()];
        int i = 0;
        for (Entry<Integer, Double> entry : map.entrySet())
        {
            quantities[i] = new OrderItemIdQty(entry.getKey(), entry.getValue());
            i++;
        }
        return quantities;
    }

    private static String toIntegerString(boolean value)
    {
        return toIntegerObject(value).toString();
    }
}
