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

═══════════════════════════════════════════════════════════
ScreeNyavo — RÉFÉRENCE PRODUIT COMPLÈTE
═══════════════════════════════════════════════════════════

VISION : application de référence pour tout ce qui touche aux écrans
Android endommagés — diagnostic, contournement, prévention, réparation
assistée, accessibilité pour écrans quasi inutilisables. Ambition de
finition = top 5 mondial des apps les plus utilisées, sans compromis
sur la qualité perçue.

IDENTITÉ VISUELLE : pixel art en nuances de violet, noir profond,
animations pixel, effets CRT/scanlines, ambiance cyberpunk, sons
rétro, vibrations intelligentes. Les interactions elles-mêmes (glow,
ondes de propagation, transitions animées) sont modernes — contraste
volontaire avec l'esthétique rétro du reste.

───────────────────────────────────────────────────────────
CATALOGUE COMPLET DES FONCTIONNALITÉS (groupées par domaine)
───────────────────────────────────────────────────────────

DIAGNOSTIC TACTILE : test en grille pixel art à balayage guidé par
zones (jamais de tap libre), détection zones mortes/fantômes/doubles
touches, précision tactile, multitouch (2-10 doigts), vitesse et
latence, gestes système, heatmap, affichage pixel par pixel, rapport
consolidé.

DIAGNOSTIC AFFICHAGE : tests de couleur plein écran, pixels
morts/bloqués, burn-in, clouding, bleeding, scintillement, HDR,
luminosité, contraste, spécificités AMOLED/LCD.

SCANNER INTELLIGENT (IA) : détection de fissures/bandes/taches/fuites
par IA embarquée, analyse photo, diagnostic en une touche avec
pourcentage de confiance. [COMPLEXITÉ ÉLEVÉE — phase tardive]

CARTE & HISTORIQUE : grille avec code couleur par état, historique par
cellule, comparaison avant/après, graphiques, export PDF/image/CSV/JSON.

PRÉVENTION : mode prévention écran fissuré, surveillance burn-in,
conseils personnalisés, suivi temps d'écran/température, rappels.

ACCESSIBILITÉ & CONTOURNEMENT (différenciation principale) : boutons
flottants auto-positionnés, gestes personnalisés et gestes de bord,
raccourcis boutons physiques, curseur virtuel gyroscopique, pavé
tactile déplaçable (virtual trackpad), mode scan avec surbrillance
séquentielle, mode une main, neutralisation logique des zones
fantômes, export vers APIs d'accessibilité système.

CONTRÔLE EXTERNE : clavier/souris USB-Bluetooth, manette, télécommande.
[COMPLEXITÉ ÉLEVÉE — phase tardive]

URGENCE & SAUVEGARDE : écran d'urgence gros boutons en zone garantie
saine, envoi de position, mode "Data Rescue" (sauvegarde en un clic
des données essentielles).

ANALYSE SYSTÈME : fréquence tactile, taux de rafraîchissement,
résolution, densité, modèle/fabricant, infos GPU.

SIMULATEURS : simulation de chaque type de panne pour développer/tester
sans device cassé et pour apprentissage utilisateur.

RÉPARATION ASSISTÉE : calibration tactile logicielle, vérification
capteurs/drivers, tests post-réparation, base de données par modèle
(problèmes connus, prix moyen, tutoriels). [Base de données = contenu
à construire progressivement, pas un chantier technique complexe]

LAUNCHER & CLAVIER ADAPTÉS : launcher temporaire hors zones mortes
priorisé par usage réel, clavier décalé réorganisé par fréquence de
lettres.

SAUVEGARDE & PARTAGE LOCAL : profils sauvegardés offline, export/import
en fichier partageable (QR code, Bluetooth) entre appareils similaires.

GAMIFICATION : XP, niveaux, badges, défis, statistiques.
[Priorité basse — polish, pas fondation]

PRO : export CSV/JSON/PDF, partage de rapport, signature électronique,
mode technicien, diagnostic en lot. [Priorité basse — monétisation]

FONCTIONNALITÉS IA AVANCÉES : prédiction d'évolution, estimation de
coût de réparation, plan de réparation généré, assistant conversationnel,
score de santé sur 100, rapport pro exportable. [COMPLEXITÉ ÉLEVÉE]

COMMUNAUTAIRE (strictement opt-in, jamais requis pour l'usage de base) :
base de pannes fréquentes par modèle, comparaison anonymisée entre
appareils du même modèle. [Nécessite un serveur — rupture du 100%
offline, à isoler proprement du reste de l'app]

IDÉES DIFFÉRENCIANTES SUPPLÉMENTAIRES (à considérer en phase avancée,
une fois le socle solide) : écran miroir compagnon vers un second
device, passeport de santé d'écran en QR code, clavier auto-apprenant
par usage réel, retour haptique différencié type "braille tactile",
diagnostic acoustique par micro, guide de réparation en réalité
augmentée, simulation avant/après réparation en RA, mode "trousse de
secours" pré-mort accessible écran éteint, score de revente dynamique,
base communautaire de fragilité par modèle.

───────────────────────────────────────────────────────────
ROADMAP PAR PHASES — AVEC CRITÈRE DE FIN OBLIGATOIRE
───────────────────────────────────────────────────────────

Chaque phase a un critère de fin VÉRIFIABLE. Une phase n'est jamais
"presque finie" — soit le critère est rempli sur un APK réellement
testé, soit la phase continue.

PHASE 0 — Fondations
Contenu : repo GitHub, structure Gradle, workflow CI validé sur projet
vide, design system (palette violette en colors.xml, dimens.xml,
typographie).
CRITÈRE DE FIN : un commit vide déclenche un build vert sur Actions ET
colors.xml/dimens.xml existent et sont documentés comme référence
unique.

PHASE 1 — Moteur visuel du test tactile de base
Contenu : modèle de données (grille + états), rendu pixel art violet
plein écran, balayage guidé par macro-zones, détection faux taps,
effets d'interaction modernes, écran de résultat.
CRITÈRE DE FIN : test complet réalisable sur device réel du début à la
fin, résultat cohérent avec l'état réel de l'écran testé.

PHASE 2 — Persistance locale
Contenu : sérialisation JSON de la carte, sauvegarde auto, historique
des tests, export/import de profil en fichier.
CRITÈRE DE FIN : fermer l'app, la rouvrir, retrouver le dernier profil
intact.

PHASE 3 — Accessibilité & contournement
Contenu : service d'accessibilité, boutons flottants, gestes, boutons
physiques, mode scan, neutralisation zones fantômes.
CRITÈRE DE FIN : naviguer dans une app simple sans toucher une zone
morte connue, uniquement via gestes/boutons physiques.

PHASE 4 — Launcher temporaire
CRITÈRE DE FIN : lancer 5 apps du quotidien sans toucher une zone
morte, retour au launcher d'origine en un geste.

PHASE 5 — Clavier adapté
CRITÈRE DE FIN : taper un SMS complet sans qu'aucune lettre nécessaire
ne tombe en zone morte.

PHASE 6 — Prévention, monitoring, historique avancé
CRITÈRE DE FIN : au moins une alerte de dégradation pertinente reçue
après une semaine d'usage réel.

PHASE 7 — Rapport, simulateur, mode urgence, Data Rescue
CRITÈRE DE FIN : rapport exportable généré et partagé ; une
fonctionnalité testée via simulateur sur écran sain ; sauvegarde
Data Rescue exécutée avec succès en test.

PHASE 8 — Diagnostic affichage complet
CRITÈRE DE FIN : les 6+ tests de couleur et les détections
burn-in/clouding/bleeding fonctionnent et donnent un résultat
exploitable sur un écran de test réel.

PHASE 9 — Scanner IA + fonctionnalités IA avancées
CRITÈRE DE FIN : uniquement entamée si les phases 0-8 remplissent
toutes leur critère de fin. Sous-découper cette phase en versions
minimales avant toute version avancée (ex: score de santé simple avant
prédiction d'évolution).

PHASE 10 — Contrôle externe, gamification, pro, communautaire
CRITÈRE DE FIN : chaque sous-fonctionnalité de cette phase doit
justifier explicitement sa priorité avant d'être commencée — ce sont
des couches de polish, pas des fondations.

PHASE 11 — Polish, QA, traduction, lancement
CRITÈRE DE FIN : une personne extérieure au projet réagit positivement
au rendu sans connaître le contexte "projet solo" ; app installable
publiquement.

───────────────────────────────────────────────────────────
RÈGLE DE MISE À JOUR DE CE DOCUMENT
───────────────────────────────────────────────────────────
Après chaque phase dont le critère de fin est rempli, ce document doit
être mis à jour (barrer la phase terminée, noter la date, ajuster
l'ordre si nécessaire) avant de démarrer la phase suivante avec
n'importe quelle IA.
