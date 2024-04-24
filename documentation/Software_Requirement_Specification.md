<!-- markdown-toc start - Don't edit this section. Run M-x markdown-toc-refresh-toc -->
**Table of Contents**

- [Uderwatch Software Requirements Specification](#uderwatch-software-requirements-specification)
    - [1. Introduction](#1-introduction)
        - [1.1 Overview](#11-overview)
        - [1.2 Scope](#12-scope)
        - [1.3 Definitions, Acronyms and Abbreviations](#13-definitions-acronyms-and-abbreviations)
        - [1.4 References](#14-references)
    - [2. Functional requirements](#2-functional-requirements)
        - [2.1 Overview ](#21-overview)
        - [2.1.1 General Use-Case](#211-general-use-case)
        - [2.1.2 Important Entities and Classes](#212-important-entities-and-classes)
        - [2.3 Name of Feature 2 / Use Case 2](#23-name-of-feature-2--use-case-2)
    - [3. Nonfunctional requirements](#3-nonfunctional-requirements)
        - [3.1 Utility tree](#31-utility-tree)
        - [3.2 Tactics for Top 3 quality attribute scenarios](#32-tactics-for-top-3-quality-attribute-scenarios)
            - [3.2.1 User Engagement](#321-user-engagement)
            - [3.2.2 Self-descriptiveness](#322-self-descriptiveness)
            - [3.2.3 Installability](#323-installability)
    - [4. Technical constraints](#4-technical-constraints)

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

> Rough Use-Case-Diagram for the End-User, 24.10.2023:

> Landing-Page-Design on Figma, 24.10.2023:

> ER-Diagram Rough first version, 24.10.2023:

> User-Activity-Diagram for signing in, 26.10.2023:

> User-Activity-Diagram for signing up, 26.10.2023:

> User-Activity-Diagram for viewing post in thread-view, 26.10.2023:

> Sequence-Diagram for PostDetail-Creation, 31.10.2023:

> Sequence-Diagram for Logging in per Google API, 31.10.2023:

> User-Activity-Diagram updated, more refined version of the first draft, 01.11.2023:

> EER-Diagram for general classe, 15.11.2023:

> Utility-Tree, 28.11.2023:

> Link to all mockups in figma, 23.12.2023:

> Sequence-Diagram for Updating profile, 25.12.2023:

---
# 2. Functional requirements

## 2.1 Overview

## 2.1.1 General Use-Case

Underwatch is comparable to almost all of the games on the game market. 
Meaning that there is a web page promoting the game as well as giving out additional information in the form of statistics.
The game differs itself from the pure techonological standpoint since we are developing for the target platforms Windows, Linux and MacOS.

The application can be split into the following sections:
 
1. Gameplay Loop
2. Website Activities

![General Usecase]( ./assets/general_use_case.drawio.svg)

## 2.1.2 Important Entities and Classes
> [!TODO] 
> We have to add the EER models which we have in stuff we have to fit later(https://github.com/SE-TINF22B6/Underwatch/wiki/Stuff-we-have-to-fit-later).
>At this point we can also rework them to be better. We dont have to go into to much detail and the core graphic will probably be the game EER.
>We can put three parts here
>1. The game
>2. The website model
>3. The data model 

## 2.3 Name of Feature 2 / Use Case 2
... ...

# 3. Nonfunctional requirements

<!-- > [!NOTE]   -->
<!-- > It is not necessary to cover all of the following ASRs (Architecturally Significant Requirements), but focus on what your project will implement.   -->
<!-- > If some ASRs are described as user stories in your backlog, add their **links** in this section, or any information to guide the reader find them in your backlog, such as a **label** of those relevant user stories. -->

<!-- > Categories: Availability, Performance, (Energy) Efficiency, Deployability, Integrability, Modifiability, Testability, Safety, Security, Usability -->

For our quality requirements we will use the ISO 25010 Product Quality standard. For more informations regarding this standard you can visit the site https://iso25000.com/index.php/en/iso-25000-standards/iso-25010 .<img width="799" alt="Screenshot 2023-10-18 at 14 38 01" src="https://github.com/SE-TINF22B6/Underwatch/assets/87950162/a407dcaf-1f93-4ef1-a589-a6a199d75db9">  
We have identified the following non-functional requirements as paramount for the success of our project:  

| Goal                    | Description                                                      |
|-------------------------|------------------------------------------------------------------|
| 1. User Engagement      | The users should be engaged while playing the game               |
| 2. Self-descriptiveness | The game should be easy to understand with low efforts           |
| 3.  Installability      | The game should run on all major platforms (windows, linux, mac) |

## 3.1 Utility tree

| Quality attribute    | Refinement             | Quality attribute scenarios   | Business value | Technical risk  |
| :---                 | :----                  | :----                         | :----          | :----           | 
| e.g. Availability    | e.g. data loss         | Scenario 1.1  who/what, Event, Influence, Condition, Action, Measurement                |  e.g. H        | e.g., L         |
|                      |                        | Scenario 1.2                  |  e.g. M        | e.g., L         |
|                      | e.g. hardware issue    | Scenario 2.1                  |  e.g. H        | e.g., L         |
| e.g. Security        | ... ...                |                               |                |                 |

> [!IMPORTANT]
> When specifying the quality attribute scenarios, cover 6 aspects: who/what, Event, Influence, Condition, Action, Measurement

## 3.2 Tactics for Top 3 quality attribute scenarios

### 3.2.1 User Engagement

### 3.2.2 Self-descriptiveness

### 3.2.3 Installability

# 4. Technical constraints
> Specify any major constraints, assumptions or dependencies, e.g., any restrictions about which type of server to use, which type of open source license must be complied, etc. 
