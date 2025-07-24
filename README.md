# Hourly Buzz (Clock Ticker)

A simple Android application that provides a discreet vibration reminder every hour available at [Google Play Store](https://play.google.com/store/apps/details?id=com.fuzzyLife.hourlyvibration).

## Overview

The Hourly Buzz app is designed to help users keep track of time by providing a short vibration at the top of every hour. It features a simple interface to start and stop the hourly vibration service.

## How it Works

The app utilizes core Android components to achieve its functionality:

1.  **Main Interface (`MainActivity.java`)**:
    *   Provides "Start" and "Stop" buttons to control the hourly vibration service.
    *   Displays informative messages to the user based on the service's status (e.g., "Hourly vibration started," "Hourly vibration is already started," "No service running to stop").
    *   Handles runtime permission requests for `SCHEDULE_EXACT_ALARM` on Android 12 (API 31) and newer. If the permission is not granted, the app guides the user to the system settings.

2.  **Scheduling Alarms (`AlarmManager`)**:
    *   When the "Start" button is pressed (and the service isn't already running), the app uses `AlarmManager` to schedule an alarm that will trigger at the next exact hour.
    *   For devices running Android Marshmallow (API 23) and above, it uses `setExactAndAllowWhileIdle()` for precise timing, crucial for an hourly reminder.
    *   For older devices (pre-Marshmallow), it uses `setRepeating()` to schedule the alarm.
    *   The `SCHEDULE_EXACT_ALARM` permission is declared in the `AndroidManifest.xml` and requested at runtime on Android 12+ to ensure alarms can be scheduled reliably.

3.  **Handling Alarms (`VibrationReceiver.java`)**:
    *   This `BroadcastReceiver` is triggered when the `AlarmManager`'s scheduled alarm goes off.
    *   Upon receiving the alarm event:
        *   It triggers the device's vibrator (requires `android.permission.VIBRATE`, declared in `AndroidManifest.xml`).
        *   (Optional) It can display a notification to the user. On Android 13 (API 33) and newer, this requires the `POST_NOTIFICATIONS` permission. The app currently checks for this permission before attempting to post a notification. _(Note: A runtime request for this permission still needs to be implemented in MainActivity if notifications are a critical feature)._
        *   For devices on Android Marshmallow and above, it then reschedules the next alarm for the subsequent hour using `setExactAndAllowWhileIdle()`. This rescheduling also checks for the `SCHEDULE_EXACT_ALARM` permission on Android 12+. Rescheduling is skipped for pre-Marshmallow devices as `setRepeating` handles this.

## Key Features & Recent Improvements

*   **Reliable Hourly Vibrations**: Utilizes `AlarmManager` for timely hourly reminders.
*   **Simple User Interface**: Easy-to-use "Start" and "Stop" buttons.
*   **Informative Feedback**:
    *   The "Start" button now correctly indicates if the service is already scheduled ("Hourly vibration is already started").
    *   The "Stop" button accurately informs the user if there's no service running ("No service running to stop").
*   **Modern Android Compatibility**:
    *   **Exact Alarm Scheduling**: Includes runtime checks and guidance for the `SCHEDULE_EXACT_ALARM` permission on Android 12 (API 31) and newer, essential for the app's core functionality.
    *   **Notification Handling**: Added checks for `POST_NOTIFICATIONS` permission in the `VibrationReceiver` for Android 13 (API 33) and newer before attempting to display a notification.
*   **Accurate Rescheduling**:
    *   Refined alarm rescheduling logic in `VibrationReceiver` to more accurately target the next exact hour.
    *   Rescheduling logic in the receiver is now correctly skipped for pre-Marshmallow devices as `setRepeating` handles this.

## Links

*   **Internal Test Build**: [https://play.google.com/console/u/0/developers/8049225430376012149/app/4975802997404127867/tracks/4698124399982310016?tab=releaseDashboard](https://play.google.com/console/u/0/developers/8049225430376012149/app/4975802997404127867/tracks/4698124399982310016?tab=releaseDashboard)
*   **App Icon Kitchen**: [https://icon.kitchen/i/H4sIAAAAAAAAAz2OOw7CMBBE7zK0FIgGkRZxAtIhCn%2FWiYWDI3%2F4JMrdWTuIZrUzoxm9GU%2FhMkU0M7QI97angdAY4SJtYbr2M7JEondC1eVpIPM0sZbdyTsf2Ngc1f4gd9X7dVSNqsGlHNib61B9EZMdR0dYyuzZGFKJKRCV53iLVED0n0R2l16sszaotTZ4nV1Bv0I8dPBWc8%2F6yPdFErflC8EdnrzdAAAA](https://icon.kitchen/i/H4sIAAAAAAAAAz2OOw7CMBBE7zK0FIgGkRZxAtIhCn%2FWiYWDI3%2F4JMrdWTuIZrUzoxm9GU%2FhMkU0M7QI97angdAY4SJtYbr2M7JEondC1eVpIPM0sZbdyTsf2Ngc1f4gd9X7dVSNqsGlHNib61B9EZMdR0dYyuzZGFKJKRCV53iLVED0n0R2l16sszaotTZ4nV1Bv0I8dPBWc8%2F6yPdFErflC8EdnrzdAAAA)
*   **Feature Graphic Concept**: [https://claude.ai/public/artifacts/26e20c0a-5ee6-40d4-8a47-e653edce33f0](https://claude.ai/public/artifacts/26e20c0a-5ee6-40d4-8a47-e653edce33f0)
*   **Privacy Policy & Terms of Use**:
    *   Generator: [https://app-privacy-policy-generator.firebaseapp.com/](https://app-privacy-policy-generator.firebaseapp.com/)
    *   Hosted Policy: [https://doc-hosting.flycricket.io/clock-ticker-terms-of-use/dd349453-bb77-4a1e-8076-9b467e40ac44/terms](https://doc-hosting.flycricket.io/clock-ticker-terms-of-use/dd349453-bb77-4a1e-8076-9b467e40ac44/terms)
