Key gaps spotted so far
Navigation layer is Android-only. Home.kt, AppNavigator.kt, and all add*TopLevel graphs rely on androidx.navigation and NavController, which aren’t available on iOS. The Screen sealed class also pulls Android resources (painterResource(id = R.drawable...)).

ViewModels + DI stuck on Android lifecycle. Every ViewModel (ContestViewModel, ContestDetailsViewModel, ProfileSearchViewModel, CompareHandlesViewModel, ThemeManagerViewModel, etc.) extends androidx.lifecycle.ViewModel and uses viewModelScope. We also inject them via org.koin.androidx.compose.koinViewModel, which doesn’t exist on iOS even though App() is shared.

Android UI bridges in commonMain. Components such as CFBarChart.kt and CFPieChart.kt wrap MPAndroidChart through AndroidView. CFWebView.kt embeds an Android WebView via Accompanist. These files will not even compile for iOS.

Platform services baked into shared UI.

ContestListItem.kt and ContestDetailsScreen.kt grab LocalContext, fire Intents, and call CalendarContract/bundleOf.
OtherSection.kt hardcodes Google Play intents and uses Snackbar scoped to Android context.
Compare cards (RatingsCard, TriedAndSolvedCard, SolvedWithOneSubmissionCard) import android.graphics.Color.
Accompanist-only APIs. Flow layouts (com.google.accompanist.flowlayout.FlowRow) are sprinkled through contest and profile screens; Accompanist isn’t cross-platform.

JVM-only utilities. TimeUtils.kt uses java.sql.Timestamp, java.text.SimpleDateFormat, and java.util.*; those classes don’t exist on Kotlin/Native.

Firebase + analytics. HomeActivity and EventLogger assume Firebase Analytics and Android Bundles. The stubs help, but the shared composable still calls EventLogger.logScreenView with Android Bundles (bundleOf is imported in multiple places).

Dependencies wired into commonMain. build.gradle.kts adds androidx.navigation, androidx.core-ktx, Android DataStore, and Firebase directly in commonMain.dependencies, which blocks the iOS targets from even compiling.

Resource usage. Several files reference com.codeforcesvisualizer.R drawables and strings. Compose Multiplatform expects you to go through org.jetbrains.compose.resources (MR or stringResource from composeResources). Android R values won’t resolve on iOS.

Feature TODOs uncovered. SearchBar.kt still has placeholder callbacks (onClearText, onNavigateBack). Not directly blocking CMP, but worth addressing when the new navigation/search flow is tackled.








---------------------------------------
Proposed action plan (prioritized)
Stabilize the architecture foundation

Replace androidx.navigation with a CMP-friendly navigation solution (e.g., Voyager, Decompose, or PreCompose). Adjust Screen definitions to stop referencing Android R icons and switch to multiplatform ImageVectors or Compose resources.

Introduce a multiplatform ViewModel pattern (KMP-NativeCoroutines + kotlinx.coroutines scopes, Lifecycle from kmp-viewmodel, or koin-core’s own KoinViewModel once Koin 3.5+ is configured). Update DI bindings (viewModelModule) to no longer depend on org.koin.androidx.compose.koinViewModel.
Move Android-only dependencies out of commonMain in build.gradle.kts. Keep only pure Compose + shared modules there; push Firebase, DataStore Android artifacts, androidx.core, etc., into androidMain or expect/actual wrappers.
Abstract platform services behind expect/actual layers

Calendar & intents: turn addCalendarEvent and link-opening logic into expect/actual functions (shareCalendarEvent(), openLink()). Provide Android implementations with Intent, and iOS implementations using EventKit + UIApplication.shared.open.
Analytics/EventLogger: define a multiplatform analytics interface with no Bundle dependency; Android actual can keep Firebase, iOS can be a no-op or hook to another service.
Rate/Review hooks: wrap the Play Store link in a common “rateApp” use case so iOS can deep-link into the App Store later.
Rebuild Android-only UI widgets

Replace MPAndroidChart + AndroidView wrappers (CFBarChart, CFPieChart, and compare cards) with Compose-native charts (Canvas-based) or adopt a multiplatform chart library (e.g., koalaplot). This ensures both Android and iOS render the same composables.
Reimplement CFWebView as an expect/actual screen: Android actual can keep WebView/Accompanist (or the new compose.ui.viewinterop.AndroidView), iOS actual can wrap WKWebView via UIKitView.
Swap com.google.accompanist.flowlayout.FlowRow for androidx.compose.foundation.layout.FlowRow (available in Compose Multiplatform 1.6+) or write a simple custom flow layout.
Make utility code platform-neutral

Rewrite TimeUtils using kotlinx.datetime.Instant and formatters from kotlinx-datetime or kotlinx.datetime.toLocalDateTime(TimeZone.currentSystemDefault()).
Remove usages of android.graphics.Color in compare cards—prefer Compose Color from androidx.compose.ui.graphics.
Audit the rest of commonMain for android.*, java.*, or androidx.* imports and move them behind expect/actual as needed (e.g., CountDownTimer in ContestDetailsViewModel can be replaced with a coroutine ticker on Dispatchers.Default).
iOS-specific polish

Extend iosMain with actual implementations for the new expect APIs (DataStore already has one). Ensure MainViewController() initializes Koin the same way Android does (currently KoinApplication is spun up inside App(), but confirm startup order and logging).
Audit resources: migrate strings/drawables referenced through R to the Compose Multiplatform resource system (org.jetbrains.compose.resources.stringResource, PainterResource). Update icons to use compose.material.icons (already available) or bundle vector assets through composeResources.
QA + tooling

Once the architecture refactor is in place, add a lightweight shared test suite (shared/src/commonTest) that covers the repository/use-case layer (GetContestListUseCase, FilterContestList, etc.) so regressions show up before reaching UI.
For Android, keep ./gradlew :composeApp:assembleDebug; for iOS, verify the generated ComposeApp framework builds via Xcode after each phase.
Stretch improvements (post-migration)

Flesh out the placeholder callbacks in SearchBar.kt.
Consider extracting common typography/colors into shared so iOS and Android share identical design tokens.
Add analytics hooks through the new expect/actual API so both platforms can emit events consistently.