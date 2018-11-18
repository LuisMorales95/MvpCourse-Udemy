package example.com.mvpcourse.login;

public interface LoginRepository {
    User getUser();
    void saveUser(User user);
}
