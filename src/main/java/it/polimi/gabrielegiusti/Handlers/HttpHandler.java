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

    public void setPostRequest(String postRequest) {

        this.getRequest = null;
        this.deleteRequest = null;
        this.postRequest = new HttpPost(postRequest);
    }

    public HttpGet getGetRequest() {
        return getRequest;
    }

    public void setGetRequest(String getRequest) {

        this.deleteRequest = null;
        this.postRequest = null;
        this.getRequest = new HttpGet(getRequest);
    }

    public HttpDelete getDeleteRequest() {
        return deleteRequest;
    }

    public void setDeleteRequest(String deleteRequest) {

        this.getRequest = null;
        this.postRequest = null;
        this.deleteRequest = new HttpDelete(deleteRequest);
    }

}
