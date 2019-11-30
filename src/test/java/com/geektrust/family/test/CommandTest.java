package com.geektrust.family.test;

import com.geektrust.family.FamilyTree;
import com.geektrust.family.commands.Command;
import com.geektrust.family.model.Person;
import com.geektrust.family.model.Sex;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;

import static com.geektrust.family.commands.Command.ADD_CHILD;
import static org.junit.Assert.*;

public class CommandTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void configureFamilyTree() throws FileNotFoundException {
        FamilyTree.createFamilyTree();
    }

    @Test
    public void addKing_kingShouldBeInFamilyTree() {
        Person kingShan = FamilyTree.findByName("King Shan");
        assertNotNull(kingShan);
        assertEquals("King Shan", kingShan.getName());
    }

    @Test
    public void addSpouse_queenShouldBeInFamilyTree() {
        Person queenAnga = FamilyTree.findByName("Queen Anga");
        assertNotNull(queenAnga);
        assertEquals("Queen Anga", queenAnga.getName());
    }

    @Test
    public void addSpouse_queenShouldBeSpouseOfKing() {
        Person kingShan = FamilyTree.findByName("King Shan");
        Person queenAnga = FamilyTree.findByName("Queen Anga");
        assertEquals(kingShan, queenAnga.getSpouse());
    }

    @Test
    public void addChild_fatherAsParent_childAdditionSucceds() {
        String[] tokens = {ADD_CHILD, "King Shan", "JuniorShan", "M"};
        Command.addChild(tokens);
        Person member = FamilyTree.findByName("JuniorShan");
        assertNotNull(member);
        assertEquals("JuniorShan", member.getName());
        assertEquals(Sex.MALE, member.getSex());
    }

    @Test
    public void addChild_motherAsParent_childAdditionSucceds() {
        String[] tokens = {ADD_CHILD, "Queen Anga", "JuniorAnga", "F"};
        Command.addChild(tokens);
        Person member = FamilyTree.findByName("JuniorAnga");
        assertNotNull(member);
        assertEquals("JuniorAnga", member.getName());
        assertEquals(Sex.FEMALE, member.getSex());
    }

    @Test
    public void addChild_parentDoesNotExist_throwIllegalArgumentException() {
        String[] tokens = {ADD_CHILD, "Vich1", "JrVich", "F"};
        expectedException.expect(IllegalArgumentException.class);
        Command.addChild(tokens);
    }

    @Test
    public void addChild_parentExists_tokenIsNull_throwIllegalArgumentException() {
        String[] tokens = null;
        expectedException.expect(IllegalArgumentException.class);
        Command.addChild(tokens);
    }

    @Test
    public void addChild_parentExists_incorrectTokenSize_throwIllegalArgumentException() {
        String[] tokens = {"King Shan", "M"};
        expectedException.expect(IllegalArgumentException.class);
        Command.addChild(tokens);
    }

    @Test
    public void addChild_parentExists_parentHasNoSpouse_throwIllegalArgumentException() {
        String[] tokens = {"Vila", "Rohit", "M"};
        expectedException.expect(IllegalArgumentException.class);
        Command.addChild(tokens);
    }

    @Test
    public void getSex_invalidArgument_returnsNull() {
        assertNull(Command.getSex("B"));
        assertNull(Command.getSex("Ball"));
    }

    @Test
    public void getSex_argumentIsM_returnsMale() {
        assertEquals(Sex.MALE, Command.getSex("M"));
    }

    @Test
    public void getSex_argumentIsMale_returnsMale() {
        assertEquals(Sex.MALE, Command.getSex("Male"));
    }

    @Test
    public void getSex_argumentIsF_returnsMale() {
        assertEquals(Sex.FEMALE, Command.getSex("F"));
    }

    @Test
    public void getSex_argumentIsFemale_returnsMale() {
        assertEquals(Sex.FEMALE, Command.getSex("Female"));
    }

    @Test
    public void getSex_argumentNull_returnsNull() {
        assertNull(Command.getSex(null));
    }
}
