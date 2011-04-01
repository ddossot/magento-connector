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

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.rmi.RemoteException;

import javax.xml.namespace.QName;

import org.apache.axis.AxisFault;
import org.junit.Test;
import org.w3c.dom.Element;

/**
 * Test for {@link MagentoClientAdaptor}
 */
public class MagentoClientAdaptorUnitTest
{
    private static final String MESSAGE = "Ups!";
    private FooBar handled = MagentoClientAdaptor.adapt(FooBar.class, new FooBarImp());

    @Test
    public void testHandleException() throws Exception
    {
        try
        {
            handled.bar();
            fail();
        }
        catch (MagentoException e)
        {
            assertEquals(101, e.getFaultCode());
            assertEquals(MESSAGE, e.getMessage());
            assertThat(e.getCause(), instanceOf(AxisFault.class));
        }
    }

    @Test
    public void testJustDelegate() throws Exception
    {
        assertEquals("foo", handled.foo());
    }

    public static interface FooBar
    {
        public String foo() throws RemoteException;

        public String bar() throws RemoteException;
    }

    public static class FooBarImp implements FooBar
    {

        public String foo() throws RemoteException
        {
            return "foo";
        }

        public String bar() throws RemoteException
        {
            throw new AxisFault(QName.valueOf("101"), MESSAGE, "The actor", new Element[]{});
        }

        public Class<?> getClientType()
        {
            return FooBar.class;
        }

    }

}
