# Underwatch
<p align="center">
<!--<img alt="Logo Banner" width="50%" src="https://github.com/SE-TINF22B6/Underwatch/assets/11832982/3be5656e-642a-4601-b716-2d651e1303f9"/>-->
<img alt="Logo Banner" width="100%" src="https://github.com/SE-TINF22B6/Underwatch/assets/122724021/3f8adb11-0176-4dd5-8002-2bbaa8ac5ef3"/>
<a href="https://underwatch.freemine.de/">

</p>

[![License](https://img.shields.io/github/license/SE-TINF22B6/Underwatch)](https://github.com/SE-TINF22B6/Underwatch/blob/main/LICENSE)
[![Java CI with Gradle](https://github.com/SE-TINF22B6/Underwatch/actions/workflows/gradle.yml/badge.svg)](https://github.com/SE-TINF22B6/Underwatch/actions/workflows/gradle.yml)
[![React CI](https://github.com/SE-TINF22B6/Underwatch/actions/workflows/react.yml/badge.svg)](https://github.com/SE-TINF22B6/Underwatch/actions/workflows/react.yml)
[![Spring Boot CI](https://github.com/SE-TINF22B6/Underwatch/actions/workflows/spring.yml/badge.svg)](https://github.com/SE-TINF22B6/Underwatch/actions/workflows/spring.yml)

![Alt](https://repobeats.axiom.co/api/embed/bd7f956aef3abb0274179cd83521e32f27852877.svg "Repobeats analytics image")


## Introduction

Underwatch is a game which was created as a student project at DHBW. 
We created a 2D-Pixelart-Hack'n'Slash-Shooting game which hopefully suits your tastes.
Currently the game is in a very bare bone but still playable state.

If you are looking for the documentation which was created during that course head over to the [📖 Wiki](https://github.com/SE-TINF22B6/Underwatch/wiki/System-Architecture-Documentation).

## 🗺️ Web Page

We are building a web page for the game which can be used to see the high scores of various players as well as browsing the game wiki for the lore of the game.

Additionally this page serves as advertisement for our game so potential players can discover us via a search engine.

## 🎮 Features

> Our mission is to provide as many players with an enjoyable experience as possible. 
> In order to achive that we are taking care that all of our features are implemented carefully and have gone through serious testing.

<details>
  <summary>🌈 Pretty Maps</summary>
  TODO
</details>

<details>
  <summary>🤖 Advanced AI</summary>
  TODO
</details>

<details>
  <summary>📊 Statistics Tracking</summary>
  TODO
</details>

<details>
  <summary>🗺️ Cozy Webpage</summary>
  TODO
</details>


## 🔩 Getting Started

You want to start contributing to this project? That's great! Here are some pointers on where to get you started in the department you want to contribute to.

Regardless of where you want to contribute, start by checking out this repository.

```bash
$ git pull git@github.com:SE-TINF22B6/Underwatch.git
```

### 🌈 Web development

The web page sources are located in the folder `Underwatch/website`. We use `npm` as a package manager.
First pull the dependencies via:

```bash
$ cd website
$ npm install
```

then run the development server with:

```bash
$ npm start
```

Changing any of the source files automatically reloads the development server effectively showing you the most recent changes you've made.

### 🕹️ Game development

The game is scaffolded with `Gradle` and is located in the `Underwatch/game` directory.
We use Java 17 as a development JDK. Verify your installation with:

```bash
$ java --version
```

Run the game via the `run` configuration:

```bash
$ cd game
$ ./gradlew run
```

> [!IMPORTANT]  
> If you're on Apples M1 architecture append the JVM argument `-XstartOnFirstThread` as this will allow the `LWJGL` application to start properly.

## 🧑‍🔬 Technologies

<p height=100px align="center">
  <a href="https://react.dev/">
    <img alt="Logo Banner" height="100px" src="https://upload.wikimedia.org/wikipedia/commons/3/30/React_Logo_SVG.svg"/>
  </a>
  <a href="https://libgdx.com/">
    <img alt="Logo Banner" height="100px" src="https://libgdx.com/assets/brand/stacked.png"/>
  </a>
  <a href="https://spring.io/projects/spring-boot">
    <img alt="Logo Banner" height="100px" src="https://spring.io/img/spring.svg"/>
  </a>
</div>


