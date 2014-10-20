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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.LogManager;
import java.util.stream.IntStream;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
@RunWith(JUnit4.class)
public class TwilioClientTest {

    @Test
    public void test() {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();
        LoggerFactory.getILoggerFactory();

        Client restClient = ClientBuilder.newBuilder().build();

        TwilioClient client = new TwilioClient(restClient);

        AtomicInteger c = new AtomicInteger(0);
        StringBuilder sb = new StringBuilder();
        IntStream.generate(() -> c.getAndIncrement() % 220).limit(300).forEach(i -> sb.append((char) (33 + i)));

        System.out.println(sb.toString());

        client.getAccount(System.getenv("TWILIO_ACCOUNT"), System.getenv("TWILIO_TOKEN"))
            .createSmsSender("+15145004374")
            //.createSMS("+15146604287")
            .createSMS("+15146604287")
            .withMessage(sb.toString())
            .send();
    }

}
