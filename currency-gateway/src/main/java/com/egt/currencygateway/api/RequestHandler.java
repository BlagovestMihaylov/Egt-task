package com.egt.currencygateway.api;

import com.egt.currencygateway.api.db.RequestRepository;
import com.egt.currencygateway.api.json.RatesRequestJson;
import com.egt.currencygateway.api.model.RequestDTO;
import com.egt.currencygateway.api.model.RequestMapper;
import com.egt.currencygateway.api.xml.Command;
import com.egt.currencygateway.rabbit.RabbitProducer;
import com.egt.currencygateway.rates.RatesService;
import com.egt.currencygateway.rates.model.Rate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class RequestHandler
{
    private final static Logger logger = Logger.getLogger(RequestHandler.class.getName());

    private final RatesService ratesService;

    private final RequestRepository requestRepository;

    private final RabbitProducer rabbitProducer;

    public RequestHandler(final RatesService ratesService,
                          final RequestRepository requestRepository,
                          final RabbitProducer rabbitProducer)
    {
        this.ratesService = ratesService;
        this.requestRepository = requestRepository;
        this.rabbitProducer = rabbitProducer;
    }

    public List<Rate> handleJsonRequest(final RatesRequestJson request, final ExternalService externalService)
    {
        logger.info("Handling request from external service: " + externalService.getName());

        return handleRequest(RequestMapper.fromJsonRequest(request, externalService.getName()));
    }

    public List<Rate> handleXmlRequest(final Command request, final ExternalService externalService)
    {
        logger.info("Handling request from external service: " + externalService.getName());

        return handleRequest(RequestMapper.fromXmlRequest(request, externalService.getName()));
    }

    private List<Rate> handleRequest(final RequestDTO request)
    {
        requestRepository.save(request);

        final List<Rate> rates = getRates(request);

        logger.info("Rates size: " + rates.size());

        rabbitProducer.send(request);

        requestRepository.complete(request.getId(), request.getServiceName());

        return rates;
    }


    private List<Rate> getRates(final RequestDTO request)
    {
        if (request.getPeriod() == null || request.getPeriod() == 0)
        {
            return ratesService.getRates(request.getCurrency());
        }

        return ratesService.getRates(request.getCurrency(), request.getPeriod());
    }
}
