package com.pbalazs.fxdemo.service.impl;

import com.pbalazs.fxdemo.model.xml.XmlModel;
import com.pbalazs.fxdemo.model.xml.XmlModelBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.pbalazs.fxdemo.service.impl.EcbRateLoaderService.URL_ECB_XCHANGE_RATE_LAST_90_DAYS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

/**
 * Created by Peter on 4/9/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class EcbRateLoaderServiceTest {

    private EcbRateLoaderService instance;

    @Mock
    private RestTemplate mockRestTemplate;

    @Mock
    private ResponseEntity<XmlModel> mockResponseEntity;

    @Before
    public void init() {
        instance = new EcbRateLoaderService() {
            @Override
            RestTemplate createRestTemplate() {
                return mockRestTemplate;
            }
        };
    }

    @Test
    public void test_retrieveRates_success() {
        final XmlModel model = new XmlModelBuilder().cube("2017-04-09", "CHF", "1.06")
                .cube("2017-04-09", "GBP", "0.86")
                .cube("2017-04-06", "CHF", "1.05")
                .cube("2017-04-06", "GBP", "0.87")
                .build();
        final List<HttpMessageConverter<?>> converter = new ArrayList<>();
        when(mockRestTemplate.getMessageConverters()).thenReturn(converter);
        when(mockRestTemplate.getForEntity(URL_ECB_XCHANGE_RATE_LAST_90_DAYS, XmlModel.class)).thenReturn(mockResponseEntity);
        when(mockResponseEntity.getStatusCodeValue()).thenReturn(200);
        when(mockResponseEntity.getBody()).thenReturn(model);

        final Map<String, Map<String, String>> result = instance.retrieveRates();

        assertNotNull(result);
        assertEquals(2, result.size());
        final Map<String, String> map1 = result.get("2017-04-09");
        assertNotNull(map1);
        assertEquals(2, map1.size());
        assertEquals("1.06", map1.get("CHF"));
        assertEquals("0.86", map1.get("GBP"));
        final Map<String, String> map2 = result.get("2017-04-06");
        assertEquals(2, map2.size());
        assertEquals("1.05", map2.get("CHF"));
        assertEquals("0.87", map2.get("GBP"));
        assertEquals(1, converter.size());
    }

    @Test
    public void test_retrieveRates_success_202() {
        final XmlModel model = new XmlModelBuilder().cube("2017-04-09", "CHF", "1.06")
                .cube("2017-04-09", "GBP", "0.86")
                .cube("2017-04-06", "CHF", "1.05")
                .cube("2017-04-06", "GBP", "0.87")
                .build();
        final List<HttpMessageConverter<?>> converter = new ArrayList<>();
        when(mockRestTemplate.getMessageConverters()).thenReturn(converter);
        when(mockRestTemplate.getForEntity(URL_ECB_XCHANGE_RATE_LAST_90_DAYS, XmlModel.class)).thenReturn(mockResponseEntity);
        when(mockResponseEntity.getStatusCodeValue()).thenReturn(202);
        when(mockResponseEntity.getBody()).thenReturn(model);

        final Map<String, Map<String, String>> result = instance.retrieveRates();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void test_retrieveRates_failedCall() {
        final List<HttpMessageConverter<?>> converter = new ArrayList<>();
        when(mockRestTemplate.getMessageConverters()).thenReturn(converter);
        when(mockRestTemplate.getForEntity(URL_ECB_XCHANGE_RATE_LAST_90_DAYS, XmlModel.class)).thenReturn(mockResponseEntity);
        when(mockResponseEntity.getStatusCodeValue()).thenReturn(404);

        final Map<String, Map<String, String>> result = instance.retrieveRates();

        assertNull(result);
    }

    @Test
    public void test_retrieveRates_NullResponse() {
        final List<HttpMessageConverter<?>> converter = new ArrayList<>();
        when(mockRestTemplate.getMessageConverters()).thenReturn(converter);
        when(mockRestTemplate.getForEntity(URL_ECB_XCHANGE_RATE_LAST_90_DAYS, XmlModel.class)).thenReturn(null);

        final Map<String, Map<String, String>> result = instance.retrieveRates();

        assertNull(result);
    }

    @Test
    public void test_retrieveRates_NullBody() {
        final List<HttpMessageConverter<?>> converter = new ArrayList<>();
        when(mockRestTemplate.getMessageConverters()).thenReturn(converter);
        when(mockRestTemplate.getForEntity(URL_ECB_XCHANGE_RATE_LAST_90_DAYS, XmlModel.class)).thenReturn(mockResponseEntity);
        when(mockResponseEntity.getStatusCodeValue()).thenReturn(201);
        when(mockResponseEntity.getBody()).thenReturn(null);

        final Map<String, Map<String, String>> result = instance.retrieveRates();

        assertNotNull(result);
        assertEquals(0, result.size());
    }
    @Test
    public void test_retrieveRates_success_withInvalidDate() {
        final XmlModel model = new XmlModelBuilder().cube("2017-04-09", "CHF", "1.06")
                .cube("2017-04-09", "GBP", "0.86")
                .cube("2017-04-06", "CHF", "1.05")
                .cube("2017-04-06", "GBP", "0.87")
                .cube("blabla", "CHF", "1.09")
                .build();
        final List<HttpMessageConverter<?>> converter = new ArrayList<>();
        when(mockRestTemplate.getMessageConverters()).thenReturn(converter);
        when(mockRestTemplate.getForEntity(URL_ECB_XCHANGE_RATE_LAST_90_DAYS, XmlModel.class)).thenReturn(mockResponseEntity);
        when(mockResponseEntity.getStatusCodeValue()).thenReturn(200);
        when(mockResponseEntity.getBody()).thenReturn(model);

        final Map<String, Map<String, String>> result = instance.retrieveRates();

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
