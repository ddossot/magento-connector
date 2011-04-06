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

/**
 * An unique opaque identifier for a product
 * 
 * @author flbulgarelli
 */
public interface ProductIdentifier
{
	/**The opaque identifier*/
	String getIdentifierAsString();

	/**The identifier type  - soap methods 
	 * support just "sku", "id" or null*/
	String getIdentifierType();

	class Id implements ProductIdentifier
	{
		private final Integer id;

		public Id(Integer id)
		{
			this.id = id;
		}

		public String getIdentifierAsString()
		{
			return id.toString();
		}

		public String getIdentifierType()
		{
			return "id";
		}
	}

	class Sku implements ProductIdentifier
	{
		private final String sku;

		public Sku(String sku)
		{
			this.sku = sku;
		}

		public String getIdentifierAsString()
		{
			return sku;
		}

		public String getIdentifierType()
		{
			return "sku";
		}
	}
	
	class IdOrSku implements ProductIdentifier
	{
		private final String idOrSku;

		public IdOrSku(String sku)
		{
			this.idOrSku = sku;
		}

		public String getIdentifierAsString()
		{
			return idOrSku;
		}

		public String getIdentifierType()
		{
			return null;
		}
	}
}
