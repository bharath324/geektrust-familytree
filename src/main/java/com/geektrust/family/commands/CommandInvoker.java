package com.geektrust.family.commands;

import java.util.HashMap;
import java.util.Map;

public class CommandInvoker {

    private static final Map<String, Command> commandMap = new HashMap<>();

    public void register(String commandName, Command command) {
        commandMap.put(commandName, command);
    }


    public Command get(String commandName) {
        return commandMap.get(commandName);
    }

    public void execute(String commandName, String[] tokens) {
        Command command = commandMap.get(commandName);
        if (command == null) {
            throw new IllegalStateException("No command registered for " + commandName);
        }
        command.execute(tokens);
    }

}
