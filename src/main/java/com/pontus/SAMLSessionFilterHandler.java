package com.pontus;

import com.pontus.rest.rule.ABean;
import com.pontus.rest.rule.AProperty;
import com.pontus.rest.rule.RuleEngineBean;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: pontus
 * Date: 10/26/12
 * Time: 11:23 AM
 * To change this template use File | Settings | File Templates.
 */
//@ManagedBean("samlSessionFilter")
public class SAMLSessionFilterHandler implements Filter {
    private Map<String, String> userToUuid = new HashMap<String, String>();
    @Inject
    private ABean theBean;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        UUIDHttpServletRequestWrapper wrappedServletRequest = new UUIDHttpServletRequestWrapper(servletRequest);

        String uuid = getUUID(wrappedServletRequest);
        setUser(uuid, wrappedServletRequest);

        String user = wrappedServletRequest.getParameter("j_username");
        if (user != null) {
            Principal principal = createPrincipal(user);
            wrappedServletRequest.setUserPrincipal(principal);

            uuid = generateUID(wrappedServletRequest.getUserPrincipal());
            if (uuid != null) {
                ((HttpServletResponse) servletResponse).addHeader("x-pontus-set-id", uuid);
            }


        }

        filterChain.doFilter(wrappedServletRequest, servletResponse);


    }

    private void setUser(String uuid, UUIDHttpServletRequestWrapper wrappedServletRequest) {
        String idHeaderValue = wrappedServletRequest.getHeader("x-pontus-id");
        if (idHeaderValue != null) {

            String user = parseIdHeaderForUser(idHeaderValue);
            if (user != null) {
                if (validateUUID(uuid, user)) {
                    Principal principal = createPrincipal(user);
                    wrappedServletRequest.setUserPrincipal(principal);
                    return;
                }

            }
        }
    }

    private String generateUID(Principal principal) {
        if (principal != null) {
            String uuid = UUID.randomUUID().toString();
            userToUuid.put(principal.getName(), uuid);
            return MessageFormat.format("{0},{1}", uuid, principal.getName());
        }
        return null;
    }


    private String getUUID(UUIDHttpServletRequestWrapper servletRequest) {
        String idHeaderValue = servletRequest.getHeader("x-pontus-id");
        String uuid = null;
        if (idHeaderValue != null) {
            uuid = parseIdHeaderForUUID(idHeaderValue);
            String user = parseIdHeaderForUser(idHeaderValue);
            if (uuid == null || user == null || !validateUUID(uuid, user)) {
                return null;
            }
        }
        return uuid;
    }

    private Principal createPrincipal(final String user) {

        return new Principal() {
            @Override
            public String getName() {
                return user;
            }
        };
    }

    private String parseIdHeaderForUUID(String idHeaderValue) {
        int separator = idHeaderValue.indexOf(",");
        if (separator > 0 && separator < idHeaderValue.length()) {
            return idHeaderValue.substring(0, separator);
        }
        return null;
    }

    private String parseIdHeaderForUser(String idHeaderValue) {
        int separator = idHeaderValue.indexOf(",");
        if (separator > 0 && separator < idHeaderValue.length()) {
            return idHeaderValue.substring(separator + 1);
        }
        return null;
    }

    private boolean validateUUID(String uuid, String principal) {
        return userToUuid.containsKey(principal) && userToUuid.get(principal).equals(uuid);
    }

    @Override
    public void destroy() {
    }

    private static class UUIDHttpServletRequestWrapper extends HttpServletRequestWrapper {
        private Principal principal;

        public UUIDHttpServletRequestWrapper(ServletRequest servletRequest) {
            super((HttpServletRequest) servletRequest);
        }

        @Override
        public Principal getUserPrincipal() {
            return principal;
        }

        public void setUserPrincipal(Principal principal) {
            this.principal = principal;
        }
    }
}
