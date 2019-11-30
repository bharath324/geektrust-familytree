package com.geektrust.family.test;

import com.geektrust.family.FamilyTree;
import com.geektrust.family.model.Person;
import com.geektrust.family.model.Sex;
import com.geektrust.family.relation.Relationship;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;
import java.util.List;

import static com.geektrust.family.test.TestUtils.testRelationsInFamilyTree;
import static org.junit.Assert.*;
import static com.geektrust.family.relation.Relationship.BROTHERS;

@RunWith(JUnit4.class)
public class GetBrothersTest {

    @BeforeClass
    public static void configureFamilyTree() throws FileNotFoundException {
        FamilyTree.createFamilyTree();
    }

    @Test
    public void brothersOfMemberAreMembersFathersMaleChildren() {
        List<Person> brothers = Relationship.getRelatives("Ish", BROTHERS);
        Person member = FamilyTree.findByName("Ish");
        assertNotNull(brothers);
        assertNotNull(member);
        Person father = member.getParents().getFather();
        assertNotNull(father);
        father.getSons().stream().filter(c -> c != member).forEach(c -> assertEquals(Sex.MALE, c.getSex()));
    }

    @Test
    public void brothersOfMaleWithNoBrothersShouldReturnNone() {
        testRelationsInFamilyTree("King Shan",BROTHERS);
    }

    @Test
    public void brothersOfFemaleWithNoBrothersShouldReturnNone() {
        testRelationsInFamilyTree("Amba",BROTHERS);
    }

    @Test
    public void brothersOfMemberShouldBeMaleChildren() {
        List<Person> brothers = Relationship.getRelatives("Aras", BROTHERS);
        assertNotNull(brothers);
        brothers.forEach(brother -> assertEquals(Sex.MALE, brother.getSex()));
    }

    @Test
    public void brothersOfMemberShouldHaveSameParents() {
        List<Person> brothers = Relationship.getRelatives("Aras", BROTHERS);
        Person member = FamilyTree.findByName("Aras");
        assertNotNull(member);
        assertNotNull(brothers);
        brothers.forEach(brother -> assertEquals(member.getParents(), brother.getParents()));
    }

    @Test
    public void memberWithManyBrothersShouldReturnListAsPerFamilyTree() {
        testRelationsInFamilyTree("Satya", BROTHERS, "Chit", "Ish", "Vich", "Aras");
    }

    @Test
    public void memberWithOnlyOneBrotherShouldReturnOnePersonAsPerFamilyTree() {
        testRelationsInFamilyTree("Dritha", BROTHERS,"Vritha");
    }

}
