package pt.ipp.isep.dei;

import pt.ipp.isep.dei.domain.Player;
import pt.ipp.isep.dei.domain.Editor;
import pt.ipp.isep.dei.domain.Position;

public class Bootstrap implements Runnable {

    //Add some task categories to the repository as bootstrap
    public void run() {
        addTaskCategories();
        addOrganization();
        addUsers();
    }

    private void addOrganization() {
        //TODO: add organizations bootstrap here
        //get organization repository
        OrganizationRepository organizationRepository = Repositories.getInstance().getOrganizationRepository();

        Editor organization = new Editor("This Company");
        organization.addEmployee(new Player("admin@this.app"));
        organization.addEmployee(new Player("employee@this.app"));
        organizationRepository.add(organization);
    }

    private void addTaskCategories() {
        //TODO: add bootstrap Task Categories here
        //get task category repository
        TaskCategoryRepository taskCategoryRepository = Repositories.getInstance().getTaskCategoryRepository();

        taskCategoryRepository.add(new Position("Analysis"));
        taskCategoryRepository.add(new Position("Design"));
        taskCategoryRepository.add(new Position("Implementation"));
        taskCategoryRepository.add(new Position("Development"));
        taskCategoryRepository.add(new Position("Testing"));
        taskCategoryRepository.add(new Position("Deployment"));
        taskCategoryRepository.add(new Position("Maintenance"));
    }

    private void addUsers() {
        //TODO: add Authentication users here: should be created for each user in the organization
        //get authentication repository
        AuthenticationRepository authenticationRepository = Repositories.getInstance().getAuthenticationRepository();

        authenticationRepository.addUserRole(AuthenticationController.ROLE_ADMIN, AuthenticationController.ROLE_ADMIN);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_EMPLOYEE, AuthenticationController.ROLE_EMPLOYEE);

        authenticationRepository.addUserWithRole("Main Administrator", "admin@this.app", "admin", AuthenticationController.ROLE_ADMIN);
        authenticationRepository.addUserWithRole("Employee", "employee@this.app", "pwd", AuthenticationController.ROLE_EMPLOYEE);
    }
}