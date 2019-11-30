package com.geektrust.family.test;

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
import static com.geektrust.family.relation.Relationship.BROTHERS;
import static com.geektrust.family.relation.Relationship.SISTERS;
import static com.geektrust.family.test.TestUtils.testRelationsInFamilyTree;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static com.geektrust.family.relation.Relationship.BROTHER_IN_LAWS;

@RunWith(JUnit4.class)
public class BrotherInLawTest {

    @BeforeClass
    public static void configureFamilyTree() throws FileNotFoundException {
        FamilyTree.createFamilyTree();
    }

    @Test
    public void brotherInLawOfBrotherShouldReturnSisterHusband() {
        testRelationsInFamilyTree("Ish", BROTHER_IN_LAWS, "Vyan");
    }

    @Test
    public void brotherInLawOfBrotherShouldReturnSisterHusband_behaviorTest() {
        List<Person> brotherInLaws = Relationship.getRelatives("Ish", BROTHER_IN_LAWS);
        assertNotNull(brotherInLaws);
        Person brother = FamilyTree.findByName("Ish");
        assertNotNull(brother);
        List<Person> sisters = brother.getRelations(SISTERS);
        assertNotNull(sisters);
        List<Person> sistersHusbands = sisters.stream().map(Person::getSpouse).collect(Collectors.toList());
        assertEquals(sistersHusbands,brotherInLaws);
    }

    @Test
    public void brotherInLawOfWifeShouldReturnHusbandBrothers_verifyFamilyTree() {
        testRelationsInFamilyTree("Amba", BROTHER_IN_LAWS,
                "Ish", "Vich", "Aras");
        testRelationsInFamilyTree("Lika", BROTHER_IN_LAWS,
                "Chit", "Ish", "Aras");
        testRelationsInFamilyTree("Chitra", BROTHER_IN_LAWS,
                "Chit", "Ish", "Vich");
        testRelationsInFamilyTree("Satvy", BROTHER_IN_LAWS, "Vyas");
        testRelationsInFamilyTree("Krpi", BROTHER_IN_LAWS, "Asva");
    }
    @Test
    public void brotherInLawOfWifeShouldReturnHusbandBrothers_behaviorTest() {
       testBrotherInLawFromSpouseBrothers("Amba");
    }
    @Test
    public void brotherInLawOfHusbandShouldReturnWifeBrothers_verifyFamilyTree() {
        testRelationsInFamilyTree("Vyan", BROTHER_IN_LAWS,
                "Chit", "Ish", "Vich", "Aras");
        testRelationsInFamilyTree("Jaya", BROTHER_IN_LAWS,
                "Vritha");
    }

    @Test
    public void brotherInLawOfHusbandShouldReturnWifeBrothers_behaviorTest() {
        testBrotherInLawFromSpouseBrothers("Vyan");
    }


    @Test
    public void brotherInLawOfSpinsterWithNoMarriedSistersShouldReturnNone() {
        testRelationsInFamilyTree("Atya", BROTHER_IN_LAWS);
        testRelationsInFamilyTree("Vila", BROTHER_IN_LAWS);
    }

    @Test
    public void brotherInLawOfHusbandWhoseWifeHasNewBrotherShouldReturnTheNewBrother() {
        testRelationsInFamilyTree("Arit", BROTHER_IN_LAWS, "Ahit");
        Command.addChild(new String[]{ADD_CHILD, "Chitra", "Arch", "M"});
        testRelationsInFamilyTree("Arit", BROTHER_IN_LAWS, "Ahit", "Arch");
    }

    @Test
    public void brotherInLawShouldReturnNoneIfMemberHasNoMarriedSistersAndWifeHasNoBrothers() {
        testRelationsInFamilyTree("Asva", BROTHER_IN_LAWS);
    }

    private static void testBrotherInLawFromSpouseBrothers(String memberName) {
        List<Person> brotherInLaws =  Relationship.getRelatives(memberName, BROTHER_IN_LAWS);
        Person member = FamilyTree.findByName(memberName);
        assertNotNull(member);
        assertNotNull(brotherInLaws);
        assertNotNull(member.getSpouse());
        List<Person> memberBrothers = member.getSpouse().getRelations(BROTHERS);
        assertEquals(memberBrothers,brotherInLaws);
    }
}
