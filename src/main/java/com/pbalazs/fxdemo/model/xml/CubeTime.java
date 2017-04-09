package com.pbalazs.fxdemo.model.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

/**
 * Created by Peter on 4/9/2017.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CubeTime {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Cube")
    private List<Cube> cube;

    @JacksonXmlProperty(localName = "time")
    private String time;

    public List<Cube> getCube() {
        return cube;
    }

    public void setCube(List<Cube> cube) {
        this.cube = cube;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
