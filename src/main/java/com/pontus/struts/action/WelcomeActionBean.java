package com.pontus.struts.action;

import com.pontus.rest.rule.ABean;
import com.pontus.rest.rule.AProperty;
import com.pontus.rest.rule.TheProperty;

import javax.inject.Inject;
import javax.inject.Named;

@Named("WelcomeAction")
@WelcomeActionQualifier
public class WelcomeActionBean {
    @AProperty
    @Inject
    private String getAProperty;
    @TheProperty
    @Inject
    private String getTheProperty;

    @Inject
    private ABean aBean;


    public String getGetAProperty() {
        return getAProperty;
    }

    public String getGetTheProperty() {
        return getTheProperty;
    }

    public ABean getaBean() {
        return aBean;
    }
}
