package com.yamounane.kata.tennis;

import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.fail;

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
	public void should_advantage_game_score_increase_when_player_win_the_game_for_party() throws ScoreException {
		scoreService.score(rollandGarrosFinal, federer);
		scoreService.score(rollandGarrosFinal, nadal);
		scoreService.score(rollandGarrosFinal, federer);
		scoreService.score(rollandGarrosFinal, nadal);
		scoreService.score(rollandGarrosFinal, federer);
		scoreService.score(rollandGarrosFinal, nadal);
		scoreService.score(rollandGarrosFinal, federer);

		assertThat(federer.getSets().get(0).getScore()).isEqualTo(0);
		assertThat(federer.getGame().getScore()).isEqualTo(4);
		assertThat(nadal.getSets().get(0).getScore()).isEqualTo(0);
		assertThat(nadal.getGame().getScore()).isEqualTo(3);
	}

	@Test
	public void should_set_score_increase_when_player_win_the_game_with_advantage_for_party() throws ScoreException {
		scoreService.score(rollandGarrosFinal, federer);
		scoreService.score(rollandGarrosFinal, nadal);
		scoreService.score(rollandGarrosFinal, federer);
		scoreService.score(rollandGarrosFinal, nadal);
		scoreService.score(rollandGarrosFinal, federer);
		scoreService.score(rollandGarrosFinal, nadal);
		scoreService.score(rollandGarrosFinal, federer);
		scoreService.score(rollandGarrosFinal, federer);

		assertThat(federer.getSets().get(0).getScore()).isEqualTo(1);
		assertThat(federer.getGame().getScore()).isEqualTo(0);
		assertThat(nadal.getSets().get(0).getScore()).isEqualTo(0);
		assertThat(nadal.getGame().getScore()).isEqualTo(0);
	}

	@Test
	public void should_player_win_the_set_when_player_win_24_games() throws ScoreException {
		repeat(24, () -> {
			try {
				scoreService.score(rollandGarrosFinal, federer);
			} catch (ScoreException e) {
				fail();
			}
		});

		assertThat(nadal.getGame().getScore()).isEqualTo(0);
		assertThat(nadal.getSets().get(0).getScore()).isEqualTo(0);
		assertThat(nadal.getSets().get(1).getScore()).isEqualTo(0);
		assertThat(nadal.getSets().get(0).isCurrent()).isEqualTo(false);
		assertThat(nadal.getSets().get(1).isCurrent()).isEqualTo(true);
		assertThat(federer.getGame().getScore()).isEqualTo(0);
		assertThat(federer.getSets().get(0).getScore()).isEqualTo(6);
		assertThat(federer.getSets().get(1).getScore()).isEqualTo(0);
		assertThat(federer.getSets().get(0).isCurrent()).isEqualTo(false);
		assertThat(federer.getSets().get(1).isCurrent()).isEqualTo(true);
	}

	@Test
	public void should_player_win_the_set_when_player_win_6_against_4_games() throws ScoreException {
		repeat(16, () -> {
			try {
				scoreService.score(rollandGarrosFinal, federer);
			} catch (ScoreException e) {
				fail();
			}
		});
		repeat(24, () -> {
			try {
				scoreService.score(rollandGarrosFinal, nadal);
			} catch (ScoreException e) {
				fail();
			}
		});

		assertThat(nadal.getGame().getScore()).isEqualTo(0);
		assertThat(nadal.getSets().get(0).getScore()).isEqualTo(6);
		assertThat(nadal.getSets().get(1).getScore()).isEqualTo(0);
		assertThat(nadal.getSets().get(0).isCurrent()).isEqualTo(false);
		assertThat(nadal.getSets().get(1).isCurrent()).isEqualTo(true);
		assertThat(federer.getGame().getScore()).isEqualTo(0);
		assertThat(federer.getSets().get(0).getScore()).isEqualTo(4);
		assertThat(federer.getSets().get(1).getScore()).isEqualTo(0);
		assertThat(federer.getSets().get(0).isCurrent()).isEqualTo(false);
		assertThat(federer.getSets().get(1).isCurrent()).isEqualTo(true);
	}

	@Test
	public void should_player_win_the_set_when_player_win_7_against_5_games() throws ScoreException {
		repeat(20, () -> {
			try {
				scoreService.score(rollandGarrosFinal, federer);
			} catch (ScoreException e) {
				fail();
			}
		});
		repeat(20, () -> {
			try {
				scoreService.score(rollandGarrosFinal, nadal);
			} catch (ScoreException e) {
				fail();
			}
		});
		repeat(8, () -> {
			try {
				scoreService.score(rollandGarrosFinal, federer);
			} catch (ScoreException e) {
				fail();
			}
		});

		assertThat(nadal.getGame().getScore()).isEqualTo(0);
		assertThat(nadal.getSets().get(0).getScore()).isEqualTo(5);
		assertThat(nadal.getSets().get(1).getScore()).isEqualTo(0);
		assertThat(nadal.getSets().get(0).isCurrent()).isEqualTo(false);
		assertThat(nadal.getSets().get(1).isCurrent()).isEqualTo(true);
		assertThat(federer.getGame().getScore()).isEqualTo(0);
		assertThat(federer.getSets().get(0).getScore()).isEqualTo(7);
		assertThat(federer.getSets().get(1).getScore()).isEqualTo(0);
		assertThat(federer.getSets().get(0).isCurrent()).isEqualTo(false);
		assertThat(federer.getSets().get(1).isCurrent()).isEqualTo(true);
	}

	@Test
	public void should_player_win_the_set_when_player_win_7_against_6_games() throws ScoreException {
		repeat(20, () -> {
			try {
				scoreService.score(rollandGarrosFinal, federer);
			} catch (ScoreException e) {
				fail();
			}
		});
		repeat(24, () -> {
			try {
				scoreService.score(rollandGarrosFinal, nadal);
			} catch (ScoreException e) {
				fail();
			}
		});
		repeat(8, () -> {
			try {
				scoreService.score(rollandGarrosFinal, federer);
			} catch (ScoreException e) {
				fail();
			}
		});

		assertThat(nadal.getGame().getScore()).isEqualTo(0);
		assertThat(nadal.getSets().get(0).getScore()).isEqualTo(6);
		assertThat(nadal.getSets().get(1).getScore()).isEqualTo(0);
		assertThat(nadal.getSets().get(0).isCurrent()).isEqualTo(false);
		assertThat(nadal.getSets().get(1).isCurrent()).isEqualTo(true);
		assertThat(federer.getGame().getScore()).isEqualTo(0);
		assertThat(federer.getSets().get(0).getScore()).isEqualTo(7);
		assertThat(federer.getSets().get(1).getScore()).isEqualTo(0);
		assertThat(federer.getSets().get(0).isCurrent()).isEqualTo(false);
		assertThat(federer.getSets().get(1).isCurrent()).isEqualTo(true);
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

	private void repeat(int times, Runnable runnable) {
		range(0, times).forEach(i -> runnable.run());
	}
}
