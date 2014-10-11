package org.zezutom.capstone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by tom on 11/10/2014.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieImageConfig implements Serializable {

    @JsonProperty("base_url")
    private String baseUrl;

    @JsonProperty("secure_base_url")
    private String secureBaseUrl;

    @JsonProperty("poster_sizes")
    private String[] posterSizes;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getSecureBaseUrl() {
        return secureBaseUrl;
    }

    public void setSecureBaseUrl(String secureBaseUrl) {
        this.secureBaseUrl = secureBaseUrl;
    }

    public String[] getPosterSizes() {
        return posterSizes;
    }

    public void setPosterSizes(String[] posterSizes) {
        this.posterSizes = posterSizes;
    }
}
