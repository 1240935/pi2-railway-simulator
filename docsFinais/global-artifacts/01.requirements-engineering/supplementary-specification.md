# Supplementary Specification (FURPS+)

## Functionality

_Specifies functionalities that:  
&nbsp; &nbsp; (i) are common across several US/UC;  
&nbsp; &nbsp; (ii) are not related to US/UC, namely: Audit, Reporting and Security._

* Map Editor: Create rectangular maps with cities and industries (primary sector, transforming industries, mixed industries).
* Scenario Editor: Define restrictions for a map (time, technological, historical).
* Simulator: Simulate the operation of a railway network, including stations, lines, trains, and cargo.
* Statistical Tools: Assess network performance, analyze connectivity, and quality of services.
* Topological Representation of Railway Lines: Represent connections between stations without graphical detail.
* Station Management: Create, upgrade, and view stations with buildings and cargo data.
* Train Management: Buy trains, assign them to routes, and manage cargo.
* Cargo Generation: Automatically generate cargo at stations based on cities and industries served.
## Usability

_Evaluates the user interface. It has several subcategories,
among them: error prevention; interface aesthetics and design; help and
documentation; consistency and standards._
* User Interface: Simple and intuitive design for the map editor, scenario editor, and simulator, ensuring ease of navigation and operation.
* Error Prevention: Provide clear validation for user input (for example, no special characters or digits in city names).
* Help and Documentation: Documentation should be in English, with clear guidelines for using the system and understanding each feature.
* Testing and Feedback: Implement user feedback loops to improve usability during iterative sprints.
## Reliability

_Refers to the integrity, compliance and interoperability of the software. The requirements to be considered are: frequency and severity of failure, possibility of recovery, possibility of prediction, accuracy, average time between failures._

* System should have a high degree of availability, with minimal downtime.
* Regular backups of data should be created to prevent loss of map and scenario configurations.
* Error recovery should be smooth, with users receiving clear error messages and instructions for resolution.
* Unit tests will be conducted to minimize errors in the core functionality (map editor, scenario editor, simulator).

## Performance

_Evaluates the performance requirements of the software, namely: response time, start-up time, recovery time, memory consumption, CPU usage, load capacity and application availability._

* Start-up time should be optimized, aiming for under 10 seconds.
* Memory usage and CPU consumption should be efficient, especially for large maps and scenarios.
* Response times should be under 2 seconds for UI interactions like station building and train assignments.
* The system should be capable of handling large networks with multiple stations, trains, and industries without performance degradation.

## Supportability

_The supportability requirements gathers several characteristics, such as:
testability, adaptability, maintainability, compatibility,
configurability, installability, scalability and more._

* The code should be modular and follow Object-Oriented principles for easy maintenance and extension.
* The system should be compatible with major operating systems like Windows, MacOS, and Linux.
* The application should be easily configurable for new features, such as adding new industries, trains, or historical events.
* Implement Javadoc for Java classes to generate useful documentation for future developers.
* The system should support unit tests for all methods except those dealing with I/O operations.
* The application should allow easy installation, with a clear set of setup instructions for new users.

## +

### Design Constraints

_Specifies or constraints the system design process. Examples may include: programming languages, software process, mandatory standards/patterns, use of development tools, class library, etc._

* The application must be developed in Java using an Object-Oriented approach.
* Follow best Object-Oriented (OO) practices in design, ensuring modularity and maintainability.
* The Test-Driven Development (TDD) approach should be followed, ensuring that tests are written before implementation.
* The JUnit 5 framework will be used for unit testing, and JaCoCo will be used for code coverage reporting.
* All images/figures should be in SVG format for scalability and clarity.

### Implementation Constraints

_Specifies or constraints the code or construction of a system such
 as: mandatory standards/patterns, implementation languages,
database integrity, resource limits, operating system._

* The system will rely on object serialization to persist data across multiple runs of the simulator.
* All business rules must be validated when recording and updating data.
* The code should follow recognized Java coding standards, such as CamelCase for variable names and methods.

### Interface Constraints

_Specifies or constraints the features inherent to the interaction of the
system being developed with other external systems._

* Integration with GitHub for version control and project management.
* Ensure interoperability with external systems for statistical tools, if applicable.
### Physical Constraints

_Specifies a limitation or physical requirement regarding the hardware used to house the system, as for example: material, shape, size or weight._

* The software should be designed to run, specially, on modern hardware configurations but with no minimum requirements.
* The system should be optimized to work on both personal computers and servers, ensuring flexibility for different deployment scenarios.