package com.pontus;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;

public class WeldRunner extends BlockJUnit4ClassRunner {

    private final Class klass;
    private final WeldContainer weldContainer;

    public WeldRunner(final Class klass) throws InitializationError {
        super(klass);
        this.klass = klass;
        Weld weld = new Weld();
        weldContainer = weld.initialize();
    }

    @Override
    protected Object createTest() throws Exception {
        return weldContainer.instance().select(klass).get();
        //  return getWelcomeActionBean();

    }

    private Object getWelcomeActionBean() {

        BeanManager beanManager = weldContainer.getBeanManager();
//CDI uses an AnnotatedType object to read the annotations of a class

        AnnotatedType type = beanManager.createAnnotatedType(klass);


//The extension uses an InjectionTarget to delegate instantiation, dependency injection

//and lifecycle callbacks to the CDI container

        InjectionTarget it = beanManager.createInjectionTarget(type);


//each instance needs its own CDI CreationalContext

        CreationalContext ctx = beanManager.createCreationalContext(null);


//instantiate the framework component and inject its dependencies

        Object instance = it.produce(ctx);  //call the constructor

        it.inject(instance, ctx);  //call initializer methods and perform field injection

        it.postConstruct(instance);  //call the @PostConstruct method
        return instance;
    }
}
