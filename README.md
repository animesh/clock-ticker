# Hourly Buzz (Clock Ticker)

A simple Android application that provides a discreet, powerful vibration reminder every hour. Available at [Google Play Store](https://play.google.com/store/apps/details?id=com.fuzzyLife.hourlyvibration).

## Overview

The Hourly Buzz app helps users keep track of time by providing a Morse code vibration pattern ("GOD") at the top of every hour. It features a simple interface to start and stop the hourly vibration service.

## How it Works

The app utilizes core Android components to achieve its functionality:

1.  **Main Interface (`MainActivity.java`)**:
    *   Provides "Start" and "Stop" buttons to control the hourly vibration service.
    *   Handles runtime permission requests for `POST_NOTIFICATIONS` on Android 13+.
    *   Features a "Shake" animation feedback when the vibration is triggered.

2.  **Scheduling Alarms (`AlarmManager`)**:
    *   Uses `setAlarmClock()` (the highest priority alarm API) to ensure reliability even when the device is in deep sleep (Doze mode).
    *   Calculates the exact top of the next hour (e.g., 10:00, 11:00) to keep the reminders synchronized with the wall clock.

3.  **Handling Alarms (`VibrationReceiver.java`)**:
    *   Acquires a `WakeLock` to ensure the CPU stays awake long enough to complete the full vibration pattern.
    *   Uses `VibratorManager` (API 31+) and `VibrationAttributes` (USAGE_ALARM) to bypass "Do Not Disturb" and system silence modes.
    *   Posts a high-priority notification with an alarm category.

## Key Features & Recent Improvements

*   **Strong Morse Code Vibration**: Features a triple-length Morse code pattern for "GOD" (`--. --- -..`) at maximum hardware intensity (Amplitude 255), making it easily felt through pockets or clothing.
*   **Clock-Synchronized Timing**: Alarms trigger exactly at `:00` minutes of every hour, regardless of when the app was started.
*   **Deep Sleep Reliability**:
    *   **`setAlarmClock`**: Bypasses Android's Doze mode restrictions that normally delay background tasks.
    *   **WakeLock Management**: Guarantees the vibration pattern finishes even if the screen is off.
*   **Modern Android Compatibility**:
    *   **Android 13+ Support**: Explicitly handles `VibrationAttributes` and `POST_NOTIFICATIONS` permissions.
    *   **VibratorManager**: Uses the latest API 31+ methods for modern hardware.

## Links & Acknowledgments

We are grateful to the following tools and services that helped in the development and deployment of this app:

*   **Android Studio & AI Agent**: This app was refactored and optimized using **Android Studio** and its built-in **AI Android Developer Agent**, which provided expert guidance on modern Android APIs and background reliability.
*   **Google Play Store**: [Public Listing](https://play.google.com/store/apps/details?id=com.fuzzyLife.hourlyvibration)
*   **Internal Test Build**: [Google Play Console](https://play.google.com/console/u/0/developers/8049225430376012149/app/4975802997404127867/tracks/4698124399982310016?tab=releaseDashboard)
*   **App Icon Kitchen**: [Icon Design](https://icon.kitchen/i/H4sIAAAAAAAAAz2OOw7CMBBE7zK0FIgGkRZxAtIhCn%2FWiYWDI3%2F4JMrdWTuIZrUzoxm9GU%2FhMkU0M7QI97angdAY4SJtYbr2M7JEondC1eVpIPM0sZbdyTsf2Ngc1f4gd9X7dVSNqsGlHNib61B9EZMdR0dYyuzZGFKJKRCV53iLVED0n0R2l16sszaotTZ4nV1Bv0I8dPBWc8%2F6yPdFErflC8EdnrzdAAAA) - Used for generating adaptive launcher icons.
*   **Feature Graphic Concept**: [Claude AI Artifact](https://claude.ai/public/artifacts/26e20c0a-5ee6-40d4-8a47-e653edce33f0) - Used for visual asset inspiration.
*   **Privacy Policy & Terms of Use**:
    *   [App Privacy Policy Generator](https://app-privacy-policy-generator.firebaseapp.com/) - Used for generating legal documents.
    *   [Flycricket Hosted Policy](https://doc-hosting.flycricket.io/clock-ticker-terms-of-use/dd349453-bb77-4a1e-8076-9b467e40ac44/terms) - Used for hosting the privacy policy.
