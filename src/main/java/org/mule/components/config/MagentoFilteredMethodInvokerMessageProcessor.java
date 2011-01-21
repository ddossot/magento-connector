package org.mule.components.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mule.api.MessagingException;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.DataType;
import org.mule.api.transformer.Transformer;
import org.mule.api.transformer.TransformerException;
import org.mule.processor.InvokerMessageProcessor;
import org.mule.transformer.types.DataTypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Magento.AssociativeEntity;
import Magento.ComplexFilter;
import Magento.Filters;

public class MagentoFilteredMethodInvokerMessageProcessor extends
		InvokerMessageProcessor {

	Logger log = LoggerFactory
			.getLogger(MagentoFilteredMethodInvokerMessageProcessor.class);

	@Override
	protected Object evaluateExpressionCandidate(Object expressionCandidate,
			MuleMessage message) throws TransformerException {
		
		if (expressionCandidate instanceof Filters) {
			Filters filtersTemplate = (Filters) expressionCandidate;
			Filters newFilters = new Filters();

			AssociativeEntity[] filters = filtersTemplate.getFilter();
			if (filters != null && filters.length > 0) {
				List<AssociativeEntity> simpleFiltersList = new ArrayList<AssociativeEntity>();

				for (int i = 0; i < filters.length; i++) {
					AssociativeEntity nextFilterTemplate = filters[i];
					AssociativeEntity newEntity = new AssociativeEntity();
					newEntity.setKey((String) evaluateExpressionCandidate(nextFilterTemplate.getKey(), message));
					newEntity.setValue((String) evaluateExpressionCandidate(nextFilterTemplate.getValue(), message));
					simpleFiltersList.add(newEntity);
				}

				newFilters.setFilter(simpleFiltersList.toArray(new AssociativeEntity[] {}));
			}

			ComplexFilter[] complexFilters = filtersTemplate.getComplex_filter();
			if (complexFilters != null && complexFilters.length > 0) {
				List<ComplexFilter> complexFiltersList = new ArrayList<ComplexFilter>();

				for (int i = 0; i < complexFilters.length; i++) {
					ComplexFilter nextFilterTemplate = complexFilters[i];
					ComplexFilter newComplexFilter = new ComplexFilter();
					
					String templateKey = new String(nextFilterTemplate.getKey());					
					newComplexFilter.setKey((String) evaluateExpressionCandidate(templateKey, message));
					
					AssociativeEntity nextFilterEntityTemplate = nextFilterTemplate.getValue();
					AssociativeEntity newEntity = new AssociativeEntity();
					
					newEntity.setKey((String) evaluateExpressionCandidate(nextFilterEntityTemplate.getKey(), message));
					newEntity.setValue((String) evaluateExpressionCandidate(nextFilterEntityTemplate.getValue(), message));
					newComplexFilter.setValue(newEntity);
					complexFiltersList.add(newComplexFilter);
				}

				newFilters.setComplex_filter(complexFiltersList.toArray(new ComplexFilter[] {}));
			}

			return newFilters;
		}

		return super.evaluateExpressionCandidate(expressionCandidate, message);
	}
}
