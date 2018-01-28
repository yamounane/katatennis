package com.yamounane.kata.tennis.model.point;

import lombok.Getter;

/**
 * Game Model Class
 *
 * @author Yassine Amounane
 */
@Getter

public class GamePoint extends Point {

	public GamePoint() {
		super(0);
	}

	public boolean score() {
		int newScore = score;

		if (++newScore > 3) {
			return false;
		}
		score = newScore;

		return true;
	}

	public void advantage() {
		score = 4;
	}
}
