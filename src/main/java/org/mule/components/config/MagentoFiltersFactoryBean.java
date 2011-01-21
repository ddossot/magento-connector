package org.mule.components.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Magento.AssociativeEntity;
import Magento.ComplexFilter;
import Magento.Filters;

public class MagentoFiltersFactoryBean extends org.springframework.beans.factory.config.AbstractFactoryBean<Filters> {

	Logger log = LoggerFactory.getLogger(MagentoFiltersFactoryBean.class);
	
	private List<AssociativeEntity> filters;
	private List<ComplexFilter> complexFilters;
	
	@Override
	public Class<Filters> getObjectType() {
		return Filters.class;
	}

	public void setFilters(List<AssociativeEntity> filters) {
		this.filters = filters;
	}

	public void setComplexFilters(List<ComplexFilter> complexFilters) {
		this.complexFilters = complexFilters;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Filters createInstance() {
		if ((this.filters == null || filters.size() < 1) &&
			(this.complexFilters == null || complexFilters.size() < 1))	{
			throw new IllegalArgumentException("filters list must include at least one filter or complex filter");
		}
		
		Filters result = new Filters();
		
		if (this.filters != null)
			result.setFilter((AssociativeEntity[])this.filters.toArray(new AssociativeEntity[] {}));
		if (this.complexFilters != null)			
			result.setComplex_filter((ComplexFilter[])this.complexFilters.toArray(new ComplexFilter[] {}));
		
		return result;
	}

    public boolean isSingleton()
    {
        return false;
    }
}
