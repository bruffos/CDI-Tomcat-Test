package com.pontus.rest;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.pontus.rest.rule.ABean;
import com.pontus.rest.rule.AProperty;
import com.pontus.rest.rule.RuleEngineBean;

/**
 * Created with IntelliJ IDEA.
 * User: pontus
 * Date: 10/30/12
 * Time: 11:13 AM
 * To change this template use File | Settings | File Templates.
 */
@Path("/s")
@Singleton
public class SingletonAPI {

    @Inject
    private ABean theBean;

    @Inject
    private RuleEngineBean ruleEngineBean;

	@Inject
	@AProperty
	private String aProperty;

    @GET
    @Path("principal/name")
    @Produces("text/plain")
    public String getPrincipalName(@Context HttpServletRequest request) throws Exception {
        return request.getUserPrincipal().getName();
    }

    @GET
    @Path("principal/theBean")
    @Produces("text/plain")
    public String getTheBean() throws Exception {
        return theBean.getTheProperty();
    }


    @GET
    @Path("principal/ABean")
    @Produces("text/plain")
    public String getABean() throws Exception {
        return theBean.getAProperty();
    }

	@GET
    @Path("principal/evaluateRules")
    @Produces("text/plain")
    public boolean evaluateRules() throws Exception {
        return ruleEngineBean.evaluateRules();
    }

    @GET
    @Path("principal/evaluateRule/{ruleId}")
    @Produces("text/plain")
    public boolean evaluateRule(@PathParam("ruleId") String ruleId) throws Exception {
        return ruleEngineBean.evaluateRule(ruleId);
    }

	@GET
	@Path("principal/aproperty")
	@Produces("text/plain")
	public String getAProperty() throws Exception {
		return aProperty;
	}
}

