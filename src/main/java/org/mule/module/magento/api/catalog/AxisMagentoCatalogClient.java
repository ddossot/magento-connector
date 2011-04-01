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
                                      String product,
                                      String position,
                                      String productIdentifierType) throws RemoteException
    {
        return getPort().catalogCategoryAssignProduct(getSessionId(), categoryId, product, position,
            productIdentifierType);
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

    public boolean deleteCategoryProduct(int categoryId, String product, String productIdentifierType)
        throws RemoteException
    {
        return getPort().catalogCategoryRemoveProduct(getSessionId(), categoryId, product,
            productIdentifierType);
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
                                                String product,
                                                String position,
                                                String productIdentifierType) throws RemoteException
    {
        return getPort().catalogCategoryUpdateProduct(getSessionId(), categoryId, product, position,
            productIdentifierType);
    }

    public Object[] listInventoryStockItems(String[] products) throws RemoteException
    {
        return getPort().catalogInventoryStockItemList(getSessionId(), products);
    }

    public int catalogInventoryStockItemUpdate(String product, @NotNull Map<String, Object> attributes)
        throws RemoteException
    {
        Validate.notNull(attributes);
        return getPort().catalogInventoryStockItemUpdate(getSessionId(), product,
            fromMap(CatalogInventoryStockItemUpdateEntity.class, attributes));
    }

    public int createProduct(String type, String set, String sku, @NotNull Map<String, Object> attributes)
        throws RemoteException
    {
        Validate.notNull(attributes);
        return getPort().catalogProductCreate(getSessionId(), type, set, sku,
            fromMap(CatalogProductCreateEntity.class, attributes));
    }

    public int updateProductStore(String storeView) throws RemoteException
    {
        return getPort().catalogProductCurrentStore(getSessionId(), storeView);
    }

    public int deleteProduct(String product, String productIdentifierType) throws RemoteException
    {
        return getPort().catalogProductDelete(getSessionId(), product, productIdentifierType);
    }

    public Object getProductSpecialPrice(String product, String storeView, String productIdentifierType)
        throws RemoteException
    {
        return getPort().catalogProductGetSpecialPrice(getSessionId(), product, storeView,
            productIdentifierType);
    }

    public Object getProduct(String product,
                             String storeView,
                             @NotNull Map<String, Object> attributes,
                             String productIdentifierType) throws RemoteException
    {
        Validate.notNull(attributes);
        return getPort().catalogProductInfo(getSessionId(), product, storeView,
            fromMap(CatalogProductRequestAttributes.class, attributes), productIdentifierType);
    }

    public Object[] listCatalogProducts(String filters, String storeView) throws RemoteException
    {
        return getPort().catalogProductList(getSessionId(), FiltersParser.parse(filters), storeView);
    }

    public int updateProductSpecialPrice(String product,
                                         String specialPrice,
                                         String fromDate,
                                         String toDate,
                                         String storeView,
                                         String productIdentifierType) throws RemoteException
    {
        return getPort().catalogProductSetSpecialPrice(getSessionId(), product, specialPrice, fromDate,
            toDate, storeView, productIdentifierType);
    }

    /**
     * Updates a product
     * 
     * @param productIdOrSku the product id or sku to update
     * @param attributes
     * @param storeViewIdOrCode optional store view
     * @param productIdentifierType
     * @throws RemoteException
     */
    public void updateProduct(@NotNull String productIdOrSku,
                              @NotNull Map<String, Object> attributes,
                              String storeViewIdOrCode,
                              @NotNull String productIdentifierType) throws RemoteException
    {
        Validate.notNull(productIdOrSku);
        Validate.notNull(attributes);
        Validate.notNull(productIdentifierType);
        getPort().catalogProductUpdate(getSessionId(), productIdOrSku,
            fromMap(CatalogProductCreateEntity.class, attributes), storeViewIdOrCode, productIdentifierType);
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
     * Update category 47.catalog-category-updateProduct Update assigned product c.
     * Product 51.catalog-product-create Create new product
     * 52.catalog-product-currentStore Set/Get current store view
     * 53.catalog-product-delete Delete product 54.catalog-product-getSpecialPrice
     * Get special price for product 55.catalog-product-info Retrieve product
     * 56.catalog-product-list Retrieve products list by filters
     * 57.catalog-product-setSpecialPrice Set special price for product
     * 58.catalog-product-update Update product
     */

    /*
     * Product Images
     */

    public String createProductAttributeMedia(@NotNull String product,
                                              @NotNull Map<String, Object> attributes,
                                              String storeView,
                                              String productIdentifierType) throws RemoteException
    {
        Validate.notNull(attributes);
        return getPort().catalogProductAttributeMediaCreate(getSessionId(), product,
            fromMap(CatalogProductAttributeMediaCreateEntity.class, attributes), storeView,
            productIdentifierType);
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

    public Object getProductAttributeMedia(@NotNull String product,
                                           @NotNull String file,
                                           String storeView,
                                           @NotNull String productIdentifierType) throws RemoteException
    {
        return getPort().catalogProductAttributeMediaInfo(getSessionId(), product, file, storeView,
            productIdentifierType);
    }

    public Object[] listProductAttributeMedia(String product, String storeView, String productIdentifierType)
        throws RemoteException
    {
        return getPort().catalogProductAttributeMediaList(getSessionId(), product, storeView,
            productIdentifierType);
    }

    public int deleteProductAttributeMedia(String product, String file, String productIdentifierType)
        throws RemoteException
    {
        return getPort().catalogProductAttributeMediaRemove(getSessionId(), product, file,
            productIdentifierType);
    }

    public Object[] listProductAttributeMediaTypes(String setId) throws RemoteException
    {
        return getPort().catalogProductAttributeMediaTypes(getSessionId(), setId);
    }

    public int updateProductAttributeMedia(String product,
                                           String file,
                                           @NotNull Map<String, Object> attributes,
                                           String storeView,
                                           String productIdentifierType) throws RemoteException
    {
        Validate.notNull(attributes);
        return getPort().catalogProductAttributeMediaUpdate(getSessionId(), product, file,
            fromMap(CatalogProductAttributeMediaCreateEntity.class, attributes), storeView,
            productIdentifierType);
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
    public Object[] listProductAttributeTierPrices(@NotNull String product,
                                                   @NotNull String productIdentifierType)
        throws RemoteException
    {
        Validate.notNull(product);
        Validate.notNull(productIdentifierType);
        return getPort().catalogProductAttributeTierPriceInfo(getSessionId(), product, productIdentifierType);
    }

    public void updateProductAttributeTierPrices(@NotNull String product,
                                                 @NotNull List<Map<String, Object>> attributes,
                                                 @NotNull String productIdentifierType)
        throws RemoteException
    {
        Validate.notNull(product);
        Validate.notNull(attributes);
        Validate.notNull(productIdentifierType);
        getPort().catalogProductAttributeTierPriceUpdate(getSessionId(), product,
            MagentoObject.fromMap(CatalogProductTierPriceEntity.class, attributes), productIdentifierType);
    }

    /* h. Product Link (related, cross sells, up sells, grouped) */

    public String assignProductLink(@NotNull String type,
                                    @NotNull String product,
                                    @NotNull String linkedProduct,
                                    @NotNull Map<String, Object> attributes,
                                    @NotNull String productIdentifierType) throws RemoteException
    {
        Validate.notNull(attributes);
        return getPort().catalogProductLinkAssign(getSessionId(), type, product, linkedProduct,
            fromMap(CatalogProductLinkEntity.class, attributes), productIdentifierType);
    }

    public Object[] listProductLinkAttributes(String type) throws RemoteException
    {
        return getPort().catalogProductLinkAttributes(getSessionId(), type);
    }

    public Object[] listProductLink(@NotNull String type,
                                    @NotNull String product,
                                    @NotNull String productIdentifierType) throws RemoteException
    {
        return getPort().catalogProductLinkList(getSessionId(), type, product, productIdentifierType);
    }

    public String deleteProductLink(@NotNull String type,
                                    @NotNull String product,
                                    @NotNull String linkedProduct,
                                    @NotNull String productIdentifierType) throws RemoteException
    {
        return getPort().catalogProductLinkRemove(getSessionId(), type, product, linkedProduct,
            productIdentifierType);
    }

    public String[] listProductLinkTypes() throws RemoteException
    {
        return getPort().catalogProductLinkTypes(getSessionId());
    }

    public String updateProductLink(@NotNull String type,
                                    @NotNull String product,
                                    @NotNull String linkedProduct,
                                    @NotNull Map<String, Object> attributes,
                                    @NotNull String productIdentifierType) throws RemoteException
    {
        Validate.notNull(attributes);
        Validate.notNull(type);
        Validate.notNull(linkedProduct);
        Validate.notNull(productIdentifierType);

        return getPort().catalogProductLinkUpdate(getSessionId(), type, product, linkedProduct,
            fromMap(CatalogProductLinkEntity.class, attributes), productIdentifierType);
    }
}
