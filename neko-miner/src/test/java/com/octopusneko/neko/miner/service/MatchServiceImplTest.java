package com.octopusneko.neko.miner.service;

import com.octopusneko.neko.miner.model.League;
import com.octopusneko.neko.miner.model.Match;
import com.octopusneko.neko.miner.model.MatchState;
import com.octopusneko.neko.miner.repository.LeagueRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class MatchServiceImplTest {

    @Mock
    private LeagueRepository leagueRepository;

    @InjectMocks
    private MatchServiceImpl matchService;

    League league = new League(33, "PrimeLeague", true);

    @BeforeEach
    void setup() {
        league.setMatchList(Collections.singletonList(new Match(10000L, league, MatchState.FINISHED)));
        Mockito.when(leagueRepository.saveAll(Mockito.any())).thenReturn(Collections.singletonList(league));
    }

    @Test
    void testSaveLeagueWithMatchList() {
        List<League> savedLeagues = matchService.saveAll(Collections.singletonList(league));
        Assertions.assertNotNull(savedLeagues);
        Assertions.assertEquals(1, savedLeagues.size());

        League savedLeague = savedLeagues.get(0);
        Assertions.assertTrue(savedLeague.isTopLevel());
        Assertions.assertNotNull(savedLeague.getMatchList());
        Assertions.assertEquals(1, savedLeague.getMatchList().size());
        Assertions.assertEquals(10000L, savedLeague.getMatchList().get(0).getId());
        Assertions.assertEquals(MatchState.FINISHED, savedLeague.getMatchList().get(0).getState());
    }

}