# MovieApp

TMDB API를 활용한 영화 정보 앱. **MVI 패턴**과 **Clean Architecture**, **멀티모듈** 구조를 적용하여 확장성과 테스트 용이성을 확보했다.

## Tech Stack

| Category | Stack |
|----------|-------|
| **UI** | Jetpack Compose, Material3 |
| **Architecture** | MVI (Model-View-Intent), Clean Architecture |
| **DI** | Hilt |
| **Async** | Kotlin Coroutines, Flow |
| **Network** | Retrofit2, OkHttp, Moshi |
| **Local** | Room, DataStore |
| **Image** | Coil |
| **Paging** | Paging3 |
| **Test** | JUnit5, MockK, Turbine |

## Architecture

### MVI Pattern

단방향 데이터 흐름으로 상태를 예측 가능하게 관리한다.

```
┌─────────────────────────────────────────────────────┐
│                      UI Layer                       │
│  ┌─────────┐    ┌──────────┐    ┌──────────────┐   │
│  │  Event  │───▶│ ViewModel│───▶│    State     │   │
│  │ (Intent)│    │ (Reducer)│    │   (Model)    │   │
│  └─────────┘    └──────────┘    └──────────────┘   │
│       ▲                               │            │
│       └───────────────────────────────┘            │
│                   Compose UI                       │
└─────────────────────────────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────┐
│                   Domain Layer                      │
│              UseCase / Repository                   │
└─────────────────────────────────────────────────────┘
                        │
                        ▼
┌─────────────────────────────────────────────────────┐
│                    Data Layer                       │
│           Remote (API) / Local (Room)               │
└─────────────────────────────────────────────────────┘
```

### MVI Contract Example

```kotlin
class HomeContract {
    data class State(
        val isLoading: Boolean = false,
        val favoriteIds: Set<Long> = emptySet(),
        val error: String? = null
    ) : UiState

    sealed class Event : UiEvent {
        data class OnMovieClick(val movieId: Long) : Event()
        data class OnFavoriteClick(val movie: Movie) : Event()
    }

    sealed class Effect : UiEffect {
        data class NavigateToDetail(val movieId: Long) : Effect()
        data class ShowToast(val message: String) : Effect()
    }
}
```

### Base MviViewModel

```kotlin
abstract class MviViewModel<State : UiState, Event : UiEvent, Effect : UiEffect> : ViewModel() {

    abstract fun createInitialState(): State
    abstract fun handleEvent(event: Event)

    protected fun setState(reduce: State.() -> State) { ... }
    protected fun setEffect(builder: () -> Effect) { ... }
}
```

## Module Structure

```
movieApp/
├── app                    # Application entry, Navigation
├── core/
│   ├── common             # Shared utilities, Result wrapper
│   ├── model              # Domain models
│   ├── network            # Retrofit, API service
│   ├── database           # Room, DAO
│   ├── data               # Repository implementations
│   ├── domain             # UseCases
│   ├── ui                 # MVI base, common composables
│   └── designsystem       # Theme, Colors, Typography
├── feature-home           # Home screen (Now Playing movies)
├── feature-detail         # Movie detail screen
├── feature-favorite       # Favorite movies screen
├── feature-common-ui      # Shared UI components
├── data                   # Legacy data module
└── domain                 # Legacy domain module
```

### Module Dependencies

```
app
 ├── feature-home
 ├── feature-detail
 ├── feature-favorite
 │    └── core-ui
 │         └── core-domain
 │              └── core-data
 │                   ├── core-network
 │                   └── core-database
 └── core-designsystem
```

## Features

- **Home**: 현재 상영중인 영화 목록 (Paging3)
- **Detail**: 영화 상세 정보 조회
- **Favorite**: 즐겨찾기 추가/삭제 (Room)

## Getting Started

### Prerequisites

- Android Studio Hedgehog | 2023.1.1+
- JDK 17
- Kotlin 1.9+

### Setup

1. [TMDB](https://www.themoviedb.org/)에서 API Key 발급
2. `gradle.properties`에 추가:
   ```properties
   movie_api_key=YOUR_API_KEY
   ```
3. Build & Run

## Testing

```bash
# Unit Tests
./gradlew test

# Specific module test
./gradlew :feature-home:test
./gradlew :domain:test
```

### Test Coverage

| Module | Test Files |
|--------|------------|
| feature-home | HomeViewModelTest |
| feature-detail | DetailViewModelTest |
| feature-favorite | FavoriteViewModelTest |
| domain | GetDetailMovieUseCaseTest, GetFavoriteMoviesUseCaseTest, AddFavoriteMovieUseCaseTest, DeleteFavoriteMovieUseCaseTest |
| data | FavoriteDataSourceTest, MovieDataSourceTest |

## Key Decisions

### Why MVI?

1. **단방향 데이터 흐름**: State가 한 곳에서 관리되어 디버깅이 쉽다
2. **예측 가능성**: 동일 Event → 동일 State 변화
3. **테스트 용이**: Reducer가 순수 함수라 테스트가 단순하다
4. **Compose 친화적**: StateFlow와 자연스럽게 연동

### Why Multi-Module?

1. **빌드 시간 단축**: 변경된 모듈만 재빌드
2. **의존성 격리**: 레이어 간 잘못된 의존 방지
3. **팀 협업**: 모듈 단위 코드 오너십
4. **재사용성**: core 모듈을 다른 프로젝트에 활용 가능

## License

```
MIT License
Copyright (c) 2024 Byeongchang Lee
```
