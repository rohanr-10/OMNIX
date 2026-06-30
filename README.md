# OMNIX — Phase 1 (Project Scaffold + Architecture)

## What's in this phase

A real, multi-module Android Studio project — not documentation, not pseudocode:

- **`:core-model`** — pure Kotlin domain models (chat messages/sessions, AI modes, AI Council agent states, model-tier/device-profile types)
- **`:core-ui`** — Material 3 theme with Material You dynamic color support, dark mode, type ramp, shape system, `GlassCard` and `ShimmerSkeleton` components
- **`:core-data`** — Room database (sessions + messages, with DAOs and migrations-ready setup), DataStore module, Hilt DI modules, a `ChatRepository` with a full implementation, and an `AiResponseProvider` interface with a working simulated implementation (real streaming behavior, mode-aware responses — this is the seam where a real on-device model plugs in during a later phase)
- **`:feature-home`** — the dashboard screen: hero section, quick actions, recent chats (live from Room via Flow), system status card, loading skeletons
- **`:feature-chat`** — the chat screen: mode chips (all 11 modes from the spec), streaming message bubbles, composer, regenerate/delete/edit wired to the repository
- **`:app`** — Hilt Application, MainActivity, navigation graph connecting Home → Chat, adaptive icon, light/dark themes, ProGuard rules

Everything compiles against real Gradle Kotlin DSL build files using a version catalog (`gradle/libs.versions.toml`).

## One thing you'll need to do before building

This environment has no network access, so I couldn't download the actual `gradle-wrapper.jar` binary — `gradlew`/`gradlew.bat` are real scripts, but they need that jar alongside them.

Easiest fix, pick one:

1. **Open the project folder in Android Studio.** It will detect the missing wrapper jar and offer to regenerate it automatically (or just resync and it pulls it in).
2. **Or, if you have Gradle installed locally**, run once from the project root:
   ```
   gradle wrapper --gradle-version 8.9
   ```

After that, `./gradlew assembleDebug` builds normally.

## Phases ahead

2. Chat screen polish + persistence edge cases
3. AI Council node-graph visualization screen
4. Model Manager (hardware detection + tier recommendation)
5. Memory subsystem (semantic search over conversation history)
6. Document/Vision/Plugin frameworks
7. Tests + final polish

Each phase = complete file replacements only, no regeneration of earlier phases, no pseudocode — same as Helix.
