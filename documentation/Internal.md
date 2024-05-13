- [Internal Documentation](#internal-documentation)
  * [Testing](#testing)
    + [Coverage](#coverage)
      - [Game](#game)

<small><i><a href='http://ecotrust-canada.github.io/markdown-toc/'>Table of contents generated with markdown-toc</a></i></small>

# Internal Documentation

Guides and nice to know things for developers, which have no official place in the documentation for the course as of yet.

## Testing

### Coverage

For the game we have two ways to provide coverage reports.

#### Unit-Test Coverage with Jacoco
We use [jacoco](https://docs.gradle.org/current/userguide/jacoco_plugin.html) to generate coverage reports for our unit-tests.
Upon executing `gradle test` we run the test suit for the game and a new report will be generated.
The report can be found under `/game/core/build/jacocoHtml/index.html`

#### Integration-Test Coverage

We use the features of IntelliJ to create Integration-Test Coverage Reports.

In order to do an integration test start the application from within IntelliJ with the Option `Start with Coverage`. 
Proceed to play the game for a certain amount of time (we recommend at least 5min of active gameplay) before quitting.
Upon finishing the application execution IntelliJ will ask where to save the coverage report to.
