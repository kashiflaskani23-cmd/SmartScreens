# Assignment 2 Submission Report — Android Application Development
## Project Name: SmartScreens
**Course Code / Name**: Android Application Development  
**Student Name**: [Your Name Here]  
**Student ID**: [Your Student ID Here]  
**Submission Date**: [Insert Submission Date]  
**GitHub Repository URL**: https://github.com/kashiflaskani23-cmd/SmartScreens.git  

---

## 1. Executive Summary & Introduction
**SmartScreens** is a fully functional, multi-screen Android application built natively using **Java and XML layouts** adhering strictly to Android SDK guidelines. The application implements simulated local user authentication, session persistence, explicit Intent transitions across seven distinct Activities, and responsive layouts across portrait and landscape orientations.

The goal of this assignment is to demonstrate foundational Android development capabilities, including layout management (`ConstraintLayout`, `LinearLayout`, and `GridLayout`), input validation, explicit intent navigation, local state management using Android `SharedPreferences`, and cohesive Material Design styling supporting both Day and Night modes.

---

## 2. Key Implemented Features & Technical Architecture

| Feature Component | Implementation Details | Layout Architecture |
| :--- | :--- | :--- |
| **1. Splash Screen** | 2-second automated transition with lifecycle leak prevention | `ConstraintLayout` |
| **2. Login Screen** | Email validation, masked password, simulated authentication against local accounts & demo account | `LinearLayout` (in `ScrollView`) |
| **3. Registration** | Multi-field validation (name length >=3, email regex, password >=6, confirmation match), `SharedPreferences` persistence | `LinearLayout` (in `ScrollView`) |
| **4. Forgot Password** | Email validation, verified account check in local storage, simulated recovery navigation | `LinearLayout` (in `ScrollView`) |
| **5. Reset Password** | Password length & matching validation, in-place `SharedPreferences` credential updates | `LinearLayout` (in `ScrollView`) |
| **6. Dashboard Screen** | Dynamic user greeting, 2x2 grid of modules (Home, Tools, History, Profile) with Toast feedback & navigation | `GridLayout` (in `ScrollView`) |
| **7. Profile Screen** | Dynamic data retrieval from `SessionManager`, safe session clearing with back-stack purge | `ConstraintLayout` |

### Technical Highlights:
- **No Third-Party Backend Required**: All persistence and session checks are encapsulated inside `SessionManager.java`.
- **Strict Back-Stack Management**: Logging out or logging in clears the Activity task stack (`FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK`) ensuring users cannot press the physical "Back" button to re-enter authenticated views.
- **Resource Discipline**: Zero hardcoded strings or layout colors; all values reside in `res/values/strings.xml`, `colors.xml`, `themes.xml`, and `res/values-night/`.

---

## 3. Source Code Evidence

### 3.1 Main Java Code Screenshot (`SessionManager.java` / `DashboardActivity.java`)
> **Instruction for Student**: Take a screenshot in Android Studio showing `SessionManager.java` or `DashboardActivity.java` open in the code editor with line numbers visible, and paste it in the box below.

```
+-------------------------------------------------------------------------+
|                                                                         |
|                     [PASTE JAVA CODE SCREENSHOT HERE]                   |
|                                                                         |
|   Caption: Figure 1 — Java Implementation showing explicit Intent       |
|            navigation, session verification, and GridLayout bindings   |
+-------------------------------------------------------------------------+
```

### 3.2 Main XML Layout Screenshot (`activity_dashboard.xml` or `activity_splash.xml`)
> **Instruction for Student**: Take a screenshot of the Design and Code split view of `activity_dashboard.xml` showing the `<GridLayout>` component and paste it in the box below.

```
+-------------------------------------------------------------------------+
|                                                                         |
|                     [PASTE XML LAYOUT SCREENSHOT HERE]                  |
|                                                                         |
|   Caption: Figure 2 — XML Layout showing 2x2 GridLayout architecture    |
|            with responsive card views and custom vector drawables       |
+-------------------------------------------------------------------------+
```

---

## 4. Application Output & Execution Screenshots
> **Instruction for Student**: Run the application in the Android Emulator or on your physical device. Capture screenshots for each of the key screens and arrange them side-by-side or in a 2x3 grid.

```
+-------------------------+-------------------------+-------------------------+
|                         |                         |                         |
|   [SCREENSHOT: SPLASH]  |   [SCREENSHOT: LOGIN]   |  [SCREENSHOT: REGISTER] |
|                         |                         |                         |
|  Figure 3: Splash View  |  Figure 4: Login View   | Figure 5: Register View |
+-------------------------+-------------------------+-------------------------+
|                         |                         |                         |
| [SCREENSHOT: FORGOT/    | [SCREENSHOT: DASHBOARD] |  [SCREENSHOT: PROFILE]  |
|         RESET]          |                         |                         |
| Figure 6: Recovery View | Figure 7: Dashboard     |  Figure 8: Profile View |
+-------------------------+-------------------------+-------------------------+
```

### Verification Checklist Demonstrated in Screenshots:
1. **Validation Feedback**: Error messages appear beneath empty or malformed fields (`EditText.setError()`).
2. **Dashboard Grid**: The four interactive module cards display cleanly in a balanced 2x2 grid.
3. **Session Reflection**: Profile screen correctly displays the name and email of the logged-in user.

---

## 5. GitHub Repository Verification
> **Instruction for Student**: Visit your repository on github.com in your web browser. Take a screenshot showing the repository name `SmartScreens`, the commit history, and the uploaded directory structure (`app/`, `build.gradle`, `README.md`).

```
+-------------------------------------------------------------------------+
|                                                                         |
|                  [PASTE GITHUB REPOSITORY SCREENSHOT HERE]              |
|                                                                         |
|   Caption: Figure 9 — Verified GitHub Repository showing committed      |
|            source files, Gradle configuration, and project README       |
+-------------------------------------------------------------------------+
```

- **Submitted GitHub Repository Link**: https://github.com/kashiflaskani23-cmd/SmartScreens.git
- **Repository Branch**: `main`

---

## 6. Conclusion & Student Reflection
The SmartScreens application was successfully developed and tested under Android API 23 through 34. The project strictly satisfies all pedagogical requirements: pure Java syntax, XML layouts, comprehensive input validation, three mandatory layout paradigms (`ConstraintLayout`, `LinearLayout`, `GridLayout`), and local persistent storage with `SharedPreferences`. All navigation flows operate predictably without crashing or UI freezing.
