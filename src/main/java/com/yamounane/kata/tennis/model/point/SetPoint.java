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

	private boolean won;

	public SetPoint() {
		super(0);
		current = true;
		won = false;
	}

	public void finish(boolean win) {
		current = false;
		won = win;
	}

	public void score() {
		score++;
	}

}
