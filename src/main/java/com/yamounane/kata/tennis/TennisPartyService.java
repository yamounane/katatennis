package com.yamounane.kata.tennis;

import com.yamounane.kata.tennis.exception.TennisPartyException;
import com.yamounane.kata.tennis.model.Player;
import com.yamounane.kata.tennis.model.TennisParty;

/**
 * Tennis Party Service
 * 
 * @author Yassine Amounane
 */
public interface TennisPartyService {

	TennisParty beginParty(Player one, Player two) throws TennisPartyException;

	String getScoreFor(TennisParty party) throws TennisPartyException;

	TennisParty playerScoresFor(TennisParty party, Player scorer) throws TennisPartyException;

}
