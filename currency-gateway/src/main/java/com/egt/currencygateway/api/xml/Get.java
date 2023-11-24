package com.egt.currencygateway.api.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Get
{
    @XmlAttribute
    private Long consumer;

    @XmlElement
    private String currency;


    public Long getConsumer()
    {
        return consumer;
    }

    public void setConsumer(Long consumer)
    {
        this.consumer = consumer;
    }

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }
}
