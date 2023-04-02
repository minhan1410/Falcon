package com.example.callapi;

import com.example.callapi.model.entity.ResponseData;
import com.example.callapi.repository.GameRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class CallApi implements CommandLineRunner {
    private static final String BASE_URL = "http://192.168.99.200:9669/falcon-fake-api/falcon-network/";
    private static OkHttpClient client = new OkHttpClient();

    private final GameRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(CallApi.class, args);
    }

    private RequestBody createRequestBody() {
        JSONArray metrics = new JSONArray();
        metrics.put("net_revenue");
        metrics.put("impression");
        metrics.put("net_ecpm");

        JSONArray dimensions = new JSONArray();
        dimensions.put("date");
        dimensions.put("app_bundle_id");
        dimensions.put("country");
        dimensions.put("platform");

        JSONObject jo = new JSONObject();
        jo.put("limit", 1000);
        jo.put("start", "2023-02-01T00:00:00");
        jo.put("end", "2023-02-28T23:59:59");
        jo.put("metrics", metrics);
        jo.put("dimensions", dimensions);

//        System.out.println(jo.toString());

        return RequestBody.create(
                MediaType.parse("application/json"), jo.toString());
    }

    private ResponseData getResponseData(String s) throws JsonProcessingException {
        JSONObject responseBody = new JSONObject(s);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(responseBody.get("data").toString(), ResponseData.class);
    }

    @Override
    public void run(String... args) throws Exception {
//        final RequestBody body = createRequestBody();
//        final Request request = new Request.Builder()
//                .url(BASE_URL).post(body)
//                .addHeader("x-userid", "vietcd")
//                .addHeader("x-authorization", "falcon-api")
//                .addHeader("Content-Type", "application/json")
//                .build();
//
//        final Call call = client.newCall(request);
//        final Response response = call.execute();
//        System.out.println(response);
//
//        ResponseData responseData = getResponseData(response.body().string());
//
//        List<Game> data = responseData.getData();
//        data.forEach(game -> {
//            TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
//            switch (game.getPlatform()) {
//                case "0": {
//                    game.setPlatform("Unknow");
//                    break;
//                }
//                case "1": {
//                    game.setPlatform("iOS");
//                    break;
//                }
//                case "2": {
//                    game.setPlatform("Android");
//                    break;
//                }
//                case "3": {
//                    game.setPlatform("");
//                    break;
//                }
//                case "4": {
//                    game.setPlatform("Linux");
//                    break;
//                }
//                case "5": {
//                    game.setPlatform("MacOS");
//                    break;
//                }
//                case "6": {
//                    game.setPlatform("Windows");
//                    break;
//                }
//                case "11": {
//                    game.setPlatform("tvOS");
//                    break;
//                }
//                case "12": {
//                    game.setPlatform("Roku");
//                    break;
//                }
//                case "13": {
//                    game.setPlatform("Amazon");
//                    break;
//                }
//                case "14": {
//                    game.setPlatform("Microsoft");
//                    break;
//                }
//                case "15": {
//                    game.setPlatform("Samsung Smart TV");
//                    break;
//                }
//                case "16": {
//                    game.setPlatform("LG Smart TV");
//                    break;
//                }
//                case "17": {
//                    game.setPlatform("Sony Playstation");
//                    break;
//                }
//                case "18": {
//                    game.setPlatform("Vizio");
//                    break;
//                }
//                case "19": {
//                    game.setPlatform("Philips Smart");
//                    break;
//                }
//                case "50": {
//                    game.setPlatform("Tizen");
//                    break;
//                }
//                case "51": {
//                    game.setPlatform("KaiOS");
//                    break;
//                }
//            }
//            game.setECPM(1000*(game.getRevenue())/ game.getImpression());
//        });
//        repository.saveAll(data);
    }
}
