#(Sample Maven setup)

This repository now contains a minimal Maven project structure with a sample application and a TestNG test.

Quick start:

1. Build and run tests:

```cmd
mvn test
```

2. Run the sample main class:

```cmd
mvn -q -Dexec.mainClass="com.qa.automation.App" exec:java
```

# QA API Automation Framework

This repository contains API automation tests using TestNG + RestAssured.

Quick start (local)

1) Run tests and generate reports:

```cmd
mvn clean verify
```

- Extent report: `target/extent-report/index.html`
- JaCoCo coverage report: `target/site/jacoco/index.html`
- Allure (if installed): `allure serve target/allure-results`

CI (CircleCI)

- A CircleCI config is provided in `.circleci/config.yml`. The pipeline will run `mvn clean verify` and store artifacts:
  - `target/site/jacoco` (coverage)
  - `target/extent-report` (extent html)

Cross-platform

- The project is Maven-based and targets Java 17. To run locally you need JDK 17 and Maven installed. The instructions above work on Windows/macOS/Linux.

Notes and next steps

- Sensitive headers/fields are logged to the Extent report — consider masking secrets before committing and running in CI.
- If you want pretty-printed JSON or header whitelisting, I can add it.

Notes:
- Tests are configured with `testng.xml` at the project root. The `pom.xml` includes TestNG as a test dependency.
- Java 17 is set in `pom.xml` as source/target.
