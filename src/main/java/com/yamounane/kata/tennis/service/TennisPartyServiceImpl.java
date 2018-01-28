package com.yamounane.kata.tennis.service;

import com.yamounane.kata.tennis.TennisPartyService;
import com.yamounane.kata.tennis.exception.TennisPartyException;
import com.yamounane.kata.tennis.model.Player;
import com.yamounane.kata.tennis.model.TennisParty;

/**
 *
 *
 * @author Yassine Amounane
 */
public class TennisPartyServiceImpl implements TennisPartyService {

	private ScoreService scoreService;

	public TennisPartyServiceImpl(ScoreService scoreService) {
		this.scoreService = scoreService;
	}

	@Override
	public TennisParty beginParty(Player one, Player two) throws TennisPartyException {
		if (one == null || two == null) {
			throw new TennisPartyException("Unable to begin party : players are invalid : " + one + ", " + two);
		}
		return new TennisParty(one, two);
	}

	@Override
	public String getScoreFor(TennisParty party) throws TennisPartyException {
		return null;
	}

	@Override
	public void playerScoresFor(TennisParty party, Player scorer) throws TennisPartyException {

	}

}
