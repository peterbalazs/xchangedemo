package com.pbalazs.fxdemo.service.impl;

import com.pbalazs.fxdemo.model.xml.XmlModel;
import com.pbalazs.fxdemo.service.RateLoaderService;
import com.pbalazs.fxdemo.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Peter on 4/9/2017.
 */
@Service
public class EcbRateLoaderService implements RateLoaderService {

    private static final Logger logger = LoggerFactory.getLogger(EcbRateLoaderService.class);

    static final String URL_ECB_XCHANGE_RATE_LAST_90_DAYS = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml";
    static final String URL_ECB_XCHANGE_RATES_LATEST = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

    @Override
    public Map<String, Map<String, String>> retrieveRates() {
        return retrieveRates(URL_ECB_XCHANGE_RATE_LAST_90_DAYS);
    }

    @Override
    public Map<String, Map<String, String>> retrieveLatestRates() {
        return retrieveRates(URL_ECB_XCHANGE_RATES_LATEST);
    }

    private Map<String, Map<String, String>> retrieveRates(final String url) {

        final RestTemplate template = createRestTemplate();
        template.getMessageConverters().add(new MappingJackson2XmlHttpMessageConverter());

        final ResponseEntity<XmlModel> re;
        try {
            re = template.getForEntity(url, XmlModel.class);
        }  catch (Throwable t) {
            logger.error("Error connecting to ECB server", t);
            return null;
        }

        if (re == null) {
            logger.error("NULL response");
            return null;
        }

        logger.info("Response status code: {}", re.getStatusCodeValue());

        if (re.getStatusCodeValue() / 100 != 2) {
            logger.error("Expected HTTP status 2xx but received " + re.getStatusCodeValue());
            return null;
        }

        final Map<String, Map<String, String>> result = new HashMap<>();
        mapResult(re.getBody(), result);

        return result;
    }

    private void mapResult(final XmlModel body, final Map<String, Map<String, String>> result) {
        if (body == null || body.getCube() == null || body.getCube().getCube() == null || body.getCube().getCube().size() == 0) {
            logger.error("Response body is NULL or no rates are provided");
            return;
        }

        body.getCube().getCube().forEach(timeCube -> {
            if (timeCube == null || timeCube.getCube() == null || timeCube.getCube().size() == 0
                    || !StringUtils.hasText(timeCube.getTime())) {
                logger.warn("Empty or non-complete entry found, skipping it!");
                return;
            }
            final String validatedTime;
            try {
                validatedTime = DateUtils.validateDate(timeCube.getTime());
            } catch (IllegalArgumentException iae) {
                logger.warn("Could not parse date {}", timeCube.getTime());
                return;
            }
            final Map<String, String> rateMap = new HashMap<>();
            timeCube.getCube().forEach(cube -> {
                if (cube == null || !StringUtils.hasText(cube.getRate()) || !StringUtils.hasText(cube.getCurrency())) {
                    logger.warn("Null or incomplete rate entry found, skipping it");
                    return;
                }
                rateMap.put(cube.getCurrency(), cube.getRate());
            });
            result.put(validatedTime, rateMap);
        });
    }

    RestTemplate createRestTemplate() {
        return new RestTemplate();
    }
}
