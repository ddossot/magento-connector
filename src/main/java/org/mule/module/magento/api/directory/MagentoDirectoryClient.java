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

import javax.validation.constraints.NotNull;

/**
 * Facade for the Magento Directory API
 */
public interface MagentoDirectoryClient<AttributesCollectionType, ExceptionType extends Exception>
{
    /**
     * Answers the list of countries
     * 
     * @return a collection of countries attributes
     */
    AttributesCollectionType listDirectoryCountries() throws ExceptionType;

    /**
     * Answers a list of regions for the given county
     * 
     * @param countryId the country code, in ISO2 or ISO3 format
     * @return the collection of regions attributes
     */
    AttributesCollectionType listDirectoryRegions(@NotNull String countryId) throws ExceptionType;
}
