package com.pontus;

import com.pontus.rest.rule.ABean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(WeldRunner.class)
public class WeldTest {

    @Inject
    private ABean abean;

    @Test
    public void aTest() {
        Assert.assertThat(abean.getAProperty(), is("aString0"));
    }

}
