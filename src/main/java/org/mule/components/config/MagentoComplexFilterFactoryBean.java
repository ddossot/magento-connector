package org.mule.components.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Magento.AssociativeEntity;
import Magento.ComplexFilter;

public class MagentoComplexFilterFactoryBean extends org.springframework.beans.factory.config.AbstractFactoryBean<ComplexFilter> {

	Logger log = LoggerFactory.getLogger(MagentoComplexFilterFactoryBean.class);
	
	private AssociativeEntity filter;
	private String key;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}


	@Override
	public Class<ComplexFilter> getObjectType() {
		return ComplexFilter.class;
	}

	public void setFilter(AssociativeEntity filter) {
		this.filter = filter;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected ComplexFilter createInstance() {
		if (this.filter == null) {
			throw new IllegalArgumentException("ComplexFilter children list must include one filter");
		}

		ComplexFilter result = new ComplexFilter();

		result.setKey(this.key);
		result.setValue(this.filter);
		
		return result;
	}

    public boolean isSingleton()
    {
        return false;
    }
}
