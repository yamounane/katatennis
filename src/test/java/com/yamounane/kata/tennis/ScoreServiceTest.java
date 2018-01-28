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
	private Player monfils;

	@Before
	public void setUp() {
		scoreService = new ScoreServiceImpl();

		nadal = new Player("Rafael Nadal");
		federer = new Player("Roger Federer");
		monfils = new Player("Gael Monfils");

		rollandGarrosFinal = new TennisParty(nadal, federer);
	}

	@Test
	public void should_current_game_score_increase_when_player_score_for_party() throws ScoreException {
		scoreService.score(rollandGarrosFinal, nadal);

		assertThat(nadal.getGame().getScore()).isEqualTo(1);
		assertThat(federer.getGame().getScore()).isEqualTo(0);
	}

	@Test
	public void should_set_score_increase_when_player_win_the_game_for_party() throws ScoreException {
		scoreService.score(rollandGarrosFinal, federer);
		scoreService.score(rollandGarrosFinal, nadal);
		scoreService.score(rollandGarrosFinal, nadal);
		scoreService.score(rollandGarrosFinal, nadal);
		scoreService.score(rollandGarrosFinal, nadal);
		scoreService.score(rollandGarrosFinal, nadal);

		assertThat(nadal.getSets().get(0).getScore()).isEqualTo(1);
		assertThat(nadal.getGame().getScore()).isEqualTo(1);
		assertThat(federer.getSets().get(0).getScore()).isEqualTo(0);
		assertThat(federer.getGame().getScore()).isEqualTo(0);
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

	@Test
	public void should_raise_exception_when_scoring_for_a_non_registered_player_for_party() throws ScoreException {
		assertThatThrownBy(() -> scoreService.score(rollandGarrosFinal, monfils)).isInstanceOf(ScoreException.class);
	}
}
