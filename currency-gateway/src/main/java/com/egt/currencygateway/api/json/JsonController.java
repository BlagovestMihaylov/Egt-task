package com.egt.currencygateway.api.json;

import com.egt.currencygateway.api.RequestHandler;
import com.egt.currencygateway.rates.model.Rate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.egt.currencygateway.api.ExternalService.json;

@RestController
public class JsonController
{
    private final RequestHandler requestHandler;

    public JsonController(RequestHandler requestHandler)
    {
        this.requestHandler = requestHandler;
    }

    @PostMapping("/current")
    public List<Rate> current(@RequestBody final RatesRequestJson request)
    {
        return requestHandler.handleJsonRequest(request, json);
    }

    @PostMapping("/history")  //kinda duplicate code
    public List<Rate> history(@RequestBody final RatesRequestJson request)
    {
        return requestHandler.handleJsonRequest(request, json);
    }
}
