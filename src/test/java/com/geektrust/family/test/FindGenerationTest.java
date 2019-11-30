package com.geektrust.family.test;

import com.geektrust.family.FamilyTree;
import com.geektrust.family.commands.AddFindOlderCommand;
import com.geektrust.family.commands.CommandInvoker;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;

public class FindGenerationTest {
    @BeforeClass
    public static void configureFamilyTree() throws FileNotFoundException {
        FamilyTree.createFamilyTree();
    }
    @Test
    public void whenGivenTwoNamesThenFirstPersonIsElderToSecondPerson() {
        String firstName = "Lavnya";
        String secondName = "Atya";

        String operation = "FIND_OLDER";
        String[] names = {operation, firstName, secondName};

        CommandInvoker commandInvoker = new CommandInvoker();
        commandInvoker.register(operation, new AddFindOlderCommand());

        commandInvoker.execute(operation, names);
    }

    @Test
    public void whenGivenTwoNamesWithKingShahAsFirstPerson() {
        String firstName = "King Shan";
        String secondName = "Atya";

        String operation = "FIND_OLDER";
        String[] names = {operation, firstName, secondName};

        CommandInvoker commandInvoker = new CommandInvoker();
        commandInvoker.register(operation, new AddFindOlderCommand());

        commandInvoker.execute(operation, names);
    }
}
