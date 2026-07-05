# OMNIX

OMNIX is a modular, local-first AI assistant for Android built using Jetpack Compose. It is designed around a multi-agent architecture where specialized AI components collaborate to produce higher-quality responses while maintaining a privacy-focused, on-device execution model.

The project is currently under active development.

---

## Overview

The long-term objective of OMNIX is to provide an extensible AI platform capable of:

- Multi-agent reasoning
- Local AI model execution
- Intelligent model management
- Long-term conversation memory
- Vision and document understanding
- Autonomous task execution
- Offline-first operation

The architecture is intentionally modular, allowing new capabilities to be integrated without major changes to the existing codebase.

---

## Current Status

Current development milestone:

**Phase 4 – Model Manager**

### Implemented

- Multi-module Android architecture
- Material 3 UI with Material You support
- Home dashboard
- Chat interface
- AI Council framework
- Model Manager
- Device hardware profiling
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
| `core-data` | Repository layer, Room database, DataStore, device profiling and AI interfaces |
| `core-ui` | Shared UI components, theme and design system |
| `feature-home` | Home dashboard |
| `feature-chat` | Chat interface |
| `feature-council` | Multi-agent AI Council |
| `feature-modelmanager` | AI model management, hardware profiling and model selection |

---

## Current Features

- Multi-agent AI Council
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

### Phase 4 (Current)
- Model Manager
- Device hardware detection
- AI model management
- Model recommendation engine

### Phase 5
- Local inference engine
- AI model execution
- Streaming responses
- Runtime management

### Phase 6
- Long-term memory
- Semantic retrieval
- Context management

### Phase 7
- Vision
- Document understanding
- Plugin framework

### Phase 8
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
