# 📊 Market Simulation Dashboard

A Java + JavaFX application that simulates stock trading using historical data and visualizes price, volatility, and portfolio performance.

---

## ⚙️ Requirements

- Java 21 installed
- JavaFX 21 SDK downloaded

Download JavaFX:
https://openjfx.io

Choose:
- Windows → x64
- Mac → AArch64 (Apple Silicon)

---

## 📁 Setup

1. Extract JavaFX SDK
2. Note the path to the `lib` folder

Example:
C:\javafx\lib
/Users/username/javafx/lib

---

## ▶️ Run (All Systems)

### Step 1: Compile

#### 🟢 Mac / Linux

javac --module-path /path/to/javafx/lib
--add-modules javafx.controls,javafx.fxml
-d out $(find . -name "*.java")

#### 🟢 Windows (PowerShell)
Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName } > sources.txt
javac --module-path "C:\path\to\javafx\lib" --add-modules javafx.controls,javafx.fxml -d out @sources.txt

---

### Step 2: Run

#### 🟢 Mac / Linux
java --module-path /path/to/javafx/lib
--add-modules javafx.controls,javafx.fxml
-cp out ui.App

#### 🟢 Windows
java --module-path "C:\path\to\javafx\lib" --add-modules javafx.controls,javafx.fxml -cp out ui.App

---

## Console Version (No JavaFX)
javac Main.java java Main

---

## Features

- Stock price visualization
- Rolling volatility calculation
- Trading simulation
- Portfolio tracking
- Trade logs

---

## ⚠ Notes

- Update JavaFX path before running
- CSV file must be inside `data/` folder