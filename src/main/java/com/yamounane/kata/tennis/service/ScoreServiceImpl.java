package com.yamounane.kata.tennis.service;

import com.yamounane.kata.tennis.exception.ScoreException;
import com.yamounane.kata.tennis.model.Player;
import com.yamounane.kata.tennis.model.TennisParty;

public class ScoreServiceImpl implements ScoreService {

	@Override
	public void score(TennisParty party, Player player) throws ScoreException {
		if (party == null || player == null || party.isFinished() || !party.isRegistered(player)) {
			throw new ScoreException(String.format(
					"Unable to score player %s for party %s : party is over or player/party are null or player is not registered for this party",
					player, party));
		}

		if (!player.getGame().score()) {
			Player scorer = party.getPlayerFrom(player, true);
			Player second = party.getPlayerFrom(player, false);

			if (!isCurrentGameIsDeuce(party)) {
				scorer.winTheGame();
				second.looseTheGame();
				if (isSetIsWonByScorer(scorer, second)) {
					scorer.endTheSet(true);
					second.endTheSet(false);
					if (partyIsWon(scorer, second)) {
						party.setFinished(true);
						scorer.wins();
						second.looses();
					}
				}
			} else {
				scorer.getGame().advantage();
			}
		}
	}

	private boolean partyIsWon(Player scorer, Player second) {
		long winningScorerSet = scorer.getSets().stream().filter(s -> s.isWon()).count();

		if (winningScorerSet >= 2) {
			return true;
		}
		return false;
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
		if (party == null) {
			throw new ScoreException("Unable to get Score for party : given party is null!");
		}

		String score = party.getPlayerOne().getName() + "/" + party.getPlayerTwo().getName() + " : "
				+ formatGameScore(party.getPlayerOne().getGame().getScore()) + "-"
				+ formatGameScore(party.getPlayerTwo().getGame().getScore());

		for (int i = 0; i < party.getPlayerOne().getSets().size() && i < party.getPlayerTwo().getSets().size(); i++) {
			score += " | " + party.getPlayerOne().getSets().get(i).getScore() + "/"
					+ party.getPlayerTwo().getSets().get(i).getScore();
		}

		if (party.isFinished()) {
			Player winner = party.getPlayerOne().isWinner() ? party.getPlayerOne() : party.getPlayerTwo();
			score += " - Winner is : " + winner.getName();
		}

		return score;
	}

	private String formatGameScore(int score) {
		switch (score) {
		case 1:
			return "15";
		case 2:
			return "30";
		case 3:
			return "40";
		case 4:
			return "40(A)";
		default:
			return "0";
		}
	}

}
