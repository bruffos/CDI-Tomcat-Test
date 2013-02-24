package com.pontus.arquillian;

import com.pontus.SAMLSessionFilterHandlerTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;

import java.io.File;
import java.net.URL;

@RunWith(Arquillian.class)
public class WeldArquillianTest extends SAMLSessionFilterHandlerTest {
    private static final String WEBAPP_SRC = "src/main/webapp";

    @Deployment(testable = false)
    public static WebArchive createTestArchive() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "CDI.war");
        webArchive
                .addPackage("com.pontus")
                .addPackages(true, "com.pontus.config")
                .addPackages(true, "com.pontus.rest")
                .addPackages(true, "com.pontus.struts")
                .addAsWebResource(new File("src/main/webapp/pages/Welcome.jsp"), "pages/Welcome.jsp")
                .addAsManifestResource(new File(WEBAPP_SRC, "META-INF/context.xml"), "context.xml")
                .addAsWebInfResource("arquillian/beans-filtered.xml", "beans.xml")
                .addAsWebInfResource(new File(WEBAPP_SRC, "WEB-INF/struts-config.xml"), "struts-config.xml")
                .addAsWebInfResource(new File(WEBAPP_SRC, "WEB-INF/web.xml"), "web.xml");
        return webArchive;
    }

    @ArquillianResource
    private URL deploymentURL;

    @Override
    protected URL getDeploymentURL() {
        return deploymentURL;
    }
}
