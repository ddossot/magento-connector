Magento Search and Upload Demo
==============================

INTRODUCTION
  This example setups environment in order to be able to run media upload and price update examples.   

HOW TO DEMO:
  1. Set the following environment variables:
    a. magentoUsername This is the username of your Magento Account
    b. magentoPassword This is the password of your Magento account
  	c. magentoAddress This is the url of your Magento server
  	d. s3AccessKey This is the access key of your Amazon Account
    e. s3SecetKey. This is the secret key of your Amazon Account
    f. s3BucketName. This is a test bucket from where images where being uploaded to Magento
  2. Run the different setup flows from the MagentoFunctionalTestDriver, or deploy it an a Mule Container:
  	a. CreatePriceUpdatesFlow: Creates some price update document in the mongodb. Alternatively, hit
  	http://localhost:9090/magento-demo-setup-create-price-updates 
  	b. CreateProductsFlow: creates some products in Magento. Alternatively, you can hit
  	http://localhost:9090/magento-demo-setup-create-products in order to run it. 
  	c. CreateS3BucketFlow and UploadS3ImageFlow: Creates a bucket and uploads an image to it. Alternatively, 
  	hit http://localhost:9090/magento-demo-setup-create-bucket and http://magento-demo-setup-upload-s3-image, respectively
  
  
