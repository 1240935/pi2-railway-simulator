# US012 - Simulate Cargo Generation

## 1. Requirements Engineering

### 1.1. User Story Description

As a Product Owner, I want to create a simulator that generates
cargoes at current stations, automatically, considering the cities and
industries that the railway network serves.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

As an user with the Product Owner role, I should be able to start a simulation of cargo. This simulation consists in gathering information, such as the cities and industries available for cargo generation covered by the railway and the stations where the cargo should appear, and simulate at user command with the start and pause input.
 

**From the client clarifications:**


### 1.3. Acceptance Criteria

* **AC1:** This simulator should
  provide options for start/pause.

### 1.4. Found out Dependencies

* There is a dependency on "US005 - Build a station" as at least one station must exist to generate cargo.
### 1.5 Input and Output Data


**Input Data:**

* Typed data:
  * simulation start command
  * simulation pause command
	
* Selected data:

**Output Data:**

* real-time status of the simulation (running/paused)
* generated cargo and its corresponding origin points

### 1.6. System Sequence Diagram (SSD)

![System Sequence Diagram](svg/US012-SSD.svg)

**_Other alternatives might exist._**

### 1.7 Other Relevant Remarks

