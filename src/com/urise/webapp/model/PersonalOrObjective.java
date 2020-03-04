package com.urise.webapp.model;

public class PersonalOrObjective extends AbstractSection<String> {

    public PersonalOrObjective(String composition) {
        super(composition);
    }

    @Override
    public String getCompositionString() {
        return getComposition();
    }
}
