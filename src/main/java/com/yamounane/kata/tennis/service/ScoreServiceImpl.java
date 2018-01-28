package com.yamounane.kata.tennis.service;

import com.yamounane.kata.tennis.exception.ScoreException;
import com.yamounane.kata.tennis.model.Player;
import com.yamounane.kata.tennis.model.TennisParty;

public class ScoreServiceImpl implements ScoreService {

	@Override
	public void score(TennisParty party, Player player) throws ScoreException {
		if (party == null || player == null || !party.isRegistered(player)) {
			throw new ScoreException(String.format(
					"Unable to score player %s for party %s : player or party are null or player is not registered for this party",
					player, party));
		}

		if (!player.getGame().score()) {
			Player scorer = party.getPlayerFrom(player, true);
			Player second = party.getPlayerFrom(player, false);

			if (!isCurrentGameIsDeuce(party)) {
				scorer.winTheGame();
				second.looseTheGame();
				if (isSetIsWonByScorer(scorer, second)) {
					scorer.endTheSet();
					second.endTheSet();
				}
			} else {
				scorer.getGame().advantage();
			}
		}
	}

	private boolean isCurrentGameIsDeuce(TennisParty party) {
		if (party.getPlayerOne().getGame().getScore() == 3 && party.getPlayerTwo().getGame().getScore() == 3) {
			return true;
		}
		return false;
	}

	private boolean isSetIsWonByScorer(Player scorer, Player second) {
		int scorerSet = scorer.getSets().stream().filter(s -> s.isCurrent()).findFirst().get().getScore();
		int secondSet = second.getSets().stream().filter(s -> s.isCurrent()).findFirst().get().getScore();

		if (secondSet < 5 && scorerSet > 5 || secondSet <= 6 && scorerSet > 6) {
			return true;
		}

		return false;
	}

	@Override
	public String getScoreFor(TennisParty party) throws ScoreException {
		return null;
	}

}
