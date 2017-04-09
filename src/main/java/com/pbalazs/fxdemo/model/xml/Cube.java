package com.pbalazs.fxdemo.model.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by Peter on 4/9/2017.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Cube {

    @JacksonXmlProperty(isAttribute = true)
    private String rate;

    @JacksonXmlProperty(isAttribute = true)
    private String currency;

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
