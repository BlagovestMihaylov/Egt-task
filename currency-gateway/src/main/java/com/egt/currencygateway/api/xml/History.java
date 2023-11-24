package com.egt.currencygateway.api.xml;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class History
{

    @XmlAttribute
    private Long consumer;

    @XmlAttribute
    private String currency;

    @XmlAttribute
    private Integer period;

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

    public Integer getPeriod()
    {
        return period;
    }

    public void setPeriod(Integer period)
    {
        this.period = period;
    }
}
