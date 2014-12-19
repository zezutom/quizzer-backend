package org.zezutom.quizzer;

import org.zezutom.quizzer.model.GameResult;

public class GameResultBuilder {

    private int firstAttemptPoints = 5;

    private int secondAttemptPoints = 3;

    private int defaultPoints = 1;

    private int round;

    private int oneTimeAttempts;

    private int twoTimeAttempts;

    private int oneTimeConsecutiveAttempts;

    private String email;

    /**
     * The number of first-time consecutive attempts needed to earn a power-up
     */
    private int powerUpAttempts = 2;

    private GameResult gameResult = new GameResult();

    public GameResultBuilder setRound(int round) {
        this.round = round;
        return this;
    }

    public GameResultBuilder setOneTimeAttempts(int oneTimeAttempts) {
        this.oneTimeAttempts = oneTimeAttempts;
        return this;
    }

    public GameResultBuilder setTwoTimeAttempts(int twoTimeAttempts) {
        this.twoTimeAttempts = twoTimeAttempts;
        return this;
    }

    public GameResultBuilder setOneTimeConsecutiveAttempts(int oneTimeConsecutiveAttempts) {
        this.oneTimeConsecutiveAttempts = oneTimeConsecutiveAttempts;
        return this;
    }

    public GameResultBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public GameResult build() {

        if (oneTimeAttempts + twoTimeAttempts > round) {
            throw new IllegalStateException("The total number of attempts exceeds the achieved round!");
        }

        if (oneTimeConsecutiveAttempts > oneTimeAttempts) {
            throw new IllegalStateException("The number of consecutive successful attempts exceeds the total number of first time attempts!");
        }

        gameResult.setEmail(email);
        gameResult.setRound(round);
        gameResult.setScore(firstAttemptPoints * oneTimeAttempts    +
                            secondAttemptPoints * twoTimeAttempts   +
                            defaultPoints * (round - oneTimeAttempts - twoTimeAttempts));
        gameResult.setPowerUps(oneTimeConsecutiveAttempts / powerUpAttempts);
        gameResult.setAttemptOneRatio(getRatio(gameResult.getRound(), oneTimeAttempts));
        gameResult.setAttemptTwoRatio(getRatio(gameResult.getRound(), twoTimeAttempts));
        gameResult.setAttemptThreeRatio(getRatio(gameResult.getRound(), round - oneTimeAttempts - twoTimeAttempts));
        return gameResult;
    }

    private int getRatio(int round, int attempts) {
        return (int) ((attempts * 100.0f) / round);
    }
}
