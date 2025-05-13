package pt.ipp.isep.dei.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.domain.Player;
import pt.ipp.isep.dei.domain.Editor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrganizationRepositoryTest {

    @Test
    void testAddOrganization() {
        OrganizationRepository organizationRepository = new OrganizationRepository();
        Editor organization = new Editor("123456789");

        Optional<Editor> returnOrganization = organizationRepository.add(organization);

        assertEquals(organization, returnOrganization.get());
    }

    @Test
    void ensureGetOrganizationByEmployeeWorks() {
        OrganizationRepository organizationRepository = new OrganizationRepository();
        Editor organization = new Editor("123456789");
        Player employee = new Player("john.doe@this.company.com");
        organization.addEmployee(employee);
        organizationRepository.add(organization);

        Optional<Editor> result = organizationRepository.getOrganizationByEmployee(employee);

        assertEquals(organization, result.get());
    }

    @Test
    void ensureGetOrganizationByEmployeeFails() {
        OrganizationRepository organizationRepository = new OrganizationRepository();
        Editor organization = new Editor("123456789");
        Player employee = new Player("john.doe@this.company.com");
        organization.addEmployee(employee);
        organizationRepository.add(organization);

        Player employee2 = new Player("jane.doe@this.company.com");
        Optional<Editor> result = organizationRepository.getOrganizationByEmployee(employee2);

        assertTrue(result.isEmpty());
    }

    @Test
    void ensureGetOrganizationByEmailWorks() {
        OrganizationRepository organizationRepository = new OrganizationRepository();
        Editor organization = new Editor("123456789");
        Player employee = new Player("john.doe@this.company.com");
        organization.addEmployee(employee);
        organizationRepository.add(organization);

        Optional<Editor> result =
                organizationRepository.getOrganizationByEmployeeEmail("john.doe@this.company.com");

        assertEquals(organization, result.get());
    }

    @Test
    void ensureAddOrganizationWorks() {
        OrganizationRepository organizationRepository = new OrganizationRepository();
        Editor organization = new Editor("123456789");
        Player employee = new Player("john.doe@this.company.com");
        organization.addEmployee(employee);

        organizationRepository.add(organization);

        Optional<Editor> returnOrganization =
                organizationRepository.getOrganizationByEmployeeEmail("john.doe@this" + ".company.com");

        //Assert
        //Make sure both represents the same object
        assertEquals(organization, returnOrganization.get());
        //Make sure it is a clone (different memory addresses)
        assertNotSame(organization, returnOrganization.get());
    }

    @Test
    void ensureAddOrganizationDuplicateFails() {
        OrganizationRepository organizationRepository = new OrganizationRepository();
        Editor organization = new Editor("123456789");
        Player employee = new Player("john.doe@this.company.com");
        organization.addEmployee(employee);
        organizationRepository.add(organization);

        Optional<Editor> result = organizationRepository.add(organization);

        assertTrue(result.isEmpty());
    }
}