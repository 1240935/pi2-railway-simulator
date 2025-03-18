# US002 - Place an Industry

## 1. Requirements Engineering

### 1.1. User Story Description

As an Editor, I want to add an industry in a position XY of the selected map.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

>The Scenario Editor must allow the user to define the restrictions and
conditions for using a given map: time, technological and historical restrictions that will impact the type of industry available and which one is favoured.

### 1.3. Acceptance Criteria

* None

### 1.4. Found out Dependencies

* There is a dependency on US01, as it needs a map to be available for the industry to be placed on.

### 1.5 Input and Output Data

**Input Data:**

* Typed data:
    * a coordinate
	
* Selected data:
    * a type of industry

**Output Data:**

* (In)Success of the operation
* The placement of the industry on the grid

### 1.6. System Sequence Diagram (SSD)

![System Sequence Diagram](svg/US002-SSD.svg)
**_Other alternatives might exist._**


### 1.7 Other Relevant Remarks

* None