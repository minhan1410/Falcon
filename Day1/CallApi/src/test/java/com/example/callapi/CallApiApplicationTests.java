package com.example.callapi;

import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class CallApiApplicationTests {
    private static final String BASE_URL = "192.168.99.200:9669/falcon-fake-api/falcon-network/";

    static OkHttpClient client = new OkHttpClient();

    @Test
    void contextLoads() {
    }

    @Test
    public void whenSendPostRequest_thenCorrect()
            throws IOException {
        final RequestBody formBody = new FormBody.Builder()
                .add("limit", "1000")
                .add("start", "2023-02-01T00:00:00")
                .add("end", "2023-02-28T23:59:59")
                .add("metrics", "[\n" +
                        "        \"net_revenue\",\n" +
                        "        \"impression\",\n" +
                        "        \"net_ecpm\"\n" +
                        "    ]" )
                .add("dimensions","[\n" +
                        "        \"date\",\n" +
                        "        \"app_bundle_id\",\n" +
                        "        \"country\",\n" +
                        "        \"platform\"\n" +
                        "    ]")
                .build();

        final Request request = new Request.Builder()
                .url(BASE_URL)
                .post(formBody).build();

        final Call call = client.newCall(request);
        final Response response = call.execute();
        System.out.println(response);
    }
}
