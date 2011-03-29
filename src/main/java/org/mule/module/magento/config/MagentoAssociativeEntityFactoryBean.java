package org.mule.module.magento.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Magento.AssociativeEntity;

public class MagentoAssociativeEntityFactoryBean extends org.springframework.beans.factory.config.AbstractFactoryBean<AssociativeEntity> {

	Logger log = LoggerFactory.getLogger(MagentoAssociativeEntityFactoryBean.class);
	
	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public Class<AssociativeEntity> getObjectType() {
		return AssociativeEntity.class;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected AssociativeEntity createInstance() {
		AssociativeEntity result = new AssociativeEntity(getKey(), getValue());
		return result;
	}

    public boolean isSingleton()
    {
        return false;
    }
}
