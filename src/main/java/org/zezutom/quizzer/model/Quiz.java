package org.zezutom.quizzer.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * A quiz comprises four different movie titles, one of which being the odd one.
 *
 * username     Identifies the author of the quiz
 *
 * title        Helps to identify the quiz, it should capture the essence of the puzzle. Should only
 *              be shown on the quiz stats screen, since it could give a user a hint.
 *
 * optionOne     First movie title
 *
 * optionTwo     Second movie title
 *
 * optionThree   Third movie title
 *
 * optionFour    Fourth movie title
 *
 * answer        Identifies the correct choice
 *
 * explanation  Explains the reasoning for the answer
 *
 * difficulty   How difficult the game set is considered to be (easy, medium, tough)
 *
 */
@Entity
public class Quiz extends GenericEntity {

    private String question;

    private String optionOne;

    private String optionTwo;

    private String optionThree;

    private String optionFour;

    private Integer answer;

    private String explanation;

    @Enumerated(EnumType.STRING)
    private QuizDifficulty difficulty;

    @Enumerated(EnumType.STRING)
    private QuizCategory category;

    public Quiz() {}

    public Quiz(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String title) {
        this.question = title;
    }

    public String getOptionOne() {
        return optionOne;
    }

    public void setOptionOne(String optionOne) {
        this.optionOne = optionOne;
    }

    public String getOptionTwo() {
        return optionTwo;
    }

    public void setOptionTwo(String movieTwo) {
        this.optionTwo = movieTwo;
    }

    public String getOptionThree() {
        return optionThree;
    }

    public void setOptionThree(String movieThree) {
        this.optionThree = movieThree;
    }

    public String getOptionFour() {
        return optionFour;
    }

    public void setOptionFour(String movieFour) {
        this.optionFour = movieFour;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public QuizDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(QuizDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public QuizCategory getCategory() {
        return category;
    }

    public void setCategory(QuizCategory category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quiz)) return false;

        Quiz quiz = (Quiz) o;

        if (answer != null ? !answer.equals(quiz.answer) : quiz.answer != null) return false;
        if (explanation != null ? !explanation.equals(quiz.explanation) : quiz.explanation != null) return false;
        if (optionFour != null ? !optionFour.equals(quiz.optionFour) : quiz.optionFour != null) return false;
        if (optionOne != null ? !optionOne.equals(quiz.optionOne) : quiz.optionOne != null) return false;
        if (optionThree != null ? !optionThree.equals(quiz.optionThree) : quiz.optionThree != null) return false;
        if (optionTwo != null ? !optionTwo.equals(quiz.optionTwo) : quiz.optionTwo != null) return false;
        if (question != null ? !question.equals(quiz.question) : quiz.question != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = question != null ? question.hashCode() : 0;
        result = 31 * result + (optionOne != null ? optionOne.hashCode() : 0);
        result = 31 * result + (optionTwo != null ? optionTwo.hashCode() : 0);
        result = 31 * result + (optionThree != null ? optionThree.hashCode() : 0);
        result = 31 * result + (optionFour != null ? optionFour.hashCode() : 0);
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        result = 31 * result + (explanation != null ? explanation.hashCode() : 0);
        return result;
    }
}
