/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.components.config;

import java.util.ArrayList;
import java.util.List;

import org.mule.components.Magento;

public class MagentoSalesOrderShipmentsListFactoryBean
        extends MagentoAbstractFilteredMethodFactoryBean
{

    public MagentoFilteredMethodInvokerMessageProcessor getObject() throws Exception
    { 
    
    	MagentoFilteredMethodInvokerMessageProcessor invokerMessageProcessor = new MagentoFilteredMethodInvokerMessageProcessor();
        List<Object> args = new ArrayList<Object>();
        args.add(filters);
        invokerMessageProcessor.setArguments(args);
        invokerMessageProcessor.setMethodName("salesOrderShipmentsList");
        
        if (config != null) {
        	invokerMessageProcessor.setObject(config);
        } else if (appContext.getBean(Magento.class) != null) {
        	invokerMessageProcessor.setObject(appContext.getBean(Magento.class));
        }
         
        return invokerMessageProcessor;
    }
}