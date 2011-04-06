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
	 * Answers a {@link ProductIdentifier} for the three given product id types.
	 * One and only of those parameters must be non null.
	 * 
	 * @param productSku
	 * @param productId
	 * @param idOrSku
	 * @return a new {@link ProductIdentifier}
	 */
	public static ProductIdentifier from(String productSku, Integer productId,
			String idOrSku)
	{
		if (productSku != null)
		{
			validateNull(idOrSku, productId);
			return new ProductIdentifier.Sku(productSku);
		}
		if (productId != null)
		{
			validateNull(idOrSku, productSku);
			return new ProductIdentifier.Id(productId);
		}
		Validate.notNull(idOrSku, "No product identifier was specifier");
		return new ProductIdentifier.IdOrSku(idOrSku);
	}

	private static void validateNull(Object o1, Object o2)
	{
		Validate.isTrue(o1 == null && o2 == null,
				"More than one product identifier specified");
	}
}
