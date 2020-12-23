package com.geektrust.family.commands;

import com.geektrust.family.model.Person;
import com.geektrust.family.relation.Relationship;

import java.util.List;

import static java.util.Objects.isNull;

public class GetRelationshipCommand implements Command {

    @Override
    public void execute(String[] tokens) {
        validateTokensCommand(tokens, GET_RELATIONSHIP);

        String name = tokens[1];
        String relation = tokens[2];

        List<Person> relatives = Relationship.getRelatives(name, relation);

        if (isNull(relatives)) {
            return;
        }
        if (relatives.isEmpty()) {
            System.out.format("No %s found for person %s\n",relation,name);
            return;
        }
        String output = relatives.toString()
                .replace("[", "")
                .replace("]", "")
                .replaceAll(",\\s+", " ");
        System.out.format("The %s of %s are %s\n",relation,name,relatives.toString());
    }
}
