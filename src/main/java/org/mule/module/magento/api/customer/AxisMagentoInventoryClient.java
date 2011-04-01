/**
 * Mule Magento Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.magento.api.customer;

import static org.mule.module.magento.api.util.MagentoObject.fromMap;

import org.mule.module.magento.api.AbstractMagentoClient;
import org.mule.module.magento.api.AxisPortProvider;
import org.mule.module.magento.api.internal.CatalogInventoryStockItemUpdateEntity;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.Validate;

public class AxisMagentoInventoryClient extends AbstractMagentoClient
    implements MagentoInventoryClient<Object[], RemoteException>
{
    public AxisMagentoInventoryClient(AxisPortProvider provider)
    {
        super(provider);
    }

    public Object[] listStockItems(@NotNull List<String> productIdsOrSkus) throws RemoteException
    {
        Validate.notNull(productIdsOrSkus);
        Validate.notEmpty(productIdsOrSkus);
        return getPort().catalogInventoryStockItemList(getSessionId(),
            toArray(productIdsOrSkus, String.class));
    }

    public void updateStockItem(@NotNull String productIdOrSkus, @NotNull Map<String, Object> attributes)
        throws RemoteException
    {
        Validate.notNull(productIdOrSkus);
        Validate.notNull(attributes);
        getPort().catalogInventoryStockItemUpdate(getSessionId(), productIdOrSkus,
            fromMap(CatalogInventoryStockItemUpdateEntity.class, attributes));
    }

}
