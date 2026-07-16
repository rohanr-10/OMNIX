# OMNIX

OMNIX is a modular, local-first AI assistant for Android built using Jetpack Compose. It is designed around a multi-agent architecture where specialized AI components collaborate to produce high-quality responses while maintaining a privacy-focused, on-device execution model.

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

**Phase 5 – Inference Engine (Current)**

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
- AI model runtime abstraction
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
- AI model runtime abstraction
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
- AI model runtime abstraction
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
