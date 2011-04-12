Magento Search and Upload Demo
==============================

INTRODUCTION
  This is minimalistic a demo about adding media - images - to a product, and searching products. This demo covers the creation and search of products, and
  upload of images.   

HOW TO DEMO:
  1. Set the following system properties:
    a. magento.username. This is the username of your Magento Account
    b. magento.password. This is the password of your Magento account
  	c. magento.address. This is the url of your Magento server
  	d. s3.accessKey. This is the access key of your Amazon Account
    e. s3.secetKey. This is the secret key of your Amazon Account
    f. s3.bucketName. This is a test bucket from where images where being uploaded to Magento
  2. Run the "SetupS3ImageFlow", "SetupS3BucketFlow" and "SetupMagentoFlow" only once, in order to create a bucket, an image and some products 
    a.  You can verify the product creations going to the Magento admin console, and then Catalog -> Manage Products. The new products should be listed there        
  3. Run the "MainFlow". This will search for products by keyword and add an image downloaded from S3
  	a. You can verify the content upload by simply going to the Magento Admin Console, and then Catalog -> Mange Product ->  Generic Usb Mouse -> Images 

HOW IT WORKS:
   - The MainFlow searches for products with meta keyword 'technology'
   - For each product found, an image with the product's sku as filename will be downloaded from s3 and uploaded to S3 

    
