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

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

public interface MagentoInventoryClient<AttributesCollectionType, ExceptionType extends Exception>
{

    /**
     * Retrieve stock data by product ids
     * 
     * @param productIdsOrSkus a not empty list of product ids or skus whose attributes to list
     * @return a list of stock items attributes
     * @throws RemoteException
     */
    AttributesCollectionType listStockItems(@NotNull List<String> productIdsOrSkus)
        throws RemoteException;

    /**
     * Update product stock data given its id or sku
     * 
     * @param productIdOrSku the product id or sku of the product to update
     * @param the attributes to update of the given product
     * @throws RemoteException
     */
    void updateStockItem(@NotNull String productIdOrSku, @NotNull Map<String, Object> attributes)
        throws RemoteException;

}
