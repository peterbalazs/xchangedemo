package com.pbalazs.fxdemo.model.xml;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 4/9/2017.
 */
public class XmlModelBuilder {

    private final XmlModel model;

    public XmlModelBuilder() {
        model = new XmlModel();
    }

    public XmlModel build() {
        return model;
    }

    public XmlModelBuilder cube(final String date, final String ccy, final String rate) {
        if (model.getCube() == null) {
            model.setCube(new CubeParent());
        }
        if (model.getCube().getCube() == null) {
            model.getCube().setCube(new ArrayList<>());
        }
        CubeTime cubeTime = lookupCubeTime(model.getCube().getCube(), date);
        if (cubeTime == null) {
            cubeTime = new CubeTime();
            cubeTime.setTime(date);
            cubeTime.setCube(new ArrayList<>());
            model.getCube().getCube().add(cubeTime);
        }
        final Cube cube = new Cube();
        cube.setCurrency(ccy);
        cube.setRate(rate);
        cubeTime.getCube().add(cube);

        return this;
    }

    private CubeTime lookupCubeTime(final List<CubeTime> list, final String date) {
        for (CubeTime ct : list) {
            if (date.equals(ct.getTime())) {
                return ct;
            }
        }
        return null;
    }
}
