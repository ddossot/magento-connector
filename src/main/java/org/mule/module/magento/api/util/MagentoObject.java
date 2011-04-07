/**
 * Mule Magento Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.magento.api.util;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.LazyDynaMap;
import org.apache.commons.beanutils.WrapDynaBean;
import org.apache.commons.beanutils.WrapDynaClass;
import org.apache.commons.lang.UnhandledException;

/**
 * An utility class for converting a map of string and objects into a magento object,
 * following the same conversion rules that in {@link MagentoMap}
 */
public final class MagentoObject
{
    private static BeanUtilsBean beanUtils;
    private static MapToBeanConverter beanConverter = new MapToBeanConverter();
    private static ListToBeanArrayConverter listConverter = new ListToBeanArrayConverter();
    static
    {
        beanUtils = new BeanUtilsBean(new ConvertUtilsBean()
        {
            @SuppressWarnings("unchecked")
            @Override
            public Converter lookup(Class type)
            {
                if (MagentoClass.isMagentoArrayClass(type))
                {
                    return listConverter;
                }
                if (MagentoClass.isMagentoClass(type))
                {
                    return beanConverter;
                }
                return super.lookup(type);
            }

        });
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromMap(Class<T> clazz, Map<String, Object> map)
    {
        return (T) beanConverter.convert(clazz, map);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] fromMap(Class<T> clazz, List<Map<String, Object>> list)
    {
        return (T[]) beanConverter.convert(Array.newInstance(clazz, 0).getClass(), list);
    }

    /**
     * {@link Converter} that transforms a list of maps into an array of magento
     * object
     */
    private static class ListToBeanArrayConverter implements Converter
    {
        @SuppressWarnings("unchecked")
        public Object convert(Class type, Object value)
        {
            try
            {
                if (value == null)
                {
                    return null;
                }
                List<Map<String, Object>> list = (List<Map<String, Object>>) value;
                Object[] array = (Object[]) Array.newInstance(type.getComponentType(), list.size());
                Iterator<Map<String, Object>> iter = list.iterator();
                for (int i = 0; i < array.length; i++)
                {
                    array[i] = fromMap(type.getComponentType(), iter.next());
                }
                return array;

            }
            catch (Exception e)
            {
                throw new UnhandledException(e);
            }
        }
    }

    private static class MapToBeanConverter implements Converter
    {
        @SuppressWarnings("unchecked")
        public Object convert(Class type, Object value)
        {
            try
            {
                if (value == null)
                {
                    return null;
                }
                LazyDynaMap dynaMap = new LazyDynaMap((Map<String, Object>) value);
                WrapDynaBean bean = (WrapDynaBean) WrapDynaClass.createDynaClass(type).newInstance();
                beanUtils.copyProperties(bean, dynaMap);
                beanUtils.copyProperty(bean, "", value);
                return bean.getInstance();
            }
            catch (Exception e)
            {
                throw new UnhandledException(e);
            }
        }
    }
}
