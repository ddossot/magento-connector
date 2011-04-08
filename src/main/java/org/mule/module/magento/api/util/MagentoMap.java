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

import static org.mule.module.magento.api.util.MagentoClass.isMagentoArrayClass;
import static org.mule.module.magento.api.util.MagentoClass.isMagentoClass;

import org.mule.module.magento.api.internal.AssociativeEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.Validate;

/**
 * A delayed map that converts a magento object into a map. The logic is the
 * following: Null attributes are converted into null, arrays of magento object
 * attributes are converted into collections of magento maps, magento object
 * attributes are converted into magento maps, and any other attribute is left
 * unchanged.
 */
public class MagentoMap extends BeanMap
{

    private static final Transformer TO_MAP = new ToMapTransformer();

    public MagentoMap(Object bean)
    {
        super(bean);
    }

    @Override
    public Object get(Object name)
    {
        Object value = super.get(name);
        if (value == null)
        {
            return null;
        }
        return transformValue(value, value.getClass());
    }

    private Object transformValue(Object value, Class<?> valueClazz)
    {
        if (isMagentoArrayClass(valueClazz))
        {
            if (valueClazz.getComponentType() == AssociativeEntity.class)
            {
                return toMap((AssociativeEntity[]) value);
            }
            return toMap((Object[]) value);
        }
        if (isMagentoClass(valueClazz))
        {
            return toMap(value);
        }
        return value;
    }
    
    @SuppressWarnings("unchecked")
    public String toString()
    {
        /*This is rough copy paste of the original toString implementation of 
         * AbstractMap, that BeanMap overrides*/
        Iterator<java.util.Map.Entry<String, Object>> i = entrySet().iterator();
        if (!i.hasNext())
        {
            return "{}";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (;;)
        {
            java.util.Map.Entry<String, Object> e = i.next();
            String key = e.getKey();
            Object value = e.getValue();
            sb.append(key);
            sb.append('=');
            sb.append(value == this ? "(this Map)" : value);
            if (!i.hasNext())
            {
                return sb.append('}').toString();
            }
            sb.append(", ");
        }
    }
    
    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(Object magentoObject)
    {
        Validate.isTrue(isMagentoClass(magentoObject.getClass()));
        return new MagentoMap(magentoObject);
    }
    
    public static Map<String, Object> toMap(AssociativeEntity[] associativeEntities)
    {
        HashMap<String, Object> map = new HashMap<String, Object>(associativeEntities.length);
        for (AssociativeEntity association : associativeEntities)
        {
            map.put(association.getKey(), association.getValue());
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> toMap(Object[] magentoObjects)
    {
        return (List<Map<String, Object>>) CollectionUtils.collect(Arrays.asList(magentoObjects), TO_MAP);
    }

    private static final class ToMapTransformer implements Transformer
    {
        public Object transform(Object input)
        {
            return toMap(input);
        }
    }

}
