package com.egt.currencygateway.api.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "command")
@XmlAccessorType(XmlAccessType.FIELD)
public class Command
{
    @XmlElement
    private String id;

    @XmlElement(name = "history")
    private History history;

    @XmlElement(name = "get")
    private Get get;


    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public History getHistory()
    {
        return history;
    }

    public void setHistory(History history)
    {
        this.history = history;
    }

    public Get getGet()
    {
        return get;
    }

    public void setGet(Get get)
    {
        this.get = get;
    }
}
