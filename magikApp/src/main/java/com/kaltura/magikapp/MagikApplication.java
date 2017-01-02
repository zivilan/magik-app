package com.kaltura.magikapp;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.connect.backend.PrimitiveResult;
import com.connect.backend.SessionProvider;
import com.connect.backend.magikapp.data.Configuration;
import com.connect.backend.magikapp.services.ConfigurationService;
import com.connect.core.OnCompletion;
import com.connect.utils.APIOkRequestsExecutor;
import com.connect.utils.OnRequestCompletion;
import com.connect.utils.RequestBuilder;
import com.connect.utils.ResponseElement;
import com.google.gson.Gson;
import com.kaltura.playkit.backend.phoenix.OttSessionProvider;

import static com.kaltura.magikapp.MockParams.PhoenixBaseUrl;

/**
 * Created by tehilarozin on 02/01/2017.
 */

public class MagikApplication extends Application {
    private static MagikApplication self;
    private Configuration appConfiguration;
    private ConfigurationsReady configurationsReady;
    private static OttSessionProvider ottSessionProvider;

    public SessionProvider getSessionProvider() {
        return ottSessionProvider;
    }

    public interface ConfigurationsReady{
        void onReady(Configuration configuration);
        void onLoadFailure();
    }

    public static MagikApplication get() {
        return self;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        self = this;

        AppLoader.loadConfigurations(new OnCompletion<Configuration>() {
            @Override
            public void onComplete(Configuration configuration) {
                if (configuration.error == null) {
                    appConfiguration = configuration;

                    AppLoader.startAnonymousSession(new OnCompletion<PrimitiveResult>() {
                        @Override
                        public void onComplete(PrimitiveResult response) {
                            if(configurationsReady != null){
                                if(response.error==null) {
                                    configurationsReady.onReady(appConfiguration);
                                } else {
                                    Log.e("MagikApplication", "failed to create session");
                                    configurationsReady.onLoadFailure();
                                }
                            }
                        }
                    });

                } else {
                    Log.e("MagikApplication", "failed to retrieve applications configurations: " + configuration.error.getMessage());
                    Toast.makeText(getApplicationContext(), "Failed to load necessary data, application will end. ", Toast.LENGTH_LONG).show();

                    if(configurationsReady != null){
                        configurationsReady.onLoadFailure();
                    }
                }

                //testWaitCount.countDown();

                //startFirstActivity();
            }
        });
        /*testWaitCount = new CountDownLatch(1);
        try {
            testWaitCount.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    public void registerToConfigurationReady(ConfigurationsReady listener){
        this.configurationsReady = listener;
        if(appConfiguration != null){
            configurationsReady.onReady(appConfiguration);
        }
    }

    public Configuration getConfigurations(){
        return appConfiguration;
    }

    static private class AppLoader {


        static void loadConfigurations(final OnCompletion<Configuration> completion) {
            RequestBuilder requestBuilder = ConfigurationService.fetch(BuildConfig.APPLICATION_ID.replace(".", "-")).completion(new OnRequestCompletion() {
                @Override
                public void onComplete(ResponseElement response) {
                    if (response.isSuccess()) {
                        Configuration configuration = new Gson().fromJson(response.getResponse(), Configuration.class);
                        if (completion != null) {
                            completion.onComplete(configuration);
                        }
                    }
                }
            });
            APIOkRequestsExecutor.getSingleton().queue(requestBuilder.build());
        }

        static void startAnonymousSession(final OnCompletion<PrimitiveResult> completion){
            ottSessionProvider = new OttSessionProvider(PhoenixBaseUrl, 1225);
            ottSessionProvider.startAnonymousSession(null, new OnCompletion<PrimitiveResult>() {
                @Override
                public void onComplete(PrimitiveResult response) {
                    if(completion != null){
                        completion.onComplete(response);
                    }
                }
            });
        }
    }
}
