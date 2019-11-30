package com.geektrust.family.commands;

import com.geektrust.family.FamilyTree;
import com.geektrust.family.exceptions.PersonNotFoundException;
import com.geektrust.family.model.Person;

import java.util.Arrays;
import java.util.Objects;

public class AddFindOlderCommand implements Command {

    @Override
    public void execute(String[] tokens) {
        System.out.println("Tokens : "+ Arrays.asList(tokens)+" "+tokens.length);
        if (tokens.length != 3) {
            throw new IllegalArgumentException("invalid tokens");
        }
        String firstPersonName = tokens[1];
        String secondPersonName = tokens[2];

        Person firstPerson = FamilyTree.findByName(firstPersonName);
        Person secondPerson = FamilyTree.findByName(secondPersonName);

        validatePerson(firstPersonName, firstPerson);
        validatePerson(secondPersonName, secondPerson);

        int depthOfFirstPerson = firstPerson.getGeneration();
        int depthOfSecondPerson = secondPerson.getGeneration();

        System.out.format(" %s : %d \t %s : %d \n",
                firstPersonName, depthOfFirstPerson, secondPersonName, depthOfSecondPerson);
        if (depthOfFirstPerson == 0) {
            System.out.println(firstPersonName);
        } else if (depthOfSecondPerson == 0) {
            System.out.println(secondPersonName);
        } else if (depthOfFirstPerson > depthOfSecondPerson) {
            System.out.println(firstPersonName);
        } else {
            System.out.println(secondPersonName);
        }
    }

    private void validatePerson(String firstName, Person firstPerson) {
        if (Objects.isNull(firstPerson)) {
            throw new PersonNotFoundException(String.format("%s not found ", firstName));
        }
    }


}
