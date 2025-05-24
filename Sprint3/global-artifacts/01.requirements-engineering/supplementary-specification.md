# Supplementary Specification (FURPS+)

## Functionality

_Specifies functionalities that:  
&nbsp; &nbsp; (i) are common across several US/UC;  
&nbsp; &nbsp; (ii) are not related to US/UC, namely: Audit, Reporting and Security._

**Authentication: Those who wish to use the application must be authenticated with a password. This password consists of seven alphanumeric characters, including three capital letters and two digits.**

## Usability

_Evaluates the user interface. It has several subcategories,
among them: error prevention; interface aesthetics and design; help and
documentation; consistency and standards._

**Help and Documentation: Documentation should be in English, with clear guidelines for using the system and understanding each feature.**

## Reliability

_Refers to the integrity, compliance and interoperability of the software. The requirements to be considered are: frequency and severity of failure, possibility of recovery, possibility of prediction, accuracy, average time between failures._

**Error Prevention: Provide clear validation for user input (for example, no special characters or digits in city names).**

**The application ought to employ object serialization to guarantee the
persistence of the data in two successive runs.**

## Performance

_Evaluates the performance requirements of the software, namely: response time, start-up time, recovery time, memory consumption, CPU usage, load capacity and application availability._

**There are no specific performance requirements.**

## Supportability

_The supportability requirements gathers several characteristics, such as:
testability, adaptability, maintainability, compatibility,
configurability, installability, scalability and more._

**Unit testing: All methods from all User Stories, apart from the ones implementing Input/Output operations, must go under unit testing.**



## +

### Design Constraints

_Specifies or constraints the system design process. Examples may include: programming languages, software process, mandatory standards/patterns, use of development tools, class library, etc._

**Business rules validation is a must when recording and updating data;**

**All the images/figures produced during the software development process should be recorded in SVG format.**

**The JaCoCo plugin should be used to generate the coverage report.**

### Implementation Constraints

_Specifies or constraints the code or construction of a system such
 as: mandatory standards/patterns, implementation languages,
database integrity, resource limits, operating system._

**The application must be developed in Java language.**

**The class structure must be designed to allow easy maintenance and the addition of new features following the best Object-Oriented (OO) practices.**

**Adopt recognized coding standards (e.g., CamelCase).**

**Use Javadoc to generate useful documentation for Java code.**

### Interface Constraints

_Specifies or constraints the features inherent to the interaction of the
system being developed with other external systems._

**There are no external systems associated with the application's features.**

### Physical Constraints

_Specifies a limitation or physical requirement regarding the hardware used to house the system, as for example: material, shape, size or weight._

**There are no specific hardware requirements for the application to run. However, the device should have the "minimum" characteristics of a modern day computer.**