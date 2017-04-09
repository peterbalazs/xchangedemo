package com.pbalazs.fxdemo.controller;

import com.pbalazs.fxdemo.model.FxResponseModel;
import com.pbalazs.fxdemo.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Peter on 4/9/2017.
 */
@RestController
public class FxDemoController {

    @Autowired
    private RateService rateService;

    @RequestMapping(value = "/rate/{ccy}/{date}", method = RequestMethod.GET)
    @ResponseBody
    public FxResponseModel getRateForCurrencyOnDate(@PathVariable final String ccy, @PathVariable final String date) {
        final String rate = rateService.getRateForCurrencyAndDate(ccy, date);

        return buildResponse(ccy, date, rate);
    }

    private FxResponseModel buildResponse(final String ccy, final String date, final String rate) {
        final FxResponseModel responseModel = new FxResponseModel();
        responseModel.setCurrency(ccy);
        responseModel.setDate(date);
        responseModel.setRate(rate);
        return responseModel;
    }
}
