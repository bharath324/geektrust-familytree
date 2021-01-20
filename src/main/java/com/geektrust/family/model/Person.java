package com.geektrust.family.model;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.geektrust.family.relation.Relationship.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Represents any person , a family member
 */
public class Person {
    private static Map<String, Function<Person, List<Person>>> relationBehaviorMap = new HashMap<>();

    static {
        relationBehaviorMap.put(BROTHERS, person -> Person.getSiblingsBasedOnGender(person, Sex.MALE));
        relationBehaviorMap.put(SISTERS, person -> Person.getSiblingsBasedOnGender(person, Sex.FEMALE));
        relationBehaviorMap.put(SIBLINGS, person -> Person.getSiblingsBasedOnGender(person, null));
        relationBehaviorMap.put(SON,Person::getSons);
        relationBehaviorMap.put(DAUGHTER,Person::getDaughters);
        relationBehaviorMap.put(BROTHER_IN_LAWS, person -> Person.getBrotherOrSisterInLaws(person, Sex.MALE));
        relationBehaviorMap.put(SISTER_IN_LAWS, person -> Person.getBrotherOrSisterInLaws(person, Sex.FEMALE));
        relationBehaviorMap.put(MATERNAL_UNCLES, person -> Person.getMaternalAuntiesOrUncles(person, Sex.MALE));
        relationBehaviorMap.put(MATERNAL_AUNTS, person -> Person.getMaternalAuntiesOrUncles(person, Sex.FEMALE));
        relationBehaviorMap.put(PATERNAL_UNCLES, person -> Person.getPaternalAuntOrUncle(person, Sex.MALE));
        relationBehaviorMap.put(PATERNAL_AUNTS, person -> Person.getPaternalAuntOrUncle(person, Sex.FEMALE));
        relationBehaviorMap.put(PARENTS, person -> Arrays.asList(person.getParents().getFather(),
                                                                    person.getParents().getMother()));
    }

    private String name;
    private Sex sex;
    private Person spouse;
    private Parents parents;
    private List<Person> children = new ArrayList<>();

    public Person(String name, Sex sex, Parents parents) {
        this.name = name;
        this.sex = sex;
        this.parents = parents;
    }

    /**
     * Returns paternal aunts or paternal uncles.
     * If {@code sex} is {@link Sex#MALE} then returns paternal aunts.
     * If {@code sex} is {@link Sex#FEMALE} then returns paternal uncles.
     */
    private static List<Person> getPaternalAuntOrUncle(Person person, Sex sex) {
        if (!validate(person)) {
            return Collections.emptyList();
        }
        Person father = person.getParents().getFather();
        return getSiblingsBasedOnGender(father, sex);
    }

    /**
     * Returns maternal aunt or maternal uncle.
     * If {@code sex} is {@link Sex#FEMALE} then returns maternal aunts.
     * If {@code sex} is {@link Sex#MALE} then returns maternal uncles.
     */
    private static List<Person> getMaternalAuntiesOrUncles(Person person, Sex sex) {
        if (!validateMother(person)) {
            return Collections.emptyList();
        }
        Person mother = person.getParents().getMother();
        return getSiblingsBasedOnGender(mother, sex);
    }

    /**
     * Validates if the given {@code person} is not {@code null}
     * and if the {@code person} has parents and has mother.
     */
    private static boolean validateMother(Person person) {
        return !isNull(person)
                && !isNull(person.getParents())
                && !isNull(person.getParents().getMother());
    }

    /**
     * Checks if the given {@code person} is {@code not null} and
     * has a father
     */
    private static boolean validate(Person person) {
        return !Objects.isNull(person) && person.hasFather();
    }

    /**
     * Get siblings based on the gender.
     * Returns brothers when {@code sex} is MALE.
     * Returns sisters when {@code sex } is FEMALE
     * Returns all siblings when {@code sex } is {@code null}
     */
    private static List<Person> getSiblingsBasedOnGender(Person person, Sex sex) {
        if (!validate(person))
            return Collections.emptyList();
        Person father = person.getParents().getFather();
        List<Person> siblings = new ArrayList<>();
        for (Person p : father.getChildren()) {
            if (p != person && (isNull(sex) || (p.getSex() == sex))) {
                siblings.add(p);
            }
        }
        return siblings;
    }

    /**
     * Gets brother-in-laws or sister-in-laws based on the parameter {@code sex}
     * If the {@code sex } is MALE then it returns brother-in-laws.
     * If the {@code sex}  is FEMALE then it returns sister-in-laws.
     */
    private static List<Person> getBrotherOrSisterInLaws(Person person, Sex sex) {
        if (isNull(person) || isNull(person.getSex())) {
            return Collections.emptyList();
        }
        List<Person> inLaws = new ArrayList<>();
        addInLawsFromSpouseSiblings(person, sex, inLaws);
        addInLawsFromSiblingsSpouses(person, sex == Sex.MALE ? Sex.FEMALE : Sex.MALE, inLaws);
        return inLaws;
    }

    private static void addInLawsFromSiblingsSpouses(Person person, Sex sex, List<Person> inLaws) {
        List<Person> personsSiblings = getSiblingsBasedOnGender(person, sex);
        if (!isNull(personsSiblings)) {
            personsSiblings.forEach(sibling -> {
                        if (!isNull(sibling) && !isNull(sibling.getSpouse())) {
                            inLaws.add(sibling.getSpouse());
                        }
                    }
            );
        }
    }

    private static void addInLawsFromSpouseSiblings(Person person, Sex sex, List<Person> inLaws) {
        Person spouse = person.getSpouse();
        List<Person> spouseSiblings = getSiblingsBasedOnGender(spouse, sex);
        if (!isNull(spouseSiblings) && !spouseSiblings.isEmpty()) {
            inLaws.addAll(spouseSiblings);
        }
    }

    public String getName() {
        return name;
    }

    public Sex getSex() {
        return sex;
    }

    public Person getSpouse() {
        return spouse;
    }

    /**
     * Marry the given spouse by validating the spouse according the rules {@link #validateSpouse(Person)}
     * and setting it to this person's spouse variable.
     */
    public void marry(Person spouse) {
        validateSpouse(spouse);
        this.spouse = spouse;
    }

    public Parents getParents() {
        return parents;
    }

    public boolean hasFather() {
        return hasParents() && nonNull(parents.getFather());
    }

    /**
     * Returns the children of this person.
     * The returned list is not backed by this list.
     * Hence any modifications from the client are not reflected in this person childrens list.
     */
    public List<Person> getChildren() {
        return new ArrayList<>(children);
    }

    /**
     * Validates the child according to the {@link #validateChild(Person) validation logic}
     * and adds the child to if validation succeeds.
     */
    public void addChild(Person child) {
        validateChild(child);
        children.add(child);
    }

    @Override
    public String toString() {
        return getName();
    }

    public List<Person> getSons() {
        return getChildrenBasedOnGender(Sex.MALE);
    }

    public List<Person> getDaughters() {
        return getChildrenBasedOnGender(Sex.FEMALE);
    }

    public int getGeneration() {

        if(Objects.isNull(this.parents) && Objects.isNull(this.getSpouse())){
              throw new IllegalStateException("Person "+this.name+" has neither a spouse nor parents");
        }
        if(Objects.isNull(this.parents)){
            Person spouse = this.getSpouse();
            return spouse.getGeneration();
        }
        Parents tempParents = this.parents;
        int depth = 0;
        while (tempParents != null) {
            depth++;
            Parents paternalGrandParents = tempParents.getFather().getParents();
            Parents maternalGrandParents = tempParents.getMother().getParents();
            if (paternalGrandParents == null) {
                tempParents = maternalGrandParents;
            } else {
                tempParents = paternalGrandParents;
            }
        }
        return depth;
    }

    private boolean hasParents() {
        return nonNull(parents);
    }

    /**
     * Validates the given {@code spouse} based on the following rules:
     * <li/> If the spouse is {@code null}
     * <li/> If this person's spouse is already married to  person who is NOT {@code spouse}
     * <li/> If the given spouse is already married to another person who is not this person
     * <p/> then {@link IllegalArgumentException} is thrown.
     *
     * @param spouse
     */
    private void validateSpouse(Person spouse) {
        if (isNull(spouse)) {
            throw new IllegalArgumentException("The given spouse is null");
        }
        if (!isNull(this.getSpouse()) && this.getSpouse() != spouse) {
            throw new IllegalArgumentException("This person:" + this + " is already married to another person");
        }
        if (!isNull(spouse.getSpouse()) && spouse.getSpouse() != this) {
            throw new IllegalArgumentException("The spouse :" + spouse + " is already married to another person");
        }
    }

    /**
     * Validates the given child based on the rules below:
     * <li/> If the child is {@code null} or
     * <li/> If the {@link #getParents() childs parents} are {@code null} or
     * <li/> If this person is father and the childs father is {@code null} or
     * <li/> If this person is mother and the childs mother is {@code null}
     * <p/> then an {@link IllegalArgumentException} is thrown
     */
    private void validateChild(Person child) {
        if (isNull(child)) {
            throw new IllegalArgumentException("Child that needs to be added is null");
        }
        if (isNull(child.getParents())) {
            throw new IllegalArgumentException("Child " + child + " does not have parents set ");
        }
        if (sex == Sex.MALE && this != child.getParents().getFather()) {
            throw new IllegalArgumentException(
                    String.format("Child %s has a  father:%s who is different " +
                                    "from this male person:%s the child added to \n",
                            child, child.getParents().getFather(), this.name));

        }
        if (sex == Sex.FEMALE && this != child.getParents().getMother()) {
            throw new IllegalArgumentException(
                    String.format("Child %s has a  mother:%s who is different " +
                                    "from this female person:%s the child added to\n",
                            child, child.getParents().getMother(), this.name));

        }
    }

    /**
     * Returns children based on the gender.
     * If the {@code sex} is MALE, then return list has Sons
     * If the {@code sex} is FEMALE, then return list has Daughters.
     *
     * @param sex
     * @return
     */
    private List<Person> getChildrenBasedOnGender(Sex sex) {
        if (!validateChildren()) {
            return Collections.emptyList();
        }
        return this.children.stream().filter(child -> child.getSex() == sex)
                .collect(Collectors.toList());
    }

    /**
     * Validate the list of the children whether it is {@code null} or empty and returns true.
     * Otherwise returns false.
     *
     * @return
     */
    private boolean validateChildren() {
        return !isNull(this.children)
                && !this.children.isEmpty();
    }

    public List<Person> getRelations(String relation) {
        return relationBehaviorMap.get(relation).apply(this);
    }

    public boolean isValidRelation(String relation) {
        return relationBehaviorMap.containsKey(relation);
    }
}
