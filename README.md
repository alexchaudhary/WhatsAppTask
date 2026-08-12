# WhatsApp Task - Junior Android Developer

A WhatsApp-inspired messaging Android application developed as part of the Junior Android Developer (Kotlin) technical assessment.

## Project Overview

This project implements a functional WhatsApp-inspired chat application based on the provided Figma design.

The application focuses on:
- Chat list UI
- Conversation screen
- Local/mock messaging data
- Navigation
- Message sending
- Archive chats
- Read all chats
- Delete chats
- MVVM architecture
- Responsive Android UI

## Tech Stack

- Kotlin
- Android SDK
- Jetpack Compose
- MVVM Architecture
- ViewModel
- Material Design
- Local/In-memory mock data
- Gradle Kotlin DSL

## Architecture

The project follows MVVM architecture with separation between UI, ViewModel and data layers.

```text
app/
├── data/
│   ├── datasource/
│   ├── model/
│   └── repository/
│
├── ui/
│   ├── screens/
│   │   ├── authorization/
│   │   ├── chat/
│   │   └── chats/
│   └── theme/
│
├── viewmodel/
│
└── MainActivity.kt
```

### Data Layer

- **Chat & Message models:** Define the data structures used by the application.
- **Mock Data & Repository:** Manages local chat and message data and provides data operations to the ViewModel.


### ViewModel Layer

Handles UI state and communication between the UI and repository using StateFlow.

### UI Layer

Contains the chat list, conversation screen, and reusable Jetpack Compose components.

## Implemented Features

### Chat List
- Display conversations with profile images, contact names, last messages, and timestamps.
- Unread message indicators & badges.
- Full scrollable list support.

### Chat / Conversation
- Open conversation from chat list with seamless back navigation.
- Display incoming and outgoing messages with timestamps.
- Functional message input field and send button (newly sent message appears instantly).

### Chat Actions
- **Archive** selected conversations.
- **Read All** (clears active badge counts).
- **Delete** selected chats from the repository flow.

## Implemented Screens
- Chats / Home screen dashboard
- Chat conversation detail thread screen
- Authorization Gateway placeholder screen

## Figma Implementation
The application was implemented based on the provided WhatsApp UI Figma reference, matching layout constraints, colors, and typography weight where applicable.

## Data
No production backend is used. The application uses local/in-memory mock data through the repository layer so that a real API or database can be integrated later without requiring major UI changes.

## How to Run

1. Clone the repository:
```bash
git clone https://github.com/alexchaudhary/WhatsAppTask.git
```
2.Open the WhatsAppTask project in Android Studio.
3.Allow Gradle to sync completely.
4.Connect an Android device or start an Android Emulator.
5.Select the app configuration and run the application

## Build APK

To generate a debug APK, execute the following commands in the terminal:

On Linux/macOS:
```bash
./gradlew assembleDebug
```

On Windows:
```bash
.\gradlew.bat assembleDebug
```

The APK will be generated at:
`app/build/outputs/apk/debug/app-debug.apk`

## Assumptions / Limitations
- Messaging is implemented using local mock/in-memory data.
- No real backend or live authentication service is connected.
- Data states are stored strictly in local memory and reset on process death.
- The application focuses on the core assessment flow rather than a full clone.

## Third-Party Libraries
The project primarily uses native official Android SDK and Jetpack/Material components.


## Submission

**GitHub Repository:**  
https://github.com/alexchaudhary/WhatsAppTask

A working debug APK and screen recording are provided separately as part of the assessment submission.

**Author:** Alex Chaudhary
