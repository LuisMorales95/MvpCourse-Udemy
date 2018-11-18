package example.com.mvpcourse.login;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {
    @Provides
    public LoginActivityMVP.Presenter provideLoginActivityPresenter (LoginActivityMVP.Model model){
        return new LoginPresenter(model);
    }
    @Provides
    public LoginActivityMVP.Model provideLoginActivityModel(LoginRepository repository){
        return new LoginModel(repository);
    }
    @Provides
    public LoginRepository provideLoginRepository(){
        return new MemoryRepository();
    }
}
