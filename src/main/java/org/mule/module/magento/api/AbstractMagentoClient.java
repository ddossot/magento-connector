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

import static org.apache.commons.lang.BooleanUtils.toIntegerObject;

import org.mule.module.magento.api.internal.Mage_Api_Model_Server_V2_HandlerPortType;

import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.util.Collection;

import org.apache.commons.lang.BooleanUtils;

/**
 * Base class for all Magento clients. Magento clients follow the following
 * conventions:
 * <ul>
 * <li>They are parameterized by the exception type they throw. For example, axis
 * clients should be parameterized to throw {@link RemoteException}s, while
 * MagentoClient's wrapers will throw {@link MagentoException}s</li>
 * <li>Create and update operations take the target's object attributes in a Map with
 * String keys and Object values. TODO actual value type depend on the conversion
 * strategy used by the client</li>
 * <li>Retrieve operations return object's attributes in a Map similar to the
 * previously described</li>
 * </ul>
 */
public abstract class AbstractMagentoClient
{
    protected final AxisPortProvider provider;

    public AbstractMagentoClient(AxisPortProvider provider)
    {
        this.provider = provider;
    }

    protected final String getSessionId() throws RemoteException
    {
        return provider.getSessionId();
    }

    protected final Mage_Api_Model_Server_V2_HandlerPortType getPort() throws RemoteException
    {
        return provider.getPort();
    }

    @SuppressWarnings("unchecked")
    protected static <T> T[] toArray(Collection<T> collection, Class<T> clazz)
    {
        return collection.toArray((T[]) Array.newInstance(clazz, collection.size()));
    }

    protected static String toIntegerString(boolean value)
    {
        return toIntegerObject(value).toString();
    }

    protected static boolean fromIntegerString(String value)
    {
        return BooleanUtils.toBoolean(Integer.parseInt(value));
    }

}
