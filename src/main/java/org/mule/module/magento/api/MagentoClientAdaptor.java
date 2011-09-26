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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.axis.AxisFault;
import org.apache.commons.lang.Validate;
import org.mule.module.magento.MagentoCloudConnector;
import org.mule.module.magento.api.util.MagentoMap;
import org.mule.util.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An utility class for creating proxies that handle {@link AxisFault}s by converting them into {@link MagentoException}
 * s, and return Maps of objects instead of magento objects
 */
public final class MagentoClientAdaptor
{
    private static Logger log = LoggerFactory.getLogger(MagentoCloudConnector.class);

    private MagentoClientAdaptor()
    {
    }

    @SuppressWarnings("unchecked")
    public static <T> T adapt(final Class<T> receptorClass, final T receptor)
    {
        Validate.isTrue(receptorClass.isInterface());
        return (T) Proxy.newProxyInstance(MagentoClientAdaptor.class.getClassLoader(),
            new Class[]{receptorClass}, new InvocationHandler()
            {
                public Object invoke(final Object proxy, final Method method, final Object[] args)
                    throws Throwable
                {
                    try
                    {
                        if (log.isDebugEnabled())
                        {
                            log.debug("Entering {} with args {}", method.getName(), args);
                        }
                        final Object ret = new MagentoMap(new Holder(method.invoke(receptor, args))).get("value");
                        if (log.isDebugEnabled())
                        {
                            log.debug("Returning from {} with value {}", method.getName(), ret);
                        }
                        return ret;
                    }
                    catch (final InvocationTargetException e)
                    {
                        if (e.getCause() instanceof AxisFault)
                        {
                            if (log.isWarnEnabled())
                            {
                                log.warn("An exception was thrown while invoking {}: {}", method.getName(),
                                    e.getCause());
                            }
                            throw toMagentoException((AxisFault) e.getCause());
                        }
                        throw e;
                    }
                }

            });
    }

    private static MagentoException toMagentoException(final AxisFault fault)
    {
        final String faultCode = fault.getFaultCode().toString();

        if (NumberUtils.isNumber(faultCode))
        {
            return new MagentoException(Integer.parseInt(faultCode), fault.getFaultString(), fault);
        }
        else
        {
            return new MagentoException(MagentoException.UNKNOWN_ERROR, fault.getFaultString(), fault);
        }
    }

    public static class Holder
    {
        private final Object value;

        public Holder(final Object value)
        {
            this.value = value;
        }

        public Object getValue()
        {
            return value;
        }
    }

}
