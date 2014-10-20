/**
 * Copyright (C) 2013 Guestful (info@guestful.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.guestful.client.twilio;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public class TwilioClient {

    private static final Logger LOGGER = Logger.getLogger(TwilioClient.class.getName());

    private final Client client;
    private final WebTarget target;
    private boolean enabled = true;

    public TwilioClient() {
        this(ClientBuilder.newClient());
    }

    public TwilioClient(Client restClient) {
        this.client = restClient;
        this.target = buildWebTarget();
    }

    public Client getClient() {
        return client;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public TwilioAccount getAccount(String accountSid, String authToken) {
        return new TwilioAccount(this, accountSid, authToken);
    }

    protected WebTarget buildWebTarget() {
        return getClient().target("https://api.twilio.com/2010-04-01");
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    Response request(TwilioAccount account, String method, String path, Map<String, String> params) {
        if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.finest(method + " " + path + " : " + params);
        }
        return isEnabled() ?
            target
                .path(path)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder().encodeToString((account.getAccountSid() + ":" + account.getAuthToken()).getBytes(StandardCharsets.UTF_8)))
                .method(method, Entity.form(new MultivaluedHashMap<>(params))) :
            Response.status(Response.Status.CREATED).build();
    }

}
