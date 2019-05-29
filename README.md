Feedback Service
=

[![Docker Repository on Quay](https://quay.io/repository/ukhomeofficedigital/pttg-feedback/status "Docker Repository on Quay")](https://quay.io/repository/ukhomeofficedigital/pttg-feedback)

## Overview

This is the Feedback Service. The service accepts requests to add and retrieve feedback.

## Find Us

* [GitHub]

## Technical Notes

The API is implemented using Spring Boot and exposes a RESTFul interface.

The endpoints are defined in `FeedbackResource.java`.

The database schema is created and maintained by the Flyway scripts found in `src/main/resources/db/migration`. 

## Building

### ACP

This service is built by Gradle on [Drone] using [Drone yaml].

## Infrastructure

### ACP

This service is packaged as a Docker image and stored on [Quay.io]

This service is deployed by [Drone] onto a Kubernetes cluster using its [Kubernetes configuration]

## Running Locally

Check out the project and run the command `./gradlew bootRun` which will install gradle locally, download all dependencies, build the project and run it.

The API should then be available on http://localhost:8080, where:
- port 8083 is defined in `application.properties` with key `server.port`
- the paths for the various endpoints are defined in the `FeedbackResource.java` class.

Note that this service runs locally against a HSQL in memory database.

## Dependencies

When deployed this service depends upon a database created by [pttg-postgres]. 

## Versioning

For the versions available, see the [tags on this repository].

## Authors

See the list of [contributors] who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENCE.md]
file for details.



[contributors]:                     https://github.com/UKHomeOffice/pttg-feedback/graphs/contributors
[Quay.io]:                          https://quay.io/repository/ukhomeofficedigital/pttg-feedback
[kubernetes configuration]:         https://github.com/UKHomeOffice/kube-pttg-feedback
[Drone yaml]:                       .drone.yml
[tags on this repository]:          https://github.com/UKHomeOffice/pttg-feedback/tags
[LICENCE.md]:                       LICENCE.md
[GitHub]:                           https://github.com/orgs/UKHomeOffice/teams/pttg
[Drone]:                            https://drone.acp.homeoffice.gov.uk/UKHomeOffice/pttg-feedback
[pttg-postgres]:                    https://github.com/UKHomeOffice/pttg-postgres
