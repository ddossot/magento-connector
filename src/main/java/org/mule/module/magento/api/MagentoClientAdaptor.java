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

import org.mule.module.magento.api.util.MagentoMap;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.axis.AxisFault;
import org.apache.commons.lang.Validate;

/**
 * An utility class for creating proxies that handle {@link AxisFault}s by converting
 * them into {@link MagentoException}s, and return Maps of objects instead of magento
 * objects
 */
public final class MagentoClientAdaptor
{

    private MagentoClientAdaptor()
    {
    }

    @SuppressWarnings("unchecked")
    public static <T> T adapt(Class<T> receptorClass, final T receptor)
    {
        Validate.isTrue(receptorClass.isInterface());
        return (T) Proxy.newProxyInstance(MagentoClientAdaptor.class.getClassLoader(),
            new Class[]{receptorClass}, new InvocationHandler()
            {
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
                {
                    try
                    {
                        return new MagentoMap(new Holder(method.invoke(receptor, args))).get("value");
                    }
                    catch (InvocationTargetException e)
                    {
                        if (e.getCause() instanceof AxisFault)
                        {
                            throw toMagentoException((AxisFault) e.getCause());
                        }
                        throw e;
                    }
                }

            });
    }

    private static MagentoException toMagentoException(AxisFault fault)
    {
        return new MagentoException( //
            Integer.parseInt(fault.getFaultCode().toString()), fault.getFaultString(), fault);
    }

    public static class Holder
    {
        private Object value;

        public Holder(Object value)
        {
            this.value = value;
        }

        public Object getValue()
        {
            return value;
        }
    }

}
