package com.egt.currencygateway.api.model;

import com.egt.currencygateway.api.json.RatesRequestJson;
import com.egt.currencygateway.api.xml.Command;

import java.time.Instant;
import java.time.OffsetDateTime;

public class RequestMapper
{
    public static RequestDTO fromJsonRequest(RatesRequestJson requestJson, String serviceName)
    {
        return new RequestDTO(requestJson.requestId(),
                              requestJson.currency(),
                              requestJson.clientId(),
                              requestJson.timestamp(),
                              requestJson.period(),
                              serviceName);
    }

    public static RequestDTO fromXmlRequest(Command requestXml, String serviceName)
    {
        if (requestXml.getGet() != null)
        {
            return fromXmlGetRequest(requestXml, serviceName);
        }
        else if (requestXml.getHistory() != null)
        {
            return fromXmlHistoryRequest(requestXml, serviceName);
        }
        else
        {
            throw new IllegalArgumentException("Unknown request type");
        }
    }

    private static RequestDTO fromXmlGetRequest(Command requestXml, String serviceName)
    {
        return new RequestDTO(requestXml.getId(),
                              requestXml.getGet().getCurrency(),
                              requestXml.getGet().getConsumer(),
                              System.currentTimeMillis(),
                              null,
                              serviceName);
    }

    private static RequestDTO fromXmlHistoryRequest(Command requestXml, String serviceName)
    {
        return new RequestDTO(requestXml.getId(),
                              requestXml.getHistory().getCurrency(),
                              requestXml.getHistory().getConsumer(),
                              Instant.now().getEpochSecond(),
                              requestXml.getHistory().getPeriod(),
                              serviceName);
    }
}
