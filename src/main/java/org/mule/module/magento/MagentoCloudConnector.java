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
import org.mule.module.magento.api.AxisPortProvider;
import org.mule.module.magento.api.DefaultAxisPortProvider;
import org.mule.module.magento.api.MagentoClientAdaptor;
import org.mule.module.magento.api.MagentoException;
import org.mule.module.magento.api.customer.AxisMagentoInventoryClient;
import org.mule.module.magento.api.customer.MagentoInventoryClient;
import org.mule.module.magento.api.directory.AxisMagentoDirectoryClient;
import org.mule.module.magento.api.directory.MagentoDirectoryClient;
import org.mule.module.magento.api.inventory.AxisMagentoCustomerClient;
import org.mule.module.magento.api.inventory.MagentoCustomerClient;
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
 * @author flbulgarelli
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
    private MagentoCustomerClient<Map<String, String>, List<Map<String, Object>>, MagentoException> customerClient;
    private MagentoInventoryClient<List<Map<String, Object>>, MagentoException> inventoryClient;
    private MagentoDirectoryClient<List<Map<String, Object>>, MagentoException> directoryClient;

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
        PortProviderInitializer initializer = new PortProviderInitializer();
        if (orderClient == null)
        {
            setOrderClient(MagentoClientAdaptor.adapt(MagentoOrderClient.class, new AxisMagentoOrderClient(
                initializer.getPorProvider())));
        }
        if (customerClient == null)
        {
            setCustomerClient(MagentoClientAdaptor.adapt(MagentoCustomerClient.class,
                new AxisMagentoCustomerClient(initializer.getPorProvider())));
        }
        if (inventoryClient == null)
        {
            setInventoryClient(MagentoClientAdaptor.adapt(MagentoInventoryClient.class,
                new AxisMagentoInventoryClient(initializer.getPorProvider())));
        }
        if (directoryClient == null)
        {
            setDirectoryClient(MagentoClientAdaptor.adapt(MagentoDirectoryClient.class,
                new AxisMagentoDirectoryClient(initializer.getPorProvider())));
        }
    }

    @Operation
    public int addOrderShipmentComment(@Parameter String shipmentId,
                                       @Parameter String comment,
                                       @Parameter(optional = true, defaultValue = "false") boolean sendEmail,
                                       @Parameter(optional = true, defaultValue = "false") boolean includeCommentInEmail)

    {
        return orderClient.addOrderShipmentComment(shipmentId, comment, sendEmail, includeCommentInEmail);
    }

    @Operation
    public int addOrderShipmentTrack(@Parameter String shipmentId,
                                     @Parameter String carrier,
                                     @Parameter String title,
                                     @Parameter String trackId)
    {
        return orderClient.addOrderShipmentTrack(shipmentId, carrier, title, trackId);
    }

    @Operation
    public boolean cancelOrder(@Parameter String orderId)
    {
        return orderClient.cancelOrder(orderId);
    }

    @Operation
    public String createOrderShipment(@Parameter String orderId,
                                      @Parameter Map<Integer, Double> itemsQuantities,
                                      @Parameter(optional = true) String comment,
                                      @Parameter(optional = true, defaultValue = "false") boolean sendEmail,
                                      @Parameter(optional = true, defaultValue = "false") boolean includeCommentInEmail)

    {
        return orderClient.createOrderShipment(orderId, itemsQuantities, comment, sendEmail,
            includeCommentInEmail);
    }

    @Operation
    public Map<String, Object> getOrder(@Parameter String orderId)
    {
        return orderClient.getOrder(orderId);
    }

    @Operation
    public Map<String, Object> getOrderInvoice(@Parameter String invoiceId)
    {
        return orderClient.getOrderInvoice(invoiceId);
    }

    @Operation
    public List<Carrier> getOrderShipmentCarriers(@Parameter String orderId)
    {
        return orderClient.getOrderShipmentCarriers(orderId);
    }

    @Operation
    public Map<String, Object> getOrderShipment(@Parameter String shipmentId)
    {
        return orderClient.getOrderShipment(shipmentId);
    }

    @Operation
    public boolean holdOrder(@Parameter String orderId)
    {
        return orderClient.holdOrder(orderId);
    }

    @Operation
    public List<Map<String, Object>> listOrders(@Parameter(optional = true) String filter)
    {
        return orderClient.listOrders(filter);
    }

    @Operation
    public List<Map<String, Object>> listOrdersInvoices(@Parameter(optional = true) String filter)

    {
        return orderClient.listOrdersInvoices(filter);
    }

    @Operation
    public List<Map<String, Object>> listOrdersShipments(@Parameter(optional = true) String filter)

    {
        return orderClient.listOrdersShipments(filter);
    }

    @Operation
    public int deleteOrderShipmentTrack(@Parameter String shipmentId, @Parameter String trackId)

    {
        return orderClient.deleteOrderShipmentTrack(shipmentId, trackId);
    }

    @Operation
    public boolean addOrderComment(@Parameter String orderId,
                                   @Parameter String status,
                                   @Parameter String comment,
                                   @Parameter(optional = true, defaultValue = "false") boolean sendEmail)

    {
        return orderClient.addOrderComment(orderId, status, comment, sendEmail);
    }

    @Operation
    public boolean unholdOrder(@Parameter String orderId)
    {
        return orderClient.unholdOrder(orderId);
    }

    @Operation
    public String createOrderInvoice(@Parameter String orderId,
                                     @Parameter Map<Integer, Double> itemsQuantities,
                                     @Parameter(optional = true) String comment,
                                     @Parameter(optional = true, defaultValue = "false") boolean sendEmail,
                                     @Parameter(optional = true, defaultValue = "false") boolean includeCommentInEmail)

    {
        return orderClient.createOrderInvoice(orderId, itemsQuantities, comment, sendEmail,
            includeCommentInEmail);
    }

    @Operation
    public String addOrderInvoiceComment(@Parameter String invoiceId,
                                         @Parameter String comment,
                                         @Parameter(optional = true, defaultValue = "false") boolean sendEmail,
                                         @Parameter(optional = true, defaultValue = "false") boolean includeCommentInEmail)

    {
        return orderClient.addOrderInvoiceComment(invoiceId, comment, sendEmail, includeCommentInEmail);
    }

    @Operation
    public boolean captureOrderInvoice(@Parameter String invoiceId)
    {
        return orderClient.captureOrderInvoice(invoiceId);
    }

    @Operation
    public String voidOrderInvoice(@Parameter String invoiceId)
    {
        return orderClient.voidOrderInvoice(invoiceId);
    }

    @Operation
    public String cancelOrderInvoice(@Parameter String invoiceId)
    {
        return orderClient.cancelOrderInvoice(invoiceId);
    }

    @Operation
    public int createCusomerAddress(int customerId, Map<String, Object> attributes) throws MagentoException
    {
        return customerClient.createCusomerAddress(customerId, attributes);
    }

    @Operation
    public int createCustomer(Map<String, Object> attributes) throws MagentoException
    {
        return customerClient.createCustomer(attributes);
    }

    @Operation
    public boolean deleteCustomer(int customerId) throws MagentoException
    {
        return customerClient.deleteCustomer(customerId);
    }

    @Operation
    public boolean deleteCustomerAddress(int addressId) throws MagentoException
    {
        return customerClient.deleteCustomerAddress(addressId);
    }

    @Operation
    public Map<String, String> getCustomer(int customerId, List<String> attributeNames)
        throws MagentoException
    {
        return customerClient.getCustomer(customerId, attributeNames);
    }

    @Operation
    public Map<String, String> getCustomerAddress(int addressId) throws MagentoException
    {
        return customerClient.getCustomerAddress(addressId);
    }

    @Operation
    public List<Map<String, Object>> listCustomerAddresses(int customerId) throws MagentoException
    {
        return customerClient.listCustomerAddresses(customerId);
    }

    @Operation
    public List<Map<String, Object>> listCustomerGroups() throws MagentoException
    {
        return customerClient.listCustomerGroups();
    }

    @Operation
    public List<Map<String, Object>> listCustomers(String filters) throws MagentoException
    {
        return customerClient.listCustomers(filters);
    }

    @Operation
    public boolean updateCustomer(int customerId, Map<String, Object> attributes) throws MagentoException
    {
        return customerClient.updateCustomer(customerId, attributes);
    }

    @Operation
    public boolean updateCustomerAddress(int addressId, Map<String, Object> addressData)
        throws MagentoException
    {
        return customerClient.updateCustomerAddress(addressId, addressData);
    }

    @SuppressWarnings("unchecked")
    public void setOrderClient(MagentoOrderClient<?> magentoOrderClient)
    {
        this.orderClient = (MagentoOrderClient<MagentoException>) magentoOrderClient;
    }

    @SuppressWarnings("unchecked")
    public void setCustomerClient(MagentoCustomerClient<?, ?, ?> customerClient)
    {
        this.customerClient = (MagentoCustomerClient<Map<String, String>, List<Map<String, Object>>, MagentoException>) customerClient;
    }

    @SuppressWarnings("unchecked")
    public void setInventoryClient(MagentoInventoryClient<?, ?> inventoryClient)
    {
        this.inventoryClient = (MagentoInventoryClient<List<Map<String, Object>>, MagentoException>) inventoryClient;
    }
    
    @SuppressWarnings("unchecked")
    public void setDirectoryClient(MagentoDirectoryClient<?,?> directoryClient)
    {
        this.directoryClient = (MagentoDirectoryClient<List<Map<String, Object>>, MagentoException>) directoryClient;
    }

    // TODO ids shoudl be integrals
    private class PortProviderInitializer
    {
        private DefaultAxisPortProvider provider;

        public AxisPortProvider getPorProvider()
        {
            if (provider != null)
            {
                provider = new DefaultAxisPortProvider(username, password, address);
            }
            return provider;
        }
    }
}
