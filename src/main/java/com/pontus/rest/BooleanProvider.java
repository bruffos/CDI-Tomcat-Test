package com.pontus.rest;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Created with IntelliJ IDEA.
 * User: pontus
 * Date: 11/5/12
 * Time: 5:44 PM
 * To change this template use File | Settings | File Templates.
 */
@Provider
@Produces("text/plain")
public class BooleanProvider implements MessageBodyWriter<Boolean> {
    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return aClass == Boolean.class;
    }

    @Override
    public long getSize(Boolean aBoolean, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return -1;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void writeTo(Boolean aBoolean, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> stringObjectMultivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        outputStream.write(aBoolean.toString().getBytes());
    }
}
