/**
 * Mule Magento Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.magento.api.catalog.model;

import org.apache.commons.lang.Validate;

public class ProductIdentifiers
{
	/**
	 * Answers a {@link ProductIdentifier} for the given optional sku and id.
	 * One and only of those parameters must be non null.
	 * 
	 * @param productSku
	 * @param productId
	 * @return a new {@link ProductIdentifier}
	 */
	public static ProductIdentifier from(String productSku, Integer productId)
	{
		Validate.isTrue((productSku == null) != (productId == null),
				"Must specify one an only one product identifier");
		if (productSku != null)
		{
			return new ProductIdentifier.Sku(productSku);
		}
		return new ProductIdentifier.Id(productId);
	}
}
