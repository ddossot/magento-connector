/**
 * Mule Magento Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.magento.api.inventory;

import org.mule.module.magento.api.AbstractMagentoClient;
import org.mule.module.magento.api.AxisPortProvider;
import org.mule.module.magento.api.MagentoMap;
import org.mule.module.magento.api.internal.CustomerAddressEntityCreate;
import org.mule.module.magento.api.internal.CustomerCustomerEntity;
import org.mule.module.magento.api.internal.CustomerCustomerEntityToCreate;
import org.mule.module.magento.api.internal.Filters;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.Validate;

import static org.mule.module.magento.api.MagentoMap.*;
import static org.mule.module.magento.filters.FiltersParser.*;

public class AxisMagentoCustomerClient extends AbstractMagentoClient implements MagentoCustomerClient
{
    // TODO creation operation return ids?

    public AxisMagentoCustomerClient(AxisPortProvider provider)
    {
        super(provider);
    }

    /**
     * Creates a customer with th given attributes
     * 
     * @param attributes the attributes of the new customer
     * @return the new customer id
     * @throws RemoteException
     */
    public int createCustomer(@NotNull Map<String, Object> attributes) throws RemoteException
    {
        return getPort().customerCustomerCreate(getSessionId(),
            MagentoMap.fromMap(CustomerCustomerEntityToCreate.class, attributes));
    }

    /**
     * Deletes a customer given its id
     * 
     * @param customerId
     * @return if the customer has effectively been deleted TODO verify this
     *         postcondition
     * @throws RemoteException
     */
    public boolean deleteCustomer(int customerId) throws RemoteException
    {
        return getPort().customerCustomerDelete(getSessionId(), customerId);
    }

    /**
     * Answers customer attributes for the given id. Only the selected attributes are
     * retrieved
     * 
     * @param customerId
     * @param attributeNames the attributes to retrieve. Not empty
     * @return the attributes map
     * @throws RemoteException
     */
    @NotNull
    public Map<String, Object> getCustomer(int customerId, @NotNull Collection<String> attributeNames)
        throws RemoteException
    {
        Validate.notEmpty(attributeNames);
        return toMap(getPort().customerCustomerInfo(getSessionId(), customerId,
            toArray(attributeNames, String.class)));
    }

    /**
     * Answers a list of customer attributes for the given filter expression.
     * 
     * @param filters a filtering expression.
     * @return the attributes map
     * @throws RemoteException
     */
    @NotNull
    public List<Map<String, Object>> listCustomers(String filters) throws RemoteException
    {
        return toMap(getPort().customerCustomerList(getSessionId(), parse(filters)));
    }

    /**
     * Updates the given customer attributes, for the given customer id. Password can
     * not be updated using this method
     * 
     * @param customerId
     * @param attributes the attributes map
     * @return if the customer attributes have been effectively updated
     * @throws RemoteException
     */
    public boolean updateCustomer(int customerId, @NotNull Map<String, Object> attributes)
        throws RemoteException
    {
        Validate.notNull(attributes);
        return getPort().customerCustomerUpdate(getSessionId(), customerId,
            fromMap(CustomerCustomerEntityToCreate.class, attributes));
    }

    /**
     * Creates a new address for the given customer using the given address
     * attributes
     * 
     * @param customerId
     * @param attributes
     * @return a new customer address id
     * @throws RemoteException
     */
    public int createCusomerAddress(int customerId, Map<String, Object> attributes) throws RemoteException
    {
        return getPort().customerAddressCreate(getSessionId(), customerId,
            fromMap(CustomerAddressEntityCreate.class, attributes));
    }

    public boolean deleteCustomerAddress(int addressId) throws RemoteException
    {
        return getPort().customerAddressDelete(getSessionId(), addressId);
    }

    public Map<String, Object> getCustomerAddress(int addressId) throws RemoteException
    {
        return toMap(getPort().customerAddressInfo(getSessionId(), addressId));
    }

//  TODO  public boolean listCustomerAddresses(int customerId) throws RemoteException
//    {
//        return getPort().customerAddressList(getSessionId(), customerId);
//    }
//
//    public void updateCustomerAddress(int addressId, CustomerAddressEntityCreate addressData)
//        throws RemoteException
//    {
//        return getPort().customerAddressUpdate(getSessionId(), addressId, addressData);
//    }
//
//    public void listCustomerGroups() throws RemoteException
//    {
//        return getPort().customerGroupList(getSessionId());
//    }
}
