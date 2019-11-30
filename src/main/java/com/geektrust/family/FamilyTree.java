package com.geektrust.family;

import com.geektrust.family.commands.AddChildCommand;
import com.geektrust.family.commands.AddFindOlderCommand;
import com.geektrust.family.commands.CommandInvoker;
import com.geektrust.family.commands.GetRelationshipCommand;
import com.geektrust.family.model.Person;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import static com.geektrust.family.commands.Command.ADD_CHILD;
import static com.geektrust.family.commands.Command.FIND_OLDER;
import static com.geektrust.family.commands.Command.GET_RELATIONSHIP;
import static java.util.Objects.isNull;

/**
 * Main class to handle the operations on the family tree.
 */
public class FamilyTree {

    private static Map<String, Person> allMembers = new LinkedHashMap<>();
    private static CommandInvoker commandInvoker = new CommandInvoker();

    public static void main(String[] args) throws FileNotFoundException {
        if ((args.length != 1)) {
            Usage();
        }
        createFamilyTree();
        registerCommands();
        handleInputTestFile(args);
    }

    private static void registerCommands() {
        commandInvoker.register(GET_RELATIONSHIP, new GetRelationshipCommand());
        commandInvoker.register(ADD_CHILD, new AddChildCommand(false));
        commandInvoker.register(FIND_OLDER, new AddFindOlderCommand());
    }

    /**
     * Delegates the responsibility of parsing the familytree.txt seed and initializing
     * {@link #allMembers} to
     * {@link FileHandler} class
     *
     * @throws FileNotFoundException if the seed file is not found
     */
    public static void createFamilyTree() throws FileNotFoundException {
        FileHandler.createFamilyFromInitFile();
    }

    /**
     * Get the member of the family tree by name.
     *
     * @param name name of the member of the family
     * @return {@link Person} object corresponding the name
     */
    public static Person findByName(String name) {
        if (isNull(name)) {
            return null;
        }
        return allMembers.get(name.toUpperCase());
    }


    /**
     * Updates the family tree with the given {@code name} and {@code person}
     * Throws {@link IllegalArgumentException} if the {@code name} is {@code null}
     * or if the {@code person} is {@code null} or if {@code name} is not equals to {@code person's }name
     */
    public static void updateTree(String name, Person person) {
        if (isNull(name) ) {
            throw new IllegalArgumentException("name is null");
        }
        if (isNull(person) ) {
            throw new IllegalArgumentException("person is null");
        }
        if (!Objects.equals(name,person.getName())) {
            throw new IllegalArgumentException("name does not match with given person");
        }
        allMembers.put(name.toUpperCase(), person);
    }

    private static void handleInputTestFile(String[] args) throws FileNotFoundException {
        File file = getFileFromArgs(args);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] tokens = line.split("\\s+");
                if (tokens.length > 0) {
                    String operation = tokens[0];
                    commandInvoker.execute(operation, tokens);
                }
            }
        }
    }

    private static File getFileFromArgs(String[] args) {
        String filePath = args[0];
        if (isNull(filePath)) {
            Usage();
        }
        return new File(filePath);
    }

    private static void Usage() {
        String msg =
                "Correct Usage: \n\t  " +
                        "gradle run --args \"/path/to/inputfile.txt\" \n\t or \n\t" +
                        "java -jar $geektrustproject/build/libs/geektrust.jar /path/to/inputfile.txt ";
        throw new RuntimeException(msg);
    }

}
