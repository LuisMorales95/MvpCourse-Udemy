package example.com.mvpcourse.root;

import javax.inject.Singleton;

import dagger.Component;
import example.com.mvpcourse.login.LoginActivity;
import example.com.mvpcourse.login.LoginModule;
import example.com.mvpcourse.twitchapi.ApiModule;

@Singleton
@Component(modules = {ApplicationModule.class, LoginModule.class, ApiModule.class})
public interface ApplicationComponent {
    void inject(LoginActivity target);
}
