package com.omnix.core.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.omnix.core.data.local.dao.ChatMessageDao;
import com.omnix.core.data.local.dao.ChatMessageDao_Impl;
import com.omnix.core.data.local.dao.ChatSessionDao;
import com.omnix.core.data.local.dao.ChatSessionDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class OmnixDatabase_Impl extends OmnixDatabase {
  private volatile ChatSessionDao _chatSessionDao;

  private volatile ChatMessageDao _chatMessageDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `chat_sessions` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `pinned` INTEGER NOT NULL, `archived` INTEGER NOT NULL, `messageCount` INTEGER NOT NULL, `lastMessagePreview` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `chat_messages` (`id` TEXT NOT NULL, `sessionId` TEXT NOT NULL, `role` TEXT NOT NULL, `content` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `mode` TEXT NOT NULL, `status` TEXT NOT NULL, `isEdited` INTEGER NOT NULL, `parentMessageId` TEXT, PRIMARY KEY(`id`), FOREIGN KEY(`sessionId`) REFERENCES `chat_sessions`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_chat_messages_sessionId` ON `chat_messages` (`sessionId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'f0e52b043874a2af3e3dad6008099f51')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `chat_sessions`");
        db.execSQL("DROP TABLE IF EXISTS `chat_messages`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsChatSessions = new HashMap<String, TableInfo.Column>(8);
        _columnsChatSessions.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatSessions.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatSessions.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatSessions.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatSessions.put("pinned", new TableInfo.Column("pinned", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatSessions.put("archived", new TableInfo.Column("archived", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatSessions.put("messageCount", new TableInfo.Column("messageCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatSessions.put("lastMessagePreview", new TableInfo.Column("lastMessagePreview", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysChatSessions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesChatSessions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoChatSessions = new TableInfo("chat_sessions", _columnsChatSessions, _foreignKeysChatSessions, _indicesChatSessions);
        final TableInfo _existingChatSessions = TableInfo.read(db, "chat_sessions");
        if (!_infoChatSessions.equals(_existingChatSessions)) {
          return new RoomOpenHelper.ValidationResult(false, "chat_sessions(com.omnix.core.data.local.entity.ChatSessionEntity).\n"
                  + " Expected:\n" + _infoChatSessions + "\n"
                  + " Found:\n" + _existingChatSessions);
        }
        final HashMap<String, TableInfo.Column> _columnsChatMessages = new HashMap<String, TableInfo.Column>(9);
        _columnsChatMessages.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("sessionId", new TableInfo.Column("sessionId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("role", new TableInfo.Column("role", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("content", new TableInfo.Column("content", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("mode", new TableInfo.Column("mode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("isEdited", new TableInfo.Column("isEdited", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatMessages.put("parentMessageId", new TableInfo.Column("parentMessageId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysChatMessages = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysChatMessages.add(new TableInfo.ForeignKey("chat_sessions", "CASCADE", "NO ACTION", Arrays.asList("sessionId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesChatMessages = new HashSet<TableInfo.Index>(1);
        _indicesChatMessages.add(new TableInfo.Index("index_chat_messages_sessionId", false, Arrays.asList("sessionId"), Arrays.asList("ASC")));
        final TableInfo _infoChatMessages = new TableInfo("chat_messages", _columnsChatMessages, _foreignKeysChatMessages, _indicesChatMessages);
        final TableInfo _existingChatMessages = TableInfo.read(db, "chat_messages");
        if (!_infoChatMessages.equals(_existingChatMessages)) {
          return new RoomOpenHelper.ValidationResult(false, "chat_messages(com.omnix.core.data.local.entity.ChatMessageEntity).\n"
                  + " Expected:\n" + _infoChatMessages + "\n"
                  + " Found:\n" + _existingChatMessages);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "f0e52b043874a2af3e3dad6008099f51", "9bda8d9fc59e2c65a14ae33ca79b85d2");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "chat_sessions","chat_messages");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `chat_sessions`");
      _db.execSQL("DELETE FROM `chat_messages`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ChatSessionDao.class, ChatSessionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ChatMessageDao.class, ChatMessageDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public ChatSessionDao chatSessionDao() {
    if (_chatSessionDao != null) {
      return _chatSessionDao;
    } else {
      synchronized(this) {
        if(_chatSessionDao == null) {
          _chatSessionDao = new ChatSessionDao_Impl(this);
        }
        return _chatSessionDao;
      }
    }
  }

  @Override
  public ChatMessageDao chatMessageDao() {
    if (_chatMessageDao != null) {
      return _chatMessageDao;
    } else {
      synchronized(this) {
        if(_chatMessageDao == null) {
          _chatMessageDao = new ChatMessageDao_Impl(this);
        }
        return _chatMessageDao;
      }
    }
  }
}
