Magento Search and Upload Demo
==============================

INTRODUCTION
  This is minimalistic a demo about updating product attributes like price. This demo covers the creation and modification of products

HOW TO DEMO:
  1. Set the following system properties:
    a. magentoUsername This is the username of your Magento Account
    b. magentoPassword This is the password of your Magento account
  	c. magentoAddress This is the url of your Magento server
  	d. mongo.database. This is the name of the database that contains price updates
    e. mongo.hostname. This is the host where the mongo db is located
  2. Run the "SetupFlow" only once, in order to create some products in Magento 
    a.  You can verify the product creations going to the Magento admin console, and then Catalog -> Manage Products. The new products should be listed there        
  3. Run the "MainFlow". This will query price-updates documents in a mongodb - those price updates should be created manually - and will update such prices
  for such products in Magento
  	a. You can verify the price update by simply going to the Magento Admin Console, and then Catalog -> Mange Product -> Price column 

HOW IT WORKS:
   - The MainFlow gets all mongo documents in the form { sku: <productSku>, price: <productPrice> }, that represent price updates
   - For each price update, prices are updates for each referred sku.   

    
