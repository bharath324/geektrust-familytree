package com.geektrust.family.test;

import com.geektrust.family.FamilyTree;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;

import static com.geektrust.family.test.TestUtils.testRelationsInFamilyTree;
import static com.geektrust.family.test.TestUtils.testBehaviorOfRelationFromParents;
import static com.geektrust.family.relation.Relationship.*;

@RunWith(JUnit4.class)
public class MaternalUncleTest {

    @BeforeClass
    public static void configureFamilyTree() throws FileNotFoundException {
        FamilyTree.createFamilyTree();
    }

    @Test
    public void maternalUncleShouldReturnMothersBrothers() {
        testBehaviorOfRelationFromParents("Asva", false, MATERNAL_UNCLES, BROTHERS);
    }

    @Test
    public void memberWithManyMaternalUnclesShouldReturnListAsPerFamilyTree() {
        testRelationsInFamilyTree("Asva", MATERNAL_UNCLES,
                "Chit", "Ish", "Vich", "Aras");
    }


    @Test
    public void membersWithNoMaternalUnclesInFamilyTree() {
        testRelationsInFamilyTree("Satvy", MATERNAL_UNCLES);
        testRelationsInFamilyTree("Dritha", MATERNAL_UNCLES);
        testRelationsInFamilyTree("Vila", MATERNAL_UNCLES);
        testRelationsInFamilyTree("Jnki", MATERNAL_UNCLES);
    }


    @Test
    public void membersWithOneMaternalUnclesInFamilyTree() {
        testRelationsInFamilyTree("Yodhan", MATERNAL_UNCLES, "Vritha");
        testRelationsInFamilyTree("Lavnya", MATERNAL_UNCLES, "Ahit");
    }

}
