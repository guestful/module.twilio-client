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

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public class TwilioClientException extends RuntimeException {

    private final int status;
    private final int code;
    private final String description;

    TwilioClientException(int status, String message, int errCode, String errDescription) {
        super(status + ": " + message + "\n" + errCode + ": " + errDescription);
        this.status = status;
        this.code = errCode;
        this.description = errDescription;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public int getStatus() {
        return status;
    }

}

