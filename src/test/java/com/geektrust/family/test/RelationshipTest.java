package com.geektrust.family.test;
import static com.geektrust.family.relation.Relationship.BROTHERS;
import com.geektrust.family.FamilyTree;
import com.geektrust.family.relation.Relationship;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class RelationshipTest {

    @BeforeClass
    public static void configureFamilyTree() throws FileNotFoundException {
        FamilyTree.createFamilyTree();
    }

    @Test
    public void getRelatives_nameDoesNotExist_returnsNull(){
        assertNull(Relationship.getRelatives("John", BROTHERS));
    }
    @Test
    public void getRelatives_nameIsNull_returnsNull(){
        assertNull(Relationship.getRelatives(null, BROTHERS));
    }
    @Test
    public void getRelatives_nameExists_relationExists_returnsPersonObject(){
        assertNotNull(Relationship.getRelatives("King Shan", BROTHERS));
    }
    @Test
    public void getRelatives_nameExists_relationDoesNotExist_returnsNull(){
        assertNull(Relationship.getRelatives("King Shan", "Aunt"));
    }
    @Test
    public void getRelatives_nameExists_relationIsNull_returnsNull(){
        assertNull(Relationship.getRelatives("King Shan", null));
    }
}
