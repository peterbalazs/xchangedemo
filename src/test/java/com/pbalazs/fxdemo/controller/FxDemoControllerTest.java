package com.pbalazs.fxdemo.controller;

import com.pbalazs.fxdemo.service.RateService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Peter on 4/9/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class FxDemoControllerTest {

    private FxDemoController instance;

    @Mock
    private RateService mockRateService;

    private MockMvc mockMvc;

    @Before
    public void init() throws Exception {
        instance = new FxDemoController();
        ReflectionTestUtils.setField(instance, "rateService", mockRateService);

        when(mockRateService.getRateForCurrencyAndDate("CHF", "2017-04-09")).thenReturn("1.070123");
        when(mockRateService.getRateForCurrencyAndDate("GBP", "2017-04-09")).thenReturn("0.863720");
        when(mockRateService.getRateForCurrencyAndDate("CHF", "2017-04-06")).thenReturn("1.068889");
        when(mockRateService.getRateForCurrencyAndDate("GBP", "2017-04-06")).thenReturn("0.862901");

        mockMvc = MockMvcBuilders.standaloneSetup(instance).build();
    }

    @Test
    public void test_getRateForCurrencyOnDate_happyFlow() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rate/CHF/2017-04-09")).andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.currency", is("CHF")))
            .andExpect(jsonPath("$.date", is("2017-04-09")))
            .andExpect(jsonPath("$.rate", is("1.070123")));

        mockMvc.perform(MockMvcRequestBuilders.get("/rate/GBP/2017-04-06")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.currency", is("GBP")))
                .andExpect(jsonPath("$.date", is("2017-04-06")))
                .andExpect(jsonPath("$.rate", is("0.862901")));
    }

    @Test
    public void test_getRateForCurrencyOnDate_lowercaseDateFlow() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rate/cHf/2017-04-06")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.currency", is("CHF")))
                .andExpect(jsonPath("$.date", is("2017-04-06")))
                .andExpect(jsonPath("$.rate", is("1.068889")));
    }

    @Test
    public void test_getRateForCurrencyDate_wrongDateFormat() throws Exception {
        when(mockRateService.getRateForCurrencyAndDate(anyString(), eq("2017_04_09"))).thenThrow(IllegalArgumentException.class);
        when(mockRateService.getRateForCurrencyAndDate(anyString(), eq("mfjsdkjfffg"))).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/rate/CHF/mfjsdkjfffg")).andExpect(status().isBadRequest());
        mockMvc.perform(MockMvcRequestBuilders.get("/rate/CHF/2017_04_09")).andExpect(status().isBadRequest());
    }

    @Test
    @Ignore("inverted date format should fail, TODO: implement solution")
    public void test_getRateForCurrencyDate_invertedDateFormat() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rate/CHF/09-04-2017")).andExpect(status().isBadRequest());
    }
}
