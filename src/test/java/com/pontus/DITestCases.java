package com.pontus;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import com.gargoylesoftware.htmlunit.TextPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public abstract class DITestCases {

	private static final String X_PONTUS_SET_ID = "x-pontus-set-id";
	private static final String X_PONTUS_ID = "x-pontus-id";


	private URL deploymentURL;

    protected URL getDeploymentURL() {
        return deploymentURL;
    }

	@Before
	public void before() throws MalformedURLException {
		deploymentURL = new URL("http://localhost:8080/CDI/");
	}
	/**
	 * Given
	 * when request page again
	 * then no id should be set on response
	 * @throws java.io.IOException
	 * @throws InterruptedException
	 */
	@Test
	public void getPageWithoutId() throws IOException, InterruptedException {
		WebClient webClient = new WebClient();

		TextPage page = webClient.getPage(getDeploymentURL().toExternalForm() + "servlet");
		String idValue = page.getWebResponse().getResponseHeaderValue(X_PONTUS_SET_ID);
		Assert.assertThat(idValue, is(nullValue()));
		Assert.assertThat(page.getContent(), is(""));
	}

	/**
	 * Given a user p
	 * when request page
	 * then id should be set on response
	 * @throws java.io.IOException
	 * @throws InterruptedException
	 */
	@Test
	public void getPageWithLogin() throws IOException, InterruptedException {
		WebClient webClient = new WebClient();

		TextPage page = webClient.getPage(getDeploymentURL().toExternalForm() + "servlet?j_username=P");
		String idValue = page.getWebResponse().getResponseHeaderValue(X_PONTUS_SET_ID);
		Assert.assertThat(idValue, is(notNullValue()));
		Assert.assertThat(page.getContent(), is("<div>P</div>"));
	}

	/**
	 * Given a user p
	 * when request page again with id
	 * then no id should be set on response
	 * @throws java.io.IOException
	 * @throws InterruptedException
	 */
	@Test
	public void getPageWithId() throws IOException, InterruptedException {
		WebClient webClient = new WebClient();

		TextPage page = webClient.getPage(getDeploymentURL().toExternalForm() + "servlet?j_username=P");
		String idValue = page.getWebResponse().getResponseHeaderValue(X_PONTUS_SET_ID);

		webClient.addRequestHeader(X_PONTUS_ID, idValue);
		page = webClient.getPage(getDeploymentURL().toExternalForm() + "servlet");

		idValue = page.getWebResponse().getResponseHeaderValue(X_PONTUS_SET_ID);
		Assert.assertThat(idValue, is(nullValue()));
		Assert.assertThat(page.getContent(), is("<div>P</div>"));
	}


	/**
	 * Given a user p
	 * when login again with id set as header
	 * then new id should be set on response
	 * then old id should not be valid anymore
	 * @throws java.io.IOException
	 * @throws InterruptedException
	 */
	@Test
	public void loginTwice() throws IOException, InterruptedException {
		WebClient webClient = new WebClient();

		TextPage page = webClient.getPage(getDeploymentURL().toExternalForm() + "servlet?j_username=P");
		String idValue = page.getWebResponse().getResponseHeaderValue(X_PONTUS_SET_ID);

		webClient.addRequestHeader(X_PONTUS_ID, idValue);
		page = webClient.getPage(getDeploymentURL().toExternalForm() + "servlet?j_username=P");

		String newIdValue = page.getWebResponse().getResponseHeaderValue(X_PONTUS_SET_ID);
		Assert.assertThat(newIdValue, is(not(idValue)));
		Assert.assertThat(newIdValue, is(notNullValue()));

		Assert.assertThat(page.getContent(), is("<div>P</div>"));

		webClient.removeRequestHeader(X_PONTUS_ID);

		webClient.addRequestHeader(X_PONTUS_ID, idValue);
		page = webClient.getPage(getDeploymentURL().toExternalForm() + "servlet");

		newIdValue = page.getWebResponse().getResponseHeaderValue(X_PONTUS_SET_ID);
		Assert.assertThat(newIdValue, is(nullValue()));
		Assert.assertThat(page.getContent(), is(""));

	}

	/**
	 * Given a authorized user P
	 * when another user, C,  tries to authorize
	 * then new user C should get a new id
	 * @throws java.io.IOException
	 * @throws InterruptedException
	 */
	@Test
	public void loginTwoUsers() throws IOException, InterruptedException {
		WebClient webClient = new WebClient();

		TextPage page = webClient.getPage(getDeploymentURL().toExternalForm() +"servlet?j_username=P");
		String idValue = page.getWebResponse().getResponseHeaderValue(X_PONTUS_SET_ID);


		page = webClient.getPage(getDeploymentURL().toExternalForm()  +"servlet?j_username=C");
		String cIdValue = page.getWebResponse().getResponseHeaderValue(X_PONTUS_SET_ID);
		Assert.assertThat(cIdValue, is(not(idValue)));
		Assert.assertThat(cIdValue, is(notNullValue()));

		Assert.assertThat(page.getContent(), is("<div>C</div>"));
	}

	/**
	 * Given a authorized user P
	 * when accessing a page with an invalid id
	 * then no page content should be visible
	 * @throws java.io.IOException
	 * @throws InterruptedException
	 */
	@Test
	public void getPageInvalidId() throws IOException, InterruptedException {
		WebClient webClient = new WebClient();

		TextPage page = webClient.getPage(getDeploymentURL().toExternalForm() + "servlet?j_username=P");
		String idValue = page.getWebResponse().getResponseHeaderValue(X_PONTUS_SET_ID);

		webClient.addRequestHeader(X_PONTUS_ID, "invalid" + idValue);
		page = webClient.getPage(getDeploymentURL().toExternalForm() + "servlet");

		idValue = page.getWebResponse().getResponseHeaderValue(X_PONTUS_SET_ID);
		Assert.assertThat(idValue, is(nullValue()));
		Assert.assertThat(page.getContent(), is(""));

	}

	/**
	 * Given a authorized user P
	 * when accessing TheAPI restapi principal/name
	 * then principal name should be returned as content
	 * @throws java.io.IOException
	 * @throws InterruptedException
	 */
	@Test
	public void restAPIGetPrincipalName() throws IOException, InterruptedException {
		WebClient webClient = new WebClient();

		TextPage page = webClient.getPage(getDeploymentURL().toExternalForm() + "rest/s/principal/name" + "?j_username=P");
		String idValue = page.getWebResponse().getResponseHeaderValue(X_PONTUS_SET_ID);
		Assert.assertThat(idValue, is(notNullValue()));
		Assert.assertThat(page.getContent(), is("P"));
	}


	/**
	 * Given a authorized user P
	 * when accessing TheAPI restapi principal/name
	 * then principal name should be returned as content
	 * @throws java.io.IOException
	 * @throws InterruptedException
	 */
	@Test
	public void restAPIGetTheBean() throws IOException, InterruptedException {
		WebClient webClient = new WebClient();

		TextPage page = webClient.getPage(getDeploymentURL().toExternalForm() + "rest/s/principal/theBean");
		Assert.assertThat(page.getContent(), is("theString0"));
	}

	/**
	 * Given a authorized user P
	 * when accessing TheAPI restapi principal/name
	 * then principal name should be returned as content
	 * @throws java.io.IOException
	 * @throws InterruptedException
	 */
	@Test
	public void restAPIGetABean() throws IOException, InterruptedException {
		WebClient webClient = new WebClient();

		TextPage page = webClient.getPage(getDeploymentURL().toExternalForm() + "rest/s/principal/ABean");
		Assert.assertThat(page.getContent(), is("aString1"));
		//No new prototype for each request
		page = webClient.getPage(getDeploymentURL().toExternalForm() + "rest/s/principal/ABean");
		Assert.assertThat(page.getContent(), is("aString1"));

	}

	/**
	 * Given a authorized user P
	 * when accessing TheAPI restapi principal/name
	 * then principal name should be returned as content
	 * @throws java.io.IOException
	 * @throws InterruptedException
	 */
	@Test
	public void restAPIAPIGetAproperty() throws IOException, InterruptedException {
		WebClient webClient = new WebClient();

		TextPage page = webClient.getPage(getDeploymentURL().toExternalForm() + "rest/s/principal/aproperty");
		Assert.assertThat(page.getContent(), is("aString2"));
		//No new prototype for each request
		page = webClient.getPage(getDeploymentURL().toExternalForm() + "rest/s/principal/aproperty");
		Assert.assertThat(page.getContent(), is("aString2"));

	}

	/**
	 * Given a authorized user P
	 * when accessing TheAPI restapi principal/evaluteRules
	 * then false is returned (two rules, one true one false)
	 * @throws java.io.IOException
	 * @throws InterruptedException
	 */
	@Test
	public void restAPIEvaluateRules() throws IOException, InterruptedException {
		WebClient webClient = new WebClient();

		TextPage page = webClient.getPage(getDeploymentURL().toExternalForm() + "rest/s/principal/evaluateRules");
		Assert.assertThat(page.getContent(), is("false"));
	}

	/**
	 * Given a authorized user P
	 * when accessing TheAPI restapi principal/evaluteRule/arule2
	 * then false is returned
	 * @throws java.io.IOException
	 * @throws InterruptedException
	 */
	@Test
	public void restAPIEvaluateRuleARule2() throws IOException, InterruptedException {
		WebClient webClient = new WebClient();

		TextPage page = webClient.getPage(getDeploymentURL().toExternalForm() + "rest/s/principal/evaluateRule/arule2");
		Assert.assertThat(page.getContent(), is("false"));
	}

	/**
	 * Given a authorized user P
	 * when accessing TheAPI restapi principal/evaluteRule/arule
	 * then true is returned
	 * @throws java.io.IOException
	 * @throws InterruptedException
	 */
	@Test
	public void restAPIEvaluateRuleARule() throws IOException, InterruptedException {
		WebClient webClient = new WebClient();

		TextPage page = webClient.getPage(getDeploymentURL().toExternalForm() + "rest/s/principal/evaluateRule/arule");
		Assert.assertThat(page.getContent(), is("true"));
	}


	/**
	 * Given a authorized user P
	 * when accessing TheAPI restapi principal/name
	 * then principal name should be returned as content
	 * @throws java.io.IOException
	 * @throws InterruptedException
	 */
	@Test
	public void restAnotherAPIAPIGetTheBean() throws IOException, InterruptedException {
		WebClient webClient = new WebClient();

		TextPage page = webClient.getPage(getDeploymentURL().toExternalForm() + "rest/anotherAPI/principal/theBean");
		Assert.assertThat(page.getContent(), is("theString0"));
	}

	/**
	 * Given a authorized user P
	 * when accessing TheAPI restapi principal/name
	 * then principal name should be returned as content
	 * @throws java.io.IOException
	 * @throws InterruptedException
	 */
	@Test
	public void restAnotherAPIAPIGetABean() throws IOException, InterruptedException {
		WebClient webClient = new WebClient();

		TextPage page = webClient.getPage(getDeploymentURL().toExternalForm() + "rest/anotherAPI/principal/ABean");
		Assert.assertThat(page.getContent(), is("aString3"));
		//No new prototype for each request
		page = webClient.getPage(getDeploymentURL().toExternalForm() + "rest/anotherAPI/principal/ABean");
		Assert.assertThat(page.getContent(), is("aString3"));

	}

	/**
	 * Given a authorized user P
	 * when accessing TheAPI restapi principal/name
	 * then principal name should be returned as content
	 * @throws java.io.IOException
	 * @throws InterruptedException
	 */
	@Test
	public void restAnotherAPIAPIGetAproperty() throws IOException, InterruptedException {
		WebClient webClient = new WebClient();

		TextPage page = webClient.getPage(getDeploymentURL().toExternalForm() + "rest/anotherAPI/principal/aproperty");
		Assert.assertThat(page.getContent(), is("aString4"));
		//No new prototype for each request
		page = webClient.getPage(getDeploymentURL().toExternalForm() + "rest/anotherAPI/principal/aproperty");
		Assert.assertThat(page.getContent(), is("aString4"));

	}

	/**
	 * Given user P
	 * when accessing /Welcome.do
	 * then content returned should be theProperty and aProperty
	 * @throws java.io.IOException
	 * @throws InterruptedException
	 */
	@Test
	public void strutsSpringConfiguredAction() throws IOException, InterruptedException {
		WebClient webClient = new WebClient();

		HtmlPage page = webClient.getPage(getDeploymentURL().toExternalForm() + "Welcome.do");
		Assert.assertThat(page.getBody().getTextContent(), is("aString5theString0aString6"));

	}
}
