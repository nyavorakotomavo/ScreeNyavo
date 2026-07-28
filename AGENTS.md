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
- NE JAMAIS lancer de build Gradle en local (pas de `./gradlew build`, `./gradlew assembleDebug`, etc.)
- L'environnement Termux n'a pas les ressources pour compiler localement (4GB RAM, pas de JDK complet configuré)
- Tout build passe exclusivement par GitHub Actions CI (push → build → APK)
- Pour vérifier un changement, push sur une branche et regarde les logs du workflow via `gh run view` ou l'interface GitHub
- Si OpenCode propose de lancer gradle localement, refuser et proposer un push + vérification CI à la place

## Source layout
- Single module `:app`, entrypoint `app/src/main/java/com/nyavo/screenyavo/MainActivity.kt`
- Navigation: manual state-based (`splash` → `onboarding` → `touchTest`) via `mutableStateOf` in `MainActivity.kt:24`
- Key directories (under `com/nyavo/screenyavo/`):
  - `ui/screens/` — screen composables (`SplashScreen`, `OnboardingScreen`, `TouchTestScreen`)
  - `ui/components/` — `AnimatedGridBackground` (native Paint on Canvas)
  - `ui/theme/` — `ScreeNyavoTheme` (dark-only), `MinecraftFontFamily` (`res/font/minecraft.ttf`)
  - `data/` — `DeadZoneMatrix` (grid model: 20 rows × 10 cols, `CellState` enum), `ProfileManager` (DataStore preferences)
  - `service/` — `NyavoAccessibilityService` (stub), `AccessibilityBridge` (SharedFlow event bus)

## Conventions
- Theme colors and typography are **only** in `ui/theme/Theme.kt` and `ui/theme/Type.kt` — do not redeclare in screens
- `res/values/colors.xml` exists but **Theme.kt is the source of truth** for Compose — XML colors are only for legacy resources
- Typography must use `MinecraftFontFamily` from `Type.kt`, **not** `FontFamily.Monospace` (fixed in Phase 0)
- `AnimatedGridBackground.kt` pre-allocates `gridLinePaint`, `nodePaint`, `deadNodePaint`, `scanLinePaint`, `glitchPaint`, and `pulsePaint` via `remember {}` — never allocate paints inside the draw scope
- Accessibility service declared in `AndroidManifest.xml` but config XML reference (`res/xml/accessibility_service_config.xml`) is **not wired** in the manifest — needs `android:accessibilityServiceConfig` attribute on the service element
- `theme.css` at project root is a web design token reference, **not used by the Android app** — ignore it when working on Compose UI
- Before creating any new data model, verify it doesn't already exist under a different name (e.g., `DeadZoneMatrix` already covers grid state, `ProfileManager` covers user prefs)

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
- `.gitignore` covers `.gradle/`, `local.properties`, `.idea/`, `build/` — no manual exclusions needed for IDE artifacts
- `GEMINI.md` exists but is empty (legacy file) — safe to ignore
- Dependencies are managed via Gradle version catalog at `gradle/libs.versions.toml` — current versions: AGP 8.7.2, Kotlin 2.0.21, Compose BOM 2024.11.00
- `proguard-rules.pro` has rules for coroutines, DataStore, and Compose — any new reflection-heavy dependency needs matching keep rules
- `res/values/dimens.xml` defines `grid_cell_size = 42dp` which matches the hardcoded `42.dp` in `TouchTestScreen.kt` — use the resource when referencing

## Roadmap progress
<!-- Update after each completed phase -->
- Phase 0 (Fondations): completed (2026-07-27) — design system, CI, typography fix, manifest wiring, paint pre-alloc
- Phase 1 (Test tactile): not started
- Phase 2 (Persistance): not started
- Phase 3 (Accessibilité): not started
- Phase 4 (Launcher): not started
- Phase 5 (Clavier): not started
- Phase 6 (Prévention): not started
- Phase 7 (Rapport/Urgence): not started
- Phase 8 (Affichage): not started

## Règles de build

- NE JAMAIS lancer de build Gradle en local (pas de `./gradlew build`, `./gradlew assembleDebug`, ni aucune variante).
- L'environnement Termux (4GB RAM, ARM64) n'a pas les ressources pour compiler un projet Android complet.
- Tout build de validation passe exclusivement par GitHub Actions CI : push → build → APK.
- Pour vérifier une modification, pousser sur une branche puis consulter les logs du run via `gh run view <run-id> --log-failed`.

## Règles avant modification de code

- Avant de modifier un fichier, toujours lire les imports existants et vérifier leur correspondance avec les classes utilisées.
- Ne jamais inventer un nom de méthode, classe ou propriété sans vérifier qu'il existe réellement dans le fichier source.
- `DeadZoneMap`, `GridCell` et `ZoneState` sont la source unique de vérité : ne jamais les dupliquer ni les renommer.
- Après toute modification, relire le fichier entier pour vérifier la cohérence des accolades, imports et types.
- Si une API Android/Compose est incertaine, le dire explicitement plutôt que deviner.

## Règle de diagnostic avant correction

- Avant de proposer un correctif suite à un échec de build, lire le log d'erreur complet (`gh run view --log-failed`) et citer la ligne d'erreur exacte.
- Ne jamais modifier du code "à l'aveugle" en espérant que ça corrige l'erreur sans avoir identifié la cause précise.

## Source layout
- Single module `:app`, entrypoint `app/src/main/java/com/nyavo/screenyavo/MainActivity.kt`
- Navigation: manual state-based (`splash` → `onboarding` → `touchTest`) via `mutableStateOf` in `MainActivity.kt:24`
- Key directories (under `com/nyavo/screenyavo/`):
  - `ui/screens/` — screen composables (`SplashScreen`, `OnboardingScreen`, `TouchTestScreen`)
  - `ui/components/` — `AnimatedGridBackground` (native Paint on Canvas)
  - `ui/theme/` — `ScreeNyavoTheme` (dark-only), `MinecraftFontFamily` (`res/font/minecraft.ttf`)
  - `data/` — `DeadZoneMatrix` (grid model: 20 rows × 10 cols, `CellState` enum), `ProfileManager` (DataStore preferences)
  - `service/` — `NyavoAccessibilityService` (stub), `AccessibilityBridge` (SharedFlow event bus)

## Conventions
- Theme colors and typography are **only** in `ui/theme/Theme.kt` and `ui/theme/Type.kt` — do not redeclare in screens
- `res/values/colors.xml` exists but **Theme.kt is the source of truth** for Compose — XML colors are only for legacy resources
- Typography must use `MinecraftFontFamily` from `Type.kt`, **not** `FontFamily.Monospace` (fixed in Phase 0)
- `AnimatedGridBackground.kt` pre-allocates `gridLinePaint`, `nodePaint`, `deadNodePaint`, `scanLinePaint`, `glitchPaint`, and `pulsePaint` via `remember {}` — never allocate paints inside the draw scope
- Accessibility service declared in `AndroidManifest.xml` but config XML reference (`res/xml/accessibility_service_config.xml`) is **not wired** in the manifest — needs `android:accessibilityServiceConfig` attribute on the service element
- `theme.css` at project root is a web design token reference, **not used by the Android app** — ignore it when working on Compose UI
- Before creating any new data model, verify it doesn't already exist under a different name (e.g., `DeadZoneMatrix` already covers grid state, `ProfileManager` covers user prefs)

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
- `.gitignore` covers `.gradle/`, `local.properties`, `.idea/`, `build/` — no manual exclusions needed for IDE artifacts
- `GEMINI.md` exists but is empty (legacy file) — safe to ignore
- Dependencies are managed via Gradle version catalog at `gradle/libs.versions.toml` — current versions: AGP 8.7.2, Kotlin 2.0.21, Compose BOM 2024.11.00
- `proguard-rules.pro` has rules for coroutines, DataStore, and Compose — any new reflection-heavy dependency needs matching keep rules
- `res/values/dimens.xml` defines `grid_cell_size = 42dp` which matches the hardcoded `42.dp` in `TouchTestScreen.kt` — use the resource when referencing

## Roadmap progress
<!-- Update after each completed phase -->
- Phase 0 (Fondations): completed (2026-07-27) — design system, CI, typography fix, manifest wiring, paint pre-alloc
- Phase 1 (Test tactile): not started
- Phase 2 (Persistance): not started
- Phase 3 (Accessibilité): not started
- Phase 4 (Launcher): not started
- Phase 5 (Clavier): not started
- Phase 6 (Prévention): not started
- Phase 7 (Rapport/Urgence): not started
- Phase 8 (Affichage): not started
