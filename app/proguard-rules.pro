# Hilt / Dagger generated code is referenced reflectively in places; keep generated components.
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.lifecycle.HiltViewModel

# Room
-keep class androidx.room.** { *; }

# Keep OMNIX domain models intact (used across module boundaries and potentially reflection-based serialization later)
-keep class com.omnix.core.model.** { *; }
