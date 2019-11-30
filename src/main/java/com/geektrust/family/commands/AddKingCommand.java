package com.geektrust.family.commands;

import com.geektrust.family.FamilyTree;
import com.geektrust.family.model.Person;
import com.geektrust.family.model.Sex;

public class AddKingCommand implements Command {

    @Override
    public void execute(String[] tokens) {

        validateTokensCommand(tokens, ADD_KING);

        String name = tokens[1];
        Sex sex = Command.getSex(tokens[2]);
        Person person = new Person(name, sex, null);
        FamilyTree.updateTree(name, person);
    }
}
