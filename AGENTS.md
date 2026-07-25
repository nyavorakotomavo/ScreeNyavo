# ScreeNyavo — Agent instructions

## Build
```bash
gradle wrapper          # generate gradlew (not committed yet)
./gradlew assembleDebug  # build debug APK
./gradlew assembleRelease # build release APK (uses proguard-rules.pro)
```

- JDK 17, namespace `com.nyavo.screenyavo`
- Single module `:app`, entrypoint `app/src/main/java/com/nyavo/screenyavo/MainActivity.kt`
- No lint/typecheck/test tasks configured yet

## Architecture
- `ui/screens/` — screen composables (`SplashScreen`, `OnboardingScreen`, `TouchTestScreen`)
- `ui/components/` — reusable composables (`AnimatedGridBackground`)
- `ui/theme/` — `ScreeNyavoTheme` (dark-only), `MinecraftFontFamily` (`minecraft.ttf`)
- `data/` — `DeadZoneMatrix` (grid model), `ProfileManager` (DataStore preferences)
- `service/` — `NyavoAccessibilityService` (AccessibilityService), `AccessibilityBridge` (SharedFlow event bus)
- Navigation: manual state-based in `MainActivity` (`splash` → `onboarding`)

## Conventions
- Theme colors in `ui/theme/Theme.kt` — do NOT redeclare them in screens (see `TouchTestScreen.kt` for legacy duplication)
- Typography uses `MinecraftFontFamily` from `Type.kt` (not `FontFamily.Monospace`)
- `AnimatedGridBackground.kt` uses native Android `Paint` on Canvas — avoid reallocating paints in draw scope
- Accessibility service config: `res/xml/accessibility_service_config.xml`
