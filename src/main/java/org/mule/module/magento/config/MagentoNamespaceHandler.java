package org.mule.module.magento.config;

import org.mule.config.spring.handlers.AbstractPojoNamespaceHandler;
import org.mule.config.spring.parsers.generic.ChildDefinitionParser;
import org.mule.config.spring.parsers.generic.OrphanDefinitionParser;
import org.mule.config.spring.parsers.specific.InvokerMessageProcessorDefinitionParser;
import org.mule.module.magento.Magento;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MagentoNamespaceHandler extends AbstractPojoNamespaceHandler
{
    Logger log = LoggerFactory.getLogger(MagentoNamespaceHandler.class);

    public void init()
    {
        registerMuleBeanDefinitionParser("config", new OrphanDefinitionParser(Magento.class, false)).addIgnored(
                "name");
        
        registerMuleBeanDefinitionParser("sales-orders-list", new ChildDefinitionParser("messageProcessor",
                MagentoSalesOrdersListFactoryBean.class, false));
        registerMuleBeanDefinitionParser("filters", new ChildDefinitionParser("filters", MagentoFiltersFactoryBean.class, false));
        registerMuleBeanDefinitionParser("filter", new ChildDefinitionParser("filter", MagentoAssociativeEntityFactoryBean.class, false));
        registerMuleBeanDefinitionParser("complex-filter", new ChildDefinitionParser("complex-filter", MagentoComplexFilterFactoryBean.class, false));

        registerMuleBeanDefinitionParser("sales-order-info", new InvokerMessageProcessorDefinitionParser("messageProcessor",
                Magento.class, "salesOrderInfo", new String[]{"orderIncrementId"}));

        registerMuleBeanDefinitionParser("sales-order-hold", new InvokerMessageProcessorDefinitionParser("messageProcessor",
                Magento.class, "salesOrderHold", new String[]{"orderIncrementId"}));

        registerMuleBeanDefinitionParser("sales-order-unhold", new InvokerMessageProcessorDefinitionParser("messageProcessor",
                Magento.class, "salesOrderUnhold", new String[]{"orderIncrementId"}));

        registerMuleBeanDefinitionParser("sales-order-cancel", new InvokerMessageProcessorDefinitionParser("messageProcessor",
                Magento.class, "salesOrderCancel", new String[]{"orderIncrementId"}));

        registerMuleBeanDefinitionParser("sales-order-comment", new InvokerMessageProcessorDefinitionParser("messageProcessor",
                Magento.class, "salesOrderComment", new String[]{"orderIncrementId", "status", "comment", "notify"}));

        registerMuleBeanDefinitionParser("sales-order-shipments-list", new ChildDefinitionParser("messageProcessor",
                MagentoSalesOrderShipmentsListFactoryBean.class, false));

        registerMuleBeanDefinitionParser("sales-order-shipment-info", new InvokerMessageProcessorDefinitionParser("messageProcessor",
                Magento.class, "salesOrderShipmentInfo", new String[]{"shipmentIncrementId"}));

        registerMuleBeanDefinitionParser("sales-order-shipment-comment", new InvokerMessageProcessorDefinitionParser("messageProcessor",
                Magento.class, "salesOrderShipmentComment", new String[]{"shipmentIncrementId", "status", "comment", "email", "includeInEmail"}));

        registerMuleBeanDefinitionParser("sales-order-shipment-get-carriers", new InvokerMessageProcessorDefinitionParser("messageProcessor",
                Magento.class, "salesOrderShipmentGetCarriers", new String[]{"orderIncrementId"}));

        registerMuleBeanDefinitionParser("sales-order-shipment-add-track", new InvokerMessageProcessorDefinitionParser("messageProcessor",
                Magento.class, "salesOrderShipmentAddTrack", new String[]{"shipmentIncrementId", "carrier", "title", "trackNumber"}));
        
        registerMuleBeanDefinitionParser("sales-order-shipment-remove-track", new InvokerMessageProcessorDefinitionParser("messageProcessor",
                Magento.class, "salesOrderShipmentRemoveTrack", new String[]{"shipmentIncrementId", "trackId"}));
        
        registerMuleBeanDefinitionParser("sales-order-shipment-create", new InvokerMessageProcessorDefinitionParser("messageProcessor",
                Magento.class, "salesOrderShipmentCreate", new String[]{"orderIncrementId", "itemsQty", "comment", "email", "includeInEmail"}));

        registerMuleBeanDefinitionParser("sales-order-invoices-list", new ChildDefinitionParser("messageProcessor",
                MagentoSalesOrderInvoicesListFactoryBean.class, false));
        
        registerMuleBeanDefinitionParser("sales-order-invoice-info", new InvokerMessageProcessorDefinitionParser("messageProcessor",
                Magento.class, "salesOrderInvoiceInfo", new String[]{"invoiceIncrementId"}));

        registerMuleBeanDefinitionParser("sales-order-invoice-create", new InvokerMessageProcessorDefinitionParser("messageProcessor",
                Magento.class, "salesOrderInvoiceCreate", new String[]{"orderIncrementId", "itemsQty", "comment", "email", "includeInEmail"}));

        registerMuleBeanDefinitionParser("sales-order-invoice-comment", new InvokerMessageProcessorDefinitionParser("messageProcessor",
                Magento.class, "salesOrderInvoiceComment", new String[]{"invoiceIncrementId", "status", "comment", "email", "includeInEmail"}));

        registerMuleBeanDefinitionParser("sales-order-invoice-capture", new InvokerMessageProcessorDefinitionParser("messageProcessor",
                Magento.class, "salesOrderInvoiceCapture", new String[]{"invoiceIncrementId"}));
        
        registerMuleBeanDefinitionParser("sales-order-invoice-void", new InvokerMessageProcessorDefinitionParser("messageProcessor",
                Magento.class, "salesOrderInvoiceVoid", new String[]{"invoiceIncrementId"}));
        
        registerMuleBeanDefinitionParser("sales-order-invoice-cancel", new InvokerMessageProcessorDefinitionParser("messageProcessor",
                Magento.class, "salesOrderInvoiceCancel", new String[]{"invoiceIncrementId"}));
    }
}
