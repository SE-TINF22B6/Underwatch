<!-- markdown-toc start - Don't edit this section. Run M-x markdown-toc-refresh-toc -->
**Table of Contents**

- [1. Introduction](#1-introduction)
  * [1.1 Overview](#11-overview)
  * [1.2 Scope](#12-scope)
  * [1.3 Definitions, Acronyms and Abbreviations](#13-definitions-acronyms-and-abbreviations)
  * [1.4 References](#14-references)
- [2. Functional requirements](#2-functional-requirements)
  * [2.1 Overview](#21-overview)
    + [2.1.1 General Use-Case](#211-general-use-case)
    + [2.1.2 Important Entities and Classes](#212-important-entities-and-classes)
  * [2.2 Use Cases](#22-use-cases)
    + [2.2.1 Upload Highscores](#221-upload-highscores)
    + [2.2.2 Filter Highscores](#222-filter-highscores)
    + [2.2.3 Look Up Mobs in Wiki](#223-look-up-mobs-in-wiki)
- [3. Nonfunctional requirements](#3-nonfunctional-requirements)
  * [3.1 Availability](#31-availability)
    + [3.1.1 Uptime](#311-uptime)
    + [3.1.2 Responsiveness](#312-responsiveness)
  * [3.2 Performance](#32-performance)
    + [3.2.1 High FPS](#321-high-fps)
    + [3.2.2 Stable Physics](#322-stable-physics)
  * [3.3 Usability](#33-usability)
    + [3.3.1 Choice of Data](#331-choice-of-data)
- [4. Technical constraints](#4-technical-constraints)

<small><i><a href='http://ecotrust-canada.github.io/markdown-toc/'>Table of contents generated with markdown-toc</a></i></small>

<!-- markdown-toc end -->
# 1. Introduction

## 1.1 Overview

We are creating a rougelike hack'n'slash game in which players can explore a pixel art world and hack their way thorugh countless enemies in order to stay alive and finish the game.

Additionally we are creating a web page, where we show statistics about the player runs as well as let the players read up on the game lore or look up ingame items, enemies and maps in a wiki.

## 1.2 Scope
This Software Requirements Specification (SRS) document covers the complete system of Underwatch. It encompasses both functional and non-functional requirements necessary for the successful development, deployment, and operation of the platform. The document aims to provide a comprehensive understanding of the system's architecture, features, and limitations.

## 1.3 Definitions, Acronyms and Abbreviations
> Here will be Definitions, Acronyms and Abbreviations for the project, we still have to figure out how to auto-generate those in markdown

## 1.4 References
> ![TODO] hier die Dokumente verlinken

---
# 2. Functional requirements

## 2.1 Overview

### 2.1.1 General Use-Case

Underwatch is comparable to almost all of the games on the game market. 
Meaning that there is a web page promoting the game as well as giving out additional information in the form of statistics.
The game differs itself from the pure techonological standpoint since we are developing for the target platforms Windows, Linux and MacOS.

The application can be split into the following sections:
 
1. Gameplay Loop
2. Website Activities

![General Usecase](./assets/general_use_case.drawio.svg)

### 2.1.2 Important Entities and Classes

![Revised Class Diagram](./assets/revisedClassDiagram.svg)

1. **WorldController**: This class handles the input of the user. Additionally the physics engine is also anchored in this class.
2. **WorldParser**: The WorldParser reads the TileMap from the FileSystem and parses some of the layers in order to add lights, walls or enemies to this game map.
3. **AnimatedGameObject**: An AnimatedGameObject combines the animation, a physical body and light emissions which is used by our Entity Component System (ECS).

## 2.2 Use Cases

In the following subsection, we will depict the most important use cases of our project and display it visually if necessary and possible.

### 2.2.1 Upload Highscores

Every user is able to play the game and try their best in order to beat the game. They may find their run/attempt good enough and want to see how good their score compares to all other players.

We envision the Game Over Screen to look like this:
![Create Highscores Mockup](./assets/create_highscore.png)

Implementationally it may look something like this:
![Create Highscore](./assets/player_enter_highscore.svg)

**Preconditions**:
This mockup has been created without any data to back it up. 
There needs to be a complete gameplay loop implemented so the player can have hp, collect coins, kill enemies and last but not least die.

**Postconditions**:
The highscore/score collection is the main data source for the web page. Therefore after this use case is realized a meaningful web page can be built.

**Estimated efforts**: low

**Linked user stories**: #109, #110

### 2.2.2 Filter Highscores

Comparing scores, seeing the ladder and competing for the high score is an essential part of the game's ecosystem. Users therefore have to have good way to filter the leaderboard in order to look for their name or a score range as well as best scores in a given date range.

On the page it could look like this:

![Filter Highscore Mockup](./assets/filter_highscores_mockup.png)

Schematically the process looks like this:
![Create Highscore](./assets/filter_highscore.svg)

**Preconditions**:
A set of user high scores accumulated over has to be submitted to a running back end, which is accessable via a RESTful endpoint.

**Postconditions**:
With the data visible and filterable other use cases such as "champion of the month" or "seasons" can be realized.

**Estimated efforts**: large

**Linked user stories**: #86, #54

### 2.2.3 Look Up Mobs in Wiki

As we have a variety of enemies and other mobs a player wants to look up some information about them.
Navigating the web page to fulfill this is necessary. 
To get a good overview the wiki articles and items should be divided into sections.
When navigating into one of the sections the overview should still be visible for a quick switch.

On the page it could look like this:

![Wiki](./assets/wiki.png)

![Look Up Mobs](./assets/wiki_mobs.png)

**Preconditions**:
A running webpage is necessary for this use case to be implemented.

**Postconditions**:
With the wiki in place and the subsections working we can implement a state as required per Mrs. Li in the form of a session cookie which automatically brings the user back to the page last visited.

**Estimated efforts**: medium

**Linked user stories**: #104, #105


# 3. Nonfunctional requirements

We have identified the following three non-functional requirements as paramount for the success of our project:

| Quality attribute    | Refinement             | Quality attribute scenarios   | Business value | Technical risk  |
| :---                 | :----                  | :----                         | :----          | :----           | 
| Availability         | uptime tracking        | Users want to look at the scoreboard, read the lore or download the game, which is only possible if the web page is online | High | Low |
| Performance          | hardware benchmarking  | Players want a smoothly running game on all hardware without stutters or hang ups | High      | High |
| Usability            | user feedback.         | Users want to start playing without having to read up lengthy documentation which is neccessary to get started.  | Medium        | Low |

## 3.1 Availability

In terms of availability we value the following attributes the most.

### 3.1.1 Uptime

The web page will be the signpost of our game as well as the primary entry point into our application ecosystem. 
It being online is therefore a very essential trait of our web page.
We are currently hosting the entire infrastructure on the local cluster of a participant.
If we cannot reach a sufficient uptime percentage we have to look into consulting a hosting provider.

### 3.1.2 Responsiveness

The data collecting end point which is used by the game to upload scores as well as by the web page to print out the high scores and various other statistics has to handle lots of data.
This may lead to potentionally long waiting times for an extraction of the entire data for the statistics on the web page.
Additionally the server might not be able to accept new submissions of high scores during this request.
Solution to this problem may any of either:

- Scale the Web Service Horizontally
- Introduce Paging and lazyloading for big requests

## 3.2 Performance

Developing a game, it is cruicial to have good performance on various target platforms. 
In this category we value the following points the most.

### 3.2.1 High FPS 

As a locally run game we have to adjust our shader programs, assets as well as post processing effects in order to never fall into a range which is not considered playable anymore.
In the industry this threshold is 60 frames per second.
We want to consider the framerate of the current monitor and set our target for this point to gameFPS = VSyncFrequency.
Sections where we can adjust most of the performance:

- Post Processing
- Physics Engine

### 3.2.2 Stable Physics

Underwatch features many weapons with shooting properties. 
This fact requires the physics engine of the game to handle potentially many objects which are travelling in the world.
When the physics engine gets overloaded the player might experience stuttering particles and objects on the screen.
To mitigate this problem we want to employ heavy testing on different hardware as well as load testing the physics engine regularly.

## 3.3 Usability

Usability is a key attribute of a game. It should be fun and interactive and users should not have the hassle of looking up the essential information on how to play the game.
There are a few approaches to approach this:

- implement a tutorial which the player can choose to play initially
- add basic input and game knowledge to the social media and marketing material so the player knows how the game works by the time they download it

### 3.3.1 Choice of Data

One of our key features is collecting statistics of the game runs and providing insights alongside a user name on the global scoreboard. However, we acknoledge, that some people don't want to upload their data for whatever reason they don't want.
As a solution we want to always implement an alternative Button/Input which results in the metrics not being uploaded at all.

# 4. Technical constraints
> Specify any major constraints, assumptions or dependencies, e.g., any restrictions about which type of server to use, which type of open source license must be complied, etc. 
