package com.yamounane.kata.tennis;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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

}
