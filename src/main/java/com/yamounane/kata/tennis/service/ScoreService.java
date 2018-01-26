package com.yamounane.kata.tennis.service;

import com.yamounane.kata.tennis.exception.ScoreException;
import com.yamounane.kata.tennis.model.Player;
import com.yamounane.kata.tennis.model.TennisParty;

/**
 * Score Service
 * 
 * @author Yassine Amounane
 */
public interface ScoreService {

	void score(TennisParty party, Player player) throws ScoreException;

	String getScoreFor(TennisParty party) throws ScoreException;

}
