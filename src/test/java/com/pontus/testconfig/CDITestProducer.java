package com.pontus.testconfig;

import com.pontus.rest.rule.*;
import org.mockito.Mock;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pontus
 * Date: 11/5/12
 * Time: 2:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class CDITestProducer {

    static int counter = 0;

    static int theecounter = 0;

    //New is deprecated in CDI 1.1
    @Produces
    public ABean getAbean(@New TheBean theBean) {
        return theBean;
    }

    @Produces
    public List<RuleInterface> getRuleList() {
        List<RuleInterface> newRuleList = new LinkedList<>();
        for (RuleInterface rule : ruleList) {
            newRuleList.add(rule);
        }
        return newRuleList;
    }

    @Inject
    private Instance<RuleInterface> ruleList;

    @Produces
    @TheProperty
    @Singleton
    public String getTheProperty() {
        return "theString" + theecounter++;
    }

    @Produces
    @AProperty
    public String getAProperty() {
        return "aString" + counter++;
    }
}
