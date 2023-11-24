package com.egt.currencygateway.api.xml;

import com.egt.currencygateway.api.RequestHandler;
import com.egt.currencygateway.rates.model.Rate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.egt.currencygateway.api.ExternalService.xml;

@RestController
public class XMLController
{
    private final RequestHandler requestHandler;

    public XMLController(RequestHandler requestHandler)
    {
        this.requestHandler = requestHandler;
    }

    @PostMapping(value = "/command", consumes = MediaType.APPLICATION_XML_VALUE)
    public List<Rate> handleXmlRequest(@RequestBody Command command)
    {
        return requestHandler.handleXmlRequest(command, xml);
    }
}
