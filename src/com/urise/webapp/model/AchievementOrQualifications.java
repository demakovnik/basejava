package com.urise.webapp.model;

import java.util.List;

public class AchievementOrQualifications extends AbstractSection<List<String>> {

    public AchievementOrQualifications(List<String> composition) {
        super(composition);
    }

    @Override
    public String getCompositionString() {
        List<String> list = this.getComposition();
        StringBuilder sb = new StringBuilder();
        int size = getComposition().size();
        for (int i = 0; i < size; i++) {
            if (i < size - 1)
                sb.append(list.get(i) + "\n");
            else sb.append(list.get(i));
        }
        return sb.toString();
    }
}

