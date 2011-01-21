package org.mule.components;


import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.components.config.MagentoFiltersFactoryBean;

import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Magento.OrderItemIdQty;
import Magento.SalesOrderEntity;
import Magento.SalesOrderShipmentEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class MagentoTestCase extends FunctionalTestCase
{
	Logger log = LoggerFactory.getLogger(MagentoTestCase.class);

    protected Magento init()
    {

        Magento sf = new Magento();
        try
        {
            sf.initialise();
        }
        catch (InitialisationException e)
        {
            throw new RuntimeException(e);
        }

        sf.setUsername(System.getProperty("username"));
        sf.setPassword(System.getProperty("password"));
        sf.setAddress(System.getProperty("address"));

        return sf;
    }

    public void testConfig() throws Exception
    {
        Magento sfdc = muleContext.getRegistry().get("MagentoTest");
        assertNotNull(sfdc.getUsername());
    }

    public void testSalesOrdersList() throws Exception
    {

    	HashMap<String, String> map = new HashMap<String, String>();
        map.put("orderId", "100000001");
        map.put("filterKey", "increment_id");
        map.put("operator", "eq");
        
        MuleClient client = new MuleClient(muleContext);
        MuleMessage result = client.send("vm://getSalesOrdersList", map, null);

        Object sr = result.getPayload();
        assertNotNull(sr);
        SalesOrderEntity[] entities = (SalesOrderEntity[])sr;
        //log.debug("\n\n******* GOT " + entities.length + " SalesOrderEntities\n\n");
        assertFalse((entities.length < 1));
    
        map = new HashMap<String, String>();
        map.put("orderId", "WRONG_ID");
        map.put("filterKey", "increment_id");
        map.put("operator", "gt");
        result = client.send("vm://getSalesOrdersList", map, null);

        sr = result.getPayload();
        assertNotNull(sr);
        entities = (SalesOrderEntity[])sr;
        //log.debug("\n\n******* GOT " + entities.length + " SalesOrderEntities\n\n");
        assertTrue((entities.length < 1));   
    }

    
    public void testSalesOrdersComment() throws Exception
    {
    	HashMap<String, String> map = new HashMap<String, String>();
        map.put("orderId", "100000001");
        map.put("status", "pending");
        map.put("comment", "HelloWorld");
        map.put("notify", "true");
        
        MuleClient client = new MuleClient(muleContext);
        MuleMessage result = client.send("vm://salesOrderComment", map, null);

        Object sr = result.getPayload();
        assertNotNull(sr);
        //log.debug("\n\n******* GOT RESULT : " + sr);       
    }

    public void testSalesOrderInfo() throws Exception
    {

    	HashMap<String, String> map = new HashMap<String, String>();
        map.put("orderId", "100000001");
        
        MuleClient client = new MuleClient(muleContext);
        MuleMessage result = client.send("vm://getSalesOrderInfo", map, null);

        Object sr = result.getPayload();
        assertNotNull(sr);
        
        SalesOrderEntity entity = (SalesOrderEntity)sr;
        log.debug("\n\n******* GOT ORDER ID : " + entity.getIncrement_id());
        
        assertEquals("100000001", entity.getIncrement_id());
        
    }
    
    public void testSalesOrderHold() throws Exception
    {

    	HashMap<String, String> map = new HashMap<String, String>();
        map.put("orderId", "100000001");
        
        MuleClient client = new MuleClient(muleContext);
        MuleMessage result = client.send("vm://salesOrderHold", map, null);

        Object sr = result.getPayload();
        assertNotNull(sr);
        
        log.debug("\n\n******* GOT RESULT : " + sr);       
    }
    
    public void testSalesOrderUnhold() throws Exception
    {

    	HashMap<String, String> map = new HashMap<String, String>();
        map.put("orderId", "100000001");
        
        MuleClient client = new MuleClient(muleContext);
        MuleMessage result = client.send("vm://salesOrderUnhold", map, null);

        Object sr = result.getPayload();
        assertNotNull(sr);
        
        log.debug("\n\n******* GOT RESULT : " + sr);       
    }
    
    public void testSalesOrderCancel() throws Exception
    {

    	HashMap<String, String> map = new HashMap<String, String>();
        map.put("orderId", "100000001");
        
        MuleClient client = new MuleClient(muleContext);
        MuleMessage result = client.send("vm://salesOrderCancel", map, null);

        Object sr = result.getPayload();
        assertNotNull(sr);
        
        log.debug("\n\n******* GOT RESULT : " + sr);       
    }

    public void testSalesOrderShipmentsList() throws Exception
    {

    	HashMap<String, String> map = new HashMap<String, String>();
        map.put("orderId", "100000002");
        map.put("filterKey", "order_increment_id");
        map.put("operator", "eq");
        
        MuleClient client = new MuleClient(muleContext);
        MuleMessage result = client.send("vm://getSalesOrderShipmentsList", map, null);

        Object sr = result.getPayload();
        assertNotNull(sr);
        
        log.debug("\n\n******* GOT RESULT : " + sr);       
        SalesOrderShipmentEntity[] entities = (SalesOrderShipmentEntity[])sr;
        log.debug("\n\n******* GOT " + entities.length + " SalesOrderShipmentEntities\n\n");
        assertFalse((entities.length < 1));
    }
    
    public void testSalesOrderCreateShipment() throws Exception
    {

    	HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("orderId", "100000003");
        List<OrderItemIdQty> itemsQty = new ArrayList<OrderItemIdQty>();
        OrderItemIdQty qty = new OrderItemIdQty(3, 1);
        itemsQty.add(qty);
        
        map.put("itemsQty", itemsQty.toArray(new OrderItemIdQty[] {}));
        
        MuleClient client = new MuleClient(muleContext);
        MuleMessage result = client.send("vm://createSalesOrderShipment", map, null);

        Object sr = result.getPayload();
        assertNotNull(sr);
        
        log.debug("\n\n******* GOT RESULT : " + sr);       
        
    }

    @Override
    protected String getConfigResources()
    {
        return "magento-conf.xml";
    }
}
