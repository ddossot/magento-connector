/**
 * Mule Magento Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.magento.api.catalog;

import static org.mule.module.magento.api.util.MagentoObject.fromMap;

import org.mule.module.magento.api.AbstractMagentoClient;
import org.mule.module.magento.api.AxisPortProvider;
import org.mule.module.magento.api.catalog.model.ProductIdentifier;
import org.mule.module.magento.api.internal.CatalogCategoryEntityCreate;
import org.mule.module.magento.api.internal.CatalogInventoryStockItemUpdateEntity;
import org.mule.module.magento.api.internal.CatalogProductAttributeMediaCreateEntity;
import org.mule.module.magento.api.internal.CatalogProductCreateEntity;
import org.mule.module.magento.api.internal.CatalogProductLinkEntity;
import org.mule.module.magento.api.internal.CatalogProductRequestAttributes;
import org.mule.module.magento.api.internal.CatalogProductTierPriceEntity;
import org.mule.module.magento.api.util.MagentoObject;
import org.mule.module.magento.filters.FiltersParser;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.Validate;

public class AxisMagentoCatalogClient extends AbstractMagentoClient
    implements MagentoCatalogClient<Object, Object[], RemoteException>
{

    public AxisMagentoCatalogClient(AxisPortProvider provider)
    {
        super(provider);
    }

    public Object[] listCategoryAssignedProducts(int categoryId) throws RemoteException
    {
        return getPort().catalogCategoryAssignedProducts(getSessionId(), categoryId);
    }

    public boolean addCategoryProduct(int categoryId,
                                      @NotNull ProductIdentifier productId,
                                      String position) throws RemoteException
    {
        return getPort().catalogCategoryAssignProduct(getSessionId(), categoryId, productId.getIdentifierAsString(), position,
            productId.getIdentifierType());
    }

    public int createCategory(int parentId, @NotNull Map<String, Object> attributes, String storeView)
        throws RemoteException
    {
        Validate.notNull(attributes);
        return getPort().catalogCategoryCreate(getSessionId(), parentId,
            fromMap(CatalogCategoryEntityCreate.class, attributes), storeView);
    }

    // TODO naming
    public int catalogCategoryCurrentStore(String storeView) throws RemoteException
    {
        return getPort().catalogCategoryCurrentStore(getSessionId(), storeView);
    }

    public boolean deleteCategory(int categoryId) throws RemoteException
    {
        return getPort().catalogCategoryDelete(getSessionId(), categoryId);
    }

    public Object getCategory(int categoryId, String storeView, String[] attributes) throws RemoteException
    {
        return getPort().catalogCategoryInfo(getSessionId(), categoryId, storeView, attributes);
    }

    public Object[] listCategoryLevels(String website, String storeView, String parentCategory)
        throws RemoteException
    {
        return getPort().catalogCategoryLevel(getSessionId(), website, storeView, parentCategory);
    }

    public void moveCategory(int categoryId, int parentId, String afterId) throws RemoteException
    {
        getPort().catalogCategoryMove(getSessionId(), categoryId, parentId, afterId);
    }

    public boolean deleteCategoryProduct(int categoryId, @NotNull ProductIdentifier productId)
        throws RemoteException
    {
        return getPort().catalogCategoryRemoveProduct(getSessionId(), categoryId, productId.getIdentifierAsString(),
            productId.getIdentifierType());
    }

    // TODO naming
    public Object catalogCategoryTree(String parentId, String storeView) throws RemoteException
    {
        return getPort().catalogCategoryTree(getSessionId(), parentId, storeView);
    }

    public boolean updateCategory(int categoryId, @NotNull Map<String, Object> attributes, String storeView)
        throws RemoteException
    {
        Validate.notNull(attributes);
        return getPort().catalogCategoryUpdate(getSessionId(), categoryId,
            fromMap(CatalogCategoryEntityCreate.class, attributes), storeView);
    }

    public boolean catalogCategoryUpdateProduct(int categoryId,
                                                @NotNull ProductIdentifier productId,
                                                String position) throws RemoteException
    {
        return getPort().catalogCategoryUpdateProduct(getSessionId(), categoryId, productId.getIdentifierAsString(), position,
            productId.getIdentifierType());
    }

    public Object[] listInventoryStockItems(String[] products) throws RemoteException
    {
        return getPort().catalogInventoryStockItemList(getSessionId(), products);
    }

    public int catalogInventoryStockItemUpdate(@NotNull ProductIdentifier productId, @NotNull Map<String, Object> attributes)
        throws RemoteException
    {
        Validate.notNull(attributes);
        return getPort().catalogInventoryStockItemUpdate(getSessionId(), productId.getIdentifierAsString(),
            fromMap(CatalogInventoryStockItemUpdateEntity.class, attributes));
    }
    /*
     * . Category 36.catalog-category-assignedProducts Retrieve list of assigned
     * products 37.catalog-category-assignProduct Assign product to category
     * 38.catalog-category-create Create new category
     * 39.catalog-category-currentStore Set/Get current store view
     * 40.catalog-category-delete Delete category 41.catalog-category-info Retrieve
     * category data 42.catalog-category-level Retrieve one level of categories by
     * website/store view/parent category NOTE Please make sure that you are not
     * moving category to any of its own children. There are no extra checks to
     * prevent doing it through webservices API, and you won’t be able to fix this
     * from admin interface then 43.catalog-category-move Move category in tree
     * 44.catalog-category-removeProduct Remove product assignment
     * 45.catalog-category-tree Retrieve hierarchical tree 46.catalog-category-update
     * Update category 
     * 
     **/
    
    /*Product*/
    
   /**
    * Creates a new product
    * 
    * @param type the new product's type
    * @param set the new product's set
    * @param sku the new product's sku
    * @param attributes the attributes of the new product
    * @return the new product's id
    */
	public int createProduct(@NotNull String type, @NotNull int set, @NotNull String sku, @NotNull Map<String, Object> attributes)
        throws RemoteException
    {
        Validate.notNull(type);
        Validate.notNull(set);
        Validate.notNull(sku);
        Validate.notNull(attributes);
        
        return getPort().catalogProductCreate(getSessionId(), type, String.valueOf(set), sku,
            fromMap(CatalogProductCreateEntity.class, attributes));
    }

    /**
     * @param storeView
     * @return
     * 
     */
    public void updateProductStoreView(String storeView) throws RemoteException
    {
        getPort().catalogProductCurrentStore(getSessionId(), storeView);
    }
    
    /**
     * TODO
     * 
     * @param storeView
     * @return
     * 
     */
    public void getProductStoreView(String storeView) throws RemoteException
    {
        getPort().catalogProductCurrentStore(getSessionId(), storeView);
    }

    /**
     * Deletes a product.
     *  See catalog-product-delete SOAP method. 
     * 
     * @param productId the product identifier
     */
    public void deleteProduct(ProductIdentifier productId) throws RemoteException
    {
		getPort().catalogProductDelete(getSessionId(),
				productId.getIdentifierAsString(),
				productId.getIdentifierType());
    }

    /**
     * Answers a product special price. 
     * 
     * See catalog-product-getSpecialPrice SOAP method.
     * @param product
     * @param storeView
     * @param productId.getIdentifierType()
     * @return
     * 
     */
    public Object getProductSpecialPrice(@NotNull ProductIdentifier productId, String storeView)
        throws RemoteException
    {
        return getPort().catalogProductGetSpecialPrice(getSessionId(), productId.getIdentifierAsString(), storeView,
            productId.getIdentifierType());
    }

    /**
     * Answers a product's attributes. See catalog-product-info SOAP method. 
     *
     * @param product
     * @param storeView
     * @param attributes
     * @param productId.getIdentifierType()
     * @return the product attributes
     */
    public Object getProduct(@NotNull ProductIdentifier productId,
                             String storeView,
                             @NotNull Map<String, Object> attributes) throws RemoteException
    {
        Validate.notNull(attributes);
        Validate.notNull(productId);
        return getPort().catalogProductInfo(getSessionId(), productId.getIdentifierAsString(), storeView,
            fromMap(CatalogProductRequestAttributes.class, attributes), productId.getIdentifierType());
    }
    
    //TODO store view or code

    /**
     * Retrieve products list by filters
     * See catalog-product-list SOAP method. 
     * @param filters an optional filtering expression
     * @param storeView an optional storeView
     * @return the list of product attributes that match the given optional filtering expression
     */
    public Object[] listProducts(String filters, String storeView) throws RemoteException
    {
        return getPort().catalogProductList(getSessionId(), FiltersParser.parse(filters), storeView);
    }

    /**
     * Sets a product special price. See catalog-product-setSpecialPrice  SOAP method
     * 
     * @param product
     * @param specialPrice
     * @param fromDate
     * @param toDate
     * @param storeView
     * @param productId.getIdentifierType()
     * @return
     * @throws RemoteException
     */
    public int updateProductSpecialPrice(@NotNull ProductIdentifier productId,
                                         @NotNull String specialPrice,
                                         String fromDate,
                                         String toDate,
                                         String storeView) throws RemoteException
    {
    	Validate.notNull(specialPrice);
    	Validate.notNull(productId);
        return getPort().catalogProductSetSpecialPrice(getSessionId(), productId.getIdentifierAsString(), specialPrice, fromDate,
            toDate, storeView, productId.getIdentifierType());
    }
    
    /**
     * Updates a product. See catalog-category-updateProduct SOAP method 
     * 
     * @param attributes
     * @param storeViewIdOrCode optional store view
     */
    public void updateProduct(@NotNull ProductIdentifier productId,
                              @NotNull Map<String, Object> attributes,
                              String storeViewIdOrCode) throws RemoteException
    {
        Validate.notNull(productId);
        Validate.notNull(attributes);
        getPort().catalogProductUpdate(getSessionId(), productId.getIdentifierAsString(),
            fromMap(CatalogProductCreateEntity.class, attributes), storeViewIdOrCode, productId.getIdentifierType());
    }
    
    
    /*
     * Product Images
     */

    public String createProductAttributeMedia(@NotNull ProductIdentifier productId,
                                              @NotNull Map<String, Object> attributes,
                                              String storeView
                                              ) throws RemoteException
    {
        Validate.notNull(attributes);
        return getPort().catalogProductAttributeMediaCreate(getSessionId(), productId.getIdentifierAsString(),
            fromMap(CatalogProductAttributeMediaCreateEntity.class, attributes), storeView,
            productId.getIdentifierType());
    }

    public void updateProductAttributeMediaStoreView(@NotNull String storeView) throws RemoteException
    {
        Validate.notNull(storeView);
        getPort().catalogProductAttributeMediaCurrentStore(getSessionId(), storeView);
    }

    public int getProductAttributeMediaStoreView() throws RemoteException
    {
        return getPort().catalogProductAttributeMediaCurrentStore(getSessionId(), null);
    }

    public Object getProductAttributeMedia(@NotNull ProductIdentifier productId,
                                           @NotNull String file,
                                           String storeView
                                           ) throws RemoteException
    {
        return getPort().catalogProductAttributeMediaInfo(getSessionId(), productId.getIdentifierAsString(), file, storeView,
            productId.getIdentifierType());
    }

    public Object[] listProductAttributeMedia(@NotNull ProductIdentifier productId, String storeView )
        throws RemoteException
    {
        return getPort().catalogProductAttributeMediaList(getSessionId(), productId.getIdentifierAsString(), storeView,
            productId.getIdentifierType());
    }

    public int deleteProductAttributeMedia(@NotNull ProductIdentifier productId, String file )
        throws RemoteException
    {
        return getPort().catalogProductAttributeMediaRemove(getSessionId(), productId.getIdentifierAsString(), file,
            productId.getIdentifierType());
    }

    public Object[] listProductAttributeMediaTypes(String setId) throws RemoteException
    {
        return getPort().catalogProductAttributeMediaTypes(getSessionId(), setId);
    }

    public int updateProductAttributeMedia(@NotNull ProductIdentifier productId,
                                           String file,
                                           @NotNull Map<String, Object> attributes,
                                           String storeView
                                           ) throws RemoteException
    {
        Validate.notNull(attributes);
        return getPort().catalogProductAttributeMediaUpdate(getSessionId(), productId.getIdentifierAsString(), file,
            fromMap(CatalogProductAttributeMediaCreateEntity.class, attributes), storeView,
            productId.getIdentifierType());
    }

    /*
     * Category Attributes
     */

    public Object[] listCategoryAttributes() throws RemoteException
    {
        return getPort().catalogCategoryAttributeList(getSessionId());
    }

    public void updateCategoryAttributeStoreView(String storeView) throws RemoteException
    {
        getPort().catalogCategoryAttributeCurrentStore(getSessionId(), storeView);
    }

    public int getCategoryAttributeStoreView() throws RemoteException
    {
        return getPort().catalogCategoryAttributeCurrentStore(getSessionId(), null);
    }

    public Object[] listCategoryAttributesOptions(@NotNull String attributeId, String storeView)
        throws RemoteException
    {
        Validate.notNull(attributeId);
        return getPort().catalogCategoryAttributeOptions(getSessionId(), attributeId, storeView);
    }

    /* Product Attribute */

    public void updateProductAttributeStoreView(@NotNull String storeView) throws RemoteException
    {
        Validate.notNull(storeView);
        getPort().catalogProductAttributeCurrentStore(getSessionId(), storeView);
    }

    public int getProductAttributeStoreView() throws RemoteException
    {
        return getPort().catalogProductAttributeCurrentStore(getSessionId(), null);
    }

    public Object[] listProductAttributes(int setId) throws RemoteException
    {
        return getPort().catalogProductAttributeList(getSessionId(), setId);
    }

    @NotNull
    public Object[] listProductAttributeOptions(@NotNull String attributeId, String storeView)
        throws RemoteException
    {
        Validate.notNull(attributeId);
        return getPort().catalogProductAttributeOptions(getSessionId(), attributeId, storeView);
    }

    @NotNull
    public Object[] listProductAttributeSets() throws RemoteException
    {
        return getPort().catalogProductAttributeSetList(getSessionId());
    }

    /* Product Type */

	@NotNull
	public Object[] listProductTypes() throws RemoteException
	{
		return getPort().catalogProductTypeList(getSessionId());
	}

    /* Product Tier Price */

    @NotNull
    public Object[] listProductAttributeTierPrices(@NotNull ProductIdentifier productId
                                                   )
        throws RemoteException
    {
        Validate.notNull(productId);
        
        return getPort().catalogProductAttributeTierPriceInfo(getSessionId(), productId.getIdentifierAsString(), productId.getIdentifierType());
    }

    public void updateProductAttributeTierPrices(@NotNull ProductIdentifier productId,
                                                 @NotNull List<Map<String, Object>> attributes)
        throws RemoteException
    {
        Validate.notNull(productId);
        Validate.notNull(attributes);
        
        getPort().catalogProductAttributeTierPriceUpdate(getSessionId(), productId.getIdentifierAsString(),
            MagentoObject.fromMap(CatalogProductTierPriceEntity.class, attributes), productId.getIdentifierType());
    }

    /* h. Product Link (related, cross sells, up sells, grouped) */

    public String assignProductLink(@NotNull String type,
                                    @NotNull ProductIdentifier productId,
                                    @NotNull String linkedProduct,
                                    @NotNull Map<String, Object> attributes) throws RemoteException
    {
        Validate.notNull(attributes);
        return getPort().catalogProductLinkAssign(getSessionId(), type, productId.getIdentifierAsString(), linkedProduct,
            fromMap(CatalogProductLinkEntity.class, attributes), productId.getIdentifierType());
    }

    public Object[] listProductLinkAttributes(String type) throws RemoteException
    {
        return getPort().catalogProductLinkAttributes(getSessionId(), type);
    }

    public Object[] listProductLink(@NotNull String type,
                                    @NotNull ProductIdentifier productId) throws RemoteException
    {
        return getPort().catalogProductLinkList(getSessionId(), type, productId.getIdentifierAsString(), productId.getIdentifierType());
    }

    public String deleteProductLink(@NotNull String type,
                                    @NotNull ProductIdentifier productId,
                                    @NotNull String linkedProduct) throws RemoteException
    {
        return getPort().catalogProductLinkRemove(getSessionId(), type, productId.getIdentifierAsString(), linkedProduct,
            productId.getIdentifierType());
    }

    public String[] listProductLinkTypes() throws RemoteException
    {
        return getPort().catalogProductLinkTypes(getSessionId());
    }

    public String updateProductLink(@NotNull String type,
                                    @NotNull ProductIdentifier productId,
                                    @NotNull String linkedProduct,
                                    @NotNull Map<String, Object> attributes) throws RemoteException
    {
        Validate.notNull(attributes);
        Validate.notNull(type);
        Validate.notNull(linkedProduct);

        return getPort().catalogProductLinkUpdate(getSessionId(), type, productId.getIdentifierAsString(), linkedProduct,
            fromMap(CatalogProductLinkEntity.class, attributes), productId.getIdentifierType());
    }
}
