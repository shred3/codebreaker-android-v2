package edu.cnm.deepdive.codebreaker.service;

import android.content.Context;
import edu.cnm.deepdive.codebreaker.model.dao.UserDao;
import edu.cnm.deepdive.codebreaker.model.entity.User;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class UserRepository {

  private final Context context;
  private final UserDao userDao;

  public UserRepository(Context context) {
    this.context = context;
    userDao = CodebreakerDatabase.getInstance().getUserDao();
  }

  public Completable save(User user) {
    return userDao
        .update(user)
        .ignoreElement()
        .subscribeOn(Schedulers.io());
  }

  public Single<User> getOrCreate(String oauthKey, String displayName) {
    return userDao
        .select(oauthKey)
        .switchIfEmpty(
            Single
                .just(new User())
                .map((user) -> {
                  user.setOauthKey(oauthKey);
                  user.setDisplayName(displayName);
                  return user;
                })
                .flatMap((user) -> userDao
                    .insert(user)
                    .map((id) -> {
                      user.setId(id);
                      return user;
                    })
                )
        )
        .subscribeOn(Schedulers.io());
  }

}
