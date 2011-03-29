/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.magento.api;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.axis.AxisFault;

public class AxisFaultExceptionHandler
{
    @SuppressWarnings("unchecked")
    public <T> T handleAxisFaults(Class<T> receptorClass, T receptor)
    {
        return (T) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{receptorClass},
            new InvocationHandler()
            {
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
                {
                    try
                    {
                        return method.invoke(proxy, args);
                    }
                    catch (InvocationTargetException e)
                    {
                        if (e.getCause() instanceof AxisFault)
                        {
                            AxisFault fault = (AxisFault) e.getCause();
                            throw new MagentoException(fault.getFaultString(), e);
                        }
                        throw e;
                    }
                }
            });
    }

    public static final class MagentoException extends RuntimeException
    {
        private static final long serialVersionUID = -5626573459450043144L;

        private MagentoException(String message, Throwable cause)
        {
            super(message, cause);
        }

    }
}
