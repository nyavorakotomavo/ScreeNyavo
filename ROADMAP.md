# ScreeNyavo — ROADMAP COMPLETE

## 1. VISION DU PROJET

### Mission
Devenir "Le médecin de l'écran Android" : l'application de référence mondiale pour le diagnostic, le contournement, la prévention et la réparation assistée des écrans Android endommagés.

### Ambition
Créer un écosystème logiciel complet, premium et offline-first, capable de sauver des données, prolonger la durée de vie des appareils et fournir des outils de niveau professionnel aux techniciens et aux utilisateurs finals.

### Positionnement
Une application haut de gamme, visuellement unique (Cyberpunk/Pixel Art), fonctionnant à 100% hors ligne par défaut, développée dans des conditions extrêmes (100% mobile) pour prouver la robustesse et l'optimisation du code.

### Utilisateurs ciblés
- **Utilisateurs finaux** : Personnes avec un écran cassé, des zones mortes tactiles ou des pixels morts.
- **Techniciens réparateurs** : Besoin de diagnostics rapides, de rapports professionnels et de tests post-réparation.
- **Gamers / Power users** : Recherche de latence tactile, de refresh rate et de calibration parfaite.
- **Seniors / Personnes en situation de handicap** : Besoin de contournement d'écran et d'accessibilité avancée.

### Différence avec les applications concurrentes
Contrairement aux applications de test basiques (souvent remplies de publicités, avec des interfaces génériques et limitées au simple test tactile), ScreeNyavo offre :
- Une approche holistique (Diagnostic + Contournement + Prévention + Réparation).
- Une identité visuelle premium et immersive (Cyberpunk, CRT, Pixel Art).
- Un système de contournement actif (Launcher, clavier, accessibilité) pour *utiliser* l'appareil, pas juste le tester.
- Une architecture modulaire pensée pour l'IA et l'export professionnel.

### Identité Visuelle
- **Style** : Pixel art moderne, Cyberpunk violet/noir.
- **Effets** : Effets CRT, Scanlines subtiles, Glow néon sur les éléments interactifs.
- **Animations** : Animations pixel fluides, transitions réactives.
- **Retour haptique et sonore** : Vibrations intelligentes (pattern haptique selon le type d'erreur/succès), sons rétro optionnels (bips 8-bit).
- **Interface** : Interface premium, sombre, contrastée, lisible même sur un écran endommagé.

---

## 2. CONTRAINTES DE DÉVELOPPEMENT

### Environnement et Outils
- **Environnement** : Uniquement Termux sur Android (Git, CLI, éditeurs texte mobiles).
- **Compilation** : Uniquement GitHub Actions (Modification → Push GitHub → CI Build → APK → Test appareil réel).
- **Technologie** : Kotlin natif Android (Jetpack Compose pour l'UI, Android SDK standard).
- **Développement** : Solo (Nyavo).

### Contraintes Matérielles et Budgétaires
- **Pas de PC** : Tout le code est écrit, commit et pushé depuis un smartphone/tablette.
- **Pas Android Studio** : Compilation et linting gérés via Gradle CLI dans Termux et GitHub Actions.
- **Pas d'émulateur** : Les tests se font exclusivement sur appareil réel (parfois l'appareil de développement lui-même, parfois des appareils de test externes).
- **Budget quasi nul** : Utilisation exclusive des tiers gratuits de GitHub, Termux, et outils open-source.

### Règles de Qualité et Méthodologie
- **Offline par défaut** : Aucune dépendance réseau critique. L'application doit fonctionner en mode avion.
- **Une seule phase active à la fois** : Interdiction de commencer une phase tant que la précédente n'est pas validée.
- **Priorité absolue à la stabilité** : Mieux vaut moins de fonctionnalités que des crashs.
- **Tests concrets** : Chaque fonctionnalité doit avoir un protocole de test réel et vérifiable.
- **Commandes mobiles** : Les scripts Termux et les commandes Git doivent être optimisés pour un clavier tactile ou un petit clavier Bluetooth.

---

## 3. RÈGLES DE COLLABORATION AVEC LES IA

Tout développement assisté par IA doit respecter ce protocole strict pour éviter la dette technique et les erreurs de contexte.

### Avant toute modification
1. **Demander l'état actuel** : Quel est le statut exact du projet ?
2. **Demander la phase actuelle** : Quelle phase de la roadmap est en cours ?
3. **Demander les fichiers existants** : Fournir le code des fichiers concernés.
4. **Vérifier l'état GitHub** : S'assurer que la dernière CI est verte et que le code est synchronisé.

### Avant de coder
1. **Annoncer la phase** : "Je vais travailler sur la Phase X".
2. **Annoncer le critère de fin** : "L'objectif est de valider le critère Y".
3. **Expliquer le plan** : Détailler les fichiers à créer/modifier et la logique implémentée.

### Après chaque fonctionnalité
1. **Donner procédure de test** : Fournir les étapes exactes pour tester sur l'appareil réel.
2. **Vérifier compilation** : Confirmer que le build Gradle passe sans erreur.
3. **Vérifier APK réel** : Confirmer que l'APK généré par GitHub Actions est installé et fonctionne sur le device.

---

## 4. ARCHITECTURE GLOBALE

```text
                           [ ScreeNyavo App ]
                                    |
      +-----------------------------+-----------------------------+
      |                             |                             |
      v                             v                             v
[ Diagnostic Engine ]       [ Rescue System ]       [ Prevention System ]
      |                             |                             |
      +-- Tactile Module            +-- Accessibility Service     +-- Burn-in Monitor
      +-- Display Module            +-- Floating UI Overlay       +-- Temp/Usage Tracker
      +-- System Analyzer           +-- Emergency Rescue          +-- Alert Engine
      |                             |                             |
      +-----------------------------+-----------------------------+
                                    |
                                    v
                      +-------------+-------------+
                      |                           |
                      v                           v
            [ Intelligence Layer ]      [ Premium Layer ]
                      |                           |
                      +-- AI Scanner (Late)       +-- Pro Reports
                      +-- Health Prediction       +-- Tech Mode
                      +-- Repair Estimation       +-- Advanced Exports
```

### Description des Modules
- **Diagnostic Engine** : Cœur analytique. Gère les tests tactiles (grille, latence, multitouch), les tests d'affichage (couleurs, pixels morts) et l'analyse système matérielle.
- **Rescue System** : Module de survie. Contient les services d'accessibilité, les boutons flottants, le launcher de secours, le clavier adapté et les outils d'urgence pour utiliser un appareil cassé.
- **Prevention System** : Module de soin. Surveille la santé de l'écran (burn-in, température, temps d'utilisation) et émet des alertes préventives.
- **Intelligence Layer** : Cerveau prédictif (activé tardivement). Analyse les données locales pour prédire l'évolution des pannes, estimer les coûts de réparation et détecter les fissures via la caméra.
- **Premium Layer** : Module professionnel. Débloque les fonctionnalités pour les techniciens (rapports PDF signés, diagnostics multiples, mode expert).

---

## 5. CATALOGUE COMPLET DES FONCTIONNALITÉS

### 5.1. Diagnostic Tactile
- Test grille pixel art (interaction visuelle).
- Balayage guidé par zones.
- Détection zones mortes (tactile).
- Détection ghost touch (touches fantômes).
- Détection double touch.
- Multitouch 2 à 10 doigts.
- Mesure de latence tactile.
- Test de précision (cibles mobiles).
- Heatmap tactile (carte de chaleur des zones les plus touchées).
- Génération de rapport tactile.

### 5.2. Diagnostic Affichage
- Couleurs plein écran (Rouge, Vert, Bleu, Blanc, Noir).
- Détection pixels morts.
- Détection pixels bloqués (stuck pixels).
- Détection burn-in (grilles de test spécifiques).
- Détection clouding (halos lumineux).
- Détection light bleeding (fuites de lumière).
- Test de scintillement (flicker).
- Test HDR (si supporté).
- Test de luminosité maximale/minimale.
- Test de contraste.
- Détection automatique technologie (AMOLED / LCD).

### 5.3. Scanner IA (Phase Tardive)
- Détection fissures via caméra.
- Détection bandes verticales/horizontales.
- Détection taches d'encre/oxydation.
- Détection fuites de cristaux liquides.
- Analyse photo de l'écran.
- Affichage pourcentage de confiance.

### 5.4. Carte et Historique
- Carte couleur de l'état de l'écran.
- Historique des cellules testées.
- Comparaison avant/après réparation.
- Graphiques d'évolution.
- Export PDF (Rapport).
- Export image (PNG de la heatmap).
- Export CSV (Données brutes).
- Export JSON (Structure complète).

### 5.5. Prévention
- Surveillance burn-in (déplacement subtil des pixels UI).
- Monitoring température écran.
- Suivi temps écran (Screen on time).
- Conseils de préservation.
- Alertes préventives (notifications).

### 5.6. Accessibilité et Contournement
- Service Accessibilité (NRAccessibilityService).
- Boutons flottants (FloatingButtonService).
- Gestes personnalisés.
- Gestes bord écran (Edge swipes).
- Mapping boutons physiques (Volume/Power).
- Curseur gyroscope (contrôle du pointeur par inclinaison).
- Virtual trackpad (pavé tactile virtuel).
- Mode scan (navigation séquentielle).
- Mode une main (UI redimensionnée).
- Neutralisation zones fantômes (filtrage logiciel des ghost touches).

### 5.7. Contrôle Externe
- Support USB OTG.
- Support Bluetooth.
- Support Souris (clic, molette).
- Support Clavier (raccourcis).
- Support Manette (gamepad mapping).
- Support Télécommande (Android TV/Box).

### 5.8. Urgence
- Écran urgence (mode haute visibilité).
- Gros boutons (cibles tactiles maximales).
- Sauvegarde Data Rescue (extraction rapide contacts/photos).
- Contact urgence (appel/SOS rapide).

### 5.9. Analyse Système
- Fréquence tactile (Polling rate).
- Refresh rate (Taux de rafraîchissement).
- Résolution native.
- Densité (DPI).
- Infos GPU (Rendu).
- Fabricant.
- Modèle exact.

### 5.10. Simulateurs
- Simulation écran cassé (filtre visuel).
- Simulation pannes (ghost touch, latence, inversion couleurs).

### 5.11. Réparation Assistée
- Calibration tactile post-réparation.
- Vérification capteurs (proximité, lumière).
- Tests après réparation (checklist).
- Base de problèmes connus par modèles.

### 5.12. Launcher
- Launcher secours (Home screen alternatif).
- Applications prioritaires (épinglées).
- Navigation hors zones mortes (déplacement automatique des icônes).

### 5.13. Clavier
- Clavier adapté (IME personnalisé).
- Touches déplacées (redimensionnement selon zones mortes).
- Fréquence lettres (agrandissement des lettres les plus utilisées).

### 5.14. Sauvegarde
- Profils offline (sauvegarde des configs).
- Import/export local.
- Partage via QR code.
- Partage via Bluetooth.

### 5.15. Gamification
- XP (expérience gagnée via tests).
- Niveaux (progression).
- Badges (succès spécifiques).
- Défis (ex: "Trouver 5 pixels morts en 1 min").

### 5.16. Version Pro
- Mode technicien (interface dense).
- Diagnostic multiple (batch tests).
- Signature (ajout de signature sur rapport).
- Export avancé (formats pro, branding).

### 5.17. IA Avancée
- Score santé écran (0 à 100).
- Prédiction évolution (durée de vie estimée).
- Estimation réparation (coût approximatif).
- Assistant vocal/texte.
- Plan réparation (étapes suggérées).

### 5.18. Communauté
- Base pannes (crowdsourced).
- Statistiques anonymes (envoi optionnel).
- Comparaison modèles (fiabilité par marque).

---

## 6 & 7. ROADMAP PAR PHASES

*Note : Une seule phase peut être active à la fois. Aucune phase ne commence tant que la précédente n'a pas atteint son critère de fin.*

### PHASE 0 — Fondations
| Critère | Détails |
| :--- | :--- |
| **Objectif** | Mettre en place l'infrastructure de développement 100% mobile et l'architecture de base. |
| **Fonctionnalités** | Repository, Gradle, CI GitHub Actions, Architecture MVVM/MVI, Design system (thème cyberpunk). |
| **Fichiers/Modules** | `build.gradle`, `.github/workflows/build.yml`, `Theme.kt`, `MainActivity.kt`, `App.kt`. |
| **Difficulté** | Moyenne (configurer Gradle et CI sans PC demande de la rigueur). |
| **Risques** | Échec du build CI, incompatibilité des versions Gradle/Kotlin sur Termux. |
| **Dépendances** | Aucune. |
| **Tests nécessaires** | Push sur GitHub -> Vérifier que l'APK debug est généré dans les artifacts -> Installer sur device -> L'appli s'ouvre et affiche le thème cyberpunk. |
| **Critère de fin** | Build GitHub Actions vert + design system présent et appliqué. |

### PHASE 1 — Moteur visuel du test tactile
| Critère | Détails |
| :--- | :--- |
| **Objectif** | Créer le cœur du diagnostic tactile avec une grille interactive. |
| **Fonctionnalités** | ScannerCell, DeadZoneMap, ZoneState, Grille pixel, Résultats. |
| **Fichiers/Modules** | `TactileTestScreen.kt`, `ScannerCell.kt`, `DeadZoneMap.kt`, `TactileViewModel.kt`. |
| **Difficulté** | Haute (gestion précise des événements `MotionEvent` et rendu Canvas/Compose performant). |
| **Risques** | Faux positifs sur les ghost touches, latence de rendu, crash sur multitouch. |
| **Dépendances** | Phase 0. |
| **Tests nécessaires** | Toucher chaque cellule -> Vérifier changement d'état -> Tester ghost touch -> Vérifier génération de la DeadZoneMap. |
| **Critère de fin** | Test complet réel fonctionnel (grille 100% interactive et précise). |

### PHASE 2 — Persistance locale
| Critère | Détails |
| :--- | :--- |
| **Objectif** | Sauvegarder les résultats et l'historique localement. |
| **Fonctionnalités** | Stockage JSON, Historique des tests, Profils utilisateur. |
| **Fichiers/Modules** | `LocalDataSource.kt`, `HistoryRepository.kt`, `ProfileManager.kt`, `DataModels.kt`. |
| **Difficulté** | Faible. |
| **Risques** | Corruption de fichier JSON, fuites de mémoire sur gros historiques. |
| **Dépendances** | Phase 1. |
| **Tests nécessaires** | Faire un test -> Fermer l'app -> Rouvrir -> Vérifier que l'historique et la DeadZoneMap sont intacts. |
| **Critère de fin** | Données conservées après fermeture et redémarrage de l'appareil. |

### PHASE 3 — Accessibilité et contournement
| Critère | Détails |
| :--- | :--- |
| **Objectif** | Permettre l'utilisation de l'appareil malgré les zones mortes. |
| **Fonctionnalités** | NRAccessibilityService, FloatingButtonService, GestureController, ExternalButtonController. |
| **Fichiers/Modules** | `NRAccessibilityService.kt`, `FloatingOverlay.kt`, `GestureMapper.kt`, `AndroidManifest.xml` (permissions). |
| **Difficulté** | Très Haute (les services d'accessibilité sont complexes et restrinctifs sur Android). |
| **Risques** | Rejet par le système Android, conflits avec d'autres apps d'accessibilité, batterie. |
| **Dépendances** | Phase 2. |
| **Tests nécessaires** | Activer le service -> Utiliser le bouton flottant pour cliquer dans une zone morte -> Mapper un bouton physique. |
| **Critère de fin** | Utiliser une application tierce (ex: Navigateur) sans toucher la zone morte. |

### PHASE 4 — Launcher temporaire
| Critère | Détails |
| :--- | :--- |
| **Objectif** | Fournir un écran d'accueil de secours contournant les pannes. |
| **Fonctionnalités** | Launcher secours, Applications prioritaires, Navigation hors zones mortes. |
| **Fichiers/Modules** | `RescueLauncherActivity.kt`, `AppDrawer.kt`, `IconMover.kt`. |
| **Difficulté** | Moyenne. |
| **Risques** | Conflits avec le launcher système, permissions "Home" refusées. |
| **Dépendances** | Phase 3. |
| **Tests nécessaires** | Définir ScreeNyavo comme launcher par défaut -> Lancer 5 apps différentes -> Vérifier que les icônes évitent les zones mortes. |
| **Critère de fin** | 5 applications utilisables sans zone morte via le launcher. |

### PHASE 5 — Clavier adapté
| Critère | Détails |
| :--- | :--- |
| **Objectif** | Permettre la saisie de texte sur un écran partiellement cassé. |
| **Fonctionnalités** | Clavier adapté (IME), Touches déplacées, Fréquence lettres. |
| **Fichiers/Modules** | `ScreeNyavoIME.kt`, `KeyboardLayout.kt`, `KeyRemapper.kt`. |
| **Difficulté** | Haute (développement d'un InputMethodService). |
| **Risques** | Bugs de focus, incompatibilité avec certaines apps, lenteur. |
| **Dépendances** | Phase 4. |
| **Tests nécessaires** | Activer le clavier -> Ouvrir SMS -> Écrire un message complet en utilisant les touches redimensionnées. |
| **Critère de fin** | Écrire un SMS complet sans erreur et sans toucher de zone morte. |

### PHASE 6 — Prévention monitoring
| Critère | Détails |
| :--- | :--- |
| **Objectif** | Surveiller la santé de l'écran et prévenir l'utilisateur. |
| **Fonctionnalités** | Surveillance burn-in, Température, Temps écran, Conseils, Alertes. |
| **Fichiers/Modules** | `PreventionMonitor.kt`, `BurnInShifter.kt`, `UsageTracker.kt`, `AlertManager.kt`. |
| **Difficulté** | Moyenne. |
| **Risques** | Fausse alerte de température, consommation de batterie en arrière-plan. |
| **Dépendances** | Phase 5. |
| **Tests nécessaires** | Laisser l'écran allumé sur une image fixe -> Vérifier le déplacement des pixels (burn-in) -> Recevoir une alerte de temps écran. |
| **Critère de fin** | Alerte utile et pertinente après utilisation réelle prolongée. |

### PHASE 7 — Rapport + simulateur + urgence + Data Rescue
| Critère | Détails |
| :--- | :--- |
| **Objectif** | Fournir les outils de sortie de crise et d'export. |
| **Fonctionnalités** | Écran urgence, Gros boutons, Sauvegarde Data Rescue, Contact urgence, Rapport exportable, Simulateurs. |
| **Fichiers/Modules** | `EmergencyScreen.kt`, `DataRescueWorker.kt`, `ReportGenerator.kt`, `SimulatorFilters.kt`. |
| **Difficulté** | Haute (gestion des permissions stockage, génération PDF, accès rapide aux données). |
| **Risques** | Échec de l'extraction de données sur Android 11+, crash lors de la génération PDF. |
| **Dépendances** | Phase 6. |
| **Tests nécessaires** | Déclencher mode urgence -> Extraire contacts -> Générer rapport PDF -> Appliquer filtre simulateur. |
| **Critère de fin** | Rapport exportable (PDF/JSON) + sauvegarde Data Rescue testée et validée. |

### PHASE 8 — Diagnostic affichage complet
| Critère | Détails |
| :--- | :--- |
| **Objectif** | Couvrir tous les tests liés à la dalle (pixels, couleurs, lumière). |
| **Fonctionnalités** | Couleurs plein écran, Pixels morts/bloqués, Burn-in, Clouding, Bleeding, Scintillement, HDR, Luminosité, Contraste, AMOLED/LCD. |
| **Fichiers/Modules** | `DisplayTestScreen.kt`, `ColorPatternRenderer.kt`, `FlickerTest.kt`, `PanelDetector.kt`. |
| **Difficulté** | Moyenne. |
| **Risques** | La luminosité maximale peut surchauffer l'écran, le scintillement peut causer des maux de tête. |
| **Dépendances** | Phase 7. |
| **Tests nécessaires** | Lancer tous les patterns de couleur -> Vérifier détection AMOLED/LCD -> Tester le pattern de scintillement. |
| **Critère de fin** | Tests écran exploitables et visuellement parfaits (pas de banding, couleurs exactes). |

### PHASE 9 — IA
| Critère | Détails |
| :--- | :--- |
| **Objectif** | Intégrer l'intelligence artificielle pour l'analyse prédictive et visuelle. |
| **Fonctionnalités** | Scanner IA (fissures, taches), Score santé, Prédiction évolution, Estimation réparation, Assistant. |
| **Fichiers/Modules** | `AIAnalyzer.kt`, `CameraScanner.kt`, `HealthPredictor.kt`, `OnDeviceModel.tflite`. |
| **Difficulté** | Très Haute (intégration TensorFlow Lite, optimisation mémoire, calibration des modèles). |
| **Risques** | Modèle trop lourd pour l'APK, faux positifs sur les fissures, lenteur d'inférence. |
| **Dépendances** | Phases 0 à 8 (nécessite toutes les données de diagnostic). |
| **Tests nécessaires** | Prendre en photo un écran fissuré -> Vérifier détection -> Vérifier score de santé -> Vérifier estimation. |
| **Critère de fin** | IA utile, précise et rapide, uniquement après validation des phases 0-8. |

### PHASE 10 — Contrôle externe + Gamification + Pro + Communauté
| Critère | Détails |
| :--- | :--- |
| **Objectif** | Ajouter les fonctionnalités avancées, sociales et de monétisation/pro. |
| **Fonctionnalités** | USB/Bluetooth/Souris/Clavier/Manette, XP/Niveaux/Badges, Mode technicien/Signature, Base pannes/Stats anonymes. |
| **Fichiers/Modules** | `ExternalInputHandler.kt`, `GamificationEngine.kt`, `ProFeatures.kt`, `CommunitySync.kt`. |
| **Difficulté** | Moyenne à Haute (gestion des périphériques HID, synchronisation réseau optionnelle). |
| **Risques** | Complexité de la gestion des périphériques Bluetooth/USB, réticence des utilisateurs à envoyer des stats. |
| **Dépendances** | Phase 9. |
| **Tests nécessaires** | Brancher une souris -> Jouer avec le clavier -> Débloquer un badge -> Générer un rapport Pro signé. |
| **Critère de fin** | Chaque fonctionnalité doit justifier sa priorité et fonctionner sans dégrader les phases précédentes. |

### PHASE 11 — Polish + QA + Traduction + Publication
| Critère | Détails |
| :--- | :--- |
| **Objectif** | Finaliser l'application pour une sortie publique mondiale. |
| **Fonctionnalités** | Polish UI/UX, QA globale, Traductions (FR, EN, ES, etc.), Publication (Play Store / GitHub Releases). |
| **Fichiers/Modules** | `strings.xml` (toutes langues), `Proguard-rules.pro`, `ReleaseNotes.md`. |
| **Difficulté** | Faible (techniquement), mais demande du temps. |
| **Risques** | Refus par le Play Store (permissions accessibilité), bugs de traduction, crash sur des devices spécifiques. |
| **Dépendances** | Phase 10. |
| **Tests nécessaires** | Audit complet de toutes les phases -> Test sur 3 devices différents (tailles/résolutions) -> Vérification des traductions. |
| **Critère de fin** | Application publiable, stable, traduite et signée. |

---

## 8. RÈGLE DE MISE À JOUR DU DOCUMENT

Ce document est vivant. Il doit être maintenu avec une rigueur absolue.

**Après chaque phase terminée, le développeur (Nyavo) doit impérativement :**

1. **Marquer la phase terminée** : Ajouter un statut `[X] TERMINÉE` ou similaire dans le titre de la phase.
2. **Ajouter la date** : Insérer la date de validation finale (ex: `*Validé le : 2023-10-27*`).
3. **Noter les changements** : Ajouter un bref commentaire sous la phase pour noter les écarts par rapport au plan initial, les bugs connus restants, ou les ajustements faits.
4. **Mettre à jour la roadmap avant la phase suivante** : Relire les phases futures et ajuster les dépendances, les risques ou les fichiers concernés si la phase précédente a modifié l'architecture.

*Exemple de format de mise à jour :*
```markdown
### PHASE 1 — Moteur visuel du test tactile [X] TERMINÉE
*Validé le : 202X-XX-XX*
> **Notes** : Le multitouch 10 doigts cause un léger lag sur les devices < 4GB RAM. Ajout d'un fallback 5 doigts dans les settings.
```

**Règle d'or** : Ne jamais passer à la phase N+1 si le document `ROADMAP.md` n'a pas été mis à jour pour la phase N.
