package com.geektrust.family.test;
import static com.geektrust.family.relation.Relationship.*;

import com.geektrust.family.FamilyTree;
import com.geektrust.family.commands.Command;
import com.geektrust.family.model.Person;
import com.geektrust.family.relation.Relationship;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static com.geektrust.family.commands.Command.ADD_CHILD;
import static com.geektrust.family.test.TestUtils.testRelationsInFamilyTree;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class SisterInLawTest {

    @BeforeClass
    public static void configureFamilyTree() throws FileNotFoundException {
        FamilyTree.createFamilyTree();
    }

    @Test
    public void sisterInLawOfSisterShouldReturnBrothersWives_verifyFromFamilyTree() {
        testRelationsInFamilyTree("Ish", SISTER_IN_LAWS, "Amba", "Lika", "Chitra");
        testRelationsInFamilyTree("Atya", SISTER_IN_LAWS, "Satvy", "Krpi");
    }

    @Test
    public void sisterInLawOfSisterShouldReturnBrothersWives_behaviorTest() {
        List<Person> sisterInLaws = Relationship.getRelatives("Atya", SISTER_IN_LAWS);
        Person member = FamilyTree.findByName("Atya");
        assertNotNull(member);
        List<Person> brothers = member.getRelations(BROTHERS);
        assertNotNull(brothers);
        List<Person> brothersWives = brothers.stream().map(Person::getSpouse).collect(Collectors.toList());
        assertEquals(brothersWives, sisterInLaws);
    }

    @Test
    public void sisterInLawOfWifeShouldReturnHusbandSister_verifyFromFamilyTree() {
        testRelationsInFamilyTree("Amba", SISTER_IN_LAWS, "Satya");
        testRelationsInFamilyTree("Lika", SISTER_IN_LAWS, "Satya");
        testRelationsInFamilyTree("Chitra", SISTER_IN_LAWS, "Satya");
        testRelationsInFamilyTree("Satvy", SISTER_IN_LAWS, "Atya");
        testRelationsInFamilyTree("Krpi", SISTER_IN_LAWS, "Atya");
    }

    @Test
    public void sisterInLawOfWifeShouldReturnHusbandSister_behaviorTest() {
        List<Person> sisterInLaws = Relationship.getRelatives("Amba", SISTER_IN_LAWS);
        Person member = FamilyTree.findByName("Amba");
        assertNotNull(member);
        assertNotNull(member.getSpouse());
        List<Person> husbandSisters = member.getSpouse().getRelations(SISTERS);
        assertNotNull(husbandSisters);
        assertEquals(husbandSisters, sisterInLaws);
    }

    @Test
    public void sisterInLawOfHusbandShouldReturnWifeSister_verifyFromFamilyTree() {
        testRelationsInFamilyTree("Jaya", SISTER_IN_LAWS, "Tritha");
    }

    @Test
    public void sisterInLawOfHusbandShouldReturnWifeSister_behaviorTest() {
        List<Person> sisterInLaws = Relationship.getRelatives("Jaya", SISTER_IN_LAWS);
        Person member = FamilyTree.findByName("Jaya");
        assertNotNull(member);
        Person wife = member.getSpouse();
        assertNotNull(wife);
        List<Person> wifeSisters = wife.getRelations(SISTERS);
        assertEquals(wifeSisters, sisterInLaws);
    }

    @Test
    public void sisterInLawOfHusbandShouldReturnWifeNewlyAddedSister() {
        Command.addChild(new String[]{ADD_CHILD, "Chitra", "Jani", "F"});
        testRelationsInFamilyTree("Arit", SISTER_IN_LAWS, "Jani");
    }

}
