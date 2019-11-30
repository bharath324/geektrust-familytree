package com.geektrust.family.test;

import com.geektrust.family.FamilyTree;
import com.geektrust.family.model.Person;
import com.geektrust.family.relation.Relationship;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

class TestUtils {

    /**
     * <li/> Retrieves the relatives of the person with name {@code personName} for the relation {@Code relation}
     * <li/> Finds and retrieves the person objects from {@link FamilyTree} for all the {@code expectedRelativeNames}
     * <li/> Compares above two lists for truthfulness.
     * @param personName
     * @param relation
     * @param expectedRelativeNames
     */
    static void testRelationsInFamilyTree(String personName, String relation, String... expectedRelativeNames) {
        List<Person> actualRelatives = Relationship.getRelatives(personName, relation);
        List<Person> expectedRelatives = Arrays.stream(expectedRelativeNames).
                map(FamilyTree::findByName).collect(Collectors.toList());
        assertEquals(expectedRelatives, actualRelatives);
    }

    /**
     * Test the behavior of the relation {@code relation}
     * <li/>The given {@code memberName} and {@code relation} is used to fetch the relatives of member based on the relation
     * <li/>The parent (father if {@code isFather} is true or mother if false) of the member is fetched
     * <li/>All the relatives of the parent based on {@code parentsRelation} are fetched
     * <li/>The list of expected relatives from point 3 is asserted with those of point 1
     * @param memberName
     * @param relation
     */
    static void testBehaviorOfRelationFromParents(String memberName, boolean isFather, String relation,String parentsRelation) {
        List<Person> actualRelatives = Relationship.getRelatives(memberName,relation);

        Person member = FamilyTree.findByName(memberName);
        assertNotNull(memberName+" not found ",member);
        assertNotNull("Parents of the "+memberName+" are not found ",member.getParents());

        Person parent = isFather ? member.getParents().getFather() : member.getParents().getMother();
        assertNotNull("Parent "+parent+" of the member"+memberName+" is not found ",parent);

        List<Person> expectedRelatives = parent.getRelations(parentsRelation);
        assertEquals(expectedRelatives,actualRelatives);
    }

}
