
# Carryon Android App

This document provides a comprehensive overview of the Carryon Android application, including its architecture, features, and setup instructions.

## Project Overview

Carryon is a modularized Android application designed to help users create and manage resumes. The app is built using modern Android development practices, including Kotlin, Jetpack Compose, and a multi-module architecture. This structure enhances maintainability, scalability, and separation of concerns.

### Modules

The project is divided into the following modules:

-   **`app`**: The main application module that integrates all other modules and provides the entry point to the app. It is responsible for the main `Activity` and the overall application class.

-   **`core`**: This module contains shared code and utilities that are used across different feature modules. It is further divided into:
    -   **`common`**: Includes common utilities and helper classes that can be reused throughout the application.
    -   **`database`**: Manages the local data persistence using Room. It defines the database, DAOs (Data Access Objects), and entities.
    -   **`datastore`**: Utilizes Jetpack DataStore for storing simple key-value data, such as user preferences and authentication tokens.
    -   **`network`**: Handles the networking layer of the app. It is built with Retrofit for making API calls and includes a `MockInterceptor` for testing with mock data.
    -   **`ui`**: A collection of shared Jetpack Compose UI components, themes, and styles that ensure a consistent look and feel across the app.

-   **`feature`**: This module contains the different feature modules of the app, each responsible for a specific functionality:
    -   **`auth`**: Manages user authentication, including sign-up, login, and password management screens. It has its own `ViewModel`s and navigation graph.
    -   **`main`**: The main feature module that the user interacts with after authentication. It includes the home screen, resume management, and user profile.

-   **`network`**: A separate module for network-related tasks. This seems to be a redundancy, as there is also a `core:network` module. This should be consolidated into a single networking module to avoid confusion and code duplication.

## Implemented Features

### `feature:auth`

-   **User Authentication**: The `auth` module provides a complete authentication flow, including:
    -   **Sign-up and Login**: Users can create a new account or log in with their email and password.
    -   **Input Validation**: The sign-up and login forms include robust input validation for all fields, such as email format, password length, and password confirmation.
    -   **Session Management**: User sessions are managed using Jetpack DataStore, which securely stores access and refresh tokens.
    -   **Dashboard**: A welcoming dashboard with a horizontal pager introduces the app's features to new users.
    -   **Architecture**: The module follows a clean architecture with `ViewModel`s for business logic, `UseCase`s for validation, and composable screens for the UI.
    -   **Password Management**: Complete integration of forgot password, reset password, and change password flows.
    -   **Social Login**: Google OAuth login integration is fully implemented.
    -   **Token Management**: Refresh token functionality and logout with proper token cleanup.

### `feature:main`

-   **Home Screen**: The `main` module features a dynamic home screen that:
    -   Welcomes the user by name.
    -   Displays recent activity, such as recently edited resumes.
    -   Includes shimmer effects for a smooth loading experience.
    -   Features a "Pro Tip" card with random advice for improving resumes.
    -   Provides quick access to create a new resume or browse templates.
-   **Resume and Template Management**: This feature allows users to:
    -   View a list of available resume templates.
    -   Filter templates by category.
    -   The app fetches templates from a remote source and caches them locally in the Room database for offline access.
-   **Profile Screen**: The profile screen offers:
    -   A summary of the user's information.
    -   Options to manage account settings.
    -   The ability to switch between light and dark themes, with the selected theme persisted in DataStore.
    -   Complete logout functionality with token cleanup.
-   **PDF Viewing**: The app integrates a PDF viewer to display resumes, although the PDF generation logic is still in progress.

## API Endpoint Integration Status

### ✅ **Fully Integrated Endpoints**

-   **Authentication**:
    -   `POST /auth/register`: Register a new user.
    -   `POST /auth/login`: Log in a user.
    -   `POST /auth/refresh`: Refresh the authentication token.
    -   `POST /auth/logout`: Log out a user.
    -   `POST /auth/forgot-password`: Initiate the password reset process.
    -   `POST /auth/reset-password`: Reset the user's password.
    -   `POST /auth/change-password`: Change the user's password.
    -   `GET /auth/google/login`: Initiate Google login.
    -   `POST /auth/google/callback`: Handle the Google login callback.
-   **Home**:
    -   `GET /api/home/profile-details`: Fetch user profile details.

### 📋 **Home Module - APIs to Implement**

#### **Backend Requirements**

-   **User Profile Management**:
    -   `PUT /api/home/profile-details`: Update user profile details.
    -   `POST /api/home/profile/upload-avatar`: Upload user profile picture.
    -   `DELETE /api/home/profile/avatar`: Remove user profile picture.

-   **Resume Management**:
    -   `GET /api/home/user-resumes`: Fetch all resumes created by user.
    -   `POST /api/home/resume/create`: Create a new resume.
    -   `PUT /api/home/resume/{id}`: Update resume details.
    -   `DELETE /api/home/resume/{id}`: Delete a resume.
    -   `GET /api/home/resume/{id}/download`: Download resume as PDF/DOC.
    -   `GET /api/home/resume/{id}/share`: Generate shareable link for resume.

-   **Activity & Analytics**:
    -   `GET /api/home/activity-history`: Fetch user's activity history.
    -   `GET /api/home/resume-analytics/{id}`: Get analytics for a specific resume (views, shares, etc.).
    -   `GET /api/home/dashboard-stats`: Get user dashboard statistics.

-   **Notifications**:
    -   `GET /api/home/notifications`: Fetch user notifications.
    -   `PUT /api/home/notifications/mark-read/{id}`: Mark notification as read.
    -   `PUT /api/home/notifications/mark-all-read`: Mark all notifications as read.
    -   `DELETE /api/home/notifications/{id}`: Delete a notification.

#### **Frontend Requirements**

-   **Profile Screen**:
    -   Implement profile editing UI with all profile details.
    -   Add functionality to upload and display profile picture.
    -   Create profile settings screen with options for privacy, notifications, etc.
    
-   **Resume List Screen**:
    -   Implement resume listing with appropriate UI components.
    -   Add resume creation flow with template selection.
    -   Create resume detail screen with edit functionality.
    -   Implement resume sharing and download features.

-   **Dashboard**:
    -   Integrate activity history display.
    -   Add resume analytics visualization.
    -   Implement dashboard statistics components.
    -   Create notification center UI.

-   **State Management**:
    -   Create ViewModels for profile management (`ProfileViewModel`).
    -   Create ViewModels for resume management (`ResumeListViewModel`, `ResumeDetailViewModel`).
    -   Implement proper repository pattern methods in `HomeRepository`.
    -   Add proper loading/error states for all API calls.

#### **Additional Features Needed**

-   **Search & Filter**:
    -   `GET /api/home/resumes/search?q={query}`: Search resumes by keywords.
    -   `GET /api/home/resumes/filter?category={category}&date={date}`: Filter resumes.

-   **Collaboration**:
    -   `POST /api/home/resume/{id}/collaborators`: Add collaborators to resume.
    -   `DELETE /api/home/resume/{id}/collaborators/{userId}`: Remove collaborator.
    -   `GET /api/home/resume/{id}/collaborators`: Get list of collaborators.

-   **Template Management**:
    -   `GET /api/home/templates`: Get available templates.
    -   `POST /api/home/templates/custom`: Create custom template.
    -   `GET /api/home/templates/my-templates`: Get user's saved templates.

### 🔧 **Implementation Details**

#### **Authentication Architecture**
- **Repository Layer**: 
  - `AuthRepository` interface with all authentication methods
  - `AuthRepositoryImpl` with proper implementation for all endpoints
  - Proper handling of `SuccessResponse` wrapper for certain endpoints like Google login

- **ViewModel Layer**:
  - `AuthenticationViewModel`: Handles logout, password management (forgot, reset, change), and Google login
  - `ProfileDetailsViewModel`: Handles profile details fetching
  - `ForgotPasswordViewModel`: Manages forgot password flow
  - `ResetPasswordViewModel`: Manages reset password flow
  - `ChangePasswordViewModel`: Manages change password flow
  - Updated `AccountViewModel` with proper logout functionality

- **Data Layer**:
  - All endpoints properly integrated with Retrofit API services
  - Proper error handling and state management (Loading, Success, Error)
  - Session management with secure token storage and cleanup

#### **Error Handling & State Management**
- All API calls follow `ApiResponse<T>` pattern with proper loading, success and error states
- Proper token management with DataStore integration
- Error messages are properly propagated to UI layer
- Loading states are displayed during network operations

#### **Security Considerations**
- Access and refresh tokens are securely stored in DataStore
- Proper token cleanup on logout
- Secure session management

## Features in Progress

-   **Resume Creation and Editing**: The UI for creating and editing resumes is partially implemented with a tabbed layout for different sections (Personal Info, Experience, Education, Skills). The `ResumeFormViewModel` is set up to handle the form state, but the logic for saving the data to the database is not yet complete.
-   **Backend Integration**: While the networking layer is set up with Retrofit, the integration with the backend for features like profile updates and resume syncing is still ongoing.

## Getting Started

To build and run the project, follow these steps:

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/your-repository/carryon.git
    ```
2.  **Open in Android Studio**:
    -   Open Android Studio and select "Open an existing Android Studio project".
    -   Navigate to the cloned repository and select the `Carryon` directory.
3.  **Sync Gradle**:
    -   Allow Android Studio to sync the Gradle files and download the required dependencies.
4.  **Run the app**:
    -   Select the `app` configuration and run it on an emulator or a physical device.

## Architecture Overview

### **Layered Architecture**
The application follows a clean architecture pattern with clear separation of concerns:

- **Presentation Layer**: Compose UI components and ViewModels
- **Domain Layer**: Use cases and business logic
- **Data Layer**: Repository interfaces and data sources (network, database, preferences)

### **Key Technologies Used**
- **Kotlin**: Primary programming language
- **Jetpack Compose**: Modern UI toolkit
- **Hilt**: Dependency injection framework
- **Retrofit**: HTTP client for API communication
- **Room**: Local database
- **DataStore**: Preferences storage
- **Coroutines**: Asynchronous programming
- **Flow**: Reactive programming for state management

## Future Work

-   Complete the implementation of resume creation, editing, and management features.
-   Integrate the PDF generation and viewing functionality.
-   Finalize the profile management screen and connect it to the backend.
-   Add unit and integration tests to ensure the app's stability and correctness.
-   Consolidate the `network` module into the `core:network` module to avoid redundancy.
-   Implement deep linking for better navigation from external sources.
-   Add biometric authentication for enhanced security.
-   Implement offline-first architecture for better user experience.
