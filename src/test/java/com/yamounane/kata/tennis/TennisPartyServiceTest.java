package com.yamounane.kata.tennis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.yamounane.kata.tennis.exception.ScoreException;
import com.yamounane.kata.tennis.exception.TennisPartyException;
import com.yamounane.kata.tennis.model.Player;
import com.yamounane.kata.tennis.model.TennisParty;
import com.yamounane.kata.tennis.service.ScoreService;
import com.yamounane.kata.tennis.service.TennisPartyServiceImpl;

/**
 * Test class for {@link TennisPartyService}
 *
 * @author Yassine Amounane
 */
@RunWith(MockitoJUnitRunner.class)
public class TennisPartyServiceTest {

	@Mock
	private ScoreService scoreServiceMock;
	@InjectMocks
	private TennisPartyService service = new TennisPartyServiceImpl(scoreServiceMock);

	private Player nadal;
	private Player federer;

	@Before
	public void setUp() {
		nadal = new Player("Rafael Nadal");
		federer = new Player("Roger Federer");
	}

	@Test
	public void should_begin_party_when_two_players_are_ready() throws TennisPartyException {
		TennisParty party = service.beginParty(nadal, federer);

		assertThat(party).isNotNull();
		assertThat(party.getPlayerOne()).isEqualTo(nadal);
		assertThat(party.getPlayerTwo()).isEqualTo(federer);
		assertThat(party.isFinished()).isEqualTo(false);
	}

	@Test
	public void should_raise_exception_for_begin_party_when_player_one_is_null() throws TennisPartyException {
		assertThatThrownBy(() -> service.beginParty(null, federer)).isInstanceOf(TennisPartyException.class);
	}

	@Test
	public void should_raise_exception_for_begin_party_when_player_two_is_null() throws TennisPartyException {
		assertThatThrownBy(() -> service.beginParty(nadal, null)).isInstanceOf(TennisPartyException.class);
	}

	@Test
	public void should_raise_exception_for_begin_party_when_players_are_null() throws TennisPartyException {
		assertThatThrownBy(() -> service.beginParty(null, null)).isInstanceOf(TennisPartyException.class);
	}

	@Test
	public void should_score_increase_when_player_score() throws TennisPartyException, ScoreException {
		TennisParty party = service.beginParty(nadal, federer);
		Player nadalScored = new Player("Rafael Nadal");
		nadalScored.getGame().score();
		Mockito.when(scoreServiceMock.score(party, party.getPlayerOne()))
				.thenReturn(new TennisParty(nadalScored, federer));

		party = service.playerScoresFor(party, party.getPlayerOne());

		assertThat(party.getPlayerOne().getGame().getScore()).isEqualTo(1);
	}

	@Test
	public void should_raise_exception_when_scoring_for_null_party() throws TennisPartyException, ScoreException {
		assertThatThrownBy(() -> service.playerScoresFor(null, nadal)).isInstanceOf(TennisPartyException.class);
	}

	@Test
	public void should_raise_exception_when_scoring_for_null_player() throws TennisPartyException, ScoreException {
		assertThatThrownBy(() -> service.playerScoresFor(new TennisParty(nadal, federer), null))
				.isInstanceOf(TennisPartyException.class);
	}

	@Test
	public void should_raise_exception_when_scoring_for_null_player_and_party()
			throws TennisPartyException, ScoreException {
		assertThatThrownBy(() -> service.playerScoresFor(null, null)).isInstanceOf(TennisPartyException.class);
	}

	@Test
	public void should_raise_exception_when_scoring_for_unregistered_player_and_party()
			throws TennisPartyException, ScoreException {
		assertThatThrownBy(() -> service.playerScoresFor(new TennisParty(nadal, federer), new Player("Gael Monfils")))
				.isInstanceOf(TennisPartyException.class);
	}

	@Test
	public void should_scoring_increase_when_player_score() throws TennisPartyException, ScoreException {
		Player nadalScored = new Player("Rafael Nadal");
		nadalScored.getGame().score();
		TennisParty party = service.beginParty(nadalScored, federer);
		String expectedScore = "Rafael Nadal/Roger Federer : 15-0 | 0/0";

		Mockito.when(scoreServiceMock.score(party, party.getPlayerOne()))
				.thenReturn(new TennisParty(nadalScored, federer));
		party = service.playerScoresFor(party, party.getPlayerOne());
		Mockito.when(scoreServiceMock.getScoreFor(party)).thenReturn(expectedScore);
		String score = service.getScoreFor(party);

		assertThat(score).isEqualTo(expectedScore);
	}

	@Test
	public void should_raise_exception_when_get_scoring_for_null_party() throws TennisPartyException {
		assertThatThrownBy(() -> service.getScoreFor(null)).isInstanceOf(TennisPartyException.class);
	}

}
