package com.riders.testing.model;

import com.riders.testing.rest.api.SearchApiService;
import com.riders.testing.rest.client.SearchApiRestClient;
import com.riders.testing.utils.SearchUtil;

public class RequestResponse {

    public static Model call(SearchApiRestClient restClient, String... szArgs) {
        String szURL = SearchUtil.contrustURL(szArgs);
        return restClient.getRestAdapter().create(SearchApiService.class).getResults(szURL);
    }

    public static Model call(SearchApiRestClient restClient, String szArgs, String userIp) {
        String szURL = SearchUtil.contrustURL(szArgs);
        return restClient.getRestAdapter().create(SearchApiService.class).getResults(szURL, userIp);
    }

    public static Model call(SearchApiRestClient restClient, String szArgs, int iStart, String userIp) {
        String szURL = SearchUtil.contrustURL(szArgs);
        return restClient.getRestAdapter().create(SearchApiService.class).getResults(szURL, iStart, userIp);
    }

}
