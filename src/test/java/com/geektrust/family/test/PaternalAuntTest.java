package com.geektrust.family.test;

import com.geektrust.family.FamilyTree;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;

import static com.geektrust.family.relation.Relationship.SISTERS;
import static com.geektrust.family.test.TestUtils.testBehaviorOfRelationFromParents;
import static com.geektrust.family.test.TestUtils.testRelationsInFamilyTree;
import static com.geektrust.family.relation.Relationship.PATERNAL_AUNTS;

@RunWith(JUnit4.class)
public class PaternalAuntTest {

    @BeforeClass
    public static void configureFamilyTree() throws FileNotFoundException {
        FamilyTree.createFamilyTree();
    }

    @Test
    public void paternalAuntShouldReturnFatherSister() {
        testBehaviorOfRelationFromParents("Dritha", true, PATERNAL_AUNTS, SISTERS);
    }

    @Test
    public void membersWithOnePaternalAuntInFamilyTree() {
        testRelationsInFamilyTree("Dritha", PATERNAL_AUNTS, "Satya");
        testRelationsInFamilyTree("Vila", PATERNAL_AUNTS, "Satya");
        testRelationsInFamilyTree("Jnki", PATERNAL_AUNTS, "Satya");
        testRelationsInFamilyTree("Kriya", PATERNAL_AUNTS, "Atya");
        testRelationsInFamilyTree("Krithi", PATERNAL_AUNTS, "Atya");
        testRelationsInFamilyTree("Vasa", PATERNAL_AUNTS, "Atya");
    }


    @Test
    public void memberWithNoPaternalAuntsShouldReturnNoneAsPerFamilyTree() {
        testRelationsInFamilyTree("Asva", PATERNAL_AUNTS);

    }

}
