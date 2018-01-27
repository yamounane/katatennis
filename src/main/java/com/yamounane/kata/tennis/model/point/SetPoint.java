package com.yamounane.kata.tennis.model.point;

import lombok.Getter;

/**
 * Set Model Point
 *
 * @author Yassine Amounane
 */
@Getter
public class SetPoint extends Point {

	private boolean current;

	public SetPoint(int score) {
		super(score);
		current = true;
	}

	public void finish() {
		current = false;
	}

	public void score() {
		score++;
	}
}
