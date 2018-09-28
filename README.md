# TBME Labs Authorization Server

 OAuth2 authorization server allowing users to authenticate across multiple applications.

[![Build Status](https://travis-ci.org/tbmelabs/tbmelabs-authorization-server.svg?branch=master)](https://travis-ci.org/tbmelabs/tbmelabs-authorization-server)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=authorization-server&metric=alert_status)](https://sonarcloud.io/dashboard?id=authorization-server)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c005d025d2954de7880f902182b18de2)](https://www.codacy.com/app/bbortt_2/tbmelabs-authorization-server?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=tbmelabs/tbmelabs-authorization-server&amp;utm_campaign=Badge_Grade)

### Prerequisites

Make sure to install the following software before starting:

* [Java Development Kit 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html): [This guide](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html) leads you through the installation.
* [Maven](https://maven.apache.org/download.cgi): Have a look at [this page](https://maven.apache.org/install.html) for any help while installing.
* [PostgreSQL](https://www.postgresql.org/download/): Installation guide is located right in the download page.
* [NodeJS and npm](https://nodejs.org/en/download/): Please follow the installer.

### Installing

#### PostgreSQL

Before executing the application you must prepare the database. There are several scripts located in src/main/resources/db/scripts. For instance `test_database.sql` is required to setup the integration test database.
If you do not have a local redis cluster you might want to use a PostgreSQL token store. To prepare the database use the script `jdbc_token_store.sql`.

## Running the tests

### Frontend

The frontend project is located in src/main/webapp. Please use [Yarn](https://yarnpkg.com/lang/en/) for all installation/testing etc. You can install yarn with npm, `npm install -g yarn` or via maven, executing `mvn package -Pwebpack`.
Navigate into the folder and take a look at the file `package.json` which contains a script section. It may give you an idea about the different use cases.

### Backend

Execute unit-tests by running `mvn test`. They are managed by the [maven-surefire-plugin](https://maven.apache.org/surefire/maven-surefire-plugin/).
Integration-tests start with `mvn verify` using the [maven-failsafe-plugin](https://maven.apache.org/surefire/maven-failsafe-plugin/).

## Deployment

This project is deployed using maven. Run `mvn clean install` to install the artifact into your local repository.

## Built With

* [Maven](https://maven.apache.org/) - A software project management and comprehension tool.
* [Yarn](https://yarnpkg.com/lang/en/) - Fast, reliable, and secure dependency management.
* [Sprint](https://spring.io/) - The source for modern java.
* [Angular](https://angularjs.org/) - Clientside JavaScript web framework.

## Contributing

Please read [CONTRIBUTING.md](https://github.com/tbmelabs/tbme-tv/blob/master/CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/tbmelabs/tbme-tv/tags). 

## Authors

* **Timon Borter** - *Initial work* - [bbortt](https://github.com/bbortt)

See also the list of [contributors](https://github.com/tbmelabs/tbme-tv/contributors) who participated in this project.

## Hat Tips

* To [swaechter](https://github.com/swaechter) for the Angular-Server-Side-Rendering in `AngularUniversalRenderEngine.java`. Original code is in [this repository](https://github.com/swaechter/angularj-universal).

## License

This project is published under the terms of MIT License. For more information see the [license file](https://github.com/tbmelabs/tbmelabs-authorization-server/blob/development/LICENSE).
