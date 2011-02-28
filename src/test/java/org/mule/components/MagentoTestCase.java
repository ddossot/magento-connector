package org.mule.components;


import org.mule.tck.FunctionalTestCase;

public class MagentoTestCase extends FunctionalTestCase
{
    public void testNamespaceConfig() throws Exception
    {
        return;
    }

    @Override
    protected String getConfigResources()
    {
        return "magento-conf.xml";
    }
}
