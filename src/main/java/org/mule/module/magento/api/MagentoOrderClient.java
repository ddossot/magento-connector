/**
 * Mule Magento Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.magento.api;

import org.mule.module.magento.api.model.Carrier;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

/**
 * Facade for the Magento Sales Order API
 * 
 * @author flbulgarelli
 */
public interface MagentoOrderClient<ExceptionType extends Exception>
{

    /**
     * Returns list of Magento sales orders
     * 
     * @param filters optional list of filters
     * @return list of sales orders
     * @throws ExceptionType
     */
    List<Map<String, Object>> list(@NotNull String filter) throws ExceptionType;

    /**
     * Retrieves order information
     * 
     * @param Order ID
     * @return sales order information
     * @throws ExceptionType
     */

    Map<String, Object> getInfo(@NotNull String orderId) throws ExceptionType;

    /**
     * Puts order on hold
     * 
     * @param order id
     * @return
     * @throws ExceptionType
     */
    boolean hold(@NotNull String orderId) throws ExceptionType;

    /**
     * Releases order
     * 
     * @param order id
     * @return
     * @throws ExceptionType
     */
    boolean unhold(String orderId) throws ExceptionType;

    /**
     * Cancels order
     * 
     * @param order id
     * @return sales order information
     * @throws ExceptionType
     */
    boolean cancel(String orderId) throws ExceptionType;

    /**
     * @param orderId
     * @param status
     * @param comment
     * @param sendEmail if an email must be sent after shipment creation
     * @return
     * @throws ExceptionType
     */
    boolean salesOrderAddComment(String orderId, String status, String comment, boolean sendEmail)
        throws ExceptionType;

    /**
     * Returns list of Magento sales order shipments
     * 
     * @param filters optional list of filters
     * @return list of sales order shipments
     * @throws ExceptionType
     */
    @NotNull
    List<Map<String, Object>> listShipments(String filter) throws ExceptionType;

    /**
     * Retrieves order shipment information
     * 
     * @param Order shipment ID
     * @return sales order shipment information
     * @throws ExceptionType
     */

    Map<String, Object> getShipmentInfo(String shipmentId) throws ExceptionType;

    /**
     * Adds a comment to the shipment
     * 
     * @param shipmentId
     * @param comment
     * @param sendEmail if an email must be sent after shipment creation
     * @param includeCommentInEmail if the comment must be sent in the email
     * @return TODO
     * @throws ExceptionType
     */
    int addShipmentComment(@NotNull String shipmentId,
                           String comment,
                           boolean sendEmail,
                           boolean includeCommentInEmail) throws ExceptionType;

    /**
     * Returns a list of carriers for the given order id
     * 
     * @param order id
     * @return list of carriers
     * @throws ExceptionType
     */
    @NotNull
    List<Carrier> getShipmentCarriers(@NotNull String orderId) throws ExceptionType;

    /**
     * Adds a new tracking number
     * 
     * @param shipmentId TODO
     * @param carrier TODO
     * @param title TODO
     * @param trackNumber TODO
     * @return track ID TODO
     * @throws ExceptionType TODO
     */
    int addShipmentTrack(@NotNull String shipmentId,
                         @NotNull String carrier,
                         @NotNull String title,
                         @NotNull String trackNumber) throws ExceptionType;

    /**
     * @param shipmentId
     * @param trackId
     * @return
     * @throws ExceptionType
     */
    int removeShipmentTrack(@NotNull String shipmentId, @NotNull String trackId) throws ExceptionType;

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
     * @throws ExceptionType
     */
    String createShipment(@NotNull String orderId,
                          @NotNull Map<Integer, Double> itemsQuantities,
                          String comment,
                          boolean sendEmail,
                          boolean includeCommentInEmail) throws ExceptionType;

    /**
     * Returns list of Magento sales order invoices
     * 
     * @param filters optional list of filters
     * @return list of sales order invoices
     * @throws ExceptionType
     */
    List<Map<String, Object>> listInvoices(String filter) throws ExceptionType;

    /**
     * Retrieves order invoice information
     * 
     * @param Order invoice ID
     * @return sales order invoice information
     * @throws ExceptionType
     */
    Map<String, Object> getInvoiceInfo(@NotNull String invoiceId) throws ExceptionType;

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
     * @throws ExceptionType
     */
    String createInvoice(@NotNull String orderId,
                         @NotNull Map<Integer, Double> itemsQuantities,
                         String comment,
                         boolean sendEmail,
                         boolean includeCommentInEmail) throws ExceptionType;

    /**
     * @param invoiceId
     * @param comment
     * @param sendEmail if an email must be sent after shipment creation
     * @param includeCommentInEmail if the comment must be sent in the email
     * @return
     * @throws ExceptionType
     */
    String addInvoiceComment(@NotNull String invoiceId,
                             String comment,
                             boolean sendEmail,
                             boolean includeCommentInEmail) throws ExceptionType;

    /**
     * Captures and invoice
     * 
     * @param invoiceId
     * @return TODO
     * @throws ExceptionType
     */
    boolean captureInvoice(@NotNull String invoiceId) throws ExceptionType;

    /**
     * Voids an invoice
     * 
     * @param invoiceId the invoice id
     * @return TODO
     * @throws ExceptionType
     */
    String voidInvoice(@NotNull String invoiceId) throws ExceptionType;

    /**
     * Cancels an invoice
     * 
     * @param invoiceId the invoice id
     * @return TODO
     * @throws ExceptionType
     */
    String cancelInvoiceOrder(@NotNull String invoiceId) throws ExceptionType;

}
