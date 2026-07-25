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

## Contraintes de développement (non négociables)
- Développeur solo, code exclusivement via Termux sur Android. Pas de PC,
  pas d'Android Studio, pas d'émulateur.
- Compilation UNIQUEMENT via GitHub Actions (push -> CI -> APK téléchargé
  -> testé sur device réel). Un code qui ne compile pas au premier essai
  coûte un cycle complet (15-30 min).
- Budget quasi nul, 100% offline par défaut.
- Toute commande donnée doit être un bloc complet copiable-collable dans
  Termux, jamais un extrait partiel.
- Stack déjà décidée : Jetpack Compose (voir Architecture ci-dessus).
  Ne pas proposer de migration vers Views/XML sans validation explicite.

## Vision produit
ScreeNyavo (ex-NyavoScreen/NRscreen) : application de référence pour le
diagnostic, le contournement, la prévention et l'assistance des écrans
Android endommagés. Ambition de finition = niveau top 5 mondial des apps
les plus utilisées, sans compromis sur la qualité perçue.
Identité visuelle : pixel art violet, noir profond, effets CRT/scanlines,
ambiance cyberpunk, sons rétro. Interactions (glow, ondes, transitions)
modernes en contraste volontaire avec l'esthétique rétro.

## Catalogue de fonctionnalités (résumé — demander le détail complet si besoin)
Diagnostic tactile (grille guidée, multitouch, latence, heatmap) ·
Diagnostic affichage (couleurs, pixels morts, burn-in) ·
Scanner IA [complexité élevée, phase tardive] ·
Carte & historique · Prévention · Accessibilité/contournement (boutons
flottants, gestes, curseur gyroscopique, mode scan) [différenciation
principale] · Contrôle externe [phase tardive] · Urgence & Data Rescue ·
Analyse système · Simulateurs · Réparation assistée · Launcher & clavier
adaptés · Sauvegarde/partage local · Gamification [priorité basse] ·
Pro [priorité basse] · IA avancée [complexité élevée] · Communautaire
[opt-in, nécessite un serveur — isoler proprement du reste offline].

## Roadmap — une phase à la fois, avec critère de fin vérifiable
- Phase 0 Fondations : repo + CI + design system violet. FIN = build vert
  sur commit vide + colors.xml/dimens.xml en place.
- Phase 1 Moteur visuel du test : grille pixel art, balayage guidé par
  zones, détection faux taps. FIN = test complet réalisable sur device
  réel avec résultat cohérent.
- Phase 2 Persistance : JSON local, historique, export/import profil.
  FIN = fermer/rouvrir l'app sans perte de profil.
- Phase 3 Accessibilité/contournement : service accessibilité, boutons
  flottants, gestes, boutons physiques, mode scan. FIN = naviguer une app
  simple sans toucher une zone morte connue.
- Phase 4 Launcher. FIN = lancer 5 apps sans toucher une zone morte.
- Phase 5 Clavier adapté. FIN = SMS complet sans lettre en zone morte.
- Phase 6 Prévention/monitoring. FIN = alerte pertinente après 1 semaine
  d'usage réel.
- Phase 7 Rapport/simulateur/urgence/Data Rescue. FIN = rapport exporté +
  fonctionnalité testée via simulateur + sauvegarde Data Rescue réussie.
- Phase 8 Diagnostic affichage complet.
- Phase 9 Scanner IA + IA avancée [uniquement si phases 0-8 toutes FIN].
- Phase 10 Contrôle externe/gamification/pro/communautaire [polish tardif].
- Phase 11 Polish/QA/traduction/lancement.

## Règles de comportement pour l'agent
1. Avant d'agir, demander l'état actuel du projet (quelle phase, quels
   fichiers existent) — ne jamais présumer depuis une session précédente.
2. Une phase à la fois jusqu'à son critère de fin, jamais en parallèle.
3. Si une fonctionnalité demandée dépasse ce qui est réaliste pour un
   développeur solo à ce stade (scanner IA photo, RA, streaming d'écran,
   diagnostic acoustique...), le dire clairement et proposer une version
   simplifiée plutôt que de commencer silencieusement.
4. Ne jamais dupliquer un modèle de données existant sous un autre nom —
   vérifier avant de créer.
5. Expliquer comment tester chaque fonctionnalité une fois l'APK compilé.
6. Si le Manifest change, lister explicitement ce qui change.
7. Une seule question à la fois en cas d'ambiguïté.
8. Après chaque phase dont le critère de fin est rempli, mettre à jour ce
   fichier AGENTS.md (cocher la phase, noter la date) avant de continuer.
