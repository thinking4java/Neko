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

@ExtendWith(MockitoExtension.class)
class MatchServiceImplTest {

    @Mock
    private LeagueRepository leagueRepository;

    @InjectMocks
    private MatchServiceImpl matchService;

    League league = new League(33, "PrimeLeague", true);

    @BeforeEach
    void setup() {
        league.setMatchList(Arrays.asList(new Match(10000L, league, MatchState.FINISHED)));
        Mockito.when(leagueRepository.save(Mockito.any())).thenReturn(league);
    }

    @Test
    void testSaveLeagueWithMatchList() {
        League savedLeague = matchService.save(league);
        Assertions.assertNotNull(savedLeague);
        Assertions.assertTrue(savedLeague.isTopLevel());
        Assertions.assertNotNull(savedLeague.getMatchList());
        Assertions.assertEquals(1, savedLeague.getMatchList().size());
        Assertions.assertEquals(10000L, savedLeague.getMatchList().get(0).getId());
        Assertions.assertEquals(MatchState.FINISHED, savedLeague.getMatchList().get(0).getState());
    }

}