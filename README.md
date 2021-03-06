# TBME Labs Authorization Server

 OAuth2 authorization server allowing users to authenticate across multiple applications.

[![Build Status](https://travis-ci.org/tbmelabs/tbmelabs-authorization-server.svg?branch=master)](https://travis-ci.org/tbmelabs/tbmelabs-authorization-server)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=authorization-server&metric=alert_status)](https://sonarcloud.io/dashboard?id=authorization-server)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c005d025d2954de7880f902182b18de2)](https://www.codacy.com/app/bbortt_2/tbmelabs-authorization-server?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=tbmelabs/tbmelabs-authorization-server&amp;utm_campaign=Badge_Grade)
[![Docker Pulls](https://img.shields.io/docker/pulls/tbmelabs/tbmelabs-authorization-server.svg)](https://hub.docker.com/r/tbmelabs/tbmelabs-authorization-server)

### Prerequisites

Make sure to install the following software before starting:

* [Maven](https://maven.apache.org/) - A software project management and comprehension tool.
* [Spring](https://spring.io/) - The source for modern java.
* [PostgreSQL](https://www.postgresql.org/download/) - The World's Most Advanced open source relational database.
* [NodeJS and npm](https://nodejs.org/en/download/) - A JavaScript runtime built on Chrome's V8 JavaScript engine.

And your IDE should support [Lombok](https://projectlombok.org/).

### Installing

#### PostgreSQL

Before executing the application you must prepare the database. There are several scripts located in src/main/resources/db/scripts. For instance `test_database.sql` is required to setup the integration test database.
If you do not have a local redis cluster you might want to use a PostgreSQL token store. To prepare the database use the script `jdbc_token_store.sql`.

#### Yarn + Frontend

We use Yarn for all frontend belongings.
You must install the frontend at least once before starting up the server. Otherwise it wont find the files required to server-side-render Angular. To do so either navigate into src/main/webapp and run `yarn install && yarn build:dev` or run `mvn clean package -Pwebpack` in the root directory.

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

* To [swaechter](https://github.com/swaechter) for the basic idea on server-side-rendering Angular in `AngularUniversalRenderEngine.java`. Original code is in [this repository](https://github.com/swaechter/angularj-universal).

## License

This project is published under the terms of MIT License. For more information see the [license file](https://github.com/tbmelabs/tbmelabs-authorization-server/blob/development/LICENSE).
