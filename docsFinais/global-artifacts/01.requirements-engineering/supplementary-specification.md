# Supplementary Specification (FURPS+)

## Functionality

_Specifies functionalities that:  
&nbsp; &nbsp; (i) are common across several US/UC;  
&nbsp; &nbsp; (ii) are not related to US/UC, namely: Audit, Reporting and Security._

(i) - The system must support authentication mechanisms to secure access to the platform.

The system must provide audit logging to track significant actions performed by users.

The system must allow the generation of reports related to railway network performance, train schedules, and cargo transport efficiency.

(ii) - The system must include security mechanisms such as role-based access control (RBAC) to restrict user permissions.

* Audit logs must capture details of user actions, including timestamp, action type, and affected data.

* Reporting tools should allow users to generate and export reports on network connectivity, train delays, and cargo efficiency.

* The system should support compliance with data protection regulations, ensuring secure data handling and storage.

## Usability

_Evaluates the user interface. It has several subcategories,
among them: error prevention; interface aesthetics and design; help and
documentation; consistency and standards._

The system must follow a consistent user interface design across all screens.

Clear error messages should be provided to assist users in troubleshooting.

Help documentation must be available to guide users through system functionalities.

The UI should prioritize usability, ensuring an intuitive experience for both editors and players.
## Reliability

_Refers to the integrity, compliance and interoperability of the software. The requirements to be considered are: frequency and severity of failure, possibility of recovery, possibility of prediction, accuracy, average time between failures._

- The system must be able to recover from failures by maintaining a persistent state of the railway network.
- Error-handling mechanisms should be implemented to minimize disruptions in the simulation process.
- The system should log errors and provide diagnostic tools to help identify and resolve issues.
  The system must comply with integrity and reliability standards to ensure data consistency.

## Performance

_Evaluates the performance requirements of the software, namely: response time, start-up time, recovery time, memory consumption, CPU usage, load capacity and application availability._

- The system should have a startup time of less than 5 seconds.
- The response time for user actions, such as placing elements on the map or modifying railway routes, should not exceed 500ms.
- The simulation should run smoothly with minimal CPU and memory overhead.
- The system should support handling at least 100 concurrent users without performance degradation.

## Supportability

_The supportability requirements gathers several characteristics, such as:
testability, adaptability, maintainability, compatibility,
configurability, installability, scalability and more._

- The system should be developed following a modular architecture, facilitating maintainability and scalability.
- The software should be compatible with multiple operating systems (Windows, macOS, Linux).
- The codebase should follow clean coding practices to improve testability and debugging.
- The system should support configuration options to customize maps, scenarios, and simulation parameters.

## +

### Design Constraints

_Specifies or constraints the system design process. Examples may include: programming languages, software process, mandatory standards/patterns, use of development tools, class library, etc._

- The system must be developed using Java.
- The Test-Driven Development (TDD) approach must be followed.
- The project should be managed using the Agile SCRUM methodology.
- The design should adhere to Object-Oriented Programming (OOP) principles.

### Implementation Constraints

_Specifies or constraints the code or construction of a system such
such as: mandatory standards/patterns, implementation languages,
database integrity, resource limits, operating system._

- The system should store data persistently using object serialization.
- Java coding standards (CamelCase, Javadoc documentation) must be followed.
- The system should use JUnit 5 for unit testing and JaCoCo for test coverage reports.
- The database must ensure data integrity and consistency, particularly for train schedules and cargo transport records.

### Interface Constraints

_Specifies or constraints the features inherent to the interaction of the
system being developed with other external systems._

The system should integrate with external statistical tools to analyze railway network performance.

The system should provide mechanisms for interoperability with necessary external components.

### Physical Constraints

_Specifies a limitation or physical requirement regarding the hardware used to house the system, as for example: material, shape, size or weight._

The system should be able to run on standard desktop computers with reasonable processing power.

The software should be optimized to run on high-resolution displays.

No specialized hardware should be required for running the application.