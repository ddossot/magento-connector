/*
 * $Id: $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.components;

import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Magento.AssociativeEntity;
import Magento.ComplexFilter;
import Magento.Filters;
import Magento.Mage_Api_Model_Server_V2_HandlerPortType;
import Magento.MagentoServiceLocator;
import Magento.OrderItemIdQty;
import Magento.SalesOrderEntity;
import Magento.SalesOrderShipmentEntity;

/**
 * @author eberman
 *
 */
/**
 * @author eberman
 *
 */
public class Magento implements Initialisable
{
	Logger log = LoggerFactory.getLogger(Magento.class);

    private String username;
    private String password;
    private String address;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    protected Mage_Api_Model_Server_V2_HandlerPortType getPort() throws Exception {
    	MagentoServiceLocator serviceLocator = new MagentoServiceLocator();
    	serviceLocator.setMage_Api_Model_Server_V2_HandlerPortEndpointAddress(this.getAddress());    	
    	Mage_Api_Model_Server_V2_HandlerPortType port = serviceLocator.getMage_Api_Model_Server_V2_HandlerPort();
    	return port;
    }
    /**
     * Need to authenticate before every call
     * @return Magento session ID
     * @throws Exception
     */
    protected String login() throws Exception
    { 	
    	String sessionId = this.getPort().login(getUsername(), getPassword());
    	return sessionId;
    }

	public void initialise() throws InitialisationException {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Returns list of Magento sales orders
	 * @param filters optional list of filters
	 * @return list of sales orders
	 * @throws Exception
	 */
	public SalesOrderEntity[] salesOrdersList(Filters filters) throws Exception {
		//log.debug("Filters is " + filters);
		ComplexFilter first = filters.getComplex_filter()[0];
		//log.debug("Filter[0] is " + filters.getFilter()[0].getKey() + "=" + filters.getFilter()[0].getValue());
		log.debug("ComplextFilter is " + first.getKey() + " " + first.getValue().getKey() + " " + first.getValue().getValue());
		
		//AssociativeEntity first = filters.getFilter()[0];
		//log.debug("Filter[0] is " + first.getKey() + "=" + first.getValue());
		
		String sessionId = this.login();
		//log.debug(">>>>> Session ID is " + sessionId);
		return this.getPort().salesOrderList(sessionId, filters);
	}
	
	/**
	 * Retrieves order information
	 * @param Order ID
	 * @return sales order information
	 * @throws Exception
	 */
	public SalesOrderEntity salesOrderInfo(String orderIncrementId) throws Exception {
		String sessionId = this.login();
		return this.getPort().salesOrderInfo(sessionId, orderIncrementId);
	}
	
	/**
	 * Puts order on hold
	 * @param order id
	 * @return 
	 * @throws Exception
	 */
	public int salesOrderHold(String orderIncrementId) throws Exception {
		String sessionId = this.login();
		return this.getPort().salesOrderHold(sessionId, orderIncrementId);
	}

	/**
	 * Releases order
	 * @param order id
	 * @return 
	 * @throws Exception
	 */
	public int salesOrderUnhold(String orderIncrementId) throws Exception {
		String sessionId = this.login();
		return this.getPort().salesOrderUnhold(sessionId, orderIncrementId);
	}

	/**
	 * Cancels order
	 * @param order id
	 * @return sales order information
	 * @throws Exception
	 */
	public int salesOrderCancel(String orderIncrementId) throws Exception {
		String sessionId = this.login();
		return this.getPort().salesOrderCancel(sessionId, orderIncrementId);
	}

	/**
	 * @param orderIncrementId
	 * @param status
	 * @param comment
	 * @param notify
	 * @return
	 * @throws Exception
	 */
	public int salesOrderComment(String orderIncrementId, String status, String comment, String notify) throws Exception {
		String sessionId = this.login();
		return this.getPort().salesOrderAddComment(sessionId, orderIncrementId, status, comment, notify);
	}

	/**
	 * Returns list of Magento sales order shipments
	 * @param filters optional list of filters
	 * @return list of sales order shipments
	 * @throws Exception
	 */
	public SalesOrderShipmentEntity[] salesOrderShipmentsList(Filters filters) throws Exception {
		log.debug("Filters is " + filters);
		ComplexFilter first = filters.getComplex_filter()[0];
		//log.debug("Filter[0] is " + filters.getFilter()[0].getKey() + "=" + filters.getFilter()[0].getValue());
		log.debug("ComplextFilter is " + first.getKey() + " " + first.getValue().getKey() + " " + first.getValue().getValue());
		
		//AssociativeEntity first = filters.getFilter()[0];
		//log.debug("Filter[0] is " + first.getKey() + "=" + first.getValue());
		
		String sessionId = this.login();
		//log.debug(">>>>> Session ID is " + sessionId);
		return this.getPort().salesOrderShipmentList(sessionId, filters);
	}
	
	/**
	 * Retrieves order information
	 * @param Order ID
	 * @return sales order information
	 * @throws Exception
	 */
	public SalesOrderShipmentEntity salesOrderShipmentInfo(String shipmentIncrementId) throws Exception {
		String sessionId = this.login();
		return this.getPort().salesOrderShipmentInfo(sessionId, shipmentIncrementId);
	}
	
	/**
	 * @param shipmentIncrementId
	 * @param comment
	 * @param notify
	 * @param includeInEmail
	 * @return
	 * @throws Exception
	 */
	public int salesOrderShipmentComment(String shipmentIncrementId, String comment, String notify, String includeInEmail) throws Exception {
		String sessionId = this.login();
		return this.getPort().salesOrderShipmentAddComment(sessionId, shipmentIncrementId, comment, notify, includeInEmail);
	}
	
	/**
	 * Returns list of carriers for the order
	 * @param order id
	 * @return list of carriers
	 * @throws Exception
	 */
	public AssociativeEntity[] salesOrderShipmentGetCarriers(String orderIncrementId) throws Exception {
		String sessionId = this.login();
		return this.getPort().salesOrderShipmentGetCarriers(sessionId, orderIncrementId);
	}

	/**
	 * @param shipmentIncrementId
	 * @param carrier
	 * @param title
	 * @param trackNumber
	 * @return track ID
	 * @throws Exception
	 */
	public int salesOrderShipmentAddTrack(String shipmentIncrementId, String carrier, String title, String trackNumber) throws Exception {
		String sessionId = this.login();
		return this.getPort().salesOrderShipmentAddTrack(sessionId, shipmentIncrementId, carrier, title, trackNumber);
	}

	/**
	 * @param shipmentIncrementId
	 * @param trackId
	 * @return
	 * @throws Exception
	 */
	public int salesOrderShipmentRemoveTrack(String shipmentIncrementId, String trackId) throws Exception {
		String sessionId = this.login();
		return this.getPort().salesOrderShipmentRemoveTrack(sessionId, shipmentIncrementId, trackId);
	}
	
	public String salesOrderShipmentCreate(String orderIncrementId, OrderItemIdQty[] itemsQty, String comment, String email, String includeInEmail) throws Exception {
		String sessionId = this.login();
		return this.getPort().salesOrderShipmentCreate(sessionId, orderIncrementId, itemsQty, comment, ("true".equals(email) ? 1 : 0), ("true".equals(includeInEmail) ? 1 : 0));
	}
}
