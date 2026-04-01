# 🌿 Plant Disease Detector

An Android application that helps farmers, gardeners, and agricultural enthusiasts identify plant diseases quickly and accurately — right from their smartphone camera.

---

## 📱 Overview

Plant Disease Detector is a native Android app built with **Kotlin** that leverages machine learning to analyze images of plant leaves and detect diseases. Users can capture or upload a photo of a plant, and the app returns a diagnosis along with relevant information about the detected condition.

---

## ✨ Features

- 📷 **Camera Integration** — Capture plant leaf images directly within the app
- 🖼️ **Gallery Upload** — Select existing images from device storage
- 🤖 **ML-Powered Detection** — On-device or API-based model for plant disease classification
- 📊 **Diagnosis Results** — Clear display of detected disease with confidence score
- 🌱 **Multi-Plant Support** — Works across a range of common crops and plants

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin 1.9.23 |
| Platform | Android |
| Build System | Gradle 8.x (Kotlin DSL) |
| Android Gradle Plugin | 8.3.2 |
| Min SDK | Android 7.0+ (API 24) |

---

## 🚀 Getting Started

### Prerequisites

- Android Studio **Hedgehog** (2023.1.1) or later
- JDK 17+
- Android SDK with API level 24 or higher

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/shiv2130/App_for_Plant_Disease_Detector.git
   cd App_for_Plant_Disease_Detector
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select **File → Open** and navigate to the cloned folder

3. **Sync Gradle**
   - Android Studio will prompt you to sync — click **Sync Now**
   - Wait for all dependencies to download

4. **Run the app**
   - Connect a physical Android device or start an emulator
   - Click the **Run ▶** button or press `Shift + F10`

---

## 📁 Project Structure

```
App_for_Plant_Disease_Detector/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/       # Kotlin source files
│   │   │   ├── res/        # Layouts, drawables, strings
│   │   │   └── AndroidManifest.xml
│   │   └── test/           # Unit tests
│   └── build.gradle.kts
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

---

## 🧪 How It Works

1. The user opens the app and either captures a photo or picks one from their gallery.
2. The image is preprocessed and passed to the disease detection model.
3. The model returns a predicted disease class along with a confidence score.
4. Results are displayed to the user with the disease name and suggested next steps.

---

## 🤝 Contributing

Contributions are welcome! To get started:

1. Fork the repository
2. Create a new branch: `git checkout -b feature/your-feature-name`
3. Make your changes and commit: `git commit -m "Add your feature"`
4. Push to your fork: `git push origin feature/your-feature-name`
5. Open a Pull Request

Please make sure your code follows the existing style and that all existing tests pass before submitting.

---

## 📄 License

This project is open source. Feel free to use, modify, and distribute it with attribution.

---

## 👤 Author

**Shiv** — [@shiv2130](https://github.com/shiv2130)

---

> 🌾 *Built to support smarter, healthier farming through accessible AI.*
