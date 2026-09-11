# Build Instructions

ZAKA Hack Academy is a native Android app (Kotlin + Jetpack Compose). There is no web build.

## Toolchain actually used for the verified builds in this repo

| Component | Version used |
| --- | --- |
| JDK | 17 |
| Gradle | 9.3.1 |
| Android SDK platform | android-36 |
| Build tools | as resolved by AGP from `app/build.gradle.kts` |

## One-time setup

1. Install JDK 17 and the Android SDK (command-line tools are enough).
2. Point the build at your SDK by creating `local.properties` in the project root:

   ```properties
   sdk.dir=/absolute/path/to/android-sdk
   ```

   `local.properties` is machine-specific and is not committed.
3. Accept the SDK licences: `sdkmanager --licenses`.

## Debug build

```bash
./gradlew assembleDebug
```

Artifact: `app/build/outputs/apk/debug/app-debug.apk`

The debug build is signed with a debug keystore. Android Studio creates one automatically at
`~/.android/debug.keystore`; on a headless machine generate one:

```bash
keytool -genkeypair -v -keystore debug.keystore -storepass android -keypass android \
  -alias androiddebugkey -keyalg RSA -keysize 2048 -validity 10000 \
  -dname "CN=Android Debug,O=Android,C=US"
```

## Unit tests

```bash
./gradlew testDebugUnitTest
```

Note: the Robolectric-based tests (`ExampleRobolectricTest`, `GreetingScreenshotTest`) require
**Java 21** because they sandbox Android SDK 36. On JDK 17 they fail with
`Failed to create a Robolectric sandbox: Android SDK 36 requires Java 21`. That is a toolchain
requirement, not a product defect — run them on JDK 21.

## Lint

```bash
./gradlew lintDebug
```

## Release APK / AAB

```bash
./gradlew assembleRelease   # APK
./gradlew bundleRelease     # AAB
```

These require a **real release keystore**. None is present in this repository and none was
fabricated. Create your own and supply it through `signingConfigs` or Gradle properties before
running a release build; without it the release tasks will not produce a distributable artifact.

## Firebase

`google-services.json` is not committed. The build tolerates its absence (with a warning) and
Firebase-backed features stay inactive until a real configuration file is added.

## API keys

`.env.example` shows the expected key names. Never commit a real key — the Gemini provider reads its
key from configuration at runtime and the app works fully offline without one.
