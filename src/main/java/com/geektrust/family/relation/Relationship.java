package com.geektrust.family.relation;

import com.geektrust.family.FamilyTree;
import com.geektrust.family.model.Person;

import java.util.List;

import static java.util.Objects.isNull;


public class Relationship {

    public static final String SON = "Son";
    public static final String DAUGHTER = "Daughter";
    public static final String BROTHERS = "Brothers";
    public static final String SISTERS = "Sisters";
    public static final String SIBLINGS = "Siblings";
    public static final String BROTHER_IN_LAWS = "Brother-In-Law";
    public static final String SISTER_IN_LAWS = "Sister-In-Law";
    public static final String PATERNAL_UNCLES = "Paternal-Uncle";
    public static final String PATERNAL_AUNTS = "Paternal-Aunt";
    public static final String MATERNAL_UNCLES = "Maternal-Uncle";
    public static final String MATERNAL_AUNTS = "Maternal-Aunt";
    public static final String PARENTS = "Parents";

    /**
     * Get all the relatives of the person with name {@code name} and based
     * on the relationship {@code relation}
     *
     * @param name
     * @param relation
     * @return
     */
    public static List<Person> getRelatives(String name, String relation) {

        String validationMessage = validatePersonRelation(name, relation);

        if (!isNull(validationMessage)) {
            System.out.println(validationMessage);
            return null;
        }
        Person person = FamilyTree.findByName(name);
        return person.getRelations(relation);
    }

    /**
     * Validates if the {@code name} and {@code relation}
     * actually corresponds to an existing member in the family tree
     * and given relation is  valid relationship that is supported.
     *
     * @param name     name of the person that needs to be validated
     * @param relation the relationship  that needs to be validated.
     * @return null if valid or validation failure message.
     */
    private static String validatePersonRelation(String name, String relation) {

        Person person = FamilyTree.findByName(name);
        if (isNull(person)) {
            System.out.format("Person %s not found ",name);
            return ("PERSON_NOT_FOUND");
        }

        if (!person.isValidRelation(relation)) {
            System.out.format("Relation %s not supported ",relation);
            return ("RELATION_DOES_NOT_EXIST");
        }

        return null;
    }

}


