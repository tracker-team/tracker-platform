package com.platform.filter;

import com.google.common.base.Optional;
import com.platform.exceptions.TrackerException;
import com.tracker.model.RequestThreadContext;
import com.tracker.model.Tenant;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.message.internal.ReaderWriter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
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
    public static final String TENANT_HEADER = "X_TRACKER_TENANT";
    public static final String USER_HEADER = "X_TRACKER_USERNAME";
    public static final String CLIENT_HEADER = "X_TRACKER_CLIENT_ID";

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException{
        setUidForRequest(containerRequestContext);
        log.info("<<<<<<<<<< Got New Request >>>>>>>>>>>");
        MultivaluedMap<String, String> headers = containerRequestContext.getHeaders();
        log.info("METHOD : " + containerRequestContext.getMethod());
        log.info("HEADERS : "+headers);
        log.info("URL : " + containerRequestContext.getUriInfo().getAbsolutePath());
        log.info("BODY : " + getEntityBody(containerRequestContext));

        Optional<String> tenant = Optional.fromNullable(headers.getFirst(TENANT_HEADER));
        Optional<String> username = Optional.fromNullable(headers.getFirst(USER_HEADER));
        Optional<String> client = Optional.fromNullable(headers.getFirst(CLIENT_HEADER));
        if (!tenant.isPresent() || !username.isPresent() || !client.isPresent()) {
           throw new TrackerException("Headers are not valid , Headers required " + TENANT_HEADER
                    + "," + USER_HEADER + "," + CLIENT_HEADER, Response.Status.BAD_REQUEST);
        }
        RequestThreadContext.get().setClientId(client.get());
        RequestThreadContext.get().setUserName(username.get());
        RequestThreadContext.get().setTenant(Tenant.getTenant(tenant.get())
                .orElseThrow(()->new TrackerException("Tenant not found "+tenant.get(), Response.Status.BAD_REQUEST)));
    }

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

    private String getEntityBody(ContainerRequestContext requestContext) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = requestContext.getEntityStream();

        final StringBuilder b = new StringBuilder();
        try {
            ReaderWriter.writeTo(in, out);

            byte[] requestEntity = out.toByteArray();
            if (requestEntity.length == 0) {
                b.append("").append("\n");
            } else {
                b.append(new String(requestEntity)).append("\n");
            }
            requestContext.setEntityStream(new ByteArrayInputStream(requestEntity));

        } catch (IOException ex) {
            log.error("error while writing body");
        }
        return b.toString();
    }
}

