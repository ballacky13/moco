package com.github.dreamhead.moco.extractor;

import org.jboss.netty.handler.codec.http.HttpRequest;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParamRequestExtractorTest {
    private ParamRequestExtractor extractor;
    private HttpRequest request;

    @Before
    public void setUp() throws Exception {
        extractor = new ParamRequestExtractor("param");
        request = mock(HttpRequest.class);
    }

    @Test
    public void should_return_null_if_uri_has_no_parameter() {
        when(request.getUri()).thenReturn("/foo");
        assertThat(extractor.extract(request), nullValue());
    }

    @Test
    public void should_return_parameter_value_if_uri_has_same_parameter() {
        when(request.getUri()).thenReturn("/foo?param=bar");
        assertThat(extractor.extract(request), is("bar"));
    }

    @Test
    public void should_extract_parameter_value_from_multiple_parameters() {
        when(request.getUri()).thenReturn("/foo?param=bar&param2=bar2");
        assertThat(extractor.extract(request), is("bar"));
    }

    @Test
    public void should_return_null_if_uri_has_no_same_parameter() {
        when(request.getUri()).thenReturn("/foo?param2=bar");
        assertThat(extractor.extract(request), nullValue());
    }
}
