package com.connect.backend.ovp.services;

import com.connect.backend.ovp.APIDefines;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.connect.backend.ovp.OvpRequestBuilder;
import com.connect.utils.MultiRequestBuilder;
import com.connect.utils.RequestBuilder;


/**
 * Created by tehilarozin on 13/11/2016.
 */

public class BaseEntryService extends OvpService {

    public static RequestBuilder entryInfo(String baseUrl, String ks, String entryId) {

        MultiRequestBuilder multiRequestBuilder = (MultiRequestBuilder) OvpService.getMultirequest(baseUrl, ks)
                .tag("mediaAsset-multi-get");

        return multiRequestBuilder.add(list(baseUrl, ks, entryId),
                getPlaybackContext(baseUrl, ks, entryId)
                /*getContextData(baseUrl, ks, entryId)*/);
    }

    public static OvpRequestBuilder list(String baseUrl, String ks, String entryId) {
        return new OvpRequestBuilder()
                .service("baseEntry")
                .action("list")
                .method("POST")
                .url(baseUrl)
                .tag("baseEntry-list")
                .params(getEntryListReqParams(ks, entryId));
    }

    private static JsonObject getEntryListReqParams(String ks, String entryId) {

        BaseEntryListParams baseEntryListParams = new BaseEntryListParams(ks);
        baseEntryListParams.filter.redirectFromEntryId = entryId;
        baseEntryListParams.responseProfile.fields = "id,name,dataUrl,duration,msDuration,flavorParamsIds,mediaType,type,tags";
        baseEntryListParams.responseProfile.type = APIDefines.ResponseProfileType.IncludeFields;

        return new Gson().toJsonTree(baseEntryListParams).getAsJsonObject();
    }

    public static OvpRequestBuilder getContextData(String baseUrl, String ks, String entryId) {
        JsonObject params = new JsonObject();
        params.addProperty("entryId", entryId);
        params.addProperty("ks", ks);

        JsonObject contextDataParams = new JsonObject();
        contextDataParams.addProperty("objectType","KalturaContextDataParams");
        params.add("contextDataParams", contextDataParams);

        return new OvpRequestBuilder().service("baseEntry")
                .action("getContextData")
                .method("POST")
                .url(baseUrl)
                .tag("baseEntry-getContextData")
                .params(params);
    }

    public static OvpRequestBuilder getPlaybackContext(String baseUrl, String ks, String entryId) {
        JsonObject params = new JsonObject();
        params.addProperty("entryId", entryId);
        params.addProperty("ks", ks);
        JsonObject contextDataParams = new JsonObject();
        contextDataParams.addProperty("objectType","KalturaContextDataParams");
        params.add("contextDataParams", contextDataParams);

        return new OvpRequestBuilder().service("baseEntry")
                .action("getPlaybackContext")
                .method("POST")
                .url(baseUrl)
                .tag("baseEntry-getPlaybackContext")
                .params(params);
    }



    static class BaseEntryListParams {
        String ks;
        Filter filter;
        ResponseProfile responseProfile;

        public BaseEntryListParams(String ks) {
            this.ks = ks;
            this.filter = new Filter();
            this.responseProfile = new ResponseProfile();
        }

        class Filter {
            String redirectFromEntryId;
        }
    }

}
