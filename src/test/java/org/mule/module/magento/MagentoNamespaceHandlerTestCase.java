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

import org.mule.construct.SimpleFlowConstruct;
import org.mule.tck.FunctionalTestCase;

/**
 * Test for the xml mapping of the {@link MagentoCloudConnector}
 */
public class MagentoNamespaceHandlerTestCase extends FunctionalTestCase
{
    public void testNamespaceConfig() throws Exception
    {
        assertNotNull(lookupFlowConstruct("MagentoOrderShipmentInfo"));
    }

    public void testNamespaceConfig2() throws Exception
    {
        assertNotNull(lookupFlowConstruct("MagentoListOrdersInvoices"));
    }

    @Override
    protected String getConfigResources()
    {
        return "magento-namespace-config.xml";
    }

    private SimpleFlowConstruct lookupFlowConstruct(String name)
    {
        return (SimpleFlowConstruct) muleContext.getRegistry().lookupFlowConstruct(name);
    }

}
