/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mulesoft.demo.magento;

import org.mule.api.MuleEvent;
import org.mule.construct.SimpleFlowConstruct;
import org.mule.tck.FunctionalTestCase;


public class MagentoFunctionalTestDriver extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "mule-config.xml";
    }
    
    /**
     * Creates some products for this test. Run this test only 
     * once
     */
    public void ignoretestSetupFlow() throws Exception
    {
        /*
        Create also the following elements in a mongo db:  
        db.priceUpdates.insert({ sku: "A04569", price: 8963})
        db.priceUpdates.insert({ sku: "FF0AS489", price: 150 })
        db.priceUpdates.insert({ sku: "1029H", price: 9863 })
        */
        lookupFlowConstruct("SetupFlow").process(getTestEvent(""));
    }

    public void testUpdateFlow() throws Exception
    {
        final MuleEvent event = getTestEvent("");
        final SimpleFlowConstruct flow = lookupFlowConstruct("MainFlow");
        final MuleEvent responseEvent = flow.process(event);
        System.out.println(responseEvent);
    }

    private SimpleFlowConstruct lookupFlowConstruct(final String name)
    {
        return (SimpleFlowConstruct) muleContext.getRegistry().lookupFlowConstruct(name);
    }

}
