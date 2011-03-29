/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.magento.api;

import Magento.Mage_Api_Model_Server_V2_HandlerPortType;
import Magento.MagentoServiceLocator;

public class DefaultPortProvider implements PortProvider
{
    private final String username;
    private final String password;
    private final String address;

    public DefaultPortProvider(String username, String password, String address)
    {
        this.username = username;
        this.password = password;
        this.address = address;
    }

    public Mage_Api_Model_Server_V2_HandlerPortType getPort() throws Exception
    {
        MagentoServiceLocator serviceLocator = new MagentoServiceLocator();
        serviceLocator.setMage_Api_Model_Server_V2_HandlerPortEndpointAddress(address);
        return serviceLocator.getMage_Api_Model_Server_V2_HandlerPort();
    }

    /**
     * Need to authenticate before every call
     * 
     * @return Magento session ID
     * @throws Exception
     */
    public String getSessionId() throws Exception
    {
        return getPort().login(username, password);
    }
}
