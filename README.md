# OMNIX

OMNIX is a modular, local-first AI assistant for Android built using Jetpack Compose. It is designed around a multi-agent architecture where specialized AI components collaborate to produce higher-quality responses while maintaining a privacy-focused, on-device execution model.

The project is currently under active development.

---

## Overview

The long-term objective of OMNIX is to provide an extensible AI platform capable of:

- Multi-agent reasoning
- Local model execution
- Conversation memory
- Document understanding
- Vision capabilities
- Autonomous task workflows
- Offline-first operation

The architecture is intentionally modular to allow new AI capabilities to be integrated without major changes to the application structure.

---

## Current Status

Current development milestone:

**Phase 3 – AI Council**

Implemented:

- Multi-module Android architecture
- Material 3 UI with dynamic color support
- Home dashboard
- Chat interface
- AI Council framework
- Room persistence layer
- Repository architecture
- Hilt dependency injection
- Navigation between application modules

---

## Project Structure

```
app/
core-data/
core-model/
core-ui/
feature-chat/
feature-council/
feature-home/
```

### Modules

| Module | Description |
|---------|-------------|
| `app` | Application entry point, navigation, dependency injection |
| `core-model` | Domain models shared across modules |
| `core-data` | Repository layer, Room database, DataStore, AI interfaces |
| `core-ui` | Shared UI components, themes, design system |
| `feature-home` | Home dashboard |
| `feature-chat` | Chat experience |
| `feature-council` | Multi-agent AI Council implementation |

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

### Phase 3 *(Current)*
- AI Council
- Multi-agent orchestration
- Council visualization

### Phase 4
- Model Manager
- Hardware capability detection
- Local model selection

### Phase 5
- Long-term memory
- Semantic retrieval
- Context management

### Phase 6
- Vision
- Document processing
- Plugin framework

### Phase 7
- Local LLM integration
- Performance optimization
- Testing and stabilization

---

## Development

Clone the repository:

```bash
git clone https://github.com/rohanr24332-del/OMNIX.git
```

Open the project in Android Studio and allow Gradle to synchronize dependencies.

---

## License

This project is currently under active development. A license will be added prior to the first stable release.
