package com.yamounane.kata.tennis.model;

import com.yamounane.kata.tennis.exception.ScoreException;

import lombok.Getter;
import lombok.Setter;

/**
 * Tennis Party Class
 *
 * @author Yassine Amounane
 */
@Getter
@Setter
public class TennisParty {

	private Player playerOne;

	private Player playerTwo;

	private boolean finished;

	public TennisParty(Player playerOne, Player playerTwo) {
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		this.finished = false;
	}

	public boolean isRegistered(Player player) {
		if (this.playerOne.equals(player) || this.playerTwo.equals(player)) {
			return true;
		}
		return false;
	}

	public Player getPlayerFrom(Player player, boolean isThisPlayer) throws ScoreException {
		if (player == null || !isRegistered(player)) {
			throw new ScoreException(
					String.format("Unable to get player %s : player is null or unrelated to this party", player));
		}

		if (isThisPlayer && playerOne.equals(player) || !isThisPlayer && !playerOne.equals(player)) {
			return playerOne;
		}
		return playerTwo;
	}

}
