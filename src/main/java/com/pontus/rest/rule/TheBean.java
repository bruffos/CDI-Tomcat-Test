package com.pontus.rest.rule;

import javax.inject.Inject;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: pontus
 * Date: 11/5/12
 * Time: 5:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class TheBean implements ABean {
    @Inject
    @AProperty
    private String aProperty;

    @Inject
    @TheProperty
    private String theProperty;

    public String getAProperty() {
        return aProperty;
    }

    public String getTheProperty() {
        return theProperty;
    }
}
