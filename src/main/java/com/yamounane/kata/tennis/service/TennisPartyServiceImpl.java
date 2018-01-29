package com.yamounane.kata.tennis.service;

import com.yamounane.kata.tennis.TennisPartyService;
import com.yamounane.kata.tennis.exception.ScoreException;
import com.yamounane.kata.tennis.exception.TennisPartyException;
import com.yamounane.kata.tennis.model.Player;
import com.yamounane.kata.tennis.model.TennisParty;

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
		if (party == null) {
			throw new TennisPartyException("Unable to get scoring for given party : party is null !");
		}
		try {
			return scoreService.getScoreFor(party);
		} catch (ScoreException se) {
			throw new TennisPartyException(se.getMessage());
		}
	}

	@Override
	public TennisParty playerScoresFor(TennisParty party, Player scorer) throws TennisPartyException {
		if (party == null || scorer == null || party.isFinished() || !party.isRegistered(scorer)) {
			throw new TennisPartyException(
					String.format("Unable to scores : Party %s or Player are %s invalid ! ", party, scorer));
		}

		try {
			return scoreService.score(party, scorer);
		} catch (ScoreException se) {
			throw new TennisPartyException(se.getMessage());
		}
	}

}
