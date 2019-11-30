package com.geektrust.family.model;

/**
 * Encapsulate father & mother persons in one Parents class
 */
public class Parents {

    private Person father;
    private Person mother;

    public Parents(Person father, Person mother) {
        if (father == null || mother == null) {
            throw new NullPointerException("Father or mother is null");
        }
        this.father = father;
        this.mother = mother;
    }

    public Person getFather() {
        return father;
    }

    public Person getMother() {
        return mother;
    }

    @Override
    public String toString() {
        return String.format("%s,%s\n",
                (father != null ? father.getName() : null),
                (mother != null ? mother.getName() : null));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parents parents = (Parents) o;

        if (!father.equals(parents.father)) return false;
        return mother.equals(parents.mother);
    }

    @Override
    public int hashCode() {
        int result = father.hashCode();
        result = 31 * result + mother.hashCode();
        return result;
    }
}
