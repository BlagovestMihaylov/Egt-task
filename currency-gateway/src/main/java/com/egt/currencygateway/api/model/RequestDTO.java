package com.egt.currencygateway.api.model;

public class RequestDTO
{
    private String id;
    private String currency;
    private Long clientId;
    private Long timestamp;
    private Integer period;
    private String serviceName;

    public RequestDTO()
    {
    }

    public RequestDTO(String id,
                      String currency,
                      Long clientId,
                      Long timestamp,
                      Integer period,
                      String serviceName)
    {
        this.id = id;
        this.currency = currency;
        this.clientId = clientId;
        this.timestamp = timestamp;
        this.period = period;
        this.serviceName = serviceName;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }

    public Long getClientId()
    {
        return clientId;
    }

    public void setClientId(Long clientId)
    {
        this.clientId = clientId;
    }

    public Long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(Long timestamp)
    {
        this.timestamp = timestamp;
    }

    public Integer getPeriod()
    {
        return period;
    }

    public void setPeriod(Integer period)
    {
        this.period = period;
    }

    public String getServiceName()
    {
        return serviceName;
    }

    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }

    @Override
    public String toString()
    {
        return "RequestDTO{" +
                "requestId='" + id + '\'' +
                ", currency='" + currency + '\'' +
                ", clientId=" + clientId +
                ", timestamp=" + timestamp +
                ", period=" + period +
                ", serviceName='" + serviceName + '\'' +
                '}';
    }
}
