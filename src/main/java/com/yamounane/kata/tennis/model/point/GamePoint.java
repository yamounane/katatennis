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
		if (++super.score > 3) {
			return false;
		}
		return true;
	}

	public void advantage() {
		score = 4;
	}
}
