# SmartScreens — Android Application

A complete native Android application built with **Java and XML** demonstrating multi-activity explicit intent navigation, local authentication simulation via `SharedPreferences`, responsive UI layouts, and form validations.

---

## 📌 Assignment Purpose
This project is developed as part of university coursework for **Android Application Development (Assignment 2)**. The primary objective is to implement a robust, student-friendly, multi-screen Android application that adheres to Android SDK standards without third-party dependencies, Kotlin, or backend frameworks.

---

## 🚀 Key Features & The Seven Screens

1. **Screen 1: Splash Screen (`SplashActivity`)**
   - Branded introduction screen built with `ConstraintLayout`.
   - Custom SmartScreens vector logo, application title, and tagline: *"Simple. Smart. Connected."*
   - Automatically navigates to the Login screen after ~2 seconds.
   - Safe lifecycle cleanup prevents duplicate navigation upon screen rotation or recreation.

2. **Screen 2: Login Screen (`LoginActivity`)**
   - Clean `LinearLayout` inside a `ScrollView`.
   - Form inputs with password masking (`textPassword`) and email type (`textEmailAddress`).
   - Pre-configured demo login hint card.
   - Validation for empty inputs and invalid credentials.
   - Navigation links to Registration and Forgot Password.

3. **Screen 3: Registration Screen (`RegisterActivity`)**
   - Built with `LinearLayout` in a `ScrollView`.
   - Fields: Full Name, Email Address, Password, Confirm Password.
   - Validation rules:
     - No empty fields.
     - Full Name minimum 3 characters.
     - Email syntax validation (`Patterns.EMAIL_ADDRESS`).
     - Password minimum 6 characters.
     - Password confirmation match.
   - Saves new user records to local `SharedPreferences` and returns to Login.

4. **Screen 4: Forgot Password Screen (`ForgotPasswordActivity`)**
   - Account verification screen using `LinearLayout`.
   - Validates email format and verifies whether the email exists in local storage.
   - Shows simulated recovery notification and passes verified email to the Reset Password screen via Intent Extra.

5. **Screen 5: Reset Password Screen (`ResetPasswordActivity`)**
   - Password reset form using `LinearLayout`.
   - Enforces minimum 6 characters and password matching.
   - Updates the stored credentials in `SharedPreferences`.
   - Confirms update with Toast and redirects user to Login.

6. **Screen 6: Dashboard Screen (`DashboardActivity`)**
   - Landing screen with personalized welcome banner: *"Welcome, [Name]!"*.
   - Implements the mandatory **`GridLayout`** displaying four interactive module cards:
     - **Home**: Shows informative Toast notification.
     - **Tools**: Shows informative Toast notification.
     - **History**: Shows informative Toast notification.
     - **Profile**: Navigates to `ProfileActivity`.
   - Quick header logout icon and bottom logout button.

7. **Screen 7: Profile Screen (`ProfileActivity`)**
   - Built with **`ConstraintLayout`**.
   - Displays user avatar, full name, email address, and account status retrieved dynamically from `SharedPreferences`.
   - Navigation button back to Dashboard.
   - Secure Logout action that clears active session state and redirects to Login with `FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK` to prevent returning via the device Back button.

---

## 🛠️ Technologies & Architectural Requirements

- **Programming Language**: Java (100% Java, no Kotlin)
- **UI Framework**: XML Layouts (No Jetpack Compose)
- **Minimum SDK**: API 23 (Android 6.0 Marshmallow)
- **Compile / Target SDK**: API 34 (Android 14)
- **Layout Types Demonstrated**:
  - `ConstraintLayout` (Splash Screen & Profile Screen)
  - `LinearLayout` (Login, Register, Forgot Password, Reset Password)
  - `GridLayout` (Dashboard 2x2 module cards)
- **Data Persistence**: Android `SharedPreferences` via encapsulated `SessionManager`
- **Navigation**: Explicit `Intent` objects between Activities
- **Day/Night Theme Support**: Complete `res/values/colors.xml` and `res/values-night/colors.xml` pairing

---

## 🔑 Demo Login Credentials

For demonstration and grading purposes, a pre-seeded student account is available out of the box:

- **Email**: `demo@example.com`
- **Password**: `123456`
- **Display Name**: `Demo Student User`

> **Note**: You may also register any new account on the Registration screen and log in with those credentials immediately.

---

## 🧭 Navigation Flow Architecture

```
[SplashActivity] (2-sec delay)
       │
       ▼
 [LoginActivity] ────────┬───────────────┬────────────────────────┐
       │                 │               │                        │
       ▼                 ▼               ▼                        ▼
[DashboardActivity] [RegisterActivity] [ForgotPasswordActivity]   │
       │                 │               │                        │
       ▼                 └───────►───────┤                        │
[ProfileActivity]                        ▼                        │
       │                       [ResetPasswordActivity]            │
       │                                 │                        │
       └──────────────Logout──────►──────┴────────►───────────────┘
```

---

## 💻 How to Open and Run the Project in Android Studio

1. **Prerequisites**: Install [Android Studio](https://developer.android.com/studio) (Hedgehog, Iguana, Jellyfish, or newer) with Android SDK API 23–34 installed.
2. **Open Project**:
   - Launch Android Studio.
   - Select **File > Open...** (or click **Open** on the Welcome screen).
   - Browse to the `SmartScreens` folder and click **OK**.
3. **Gradle Sync**:
   - Allow Android Studio to automatically sync the Gradle configuration and download required libraries.
4. **Run the App**:
   - Connect an Android device (via USB with USB Debugging enabled) or start an Android Virtual Device (AVD) emulator.
   - Click the green **Run** button (▶) or press `Shift + F10`.
   - The app will launch directly into the **Splash Screen** and transition to **Login** after 2 seconds.

---

## 🔒 Known Limitations & Classroom Simulation Notice

This application is designed specifically for an academic university assignment:
- **Client-Side Simulation**: User authentication and session management are handled locally on device using `SharedPreferences`.
- **Classroom Password Handling**: Passwords are saved in local preferences for simulation clarity. In real-world production systems, client-side preferences should never store raw credentials; authentication must be executed against a hardened cloud backend using salted cryptographical hashes (e.g., PBKDF2/bcrypt) and encrypted OAuth2/JWT session tokens.
- **Simulated Recovery**: The Forgot Password feature verifies local account registration and proceeds directly to simulated password reset without sending outbound SMTP emails or SMS tokens.

---

## 🔗 GitHub Repository Placeholder

- **Repository URL**: https://github.com/kashiflaskani23-cmd/SmartScreens.git
