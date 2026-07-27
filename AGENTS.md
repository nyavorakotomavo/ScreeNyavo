# ScreeNyavo — Agent instructions

## Build & verify
```bash
./gradlew assembleDebug  # debug APK
./gradlew assembleRelease # release APK (proguard-rules.pro applied)
```
- JDK 17, namespace `com.nyavo.screenyavo`, minSdk 21, compileSdk/targetSdk 35
- **No test/lint/typecheck tasks configured** — the only verification is `assembleDebug` passing
- CI (`.github/workflows/build.yml`) runs `gradle wrapper && ./gradlew assembleDebug` on push/PR to `main`, uploads APK as artifact
- Development environment: Termux-only, no Android Studio, no emulator, no PC. All code must compile on first CI push (15-30 min per cycle). Commands must be complete copy-paste blocks.

## Source layout
- Single module `:app`, entrypoint `app/src/main/java/com/nyavo/screenyavo/MainActivity.kt`
- Navigation: manual state-based (`splash` → `onboarding` → `touchTest`)
- Key directories (under `com/nyavo/screenyavo/`):
  - `ui/screens/` — screen composables (`SplashScreen`, `OnboardingScreen`, `TouchTestScreen`)
  - `ui/components/` — `AnimatedGridBackground` (native Paint on Canvas)
  - `ui/theme/` — `ScreeNyavoTheme` (dark-only), `MinecraftFontFamily` (`minecraft.ttf`)
  - `data/` — `DeadZoneMatrix` (grid model), `ProfileManager` (DataStore preferences)
  - `service/` — `NyavoAccessibilityService`, `AccessibilityBridge` (SharedFlow event bus)

## Conventions
- Theme colors and typography are **only** in `ui/theme/Theme.kt` and `ui/theme/Type.kt` — do not redeclare in screens
- Typography uses `MinecraftFontFamily` from `Type.kt`, **not** `FontFamily.Monospace` (see `TouchTestScreen.kt` for violations to fix)
- `AnimatedGridBackground.kt` uses pre-allocated `Paint` objects — never allocate paints inside the draw scope
- Accessibility service config: `res/xml/accessibility_service_config.xml`
- Before creating any new data model, verify it doesn't already exist under a different name

## Phase-aware workflow
Detailed roadmap with per-phase completion criteria in `ROADMAP.md`. Current phase status is tracked at the bottom of this file.

Agent behavior rules:
1. Ask for current project state (phase, existing files) before acting — never assume from prior session
2. One phase at a time until its completion criterion is met
3. If a feature exceeds what's realistic for a solo mobile dev, propose a simplified version
4. Explain how to test each feature once APK is built
5. If Manifest changes, list modifications explicitly
6. One question at a time in case of ambiguity
7. After each completed phase, update this file (mark phase, note date) before starting next

## Environment quirks
- `.gitignore` is sparse — only ignores `node_modules/`, `.git/`, `dist/`, `build/`, `.env`, `*.log`. Entries like `.gradle/` and `local.properties` are missing.
- `GEMINI.md` exists but is empty (legacy file)
- No version catalog for dependencies beyond what's in `gradle/libs.versions.toml`

## Roadmap progress
<!-- Update after each completed phase -->
- Phase 0 (Fondations): not yet completed
- Phase 1 (Test tactile): not started
- Phase 2 (Persistance): not started
- Phase 3 (Accessibilité): not started
- Phase 4 (Launcher): not started
- Phase 5 (Clavier): not started
- Phase 6 (Prévention): not started
- Phase 7 (Rapport/Urgence): not started
- Phase 8 (Affichage): not started
