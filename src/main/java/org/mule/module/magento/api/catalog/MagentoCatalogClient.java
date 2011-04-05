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

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.mule.module.magento.api.catalog.model.ProductIdentifier;

public interface MagentoCatalogClient<AttributesType, AttributesCollectionType, ExceptionType extends Exception>
{
	 
    /**
     * Lists product of a given category. See  catalog-category-assignedProducts SOAP method.   
     *  
     * @param categoryId
     * @return the listing of category products
     */
    AttributesCollectionType listCategoryProducts(int categoryId) throws ExceptionType;
    
    /**
     * Assign product to category. See catalog-category-assignProduct SOAP method
     *  
     * @param categoryId
     * @param productId
     * @param position
     */
    void addCategoryProduct(int categoryId,
                                   @NotNull ProductIdentifier productId,
                                   String position) throws ExceptionType;

    /**
     * Creates a new category. See catalog-category-create SOAP method.
     *  
     * @param parentId
     * @param attributes
     * @param storeView
     * @return the new category id
     */
    int createCategory(int parentId, @NotNull Map<String, Object> attributes, String storeView)
        throws ExceptionType;

    /**
     * TODO naming
     * catalog-category-currentStore
     */
    int catalogCategoryCurrentStore(String storeView) throws ExceptionType;

    /**
     * Deletes a category. See  catalog-category-delete SOAP method
     *  
     * @param categoryId
     */
    void deleteCategory(int categoryId) throws ExceptionType;

    /**
     * Answers category attributes. See catalog-category-info  SOAP method.  
     * 
     * @param categoryId
     * @param storeView
     * @param attributeNames
     * @return the category attributes
     */
    AttributesType getCategory(int categoryId, String storeView, List<String> attributeNames) throws ExceptionType;
    
    /** TODO 
    42.catalog-category-level Retrieve one level of categories by
        * website/store view/parent category NOTE Please make sure that you are not
        * moving category to any of its own children. There are no extra checks to
        * prevent doing it through webservices API, and you won’t be able to fix this
        * from admin interface then 
     * 
     * @param website
     * @param storeView
     * @param parentCategory
     * @return
     * @throws ExceptionType
     */
    AttributesCollectionType listCategoryLevels(String website, String storeView, String parentCategory)
        throws ExceptionType;

    /**
     * Move category in tree. See  catalog-category-move SOAP method. 
     *  
     * @param categoryId
     * @param parentId
     * @param afterId
     * 
     */
    void moveCategory(int categoryId, int parentId, String afterId) throws ExceptionType;

    /**
     * Remove a product assignment. See catalog-category-removeProduct SOAP method. 
     *   
     * @param categoryId
     * @param productId
     * @return
     * 
     */
    void deleteCategoryProduct(int categoryId, @NotNull ProductIdentifier productId)
        throws ExceptionType;

    /**
     * TODO return something else 
     * 
     * Retrieve hierarchical tree. See  catalog-category-tree SOAP method. 
     * @param parentId
     * @param storeView
     * @return
     * 
     */
    AttributesType getCategoryTree(String parentId, String storeView) throws ExceptionType;

    /**
     * Updates a category. See catalog-category-update SOAP method
     * 
     * @param categoryId
     * @param attributes
     * @param storeView
     * @return
     */
    void updateCategory(int categoryId, @NotNull Map<String, Object> attributes, String storeView)
        throws ExceptionType;

    /**
     * 
     * @param categoryId
     * @param productId
     * @param position
     * @return
     * 
     */
    void updateCategoryProduct(int categoryId,
                                                @NotNull ProductIdentifier productId,
                                                String position) throws ExceptionType;

    /**
     * 
     * @param products
     * @return
     */
    AttributesCollectionType listInventoryStockItems(String[] products) throws ExceptionType;

    /**
     * 
     * @param productId
     * @param attributes
     * @return
     * 
     */
    int updateInventoryStockItem(@NotNull ProductIdentifier productId, @NotNull Map<String, Object> attributes)
        throws ExceptionType;
	
	
   /**
    * Creates a new product
    * 
    * @param type the new product's type
    * @param set the new product's set
    * @param sku the new product's sku
    * @param attributes the attributes of the new product
    * @return the new product's id
    */
	int createProduct(@NotNull String type, @NotNull int set, @NotNull String sku, @NotNull Map<String, Object> attributes)
        throws ExceptionType;

    /**
     * @param storeView
     * @return
     * 
     */
    void updateProductStoreView(String storeView) throws ExceptionType;
    
    /**
     * TODO
     * 
     * @param storeView
     * @return
     * 
     */
    void getProductStoreView(String storeView) throws ExceptionType;

    /**
     * Deletes a product.
     *  See catalog-product-delete SOAP method. 
     * 
     * @param productId the product identifier
     */
    void deleteProduct(ProductIdentifier productId) throws ExceptionType;

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
    AttributesType getProductSpecialPrice(@NotNull ProductIdentifier productId, String storeView)
        throws ExceptionType;

    /**
     * Answers a product's attributes. See catalog-product-info SOAP method. 
     *
     * @param product
     * @param storeView
     * @param attributes
     * @param productId.getIdentifierType()
     * @return the product attributes
     */
    AttributesType getProduct(@NotNull ProductIdentifier productId,
                             String storeView,
                             @NotNull Map<String, Object> attributes) throws ExceptionType;
    
    //TODO store view or code

    /**
     * Retrieve products list by filters
     * See catalog-product-list SOAP method. 
     * @param filters an optional filtering expression
     * @param storeView an optional storeView
     * @return the list of product attributes that match the given optional filtering expression
     */
    AttributesCollectionType listProducts(String filters, String storeView) throws ExceptionType;

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
     * @throws ExceptionType
     */
    int updateProductSpecialPrice(@NotNull ProductIdentifier productId,
                                         @NotNull String specialPrice,
                                         String fromDate,
                                         String toDate,
                                         String storeView) throws ExceptionType;
    
    /**
     * Updates a product. See catalog-category-updateProduct SOAP method 
     * 
     * @param attributes
     * @param storeViewIdOrCode optional store view
     */
    void updateProduct(@NotNull ProductIdentifier productId,
                              @NotNull Map<String, Object> attributes,
                              String storeViewIdOrCode) throws ExceptionType;
    


	

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
                                       String storeView) throws ExceptionType;

    /**
     * Set current store view. See catalog-product-attribute-media-currentStore SOAP
     * method. TODO verify
     * 
     * @param storeView
     * @return
     * 
     */
    void updateProductAttributeMediaStoreView(@NotNull String storeView) throws ExceptionType;

    /**
     * Gets current store view. See catalog-product-attribute-media-currentStore SOAP
     * method. TODO verify
     * 
     * @param storeView
     * @return
     * 
     */
    int getProductAttributeMediaStoreView() throws ExceptionType;

    /**
     * Answers product image attributes. See catalog-product-attribute-media-info SOAP method 
     * @param productId
     * @param file
     * @param storeView
     * @return the product attributes
     */
    AttributesType getProductAttributeMedia(@NotNull ProductIdentifier productId,
                                            @NotNull String file,
                                            String storeView) throws ExceptionType;

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
                                                       String storeView) throws ExceptionType;

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
        throws ExceptionType;

    /**
     * Retrieve product image types. See catalog-product-attribute-media-types SOAP
     * method.
     * 
     * @param setId
     * @return
     * 
     */
    AttributesCollectionType listProductAttributeMediaTypes(String setId) throws ExceptionType;

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
                                    String storeView) throws ExceptionType;

    /**
     * Retrieves category attributes. See catalog-category-attribute-list SOAP
     * method.
     * 
     * @return the list of attributes
     * 
     */
    AttributesCollectionType listCategoryAttributes() throws ExceptionType;

    /**
     * TODO verify
     * 
     * @param storeView
     * 
     */
    void updateCategoryAttributeStoreView(String storeView) throws ExceptionType;

    /**
     * TODO verify
     * 
     * @return
     * 
     */
    int getCategoryAttributeStoreView() throws ExceptionType;

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
        throws ExceptionType;

    /**
     * Sets the product attribute store. TODO verify
     * 
     * @param storeView
     * 
     */
    void updateProductAttributeStoreView(@NotNull String storeView) throws ExceptionType;

    /**
     * Gets the product attribute store view. TODO verify
     * 
     * @return the current store
     * 
     */
    int getProductAttributeStoreView() throws ExceptionType;

    /**
     * Retrieves product attributes list. See catalog-product-attribute-list SOAP
     * methods
     * 
     * @param setId
     * @return the list of product attributes
     * 
     */
    AttributesCollectionType listProductAttributes(int setId) throws ExceptionType;

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
        throws ExceptionType;

    /**
     * Retrieves product attribute sets. See catalog-product-attribute-set-list SOAP
     * method.
     * 
     * @return The list of product attributes sets
     * 
     */
    @NotNull
    AttributesCollectionType listProductAttributeSets() throws ExceptionType;

    /**
     * Retrieves product types. See catalog-product-type-list SOAP method
     * 
     * @return the list of product types
     * 
     */
    @NotNull
    AttributesCollectionType listProductTypes() throws ExceptionType;

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
        throws ExceptionType;

    /**
     * Updates product tier prices. See catalog-product-attribute-tier-price-update
     * SOAP method.
     * 
     * @param productId
     * @param attributes
     */
    void updateProductAttributeTierPrices(@NotNull ProductIdentifier productId,
                                          @NotNull List<Map<String, Object>> attributes) throws ExceptionType;

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
                             @NotNull Map<String, Object> attributes) throws ExceptionType;

    /**
     * Retrieve product link type attributes
     * 
     * @param type
     * @return
     * 
     */
    AttributesCollectionType listProductLinkAttributes(String type) throws ExceptionType;

    /**
     * Retrieve linked products
     * 
     * @param type
     * @param productId
     * @return the list of links to the product
     * 
     */
    AttributesCollectionType listProductLink(@NotNull String type,
    										 @NotNull ProductIdentifier productId) throws ExceptionType;

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
                             @NotNull String linkedProduct) throws ExceptionType;

    /**
     * Retrieve product link types
     * 
     * @return the list of product link types
     * 
     */
    String[] listProductLinkTypes() throws ExceptionType;

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
                             @NotNull Map<String, Object> attributes) throws ExceptionType;

}
