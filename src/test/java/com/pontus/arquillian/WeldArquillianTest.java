package com.pontus.arquillian;

import com.pontus.SAMLSessionFilterHandlerTest;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;

@RunWith(Arquillian.class)
public class WeldArquillianTest extends SAMLSessionFilterHandlerTest {

    @Deployment(testable = false)
    public static WebArchive createRestArchive() {
        return ShrinkWrap.create(WebArchive.class, "CDI.war")
                .addPackages(true, "com.pontus")
                .addAsManifestResource("META-INF/context.xml", "context.xml")
                .addAsWebInfResource("WEB-INF/beans.xml", "beans.xml")
                .addAsWebInfResource("WEB-INF/struts-config.xml", "struts-config.xml")
                .addAsWebInfResource("WEB-INF/web.xml", "web.xml");
    }

    @ArquillianResource
    private URL deploymentURL;


    @Test
    public void aTest() {
        Assert.fail();
    }
}
