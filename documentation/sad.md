# Project Name
## Software Architecture Documentation
> This template is a simplified version based on the documentation templates from IBM Rational Unified Process (RUP) and arc42.org (https://docs.arc42.org/home/)
> If necessary, you can add more topics related to the architecture design of your application.

### 1. Introduction
#### 1.1 Overview
The following patterns are selling points of our architecture, covering the game, the website and our backend
1. *Model-View-Controller (MVC):*
   - `Game`: Separates game logic (Model), rendering (View), and user input (Controller), enhancing modularity and maintainability.
   - `Website`: Uses MVC to segregate data handling, UI, and control flow, making it easier to manage and extend.

2. *Client-Server Architecture:*
   - `Backend Integration`: Game acts as the client requesting high scores from the server, ensuring a clear division between frontend gameplay and backend data processing.
   - `Scalability`: Enables the backend to scale independently, handling more players and higher loads without modifying the game client.

3. *Layered Architecture:*
   - `Application Layer`: Manages game logic and user input processing.
   - `Data Layer`: Interfaces with backend services and databases for persistent storage and retrieval of high scores.

4. *RESTful Architecture:*
   - `API Design`: Backend provides RESTful APIs for high score data exchange, following standard HTTP methods and stateless operations for simplicity and interoperability.

5. *Service-Oriented Architecture (SOA):*
   - `High Scores Service`: A dedicated service for managing high score data, allowing for independent scaling and updating without impacting other backend components.

6. *Event-Driven Architecture:*
   - `Game Events`: Utilizes event-driven mechanisms for gameplay actions (e.g., scoring points, game over), ensuring responsive and decoupled game logic.

7. *Repository Pattern:*
   - `Data Access`: Encapsulates data access logic within repositories, providing a clean separation of data retrieval and manipulation from business logic.

8. *Singleton Pattern:*
   - `Game Manager`: Ensures a single instance of core game management classes, controlling the game’s state and lifecycle uniformly.

By incorporating these architectural patterns, our design ensures robust, scalable, and maintainable software that can efficiently support the features and future growth of the project.

#### 1.2 Constraints
> Any technical or organizational constraints, conventions (Tips: https://docs.arc42.org/section-2/)
#### 1.2.1 Technical Constraints
| Constraint | Background and /or motivation|
|-|-|
|Operating on Linux, Mac, and Windows | We want the largest possible user-base. |
|Implementation in Java and React| We use Java 21 for the game and the backend. Our website is written with React |

#### 1.2.2 Organizational Constraints
| Constraint | Background and /or motivation|
|-|-|
|Team|Martin, Benjamin, Jeremias and Jakob in the current course Tinf22B6|
|Schedule| Start of work with beginning of the third semester. First presentable version at the end of the third semester. Advanced final version at the end of the fourth semester.|
|Development Tools| IDE: bring your own. Diagramms: DrawIO or PlantUML. Versioning: Git. Host: Github|
|Release as Open Source| The code is made available as open source|
#### 1.2.3 Conventions
| Convention | Background and /or motivation|
|-|-|
|Architecture Documentation| We will use the structure according to the lecture Software-Engineering and use the provided templates (sad.md, srs.md, and emmm.org)|
#### 1.3 Definitions, Acronyms and Abbreviations
> Definitions of all terms, acronyms, and abbreviations required to properly interpret this document.
#### 1.4 References
> A complete list of all documents referenced -- hyperlinks to those documents.

### 2. Architecture tactics
> Reference your architecturally significant requirements from Semester 3.
> Revise your architecture tactics from Semester 3.

### 3. Architecture design
> This section specifies the architecture design in various views.
> Minimum requirement:
> - sequence diagram on a component level and necessary description
> - component diagrams and/or package diagrams, and necessary description

![Component Diagram](./assets/ComponentDiagram.drawio.svg)

![Sequence Diagram](./assets/sadSequenceDiagram.svg)

#### 3.1 Overview 
> A summary of the architecture design -- highlights.  

#### 3.2 Runtime view (Tips: https://docs.arc42.org/section-6/)

#### 3.3 Deployment view (Tips: https://docs.arc42.org/section-7/)

#### 3.4 ... ...
