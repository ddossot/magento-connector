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

import static org.apache.commons.lang.BooleanUtils.toInteger;
import static org.apache.commons.lang.BooleanUtils.toIntegerObject;

import org.mule.module.magento.api.internal.AssociativeEntity;
import org.mule.module.magento.api.internal.OrderItemIdQty;
import org.mule.module.magento.api.model.Carrier;
import org.mule.module.magento.filters.FiltersParser;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.Validate;

public class AxisMagentoOrderClient implements MagentoOrderClient<Exception>
{
    private final AxisPortProvider provider;

    public AxisMagentoOrderClient(AxisPortProvider provider)
    {
        this.provider = provider;
    }

    private String getSessionId() throws Exception
    {
        return provider.getSessionId();
    }

    public List<Map<String, Object>> list(String filter) throws Exception
    {
        return MagentoMap.toMap(provider.getPort()
            .salesOrderList(getSessionId(), FiltersParser.parse(filter)));
    }

    public Map<String, Object> getInfo(String orderId) throws Exception
    {
        return MagentoMap.toMap(provider.getPort().salesOrderInfo(getSessionId(), orderId));
    }

    public boolean hold(String orderId) throws Exception
    {
        return BooleanUtils.toBoolean(provider.getPort().salesOrderHold(getSessionId(), orderId));
    }

    public boolean unhold(String orderId) throws Exception
    {
        return BooleanUtils.toBoolean(provider.getPort().salesOrderUnhold(getSessionId(), orderId));
    }

    public boolean cancel(String orderId) throws Exception
    {
        return BooleanUtils.toBoolean(provider.getPort().salesOrderCancel(getSessionId(), orderId));
    }

    public boolean salesOrderAddComment(String orderId, String status, String comment, boolean sendEmail)
        throws Exception
    {
        return BooleanUtils.toBoolean(provider.getPort().salesOrderAddComment(getSessionId(), orderId,
            status, comment, toIntegerString(sendEmail)));
    }

    @NotNull
    public List<Map<String, Object>> listShipments(String filter) throws Exception
    {
        return MagentoMap.toMap(provider.getPort().salesOrderShipmentList(getSessionId(),
            FiltersParser.parse(filter)));
    }

    public Map<String, Object> getShipmentInfo(String shipmentId) throws Exception
    {
        return MagentoMap.toMap(provider.getPort().salesOrderShipmentInfo(getSessionId(), shipmentId));
    }

    public int addShipmentComment(@NotNull String shipmentId,
                                  String comment,
                                  boolean sendEmail,
                                  boolean includeCommentInEmail) throws Exception
    {
        return provider.getPort() //
            .salesOrderShipmentAddComment(getSessionId(), shipmentId, comment, toIntegerString(sendEmail),
                toIntegerString(includeCommentInEmail));
    }

    @SuppressWarnings("unchecked")
    @NotNull
    public List<Carrier> getShipmentCarriers(@NotNull String orderId) throws Exception
    {
        Validate.notNull(orderId);
        return (List<Carrier>) CollectionUtils.collect(Arrays.asList(provider.getPort()
            .salesOrderShipmentGetCarriers(getSessionId(), orderId)), new Transformer()
        {
            public Object transform(Object input)
            {
                AssociativeEntity entity = (AssociativeEntity) input;
                return new Carrier(entity.getKey(), entity.getValue());
            }
        });
    }

    public int addShipmentTrack(@NotNull String shipmentId,
                                @NotNull String carrier,
                                @NotNull String title,
                                @NotNull String trackNumber) throws Exception
    {
        Validate.notNull(shipmentId);
        Validate.notNull(carrier);
        Validate.notNull(title);
        Validate.notNull(trackNumber);
        return provider.getPort().salesOrderShipmentAddTrack(getSessionId(), shipmentId, carrier, title,
            trackNumber);
    }

    /**
     * @param shipmentId
     * @param trackId
     * @return
     * @throws Exception
     */
    public int removeShipmentTrack(@NotNull String shipmentId, @NotNull String trackId) throws Exception
    {
        Validate.notNull(shipmentId);
        Validate.notNull(trackId);
        return provider.getPort().salesOrderShipmentRemoveTrack(getSessionId(), shipmentId, trackId);
    }

    public String createShipment(@NotNull String orderId,
                                 @NotNull Map<Integer, Double> itemsQuantities,
                                 String comment,
                                 boolean sendEmail,
                                 boolean includeCommentInEmail) throws Exception
    {
        return provider.getPort().salesOrderShipmentCreate(getSessionId(), orderId, fromMap(itemsQuantities),
            comment, toInteger(sendEmail), toInteger(includeCommentInEmail));
    }

    public List<Map<String, Object>> listInvoices(String filter) throws Exception
    {
        return MagentoMap.toMap(provider.getPort().salesOrderInvoiceList(getSessionId(),
            FiltersParser.parse(filter)));
    }

    public Map<String, Object> getInvoiceInfo(@NotNull String invoiceId) throws Exception
    {
        Validate.notNull(invoiceId);
        return MagentoMap.toMap(provider.getPort().salesOrderInvoiceInfo(getSessionId(), invoiceId));
    }

    public String createInvoice(@NotNull String orderId,
                                @NotNull Map<Integer, Double> itemsQuantities,
                                String comment,
                                boolean sendEmail,
                                boolean includeCommentInEmail) throws Exception
    {
        Validate.notNull(orderId);
        return provider.getPort() //
            .salesOrderInvoiceCreate(getSessionId(), orderId, fromMap(itemsQuantities), comment,
                toIntegerString(sendEmail), toIntegerString(includeCommentInEmail));
    }

    public String addInvoiceComment(@NotNull String invoiceId,
                                    @NotNull String comment,
                                    boolean sendEmail,
                                    boolean includeCommentInEmail) throws Exception
    {
        Validate.notNull(invoiceId);
        Validate.notNull(comment);
        return provider.getPort().salesOrderInvoiceAddComment(getSessionId(), invoiceId, comment,
            toIntegerString(sendEmail), toIntegerString(includeCommentInEmail));
    }

    public boolean captureInvoice(@NotNull String invoiceId) throws Exception
    {
        Validate.notNull(invoiceId);
        return fromIntegerString(provider.getPort().salesOrderInvoiceCapture(getSessionId(), invoiceId));
    }

    public String voidInvoice(@NotNull String invoiceId) throws Exception
    {
        Validate.notNull(invoiceId);
        return provider.getPort().salesOrderInvoiceVoid(getSessionId(), invoiceId);
    }

    public String cancelInvoiceOrder(@NotNull String invoiceId) throws Exception
    {
        Validate.notNull(invoiceId);
        return provider.getPort().salesOrderInvoiceCancel(getSessionId(), invoiceId);
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

    private static boolean fromIntegerString(String value)
    {
        return BooleanUtils.toBoolean(Integer.parseInt(value));
    }

}
