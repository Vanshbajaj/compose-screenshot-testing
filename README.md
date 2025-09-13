# ComposeScreenshotOfficial (using com.android.compose.screenshot)

This sample demonstrates using the official Compose Preview Screenshot plugin `com.android.compose.screenshot` (0.0.1-alpha11).

Important notes:
- This plugin is alpha and experiments with AGP/Kotlin internals. The versions pinned here are based on the official docs recommending AGP 8.11.0-alpha06 and Kotlin 2.1.20 for alpha11.
- Using these pre-release AGP/Kotlin versions may require a matching Gradle version and Android Studio preview.
- If you prefer a stable setup, consider the Paparazzi sample instead (no emulator required).

To run:
1. Ensure you have a compatible Java JDK (Java 11 or compatible as required).
2. From project root:
   ```
   ./gradlew --stop
   ./gradlew :app:connectedAndroidTest --info
   ```
   or run specific connected tests / instrumentation tests from Android Studio.
3. The plugin generates screenshots under the build outputs used by the plugin. Check Gradle logs for exact paths.

If you want, I can also produce a version constrained to stable AGP/Kotlin but using the plugin in library-only mode (if possible). Use with caution and expect to adjust versions to match your environment.
