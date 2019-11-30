package com.geektrust.family.test;

import com.geektrust.family.FamilyTree;
import com.geektrust.family.model.Person;
import com.geektrust.family.model.Sex;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class FamilyTreeTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void configureFamilyTree() throws FileNotFoundException {
        FamilyTree.createFamilyTree();
    }

    @Test
    public void findByName_nameDoesNotExist_returnNull() {
        assertNull(FamilyTree.findByName("Peter"));
    }

    @Test
    public void findByName_nameIsNull_returnNull() {
        assertNull(FamilyTree.findByName(null));
    }

    @Test
    public void findByName_nameExist_returnPersonObject() {
        Person person = FamilyTree.findByName("King Shan");
        assertNotNull(person);
        assertEquals("King Shan", person.getName());
    }

    @Test
    public void findByName_nameIsNull_returnsNull() {
        assertNull(FamilyTree.findByName(null));
    }

    @Test
    public void updateTree_nameIsNull_throwIllegalArgumentException() {
        expectedException.expect(IllegalArgumentException.class);
        FamilyTree.updateTree(null, new Person("abc", Sex.MALE, null));
    }

    @Test
    public void updateTree_personIsNull_throwIllegalArgumentException() {
        expectedException.expect(IllegalArgumentException.class);
        FamilyTree.updateTree("abc", null);
    }

    @Test
    public void updateTree_nameDoesNotMatchPersonName_throwIllegalArgumentException() {
        expectedException.expect(IllegalArgumentException.class);
        FamilyTree.updateTree("abc", new Person("xyz", Sex.MALE, null));
    }

}
