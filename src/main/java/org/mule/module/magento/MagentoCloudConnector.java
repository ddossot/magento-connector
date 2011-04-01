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

import java.rmi.RemoteException;
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
                initializer.getPortProvider())));
        }
        if (customerClient == null)
        {
            setCustomerClient(MagentoClientAdaptor.adapt(MagentoCustomerClient.class,
                new AxisMagentoCustomerClient(initializer.getPortProvider())));
        }
        if (inventoryClient == null)
        {
            setInventoryClient(MagentoClientAdaptor.adapt(MagentoInventoryClient.class,
                new AxisMagentoInventoryClient(initializer.getPortProvider())));
        }
        if (directoryClient == null)
        {
            setDirectoryClient(MagentoClientAdaptor.adapt(MagentoDirectoryClient.class,
                new AxisMagentoDirectoryClient(initializer.getPortProvider())));
        }
    }

    @Operation
    public void addOrderShipmentComment(@Parameter String shipmentId,
                                       @Parameter String comment,
                                       @Parameter(optional = true, defaultValue = "false") boolean sendEmail,
                                       @Parameter(optional = true, defaultValue = "false") boolean includeCommentInEmail)

    {
        orderClient.addOrderShipmentComment(shipmentId, comment, sendEmail, includeCommentInEmail);
    }

    @Operation
    public int addOrderShipmentTrack(@Parameter String shipmentId,
                                     @Parameter String carrierCode,
                                     @Parameter String title,
                                     @Parameter String trackId)
    {
        return orderClient.addOrderShipmentTrack(shipmentId, carrierCode, title, trackId);
    }

    @Operation
    public void cancelOrder(@Parameter String orderId)
    {
        orderClient.cancelOrder(orderId);
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
    public void holdOrder(@Parameter String orderId)
    {
        orderClient.holdOrder(orderId);
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
    public void deleteOrderShipmentTrack(@Parameter String shipmentId, @Parameter String trackId)
    {
        orderClient.deleteOrderShipmentTrack(shipmentId, trackId);
    }

    @Operation
    public void addOrderComment(@Parameter String orderId,
                                   @Parameter String status,
                                   @Parameter String comment,
                                   @Parameter(optional = true, defaultValue = "false") boolean sendEmail)
    {
        orderClient.addOrderComment(orderId, status, comment, sendEmail);
    }

    @Operation
    public void unholdOrder(@Parameter String orderId)
    {
        orderClient.unholdOrder(orderId);
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
    public void addOrderInvoiceComment(@Parameter String invoiceId,
                                         @Parameter String comment,
                                         @Parameter(optional = true, defaultValue = "false") boolean sendEmail,
                                         @Parameter(optional = true, defaultValue = "false") boolean includeCommentInEmail)
    {
        orderClient.addOrderInvoiceComment(invoiceId, comment, sendEmail, includeCommentInEmail);
    }

    @Operation
    public void captureOrderInvoice(@Parameter String invoiceId)
    {
        orderClient.captureOrderInvoice(invoiceId);
    }

    @Operation
    public void voidOrderInvoice(@Parameter String invoiceId)
    {
        orderClient.voidOrderInvoice(invoiceId);
    }

    @Operation
    public void cancelOrderInvoice(@Parameter String invoiceId)
    {
        orderClient.cancelOrderInvoice(invoiceId);
    }

    @Operation
    public int createCustomerAddress(@Parameter int customerId, @Parameter Map<String, Object> attributes)
    {
        return customerClient.createCustomerAddress(customerId, attributes);
    }

    @Operation
    public int createCustomer(@Parameter Map<String, Object> attributes)
    {
        return customerClient.createCustomer(attributes);
    }

    @Operation
    public void deleteCustomer(@Parameter int customerId)
    {
        customerClient.deleteCustomer(customerId);
    }

    @Operation
    public void deleteCustomerAddress(@Parameter int addressId)
    {
        customerClient.deleteCustomerAddress(addressId);
    }

    @Operation
    public Map<String, String> getCustomer(@Parameter int customerId, @Parameter List<String> attributeNames)

    {
        return customerClient.getCustomer(customerId, attributeNames);
    }

    @Operation
    public Map<String, String> getCustomerAddress(@Parameter int addressId)
    {
        return customerClient.getCustomerAddress(addressId);
    }

    @Operation
    public List<Map<String, Object>> listCustomerAddresses(@Parameter int customerId)
    {
        return customerClient.listCustomerAddresses(customerId);
    }

    @Operation
    public List<Map<String, Object>> listCustomerGroups()
    {
        return customerClient.listCustomerGroups();
    }

    @Operation
    public List<Map<String, Object>> listCustomers(@Parameter String filters)
    {
        return customerClient.listCustomers(filters);
    }

    @Operation
    public void updateCustomer(@Parameter int customerId, @Parameter Map<String, Object> attributes)
    {
        customerClient.updateCustomer(customerId, attributes);
    }

    @Operation
    public void updateCustomerAddress(@Parameter int addressId, @Parameter Map<String, Object> addressData)
    {
        customerClient.updateCustomerAddress(addressId, addressData);
    }
    
    @Operation
    public List<Map<String, Object>> listStockItems(@Parameter List<String> productIdsOrSkus) throws RemoteException
    {
        return inventoryClient.listStockItems(productIdsOrSkus);
    }

    @Operation
    public void updateStockItem(@Parameter String productIdOrSku, @Parameter Map<String, Object> attributes)
        throws RemoteException
    {
        inventoryClient.updateStockItem(productIdOrSku, attributes);
    }

    @Operation
    public List<Map<String, Object>> listDirectoryCountries() throws MagentoException
    {
        return directoryClient.listDirectoryCountries();
    }

    @Operation
    public List<Map<String, Object>> listDirectoryRegions(@Parameter String countryId) throws MagentoException
    {
        return directoryClient.listDirectoryRegions(countryId);
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
    public void setDirectoryClient(MagentoDirectoryClient<?, ?> directoryClient)
    {
        this.directoryClient = (MagentoDirectoryClient<List<Map<String, Object>>, MagentoException>) directoryClient;
    }

    private class PortProviderInitializer
    {
        private DefaultAxisPortProvider provider;

        public AxisPortProvider getPortProvider()
        {
            if (provider == null)
            {
                provider = new DefaultAxisPortProvider(username, password, address);
            }
            return provider;
        }
    }
}
