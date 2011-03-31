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

import static org.mule.module.magento.api.MagentoMap.*;

import org.mule.module.magento.api.AbstractMagentoClient;
import org.mule.module.magento.api.AxisPortProvider;
import org.mule.module.magento.api.internal.CatalogInventoryStockItemEntity;
import org.mule.module.magento.api.internal.CatalogInventoryStockItemUpdateEntity;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.Validate;

public class AxisMagentoInventoryClient extends AbstractMagentoClient implements MagentoInventoryClient
{
    public AxisMagentoInventoryClient(AxisPortProvider provider)
    {
        super(provider);
    }

    /**
     * Retrieve stock data by product ids
     * 
     * @param productIds not empty
     * @return a list of stock items properties
     * @throws RemoteException
     */
    public List<Map<String, Object>> listStockItems(@NotNull List<String> productIds) throws RemoteException
    {
        Validate.notNull(productIds);
        Validate.notEmpty(productIds);
        return toMap(getPort().catalogInventoryStockItemList(getSessionId(),
            toArray(productIds, String.class)));
    }

    /**
     * Update product stock data
     * 
     * @param productId
     * @param data
     * @return TODO
     * @throws RemoteException
     */
    public Map<String, Object> updateStockItem(@NotNull String productId,
                                               @NotNull Map<String, Object> attributes)
        throws RemoteException
    {
        Validate.notNull(productId);
        // //FIXME not clear if it updates a whole item or the full list
        return toMap(getPort().catalogInventoryStockItemUpdate(getSessionId(), productId,
            fromMap(CatalogInventoryStockItemUpdateEntity.class, attributes)));
    }

}
