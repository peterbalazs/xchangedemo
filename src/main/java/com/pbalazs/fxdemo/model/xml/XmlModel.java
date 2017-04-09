package com.pbalazs.fxdemo.model.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Peter on 4/9/2017.
 */
@XmlRootElement(name="Envelope", namespace = "http://www.gesmes.org/xml/2002-08-01")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlModel {

    @JacksonXmlProperty(namespace = "gesmes")
    private String subject;

    @JacksonXmlProperty(localName = "Sender", namespace = "gesmes")
    private Sender sender;

    @JacksonXmlProperty(localName = "Cube")
    private CubeParent cube;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public CubeParent getCube() {
        return cube;
    }

    public void setCube(CubeParent cube) {
        this.cube = cube;
    }
}
