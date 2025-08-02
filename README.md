
# Car UI Library – Jetpack Compose Adaptation for Android Automotive OS

## Overview

This repository provides a **Jetpack Compose-based adaptation of the Android Car UI Library** for Android Automotive OS (AAOS). Developed as part of a master's thesis, it demonstrates how Google's traditional XML-based Car UI components can be modernized using declarative, composable UIs, thus improving performance, modularity, and development experience for in-vehicle infotainment (IVI) applications.

Key objectives:
- Achieve feature and behavioral parity with the original Car UI Library
- Adhere to AAOS design guidelines and safety requirements
- Provide empirical performance comparison between XML and Compose approaches
- Serve as a modular foundation for next-generation automotive UI development

---

## Project Structure

```
car-ui-compose/
├── car-ui-compose-lib/        # Jetpack Compose adaptation of Car UI components
│   ├── src/                   # Core component implementations (Compose)
│   ├── res/                   # Theming, color, dimension resources
│   └── ...                    # Utility, theme, state management
│
├── prototypeA/                # Sample AAOS app based on AOSP paintbooth app using XML Car UI Library components
│   ├── src/                   # Activities and screens for demonstration
│   └── ...                    # Manifest, resources
│
├── prototypeB/                # Sample AAOS app using the adapted Compose-based components
│   ├── src/                   # Reference implementation for benchmarking
│   └── ...                    # Manifest, resources
│
├── prototypeA-benchmark/      # Macrobenchmark tests for XML app
│   ├── src/                   # Startup, scroll, and interaction benchmarks
│   └── ...                    
│
├── prototypeB-benchmark/      # Macrobenchmark tests for Compose app
│   ├── src/                   # Identical benchmarks for fair comparison
│   └── ...   
|                 
├── TestData/                  # Macrobenchmark & Android Profiler benchmarking logs and result data
│
|
├── build.gradle.kts           # Project-level Gradle build file
└── README.md                  # Project documentation (this file)
```

### Module Details

- **car-ui-compose-lib/**  
  The core library: Jetpack Compose implementations of Toolbar, Menu Items, SearchView, List Items, Preferences, etc., with AAOS compliance (restricted states, theming, overlays).
  
- **prototypeA/**  
  Baseline app using original XML Car UI Library based on AOSP car-ui-lib paintbooth application.

- **prototypeB/**  
  Sample Automotive app built entirely with Compose-based components for real-world testing and showcasing usage patterns. Ensures 1:1 functional comparison with the XML app for benchmarking and parity.

- **prototypeA-benchmark/** & **prototypeB-benchmark/**  
  Macrobenchmark modules that automate startup, scroll, and interaction tests using [Jetpack Macrobenchmark](https://developer.android.com/topic/performance/benchmarking/macrobenchmark). Enables empirical, reproducible performance evaluation.

- **TestData/**  
  Macrobenchmark & Android Profiler benchmarking logs and result data. Also includes python scripts used to plot the benchmark charts.
---

## Getting Started

1. **Clone the repository:**
    ```sh
    git clone https://github.com/jawahar-venugopal/car-ui-lib-compose-adoption.git
    cd car-ui-lib-compose-adoption
    ```

2. **Open with Android Studio** (Giraffe/Hedgehog or later recommended).

3. **Build the Modules:**
    - Use `prototypeA` for XML
    - Use `prototypeB` for Compose baseline

4. **Run on AAOS Emulator**  
   (Requires an [AAOS system image](https://developer.android.com/training/cars/testing/emulator); enable automotive features in AVD Manager).

5. **Benchmarking**  
   Execute benchmarks in `prototypeA-benchmark` and `prototypeB-benchmark` using the [Macrobenchmark Runner](https://developer.android.com/topic/performance/benchmarking/macrobenchmark/macrobenchmark-setup).

---

## Benchmarking & Evaluation

- **Metrics captured:**  
  - Cold & warm startup times
  - Frame rendering latency & jank
  - CPU and memory usage
  - Responsiveness under scroll and interaction

- **Tools used:**  
  - Jetpack Macrobenchmark
  - Android Profiler
  - Perfetto traces

- **Results:**  
  Compose-based adaptation generally shows improved rendering smoothness and lower recomposition frame count, at the cost of slightly higher native memory.

---

## Research Context

This project is part of the MSc thesis:
> **"Developing a Novel Android Component Library of Car UI Elements Using Jetpack Compose to Enhance Android Automotive Application Development"**  
> Author: Jawahar Venugopal  
> Institution: Liverpool John Moores University

---

## License

```
Copyright (C) 2025 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0
```

---

## Contact

- **Author:** Jawahar Venugopal  
- **Email:** jawahar456@gmail.com   
- **Institution:** Liverpool John Moores University  

---

**Contributions, suggestions, and benchmarking scripts for additional Car UI components are welcome!**
