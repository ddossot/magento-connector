Mule Magento Cloud Connector
============================

Mule Cloud connector to Magento

Installation
------------

The connector can either be installed for all applications running within the Mule instance or can be setup to be used
for a single application.

*All Applications*

Download the connector from the link above and place the resulting jar file in
/lib/user directory of the Mule installation folder.

*Single Application*

To make the connector available only to single application then place it in the
lib directory of the application otherwise if using Maven to compile and deploy
your application the following can be done:

Add the connector's maven repo to your pom.xml:

    <repositories>
        <repository>
            <id>muleforge-releases</id>
            <name>MuleForge Snapshot Repository</name>
            <url>https://repository.muleforge.org/release/</url>
            <layout>default</layout>
        </repsitory>
    </repositories>

Add the connector as a dependency to your project. This can be done by adding
the following under the dependencies element in the pom.xml file of the
application:

    <dependency>
        <groupId>org.mule.modules</groupId>
        <artifactId>mule-module-magento</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>

Configuration
-------------

You can configure the connector as follows:

    <magento:config username="value" password="value" address="value"/>

Here is detailed list of all the configuration attributes:

| attribute | description | optional | default value |
|:-----------|:-----------|:---------|:--------------|
|name|Give a name to this configuration so it can be later referenced by config-ref.|yes||
|username||no|
|password||no|
|address||no|
















Add Order Shipment Comment
--------------------------

Adds a comment to the shipment. 

Example:



     <magento:add-order-shipment-comment 
             shipmentId="#[map-payload:shipmentId]" 
             comment="#[map-payload:comment]" 
             sendEmail="true" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|shipmentId|the shipment's increment id|no||
|comment|the comment to add|no||
|sendEmail|if an email must be sent after shipment creation|yes|false|
|includeCommentInEmail|if the comment must be sent in the email|yes|false|



Add Order Shipment Track
------------------------

Adds a new tracking number

Example:



     <magento:add-order-shipment-track
             shipmentId="#[map-payload:shipmentId]" 
             carrierCode="#[map-payload:carrierCode]"
             title="#[map-payload:title]" 
             trackId="#[map-payload:trackId]" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|shipmentId|the shipment id|no||
|carrierCode|the new track's carrier code|no||
|title|the new track's title|no||
|trackId||no||

Returns new track's id



Cancel Order
------------

Cancels an order

Example:



     <magento:cancel-order
            orderId="#[map-payload:orderId]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|orderId|the order to cancel|no||



Create Order Shipment
---------------------

Creates a shipment for order

Example:



     <magento:create-order-shipment 
             orderId="#[map-payload:orderId]"
             comment="#[map-payload:comment]">
             <magento:itemsQuantities>
                 <magento:itemsQuantity key="#[map-payload:orderItemId1]" value="#[map-payload:Quantity1]"/>
                 <magento:itemsQuantity key="#[map-payload:orderItemId2]" value="#[map-payload:Quantity2]"/>
             </magento:itemsQuantities>
          </magento:create-order-shipment>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|orderId|the order increment id|no||
|itemsQuantities|a map containing an entry per each (orderItemId, quantity) pair|no||
|comment|an optional comment|yes||
|sendEmail|if an email must be sent after shipment creation|yes|false|
|includeCommentInEmail|if the comment must be sent in the email|yes|false|

Returns new shipment's id



Get Order
---------

Answers the order properties for the given orderId

Example:



      <magento:get-order orderId="#[map-payload:orderId]" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|orderId|the order whose properties to fetch|no||

Returns string-object map of order properties



Get Order Invoice
-----------------

Retrieves order invoice information

Example:



           <magento:get-order-invoice invoiceId="#[map-payload:invoiceId]"  />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|invoiceId||no||

Returns invoice attributes



Get Order Shipment Carriers
---------------------------

Creates an invoice for the given order

Example:



      <magento:get-order-shipment-carriers  orderId="#[map-payload:orderId]"  />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|orderId||no||

Returns new invoice's id



Get Order Shipment
------------------

Adds a comment to the given order's invoice

Example:



      <magento:get-order-shipment 
                shipmentId="#[map-payload:orderShipmentId]" /> 

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|shipmentId||no||



Hold Order
----------

Puts order on hold

Example:



      <magento:hold-order orderId="#[map-payload:orderId]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|orderId||no||



List Orders
-----------

Lists order attributes that match the 
given filtering expression

Example



     <magento:list-orders 
                filter="gt(subtotal, #[map-payload:minSubtotal])"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|filter||yes||

Returns list of string-object maps



List Orders Invoices
--------------------

Lists order invoices that match the given filtering expression

Example:



     <magento:list-orders-invoices filter="notnull(parent_id)" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|filter||yes||

Returns of string-object maps order attributes



List Orders Shipments
---------------------

Lists order shipment atrributes that match the given 
optional filtering expression

Example:



     <magento:list-orders-shipments filter="null(parent_id)" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|filter||yes||

Returns of string-object map order shipments attributes



Delete Order Shipment Track
---------------------------

Deletes the given track of the given order's shipment

<magento:delete-order-shipment-track
      shipmentId="#[map-payload:shipmentId]" trackId="#[map-payload:trackId]" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|shipmentId|the target shipment id|no||
|trackId|the id of the track to delete|no||



Add Order Comment
-----------------

Adds a comment to the given order id



     <magento:add-order-comment 
                  orderId="#[map-payload:orderId]"
                 status="#[map-payload:status]" 
                 comment="#[map-payload:comment]" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|orderId|the order id|no||
|status|the comment status|no||
|comment|the comment|no||
|sendEmail|if an email must be sent after shipment creation|yes|false|



Unhold Order
------------

Releases order

Example:



      <magento:unhold-order orderId="#[map-payload:orderId]" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|orderId||no||



Create Order Invoice
--------------------

Creates an invoice for the given order

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|orderId||no||
|itemsQuantities|a map containing an entry per each (orderItemId, quantity) pair|no||
|comment|an optional comment|yes||
|sendEmail|if an email must be sent after shipment creation|yes|false|
|includeCommentInEmail|if the comment must be sent in the email|yes|false|

Returns new invoice's id



Add Order Invoice Comment
-------------------------

Adds a comment to the given order's invoice

Example: 



      <magento:add-order-comment 
                orderId="#[map-payload:orderId]"
                status="#[map-payload:status]" 
                comment="#[map-payload:comment]" 

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|invoiceId|the invoice id|no||
|comment|the comment to add|no||
|sendEmail|if an email must be sent after shipment creation|yes|false|
|includeCommentInEmail|if the comment must be sent in the email|yes|false|



Capture Order Invoice
---------------------

Captures and invoice

Example: 



      <magento:capture-order-invoice invoiceId="#[map-payload:invoiceId]"  />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|invoiceId|the invoice to capture|no||



Void Order Invoice
------------------

Voids an invoice

Example: 



      <magento:void-order-invoice invoiceId="#[map-payload:invoiceId]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|invoiceId|the invoice id|no||



Cancel Order Invoice
--------------------

Cancels an order's invoice

Example: 



      <magento:cancel-order-invoice invoiceId="#[map-payload:invoiceId]"  />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|invoiceId|the invoice id|no||



Create Customer Address
-----------------------

Creates a new address for the given customer using the given address
attributes



     <magento:create-customer-address customerId="#[map-payload:customerId]"  >
               <magento:attributes >
                 <magento:attribute key="city_code" value="#[map-payload:cityCode]"/>
               </magento:attributes>
             </magento:create-customer-address>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|customerId||no||
|attributes||no||

Returns new customer address id



Create Customer
---------------

Creates a customer with the given attributes

Example: 



          <magento:create-customer>
                 <magento:attributes >
                   <magento:attribute key="email" value="#[map-payload:email]"/>
                   <magento:attribute key="firstname" value="#[map-payload:firstname]"/>
                   <magento:attribute key="lastname" value="#[map-payload:lastname]"/>
                    <magento:attribute key="password" value="#[map-payload:password]"/>
                 </magento:attributes>
              </magento:create-customer>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|attributes|the attributes of the new customer|no||

Returns new customer id



Delete Customer
---------------

Deletes a customer given its id

Example:



     <magento:delete-customer customerId="#[map-payload:customerId]" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|customerId||no||



Delete Customer Address
-----------------------

Deletes a Customer Address

Example:



     <magento:delete-customer-address addressId="#[map-payload:addressId]" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|addressId||no||



Get Customer
------------

Answers customer attributes for the given id. Only the selected attributes are
retrieved

Example:



     <magento:get-customer  customerId="#[map-payload:customerId]"  >
          <magento:attributeNames>
               <magento:attributeName>customer_name</magento:attributeName>
                 <magento:attributeName>customer_last_name </magento:attributeName>
               <magento:attributeName>customer_age</magento:attributeName>
          </magento:attributeNames>
         </magento:get-customer>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|customerId||no||
|attributeNames|the attributes to retrieve. Not empty|no||

Returns attributes map



Get Customer Address
--------------------

Answers the customer address attributes

Example: 


     <magento:get-customer-address  addressId="#[map-payload:addressId]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|addressId||no||

Returns customer address attributes map



List Customer Addresses
-----------------------

Lists the customer address for a given customer id

Example: 



      <magento:list-customer-addresses customerId="#[map-payload:customerAddresses]" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|customerId|the id of the customer|no||

Returns listing of addresses



List Customer Groups
--------------------

Lists all the customer groups

Example: 



     <magento:list-customer-groups />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||

Returns listing of groups attributes



List Customers
--------------

Answers a list of customer attributes for the given filter expression.

Example:



     <magento:list-customers filters="gteq(customer_age, #[map-payload:minCustomerAge])" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|filters|an optional filtering expression.|yes||

Returns list of attributes map



Update Customer
---------------

Updates the given customer attributes, for the given customer id. Password can
not be updated using this method

Example:



     <magento:update-customer customerId="#[map-payload:customerId]">
               <magento:attributes>
                  <magento:attribute key="lastname" value="#[map-payload:lastname]"/>
               </magento:attributes>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|customerId||no||
|attributes|the attributes map|no||



Update Customer Address
-----------------------

Updates the given map of customer address attributes, for the given customer address

Example:



     <magento:update-customer-address addressId="#[map-payload:addressId]">
             <magento:attributes>
                  <magento:attribute key="street" value="#[map-payload:street]"/>
                  <magento:attribute key="region" value="#[map-payload:region]"/>
              </magento:attributes>
           </magento:update-customer-address>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|addressId|the customer address to update|no||
|attributes|the address attributes to update|no||



List Stock Items
----------------

Retrieve stock data by product ids

Example:



     <magento:list-stock-items >
             <magento:productIdOrSkus>
                  <magento:productIdOrSku>1560</magento:productIdOrSku>
                 <magento:productIdOrSku>JJFO986</magento:productIdOrSku>
             </magento:productIdOrSkus>
        </magento:list-stock-items>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|productIdOrSkus|a not empty list of product ids or skus whose attributes to list|no||

Returns list of stock items attributes



Update Stock Item
-----------------

Update product stock data given its id or sku

Example:



      <magento:update-stock-item productIdOrSku="#[map-payload:productIdOrSku]">
             <magento:attributes>
                  <magento:attribute key="qty" value="#[map-payload:quantity]"/>
             </magento:attributes>
        </magento:update-stock-item>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|productIdOrSku|the product id or sku of the product to update|no||
|attributes||no||



List Directory Countries
------------------------

Answers the list of countries
Example:


     <magento:list-directory-countries"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||

Returns collection of countries attributes



List Directory Regions
----------------------

Answers a list of regions for the given county
Example:


     <magento:list-directory-regions countryId="#[map-payload:countryId]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|countryId|the country code, in ISO2 or ISO3 format|no||

Returns collection of regions attributes



Add Product Link
----------------

Links two products, given its source and destination productIdOrSku.
Example:



      <magento:add-product-link type="#[map-payload:type]"     
                             productId="#[map-payload:productId]"
                             linkedProductIdOrSku="#[map-payload:linkedProductId]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|type|the product type|no||
|productId|the id of the source product. Use it instead of productIdOrSku in case you are sure the source product identifier is a product id|yes||
|productSku|the sku of the source product. Use it instead of productIdOrSku in case you are sure the source product identifier is a product sku|yes||
|productIdOrSku|the id or sku of the source product.|yes||
|linkedProductIdOrSku|the destination product id or sku.|no||
|attributes|the link attributes|yes||



Create Product Attribute Media
------------------------------

Creates a new product media. See catalog-product-attribute-media-create SOAP
method. 
Example:



      
      <magento:create-product-attribute-media 
           content="#[map-payload:content]" 
          productId="#[map-payload:productId]"
          fileName="#[map-payload:fileName]" 
          mimeType="JPEG">
          <magento:attributes>
              <magento:attribute key="label" value="#[map-payload:label]"/>
              <magento:attribute key="position" value="#[map-payload:position]"/>
          </magento:attributes>
      </magento:create-product-attribute-media>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|productId|the id of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product id|yes||
|productSku|the sku of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product sku|yes||
|productIdOrSku|the id or sku of the product.|yes||
|attributes|the media attributes|yes||
|storeViewIdOrCode|the id or code of the target store. Left unspecified for using current store|yes||
|content|the image to upload|no||
|mimeType|the mimetype|no||*JPEG*, *GIF*, *PNG*
|baseFileName|the base name of the new remote image|yes||

Returns new image filename



Delete Product Attribute Media
------------------------------

Removes a product image. See catalog-product-attribute-media-remove SOAP
method.
Example:



     <magento:delete-product-attribute-media 
                 productSku="#[map-payload:productSku]" 
                 fileName="#[map-payload:fileName]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|productId|the id of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product id|yes||
|productSku|the sku of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product sku|yes||
|productIdOrSku|the id or sku of the product.|yes||
|fileName||no||



Delete Product Link
-------------------

Deletes a product's link.

Example:



     <magento:delete-product-link 
                 type="#[map-payload:type]"                    
                 productId="#[map-payload:productId]"
                 linkedProductIdOrSku="#[map-payload:linkedProductId]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|type|link type|no||
|productId|the id of the source product. Use it instead of productIdOrSku in case you are sure the source product identifier is a product id|yes||
|productSku|the sku of the source product. Use it instead of productIdOrSku in case you are sure the source product identifier is a product sku|yes||
|productIdOrSku|the id or sku of the source product.|yes||
|linkedProductIdOrSku||no||



Get Product Attribute Media
---------------------------

Lists linked products to the given product and for the given link type.
Example:



     <magento:get-product-attribute-media 
                 productIdOrSku="#[map-payload:productIdOrSku]"
                 fileName="#[map-payload:fileName]"
                 storeViewIdOrCode="#[map-payload:storeViewIdOrCode]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|productId|the id of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product id|yes||
|productSku|the sku of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product sku|yes||
|productIdOrSku|the id or sku of the product.|yes||
|fileName||no||
|storeViewIdOrCode||yes||

Returns list of links to the product





Update Category Attribute Store View
------------------------------------

Set the default catalog store view for this session

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|storeViewIdOrCode|the id or code of the target store. Left unspecified for using current store the id or code of the store view to set as default for this session|no||



List Category Attributes
------------------------

Retrieve product image types. See catalog-product-attribute-media-types SOAP
method.

Example: 


     <magento:list-category-attributes setId="#[map-payload:setId]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||

Returns list of category attributes



List Category Attribute Options
-------------------------------

Retrieves attribute options. See catalog-category-attribute-options SOAP
method.

Example:


     <magento:list-category-attributes-options attributeId="#[map-payload:attributeId]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|attributeId||no||
|storeViewIdOrCode|the id or code of the target store. Left unspecified for using current store|yes||

Returns list of category attribute options



List Product Attribute Media
----------------------------

Retrieves product image list. See catalog-product-attribute-media-list SOAP
method
Example:


       <magento:list-product-attribute-media
                     productId="#[map-payload:productId]"
                     storeViewIdOrCode="#[map-payload:storeViewIdOrCode]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|productId|the id of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product id|yes||
|productSku|the sku of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product sku|yes||
|productIdOrSku|the id or sku of the product.|yes||
|storeViewIdOrCode|the id or code of the target store. Left unspecified for using current store|yes||

Returns list of product images attributes



List Product Attribute Media Types
----------------------------------

Retrieve product image types. See catalog-product-attribute-media-types SOAP
method.

Example:


     <magento:list-product-attribute-media-types setId="#[map-payload:setId]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|setId|the setId|no||

Returns list of attribute media types



List Product Attribute Options
------------------------------

Answers the product attribute options. See catalog-product-attribute-options
SOAP method.

Example:


     <magento:list-product-attribute-options attributeId="#[map-payload:attributeId]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|attributeId||no||
|storeViewIdOrCode|the id or code of the target store. Left unspecified for using current store|yes||

Returns attributes list



List Product Attributes
-----------------------

Retrieves product attributes list. See catalog-product-attribute-list SOAP
methods

Example:



     <magento:list-product-attributes setId="#[map-payload:setId]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|setId||no||

Returns list of product attributes



List Product Attribute Sets
---------------------------

Retrieves product attribute sets. See catalog-product-attribute-set-list SOAP
method.

Example:



     <magento:list-product-attribute-sets/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||

Returns list of product attributes sets



List Product Attribute Tier Prices
----------------------------------

Retrieve product tier prices. See catalog-product-attribute-tier-price-info
SOAP Method.
Example:



     <magento:list-product-attribute-tier-prices productIdOrSku="#[map-payload:productIdOrSku]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|productId|the id of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product id|yes||
|productSku|the sku of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product sku|yes||
|productIdOrSku|the id or sku of the product.|yes||

Returns list of product attributes



List Product Link
-----------------

Lists linked products to the given product and for the given link type

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|type|the link type|no||
|productId|the id of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product id|yes||
|productSku|the sku of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product sku|yes||
|productIdOrSku|the id or sku of the product.|yes||

Returns list of links to the product



List Product Link Attributes
----------------------------

Lists all the attributes for the given product link type

Example:



     <magento:list-product-link-attributes type="#[map-payload:type]" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|type|the product type|no||

Returns listing of product attributes



List Product Link Types
-----------------------

Answers product link types
Example:



     <magento:list-product-link-types />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||

Returns list of product link types



List Product Types
------------------

Answers product types. See catalog-product-type-list SOAP method
Example:



     <magento:list-product-types />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||

Returns list of product types



Update Product Attribute Media
------------------------------

Updates product media. See catalog-product-attribute-media-update
Example:


     <magento:update-product-attribute-media fileName="#[map-payload:fileName]" productId="#[map-payload:productId]">
             <magento:attributes>
                 <magento:attribute key="label" value="#[map-payload:label]"/>
             </magento:attributes>
           </magento:update-product-attribute-media>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|productId|the id of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product id|yes||
|productSku|the sku of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product sku|yes||
|productIdOrSku|the id or sku of the product.|yes||
|fileName||no||
|attributes||no||
|storeViewIdOrCode|the id or code of the target store. Left unspecified for using current store|yes||



Update Product Attribute Tier Price
-----------------------------------

Updates a single product tier price. See catalog-product-attribute-tier-price-update
SOAP method.

Example:



     <magento:update-product-attribute-tier-price 
             productSku="#[map-payload:productSku]"> 
            <magento:attributes>
             <magento:attribute key="price" value="#[map-payload:price]"/>
             <magento:attribute key="qty" value="#[map-payload:quantity]"/>
             <magento:attribute key="website" value="#[map-payload:website]"/>
             <magento:attribute key="customer_group_id" value="#[map-payload:customerGroupId]"/>
            </magento:attributes>
           <magento:update-product-attribute-tier-price/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|productId||yes||
|productSku||yes||
|productIdOrSku||yes||
|attributes|the tier price to update.|no||



Update Product Link
-------------------

Update product link
Example:



     <magento:update-product-link type="#[map-payload:type]" productId="#[map-payload:productId]"
                    linkedProductIdOrSku="#[map-payload:linkedProductId]">
             <magento:attributes>
                 <magento:attribute key="qty" value="#[map-payload:qty]"/>
             </magento:attributes>                   
           </magento:update-product-link>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|type||no||
|productId|the id of the source product. Use it instead of productIdOrSku in case you are sure the source product identifier is a product id|yes||
|productSku|the sku of the source product. Use it instead of productIdOrSku in case you are sure the source product identifier is a product sku|yes||
|productIdOrSku|the id or sku of the source product.|yes||
|linkedProductIdOrSku|the destination product id or sku.|no||
|attributes|the link attributes|no||



List Category Products
----------------------

Lists product of a given category. See  catalog-category-assignedProducts SOAP method.   
Example:



     <magento:list-category-products  categoryId="#[map-payload:categoryId]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|categoryId||no||

Returns listing of category products



Add Category Product
--------------------

Assign product to category. See catalog-category-assignProduct SOAP method

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|categoryId||no||
|productId|the id of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product id|yes||
|productSku|the sku of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product sku|yes||
|productIdOrSku|the id or sku of the product.|yes||
|position||no||



Create Category
---------------

Creates a new category. See catalog-category-create SOAP method.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|parentId||no||
|attributes|the new category attributes|no||
|storeViewIdOrCode|the id or code of the target store. Left unspecified for using current store|yes||

Returns new category id



Delete Category
---------------

Deletes a category. See  catalog-category-delete SOAP method
Example:


     <magento:delete-category categoryId="#[map-payload:categoryId]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|categoryId|the category to delete|no||



Get Category
------------

Answers category attributes. See catalog-category-info SOAP method. 
Example:


     <magento:get-category categoryId="#[map-payload:categoryId]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|categoryId||no||
|storeViewIdOrCode|the id or code of the target store. Left unspecified for using current store|yes||
|attributeNames||no||

Returns category attributes



List Category Levels
--------------------

Answers levels of categories for a website, store view and parent category
Example:



     <magento:list-category-levels/> 

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|website||yes||
|storeViewIdOrCode||yes||
|parentCategoryId||yes||

Returns list of categories attributes



Move Category
-------------

Move category in tree. See  catalog-category-move SOAP method. Please make sure that you are not 
moving category to any of its own children. There are no
extra checks to prevent doing it through webservices API, and you won’t be
able to fix this from admin interface then .

Example:



     <magento:move-category categoryId="#[map-payload:categoryId]" parentId="#[map-payload:afterId]"/> 

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|categoryId|the id of the category to be moved|no||
|parentId|the new parent category id|no||
|afterId|an optional category id for use as reference in the positioning of the moved category|yes||



Delete Category Product
-----------------------

Remove a product assignment. See catalog-category-removeProduct SOAP method. 
Example:



     <magento:delete-category-product 
                 categoryId="#[map-payload:categoryId]"
                 productSku="#[map-payload:productSku]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|categoryId||no||
|productId|the id of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product id|yes||
|productSku|the sku of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product sku|yes||
|productIdOrSku|the id or sku of the product.|yes||



Get Category Tree
-----------------

Answers the category tree. 
See  catalog-category-tree SOAP method.

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|parentId||no||
|storeViewIdOrCode||yes||

Returns category tree attributes



Update Category
---------------

Updates a category. See catalog-category-update SOAP method

Example:



     <magento:update-category categoryId="#[map-payload:categoryId]" />  
             <magento:attributes>
                 <magento:attribute key="name" value="#[map-payload:categoryName]"/>
             </magento:attributes>
          </magento:update-category>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|categoryId||no||
|attributes||no||
|storeViewIdOrCode|the id or code of the target store. Left unspecified for using current store|yes||



Update Category Product
-----------------------

Updates a category product 

Example:



     <magento:update-category-product 
                 categoryId="#[header:categoryId]" 
                 position="#[header:position]"
                 productSku="#[header:productSku]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|categoryId|the category id|no||
|productId|the id of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product id|yes||
|productSku|the sku of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product sku|yes||
|productIdOrSku|the id or sku of the product.|yes||
|position||no||



List Inventory Stock Items
--------------------------

Lists inventory stock items.
 Example: 
 

     <magento:list-inventory-stock-items >
             <magento:productIdOrSkus>
                 <magento:productIdOrSku>1560</magento:productIdOrSku>
                 <magento:productIdOrSku>10036</magento:productIdOrSku>
                 <magento:productIdOrSku>9868</magento:productIdOrSku>
             </magento:productIdOrSkus>
            </magento:list-inventory-stock-items>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|productIdOrSkus||no||

Returns list of attributes



Update Inventory Stock Item
---------------------------

Updates an stock inventory item



      <magento:update-product-inventory-stock-item  productIdOrSku="#[map-payload:productIdOrSku]">
               <magento:attributes>
                 <magento:attribute key="qty" value="#[map-payload:quantity]"/>
               </magento:attributes>
            </magento:update-product-inventory-stock-item>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|productId|the id of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product id|yes||
|productSku|the sku of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product sku|yes||
|productIdOrSku|the id or sku of the product.|yes||
|attributes||no||



Create Product
--------------

Creates a new product

Example:


     <magento:create-product set="4" sku="78962" type="simple"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|type|the new product's type|no||
|set|the new product's set|no||
|sku|the new product's sku|no||
|attributes|the attributes of the new product|yes||

Returns new product's id



Delete Product
--------------

Deletes a product. See catalog-product-delete SOAP method.

Example:



     <magento:delete-product productId="#[map-payload:productId]" />

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|productId|the id of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product id|yes||
|productSku|the sku of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product sku|yes||
|productIdOrSku|the id or sku of the product.|yes||



Get Product Special Price
-------------------------

Answers a product special price. See catalog-product-getSpecialPrice SOAP method.

Example: 



     <magento:get-product-special-price productId="#[map-payload:productId]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|productId|the id of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product id|yes||
|productSku|the sku of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product sku|yes||
|productIdOrSku|the id or sku of the product.|yes||
|storeViewIdOrCode|the id or code of the target store. Left unspecified for using current store|yes||

Returns product special price attributes



Get Product
-----------

Answers a product's specified attributes. At least one of attributNames or
additionalAttributeNames must be non null and non empty. See
catalog-product-info SOAP method



       <magento:get-product  productIdOrSku="#[map-payload:productIdOrSku]" storeViewIdOrCode="#[map-payload:storeViewIdOrCode]">
                 <magento:additionalAttributeNames>
                     <magento:additionalAttributeName>keyboard_distribution_type</magento:additionalAttributeName>
                 </magento:additionalAttributeNames>
             </magento:get-product>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|productId|the id of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product id|yes||
|productSku|the sku of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product sku|yes||
|productIdOrSku|the id or sku of the product.|yes||
|storeViewIdOrCode|the id or code of the target store. Left unspecified for using current store|yes||
|attributesNames||yes||
|additionalAttributeNames|the list of non standard attributes to be returned in the additionalAttributes attribute|yes||

Returns attributes



List Products
-------------

Retrieve products list by filters. See catalog-product-list SOAP method.

Example:



     <magento:list-products/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|filters|an optional filtering expression|yes||
|storeViewIdOrCode|the id or code of the target store. Left unspecified for using current store|yes||

Returns list of product attributes that match the given optional filtering expression



Update Product Special Price
----------------------------

Sets a product special price. See catalog-product-setSpecialPrice SOAP method

Example:



     <magento:update-product-special-price specialPrice="#[variable:session:specialPrice]" productId="#[variable:session:productId]"/>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|productId|the id of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product id|yes||
|productSku|the sku of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product sku|yes||
|productIdOrSku|the id or sku of the product.|yes||
|specialPrice|the special price to set|no||
|fromDate||yes||
|toDate||yes||
|storeViewIdOrCode|the id or code of the target store. Left unspecified for using current store|yes||



Update Product
--------------

Updates a product. See catalog-category-updateProduct SOAP method 
Example:



     <magento:update-product productIdOrSku="#[map-payload:productIdOrSku]">
             <magento:attributes>
                 <magento:attribute key="name" value="#[map-payload:name]"/>
             </magento:attributes>
           </magento:update-product>

| attribute | description | optional | default value | possible values |
|:-----------|:-----------|:---------|:--------------|:----------------|
|config-ref|Specify which configuration to use for this invocation|yes||
|productId|the id of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product id|yes||
|productSku|the sku of the product. Use it instead of productIdOrSku in case you are sure the product identifier is a product sku|yes||
|productIdOrSku|the id or sku of the product.|yes||
|attributes|the not empty map of product attributes to update|no||
|storeViewIdOrCode|the id or code of the target store. Left unspecified for using current store|yes||





































