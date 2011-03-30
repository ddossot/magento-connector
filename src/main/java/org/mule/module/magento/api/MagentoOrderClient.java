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

import org.mule.module.magento.api.internal.AssociativeEntity;
import org.mule.module.magento.api.internal.SalesOrderEntity;
import org.mule.module.magento.api.internal.SalesOrderInvoiceEntity;
import org.mule.module.magento.api.internal.SalesOrderShipmentEntity;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

public interface MagentoOrderClient<E extends Exception>
{

    /**
     * Returns list of Magento sales orders
     * 
     * @param filters optional list of filters
     * @return list of sales orders
     * @throws E
     */
    List<SalesOrderEntity> list(@NotNull String filter) throws E;

    /**
     * Retrieves order information
     * 
     * @param Order ID
     * @return sales order information
     * @throws E
     */

    SalesOrderEntity getInfo(@NotNull String orderId) throws E;

    /**
     * Puts order on hold
     * 
     * @param order id
     * @return
     * @throws E
     */
    boolean hold(@NotNull String orderId) throws E;

    /**
     * Releases order
     * 
     * @param order id
     * @return
     * @throws E
     */
    boolean unhold(String orderId) throws E;

    /**
     * Cancels order
     * 
     * @param order id
     * @return sales order information
     * @throws E
     */
    boolean cancel(String orderId) throws E;

    /**
     * @param orderId
     * @param status
     * @param comment
     * @param sendEmail if an email must be sent after shipment creation
     * @return
     * @throws E
     */
    boolean salesOrderAddComment(String orderId, String status, String comment, boolean sendEmail)
        throws E;

    /**
     * Returns list of Magento sales order shipments
     * 
     * @param filters optional list of filters
     * @return list of sales order shipments
     * @throws E
     */
    @NotNull
    List<SalesOrderShipmentEntity> listShipments(String filter) throws E;

    /**
     * Retrieves order shipment information
     * 
     * @param Order shipment ID
     * @return sales order shipment information
     * @throws E
     */

    SalesOrderShipmentEntity getShipmentInfo(String shipmentId) throws E;

    /**
     * Adds a comment to the shipment
     * 
     * @param shipmentId
     * @param comment
     * @param sendEmail if an email must be sent after shipment creation
     * @param includeCommentInEmail if the comment must be sent in the email
     * @return TODO
     * @throws E
     */
    int addShipmentComment(@NotNull String shipmentId,
                           String comment,
                           boolean sendEmail,
                           boolean includeCommentInEmail) throws E;

    /**
     * Returns list of carriers for the order
     * 
     * @param order id
     * @return list of carriers
     * @throws E
     */
    @NotNull
    List<AssociativeEntity> getShipmentCarriers(@NotNull String orderId) throws E;

    /**
     * Adds a new tracking number
     * 
     * @param shipmentId TODO
     * @param carrier TODO
     * @param title TODO
     * @param trackNumber TODO
     * @return track ID TODO
     * @throws E TODO
     */
    int addShipmentTrack(@NotNull String shipmentId,
                         @NotNull String carrier,
                         @NotNull String title,
                         @NotNull String trackNumber) throws E;

    /**
     * @param shipmentId
     * @param trackId
     * @return
     * @throws E
     */
    int removeShipmentTrack(@NotNull String shipmentId, @NotNull String trackId) throws E;

    /**
     * Creates a shipment for order
     * 
     * @param orderId the order increment id
     * @param itemsQuantities a map containing an entry per each (orderItemId,
     *            quantity) pair
     * @param comment an optional comment
     * @param sendEmail if an email must be sent after shipment creation
     * @param includeCommentInEmail if the comment must be sent in the email
     * @return
     * @throws E
     */
    String createShipment(@NotNull String orderId,
                          @NotNull Map<Integer, Double> itemsQuantities,
                          String comment,
                          boolean sendEmail,
                          boolean includeCommentInEmail) throws E;

    /**
     * Returns list of Magento sales order invoices
     * 
     * @param filters optional list of filters
     * @return list of sales order invoices
     * @throws E
     */
    List<SalesOrderInvoiceEntity> listInvoices(String filter) throws E;

    /**
     * Retrieves order invoice information
     * 
     * @param Order invoice ID
     * @return sales order invoice information
     * @throws E
     */
    SalesOrderInvoiceEntity getInvoiceInfo(@NotNull String invoiceId) throws E;

    /**
     * Creates an invoice for the given order
     * 
     * @param orderId
     * @param itemsQuantities a map containing an entry per each (orderItemId,
     *            quantity) pair
     * @param comment an optional comment
     * @param sendEmail if an email must be sent after shipment creation
     * @param includeCommentInEmail if the comment must be sent in the email
     * @return TODO
     * @throws E
     */
    String createInvoice(@NotNull String orderId,
                         @NotNull Map<Integer, Double> itemsQuantities,
                         String comment,
                         boolean sendEmail,
                         boolean includeCommentInEmail) throws E;

    /**
     * @param invoiceId
     * @param comment
     * @param sendEmail if an email must be sent after shipment creation
     * @param includeCommentInEmail if the comment must be sent in the email
     * @return
     * @throws E
     */
    String addInvoiceComment(@NotNull String invoiceId,
                             String comment,
                             boolean sendEmail,
                             boolean includeCommentInEmail) throws E;

    /**
     * Captures and invoice
     * 
     * @param invoiceId
     * @return TODO
     * @throws E
     */
    boolean captureInvoice(@NotNull String invoiceId) throws E;

    /**
     * Voids an invoice
     * 
     * @param invoiceId the invoice id
     * @return TODO
     * @throws E
     */
    String voidInvoice(@NotNull String invoiceId) throws E;

    /**
     * Cancels an invoice
     * 
     * @param invoiceId the invoice id
     * @return TODO
     * @throws E
     */
    String cancelInvoiceOrder(@NotNull String invoiceId) throws E;

}
