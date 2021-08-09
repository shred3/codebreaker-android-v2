package edu.cnm.deepdive.codebreaker.service;

import android.app.Application;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import edu.cnm.deepdive.codebreaker.model.dao.GameDao;
import edu.cnm.deepdive.codebreaker.model.dao.GuessDao;
import edu.cnm.deepdive.codebreaker.model.dao.UserDao;
import edu.cnm.deepdive.codebreaker.model.entity.Game;
import edu.cnm.deepdive.codebreaker.model.entity.Guess;
import edu.cnm.deepdive.codebreaker.model.entity.User;
import edu.cnm.deepdive.codebreaker.service.CodebreakerDatabase.Converters;
import java.util.Date;
import java.util.UUID;

@Database(
    entities = {Game.class, Guess.class, User.class},
    version = 3,
    exportSchema = true
)
@TypeConverters({Converters.class})
public abstract class CodebreakerDatabase extends RoomDatabase {

  private static final String DB_NAME = "codebreaker-db";

  private static Application context;

  public static void setContext(Application context) {
    CodebreakerDatabase.context = context;
  }

  public static CodebreakerDatabase getInstance() {
    return InstanceHolder.INSTANCE;
  }

  public abstract GameDao getGameDao();

  public abstract GuessDao getGuessDao();

  public abstract UserDao getUserDao();

  private static class InstanceHolder {

    private static final CodebreakerDatabase INSTANCE =
        Room
            .databaseBuilder(context, CodebreakerDatabase.class, DB_NAME)
            .build();

  }

  public static class Converters {

    @TypeConverter
    public static Long toLong(Date value) {
      return (value != null) ? value.getTime() : null;
    }

    @TypeConverter
    public static Date toDate(Long value) {
      return (value != null) ? new Date(value) : null;
    }

    @TypeConverter
    public static String toString(UUID value) {
      return (value != null) ? value.toString() : null;
    }

    @TypeConverter
    public static UUID toUUID(String value) {
      return (value != null) ? UUID.fromString(value) : null;
    }

  }

}
