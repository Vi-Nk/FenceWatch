#  FenceWatch
[![Java](https://img.shields.io/badge/Java-17%2B-blue.svg)](https://openjdk.java.net/)
[![Gradle](https://img.shields.io/badge/Gradle-8.5-brightgreen.svg)](https://gradle.org/)
[![Dropwizard](https://img.shields.io/badge/Dropwizard-4.0.14-orange.svg)](https://www.dropwizard.io/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

**FenceWatch** is a lightweight, high-performance geofencing alert microservice built with [Dropwizard](https://www.dropwizard.io/). It allows you to define geofencing zones , track GPS device movements, and generate zone entry/exit alerts through clean RESTful APIs.

## Prerequisites

- Java 17 or higher
- Gradle 8.5 or higher (included via Gradle wrapper)

## Features

### MVP Scope

These features are targeted for MVP , Project would be in WIP state till then :

- **Circular Geofence Zones**  
  Create zones with a center coordinate and a radius in meters.

- **Device Registration & Tracking**  
  Devices can register and send live location updates via API.

- **Real-Time Zone Entry/Exit Detection**  
  Automatically determines when a device enters or leaves a zone.

- **Event Logging**  
  Entry/exit events are timestamped and stored for querying.

- **RESTful JSON APIs**  
  Dropwizard-based endpoints for zones, devices, locations, and events.

- **In-Memory Storage (H2)**  
  Supports embedded DB for fast prototyping and testing.

- **Health & Metrics Endpoints**  
  Dropwizard exposes `/healthcheck`, `/metrics`, `/ping`.

---

### Next Possible Features

These features are planned or ideal for future enhancements:

- **Polygonal Zone Support**  
  Add point-in-polygon checks using JTS for complex boundaries.

- **Spatial Indexing**  
  Use Geohash, Quadtree, or R-tree to evaluate zones more efficiently at scale.

- **Zone Grouping or Tagging**  
  Organize zones by project, customer, or region.

- **Fleet Management**  
  Add devices to a fleet and provide tracking rules at fleet level.

- **Device History & Path Tracking**  
  Store time series of location updates per device.

- **Notification Hooks**  
  Support for webhooks or Kafka events on zone transitions.

- **Authentication & API Keys**  
  Add access control using tokens or headers.
