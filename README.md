# LearnEasy

A native Android application that provides an interactive learning experience for high school students across Mathematics and Science.

## Features

- **Lessons** — Structured educational content with read-aloud support
- **Quizzes** — Multiple-choice questions with scoring and performance history
- **Flashcards** — Quick revision cards with show/hide answer toggle
- **Progress** — Dashboard showing quiz history, scores, and trends
- **Settings** — Dark mode and text-to-speech controls

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 11 |
| Database | SQLite (via DatabaseHelper) |
| UI | Material Design, ConstraintLayout, RecyclerView |
| Architecture | MVC |
| Build | Gradle |
| Min SDK | API 21 |
| Target SDK | API 33 |

## Project Structure

```
app/
├── manifests/
├── java/
│   ├── activities/       # SplashActivity, LoginActivity, MainActivity, ...
│   ├── models/           # User, Lesson, QuizQuestion, QuizResult, Flashcard
│   ├── adapters/         # LessonAdapter, QuizResultAdapter
│   └── database/         # DatabaseHelper
└── res/
    ├── layout/
    ├── drawable/
    └── values/
```

## Getting Started

1. Clone the repository
2. Open in Android Studio 2023.1 or later
3. Sync Gradle and build the project
4. Run on an emulator (API 33 recommended) or a physical device (API 21+)

---

**Course:** Mobile Programming &nbsp;|&nbsp; **Submitted:** May 16, 2026
