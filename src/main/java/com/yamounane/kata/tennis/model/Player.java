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

	private ArrayList<SetPoint> sets;

	public Player(String name) {
		this.name = name;
		this.game = new GamePoint();
		this.sets = new ArrayList<>();
	}

	public void winTheSet() {
		this.game = new GamePoint();
		sets.stream().filter(s -> s.isCurrent()).findFirst().get().score();
	}

	public void looseTheSet() {
		this.game = new GamePoint();
	}

}
