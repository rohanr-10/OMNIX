# OMNIX — Phase 1 (Project Scaffold + Architecture)

<<<<<<< HEAD
OMNIX is a modular, local-first AI assistant for Android built using Jetpack Compose. It is designed around a multi-agent architecture where specialized AI components collaborate to produce high-quality responses while maintaining a privacy-focused, on-device execution model.
=======
## What's in this phase
>>>>>>> 19678db (feat: integrate Phase 5 Inference Engine)

A real, multi-module Android Studio project — not documentation, not pseudocode:

- **`:core-model`** — pure Kotlin domain models (chat messages/sessions, AI modes, AI Council agent states, model-tier/device-profile types)
- **`:core-ui`** — Material 3 theme with Material You dynamic color support, dark mode, type ramp, shape system, `GlassCard` and `ShimmerSkeleton` components
- **`:core-data`** — Room database (sessions + messages, with DAOs and migrations-ready setup), DataStore module, Hilt DI modules, a `ChatRepository` with a full implementation, and an `AiResponseProvider` interface with a working simulated implementation (real streaming behavior, mode-aware responses — this is the seam where a real on-device model plugs in during a later phase)
- **`:feature-home`** — the dashboard screen: hero section, quick actions, recent chats (live from Room via Flow), system status card, loading skeletons
- **`:feature-chat`** — the chat screen: mode chips (all 11 modes from the spec), streaming message bubbles, composer, regenerate/delete/edit wired to the repository
- **`:app`** — Hilt Application, MainActivity, navigation graph connecting Home → Chat, adaptive icon, light/dark themes, ProGuard rules

Everything compiles against real Gradle Kotlin DSL build files using a version catalog (`gradle/libs.versions.toml`).

## One thing you'll need to do before building

<<<<<<< HEAD
- Multi-agent reasoning
- Local AI model execution
- Intelligent model management
- Long-term conversation memory
- Vision and document understanding
- Autonomous task execution
- Offline-first operation

The architecture is intentionally modular, allowing new capabilities to be integrated without major changes to the existing codebase.
=======
This environment has no network access, so I couldn't download the actual `gradle-wrapper.jar` binary — `gradlew`/`gradlew.bat` are real scripts, but they need that jar alongside them.

Easiest fix, pick one:
>>>>>>> aa6ce3a (feat: integrate Phase 5 Inference Engine)

1. **Open the project folder in Android Studio.** It will detect the missing wrapper jar and offer to regenerate it automatically (or just resync and it pulls it in).
2. **Or, if you have Gradle installed locally**, run once from the project root:
   ```
   gradle wrapper --gradle-version 8.9
   ```

After that, `./gradlew assembleDebug` builds normally.

<<<<<<< HEAD
**Phase 5 – Inference Engine**
=======
## Phases ahead

<<<<<<< HEAD
**Phase 4 – Model Manager**
>>>>>>> 19678db (feat: integrate Phase 5 Inference Engine)

### Implemented

- Multi-module Android architecture
- Material 3 UI with Material You support
- Home dashboard
- Chat interface
- AI Council framework
- Model Manager
- Device hardware profiling
- Inference Engine
- Provider-based AI architecture
- Streaming inference pipeline
- Prompt formatting system
- Conversation context management
- Room persistence layer
- Repository architecture
- Hilt dependency injection
- Navigation between feature modules

---

## Project Structure

```text
app/
core-data/
core-model/
core-ui/
feature-chat/
feature-council/
feature-home/
feature-modelmanager/
```

### Modules

| Module | Description |
|---------|-------------|
| `app` | Application entry point, navigation and dependency injection |
| `core-model` | Shared domain models |
| `core-data` | Repository layer, Room database, DataStore, inference engine, model management and AI interfaces |
| `core-ui` | Shared UI components, theme and design system |
| `feature-home` | Home dashboard |
| `feature-chat` | Chat interface |
| `feature-council` | Multi-agent AI Council |
| `feature-modelmanager` | AI model management, hardware profiling and model selection |

---

## Current Features

- Multi-agent AI Council
- Provider-based Inference Engine
- Streaming response architecture
- Conversation context management
- Prompt formatting
- Model Manager
- Device capability detection
- AI model recommendation
- Simulated model installation workflow
- Material You interface
- Dark mode support
- Room persistence
- Modular architecture
- Jetpack Compose UI
- Dependency injection with Hilt

---

## Technology Stack

- Kotlin
- Jetpack Compose
- Material 3
- Hilt
- Room
- DataStore
- Kotlin Coroutines
- Flow

---

## Roadmap

### Phase 1
- Project scaffold
- Core architecture
- Design system

### Phase 2
- Chat experience
- Persistence improvements

### Phase 3
- AI Council
- Multi-agent orchestration
- Council visualization

### Phase 4
- Model Manager
- Device hardware detection
- AI model management
- Model recommendation engine

### Phase 5 (Current)
- Inference Engine
- Provider architecture
- Streaming pipeline
- Prompt formatting
- Context management

### Phase 6
- Local runtime architecture
- Local model loading
- Runtime management
- Performance metrics

### Phase 7
- Real local LLM integration
- Adaptive reasoning
- AI Council runtime integration

### Phase 8
- Long-term memory
- Semantic retrieval
- Vision
- Document understanding
- Plugin framework

### Phase 9
- Performance optimization
- Testing
- Production stabilization

---

## Development

Clone the repository:

```bash
git clone https://github.com/rohanr-10/OMNIX.git
```

Open the project in Android Studio and allow Gradle to synchronize dependencies.

---

## License

This project is currently under active development. A license will be added prior to the first stable release.
=======
2. Chat screen polish + persistence edge cases
3. AI Council node-graph visualization screen
4. Model Manager (hardware detection + tier recommendation)
5. Memory subsystem (semantic search over conversation history)
6. Document/Vision/Plugin frameworks
7. Tests + final polish

Each phase = complete file replacements only, no regeneration of earlier phases, no pseudocode — same as Helix.
>>>>>>> aa6ce3a (feat: integrate Phase 5 Inference Engine)
