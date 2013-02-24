package com.pontus.struts.action;

import javax.annotation.ManagedBean;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.*;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pontus.rest.rule.ABean;
import com.pontus.rest.rule.AProperty;
import com.pontus.rest.rule.TheProperty;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.BaseAction;

import java.util.Set;

public class WelcomeAction extends BaseAction {


    private final WelcomeActionBean welcomeActionBean;

    public WelcomeAction() throws NamingException {
        welcomeActionBean = getWelcomeActionBean();
    }

	@Override
	public ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
    	request.setAttribute("aPRoperty",  welcomeActionBean.getGetAProperty());
		request.setAttribute("thePRoperty",  welcomeActionBean.getGetTheProperty());
		request.setAttribute("aBean", welcomeActionBean.getaBean());

		return mapping.findForward("success");
	}


    private WelcomeActionBean getWelcomeActionBean() {

        BeanManager beanManager = CDI.current().getBeanManager();
//CDI uses an AnnotatedType object to read the annotations of a class

        AnnotatedType<WelcomeActionBean> type = beanManager.createAnnotatedType(WelcomeActionBean.class);


//The extension uses an InjectionTarget to delegate instantiation, dependency injection

//and lifecycle callbacks to the CDI container

        InjectionTarget<WelcomeActionBean> it = beanManager.createInjectionTarget(type);


//each instance needs its own CDI CreationalContext

        CreationalContext ctx = beanManager.createCreationalContext(null);


//instantiate the framework component and inject its dependencies

        WelcomeActionBean instance = it.produce(ctx);  //call the constructor

        it.inject(instance, ctx);  //call initializer methods and perform field injection

        it.postConstruct(instance);  //call the @PostConstruct method
        return instance;
    }

}
