package com.pontus;

import javax.servlet.annotation.WebFilter;

/**
 * Created with IntelliJ IDEA.
 * User: pontus
 * Date: 11/5/12
 * Time: 5:58 PM
 * To change this template use File | Settings | File Templates.
 */
@WebFilter(filterName = "samlSessionFilter", urlPatterns = "/*")
public class SAMLSessionCDIFilter extends SAMLSessionFilterHandler {
}
