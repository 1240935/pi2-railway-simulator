# US010 - Route Selection

## 1. Requirements Engineering

### 1.1. User Story Description

As a Player, I want to assign a selected train to a route with a
valid station and the respective list of cargoes to be picked up

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

>As a user with the Player role, I should be able to choose a train from the list and assign in to a route. This route should include a valid station as a stop for the train. 
In the station, there should be a list of cargoes to be picked up by the train.

**From the client clarifications:**

> **Question**: "A route is a list of stations where the train passes. If there are many stations but no railway lines, are we able to create a route?"
>
> **Answer**: "If there is no path between any pair of consecutive points of the route, the player should be warned, then the player can opt between cancel/proceed."

> **Question**: "Can multiple cargo types be assigned to the same train?"
> 
> **Answer**: "Yes."

> **Question**: "Does a route have a limit of stations that can be assigned?"
>
> **Answer**: "No."

### 1.3. Acceptance Criteria

* If the selected route isn't connected by railways, it can't be created.

### 1.4. Found out Dependencies

* There is an inherent dependency in US001, US005, US008 and US009 as this one needs a map, at least 2 stations, railways and 
 a train to exist so that it can be run.

### 1.5 Input and Output Data

**Input Data:**

* Typed data:
    * the request         
	
* Selected data:
    * a beginning and ending station
    * a locomotive
    * stops
    * list of cargoes

**Output Data:**

* Available stations, locomotives and cargo
* Data for confirmation
* (In)Success of the operation

### 1.6. System Sequence Diagram (SSD)

![System Sequence Diagram](svg/US010-SSD.svg)

**_Other alternatives might exist._**

### 1.7 Other Relevant Remarks

* No other relevant remarks