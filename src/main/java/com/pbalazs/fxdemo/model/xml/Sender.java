package com.pbalazs.fxdemo.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Peter on 4/9/2017.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Sender {

    @XmlElement(namespace = "gesmes")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
