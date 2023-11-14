package com.zone.android.mibus;

/**
 * Created by Inspiron on 08-06-2018.
 */

public class GeoApiContext {
    int QueryRateLimit;
    String ApiKey;

    public int getQueryRateLimit() {
        return QueryRateLimit;
    }

    public void setQueryRateLimit(int queryRateLimit) {
        QueryRateLimit = queryRateLimit;
    }

    public String getApiKey() {
        return ApiKey;
    }

    public void setApiKey(String apiKey) {
        ApiKey = apiKey;
    }

    public int getConnectTimeout() {
        return ConnectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        ConnectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return ReadTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        ReadTimeout = readTimeout;
    }

    public int getWriteTimeout() {
        return WriteTimeout;
    }

    public void setWriteTimeout(int writeTimeout) {
        WriteTimeout = writeTimeout;
    }

    int ConnectTimeout;
    int ReadTimeout;
    int WriteTimeout;

}
