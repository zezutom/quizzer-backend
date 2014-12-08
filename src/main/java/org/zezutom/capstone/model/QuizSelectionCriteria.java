package org.zezutom.capstone.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Defines a fine-grained quiz selection.
 */
public class QuizSelectionCriteria {

    private List<QuizCategory> categories;

    private List<QuizDifficulty> difficultyLevels;

    public QuizSelectionCriteria() {
        this.categories = new ArrayList<>();
        this.difficultyLevels = new ArrayList<>();
    }

    public void addCategories(QuizCategory... categories) {

        if (categories == null || categories.length == 0) {
            this.categories.addAll(getAllCategories());
            return;
        }

        for (QuizCategory category : categories) {
            if (category != null) {
                this.categories.add(category);
            }
        }
    }


    public void addDifficultyLevels(QuizDifficulty ... difficultyLevels) {

        if (difficultyLevels == null || difficultyLevels.length == 0) {
            this.difficultyLevels.addAll(getAllDifficultyLevels());
            return;
        }

        for (QuizDifficulty difficultyLevel : difficultyLevels) {
            if (difficultyLevel != null) {
                this.difficultyLevels.add(difficultyLevel);
            }
        }
    }

    public List<QuizCategory> getCategories() {
        return (categories == null || categories.isEmpty()) ? getAllCategories() : categories;
    }

    public List<QuizDifficulty> getDifficultyLevels() {
        return (difficultyLevels == null || difficultyLevels.isEmpty()) ? getAllDifficultyLevels() : difficultyLevels;
    }

    private List<QuizCategory> getAllCategories() {
        return Arrays.asList(QuizCategory.values());
    }

    private List<QuizDifficulty> getAllDifficultyLevels() {
        return Arrays.asList(QuizDifficulty.values());
    }

}
