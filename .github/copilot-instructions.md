# Codeforces-Visualizer - Copilot Instructions

## Architecture & Modules
- Kotlin Multiplatform project split across `composeApp` (UI), `shared` (core/domain/data), and `iosApp` (Xcode wrapper around `MainViewController`).
- `shared` follows Clean Architecture: `data` (Ktor DTOs and mappers), `domain` (entities, repositories, use-cases), `core` (error/result primitives).
- `composeApp/src/commonMain` groups features (`contest`, `profile`, `compare`, `preference`, `webview`) plus `core` UI components and navigation helpers.

## Data & Domain Flow
- Network calls run through `CFApiService` -> `CFRemoteDataSourceImpl.executeRequest`, returning `Either<AppError, T>` and validating `StatusModel` responses.
- `CFRepositoryImpl` caches the contest list in memory; `filterContestList` assumes `getContestList` ran first, so refresh caches before filtering.
- New endpoints should expose `@Serializable` models with `toEntity()` converters mirroring `shared/data/model`, then wire them through a repository method and use-case.

## Dependency Injection & State
- Koin modules live under `composeApp/src/commonMain/kotlin/com/codeforcesvisualizer/inject`; `Home()` bootstraps DI via `KoinApplication` even for previews.
- Keep new ViewModels registered in `viewModelModule`; construct them with use-cases rather than repositories directly.
- Theme preferences flow through `ThemeManagerViewModel` (implements `ThemeManager`), using DataStore-based use-cases (`Get/SetUiThemeModeUseCase`).

## UI & Navigation Conventions
- Compose screens consume `StateFlow` from Koin view models, collected via `collectAsState()`; update state by copying immutable `UiState` data classes.
- Navigation is centralized in `navigation/*`: top-level tabs defined in `Screen`, feature graphs in `add*TopLevel` helpers, and leaf routes nest under the tab route.
- Reusable UI resides in `core/components` (e.g., `CFBarChart`, `CFAppBar`); these often wrap Android views like MPAndroidChart via `AndroidView` - mirror that pattern for similar widgets.
- `CompareHandlesViewModel` sequentially fetches both users with a 2s delay; keep that UX expectation (spinner shows until both requests complete).

## External Services & Platform Notes
- HTTP client is Ktor CIO with timeouts from `shared/data/config/Constants.kt`; adjust there when tweaking networking behaviour.
- Firebase Analytics/Crashlytics are wired via Gradle BOM; `EventLogger` is a no-op in debug and forwards to Firebase on release - use it for analytics hooks.
- Preferences rely on platform `createPlatformDataStore()` expect/actual (Android & iOS implementations). Keep JVM-only utilities (`TimeUtils`) out of shared iOS builds unless you add expect/actual shims.
- `webview/CFWebView.kt` is Android-specific (Accompanist WebView); wrap new platform-specific UI similarly or guard with `expect/actual` APIs for iOS.
- Signing assets live in `keys/codeforces.jks` and Firebase config in `composeApp/google-services.json`; avoid altering or committing substitutes without coordination.

## Build & Run Workflow
- For Android debug builds run `./gradlew :composeApp:assembleDebug`; use `:composeApp:installDebug` when connected to a device.
- iOS uses the KMP-generated framework; open `iosApp/iosApp.xcodeproj` and launch the `iosApp` target (it calls `MainViewController()` which renders Compose `App()`).
- Rapid iteration on shared logic can rely on `./gradlew :shared:allTests` once tests are added; current repo has minimal automated coverage, so manual verification is common.
- When adding Gradle plugins or libraries, prefer editing `gradle/libs.versions.toml` and reference them through version catalogs.

## Working Effectively
- Respect the `Either` error flow; surface user-facing strings via `AppError.message` to keep Compose screens consistent.
- Extend `ThemeModeUiState`, `ContestListUiState`, etc., rather than introducing mutable fields - state flows should remain immutable snapshots.
- Before filtering or comparing users, validate input handles with `.trim()` and reuse existing use-cases to keep repository caching intact.
- Leave placeholders like `EventLogger.initialize` untouched in debug builds to avoid crashing analytics-free environments.