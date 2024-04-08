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
        - [2.2 Name of Feature 1 / Use Case 1](#22-name-of-feature-1--use-case-1)
        - [2.3 Name of Feature 2 / Use Case 2](#23-name-of-feature-2--use-case-2)
    - [3. Nonfunctional requirements](#3-nonfunctional-requirements)
        - [3.1 Utility tree](#31-utility-tree)
        - [3.2 Tactics for Top 3 quality attribute scenarios](#32-tactics-for-top-3-quality-attribute-scenarios)
            - [3.2.1 ...](#321-)
            - [3.2.2 ...](#322-)
            - [3.2.3 ...](#323-)
    - [4. Technical constraints](#4-technical-constraints)

<!-- markdown-toc end -->
# Uderwatch Software Requirements Specification
Welcome to the Underwatch Software Requirements Specification!

This Project is part of the third semester educational program at DHBW-Karlsruhe for course TINF22B6.
## 1. Introduction
### 1.1 Overview
Underwatch is a roguelike adventure that immerses players in an enigmatic underworld. Here, they take on the role of fearless explorers, armed with a diverse and imaginative array of weapons. As they navigate through procedurally generated environments, they must confront relentless bullet storms, vanquish formidable adversaries, and challenge one-of-a-kind bosses.
### 1.2 Scope
This Software Requirements Specification (SRS) document covers the complete system of Underwatch. It encompasses both functional and non-functional requirements necessary for the successful development, deployment, and operation of the platform. The document aims to provide a comprehensive understanding of the system's architecture, features, and limitations.

### 1.3 Definitions, Acronyms and Abbreviations
> Here will be Definitions, Acronyms and Abbreviations for the project, we still have to figure out how to auto-generate those in markdown
### 1.4 References
> A complete list of all documents referenced. Each document should be identified by title, date, and publishing organization. You can also insert hyperlinks, in order to open the references conviniently.

---
## 2. Functional requirements
>  This section contains all the software requirements to a level of detail sufficient to enable designers to design a system to satisfy those requirements and testers to test that the system satisfies those requirements.  
>  This section is typically organized by feature, but alternative organization methods may also be appropriate, for example, organization by user or organization by subsystem.

> [!NOTE]
> You can insert links to your UML diagrams and user stories, or labels of user stories into this document.

### 2.1 Overview 
> A brief description of the functionality of your application.  
> Include one or more **UML use case** diagram(s) and necessary description to specify the major use cases of your application.

### 2.1.1 General Use-Case
Underwatch aims to provide a fun, engaging gaming experience for the following plattforms:
1. Windows 
2. Linux
3. Mac

It also provides a basic website with the following features:
1. Players can download the game.
2. The website containts highscores.
3. The website provides a wiki. 



For better readabillity we will provide three seperate use-case-diagrams
1. The Game:

![Game-Use-Case-diagram]( ./assets/underwatch_game_usecase.drawio.svg )

2. The Users System:

![System-Use-Case-diagram]( ./assets/underwatch_system_usecase.drawio.svg )

3. The Website:

![Website-Use-Case-diagram]( ./assets/underwatch_website_usecase.drawio.svg )

<!-- ### 2.2 Name of Feature 1 / Use Case 1 -->
<!-- > Specify this feature / use case by: -->
<!-- > - Relevant **user stories (their links or labels)** -->
<!-- > - **UI mockups** -->
<!-- > - **UML behavior diagrams** and necessary text specification -->
<!-- > - **Preconditions**. *A precondition of a use case is the state of the system that must be present prior to a use case being performed.* -->
<!-- > - Postconditions. *A postcondition of a use case is a list of possible states the system can be in immediately after a use case has finished.* -->
<!-- > - **Estimated efforts (high, medium, low)** -->
### 2.1.2 Important Entities and Classes
> [!TODO] We have to add the EER models which we have in stuff we have to fit later(https://github.com/SE-TINF22B6/Underwatch/wiki/Stuff-we-have-to-fit-later).
>At this point we can also rework them to be better. We dont have to go into to much detail and the core graphic will probably be the game EER.
>We can put three parts here
>1. The game
>2. The website model
>3. The data model 

### 2.3 Name of Feature 2 / Use Case 2
... ...

## 3. Nonfunctional requirements

<!-- > [!NOTE]   -->
<!-- > It is not necessary to cover all of the following ASRs (Architecturally Significant Requirements), but focus on what your project will implement.   -->
<!-- > If some ASRs are described as user stories in your backlog, add their **links** in this section, or any information to guide the reader find them in your backlog, such as a **label** of those relevant user stories. -->

<!-- > Categories: Availability, Performance, (Energy) Efficiency, Deployability, Integrability, Modifiability, Testability, Safety, Security, Usability -->

For our quality requirements we will use the ISO 25010 Product Quality standard. For more informations regarding this standard you can visit the site https://iso25000.com/index.php/en/iso-25000-standards/iso-25010 .This section contains all quality requirements as quality tree with scenarios. The most important ones have already been described in section 1.2. (quality goals)

<img width="799" alt="Screenshot 2023-10-18 at 14 38 01" src="https://github.com/SE-TINF22B6/Underwatch/assets/87950162/a407dcaf-1f93-4ef1-a589-a6a199d75db9">  
We have identified the following non-functional requirements as paramount for the success of our project:  

| Goal                    | Description                                                      |
|-------------------------|------------------------------------------------------------------|
| 1. User Engagement      | The users should be engaged while playing the game               |
| 2. Self-descriptiveness | The game should be easy to understand with low efforts           |
| 3.  Installability      | The game should run on all major platforms (windows, linux, mac) |

### 3.1 Utility tree

| Quality attribute    | Refinement             | Quality attribute scenarios   | Business value | Technical risk  |
| :---                 | :----                  | :----                         | :----          | :----           | 
| e.g. Availability    | e.g. data loss         | Scenario 1.1  who/what, Event, Influence, Condition, Action, Measurement                |  e.g. H        | e.g., L         |
|                      |                        | Scenario 1.2                  |  e.g. M        | e.g., L         |
|                      | e.g. hardware issue    | Scenario 2.1                  |  e.g. H        | e.g., L         |
| e.g. Security        | ... ...                |                               |                |                 |

> [!IMPORTANT]
> When specifying the quality attribute scenarios, cover 6 aspects: who/what, Event, Influence, Condition, Action, Measurement

### 3.2 Tactics for Top 3 quality attribute scenarios

#### 3.2.1 ...

#### 3.2.2 ...

#### 3.2.3 ...

## 4. Technical constraints
> Specify any major constraints, assumptions or dependencies, e.g., any restrictions about which type of server to use, which type of open source license must be complied, etc. 
