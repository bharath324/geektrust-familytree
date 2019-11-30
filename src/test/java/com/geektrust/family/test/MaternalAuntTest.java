package com.geektrust.family.test;

import com.geektrust.family.FamilyTree;
import com.geektrust.family.commands.Command;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;

import static com.geektrust.family.commands.Command.ADD_CHILD;
import static com.geektrust.family.test.TestUtils.testBehaviorOfRelationFromParents;
import static com.geektrust.family.test.TestUtils.testRelationsInFamilyTree;
import static com.geektrust.family.relation.Relationship.*;

@RunWith(JUnit4.class)
public class MaternalAuntTest {

    @BeforeClass
    public static void configureFamilyTree() throws FileNotFoundException {
        FamilyTree.createFamilyTree();
    }

    @Test
    public void memberWithOnlyOneMaternalAuntShouldReturnOnePersonAsPerFamilyTree() {
        testRelationsInFamilyTree("Yodhan", MATERNAL_AUNTS, "Tritha");
    }

    @Test
    public void maternalAuntShouldReturnMotherSister() {
        testBehaviorOfRelationFromParents("Yodhan", false, MATERNAL_AUNTS, SISTERS);
    }

    @Test
    public void membersWithNoMaternalAuntInFamilyTree() {
        testRelationsInFamilyTree("Dritha", MATERNAL_AUNTS);
        testRelationsInFamilyTree("Vila", MATERNAL_AUNTS);
        testRelationsInFamilyTree("Jnki", MATERNAL_AUNTS);
        testRelationsInFamilyTree("Asva", MATERNAL_AUNTS);
        testRelationsInFamilyTree("Kriya", MATERNAL_AUNTS);
        testRelationsInFamilyTree("Krithi", MATERNAL_AUNTS);
        testRelationsInFamilyTree("Vasa", MATERNAL_AUNTS);
    }

    @Test
    public void newSisterAddedToPersonMotherShouldBeMaternalAunt() {
        Command.addChild(new String[]{ADD_CHILD, "Amba", "Yogita", "F"});
        testRelationsInFamilyTree("Yodhan", MATERNAL_AUNTS, "Tritha", "Yogita");
    }
}
