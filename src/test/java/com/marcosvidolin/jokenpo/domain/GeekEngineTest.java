package com.marcosvidolin.jokenpo.domain;

import com.marcosvidolin.jokenpo.domain.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.UUID;

public class GeekEngineTest {

    @Test
    public void getWinner_withTwoPlayers_mustReturnWinner()
            throws BusinessException {

        Game geekGame = new Game(new GeekEngine());
        geekGame.addPlayers(Arrays.asList(
                new Player("marcos"),
                new Player("joao")));

        UUID fakeGameCode = UUID.randomUUID();
        geekGame.addMove(new Move(fakeGameCode, "marcos", Item.LIZARD.name()));
        geekGame.addMove(new Move(fakeGameCode, "joao", Item.ROCK.name()));

        Player winner = geekGame.getWinner();

        Assertions.assertEquals(winner.getUsername(), "joao");
    }

    @Test
    public void getWinner_whenNoWinner_mustThrowBusinessException()
            throws BusinessException {

        Game geekGame = new Game(new GeekEngine());
        geekGame.addPlayers(Arrays.asList(
                new Player("marcos"),
                new Player("joao")));

        UUID fakeGameCode = UUID.randomUUID();
        geekGame.addMove(new Move(fakeGameCode, "marcos", Item.LIZARD.name()));
        geekGame.addMove(new Move(fakeGameCode, "joao", Item.LIZARD.name()));

        Assertions.assertThrows(BusinessException.class, () -> {
            geekGame.getWinner();
        });

    }

}
