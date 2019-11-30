package com.geektrust.family.commands;

import com.geektrust.family.FamilyTree;
import com.geektrust.family.model.Parents;
import com.geektrust.family.model.Person;
import com.geektrust.family.model.Sex;

import java.util.Objects;

import static java.util.Objects.isNull;

public interface Command {

    String ADD_KING = "ADD_KING";
    String ADD_SPOUSE = "ADD_SPOUSE";
    String ADD_CHILD = "ADD_CHILD";
    String GET_RELATIONSHIP = "GET_RELATIONSHIP";
    String FIND_OLDER = "FIND_OLDER";

    /**
     * Get the Sex or Gender based on {@code token}
     * "M" or "Male" and "F" or "Female"
     */
    static Sex getSex(String token) {
        if ("M".equals(token) || "Male".equalsIgnoreCase(token)) {
            return Sex.MALE;
        } else if ("F".equals(token) || "Female".equalsIgnoreCase(token)) {
            return Sex.FEMALE;
        }
        return null;
    }

    /**
     * Adds a new child to the parent
     * <li/>Parent name is extracted from tokens[1]
     * <li/>Child name is taken from tokens[2]
     * <li/>And Gender of the child is taken from tokens[3]
     * <li/>A {@link Person} object is created from the above values.
     * <li/>This object is the new child which is added to the  list of children of parent and
     * parent's spouse.
     * <li/>Finally the family tree is updated with the child, the new member.
     *
     * @throws IllegalArgumentException if the parent of the child not found
     */
    static void addChild(String[] tokens) {
        if (Objects.isNull(tokens) || tokens.length < 4) {
            throw new IllegalArgumentException("tokens is null or invalid");
        }
        String parentName = tokens[1];
        String childName = tokens[2];
        Sex sex = getSex(tokens[3]);

        Person parent = FamilyTree.findByName(parentName);

        if (Objects.isNull(parent)) {
            throw new IllegalArgumentException("Parent " + parentName + " is not found");
        }
        if (Objects.isNull(parent.getSpouse())) {
            throw new IllegalArgumentException("Parent " + parentName + " has not spouse. " +
                    "Cannot add child to parent without spouse");
        }
        Parents parents = createParents(parent);
        Person child = new Person(childName, sex, parents);

        parent.addChild(child);
        parent.getSpouse().addChild(child);

        FamilyTree.updateTree(childName, child);
    }

    static Parents createParents(Person parent) {
        Parents parents;
        if (parent.getSex() == Sex.MALE) {
            parents = new Parents(parent, parent.getSpouse());
        } else {
            parents = new Parents(parent.getSpouse(), parent);
        }
        return parents;
    }

    default void validateTokensCommand(String[] tokens, String expectedCommand) {
        if (isNull(tokens) || tokens.length == 0) {
            throw new IllegalArgumentException("Illegal tokens :tokens array is empty ");
        }
        if (isNull(tokens[0]) || !Objects.equals(expectedCommand, tokens[0])) {
            throw new IllegalArgumentException("Illegal command from tokens array " + tokens[0]);
        }
    }

    void execute(String[] tokens);

}
