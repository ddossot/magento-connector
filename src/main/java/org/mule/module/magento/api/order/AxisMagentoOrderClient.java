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

import static org.apache.commons.lang.BooleanUtils.toInteger;
import static org.apache.commons.lang.BooleanUtils.toIntegerObject;

import org.mule.module.magento.api.AbstractMagentoClient;
import org.mule.module.magento.api.AxisPortProvider;
import org.mule.module.magento.api.MagentoMap;
import org.mule.module.magento.api.internal.AssociativeEntity;
import org.mule.module.magento.api.internal.OrderItemIdQty;
import org.mule.module.magento.api.order.model.Carrier;
import org.mule.module.magento.filters.FiltersParser;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.Validate;

public class AxisMagentoOrderClient extends AbstractMagentoClient implements MagentoOrderClient<RemoteException>
{

    public AxisMagentoOrderClient(AxisPortProvider provider)
    {
        super(provider);
    }

    public List<Map<String, Object>> list(String filter) throws RemoteException
    {
        return MagentoMap.toMap(getPort().salesOrderList(getSessionId(), FiltersParser.parse(filter)));
    }

    public Map<String, Object> getInfo(String orderId) throws RemoteException
    {
        return MagentoMap.toMap(getPort().salesOrderInfo(getSessionId(), orderId));
    }

    public boolean hold(String orderId) throws RemoteException
    {
        return BooleanUtils.toBoolean(getPort().salesOrderHold(getSessionId(), orderId));
    }

    public boolean unhold(@NotNull String orderId) throws RemoteException
    {
        Validate.notNull(orderId);
        return BooleanUtils.toBoolean(getPort().salesOrderUnhold(getSessionId(), orderId));
    }

    public boolean cancel(@NotNull String orderId) throws RemoteException
    {
        Validate.notNull(orderId);
        return BooleanUtils.toBoolean(getPort().salesOrderCancel(getSessionId(), orderId));
    }

    public boolean addComment(@NotNull String orderId,
                              @NotNull String status,
                              @NotNull String comment,
                              boolean sendEmail) throws RemoteException
    {
        Validate.notNull(orderId);
        Validate.notNull(status);
        Validate.notNull(comment);
        return BooleanUtils.toBoolean(getPort().salesOrderAddComment(getSessionId(), orderId, status,
            comment, toIntegerString(sendEmail)));
    }

    @NotNull
    public List<Map<String, Object>> listShipments(String filter) throws RemoteException
    {
        return MagentoMap.toMap(getPort().salesOrderShipmentList(getSessionId(), FiltersParser.parse(filter)));
    }

    public Map<String, Object> getShipmentInfo(String shipmentId) throws RemoteException
    {
        return MagentoMap.toMap(getPort().salesOrderShipmentInfo(getSessionId(), shipmentId));
    }

    public int addShipmentComment(@NotNull String shipmentId,
                                  String comment,
                                  boolean sendEmail,
                                  boolean includeCommentInEmail) throws RemoteException
    {
        return getPort().salesOrderShipmentAddComment(getSessionId(), shipmentId, comment,
            toIntegerString(sendEmail), toIntegerString(includeCommentInEmail));
    }

    @SuppressWarnings("unchecked")
    @NotNull
    public List<Carrier> getShipmentCarriers(@NotNull String orderId) throws RemoteException
    {
        Validate.notNull(orderId);
        return (List<Carrier>) CollectionUtils.collect(Arrays.asList(getPort().salesOrderShipmentGetCarriers(
            getSessionId(), orderId)), new Transformer()
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
                                @NotNull String trackNumber) throws RemoteException
    {
        Validate.notNull(shipmentId);
        Validate.notNull(carrier);
        Validate.notNull(title);
        Validate.notNull(trackNumber);
        return getPort().salesOrderShipmentAddTrack(getSessionId(), shipmentId, carrier, title, trackNumber);
    }

    /**
     * @param shipmentId
     * @param trackId
     * @return
     * @throws RemoteException
     */
    public int removeShipmentTrack(@NotNull String shipmentId, @NotNull String trackId) throws RemoteException
    {
        Validate.notNull(shipmentId);
        Validate.notNull(trackId);
        return getPort().salesOrderShipmentRemoveTrack(getSessionId(), shipmentId, trackId);
    }

    public String createShipment(@NotNull String orderId,
                                 @NotNull Map<Integer, Double> itemsQuantities,
                                 String comment,
                                 boolean sendEmail,
                                 boolean includeCommentInEmail) throws RemoteException
    {
        return getPort().salesOrderShipmentCreate(getSessionId(), orderId, fromMap(itemsQuantities), comment,
            toInteger(sendEmail), toInteger(includeCommentInEmail));
    }

    public List<Map<String, Object>> listInvoices(String filter) throws RemoteException
    {
        return MagentoMap.toMap(getPort().salesOrderInvoiceList(getSessionId(), FiltersParser.parse(filter)));
    }

    public Map<String, Object> getInvoiceInfo(@NotNull String invoiceId) throws RemoteException
    {
        Validate.notNull(invoiceId);
        return MagentoMap.toMap(getPort().salesOrderInvoiceInfo(getSessionId(), invoiceId));
    }

    public String createInvoice(@NotNull String orderId,
                                @NotNull Map<Integer, Double> itemsQuantities,
                                String comment,
                                boolean sendEmail,
                                boolean includeCommentInEmail) throws RemoteException
    {
        Validate.notNull(orderId);
        return getPort().salesOrderInvoiceCreate(getSessionId(), orderId, fromMap(itemsQuantities), comment,
            toIntegerString(sendEmail), toIntegerString(includeCommentInEmail));
    }

    public String addInvoiceComment(@NotNull String invoiceId,
                                    @NotNull String comment,
                                    boolean sendEmail,
                                    boolean includeCommentInEmail) throws RemoteException
    {
        Validate.notNull(invoiceId);
        Validate.notNull(comment);
        return getPort().salesOrderInvoiceAddComment(getSessionId(), invoiceId, comment,
            toIntegerString(sendEmail), toIntegerString(includeCommentInEmail));
    }

    public boolean captureInvoice(@NotNull String invoiceId) throws RemoteException
    {
        Validate.notNull(invoiceId);
        return fromIntegerString(getPort().salesOrderInvoiceCapture(getSessionId(), invoiceId));
    }

    public String voidInvoice(@NotNull String invoiceId) throws RemoteException
    {
        Validate.notNull(invoiceId);
        return getPort().salesOrderInvoiceVoid(getSessionId(), invoiceId);
    }

    public String cancelInvoiceOrder(@NotNull String invoiceId) throws RemoteException
    {
        Validate.notNull(invoiceId);
        return getPort().salesOrderInvoiceCancel(getSessionId(), invoiceId);
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
