package com.platform.filter;

import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.message.internal.ReaderWriter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

@Slf4j
@Provider
public class RequestFilter implements ContainerRequestFilter {

    public void setUidForRequest(ContainerRequestContext containerRequestContext) {

        String transId = containerRequestContext.getHeaderString("TransactionId");

        DateFormat dateFormat = new SimpleDateFormat("MM-dd-HH-mm-ss-SSS");
        Calendar cal = Calendar.getInstance();
        String uId = dateFormat.format(cal.getTime()) + "-" + String.valueOf(UUID.randomUUID().getLeastSignificantBits()).substring(1, 3);

        if (transId != null) uId = transId + "-" + uId;
        String machineName = Machine.getName();
        if (machineName != null) uId = machineName + "-" + uId;

        org.slf4j.MDC.put("id", uId);
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        setUidForRequest(containerRequestContext);
        log.info("<<<<<<<<<< Got New Request >>>>>>>>>>>");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = containerRequestContext.getEntityStream();
        ReaderWriter.writeTo(in, out);
        byte[] requestEntity = out.toByteArray();
        log.info("METHOD : " + containerRequestContext.getMethod());
        log.info("URL : " + containerRequestContext.getUriInfo().getAbsolutePath());
        log.info("BODY : " + out);
        containerRequestContext.setEntityStream(new ByteArrayInputStream(requestEntity));
    }
}

