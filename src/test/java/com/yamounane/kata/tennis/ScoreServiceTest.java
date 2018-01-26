package com.yamounane.kata.tennis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.yamounane.kata.tennis.exception.ScoreException;
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
	public void should_score_increase_when_player_score_for_party() throws ScoreException {
		scoreService.score(rollandGarrosFinal, nadal);
		
		assertThat(scoreService.getScoreFor(rollandGarrosFinal)).isEqualTo("Rafael Nadal | 15 | 0\n" + 
																			"Roger Federer | 0 | 0");
	}
	
	@Test
	public void should_raise_exception_when_scoring_for_null_player() throws ScoreException {
		assertThatThrownBy(() -> scoreService.score(rollandGarrosFinal, null)).isInstanceOf(ScoreException.class);
	}
	
	@Test
	public void should_raise_exception_when_scoring_for_null_party() throws ScoreException {
		assertThatThrownBy(() -> scoreService.score(null, federer)).isInstanceOf(ScoreException.class);
	}

	@Test
	public void should_raise_exception_when_scoring_for_null_party_and_null_player() throws ScoreException {
		assertThatThrownBy(() -> scoreService.score(null, null)).isInstanceOf(ScoreException.class);
	}
}
