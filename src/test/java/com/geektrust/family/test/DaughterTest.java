package com.geektrust.family.test;

import com.geektrust.family.FamilyTree;
import com.geektrust.family.commands.Command;
import com.geektrust.family.model.Person;
import com.geektrust.family.model.Sex;
import com.geektrust.family.relation.Relationship;
import org.junit.Assert;
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
import static com.geektrust.family.relation.Relationship.DAUGHTER;

@RunWith(JUnit4.class)
public class DaughterTest {

    @BeforeClass
    public static void configureFamilyTree() throws FileNotFoundException {
        FamilyTree.createFamilyTree();
    }

    @Test
    public void daughtersShouldReturnFemaleChildrenOfMember(){
        Person member = FamilyTree.findByName("Vich");
        assertNotNull(member);
        List<Person> daughters =  member.getDaughters();
        List<Person> expectedDaughters = member.getChildren().stream().filter(c -> c!=member && c.getSex() == Sex.FEMALE)
                .collect(Collectors.toList());
        assertEquals(expectedDaughters,daughters);
    }

    @Test
    public void memberWithNoDaughterShouldReturnNoneAsPerFamilyTree(){
        testRelationsInFamilyTree("Dritha", DAUGHTER);
        testRelationsInFamilyTree("Jaya", DAUGHTER);
    }

    @Test
    public void memberWithOnlyOneDaughterShouldReturnOnePersonAsPerFamilyTree() {
        testRelationsInFamilyTree("King Shan", DAUGHTER, "Satya");
        testRelationsInFamilyTree("Queen Anga", DAUGHTER, "Satya");
        testRelationsInFamilyTree("Vich", DAUGHTER, "Vila", "Chika");
    }

    @Test
    public void daughtersOfMemberInFamilyTreeShouldReturnOnlyFemaleChildren() {
        List<Person> daugthers = Relationship.getRelatives("Vich", DAUGHTER);
        Assert.assertNotNull(daugthers);
        Assert.assertFalse(daugthers.isEmpty());
        daugthers.forEach(daugther -> Assert.assertEquals(Sex.FEMALE, daugther.getSex()));
    }

    @Test
    public void daughtersOfHusbandAreSameAsDaughtersOfWife() {
        List<Person> daughtersOfHusband = Relationship.getRelatives("Aras", DAUGHTER);
        List<Person> daughtersOfWife = Relationship.getRelatives("Chitra", DAUGHTER);
        Assert.assertEquals(daughtersOfHusband, daughtersOfWife);
    }

    @Test
    public void addingNewFemaleChildToMotherShouldIncludeHerInMotherDaughterList() {
        testRelationsInFamilyTree("Chit", DAUGHTER, "Dritha", "Tritha");
        Command.addChild(new String[]{ADD_CHILD, "Amba", "Britha", "F"});
        testRelationsInFamilyTree("Chit", DAUGHTER, "Dritha", "Tritha", "Britha");
    }
}
