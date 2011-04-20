Magento Search and Upload Demo
==============================

INTRODUCTION
  This is minimalistic a demo about adding media - images - to a product, and searching products. This demo covers search of products and
  upload of images.   

HOW TO DEMO:
  1. Set the following system properties:
    a. magentoUsername This is the username of your Magento Account
    b. magentoPassword This is the password of your Magento account
  	c. magentoAddress This is the url of your Magento server
  	d. s3AccessKey This is the access key of your Amazon Account
    e. s3SecetKey. This is the secret key of your Amazon Account
    f. s3BucketName. This is a test bucket from where images where being uploaded to Magento
  2. Run the "MainFlow" from the MagentoFunctionalTestDriver, or deploy the example in a Mule Container and hit
   localhost:9092/magento-demo-search-product-and-update-media This will search for products by keyword and add an image downloaded from S3
  	a. You can verify the content upload by simply going to the Magento Admin Console, and then Catalog -> Mange Product ->  Generic Usb Mouse -> Images

HOW IT WORKS:
   - The MainFlow searches for products with meta keyword 'technology'
   - For each product found, an image with the product's sku as filename will be downloaded from s3 and uploaded to S3 

    
