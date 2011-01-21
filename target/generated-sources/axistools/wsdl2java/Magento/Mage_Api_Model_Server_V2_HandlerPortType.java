/**
 * Mage_Api_Model_Server_V2_HandlerPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package Magento;

public interface Mage_Api_Model_Server_V2_HandlerPortType extends java.rmi.Remote {

    /**
     * End web service session
     */
    public boolean endSession(java.lang.String sessionId) throws java.rmi.RemoteException;

    /**
     * Login user and retrive session id
     */
    public java.lang.String login(java.lang.String username, java.lang.String apiKey) throws java.rmi.RemoteException;

    /**
     * Start web service session
     */
    public java.lang.String startSession() throws java.rmi.RemoteException;

    /**
     * List of available resources
     */
    public Magento.ApiEntity[] resources(java.lang.String sessionId) throws java.rmi.RemoteException;

    /**
     * List of global faults
     */
    public Magento.ExistsFaltureEntity[] globalFaults(java.lang.String sessionId) throws java.rmi.RemoteException;

    /**
     * List of resource faults
     */
    public Magento.ExistsFaltureEntity[] resourceFaults(java.lang.String resourceName, java.lang.String sessionId) throws java.rmi.RemoteException;

    /**
     * List of countries
     */
    public Magento.DirectoryCountryEntity[] directoryCountryList(java.lang.String sessionId) throws java.rmi.RemoteException;

    /**
     * List of regions in specified country
     */
    public Magento.DirectoryRegionEntity[] directoryRegionList(java.lang.String sessionId, java.lang.String country) throws java.rmi.RemoteException;

    /**
     * Retrieve customers
     */
    public Magento.CustomerCustomerEntity[] customerCustomerList(java.lang.String sessionId, Magento.Filters filters) throws java.rmi.RemoteException;

    /**
     * Create customer
     */
    public int customerCustomerCreate(java.lang.String sessionId, Magento.CustomerCustomerEntityToCreate customerData) throws java.rmi.RemoteException;

    /**
     * Retrieve customer data
     */
    public Magento.CustomerCustomerEntity customerCustomerInfo(java.lang.String sessionId, int customerId, java.lang.String[] attributes) throws java.rmi.RemoteException;

    /**
     * Update customer data
     */
    public boolean customerCustomerUpdate(java.lang.String sessionId, int customerId, Magento.CustomerCustomerEntityToCreate customerData) throws java.rmi.RemoteException;

    /**
     * Delete customer
     */
    public boolean customerCustomerDelete(java.lang.String sessionId, int customerId) throws java.rmi.RemoteException;

    /**
     * Retrieve customer groups
     */
    public Magento.CustomerGroupEntity[] customerGroupList(java.lang.String sessionId) throws java.rmi.RemoteException;

    /**
     * Retrieve customer addresses
     */
    public Magento.CustomerAddressEntityItem[] customerAddressList(java.lang.String sessionId, int customerId) throws java.rmi.RemoteException;

    /**
     * Create customer address
     */
    public int customerAddressCreate(java.lang.String sessionId, int customerId, Magento.CustomerAddressEntityCreate addressData) throws java.rmi.RemoteException;

    /**
     * Retrieve customer address data
     */
    public Magento.CustomerAddressEntityItem customerAddressInfo(java.lang.String sessionId, int addressId) throws java.rmi.RemoteException;

    /**
     * Update customer address data
     */
    public boolean customerAddressUpdate(java.lang.String sessionId, int addressId, Magento.CustomerAddressEntityCreate addressData) throws java.rmi.RemoteException;

    /**
     * Delete customer address
     */
    public boolean customerAddressDelete(java.lang.String sessionId, int addressId) throws java.rmi.RemoteException;

    /**
     * Set_Get current store view
     */
    public int catalogCategoryCurrentStore(java.lang.String sessionId, java.lang.String storeView) throws java.rmi.RemoteException;

    /**
     * Retrieve hierarchical tree of categories.
     */
    public Magento.CatalogCategoryTree catalogCategoryTree(java.lang.String sessionId, java.lang.String parentId, java.lang.String storeView) throws java.rmi.RemoteException;

    /**
     * Retrieve hierarchical tree of categories.
     */
    public Magento.CatalogCategoryEntityNoChildren[] catalogCategoryLevel(java.lang.String sessionId, java.lang.String website, java.lang.String storeView, java.lang.String parentCategory) throws java.rmi.RemoteException;

    /**
     * Retrieve hierarchical tree of categories.
     */
    public Magento.CatalogCategoryInfo catalogCategoryInfo(java.lang.String sessionId, int categoryId, java.lang.String storeView, java.lang.String[] attributes) throws java.rmi.RemoteException;

    /**
     * Create new category and return its id.
     */
    public int catalogCategoryCreate(java.lang.String sessionId, int parentId, Magento.CatalogCategoryEntityCreate categoryData, java.lang.String storeView) throws java.rmi.RemoteException;

    /**
     * Update category
     */
    public boolean catalogCategoryUpdate(java.lang.String sessionId, int categoryId, Magento.CatalogCategoryEntityCreate categoryData, java.lang.String storeView) throws java.rmi.RemoteException;

    /**
     * Move category in tree
     */
    public boolean catalogCategoryMove(java.lang.String sessionId, int categoryId, int parentId, java.lang.String afterId) throws java.rmi.RemoteException;

    /**
     * Delete category
     */
    public boolean catalogCategoryDelete(java.lang.String sessionId, int categoryId) throws java.rmi.RemoteException;

    /**
     * Retrieve list of assigned products
     */
    public Magento.CatalogAssignedProduct[] catalogCategoryAssignedProducts(java.lang.String sessionId, int categoryId) throws java.rmi.RemoteException;

    /**
     * Assign product to category
     */
    public boolean catalogCategoryAssignProduct(java.lang.String sessionId, int categoryId, java.lang.String product, java.lang.String position, java.lang.String productIdentifierType) throws java.rmi.RemoteException;

    /**
     * Update assigned product
     */
    public boolean catalogCategoryUpdateProduct(java.lang.String sessionId, int categoryId, java.lang.String product, java.lang.String position, java.lang.String productIdentifierType) throws java.rmi.RemoteException;

    /**
     * Remove product assignment from category
     */
    public boolean catalogCategoryRemoveProduct(java.lang.String sessionId, int categoryId, java.lang.String product, java.lang.String productIdentifierType) throws java.rmi.RemoteException;

    /**
     * Set/Get current store view
     */
    public int catalogCategoryAttributeCurrentStore(java.lang.String sessionId, java.lang.String storeView) throws java.rmi.RemoteException;

    /**
     * Retrieve category attributes
     */
    public Magento.CatalogAttributeEntity[] catalogCategoryAttributeList(java.lang.String sessionId) throws java.rmi.RemoteException;

    /**
     * Retrieve attribute options
     */
    public Magento.CatalogAttributeOptionEntity[] catalogCategoryAttributeOptions(java.lang.String sessionId, java.lang.String attributeId, java.lang.String storeView) throws java.rmi.RemoteException;

    /**
     * Set/Get current store view
     */
    public int catalogProductCurrentStore(java.lang.String sessionId, java.lang.String storeView) throws java.rmi.RemoteException;

    /**
     * Retrieve products list by filters
     */
    public Magento.CatalogProductEntity[] catalogProductList(java.lang.String sessionId, Magento.Filters filters, java.lang.String storeView) throws java.rmi.RemoteException;

    /**
     * Retrieve product
     */
    public Magento.CatalogProductReturnEntity catalogProductInfo(java.lang.String sessionId, java.lang.String product, java.lang.String storeView, Magento.CatalogProductRequestAttributes attributes, java.lang.String productIdentifierType) throws java.rmi.RemoteException;

    /**
     * Create new product and return product id
     */
    public int catalogProductCreate(java.lang.String sessionId, java.lang.String type, java.lang.String set, java.lang.String sku, Magento.CatalogProductCreateEntity productData) throws java.rmi.RemoteException;

    /**
     * Update product
     */
    public boolean catalogProductUpdate(java.lang.String sessionId, java.lang.String product, Magento.CatalogProductCreateEntity productData, java.lang.String storeView, java.lang.String productIdentifierType) throws java.rmi.RemoteException;

    /**
     * Update product special price
     */
    public int catalogProductSetSpecialPrice(java.lang.String sessionId, java.lang.String product, java.lang.String specialPrice, java.lang.String fromDate, java.lang.String toDate, java.lang.String storeView, java.lang.String productIdentifierType) throws java.rmi.RemoteException;

    /**
     * Get product special price data
     */
    public Magento.CatalogProductReturnEntity catalogProductGetSpecialPrice(java.lang.String sessionId, java.lang.String product, java.lang.String storeView, java.lang.String productIdentifierType) throws java.rmi.RemoteException;

    /**
     * Delete product
     */
    public int catalogProductDelete(java.lang.String sessionId, java.lang.String product, java.lang.String productIdentifierType) throws java.rmi.RemoteException;

    /**
     * Set/Get current store view
     */
    public int catalogProductAttributeCurrentStore(java.lang.String sessionId, java.lang.String storeView) throws java.rmi.RemoteException;

    /**
     * Retrieve attribute list
     */
    public Magento.CatalogAttributeEntity[] catalogProductAttributeList(java.lang.String sessionId, int setId) throws java.rmi.RemoteException;

    /**
     * Retrieve attribute options
     */
    public Magento.CatalogAttributeOptionEntity[] catalogProductAttributeOptions(java.lang.String sessionId, java.lang.String attributeId, java.lang.String storeView) throws java.rmi.RemoteException;

    /**
     * Retrieve product attribute sets
     */
    public Magento.CatalogProductAttributeSetEntity[] catalogProductAttributeSetList(java.lang.String sessionId) throws java.rmi.RemoteException;

    /**
     * Retrieve product types
     */
    public Magento.CatalogProductTypeEntity[] catalogProductTypeList(java.lang.String sessionId) throws java.rmi.RemoteException;

    /**
     * Retrieve product tier prices
     */
    public Magento.CatalogProductTierPriceEntity[] catalogProductAttributeTierPriceInfo(java.lang.String sessionId, java.lang.String product, java.lang.String productIdentifierType) throws java.rmi.RemoteException;

    /**
     * Update product tier prices
     */
    public int catalogProductAttributeTierPriceUpdate(java.lang.String sessionId, java.lang.String product, Magento.CatalogProductTierPriceEntity[] tier_price, java.lang.String productIdentifierType) throws java.rmi.RemoteException;

    /**
     * Set/Get current store view
     */
    public int catalogProductAttributeMediaCurrentStore(java.lang.String sessionId, java.lang.String storeView) throws java.rmi.RemoteException;

    /**
     * Retrieve product image list
     */
    public Magento.CatalogProductImageEntity[] catalogProductAttributeMediaList(java.lang.String sessionId, java.lang.String product, java.lang.String storeView, java.lang.String productIdentifierType) throws java.rmi.RemoteException;

    /**
     * Retrieve product image data
     */
    public Magento.CatalogProductImageEntity catalogProductAttributeMediaInfo(java.lang.String sessionId, java.lang.String product, java.lang.String file, java.lang.String storeView, java.lang.String productIdentifierType) throws java.rmi.RemoteException;

    /**
     * Retrieve product image types
     */
    public Magento.CatalogProductAttributeMediaTypeEntity[] catalogProductAttributeMediaTypes(java.lang.String sessionId, java.lang.String setId) throws java.rmi.RemoteException;

    /**
     * Upload new product image
     */
    public java.lang.String catalogProductAttributeMediaCreate(java.lang.String sessionId, java.lang.String product, Magento.CatalogProductAttributeMediaCreateEntity data, java.lang.String storeView, java.lang.String productIdentifierType) throws java.rmi.RemoteException;

    /**
     * Update product image
     */
    public int catalogProductAttributeMediaUpdate(java.lang.String sessionId, java.lang.String product, java.lang.String file, Magento.CatalogProductAttributeMediaCreateEntity data, java.lang.String storeView, java.lang.String productIdentifierType) throws java.rmi.RemoteException;

    /**
     * Remove product image
     */
    public int catalogProductAttributeMediaRemove(java.lang.String sessionId, java.lang.String product, java.lang.String file, java.lang.String productIdentifierType) throws java.rmi.RemoteException;

    /**
     * Retrieve linked products
     */
    public Magento.CatalogProductLinkEntity[] catalogProductLinkList(java.lang.String sessionId, java.lang.String type, java.lang.String product, java.lang.String productIdentifierType) throws java.rmi.RemoteException;

    /**
     * Assign product link
     */
    public java.lang.String catalogProductLinkAssign(java.lang.String sessionId, java.lang.String type, java.lang.String product, java.lang.String linkedProduct, Magento.CatalogProductLinkEntity data, java.lang.String productIdentifierType) throws java.rmi.RemoteException;

    /**
     * Update product link
     */
    public java.lang.String catalogProductLinkUpdate(java.lang.String sessionId, java.lang.String type, java.lang.String product, java.lang.String linkedProduct, Magento.CatalogProductLinkEntity data, java.lang.String productIdentifierType) throws java.rmi.RemoteException;

    /**
     * Remove product link
     */
    public java.lang.String catalogProductLinkRemove(java.lang.String sessionId, java.lang.String type, java.lang.String product, java.lang.String linkedProduct, java.lang.String productIdentifierType) throws java.rmi.RemoteException;

    /**
     * Retrieve product link types
     */
    public java.lang.String[] catalogProductLinkTypes(java.lang.String sessionId) throws java.rmi.RemoteException;

    /**
     * Retrieve product link type attributes
     */
    public Magento.CatalogProductLinkAttributeEntity[] catalogProductLinkAttributes(java.lang.String sessionId, java.lang.String type) throws java.rmi.RemoteException;

    /**
     * Retrieve list of orders by filters
     */
    public Magento.SalesOrderEntity[] salesOrderList(java.lang.String sessionId, Magento.Filters filters) throws java.rmi.RemoteException;

    /**
     * Retrieve order information
     */
    public Magento.SalesOrderEntity salesOrderInfo(java.lang.String sessionId, java.lang.String orderIncrementId) throws java.rmi.RemoteException;

    /**
     * Add comment to order
     */
    public int salesOrderAddComment(java.lang.String sessionId, java.lang.String orderIncrementId, java.lang.String status, java.lang.String comment, java.lang.String notify) throws java.rmi.RemoteException;

    /**
     * Hold order
     */
    public int salesOrderHold(java.lang.String sessionId, java.lang.String orderIncrementId) throws java.rmi.RemoteException;

    /**
     * Unhold order
     */
    public int salesOrderUnhold(java.lang.String sessionId, java.lang.String orderIncrementId) throws java.rmi.RemoteException;

    /**
     * Cancel order
     */
    public int salesOrderCancel(java.lang.String sessionId, java.lang.String orderIncrementId) throws java.rmi.RemoteException;

    /**
     * Retrieve list of shipments by filters
     */
    public Magento.SalesOrderShipmentEntity[] salesOrderShipmentList(java.lang.String sessionId, Magento.Filters filters) throws java.rmi.RemoteException;

    /**
     * Retrieve shipment information
     */
    public Magento.SalesOrderShipmentEntity salesOrderShipmentInfo(java.lang.String sessionId, java.lang.String shipmentIncrementId) throws java.rmi.RemoteException;

    /**
     * Create new shipment for order
     */
    public java.lang.String salesOrderShipmentCreate(java.lang.String sessionId, java.lang.String orderIncrementId, Magento.OrderItemIdQty[] itemsQty, java.lang.String comment, int email, int includeComment) throws java.rmi.RemoteException;

    /**
     * Add new comment to shipment
     */
    public int salesOrderShipmentAddComment(java.lang.String sessionId, java.lang.String shipmentIncrementId, java.lang.String comment, java.lang.String email, java.lang.String includeInEmail) throws java.rmi.RemoteException;

    /**
     * Add new tracking number
     */
    public int salesOrderShipmentAddTrack(java.lang.String sessionId, java.lang.String shipmentIncrementId, java.lang.String carrier, java.lang.String title, java.lang.String trackNumber) throws java.rmi.RemoteException;

    /**
     * Remove tracking number
     */
    public int salesOrderShipmentRemoveTrack(java.lang.String sessionId, java.lang.String shipmentIncrementId, java.lang.String trackId) throws java.rmi.RemoteException;

    /**
     * Retrieve list of allowed carriers for order
     */
    public Magento.AssociativeEntity[] salesOrderShipmentGetCarriers(java.lang.String sessionId, java.lang.String orderIncrementId) throws java.rmi.RemoteException;

    /**
     * Retrieve list of invoices by filters
     */
    public Magento.SalesOrderInvoiceEntity[] salesOrderInvoiceList(java.lang.String sessionId, Magento.Filters filters) throws java.rmi.RemoteException;

    /**
     * Retrieve invoice information
     */
    public Magento.SalesOrderInvoiceEntity salesOrderInvoiceInfo(java.lang.String sessionId, java.lang.String invoiceIncrementId) throws java.rmi.RemoteException;

    /**
     * Create new invoice for order
     */
    public java.lang.String salesOrderInvoiceCreate(java.lang.String sessionId, java.lang.String invoiceIncrementId, Magento.OrderItemIdQty[] itemsQty, java.lang.String comment, java.lang.String email, java.lang.String includeComment) throws java.rmi.RemoteException;

    /**
     * Add new comment to shipment
     */
    public java.lang.String salesOrderInvoiceAddComment(java.lang.String sessionId, java.lang.String invoiceIncrementId, java.lang.String comment, java.lang.String email, java.lang.String includeComment) throws java.rmi.RemoteException;

    /**
     * Capture invoice
     */
    public java.lang.String salesOrderInvoiceCapture(java.lang.String sessionId, java.lang.String invoiceIncrementId) throws java.rmi.RemoteException;

    /**
     * Void invoice
     */
    public java.lang.String salesOrderInvoiceVoid(java.lang.String sessionId, java.lang.String invoiceIncrementId) throws java.rmi.RemoteException;

    /**
     * Cancel invoice
     */
    public java.lang.String salesOrderInvoiceCancel(java.lang.String sessionId, java.lang.String invoiceIncrementId) throws java.rmi.RemoteException;

    /**
     * Retrieve stock data by product ids
     */
    public Magento.CatalogInventoryStockItemEntity[] catalogInventoryStockItemList(java.lang.String sessionId, java.lang.String[] products) throws java.rmi.RemoteException;

    /**
     * Update product stock data
     */
    public int catalogInventoryStockItemUpdate(java.lang.String sessionId, java.lang.String product, Magento.CatalogInventoryStockItemUpdateEntity data) throws java.rmi.RemoteException;

    /**
     * Create shopping cart
     */
    public int shoppingCartCreate(java.lang.String sessionId, java.lang.String storeId) throws java.rmi.RemoteException;
}
