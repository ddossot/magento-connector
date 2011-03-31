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

import org.mule.module.magento.api.internal.CatalogProductTierPriceEntity;

import java.rmi.RemoteException;
import java.util.Map;

import javax.validation.constraints.NotNull;

public interface MagentoCatalogClient<T1, T2, ExceptionType extends Exception>
{

    public Object[] getCategoryAssignedProducts(int categoryId) throws RemoteException;

    public boolean addCategoryProduct(int categoryId,
                                         String product,
                                         String position,
                                         String productIdentifierType) throws RemoteException;

    public int getCategoryAttributeCurrentStore(String storeView) throws RemoteException;

    public Object[] listCategoryAttributes() throws RemoteException;

    public Object[] listCategoryAttributesOptions(String attributeId, String storeView)
        throws RemoteException;

    public int createCategory(int parentId, @NotNull Map<String, Object> attributes, String storeView)
        throws RemoteException;

    public int catalogCategoryCurrentStore(String storeView) throws RemoteException;

    public boolean deleteCategory(int categoryId) throws RemoteException;

    public Object getCategory(int categoryId, String storeView, String[] attributes)
        throws RemoteException;

    public Object[] catalogCategoryLevel(String website, String storeView, String parentCategory)
        throws RemoteException;

    public boolean catalogCategoryMove(int categoryId, int parentId, String afterId) throws RemoteException;

    public boolean deleteCategoryProduct(int categoryId, String product, String productIdentifierType)
        throws RemoteException;

    public Object catalogCategoryTree(String parentId, String storeView) throws RemoteException;

    public boolean updateCategory(int categoryId,
                                         @NotNull Map<String, Object> attributes,
                                         String storeView) throws RemoteException;

    public boolean catalogCategoryUpdateProduct(int categoryId,
                                                String product,
                                                String position,
                                                String productIdentifierType) throws RemoteException;

    public Object[] catalogInventoryStockItemList(String[] products) throws RemoteException;

    public int catalogInventoryStockItemUpdate(String product, @NotNull Map<String, Object> attributes)
        throws RemoteException;

    public int catalogProductAttributeCurrentStore(String storeView) throws RemoteException;

    public Object[] catalogProductAttributeList(int setId) throws RemoteException;

    public String catalogProductAttributeMediaCreate(String product,
                                                     @NotNull Map<String, Object> attributes,
                                                     String storeView,
                                                     String productIdentifierType) throws RemoteException;

    public int catalogProductAttributeMediaCurrentStore(String storeView) throws RemoteException;

    public Object catalogProductAttributeMediaInfo(String product,
                                                   String file,
                                                   String storeView,
                                                   String productIdentifierType) throws RemoteException;

    public Object[] catalogProductAttributeMediaList(String product,
                                                     String storeView,
                                                     String productIdentifierType) throws RemoteException;

    public int catalogProductAttributeMediaRemove(String product, String file, String productIdentifierType)
        throws RemoteException;

    public Object[] catalogProductAttributeMediaTypes(String setId) throws RemoteException;

    public int catalogProductAttributeMediaUpdate(String product,
                                                  String file,
                                                  @NotNull Map<String, Object> attributes,
                                                  String storeView,
                                                  String productIdentifierType) throws RemoteException;

    public Object[] catalogProductAttributeOptions(String attributeId, String storeView)
        throws RemoteException;

    public Object[] catalogProductAttributeSetList() throws RemoteException;

    public Object[] catalogProductAttributeTierPriceInfo(String product, String productIdentifierType)
        throws RemoteException;

    public int catalogProductAttributeTierPriceUpdate(String product,
                                                      CatalogProductTierPriceEntity[] tierPrice,
                                                      String productIdentifierType) throws RemoteException;

    public int catalogProductCreate(String type,
                                    String set,
                                    String sku,
                                    @NotNull Map<String, Object> attributes) throws RemoteException;

    public int catalogProductCurrentStore(String storeView) throws RemoteException;

    public int catalogProductDelete(String product, String productIdentifierType) throws RemoteException;

    public Object catalogProductGetSpecialPrice(String product, String storeView, String productIdentifierType)
        throws RemoteException;

    public Object catalogProductInfo(String product,
                                     String storeView,
                                     @NotNull Map<String, Object> attributes,
                                     String productIdentifierType) throws RemoteException;

    public String catalogProductLinkAssign(String type,
                                           String product,
                                           String linkedProduct,
                                           @NotNull Map<String, Object> attributes,
                                           String productIdentifierType) throws RemoteException;

    public Object[] catalogProductLinkAttributes(String type) throws RemoteException;

    public Object[] catalogProductLinkList(String type, String product, String productIdentifierType)
        throws RemoteException;

    public String catalogProductLinkRemove(String type,
                                           String product,
                                           String linkedProduct,
                                           String productIdentifierType) throws RemoteException;

    public String[] catalogProductLinkTypes() throws RemoteException;

    public String catalogProductLinkUpdate(String type,
                                           String product,
                                           String linkedProduct,
                                           @NotNull Map<String, Object> attributes,
                                           String productIdentifierType) throws RemoteException;

    public Object[] listCatalogProducts(String filters, String storeView) throws RemoteException;

    public int catalogProductSetSpecialPrice(String product,
                                             String specialPrice,
                                             String fromDate,
                                             String toDate,
                                             String storeView,
                                             String productIdentifierType) throws RemoteException;

    public Object[] listProductTypes() throws RemoteException;

    public boolean updateProduct(String product,
                                        Map<String, Object> attributes,
                                        String storeView,
                                        String productIdentifierType) throws RemoteException;

}
