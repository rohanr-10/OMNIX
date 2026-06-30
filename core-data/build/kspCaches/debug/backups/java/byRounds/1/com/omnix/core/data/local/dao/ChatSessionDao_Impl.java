package com.omnix.core.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.omnix.core.data.local.entity.ChatSessionEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ChatSessionDao_Impl implements ChatSessionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ChatSessionEntity> __insertionAdapterOfChatSessionEntity;

  private final EntityDeletionOrUpdateAdapter<ChatSessionEntity> __deletionAdapterOfChatSessionEntity;

  private final EntityDeletionOrUpdateAdapter<ChatSessionEntity> __updateAdapterOfChatSessionEntity;

  private final SharedSQLiteStatement __preparedStmtOfSetPinned;

  private final SharedSQLiteStatement __preparedStmtOfSetArchived;

  public ChatSessionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfChatSessionEntity = new EntityInsertionAdapter<ChatSessionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `chat_sessions` (`id`,`title`,`createdAt`,`updatedAt`,`pinned`,`archived`,`messageCount`,`lastMessagePreview`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ChatSessionEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindLong(3, entity.getCreatedAt());
        statement.bindLong(4, entity.getUpdatedAt());
        final int _tmp = entity.getPinned() ? 1 : 0;
        statement.bindLong(5, _tmp);
        final int _tmp_1 = entity.getArchived() ? 1 : 0;
        statement.bindLong(6, _tmp_1);
        statement.bindLong(7, entity.getMessageCount());
        statement.bindString(8, entity.getLastMessagePreview());
      }
    };
    this.__deletionAdapterOfChatSessionEntity = new EntityDeletionOrUpdateAdapter<ChatSessionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `chat_sessions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ChatSessionEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfChatSessionEntity = new EntityDeletionOrUpdateAdapter<ChatSessionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `chat_sessions` SET `id` = ?,`title` = ?,`createdAt` = ?,`updatedAt` = ?,`pinned` = ?,`archived` = ?,`messageCount` = ?,`lastMessagePreview` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ChatSessionEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindLong(3, entity.getCreatedAt());
        statement.bindLong(4, entity.getUpdatedAt());
        final int _tmp = entity.getPinned() ? 1 : 0;
        statement.bindLong(5, _tmp);
        final int _tmp_1 = entity.getArchived() ? 1 : 0;
        statement.bindLong(6, _tmp_1);
        statement.bindLong(7, entity.getMessageCount());
        statement.bindString(8, entity.getLastMessagePreview());
        statement.bindString(9, entity.getId());
      }
    };
    this.__preparedStmtOfSetPinned = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE chat_sessions SET pinned = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSetArchived = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE chat_sessions SET archived = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object upsert(final ChatSessionEntity session,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfChatSessionEntity.insert(session);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final ChatSessionEntity session,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfChatSessionEntity.handle(session);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final ChatSessionEntity session,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfChatSessionEntity.handle(session);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object setPinned(final String sessionId, final boolean pinned,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetPinned.acquire();
        int _argIndex = 1;
        final int _tmp = pinned ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindString(_argIndex, sessionId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfSetPinned.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object setArchived(final String sessionId, final boolean archived,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetArchived.acquire();
        int _argIndex = 1;
        final int _tmp = archived ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindString(_argIndex, sessionId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfSetArchived.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ChatSessionEntity>> observeActiveSessions() {
    final String _sql = "SELECT * FROM chat_sessions WHERE archived = 0 ORDER BY pinned DESC, updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"chat_sessions"}, new Callable<List<ChatSessionEntity>>() {
      @Override
      @NonNull
      public List<ChatSessionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfPinned = CursorUtil.getColumnIndexOrThrow(_cursor, "pinned");
          final int _cursorIndexOfArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "archived");
          final int _cursorIndexOfMessageCount = CursorUtil.getColumnIndexOrThrow(_cursor, "messageCount");
          final int _cursorIndexOfLastMessagePreview = CursorUtil.getColumnIndexOrThrow(_cursor, "lastMessagePreview");
          final List<ChatSessionEntity> _result = new ArrayList<ChatSessionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ChatSessionEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpPinned;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfPinned);
            _tmpPinned = _tmp != 0;
            final boolean _tmpArchived;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfArchived);
            _tmpArchived = _tmp_1 != 0;
            final int _tmpMessageCount;
            _tmpMessageCount = _cursor.getInt(_cursorIndexOfMessageCount);
            final String _tmpLastMessagePreview;
            _tmpLastMessagePreview = _cursor.getString(_cursorIndexOfLastMessagePreview);
            _item = new ChatSessionEntity(_tmpId,_tmpTitle,_tmpCreatedAt,_tmpUpdatedAt,_tmpPinned,_tmpArchived,_tmpMessageCount,_tmpLastMessagePreview);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ChatSessionEntity>> observeArchivedSessions() {
    final String _sql = "SELECT * FROM chat_sessions WHERE archived = 1 ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"chat_sessions"}, new Callable<List<ChatSessionEntity>>() {
      @Override
      @NonNull
      public List<ChatSessionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfPinned = CursorUtil.getColumnIndexOrThrow(_cursor, "pinned");
          final int _cursorIndexOfArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "archived");
          final int _cursorIndexOfMessageCount = CursorUtil.getColumnIndexOrThrow(_cursor, "messageCount");
          final int _cursorIndexOfLastMessagePreview = CursorUtil.getColumnIndexOrThrow(_cursor, "lastMessagePreview");
          final List<ChatSessionEntity> _result = new ArrayList<ChatSessionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ChatSessionEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpPinned;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfPinned);
            _tmpPinned = _tmp != 0;
            final boolean _tmpArchived;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfArchived);
            _tmpArchived = _tmp_1 != 0;
            final int _tmpMessageCount;
            _tmpMessageCount = _cursor.getInt(_cursorIndexOfMessageCount);
            final String _tmpLastMessagePreview;
            _tmpLastMessagePreview = _cursor.getString(_cursorIndexOfLastMessagePreview);
            _item = new ChatSessionEntity(_tmpId,_tmpTitle,_tmpCreatedAt,_tmpUpdatedAt,_tmpPinned,_tmpArchived,_tmpMessageCount,_tmpLastMessagePreview);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getSessionById(final String sessionId,
      final Continuation<? super ChatSessionEntity> $completion) {
    final String _sql = "SELECT * FROM chat_sessions WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, sessionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ChatSessionEntity>() {
      @Override
      @Nullable
      public ChatSessionEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfPinned = CursorUtil.getColumnIndexOrThrow(_cursor, "pinned");
          final int _cursorIndexOfArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "archived");
          final int _cursorIndexOfMessageCount = CursorUtil.getColumnIndexOrThrow(_cursor, "messageCount");
          final int _cursorIndexOfLastMessagePreview = CursorUtil.getColumnIndexOrThrow(_cursor, "lastMessagePreview");
          final ChatSessionEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpPinned;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfPinned);
            _tmpPinned = _tmp != 0;
            final boolean _tmpArchived;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfArchived);
            _tmpArchived = _tmp_1 != 0;
            final int _tmpMessageCount;
            _tmpMessageCount = _cursor.getInt(_cursorIndexOfMessageCount);
            final String _tmpLastMessagePreview;
            _tmpLastMessagePreview = _cursor.getString(_cursorIndexOfLastMessagePreview);
            _result = new ChatSessionEntity(_tmpId,_tmpTitle,_tmpCreatedAt,_tmpUpdatedAt,_tmpPinned,_tmpArchived,_tmpMessageCount,_tmpLastMessagePreview);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
