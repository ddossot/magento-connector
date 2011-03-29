
package org.mule.module.magento;

import org.mule.construct.SimpleFlowConstruct;
import org.mule.tck.FunctionalTestCase;

public class MagentoNamespaceHandlerTestCase extends FunctionalTestCase
{
    public void testNamespaceConfig() throws Exception
    {
        assertNotNull(lookupFlowConstruct("MagentoOrderShipmentInfo"));
    }

    public void testNamespaceConfig2() throws Exception
    {
        assertNotNull(lookupFlowConstruct("MagentoOrderInvoicesList"));
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
