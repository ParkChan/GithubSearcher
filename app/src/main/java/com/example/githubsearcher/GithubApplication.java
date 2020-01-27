package com.example.githubsearcher;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class GithubApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //디버그 모드에서만 동작하도록 설정
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }
}
