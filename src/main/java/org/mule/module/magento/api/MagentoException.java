/**
 * Mule Magento Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.magento.api;

public final class MagentoException extends RuntimeException
{
    private static final long serialVersionUID = -5626573459450043144L;
    private final int faultCode;

    public static final int UNKNOWN_ERROR = -1;
    public static final int SHIPMENT_DOES_NOT_EXIST = 100;
    public static final int INVALID_FILTERS = 101;
    public static final int INVALID_DATA = 102;
    public static final int ORDER_DOES_NOT_EXIST = 103;
    public static final int TRACKING_DOES_NOT_EXIST = 104;
    public static final int TRACKING_NOT_DELETED = 105;

    MagentoException(final int faultCode, final String message, final Throwable cause)
    {
        super(message, cause);
        this.faultCode = faultCode;
    }

    public int getFaultCode()
    {
        return faultCode;
    }
}
