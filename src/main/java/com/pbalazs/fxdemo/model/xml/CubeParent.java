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
public class CubeParent {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Cube")
    private List<CubeTime> cube;

    public List<CubeTime> getCube() {
        return cube;
    }

    public void setCube(List<CubeTime> cube) {
        this.cube = cube;
    }
}
