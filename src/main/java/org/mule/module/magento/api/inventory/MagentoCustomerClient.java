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

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

public interface MagentoCustomerClient<AttributesType, AttributesCollectionType, ExceptionType extends Exception>
{

    /**
     * Creates a customer with th given attributes
     * 
     * @param attributes the attributes of the new customer
     * @return the new customer id
     * @throws ExceptionType
     */
    int createCustomer(@NotNull Map<String, Object> attributes) throws ExceptionType;

    /**
     * Deletes a customer given its id
     * 
     * @param customerId
     * @throws ExceptionType
     */
    void deleteCustomer(int customerId) throws ExceptionType;

    /**
     * Answers customer attributes for the given id. Only the selected attributes are
     * retrieved
     * 
     * @param customerId
     * @param attributeNames the attributes to retrieve. Not empty
     * @return the attributes map
     * @throws ExceptionType
     */
    @NotNull
    AttributesType getCustomer(int customerId, @NotNull List<String> attributeNames) throws ExceptionType;

    /**
     * Answers a list of customer attributes for the given filter expression.
     * 
     * @param filters a filtering expression.
     * @return the attributes map
     * @throws ExceptionType
     */
    @NotNull
    AttributesCollectionType listCustomers(String filters) throws ExceptionType;

    /**
     * Updates the given customer attributes, for the given customer id. Password can
     * not be updated using this method
     * 
     * @param customerId
     * @param attributes the attributes map
     * @throws ExceptionType
     */
    void updateCustomer(int customerId, @NotNull Map<String, Object> attributes) throws ExceptionType;

    /**
     * Creates a new address for the given customer using the given address
     * attributes
     * 
     * @param customerId
     * @param attributes
     * @return a new customer address id
     * @throws ExceptionType
     */
    int createCustomerAddress(int customerId, @NotNull Map<String, Object> attributes) throws ExceptionType;

    void deleteCustomerAddress(int addressId) throws ExceptionType;

    AttributesType getCustomerAddress(int addressId) throws ExceptionType;

    AttributesCollectionType listCustomerAddresses(int customerId) throws ExceptionType;

    void updateCustomerAddress(int addressId, @NotNull Map<String, Object> addressData) throws ExceptionType;

    AttributesCollectionType listCustomerGroups() throws ExceptionType;

}
