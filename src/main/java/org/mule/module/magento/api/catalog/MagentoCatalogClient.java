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

import org.mule.module.magento.api.catalog.model.ProductIdentifier;

public interface MagentoCatalogClient<AttributesType, AttributesCollectionType, ExceptionType extends Exception>
{

    /**
     * Creates a new product media. See catalog-product-attribute-media-create SOAP
     * method.
     * 
     * @param product
     * @param attributes
     * @param storeView
     * @return
     * 
     */
    String createProductAttributeMedia(@NotNull ProductIdentifier productId,
                                       @NotNull Map<String, Object> attributes,
                                       String storeView) throws RemoteException;

    /**
     * Set current store view. See catalog-product-attribute-media-currentStore SOAP
     * method. TODO verify
     * 
     * @param storeView
     * @return
     * 
     */
    void updateProductAttributeMediaStoreView(@NotNull String storeView) throws RemoteException;

    /**
     * Gets current store view. See catalog-product-attribute-media-currentStore SOAP
     * method. TODO verify
     * 
     * @param storeView
     * @return
     * 
     */
    int getProductAttributeMediaStoreView() throws RemoteException;

    /**
     * Answers product image attributes. See catalog-product-attribute-media-info SOAP method 
     * @param productId
     * @param file
     * @param storeView
     * @return the product attributes
     */
    AttributesType getProductAttributeMedia(@NotNull ProductIdentifier productId,
                                            @NotNull String file,
                                            String storeView) throws RemoteException;

    /**
     * Retrieves product image list. See catalog-product-attribute-media-list SOAP
     * method
     * 
     * @param product
     * @param storeView
     * @return the list of product images attributes
     * 
     */
    AttributesCollectionType listProductAttributeMedia(@NotNull ProductIdentifier productId,
                                                       String storeView) throws RemoteException;

    /**
     * Removes a product image. See catalog-product-attribute-media-remove SOAP
     * method.
     * 
     * @param product
     * @param file
     * @return
     * 
     */
    int deleteProductAttributeMedia(@NotNull ProductIdentifier productId, @NotNull String file)
        throws RemoteException;

    /**
     * Retrieve product image types. See catalog-product-attribute-media-types SOAP
     * method.
     * 
     * @param setId
     * @return
     * 
     */
    AttributesCollectionType listProductAttributeMediaTypes(String setId) throws RemoteException;

    /**
     * Updates product media. See catalog-product-attribute-media-update
     * 
     * @param product
     * @param file
     * @param attributes
     * @param storeView
     * @return
     * 
     */
    int updateProductAttributeMedia(@NotNull ProductIdentifier productId,
                                    String file,
                                    @NotNull Map<String, Object> attributes,
                                    String storeView) throws RemoteException;

    /**
     * Retrieves category attributes. See catalog-category-attribute-list SOAP
     * method.
     * 
     * @return the list of attributes
     * 
     */
    AttributesCollectionType listCategoryAttributes() throws RemoteException;

    /**
     * TODO verify
     * 
     * @param storeView
     * 
     */
    void updateCategoryAttributeStoreView(String storeView) throws RemoteException;

    /**
     * TODO verify
     * 
     * @return
     * 
     */
    int getCategoryAttributeStoreView() throws RemoteException;

    /**
     * Retrieves attribute options. See catalog-category-attribute-options SOAP
     * method.
     * 
     * @param attributeId
     * @param storeView optinal
     * @return the list of attributes
     * 
     */
    AttributesCollectionType listCategoryAttributesOptions(@NotNull String attributeId, String storeView)
        throws RemoteException;

    /**
     * Sets the product attribute store. TODO verify
     * 
     * @param storeView
     * 
     */
    void updateProductAttributeStoreView(@NotNull String storeView) throws RemoteException;

    /**
     * Gets the product attribute store view. TODO verify
     * 
     * @return the current store
     * 
     */
    int getProductAttributeStoreView() throws RemoteException;

    /**
     * Retrieves product attributes list. See catalog-product-attribute-list SOAP
     * methods
     * 
     * @param setId
     * @return the list of product attributes
     * 
     */
    AttributesCollectionType listProductAttributes(int setId) throws RemoteException;

    /**
     * Answers the product attribute options. See catalog-product-attribute-options
     * SOAP method.
     * 
     * @param attributeId
     * @param storeView optional
     * @return the attributes list
     * 
     */
    @NotNull
    AttributesCollectionType listProductAttributeOptions(@NotNull String attributeId, String storeView)
        throws RemoteException;

    /**
     * Retrieves product attribute sets. See catalog-product-attribute-set-list SOAP
     * method.
     * 
     * @return The list of product attributes sets
     * 
     */
    @NotNull
    AttributesCollectionType listProductAttributeSets() throws RemoteException;

    /**
     * Retrieves product types. See catalog-product-type-list SOAP method
     * 
     * @return the list of product types
     * 
     */
    @NotNull
    AttributesCollectionType listProductTypes() throws RemoteException;

    /**
     * Retrieve product tier prices. See catalog-product-attribute-tier-price-info
     * SOAP Method.
     * 
     * @param productId
     * @return the list of product attributes
     * 
     */
    @NotNull
    AttributesCollectionType listProductAttributeTierPrices(@NotNull ProductIdentifier productId)
        throws RemoteException;

    /**
     * Updates product tier prices. See catalog-product-attribute-tier-price-update
     * SOAP method.
     * 
     * @param productId
     * @param attributes
     */
    void updateProductAttributeTierPrices(@NotNull ProductIdentifier productId,
                                          @NotNull List<Map<String, Object>> attributes) throws RemoteException;

    /**
     * Assigns a link to a product
     * 
     * @param type
     * @param productId
     * @param linkedProduct
     * @param attributes
     * @return TODO
     */
    String assignProductLink(@NotNull String type,
    						 @NotNull ProductIdentifier productId,
                             @NotNull String linkedProduct,
                             @NotNull Map<String, Object> attributes) throws RemoteException;

    /**
     * Retrieve product link type attributes
     * 
     * @param type
     * @return
     * 
     */
    AttributesCollectionType listProductLinkAttributes(String type) throws RemoteException;

    /**
     * Retrieve linked products
     * 
     * @param type
     * @param productId
     * @return the list of links to the product
     * 
     */
    AttributesCollectionType listProductLink(@NotNull String type,
    										 @NotNull ProductIdentifier productId) throws RemoteException;

    /**
     * Deletes a link in a product
     * 
     * @param type
     * @param productId
     * @param linkedProduct
     * @return TODO
     */
    String deleteProductLink(@NotNull String type,
    						 @NotNull ProductIdentifier productId,
                             @NotNull String linkedProduct) throws RemoteException;

    /**
     * Retrieve product link types
     * 
     * @return the list of product link types
     * 
     */
    String[] listProductLinkTypes() throws RemoteException;

    /**
     * Update product link
     * 
     * @param type
     * @param product
     * @param linkedProduct
     * @param attributes
     * @return TODO
     */
    String updateProductLink(@NotNull String type,
    						 @NotNull ProductIdentifier productId,
                             @NotNull String linkedProduct,
                             @NotNull Map<String, Object> attributes) throws RemoteException;

}
