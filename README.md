# Smart Pantry Manager

A Java Android app prototype for managing pantry ingredients and suggesting recipes only when all listed ingredients are present.

## Technology
- Java and Android Studio
- SQLite via `SQLiteOpenHelper`
- Android ListView with `ArrayAdapter`
- Local persistent storage

## Open and run
1. Unzip the project.
2. Open Android Studio and choose **Open**.
3. Select the `SmartPantryManager` folder.
4. Allow Gradle sync to finish. Install Android SDK 35 if Android Studio asks.
5. Start an Android emulator (API 23 or newer) or connect an Android device with USB debugging enabled.
6. Press **Run**.

## Features in this starter
- Pantry create, list, update and delete
- Quantity, unit and optional expiry date
- 20 seeded recipes
- Strict recipe ingredient-name matching with simple plural normalization
- Recipe detail view
- Settings preference stored locally

## Important notes
- The seed recipe logic currently treats each listed recipe ingredient as requiring at least 1 unit, regardless of the unit selected by the user. This is an explicit simplifying assumption and should be discussed in the report.
- The settings screen stores an expiry-alert preference; it does not schedule notifications.
- Verify and test the project in your installed Android Studio before submission. Capture your own screenshots, make your own Git commits over the development process, and record a narrated demonstration of the app running.
