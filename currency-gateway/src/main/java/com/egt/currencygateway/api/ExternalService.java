package com.egt.currencygateway.api;

public enum ExternalService
{
    json("EXT_SERVICE_1"),
    xml("EXT_SERVICE_2");

    private final String name;

    ExternalService(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
