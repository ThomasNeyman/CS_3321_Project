package cs3321.client;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Connection {

    String  url;
    HttpClient client;
    static Connection instance = null;


    public void initialize(String url){
        this.url = url;
        client = HttpClient.newBuilder().build();

    }

    public HttpRequest createGet(String url){
        return HttpRequest.newBuilder()
                .uri(URI.create(url)).
                GET().
                build();
    }

    public boolean test() {
        HttpRequest request = createGet(url);
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body().equals("OK");
        } catch (IOException | InterruptedException ex) {
            return false;
        }
    }}
