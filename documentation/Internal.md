- [Internal Documentation](#internal-documentation)
  * [Testing](#testing)
    + [Coverage](#coverage)
      - [Game](#game)

<small><i><a href='http://ecotrust-canada.github.io/markdown-toc/'>Table of contents generated with markdown-toc</a></i></small>
# Internal Documentation
Guides and nice to know things for developers, which have no official place in the documentation for the course as of yet.
## Testing
### Coverage
#### Game
For the game we have two ways to provide coverage reports.
1. Unit-Test Coverage with Jacoco
We use jacoco to generate coverage reports for our unit-tests. [](https://docs.gradle.org/current/userguide/jacoco_plugin.html)
Upon executing `gradle test` we run the test suit for the game and a new report will be generated.
The report can be found under `/game/core/build/jacocoHtml/index.html`
2. Integration-Test Coverage. 
We use the features of IntelliJ to create Integration-Test Coverage Reports.
