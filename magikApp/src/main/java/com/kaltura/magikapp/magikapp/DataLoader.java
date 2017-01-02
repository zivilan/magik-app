package com.kaltura.magikapp.magikapp;

import android.util.Log;

import com.connect.backend.BaseResult;
import com.connect.backend.PrimitiveResult;
import com.connect.backend.SessionProvider;
import com.connect.backend.phoenix.PhoenixConfigs;
import com.connect.backend.phoenix.data.KalturaAssetListResponse;
import com.connect.backend.phoenix.data.KalturaMediaAsset;
import com.connect.backend.phoenix.data.PhoenixParser;
import com.connect.backend.phoenix.services.AssetService;
import com.connect.core.OnCompletion;
import com.connect.utils.APIOkRequestsExecutor;
import com.connect.utils.OnRequestCompletion;
import com.connect.utils.RequestBuilder;
import com.connect.utils.ResponseElement;

import java.util.ArrayList;

/**
 * Created by tehilarozin on 02/01/2017.
 */

public class DataLoader {

    public static void getChannelContent(final SessionProvider sessionProvider, final int channelId, final OnCompletion<ArrayList<KalturaMediaAsset>> content) {

        sessionProvider.getSessionToken(new OnCompletion<PrimitiveResult>() {
            @Override
            public void onComplete(PrimitiveResult response) {
                if (response.error == null) {
                    fetchContent(sessionProvider.baseUrl() + PhoenixConfigs.ApiPrefix, response.getResult(), channelId, content);
                }
            }
        });
    }

    private static void fetchContent(String baseUrl, String ks, int channelId, final OnCompletion<ArrayList<KalturaMediaAsset>> onReady) {
        RequestBuilder requestBuilder = AssetService.listByChannel(baseUrl, ks, channelId, null).completion(new OnRequestCompletion() {
            @Override
            public void onComplete(ResponseElement response) {
                ArrayList<KalturaMediaAsset> contentData = new ArrayList<KalturaMediaAsset>();
                if (response.isSuccess()) {
                    BaseResult res = PhoenixParser.parse(response.getResponse());
                    Log.d("DataLoader", "");
                    if(res.error == null){
                        contentData = ((KalturaAssetListResponse)res).objects;
                    }
                }

                if (onReady != null) {
                    onReady.onComplete(contentData);
                }
            }
        });
        APIOkRequestsExecutor.getSingleton().queue(requestBuilder.build());
    }
}
