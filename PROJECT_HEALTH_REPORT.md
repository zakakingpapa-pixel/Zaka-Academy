# Project Health Report

Every line below reflects a command that was actually run on this codebase. Nothing here is
projected or assumed.

## Verification results

| Check | Command | Result |
| --- | --- | --- |
| Kotlin compilation | `gradle compileDebugKotlin` | BUILD SUCCESSFUL |
| Unit tests | `gradle testDebugUnitTest` | 24 tests, 22 passed, 2 failed (see below) |
| Android lint | `gradle lintDebug` | BUILD SUCCESSFUL — 0 errors, 38 warnings |
| Debug APK | `gradle assembleDebug` | BUILD SUCCESSFUL — `app/build/outputs/apk/debug/app-debug.apk` (~23.5 MB) |
| Release APK / AAB | not run | No release keystore exists; no signing credentials were created |

### The two failing tests

`ExampleRobolectricTest` and `GreetingScreenshotTest` fail with:

```
java.lang.UnsupportedOperationException: Failed to create a Robolectric sandbox:
Android SDK 36 requires Java 21 (have Java 17)
```

This is a JDK requirement of the Robolectric sandbox on the build machine, not a defect introduced
by this work, and it is independent of the new code. Run the suite on JDK 21 to execute them.

### New tests added

`app/src/test/java/com/example/QuestionTesterUnitTest.kt` — 16 tests, all passing. They cover:

- every chapter producing practice questions, and question IDs being unique;
- the model answer grading as ✅ Correct and an off-topic answer grading as ❌ Incorrect;
- a partial answer landing strictly between, and a blank answer being "not attempted";
- a reworded answer still scoring (proving grading is not exact-text matching);
- a three-step progressive hint ladder;
- exam paper size, revision mode targeting weak chapters, and MCQ scoring;
- progress reporting `null` (not a fake score) when there are no attempts, and flagging a weak
  chapter from real recorded data;
- the daily challenge being stable for a given date;
- the Error Solver recognising a known Python error and refusing to invent a cause for unknown text.

## Data safety

The Room database (`zaka_academy_database`) moved from version 1 to version 2. Destructive
migration was **removed** and replaced with an explicit `MIGRATION_1_2` that only runs
`CREATE TABLE IF NOT EXISTS` for the three new tables (`written_attempts`, `exam_results`,
`daily_challenges`). No existing table is dropped or altered, so installed users keep their
progress, quiz history, flashcard state and streak.

## What was preserved

All eight chapters, their topics and notes, the existing quizzes, flashcards, AI Teacher, topic
detail and settings screens, and every existing navigation route remain in place and untouched.
The new screens are additions reachable from the dashboard.

## Known limitations (stated, not worked around)

- **No on-device code execution.** Android cannot safely run arbitrary user code in-process, so the
  Error Solver is a static analyser and says so in its output.
- **No release artifact.** Producing one requires a real keystore, which is not in this repository.
- **Cloud AI is optional.** Without a configured provider the app grades and teaches entirely
  offline, and labels the offline evaluator by name.
- **Firebase is inert** until a real `google-services.json` is supplied.
- **Lint warnings (38)** are pre-existing style/deprecation notices; no lint errors exist.

## Not verified

The new screens have not been exercised on a device or emulator in this session — only compiled,
unit-tested at the logic layer, and packaged into a debug APK.
