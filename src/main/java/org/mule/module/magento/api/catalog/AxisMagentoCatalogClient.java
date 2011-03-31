/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.magento.api.catalog;

import static org.mule.module.magento.api.MagentoMap.fromMap;

import org.mule.module.magento.api.AbstractMagentoClient;
import org.mule.module.magento.api.AxisPortProvider;
import org.mule.module.magento.api.internal.CatalogCategoryEntityCreate;
import org.mule.module.magento.api.internal.CatalogInventoryStockItemUpdateEntity;
import org.mule.module.magento.api.internal.CatalogProductAttributeMediaCreateEntity;
import org.mule.module.magento.api.internal.CatalogProductCreateEntity;
import org.mule.module.magento.api.internal.CatalogProductLinkEntity;
import org.mule.module.magento.api.internal.CatalogProductRequestAttributes;
import org.mule.module.magento.api.internal.CatalogProductTierPriceEntity;
import org.mule.module.magento.filters.FiltersParser;

import java.rmi.RemoteException;
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

    public Object[] getCategoryAssignedProducts(int categoryId) throws RemoteException
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

    // TODO naming
    public int getCategoryAttributeCurrentStore(String storeView) throws RemoteException
    {
        return getPort().catalogCategoryAttributeCurrentStore(getSessionId(), storeView);
    }

    // TODO naming
    public Object[] listCategoryAttributes() throws RemoteException
    {
        return getPort().catalogCategoryAttributeList(getSessionId());
    }

    public Object[] listCategoryAttributesOptions(String attributeId, String storeView)
        throws RemoteException
    {
        return getPort().catalogCategoryAttributeOptions(getSessionId(), attributeId, storeView);
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

    // TODO naming
    public Object[] catalogCategoryLevel(String website, String storeView, String parentCategory)
        throws RemoteException
    {
        return getPort().catalogCategoryLevel(getSessionId(), website, storeView, parentCategory);
    }

    // TODO naming
    public boolean catalogCategoryMove(int categoryId, int parentId, String afterId) throws RemoteException
    {
        return getPort().catalogCategoryMove(getSessionId(), categoryId, parentId, afterId);
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

    public Object[] catalogInventoryStockItemList(String[] products) throws RemoteException
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

    public int catalogProductAttributeCurrentStore(String storeView) throws RemoteException
    {
        return getPort().catalogProductAttributeCurrentStore(getSessionId(), storeView);
    }

    public Object[] catalogProductAttributeList(int setId) throws RemoteException
    {
        return getPort().catalogProductAttributeList(getSessionId(), setId);
    }

    public String catalogProductAttributeMediaCreate(String product,
                                                     @NotNull Map<String, Object> attributes,
                                                     String storeView,
                                                     String productIdentifierType) throws RemoteException
    {
        Validate.notNull(attributes);
        return getPort().catalogProductAttributeMediaCreate(getSessionId(), product,
            fromMap(CatalogProductAttributeMediaCreateEntity.class, attributes), storeView,
            productIdentifierType);
    }

    public int catalogProductAttributeMediaCurrentStore(String storeView) throws RemoteException
    {
        return getPort().catalogProductAttributeMediaCurrentStore(getSessionId(), storeView);
    }

    public Object catalogProductAttributeMediaInfo(String product,
                                                   String file,
                                                   String storeView,
                                                   String productIdentifierType) throws RemoteException
    {
        return getPort().catalogProductAttributeMediaInfo(getSessionId(), product, file, storeView,
            productIdentifierType);
    }

    public Object[] catalogProductAttributeMediaList(String product,
                                                     String storeView,
                                                     String productIdentifierType) throws RemoteException
    {
        return getPort().catalogProductAttributeMediaList(getSessionId(), product, storeView,
            productIdentifierType);
    }

    public int catalogProductAttributeMediaRemove(String product, String file, String productIdentifierType)
        throws RemoteException
    {
        return getPort().catalogProductAttributeMediaRemove(getSessionId(), product, file,
            productIdentifierType);
    }

    public Object[] catalogProductAttributeMediaTypes(String setId) throws RemoteException
    {
        return getPort().catalogProductAttributeMediaTypes(getSessionId(), setId);
    }

    public int catalogProductAttributeMediaUpdate(String product,
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

    public Object[] catalogProductAttributeOptions(String attributeId, String storeView)
        throws RemoteException
    {
        return getPort().catalogProductAttributeOptions(getSessionId(), attributeId, storeView);
    }

    public Object[] catalogProductAttributeSetList() throws RemoteException
    {
        return getPort().catalogProductAttributeSetList(getSessionId());
    }

    public Object[] catalogProductAttributeTierPriceInfo(String product, String productIdentifierType)
        throws RemoteException
    {
        return getPort().catalogProductAttributeTierPriceInfo(getSessionId(), product, productIdentifierType);
    }

    // TODO
    public int catalogProductAttributeTierPriceUpdate(String product,
                                                      CatalogProductTierPriceEntity[] tierPrice,
                                                      String productIdentifierType) throws RemoteException
    {
        return getPort().catalogProductAttributeTierPriceUpdate(getSessionId(), product, tierPrice,
            productIdentifierType);
    }

    public int catalogProductCreate(String type,
                                    String set,
                                    String sku,
                                    @NotNull Map<String, Object> attributes) throws RemoteException
    {

        Validate.notNull(attributes);
        return getPort().catalogProductCreate(getSessionId(), type, set, sku,
            fromMap(CatalogProductCreateEntity.class, attributes));
    }

    public int catalogProductCurrentStore(String storeView) throws RemoteException
    {
        return getPort().catalogProductCurrentStore(getSessionId(), storeView);
    }

    public int catalogProductDelete(String product, String productIdentifierType) throws RemoteException
    {
        return getPort().catalogProductDelete(getSessionId(), product, productIdentifierType);
    }

    public Object catalogProductGetSpecialPrice(String product, String storeView, String productIdentifierType)
        throws RemoteException
    {
        return getPort().catalogProductGetSpecialPrice(getSessionId(), product, storeView,
            productIdentifierType);
    }

    public Object catalogProductInfo(String product,
                                     String storeView,
                                     @NotNull Map<String, Object> attributes,
                                     String productIdentifierType) throws RemoteException
    {
        Validate.notNull(attributes);
        return getPort().catalogProductInfo(getSessionId(), product, storeView,
            fromMap(CatalogProductRequestAttributes.class, attributes), productIdentifierType);
    }

    public String catalogProductLinkAssign(String type,
                                           String product,
                                           String linkedProduct,
                                           @NotNull Map<String, Object> attributes,
                                           String productIdentifierType) throws RemoteException
    {
        Validate.notNull(attributes);
        return getPort().catalogProductLinkAssign(getSessionId(), type, product, linkedProduct,
            fromMap(CatalogProductLinkEntity.class, attributes), productIdentifierType);
    }

    public Object[] catalogProductLinkAttributes(String type) throws RemoteException
    {
        return getPort().catalogProductLinkAttributes(getSessionId(), type);
    }

    public Object[] catalogProductLinkList(String type, String product, String productIdentifierType)
        throws RemoteException
    {
        return getPort().catalogProductLinkList(getSessionId(), type, product, productIdentifierType);
    }

    public String catalogProductLinkRemove(String type,
                                           String product,
                                           String linkedProduct,
                                           String productIdentifierType) throws RemoteException
    {
        return getPort().catalogProductLinkRemove(getSessionId(), type, product, linkedProduct,
            productIdentifierType);
    }

    public String[] catalogProductLinkTypes() throws RemoteException
    {
        return getPort().catalogProductLinkTypes(getSessionId());
    }

    public String catalogProductLinkUpdate(String type,
                                           String product,
                                           String linkedProduct,
                                           @NotNull Map<String, Object> attributes,
                                           String productIdentifierType) throws RemoteException
    {
        Validate.notNull(attributes);
        return getPort().catalogProductLinkUpdate(getSessionId(), type, product, linkedProduct,
            fromMap(CatalogProductLinkEntity.class, attributes), productIdentifierType);
    }

    public Object[] listCatalogProducts(String filters, String storeView) throws RemoteException
    {
        return getPort().catalogProductList(getSessionId(), FiltersParser.parse(filters), storeView);
    }

    // TODO naming
    public int catalogProductSetSpecialPrice(String product,
                                             String specialPrice,
                                             String fromDate,
                                             String toDate,
                                             String storeView,
                                             String productIdentifierType) throws RemoteException
    {
        return getPort().catalogProductSetSpecialPrice(getSessionId(), product, specialPrice, fromDate,
            toDate, storeView, productIdentifierType);
    }

    public Object[] listProductTypes() throws RemoteException
    {
        return getPort().catalogProductTypeList(getSessionId());
    }

    public boolean updateProduct(String product,
                                 Map<String, Object> attributes,
                                 String storeView,
                                 String productIdentifierType) throws RemoteException
    {
        Validate.notNull(attributes);
        return getPort().catalogProductUpdate(getSessionId(), product,
            fromMap(CatalogProductCreateEntity.class, attributes), storeView, productIdentifierType);
    }

}
