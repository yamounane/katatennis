package com.yamounane.kata.tennis.model;

import java.util.ArrayList;

import com.yamounane.kata.tennis.model.point.GamePoint;
import com.yamounane.kata.tennis.model.point.SetPoint;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Player Class
 *
 * @author Yassine Amounane
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = "game")
@ToString
public class Player {

	private String name;

	private GamePoint game;

	private boolean winner;

	private ArrayList<SetPoint> sets;

	public Player(String name) {
		this.name = name;
		this.game = new GamePoint();
		this.sets = new ArrayList<>();
		sets.add(new SetPoint());
		this.winner = false;
	}

	public void winTheGame() {
		this.game.setScore(0);
		sets.stream().filter(s -> s.isCurrent()).findFirst().get().score();
	}

	public void endTheSet(boolean win) {
		sets.stream().filter(s -> s.isCurrent()).findFirst().get().finish(win);
		sets.add(new SetPoint());
	}

	public void looseTheGame() {
		this.game.setScore(0);
	}

	public void wins() {
		this.winner = true;
		this.sets.remove(sets.size() - 1);
	}

	public void looses() {
		this.sets.remove(sets.size() - 1);
	}
}
