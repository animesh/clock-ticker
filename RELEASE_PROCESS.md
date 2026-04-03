# Release & Testing Process: From Debug to Production

This document serves as a guide and record of the steps taken to test, refine, and ultimately release the **Hourly Buzz (Clock Ticker)** app.

## 🧪 Phase 1: Rapid Testing (Per-Minute Intervals)
To verify the vibration logic and background reliability without waiting for an hour, we implemented a "Rapid Test" mode.

1.  **Code Modification**: In `VibrationReceiver.java`, the `scheduleNext` method was temporarily modified to schedule the alarm for `Calendar.MINUTE, 1` instead of the next hour.
2.  **Notification Feedback**: The notification text was updated to include `(Testing - Every Minute)` to distinguish it from the final version.
3.  **Build Debug APK**:
    *   Command: `./gradlew app:assembleDebug`
    *   Output: `app/build/outputs/apk/debug/app-debug.apk`
4.  **Deployment via ADB**:
    *   Due to Gmail blocking debug APKs, we used direct ADB installation:
    *   `adb install -r app-debug.apk`
5.  **Verification**: Confirmed that the vibration triggered every 60 seconds, even with the screen off, using `setAlarmClock` and `WakeLock`.

## 🛠 Phase 2: Production Refinement
Once the core logic was verified, we prepared the app for its actual purpose.

1.  **Revert to Hourly Mode**:
    *   Modified `scheduleNext` to calculate the exact top of the next hour (`c.set(Calendar.MINUTE, 0)`).
    *   Ensured the alarm uses `AlarmManager.AlarmClockInfo` for maximum priority.
2.  **Logo Update**:
    *   Replaced the default XML icons with a high-resolution PNG (`ic_launcher_buzz.png`).
    *   Updated `AndroidManifest.xml` to point `android:icon` and `android:roundIcon` to the new drawable.
3.  **Version Increment**:
    *   Updated `app/build.gradle.kts`:
        *   `versionCode` incremented to **4**.
        *   `versionName` updated to **"1.3"**.

## 📦 Phase 3: Generating the Release Bundle (AAB)
The Play Store requires the Android App Bundle format for all new submissions.

1.  **Wizard Path**: In Android Studio, go to `Build` > `Generate Signed Bundle / APK...`.
2.  **Select Bundle**: Choose `Android App Bundle`.
3.  **Signing**:
    *   Used a secure Keystore file (`.jks`).
    *   Entered the Key Store password, Key alias, and Key password.
4.  **Build Variant**: Selected the `release` variant.
5.  **Output**: The resulting file is located at `app/release/app-release.aab`.

## 🚀 Phase 4: Google Play Console Submission
The final step was uploading the signed bundle for review.

1.  **Access Console**: Navigate to the [Google Play Console](https://play.google.com/console/).
2.  **Internal Testing/Production**:
    *   Navigate to `Test and release` > `Testing` > `Internal testing` (or `Production` for public release).
    *   Click `Create new release`.
    *   Upload `app-release.aab`.
3.  **Review Submission**:
    *   Summarize changes in the release notes (e.g., "Updated branding and improved background reliability").
    *   Navigate to `Publishing overview`.
    *   Click **"Send changes for review"** to start the official Google review process.

---
*This process ensures that every feature is physically tested on-device before being bundled for thousands of users.*
