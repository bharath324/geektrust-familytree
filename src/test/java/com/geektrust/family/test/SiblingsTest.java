package com.geektrust.family.test;
import static com.geektrust.family.test.TestUtils.*;
import static org.junit.Assert.*;

import com.geektrust.family.FamilyTree;
import com.geektrust.family.model.Person;
import com.geektrust.family.relation.Relationship;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(JUnit4.class)
public class SiblingsTest {
    private static final String SIBLINGS ="Siblings";

    @BeforeClass
    public static void configureFamilyTree() throws FileNotFoundException {
        FamilyTree.createFamilyTree();
    }

    @Test
    public void siblingsOfMemberShouldReturnFathersChildrenExcludingMember(){
        List<Person> siblings = Relationship.getRelatives("Chit",SIBLINGS);
        Person member = FamilyTree.findByName("Chit");
        assertNotNull("Siblings are null for Chit" ,siblings);
        assertNotNull("Person Chit is not found",member);
        assertNotNull("Parents of Chit are not found",member.getParents());
        assertNotNull("Father of Chit is not found",member.getParents().getFather());
        Person father = member.getParents().getFather();
        List<Person> childrenExcludingMember= father.getChildren().stream()
                .filter(c -> c!= member).collect(Collectors.toList());
        assertEquals(childrenExcludingMember,siblings);
    }
    @Test
    public void memberWithManySiblingsShouldReturnListAsPerFamilyTree(){
        testRelationsInFamilyTree("Satya",SIBLINGS,"Chit","Ish","Vich","Aras");
        testRelationsInFamilyTree("Vyas",SIBLINGS,"Asva","Atya");
        testRelationsInFamilyTree("Asva",SIBLINGS,"Vyas","Atya");
        testRelationsInFamilyTree("Atya",SIBLINGS,"Asva","Vyas");
    }

    @Test
    public void memberWithNoSiblingsShouldReturnNoneAsPerFamilyTree(){
        testRelationsInFamilyTree("Amba",SIBLINGS);
    }

    @Test
    public void memberWithOnlyOneSiblingShouldReturnOnePersonAsPerFamilyTree(){
        testRelationsInFamilyTree("Vila",SIBLINGS,"Chika");
        testRelationsInFamilyTree("Jnki",SIBLINGS,"Ahit");
    }

}
