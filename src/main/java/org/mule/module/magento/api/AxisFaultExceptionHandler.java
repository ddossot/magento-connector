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
import org.apache.commons.lang.Validate;

public final class AxisFaultExceptionHandler
{

    private AxisFaultExceptionHandler()
    {
    }

    @SuppressWarnings("unchecked")
    public static <T> T handleFaults(Class<T> receptorClass, final T receptor)
    {
        Validate.isTrue(receptorClass.isInterface());
        return (T) Proxy.newProxyInstance(AxisFaultExceptionHandler.class.getClassLoader(),
            new Class[]{receptorClass}, new InvocationHandler()
            {
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
                {
                    try
                    {
                        return method.invoke(receptor, args);
                    }
                    catch (InvocationTargetException e)
                    {
                        if (e.getCause() instanceof AxisFault)
                        {
                            throw toMagentoException(e, (AxisFault) e.getCause());
                        }
                        throw e;
                    }
                }
            });
    }

    private static MagentoException toMagentoException(InvocationTargetException e, AxisFault fault)
    {
        return new MagentoException( //
            Integer.parseInt(fault.getFaultCode().toString()), fault.getFaultString(), e);
    }

}
