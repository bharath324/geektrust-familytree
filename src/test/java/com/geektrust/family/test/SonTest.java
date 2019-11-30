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
import static com.geektrust.family.relation.Relationship.SON;
import static com.geektrust.family.test.TestUtils.testRelationsInFamilyTree;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class SonTest {

    @BeforeClass
    public static void configureFamilyTree() throws FileNotFoundException {
        FamilyTree.createFamilyTree();
    }

    @Test
    public void sonsShouldReturnMaleChildrenOfMember() {
        Person member = FamilyTree.findByName("King Shan");
        assertNotNull(member);
        List<Person> sons = member.getSons();
        List<Person> expectedSons = member.getChildren().stream().filter(c -> c != member && c.getSex() == Sex.MALE)
                .collect(Collectors.toList());
        assertEquals(expectedSons, sons);
    }

    @Test
    public void sonsOfFatherAreSameAsSonsOfMother() {
        Person father = FamilyTree.findByName("King Shan");
        assertNotNull(father);
        List<Person> sonsOfFather = father.getSons();
        Person mother = FamilyTree.findByName("Queen Anga");
        assertNotNull(mother);
        List<Person> sonsOfMother = mother.getSons();
        assertEquals(sonsOfFather, sonsOfMother);
    }

    @Test
    public void sonsOfMemberInFamilyTreeShouldReturnOnlyMaleChildren() {
        List<Person> sons = Relationship.getRelatives("King Shan", SON);
        Assert.assertNotNull(sons);
        Assert.assertFalse(sons.isEmpty());
        sons.forEach(daugther -> Assert.assertEquals(Sex.MALE, daugther.getSex()));
    }

    @Test
    public void memberWithOnlyOneSonShouldReturnOnePersonAsPerFamilyTree() {
        testRelationsInFamilyTree("Aras", SON, "Ahit");
    }

    @Test
    public void memberWithNoSonsShouldReturnNoneAsPerFamilyTree() {
        testRelationsInFamilyTree("Vich", SON);
    }

    @Test
    public void addingNewMaleChildToMotherShouldIncludeHerInMotherSonsList() {
        testRelationsInFamilyTree("Chit", SON, "Vritha");
        Command.addChild(new String[]{ADD_CHILD, "Amba", "Brit", "M"});
        testRelationsInFamilyTree("Chit", SON, "Vritha", "Brit");
    }

    @Test
    public void memberWithManySonsShouldReturnListAsPerFamilyTree() {
        testRelationsInFamilyTree("Satya", SON, "Asva", "Vyas");
        testRelationsInFamilyTree("Vyan", SON, "Asva", "Vyas");
    }
}
