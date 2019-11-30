package com.geektrust.family.commands;

import com.geektrust.family.FamilyTree;
import com.geektrust.family.model.Person;
import com.geektrust.family.model.Sex;

import java.util.Objects;

public class AddSpouseCommand implements Command {

    @Override
    public void execute(String[] tokens) {
        validateTokensCommand(tokens, ADD_SPOUSE);

        String personName = tokens[1];
        String spouseName = tokens[2];
        Sex sex = Command.getSex(tokens[3]);
        Person existingPerson = FamilyTree.findByName(personName);
        if (Objects.isNull(existingPerson)) {
            throw new IllegalArgumentException("Person " + personName + " is not found");
        }
        Person spouse = new Person(spouseName, sex, null);
        spouse.marry(existingPerson);
        existingPerson.marry(spouse);
        FamilyTree.updateTree(spouseName, spouse);
    }

}
