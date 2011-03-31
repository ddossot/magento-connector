/**
 * Mule Magento Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.magento.api.order;

import org.mule.module.magento.api.order.model.Carrier;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

/**
 * Facade for the Magento Sales Order API. This Facade uses order, shipments and
 * invoices ids called, {@code orderId}, {@code shipmentId} and {@code invoiceId}
 * respectively. However, those Ids correspond internally to the Magento's {@code
 * xxxIncrementId}. Actual {@code xxxId} are used internally by magento and not
 * exposed by the webservice in most operations
 * 
 * @author flbulgarelli
 * @param <ExceptionType> the type of exception that this client throws
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
    @NotNull
    List<Map<String, Object>> listOrders(@NotNull String filter) throws ExceptionType;

    /**
     * Answers the order properties for the given orderId
     * 
     * @param orderId
     * @return the order properties
     * @throws ExceptionType
     */
    @NotNull
    Map<String, Object> getOrder(@NotNull String orderId) throws ExceptionType;

    /**
     * Puts order on hold
     * 
     * @param order id
     * @return
     * @throws ExceptionType
     */
    boolean holdOrder(@NotNull String orderId) throws ExceptionType;

    /**
     * Releases order
     * 
     * @param order id
     * @return
     * @throws ExceptionType
     */
    boolean unholdOrder(String orderId) throws ExceptionType;

    /**
     * Cancels order
     * 
     * @param order id
     * @return sales order information
     * @throws ExceptionType
     */
    boolean cancelOrder(@NotNull String orderId) throws ExceptionType;

    /**
     * Adds a comment to the given order id
     * 
     * @param orderId the order id
     * @param status TODO possible values?
     * @param comment
     * @param sendEmail if an email must be sent after shipment creation
     * @return TODO
     * @throws ExceptionType
     */
    boolean addOrderComment(@NotNull String orderId,
                       @NotNull String status,
                       @NotNull String comment,
                       boolean sendEmail) throws ExceptionType;

    /**
     * Returns list of Magento sales order shipments
     * 
     * @param filters optional list of filters
     * @return list of sales order shipments attributes
     * @throws ExceptionType
     */
    @NotNull
    List<Map<String, Object>> listOrdersShipments(String filter) throws ExceptionType;

    /**
     * Retrieves order shipment information
     * 
     * @param Order shipment ID
     * @return sales order shipment information
     * @throws ExceptionType
     */

    Map<String, Object> getOrderShipment(String shipmentId) throws ExceptionType;

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
    int addOrderShipmentComment(@NotNull String shipmentId,
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
    List<Carrier> getOrderShipmentCarriers(@NotNull String orderId) throws ExceptionType;

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
    int addOrderShipmentTrack(@NotNull String shipmentId,
                         @NotNull String carrier,
                         @NotNull String title,
                         @NotNull String trackNumber) throws ExceptionType;

    /**
     * @param shipmentId
     * @param trackId
     * @return
     * @throws ExceptionType
     */
    int deleteOrderShipmentTrack(@NotNull String shipmentId, @NotNull String trackId) throws ExceptionType;

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
    String createOrderShipment(@NotNull String orderId,
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
    List<Map<String, Object>> listOrdersInvoices(String filter) throws ExceptionType;

    /**
     * Retrieves order invoice information
     * 
     * @param Order invoice ID
     * @return sales order invoice attributes
     * @throws ExceptionType
     */
    Map<String, Object> getOrderInvoice(@NotNull String invoiceId) throws ExceptionType;

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
    String createOrderInvoice(@NotNull String orderId,
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
    String addOrderInvoiceComment(@NotNull String invoiceId,
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
    boolean captureOrderInvoice(@NotNull String invoiceId) throws ExceptionType;

    /**
     * Voids an invoice
     * 
     * @param invoiceId the invoice id
     * @return TODO
     * @throws ExceptionType
     */
    String voidOrderInvoice(@NotNull String invoiceId) throws ExceptionType;

    /**
     * Cancels an invoice
     * 
     * @param invoiceId the invoice id
     * @return TODO
     * @throws ExceptionType
     */
    String cancelOrderInvoice(@NotNull String invoiceId) throws ExceptionType;

}
