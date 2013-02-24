package com.pontus.rest.rule;

/**
 * Created with IntelliJ IDEA.
 * User: pontus
 * Date: 11/1/12
 * Time: 2:50 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RuleInterface {
    String getId();

    boolean eval();
}
