/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.components.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import Magento.Filters;

public abstract class MagentoAbstractFilteredMethodFactoryBean
        implements FactoryBean<MagentoFilteredMethodInvokerMessageProcessor>, ApplicationContextAware
{
	Logger log = LoggerFactory.getLogger(MagentoAbstractFilteredMethodFactoryBean.class);
	
    protected Filters filters;
    protected Object config;

    protected ApplicationContext appContext;

    public abstract MagentoFilteredMethodInvokerMessageProcessor getObject() throws Exception;
    
    public Class<MagentoFilteredMethodInvokerMessageProcessor> getObjectType()
    {
        return MagentoFilteredMethodInvokerMessageProcessor.class;
    }

    public boolean isSingleton()
    {
        return false;
    }

    public void setFilters(Filters filters)
    {
        this.filters = filters;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        this.appContext = applicationContext;
    }

    public void setConfig(Object config)
    {
        this.config = config;
    }

}
