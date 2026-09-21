package training01;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class HttpJsonClient {


    public JsonNode getJson(String url)
            throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IOException(
                    "HTTP 请求失败，状态码：" + response.statusCode()
            );
        }

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readTree(response.body());
    }


    public JsonNode getJson(
            String url,
            String apiKey,
            String secretKey
    ) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("APCA-API-KEY-ID", apiKey)
                .header("APCA-API-SECRET-KEY", secretKey)
                .GET()
                .build();

        // TODO 1：发送请求，获得 HttpResponse<String>

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
        // TODO 2：检查 statusCode 是否为 2xx
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IOException(
                    "HTTP 请求失败，状态码：" + response.statusCode()
            );
        }

        // TODO 3：使用 ObjectMapper 解析 body，返回 JsonNode

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readTree(response.body());
    }


}


