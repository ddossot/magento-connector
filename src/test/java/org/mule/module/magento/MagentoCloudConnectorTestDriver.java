/**
 * Mule Magento Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.magento;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mule.module.magento.api.MagentoException;

/**
 * Integration test of the {@link MagentoCloudConnector}
 */
@SuppressWarnings("serial")
public class MagentoCloudConnectorTestDriver
{
    private static final String ORDER_ID = "100000001";
    private MagentoCloudConnector connector;

    @Before
    public void setup() throws Exception
    {
        connector = new MagentoCloudConnector();
        connector.setAddress("http://magento.it.zauber.com.ar/index.php/api/v2_soap");
        connector.setPassword(System.getenv("magento.password"));
        connector.setUsername(System.getenv("magento.username"));
        connector.initialise();
    }

    @Test
    public void listOrders() throws Exception
    {
        assertNotNull(connector.listOrders(null));
    }

    /**
     * Tests listing order invoices
     */
    @Test
    public void listOrdersInvoices() throws Exception
    {
        assertNotNull(connector.listOrdersInvoices(null));
    }

    /**
     * Tests getting information of an existent order
     */
    @Test
    public void getOrder() throws Exception
    {
        Map<String, Object> orderInfo = connector.getOrder(ORDER_ID);
        assertNotNull(orderInfo);
        System.out.println(ToStringBuilder.reflectionToString(orderInfo));
    }

    /**
     * Tests adding a comment to an existent order
     */
    @Test
    public void addOrderComment() throws Exception
    {
        connector.addOrderComment(ORDER_ID, "status", "A comment", false);
    }

    @Test
    public void getShipmentCarriers() throws Exception
    {
        assertFalse(connector.getOrderShipmentCarriers(ORDER_ID).isEmpty());
    }

    /**
     * Tests getting an order that does not exists
     */
    @Test(expected = MagentoException.class)
    public void getOrderInexistent()
    {
        connector.getOrder("899966");
    }

    @Test
    public void updateStockItem() throws Exception
    {
        connector.updateStockItem("1", new HashMap<String, Object>()
        {
            {
                put("manage_stock", "0");
                put("use_config_manage_stock", "0");
            }
        });
    }
    
    /**
     * Tests that filtering expressions
     * are interpreted properly the the webservice when passing numeric arguments 
     */
    @Test
    public void getWithNumericFilter() throws Exception
    {
		assertEquals(connector.listOrders("").size(),
			connector.listOrders("gt(subtotal, 800)").size() 
		  + connector.listOrders("lteq(subtotal, 800)").size()); 
    }
    
    /**
     * Tests that filtering expressions
     * are interpreted properly the the webservice when passing string 
     * arguments 
     */
    @Test
    public void getWithStringFilter() throws Exception
    {
		assertEquals(connector.listOrders("").size(),
			connector.listOrders("eq(customer_firstname, 'John')").size() 
		  + connector.listOrders("neq(customer_firstname, 'John')").size()); 
    }
    
    /**
     * Test that a user can be created
     */
    @Test
	public void createCustomer() throws Exception {
    	int customerId = connector.createCustomer(new HashMap<String, Object>(){{
    		put("email", "johndoe@mycia.com");
    		put("firstname", "John");
    		put("lastname", "Doe");
    		put("password", "123456");
		}});
    	try{
    		assertEquals("John", connector.getCustomer(customerId, Arrays.asList("firstname")).get("firstname"));
    	}finally{
    		connector.deleteCustomer(customerId);
    	}
    }
    
	/**
	 * Tests that the XXXCurrentStore SOAP methods can be used like a getter -
	 * they are not very well documented
	 * 
	 * @throws Exception
	 */
	@Test
	public void storeView() throws Exception 
	{
		assertEquals(connector.getCatalogCurrentStoreView(), connector.getCatalogCurrentStoreView());
	}
	
	@Test
    public void linkProduct() throws Exception
    {
        int productId = connector.createProduct("Hardware", 1, "HDW100001",
                new HashMap<String, Object>()
                {
                    {
                        put("", "");
                        put("", "");
                        put("", "");
                        put("", "");
                    }
                });
        Integer productId2 = connector.createProduct("Hardware", 2, "HDW100002",
                new HashMap<String, Object>()
                {
                    {
                    }
                });
        connector.addProductLink("related", productId, null, null,
                productId2.toString(), new HashMap<String, Object>()
                {
                    {
                    }
                });
        
    }

}
