package it.polimi.gabrielegiusti.Handlers;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.classic.HttpClient;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.classic.methods.HttpDelete;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.classic.methods.HttpGet;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.classic.methods.HttpPost;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.HttpClientBuilder;


public class HttpHandler {

    private HttpClient httpClient;
    private HttpPost postRequest;
    private HttpGet getRequest;
    private HttpDelete deleteRequest;

    public HttpHandler(){
        httpClient = HttpClientBuilder.create().build();
    }

    public HttpPost getPostRequest() {
        return postRequest;
    }

    public void setPostRequest(HttpPost postRequest) {
        this.postRequest = postRequest;
    }

    public HttpGet getGetRequest() {
        return getRequest;
    }

    public void setGetRequest(HttpGet getRequest) {
        this.getRequest = getRequest;
    }

    public HttpDelete getDeleteRequest() {
        return deleteRequest;
    }

    public void setDeleteRequest(HttpDelete deleteRequest) {
        this.deleteRequest = deleteRequest;
    }
}
