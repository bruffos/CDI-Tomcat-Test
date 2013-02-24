package com.pontus.rest.rule;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: pontus
 * Date: 11/1/12
 * Time: 2:44 PM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
public class RuleEngineBean {
    private final Map<String, RuleInterface> rules = new HashMap<>();

    @Inject
    private List<RuleInterface> ruleList;

    @PostConstruct
    public void init() throws IOException {
        for (RuleInterface rule : ruleList) {
            rules.put(rule.getId().toLowerCase(), rule);
        }

    }

    public boolean evaluateRules() {
        for (RuleInterface rule : ruleList) {
            if (!rule.eval()) {
                return false;
            }
        }
        return true;
    }


    public boolean evaluateRule(String id) {
        return rules.get(id).eval();
    }
}
