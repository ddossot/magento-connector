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

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

public interface MagentoCatalogClient<AttributesType, AttributesCollectionType, ExceptionType extends Exception>
{

    /**
     * Creates a new product media. See catalog-product-attribute-media-create SOAP
     * method.
     * 
     * @param product
     * @param attributes
     * @param storeView
     * @param productIdentifierType
     * @return
     * @throws RemoteException
     */
    String createProductAttributeMedia(@NotNull String product,
                                       @NotNull Map<String, Object> attributes,
                                       String storeView,
                                       String productIdentifierType) throws RemoteException;

    /**
     * Set current store view. See catalog-product-attribute-media-currentStore SOAP
     * method. TODO verify
     * 
     * @param storeView
     * @return
     * @throws RemoteException
     */
    void updateProductAttributeMediaStoreView(@NotNull String storeView) throws RemoteException;

    /**
     * Gets current store view. See catalog-product-attribute-media-currentStore SOAP
     * method. TODO verify
     * 
     * @param storeView
     * @return
     * @throws RemoteException
     */
    int getProductAttributeMediaStoreView() throws RemoteException;

    /**
     * 66.catalog-product-attribute-media-info Retrieve product image
     * 
     * @param product
     * @param file
     * @param storeView
     * @param productIdentifierType
     * @return
     * @throws RemoteException
     */
    AttributesType getProductAttributeMedia(@NotNull String product,
                                            @NotNull String file,
                                            String storeView,
                                            @NotNull String productIdentifierType) throws RemoteException;

    /**
     * Retrieves product image list. See catalog-product-attribute-media-list SOAP
     * method
     * 
     * @param product
     * @param storeView
     * @param productIdentifierType
     * @return the list of product images attributes
     * @throws RemoteException
     */
    AttributesCollectionType listProductAttributeMedia(String product,
                                                       String storeView,
                                                       String productIdentifierType) throws RemoteException;

    /**
     * Removes a product image. See catalog-product-attribute-media-remove SOAP
     * method.
     * 
     * @param product
     * @param file
     * @param productIdentifierType
     * @return
     * @throws RemoteException
     */
    int deleteProductAttributeMedia(String product, String file, String productIdentifierType)
        throws RemoteException;

    /**
     * Retrieve product image types. See catalog-product-attribute-media-types SOAP
     * method.
     * 
     * @param setId
     * @return
     * @throws RemoteException
     */
    AttributesCollectionType listProductAttributeMediaTypes(String setId) throws RemoteException;

    /**
     * Updates product media. See catalog-product-attribute-media-update
     * 
     * @param product
     * @param file
     * @param attributes
     * @param storeView
     * @param productIdentifierType
     * @return
     * @throws RemoteException
     */
    int updateProductAttributeMedia(String product,
                                    String file,
                                    @NotNull Map<String, Object> attributes,
                                    String storeView,
                                    String productIdentifierType) throws RemoteException;

    /**
     * Retrieves category attributes. See catalog-category-attribute-list SOAP
     * method.
     * 
     * @return the list of attributes
     * @throws RemoteException
     */
    AttributesCollectionType listCategoryAttributes() throws RemoteException;

    /**
     * TODO verify
     * 
     * @param storeView
     * @throws RemoteException
     */
    void updateCategoryAttributeStoreView(String storeView) throws RemoteException;

    /**
     * TODO verify
     * 
     * @return
     * @throws RemoteException
     */
    int getCategoryAttributeStoreView() throws RemoteException;

    /**
     * Retrieves attribute options. See catalog-category-attribute-options SOAP
     * method.
     * 
     * @param attributeId
     * @param storeView optinal
     * @return the list of attributes
     * @throws RemoteException
     */
    AttributesCollectionType listCategoryAttributesOptions(@NotNull String attributeId, String storeView)
        throws RemoteException;

    /**
     * Sets the product attribute store. TODO verify
     * 
     * @param storeView
     * @throws RemoteException
     */
    void updateProductAttributeStoreView(@NotNull String storeView) throws RemoteException;

    /**
     * Gets the product attribute store view. TODO verify
     * 
     * @return the current store
     * @throws RemoteException
     */
    int getProductAttributeStoreView() throws RemoteException;

    /**
     * Retrieves product attributes list. See catalog-product-attribute-list SOAP
     * methods
     * 
     * @param setId
     * @return the list of product attributes
     * @throws RemoteException
     */
    AttributesCollectionType listProductAttributes(int setId) throws RemoteException;

    /**
     * Answers the product attribute options. See catalog-product-attribute-options
     * SOAP method.
     * 
     * @param attributeId
     * @param storeView optional
     * @return the attributes list
     * @throws RemoteException
     */
    @NotNull
    AttributesCollectionType listProductAttributeOptions(@NotNull String attributeId, String storeView)
        throws RemoteException;

    /**
     * Retrieves product attribute sets. See catalog-product-attribute-set-list SOAP
     * method.
     * 
     * @return The list of product attributes sets
     * @throws RemoteException
     */
    @NotNull
    AttributesCollectionType listProductAttributeSets() throws RemoteException;

    /**
     * Retrieves product types. See catalog-product-type-list SOAP method
     * 
     * @return the list of product types
     * @throws RemoteException
     */
    @NotNull
    AttributesCollectionType listProductTypes() throws RemoteException;

    /**
     * Retrieve product tier prices. See catalog-product-attribute-tier-price-info
     * SOAP Method.
     * 
     * @param product
     * @param productIdentifierType
     * @return the list of product attributes
     * @throws RemoteException
     */
    @NotNull
    AttributesCollectionType listProductAttributeTierPrices(@NotNull String product,
                                                            @NotNull String productIdentifierType)
        throws RemoteException;

    /**
     * Updates product tier prices. See catalog-product-attribute-tier-price-update
     * SOAP method.
     * 
     * @param product
     * @param attributes
     * @param productIdentifierType
     * @throws RemoteException
     */
    void updateProductAttributeTierPrices(@NotNull String product,
                                          @NotNull List<Map<String, Object>> attributes,
                                          @NotNull String productIdentifierType) throws RemoteException;

    /**
     * Assigns a link to a product
     * 
     * @param type
     * @param product
     * @param linkedProduct
     * @param attributes
     * @param productIdentifierType
     * @return TODO
     * @throws RemoteException
     */
    String assignProductLink(@NotNull String type,
                             @NotNull String product,
                             @NotNull String linkedProduct,
                             @NotNull Map<String, Object> attributes,
                             @NotNull String productIdentifierType) throws RemoteException;

    /**
     * Retrieve product link type attributes
     * 
     * @param type
     * @return
     * @throws RemoteException
     */
    AttributesCollectionType listProductLinkAttributes(String type) throws RemoteException;

    /**
     * Retrieve linked products
     * 
     * @param type
     * @param product
     * @param productIdentifierType
     * @return the list of links to the product
     * @throws RemoteException
     */
    AttributesCollectionType listProductLink(@NotNull String type,
                                             @NotNull String product,
                                             @NotNull String productIdentifierType) throws RemoteException;

    /**
     * Deletes a link in a product
     * 
     * @param type
     * @param product
     * @param linkedProduct
     * @param productIdentifierType
     * @return TODO
     * @throws RemoteException
     */
    String deleteProductLink(@NotNull String type,
                             @NotNull String product,
                             @NotNull String linkedProduct,
                             @NotNull String productIdentifierType) throws RemoteException;

    /**
     * Retrieve product link types
     * 
     * @return the list of product link types
     * @throws RemoteException
     */
    String[] listProductLinkTypes() throws RemoteException;

    /**
     * Update product link
     * 
     * @param type
     * @param product
     * @param linkedProduct
     * @param attributes
     * @param productIdentifierType
     * @return TODO
     * @throws RemoteException
     */
    String updateProductLink(@NotNull String type,
                             @NotNull String product,
                             @NotNull String linkedProduct,
                             @NotNull Map<String, Object> attributes,
                             @NotNull String productIdentifierType) throws RemoteException;

}
