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

import javax.json.JsonObject;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Response;
import java.util.LinkedHashMap;
import java.util.Map;

public class TwilioSmsSender {

    private final TwilioAccount account;
    private final String from;

    TwilioSmsSender(TwilioAccount account, String from) {
        this.account = account;
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public TwilioAccount getAccount() {
        return account;
    }

    public TwilioSms createSMS(String to) {
        return new TwilioSms(this, to);
    }

    @SuppressWarnings("unchecked")
    void send(String to, String body) {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("Body", body);
        params.put("To", to);
        params.put("From", getFrom());
        Response response = getAccount()
            .getClient()
            .request(getAccount(), HttpMethod.POST, "Accounts/" + getAccount().getAccountSid() + "/Messages.json", params);
        try {
            if (response.getStatus() != 201) {
                if (Response.Status.Family.familyOf(response.getStatus()) == Response.Status.Family.CLIENT_ERROR) {
                    JsonObject json = response.readEntity(JsonObject.class);
                    throw new TwilioClientException(
                        response.getStatus(),
                        json.getString("message", ""),
                        json.getInt("code", 0),
                        json.getString("more_info", ""));
                } else {
                    throw new TwilioException("Unable to send Twilio SMS: " + response.getStatus() + ", Account: " + getAccount().getAccountSid() + "\n" + response.readEntity(String.class) + "SMS:\n" + params);
                }
            }
        } finally {
            response.close();
        }
    }

    @Override
    public String toString() {
        return from;
    }

}
