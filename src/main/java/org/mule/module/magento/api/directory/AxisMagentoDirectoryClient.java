/**
 * Mule Magento Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.magento.api.directory;

import org.mule.module.magento.api.AbstractMagentoClient;
import org.mule.module.magento.api.AxisPortProvider;

import java.rmi.RemoteException;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.Validate;

public class AxisMagentoDirectoryClient extends AbstractMagentoClient
    implements MagentoDirectoryClient<Object[], RemoteException>
{
    public AxisMagentoDirectoryClient(AxisPortProvider provider)
    {
        super(provider);
    }

    public Object[] listDirectoryCountries() throws RemoteException
    {
        return getPort().directoryCountryList(getSessionId());
    }

    public Object[] listDirectoryRegions(@NotNull String countryId) throws RemoteException
    {
        Validate.notNull(countryId);
        return getPort().directoryRegionList(getSessionId(), countryId);
    }

}
