package com.geektrust.family.test;

import com.geektrust.family.FamilyTree;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;

import static com.geektrust.family.relation.Relationship.BROTHERS;
import static com.geektrust.family.test.TestUtils.testBehaviorOfRelationFromParents;
import static com.geektrust.family.test.TestUtils.testRelationsInFamilyTree;
import static com.geektrust.family.relation.Relationship.PATERNAL_UNCLES;


public class PaternalUncleTest {


    @BeforeClass
    public static void configureFamilyTree() throws FileNotFoundException {
        FamilyTree.createFamilyTree();
    }

    @Test
    public void paternalUncleShouldReturnFatherBrothers() {
        testBehaviorOfRelationFromParents("Dritha", true, PATERNAL_UNCLES, BROTHERS);
    }

    @Test
    public void memberWithManyPaternalUnclesShouldReturnListAsPerFamilyTree() {
        testRelationsInFamilyTree("Dritha", PATERNAL_UNCLES, "Ish", "Vich", "Aras");
        testRelationsInFamilyTree("Tritha", PATERNAL_UNCLES, "Ish", "Vich", "Aras");
        testRelationsInFamilyTree("Vritha", PATERNAL_UNCLES, "Ish", "Vich", "Aras");
        testRelationsInFamilyTree("Vila", PATERNAL_UNCLES, "Chit", "Ish", "Aras");
        testRelationsInFamilyTree("Ahit", PATERNAL_UNCLES, "Chit", "Ish", "Vich");
    }

    @Test
    public void memberWithNoPaternalUncleShouldReturnNoneAsPerFamilyTree() {
        testRelationsInFamilyTree("Arit", PATERNAL_UNCLES);
        testRelationsInFamilyTree("Laki", PATERNAL_UNCLES);
        testRelationsInFamilyTree("Lavnya", PATERNAL_UNCLES);
        testRelationsInFamilyTree("Asva", PATERNAL_UNCLES);
    }

    @Test
    public void memberWithOnlyOnePaternalUncleShouldReturnOnePersonAsPerFamilyTree() {
        testRelationsInFamilyTree("Kriya", PATERNAL_UNCLES, "Asva");
        testRelationsInFamilyTree("Krithi", PATERNAL_UNCLES, "Asva");
    }


}
