package com.pontus.rest.rule;

import javax.annotation.ManagedBean;

/**
 * Created with IntelliJ IDEA.
 * User: pontus
 * Date: 11/1/12
 * Time: 2:48 PM
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean("ARule")
public class RuleImpl implements RuleInterface {

    @Override
    public String getId() {
        return "ARule";
    }

    @Override
    public boolean eval() {
        return true;
    }

}
