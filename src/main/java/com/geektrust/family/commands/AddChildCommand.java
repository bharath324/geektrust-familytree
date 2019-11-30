package com.geektrust.family.commands;

import com.geektrust.family.model.Person;
import com.geektrust.family.model.Sex;

import static com.geektrust.family.FamilyTree.findByName;
import static java.util.Objects.isNull;

public class AddChildCommand implements Command {

    private static final String CHILD_ADDITION_SUCCEEDED = "CHILD_ADDITION_SUCCEEDED";
    private static final String CHILD_ADDITION_FAILED = "CHILD_ADDITION_FAILED";
    private static final String PERSON_NOT_FOUND = "PERSON_NOT_FOUND";
    private boolean isInitFile;

    public AddChildCommand(boolean isInitFile) {
        this.isInitFile = isInitFile;
    }

    @Override
    public void execute(String[] tokens) {
        validateTokensCommand(tokens, ADD_CHILD);

        String mothersName = tokens[1];
        String childName = tokens[2];

        String validationResult = validateInputs(mothersName, childName);
        if (!isNull(validationResult) && !isInitFile) {
            System.out.println(validationResult);
            return;
        }
        Command.addChild(tokens);

        if (!isInitFile) {
            verifyAndLogChildAdditionResult(childName);
        }
    }

    private void verifyAndLogChildAdditionResult(String childName) {
        if (findByName(childName) != null) {
            System.out.println(CHILD_ADDITION_SUCCEEDED);
        } else {
            System.out.println(CHILD_ADDITION_FAILED);
        }
    }

    private String validateInputs(String mothersName, String childName) {

        Person mother = findByName(mothersName);
        if (isNull(childName) || isNull(mothersName) || isNull(mother)) {
            return PERSON_NOT_FOUND;
        }
        if (mother.getSex() == Sex.MALE) {
            return CHILD_ADDITION_FAILED;
        }
        return null;
    }
}
