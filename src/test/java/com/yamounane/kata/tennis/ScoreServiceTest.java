package com.yamounane.kata.tennis;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.yamounane.kata.tennis.model.Player;
import com.yamounane.kata.tennis.model.TennisParty;
import com.yamounane.kata.tennis.service.ScoreService;
import com.yamounane.kata.tennis.service.ScoreServiceImpl;

/**
 * Test class for {@link ScoreService}
 *
 * @author Yassine Amounane
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class ScoreServiceTest {
	
	private ScoreService scoreService;
	private TennisParty rollandGarrosFinal;
	private Player nadal; 
	private Player federer; 
	
	@Before
	public void setUp() {
		scoreService = new ScoreServiceImpl();
		nadal = new Player("Rafael Nadal");
		federer = new Player("Roger Federer");
		rollandGarrosFinal = new TennisParty(nadal, federer);
	}
	
	@Test
	public void should_score_increase_when_player_score_for_party() {
		
	}

}
