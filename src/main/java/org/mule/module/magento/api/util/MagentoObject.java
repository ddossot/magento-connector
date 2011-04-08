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

import org.mule.module.magento.api.internal.AssociativeEntity;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
@SuppressWarnings("unchecked")
public final class MagentoObject
{
    private static BeanUtilsBean beanUtils;
    private static MapToBeanConverter beanConverter = new MapToBeanConverter();
    private static MapToAssociativeArray associativeConverter = new MapToAssociativeArray();
    private static ListToBeanArrayConverter listConverter = new ListToBeanArrayConverter();
    static
    {
        
        beanUtils = new BeanUtilsBean(new ConvertUtilsBean()
        {
            @Override
            public Converter lookup(Class type)
            {
                if (MagentoClass.isMagentoArrayClass(type))
                {
                    if (type.getComponentType() == AssociativeEntity.class)
                    {
                        return associativeConverter;
                    }
                    return listConverter;
                }
                if (MagentoClass.isMagentoClass(type))
                {
                    return beanConverter;
                }
                return super.lookup(type);
            }
        })
        {
            @Override
            protected Object convert(Object value, Class targetType)
            {
                if (!targetType.isPrimitive() && value == null)
                {
                    return null;
                }
                return super.convert(value, targetType);
            }
        };
    }

    public static <T> T fromMap(Class<T> clazz, Map<String, Object> map)
    {
        return (T) beanConverter.convert(clazz, map);
    }
    
    public static AssociativeEntity[] fromMap(Map<String, Object> map)
    {
        return associativeConverter.convert(null, map);
    }

    public static <T> T[] fromMap(Class<T> clazz, List<Map<String, Object>> list)
    {
        return (T[]) listConverter.convert(Array.newInstance(clazz, 0).getClass(), list);
    }

    /**
     * {@link Converter} that transforms a list of maps into an array of magento
     * object
     */
    private static class ListToBeanArrayConverter implements Converter
    {
        public Object convert(Class type, Object value)
        {
            try
            {
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
    
    private static class MapToAssociativeArray implements Converter
    {
        public AssociativeEntity[] convert(Class type, Object value)
        {
            try
            {
                Map<String, String> map = (Map<String,String>)value;
                AssociativeEntity[] array = new AssociativeEntity[map.size()];
                int i = 0;
                for(Entry<String,String> entry : map.entrySet()) 
                {
                    array[i++] = new AssociativeEntity(entry.getKey(), entry.getValue());
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
        public Object convert(Class type, Object value)
        {
            try
            {
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
