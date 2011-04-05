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
import org.mule.module.magento.api.catalog.AxisMagentoCatalogClient;
import org.mule.module.magento.api.catalog.MagentoCatalogClient;
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
    @Property
    private String username;
    @Property
    private String password;
    @Property
    private String address;

    private MagentoOrderClient<Map<String, Object>, List<Map<String, Object>>, MagentoException> orderClient;
    private MagentoCustomerClient<Map<String, Object>, List<Map<String, Object>>, MagentoException> customerClient;
    private MagentoInventoryClient<List<Map<String, Object>>, MagentoException> inventoryClient;
    private MagentoDirectoryClient<List<Map<String, Object>>, MagentoException> directoryClient;
    private MagentoCatalogClient<Map<String, Object>, List<Map<String, Object>>, MagentoException> catalogClient;

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
            setOrderClient(new AxisMagentoOrderClient(initializer.getPortProvider()));
        }
        if (customerClient == null)
        {
            setCustomerClient(new AxisMagentoCustomerClient(initializer.getPortProvider()));
        }
        if (inventoryClient == null)
        {
            setInventoryClient(new AxisMagentoInventoryClient(initializer.getPortProvider()));
        }
        if (directoryClient == null)
        {
            setDirectoryClient(new AxisMagentoDirectoryClient(initializer.getPortProvider()));
        }
        if (catalogClient == null)
        {
            setCatalogClient(new AxisMagentoCatalogClient(initializer.getPortProvider()));
        }
    }
    /**
     * Adds a comment to the shipment. 
     * 
     * Example:
     * 
     * {@code <magento:add-order-shipment-comment 
     * 			shipmentId="#[map-payload:shipmentId]" 
     *        	comment="#[map-payload:comment]" 
     *          sendEmail="true" />}
     * 
     * @param shipmentId the shipment's increment id
     * @param comment the comment to add
     * @param sendEmail if an email must be sent after shipment creation
     * @param includeCommentInEmail if the comment must be sent in the email
     */
    @Operation
    public void addOrderShipmentComment(@Parameter String shipmentId,
                                        @Parameter String comment,
                                        @Parameter(optional = true, defaultValue = "false") boolean sendEmail,
                                        @Parameter(optional = true, defaultValue = "false") boolean includeCommentInEmail)

    {
        orderClient.addOrderShipmentComment(shipmentId, comment, sendEmail, includeCommentInEmail);
    }

    /**
     * Adds a new tracking number
     * 
     * Example:
     * 
     * {@code <magento:add-order-shipment-track
	 * 			shipmentId="#[map-payload:shipmentId]" 
	 * 			carrierCode="#[map-payload:carrierCode]"
	 *			title="#[map-payload:title]" 
	 *			trackId="#[map-payload:trackId]" />}
     * 
     * 
     * @param shipmentId the shipment id
     * @param carrierCode the new track's carrier code
     * @param title the new track's title
     * @param trackNumber the new track's number
     * @return the new track's id
     */
    @Operation
    public int addOrderShipmentTrack(@Parameter String shipmentId,
                                     @Parameter String carrierCode,
                                     @Parameter String title,
                                     @Parameter String trackId)
    {
        return orderClient.addOrderShipmentTrack(shipmentId, carrierCode, title, trackId);
    }

    /**
     * Cancels an order
     * 
     * Example:
     * 
     * {@code <magento:cancel-order
     *  	   orderId="#[map-payload:orderId]"/>}
     * 
     * @param orderId the order to cancel
     */
    @Operation
    public void cancelOrder(@Parameter String orderId)
    {
        orderClient.cancelOrder(orderId);
    }

    /**
     * Creates a shipment for order
     * 
     * Example:
     * 
     * {@code <magento:create-order-shipment 
     * 			orderId="#[map-payload:orderId]"
	 * 			comment="#[map-payload:comment]">
	 *			<magento:itemsQuantities>
	 *				<magento:itemsQuantity key="#[map-payload:orderItemId1]" value="#[map-payload:Quantity1]"/>
	 *				<magento:itemsQuantity key="#[map-payload:orderItemId2]" value="#[map-payload:Quantity2]"/>
	 *			</magento:itemsQuantities>
	 *		</magento:create-order-shipment>}
     * 
     * @param orderId the order increment id
     * @param itemsQuantities a map containing an entry per each (orderItemId,
     *            quantity) pair
     * @param comment an optional comment
     * @param sendEmail if an email must be sent after shipment creation
     * @param includeCommentInEmail if the comment must be sent in the email
     * @return the new shipment's id
     */
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

    /**
     * Answers the order properties for the given orderId
     * 
     * Example:
     * 
     * {@code  <magento:get-order orderId="#[map-payload:orderId]" />}
     * 
     * @param orderId the order whose properties to fetch
     * @return a string-object map of order properties
     */
    @Operation
    public Map<String, Object> getOrder(@Parameter String orderId)
    {
        return orderClient.getOrder(orderId);
    }

    /**
     * Retrieves order invoice information
     * 
     * Example:
     * 
     * {@code  	<magento:get-order-invoice invoiceId="#[map-payload:invoiceId]"  />}
     * 
     * @param invoiceId
     * @return the invoice attributes
     */
    @Operation
    public Map<String, Object> getOrderInvoice(@Parameter String invoiceId)
    {
        return orderClient.getOrderInvoice(invoiceId);
    }

    /**
     * Creates an invoice for the given order
     * 
     * Example:
     * 
     * {@code  <magento:get-order-shipment-carriers  orderId="#[map-payload:orderId]"  />}
     * 
     * @param orderId
     * @param itemsQuantities a map containing an entry per each (orderItemId,
     *            quantity) pair
     * @param comment an optional comment
     * @param sendEmail if an email must be sent after shipment creation
     * @param includeCommentInEmail if the comment must be sent in the email
     * @return the new invoice's id
     */
    @Operation
    public List<Carrier> getOrderShipmentCarriers(@Parameter String orderId)
    {
        return orderClient.getOrderShipmentCarriers(orderId);
    }

    /**
     * Adds a comment to the given order's invoice
     * 
     * Example:
     * 
     * {@code  <magento:get-order-shipment 
     * 			shipmentId="#[map-payload:orderShipmentId]" /> }
     * 
     * @param invoiceId the invoice id
     * @param comment the comment to add
     * @param sendEmail if an email must be sent after shipment creation
     * @param includeCommentInEmail if the comment must be sent in the email
     */
    @Operation
    public Map<String, Object> getOrderShipment(@Parameter String shipmentId)
    {
        return orderClient.getOrderShipment(shipmentId);
    }

    /**
     * Puts order on hold
     * 
     * Example:
     * 
     * {@code  <magento:hold-order orderId="#[map-payload:orderId]"/>}
     * 
     * @param order id
     */
    @Operation
    public void holdOrder(@Parameter String orderId)
    {
        orderClient.holdOrder(orderId);
    }

    
    /**
     * Lists order attributes that match the 
     * given filtering expression
     * 
     * Example
     * 
     * {@code <magento:list-orders 
     * 			filter="gt(subtotal, #[map-payload:minSubtotal])"/>}	
     * 
     * @param filters optional filtering expression
     * @return a list of string-object maps
     */
    @Operation
    public List<Map<String, Object>> listOrders(@Parameter(optional = true) String filter)
    {
        return orderClient.listOrders(filter);
    }

    /**
     * Lists order invoices that match the given filtering expression
     * 
     * Example:
     * 
     * {@code <magento:list-orders-invoices filter="notnull(parent_id)" />}	
     * 
     * @param filters optional list of filters
     * @return list of string-object maps order attributes
     */
    @Operation
    public List<Map<String, Object>> listOrdersInvoices(@Parameter(optional = true) String filter)
    {
        return orderClient.listOrdersInvoices(filter);
    }

    /**
     * Lists order shipment atrributes that match the given 
     * optional filtering expression
     * 
     * Example:
     * 
     * {@code <magento:list-orders-shipments filter="null(parent_id)" />}
     * 
     * @param filters optional list of filters
     * @return list of string-object map order shipments attributes
     */
    @Operation
    public List<Map<String, Object>> listOrdersShipments(@Parameter(optional = true) String filter)
    {
        return orderClient.listOrdersShipments(filter);
    }

    /**
     * Deletes the given track of the given order's shipment
     * 
     * <magento:delete-order-shipment-track
	 *		shipmentId="#[map-payload:shipmentId]" trackId="#[map-payload:trackId]" />
     * 
     * @param shipmentId the target shipment id
     * @param trackId the id of the track to delete
     */
    @Operation
    public void deleteOrderShipmentTrack(@Parameter String shipmentId, @Parameter String trackId)
    {
        orderClient.deleteOrderShipmentTrack(shipmentId, trackId);
    }

    /**
     * Adds a comment to the given order id
     * 
     * {@code <magento:add-order-comment 
     * 				orderId="#[map-payload:orderId]"
	 *				status="#[map-payload:status]" 
	 *				comment="#[map-payload:comment]" />}	
     * 
     * @param orderId the order id
     * @param status TODO possible values?
     * @param comment
     * @param sendEmail if an email must be sent after shipment creation
     */
    @Operation
    public void addOrderComment(@Parameter String orderId,
                                @Parameter String status,
                                @Parameter String comment,
                                @Parameter(optional = true, defaultValue = "false") boolean sendEmail)
    {
        orderClient.addOrderComment(orderId, status, comment, sendEmail);
    }

    /**
     * Releases order
     * 
     * Example:
     * 
     * {@code  <magento:unhold-order orderId="#[map-payload:orderId]" />}
     * 
     * @param order id
     */
    @Operation
    public void unholdOrder(@Parameter String orderId)
    {
        orderClient.unholdOrder(orderId);
    }

    //TODO generic creation?
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

    /**
     * Adds a comment to the given order's invoice
     * 
     * Example: 
     * 
     * {@code  <magento:add-order-comment 
     * 			orderId="#[map-payload:orderId]"
     * 			status="#[map-payload:status]" 
     * 			comment="#[map-payload:comment]" }
     * 
     * @param invoiceId the invoice id
     * @param comment the comment to add
     * @param sendEmail if an email must be sent after shipment creation
     * @param includeCommentInEmail if the comment must be sent in the email
     */
    @Operation
    public void addOrderInvoiceComment(@Parameter String invoiceId,
                                       @Parameter String comment,
                                       @Parameter(optional = true, defaultValue = "false") boolean sendEmail,
                                       @Parameter(optional = true, defaultValue = "false") boolean includeCommentInEmail)
    {
        orderClient.addOrderInvoiceComment(invoiceId, comment, sendEmail, includeCommentInEmail);
    }

    /**
     * Captures and invoice
     * 
     * Example: 
     * 
     * {@code  <magento:capture-order-invoice invoiceId="#[map-payload:invoiceId]"  />}
     * 
     * @param invoiceId the invoice to capture
     */
    @Operation
    public void captureOrderInvoice(@Parameter String invoiceId)
    {
        orderClient.captureOrderInvoice(invoiceId);
    }

    /**
     * Voids an invoice
     * 
     * Example: 
     * 
     * {@code  <magento:void-order-invoice invoiceId="#[map-payload:invoiceId]"/>}
     * 
     * @param invoiceId the invoice id
     */
    @Operation
    public void voidOrderInvoice(@Parameter String invoiceId)
    {
        orderClient.voidOrderInvoice(invoiceId);
    }

    /**
     * Cancels an order's invoice
     * 
     * Example: 
     * 
     * {@code  <magento:cancel-order-invoice invoiceId="#[map-payload:invoiceId]"  />}
     * 
     * @param invoiceId the invoice id
     */
    @Operation
    public void cancelOrderInvoice(@Parameter String invoiceId)
    {
        orderClient.cancelOrderInvoice(invoiceId);
    }
    
    /**
     * Creates a new address for the given customer using the given address
     * attributes
     * 
     * {@code <magento:create-customer-address customerId="#[map-payload:customerId]"  >
	 *		    <magento:attributes >
	 *			  <magento:attribute key="city_code" value="#[map-payload:cityCode]"/>
	 *		    </magento:attributes>
	 *	      </magento:create-customer-address>}
     * 
     * @param customerId
     * @param attributes
     * @return a new customer address id
     */
    @Operation
    public int createCustomerAddress(@Parameter int customerId, @Parameter Map<String, Object> attributes)
    {
        return customerClient.createCustomerAddress(customerId, attributes);
    }

    /**
     * Creates a customer with the given attributes
     * 
     * Example: 
     * 
     * {@code 	<magento:create-customer>
	 *		      <magento:attributes >
	 *			    <magento:attribute key="email" value="#[map-payload:email]"/>
	 *			    <magento:attribute key="firstname" value="#[map-payload:firstname]"/>
	 *			    <magento:attribute key="lastname" value="#[map-payload:lastname]"/>
	 * 			    <magento:attribute key="password" value="#[map-payload:password]"/>
	 *		      </magento:attributes>
	 *	       </magento:create-customer>} 
     * 
     * @param attributes the attributes of the new customer
     * @return the new customer id
     */
    @Operation
    public int createCustomer(@Parameter Map<String, Object> attributes)
    {
        return customerClient.createCustomer(attributes);
    }

    /**
     * Deletes a customer given its id
     * 
     * Example:
     * 
     * {@code <magento:delete-customer customerId="#[map-payload:customerId]" />} 
     * 
     * @param customerId
     */
    @Operation
    public void deleteCustomer(@Parameter int customerId)
    {
        customerClient.deleteCustomer(customerId);
    }

    /**
     * Deletes a Customer Address
     * 
     * Example:
     * 
     * {@code <magento:delete-customer-address addressId="#[map-payload:addressId]" />}
     * 
     * @param addressId
     */
    @Operation
    public void deleteCustomerAddress(@Parameter int addressId)
    {
        customerClient.deleteCustomerAddress(addressId);
    }

    /**
     * Answers customer attributes for the given id. Only the selected attributes are
     * retrieved
     * 
     * Example:
     * 
     * {@code <magento:get-customer  customerId="#[map-payload:customerId]"  >
	 *		<magento:attributeNames>
	 *			<magento:attributeName>customer_name</magento:attributeName>
     *				<magento:attributeName>customer_last_name </magento:attributeName>
 	 *			<magento:attributeName>customer_age</magento:attributeName>
	 *		</magento:attributeNames>
	 *	  </magento:get-customer>}
     * 
     * @param customerId
     * @param attributeNames the attributes to retrieve. Not empty
     * @return the attributes map
     * 
     */
    @Operation
    public Map<String, Object> getCustomer(@Parameter int customerId, @Parameter List<String> attributeNames)

    {
        return customerClient.getCustomer(customerId, attributeNames);
    }

    /**
     * Answers the customer address attributes
     * 
     * Example: 
     * {@code <magento:get-customer-address  addressId="#[map-payload:addressId]"/>}
     * 
     * @param addressId
     * @return the customer address attributes map
     */
    @Operation
    public Map<String, Object> getCustomerAddress(@Parameter int addressId)
    {
        return customerClient.getCustomerAddress(addressId);
    }

    /**
     * Lists the customer address for a given customer id
     * 
     * Example: 
     * 
     * {@code  <magento:list-customer-addresses customerId="#[map-payload:customerAddresses]" />}
     * 
     * @param customerId the id of the customer
     * @return a listing of addresses
     */
    @Operation
    public List<Map<String, Object>> listCustomerAddresses(@Parameter int customerId)
    {
        return customerClient.listCustomerAddresses(customerId);
    }

    /**
     * Lists all the customer groups
     * 
     * Example: 
     * 
     * {@code <magento:list-customer-groups />}
     * 
     * @return a listing of groups attributes
     */
    @Operation
    public List<Map<String, Object>> listCustomerGroups()
    {
        return customerClient.listCustomerGroups();
    }

    /**
     * Answers a list of customer attributes for the given filter expression.
     * 
     * Example:
     * 
     * {@code <magento:list-customers filters="gteq(customer_age, #[map-payload:minCustomerAge])" />}
     * 
     * @param filters an optional filtering expression.
     * @return the list of attributes map
     */
    @Operation
    public List<Map<String, Object>> listCustomers(@Parameter(optional=true) String filters)
    {
        return customerClient.listCustomers(filters);
    }

    /**
     * Updates the given customer attributes, for the given customer id. Password can
     * not be updated using this method
     * 
     * Example:
     * 
     * {@code <magento:update-customer customerId="#[map-payload:customerId]">
	 *		    <magento:attributes>
	 *		       <magento:attribute key="lastname" value="#[map-payload:lastname]"/>
	 *		    </magento:attributes>}
     * 
     * @param customerId
     * @param attributes the attributes map
     * 
     */
    @Operation
    public void updateCustomer(@Parameter int customerId, @Parameter Map<String, Object> attributes)
    {
        customerClient.updateCustomer(customerId, attributes);
    }

    /**
     * Updates the given map of customer address attributes, for the given customer address
     * 
     * Example:
     * 
     * {@code <magento:update-customer-address addressId="#[map-payload:addressId]">
	 *		  <magento:attributes>
	 *			<magento:attribute key="street" value="#[map-payload:street]"/>
	 *			<magento:attribute key="region" value="#[map-payload:region]"/>
	 *		   </magento:attributes>
	 *	    </magento:update-customer-address>} 
     * 
     * 
     * @param addressId the customer address to update
     * @param attributes  the address attributes to update
     */
    @Operation
    public void updateCustomerAddress(@Parameter int addressId, @Parameter Map<String, Object> attributes)
    {
        customerClient.updateCustomerAddress(addressId, attributes);
    }

    @Operation
    public List<Map<String, Object>> listStockItems(@Parameter List<String> productIdsOrSkus)
        throws RemoteException
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
    public List<Map<String, Object>> listDirectoryRegions(@Parameter String countryId)
        throws MagentoException
    {
        return directoryClient.listDirectoryRegions(countryId);
    }

    @Operation
    public String assignProductLink(String type,
                                    String product,
                                    String linkedProduct,
                                    Map<String, Object> attributes,
                                    String productIdentifierType) throws RemoteException
    {
        return catalogClient.assignProductLink(type, product, linkedProduct, attributes,
            productIdentifierType);
    }

    @Operation
    public String createProductAttributeMedia(String product,
                                              Map<String, Object> attributes,
                                              String storeView,
                                              String productIdentifierType) throws RemoteException
    {
        return catalogClient.createProductAttributeMedia(product, attributes, storeView,
            productIdentifierType);
    }

    @Operation
    public int deleteProductAttributeMedia(String product, String file, String productIdentifierType)
        throws RemoteException
    {
        return catalogClient.deleteProductAttributeMedia(product, file, productIdentifierType);
    }

    @Operation
    public String deleteProductLink(String type,
                                    String product,
                                    String linkedProduct,
                                    String productIdentifierType) throws RemoteException
    {
        return catalogClient.deleteProductLink(type, product, linkedProduct, productIdentifierType);
    }

    @Operation
    public int getCategoryAttributeStoreView() throws RemoteException
    {
        return catalogClient.getCategoryAttributeStoreView();
    }

    @Operation
    public Map<String, Object> getProductAttributeMedia(String product,
                                                        String file,
                                                        String storeView,
                                                        String productIdentifierType) throws RemoteException
    {
        return catalogClient.getProductAttributeMedia(product, file, storeView, productIdentifierType);
    }

    @Operation
    public int getProductAttributeMediaStoreView() throws RemoteException
    {
        return catalogClient.getProductAttributeMediaStoreView();
    }

    @Operation
    public int getProductAttributeStoreView() throws RemoteException
    {
        return catalogClient.getProductAttributeStoreView();
    }

    @Operation
    public List<Map<String, Object>> listCategoryAttributes() throws RemoteException
    {
        return catalogClient.listCategoryAttributes();
    }

    @Operation
    public List<Map<String, Object>> listCategoryAttributesOptions(String attributeId, String storeView)
        throws RemoteException
    {
        return catalogClient.listCategoryAttributesOptions(attributeId, storeView);
    }

    @Operation
    public List<Map<String, Object>> listProductAttributeMedia(String product,
                                                               String storeView,
                                                               String productIdentifierType)
        throws RemoteException
    {
        return catalogClient.listProductAttributeMedia(product, storeView, productIdentifierType);
    }

    @Operation
    public List<Map<String, Object>> listProductAttributeMediaTypes(String setId) throws RemoteException
    {
        return catalogClient.listProductAttributeMediaTypes(setId);
    }

    @Operation
    public List<Map<String, Object>> listProductAttributeOptions(String attributeId, String storeView)
        throws RemoteException
    {
        return catalogClient.listProductAttributeOptions(attributeId, storeView);
    }

    @Operation
    public List<Map<String, Object>> listProductAttributes(int setId) throws RemoteException
    {
        return catalogClient.listProductAttributes(setId);
    }

    @Operation
    public List<Map<String, Object>> listProductAttributeSets() throws RemoteException
    {
        return catalogClient.listProductAttributeSets();
    }

    @Operation
    public List<Map<String, Object>> listProductAttributeTierPrices(String product,
                                                                    String productIdentifierType)
        throws RemoteException
    {
        return catalogClient.listProductAttributeTierPrices(product, productIdentifierType);
    }

    @Operation
    public List<Map<String, Object>> listProductLink(String type, String product, String productIdentifierType)
        throws RemoteException
    {
        return catalogClient.listProductLink(type, product, productIdentifierType);
    }

    @Operation
    public List<Map<String, Object>> listProductLinkAttributes(String type) throws RemoteException
    {
        return catalogClient.listProductLinkAttributes(type);
    }

    @Operation
    public String[] listProductLinkTypes() throws RemoteException
    {
        return catalogClient.listProductLinkTypes();
    }

    @Operation
    public List<Map<String, Object>> listProductTypes() throws RemoteException
    {
        return catalogClient.listProductTypes();
    }

    @Operation
    public void updateCategoryAttributeStoreView(String storeView) throws RemoteException
    {
        catalogClient.updateCategoryAttributeStoreView(storeView);
    }

    @Operation
    public int updateProductAttributeMedia(String product,
                                           String file,
                                           Map<String, Object> attributes,
                                           String storeView,
                                           String productIdentifierType) throws RemoteException
    {
        return catalogClient.updateProductAttributeMedia(product, file, attributes, storeView,
            productIdentifierType);
    }

    @Operation
    public void updateProductAttributeMediaStoreView(String storeView) throws RemoteException
    {
        catalogClient.updateProductAttributeMediaStoreView(storeView);
    }

    @Operation
    public void updateProductAttributeStoreView(String storeView) throws RemoteException
    {
        catalogClient.updateProductAttributeStoreView(storeView);
    }

    /** FIXME */
    public void updateProductAttributeTierPrices(String product,
                                                 List<Map<String, Object>> attributes,
                                                 String productIdentifierType) throws RemoteException
    {
        catalogClient.updateProductAttributeTierPrices(product, attributes, productIdentifierType);
    }

    @Operation
    public String updateProductLink(String type,
                                    String product,
                                    String linkedProduct,
                                    Map<String, Object> attributes,
                                    String productIdentifierType) throws RemoteException
    {
        return catalogClient.updateProductLink(type, product, linkedProduct, attributes,
            productIdentifierType);
    }

    @SuppressWarnings("unchecked")
    public void setOrderClient(MagentoOrderClient<?, ?, ?> magentoOrderClient)
    {
        this.orderClient = MagentoClientAdaptor.adapt(MagentoOrderClient.class, magentoOrderClient);
    }

    @SuppressWarnings("unchecked")
    public void setCustomerClient(MagentoCustomerClient<?, ?, ?> customerClient)
    {
        this.customerClient = MagentoClientAdaptor.adapt(MagentoCustomerClient.class, customerClient);
    }

    @SuppressWarnings("unchecked")
    public void setInventoryClient(MagentoInventoryClient<?, ?> inventoryClient)
    {
        this.inventoryClient = MagentoClientAdaptor.adapt(MagentoInventoryClient.class, inventoryClient);
    }

    @SuppressWarnings("unchecked")
    public void setDirectoryClient(MagentoDirectoryClient<?, ?> directoryClient)
    {
        this.directoryClient = MagentoClientAdaptor.adapt(MagentoDirectoryClient.class, directoryClient);
    }

    @SuppressWarnings("unchecked")
    public void setCatalogClient(MagentoCatalogClient<?, ?, ?> catalogClient)
    {
        this.catalogClient = MagentoClientAdaptor.adapt(MagentoCatalogClient.class, catalogClient);
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
