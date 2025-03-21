# US004 - Create a scenario

## 1. Requirements Engineering

### 1.1. User Story Description

As an Editor, I want to create a scenario for a selected map.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**
 Each scenario is characterized by defining restrictions and conditions for using a given map, namely:

>Time restrictions – the period in which the simulation will take place.

>Technological restrictions – for example, which locomotives or industries are available in the scenario.

>Historical restrictions – historical events that influence the scenario and change the operating conditions of the simulator.

**From the client clarifications:**


### 1.3. Acceptance Criteria

* **AC1:** All required fields must be filled in.
* **AC2:** The scenario must be linked to a valid existing map.
* **AC3:** A scenario must include at least one restriction (time, technological, or historical).

### 1.4. Found out Dependencies

* There is a dependency on "US001 - Create a Map" since a scenario must be associated with an existing map.

### 1.5 Input and Output Data

**Input Data:**

* Typed data:
    * A scenario name
    * A description 
    * A time restriction
    * A technological restriction
    * A historical restriction
	
* Selected data:
    * A map to associate the scenario with 

**Output Data:**

* List of existing maps available for selection
* (In) Success of the operation

### 1.6. System Sequence Diagram (SSD)

![System Sequence Diagram](svg/US004-SSD.svg)

**_Other alternatives might exist._**

### 1.7 Other Relevant Remarks

* The created scenario remains in an "unpublished" state until explicitly published by the Editor.