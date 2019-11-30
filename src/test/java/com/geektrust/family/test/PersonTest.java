package com.geektrust.family.test;

import com.geektrust.family.FamilyTree;
import com.geektrust.family.model.Person;
import com.geektrust.family.model.Sex;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;

public class PersonTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void configureFamilyTree() throws FileNotFoundException {
        FamilyTree.createFamilyTree();
    }

    @Test
    public void marry_spouseIsNull_throwIllegalArgumentException() {
        Person kingShan = FamilyTree.findByName("King Shan");
        expectedException.expect(IllegalArgumentException.class);
        kingShan.marry(null);
    }

    @Test
    public void marry_personIsAlreadyMarriedToAnotherPerson_throwIllegalArgumentException() {
        Person kingShan = FamilyTree.findByName("King Shan");
        expectedException.expect(IllegalArgumentException.class);
        kingShan.marry(FamilyTree.findByName("Chitra"));
    }

    @Test
    public void marry_spouseIsAlreadyMarriedToAnotherPerson_throwIllegalArgumentException() {
        Person xyz = new Person("XYZ", Sex.MALE, null);
        expectedException.expect(IllegalArgumentException.class);
        xyz.marry(FamilyTree.findByName("Chitra"));
    }

    @Test
    public void marry_personIsMarriedToSpouse_getSpouseShouldReturnSpouse() {
        Person xyz = new Person("XYZ", Sex.MALE, null);
        Person abc = new Person("ABC", Sex.FEMALE, null);
        xyz.marry(abc);
        abc.marry(xyz);
        Assert.assertEquals(xyz.getSpouse(), abc);
        Assert.assertEquals(abc.getSpouse(), xyz);
    }
}
