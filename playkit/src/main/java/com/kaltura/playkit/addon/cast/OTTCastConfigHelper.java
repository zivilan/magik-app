package com.kaltura.playkit.addon.cast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by itanbarpeled on 14/12/2016.
 */


class OTTCastConfigHelper extends CastConfigHelper {


    @Override
    protected String getSessionInfo(CastInfo castInfo) {

        return castInfo.getInitObject();

    }


    @Override
    protected void setProxyData(JSONObject flashVars, String initObject, String fileFormat,
                                String entryId) {


        JSONObject proxyData = new JSONObject();
        JSONObject configObject = new JSONObject();


        try {

            configObject.put("flavorassets", new JSONObject().put("filters", new JSONObject().put("include", new JSONObject().put("Format", new JSONArray().put(fileFormat)))));
            configObject.put("baseentry", new JSONObject().put("vars", new JSONObject().put("isTrailer", "" + false)));
            proxyData.put("config", configObject);

            proxyData.put("initObj", new JSONObject(initObject));

            proxyData.put("MediaID", entryId);
            proxyData.put("iMediaID", entryId);
            proxyData.put("withDynamic", false);
            proxyData.put("mediaType", 0);

            flashVars.put("proxyData", proxyData);

        } catch(JSONException e) {
            e.printStackTrace();
        }



    }


}

