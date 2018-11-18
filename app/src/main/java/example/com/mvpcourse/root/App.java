package example.com.mvpcourse.root;

import android.app.Application;

import example.com.mvpcourse.login.LoginModule;
import example.com.mvpcourse.twitchapi.ApiModule;


public class App extends Application{
    private ApplicationComponent component;
    
    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .loginModule(new LoginModule())
                .apiModule(new ApiModule())
                .build();
    }
    
    public ApplicationComponent getComponent() {
        return component;
    }
}
