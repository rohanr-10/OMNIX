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
import com.omnix.core.data.local.entity.ChatMessageEntity;
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
public final class ChatMessageDao_Impl implements ChatMessageDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ChatMessageEntity> __insertionAdapterOfChatMessageEntity;

  private final EntityDeletionOrUpdateAdapter<ChatMessageEntity> __deletionAdapterOfChatMessageEntity;

  private final EntityDeletionOrUpdateAdapter<ChatMessageEntity> __updateAdapterOfChatMessageEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllForSession;

  public ChatMessageDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfChatMessageEntity = new EntityInsertionAdapter<ChatMessageEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `chat_messages` (`id`,`sessionId`,`role`,`content`,`timestamp`,`mode`,`status`,`isEdited`,`parentMessageId`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ChatMessageEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getSessionId());
        statement.bindString(3, entity.getRole());
        statement.bindString(4, entity.getContent());
        statement.bindLong(5, entity.getTimestamp());
        statement.bindString(6, entity.getMode());
        statement.bindString(7, entity.getStatus());
        final int _tmp = entity.isEdited() ? 1 : 0;
        statement.bindLong(8, _tmp);
        if (entity.getParentMessageId() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getParentMessageId());
        }
      }
    };
    this.__deletionAdapterOfChatMessageEntity = new EntityDeletionOrUpdateAdapter<ChatMessageEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `chat_messages` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ChatMessageEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfChatMessageEntity = new EntityDeletionOrUpdateAdapter<ChatMessageEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `chat_messages` SET `id` = ?,`sessionId` = ?,`role` = ?,`content` = ?,`timestamp` = ?,`mode` = ?,`status` = ?,`isEdited` = ?,`parentMessageId` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ChatMessageEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getSessionId());
        statement.bindString(3, entity.getRole());
        statement.bindString(4, entity.getContent());
        statement.bindLong(5, entity.getTimestamp());
        statement.bindString(6, entity.getMode());
        statement.bindString(7, entity.getStatus());
        final int _tmp = entity.isEdited() ? 1 : 0;
        statement.bindLong(8, _tmp);
        if (entity.getParentMessageId() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getParentMessageId());
        }
        statement.bindString(10, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAllForSession = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM chat_messages WHERE sessionId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object upsert(final ChatMessageEntity message,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfChatMessageEntity.insert(message);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final ChatMessageEntity message,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfChatMessageEntity.handle(message);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final ChatMessageEntity message,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfChatMessageEntity.handle(message);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllForSession(final String sessionId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllForSession.acquire();
        int _argIndex = 1;
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
          __preparedStmtOfDeleteAllForSession.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ChatMessageEntity>> observeMessagesForSession(final String sessionId) {
    final String _sql = "SELECT * FROM chat_messages WHERE sessionId = ? ORDER BY timestamp ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, sessionId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"chat_messages"}, new Callable<List<ChatMessageEntity>>() {
      @Override
      @NonNull
      public List<ChatMessageEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfMode = CursorUtil.getColumnIndexOrThrow(_cursor, "mode");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfIsEdited = CursorUtil.getColumnIndexOrThrow(_cursor, "isEdited");
          final int _cursorIndexOfParentMessageId = CursorUtil.getColumnIndexOrThrow(_cursor, "parentMessageId");
          final List<ChatMessageEntity> _result = new ArrayList<ChatMessageEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ChatMessageEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpSessionId;
            _tmpSessionId = _cursor.getString(_cursorIndexOfSessionId);
            final String _tmpRole;
            _tmpRole = _cursor.getString(_cursorIndexOfRole);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpMode;
            _tmpMode = _cursor.getString(_cursorIndexOfMode);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final boolean _tmpIsEdited;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsEdited);
            _tmpIsEdited = _tmp != 0;
            final String _tmpParentMessageId;
            if (_cursor.isNull(_cursorIndexOfParentMessageId)) {
              _tmpParentMessageId = null;
            } else {
              _tmpParentMessageId = _cursor.getString(_cursorIndexOfParentMessageId);
            }
            _item = new ChatMessageEntity(_tmpId,_tmpSessionId,_tmpRole,_tmpContent,_tmpTimestamp,_tmpMode,_tmpStatus,_tmpIsEdited,_tmpParentMessageId);
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
  public Object getMessageById(final String messageId,
      final Continuation<? super ChatMessageEntity> $completion) {
    final String _sql = "SELECT * FROM chat_messages WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, messageId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ChatMessageEntity>() {
      @Override
      @Nullable
      public ChatMessageEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfMode = CursorUtil.getColumnIndexOrThrow(_cursor, "mode");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfIsEdited = CursorUtil.getColumnIndexOrThrow(_cursor, "isEdited");
          final int _cursorIndexOfParentMessageId = CursorUtil.getColumnIndexOrThrow(_cursor, "parentMessageId");
          final ChatMessageEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpSessionId;
            _tmpSessionId = _cursor.getString(_cursorIndexOfSessionId);
            final String _tmpRole;
            _tmpRole = _cursor.getString(_cursorIndexOfRole);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpMode;
            _tmpMode = _cursor.getString(_cursorIndexOfMode);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final boolean _tmpIsEdited;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsEdited);
            _tmpIsEdited = _tmp != 0;
            final String _tmpParentMessageId;
            if (_cursor.isNull(_cursorIndexOfParentMessageId)) {
              _tmpParentMessageId = null;
            } else {
              _tmpParentMessageId = _cursor.getString(_cursorIndexOfParentMessageId);
            }
            _result = new ChatMessageEntity(_tmpId,_tmpSessionId,_tmpRole,_tmpContent,_tmpTimestamp,_tmpMode,_tmpStatus,_tmpIsEdited,_tmpParentMessageId);
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

  @Override
  public Object searchInSession(final String sessionId, final String query,
      final Continuation<? super List<ChatMessageEntity>> $completion) {
    final String _sql = "\n"
            + "        SELECT * FROM chat_messages\n"
            + "        WHERE sessionId = ? AND content LIKE '%' || ? || '%'\n"
            + "        ORDER BY timestamp DESC\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, sessionId);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ChatMessageEntity>>() {
      @Override
      @NonNull
      public List<ChatMessageEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfMode = CursorUtil.getColumnIndexOrThrow(_cursor, "mode");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfIsEdited = CursorUtil.getColumnIndexOrThrow(_cursor, "isEdited");
          final int _cursorIndexOfParentMessageId = CursorUtil.getColumnIndexOrThrow(_cursor, "parentMessageId");
          final List<ChatMessageEntity> _result = new ArrayList<ChatMessageEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ChatMessageEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpSessionId;
            _tmpSessionId = _cursor.getString(_cursorIndexOfSessionId);
            final String _tmpRole;
            _tmpRole = _cursor.getString(_cursorIndexOfRole);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpMode;
            _tmpMode = _cursor.getString(_cursorIndexOfMode);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final boolean _tmpIsEdited;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsEdited);
            _tmpIsEdited = _tmp != 0;
            final String _tmpParentMessageId;
            if (_cursor.isNull(_cursorIndexOfParentMessageId)) {
              _tmpParentMessageId = null;
            } else {
              _tmpParentMessageId = _cursor.getString(_cursorIndexOfParentMessageId);
            }
            _item = new ChatMessageEntity(_tmpId,_tmpSessionId,_tmpRole,_tmpContent,_tmpTimestamp,_tmpMode,_tmpStatus,_tmpIsEdited,_tmpParentMessageId);
            _result.add(_item);
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
