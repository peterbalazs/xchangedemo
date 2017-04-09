package com.pbalazs.fxdemo.controller;

import com.pbalazs.fxdemo.exceptions.RateNotAvailableException;
import com.pbalazs.fxdemo.model.DateRatePair;
import com.pbalazs.fxdemo.model.FxResponseModel;
import com.pbalazs.fxdemo.service.RateService;
import com.pbalazs.fxdemo.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Peter on 4/9/2017.
 */
@RestController
public class FxDemoController {

    private static final Logger logger = LoggerFactory.getLogger(FxDemoController.class);

    @Autowired
    private RateService rateService;

    @RequestMapping(value = "/rate/{ccy}/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public FxResponseModel getRateForCurrencyOnDate(@PathVariable final String ccy, @PathVariable final String date)
            throws RateNotAvailableException
    {
        logger.debug("Getting rate for {} on {}", ccy, date);

        final DateRatePair dateRatePair = rateService.getRateForCurrencyAndDate(ccy.toUpperCase(), date);
        final FxResponseModel response = buildResponse(ccy, dateRatePair.getDate(), dateRatePair.getRate());

        logger.info("Result: {}", response);
        return response;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Date must be in format yyyy-MM-dd, ex: 2017-04-09")
    @ExceptionHandler(IllegalArgumentException.class)
    public void invalidParams() {
        // empty
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No rate found for the specified currency - date pair")
    @ExceptionHandler(RateNotAvailableException.class)
    public void noRate() {
        // empty
    }

    private FxResponseModel buildResponse(final String ccy, final String date, final String rate) {
        final FxResponseModel responseModel = new FxResponseModel();
        responseModel.setCurrency(ccy.toUpperCase());
        responseModel.setDate(date);
        responseModel.setRate(rate);
        return responseModel;
    }
}
