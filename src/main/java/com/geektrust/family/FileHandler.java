package com.geektrust.family;

import com.geektrust.family.commands.AddChildCommand;
import com.geektrust.family.commands.AddKingCommand;
import com.geektrust.family.commands.AddSpouseCommand;
import com.geektrust.family.commands.CommandInvoker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import static com.geektrust.family.commands.Command.*;


public class FileHandler {

    private static final CommandInvoker commandInvoker = new CommandInvoker();

    static void createFamilyFromInitFile() throws FileNotFoundException {
        InputStream inputStream = new FileHandler().getFileFromClasspathOrJar("familytree.txt");
        registerCommands();
        try (Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] tokens = line.split("\\;");
                String operation = tokens[0];
                commandInvoker.execute(operation, tokens);
            }
        }
    }

    private static void registerCommands() {
        commandInvoker.register(ADD_KING, new AddKingCommand());
        commandInvoker.register(ADD_SPOUSE, new AddSpouseCommand());
        commandInvoker.register(ADD_CHILD, new AddChildCommand(true));
    }

    private InputStream getFileFromClasspathOrJar(String fileName) throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException(fileName + " is not found!");
        } else {
            File file = new File(resource.getFile());
            if (file.exists()) {
                return new FileInputStream(file);
            }
            return getClass().getResourceAsStream("/" + fileName);
        }
    }

}
