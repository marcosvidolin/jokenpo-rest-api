package com.marcosvidolin.jokenpo.service;

import com.marcosvidolin.jokenpo.domain.*;
import com.marcosvidolin.jokenpo.domain.exception.BusinessException;
import com.marcosvidolin.jokenpo.domain.exception.ModelNotFoundException;
import com.marcosvidolin.jokenpo.repository.GameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.BDDMockito.given;

@SpringBootTest
public class GameServceTest {

    @MockBean
    private GameRepository repository;

    @Autowired
    private GameService service;

    @Test
    public void makeMove_whenMoreMovesThanPlayers_mustThrowBusinessException()
            throws ModelNotFoundException, BusinessException {

        UUID gameCode = UUID.randomUUID();
        Game game = new Game(new GeekEngine());
        Player player =new Player("marcos");
        game.addPlayers(Arrays.asList(player));

        given(this.repository.findByCode(gameCode)).willReturn(game);

        Move move1 = new Move(gameCode, player.getUsername(), Item.ROCK.name());
        this.service.makeMove(move1);

        Move move2 = new Move(gameCode, player.getUsername(), Item.ROCK.name());
        Assertions.assertThrows(BusinessException.class, () -> {
            this.service.makeMove(move2);
        });
    }

    @Test
    public void makeMove_withValidMove_mustReturnTheMove()
            throws ModelNotFoundException, BusinessException {

        UUID gameCode = UUID.randomUUID();
        Game game = new Game(new GeekEngine());
        Player player =new Player("marcos");
        game.addPlayers(Arrays.asList(player));

        given(this.repository.findByCode(gameCode)).willReturn(game);

        Move move = new Move(gameCode, player.getUsername(), Item.ROCK.name());
        Move moved = this.service.makeMove(move);

        Assertions.assertNotNull(moved);
        Assertions.assertEquals(moved.getGameCode(), gameCode);
        Assertions.assertEquals(moved.getPlayerUsername(), player.getUsername());
        Assertions.assertEquals(moved.getItemValue(), Item.ROCK.name());
    }

    @Test
    public void getWinner_withNoWinner_mustThrowBusinessException()
            throws BusinessException {

        UUID gameCode = UUID.randomUUID();
        Game game = new Game(new GeekEngine());
        Player marcos =new Player("marcos");
        Player joao =new Player("joao");
        game.addPlayers(Arrays.asList(marcos, joao));

        game.addMove(new Move(gameCode, marcos.getUsername(), Item.ROCK.name()));
        game.addMove(new Move(gameCode, joao.getUsername(), Item.ROCK.name()));

        given(this.repository.findByCode(gameCode)).willReturn(game);

        Assertions.assertThrows(BusinessException.class, () -> {
            this.service.getWinner(gameCode);
        });

    }

    @Test
    public void getWinner_withWinner_mustReturnTheWinner()
            throws ModelNotFoundException, BusinessException {

        UUID gameCode = UUID.randomUUID();
        Game game = new Game(new GeekEngine());
        Player marcos =new Player("marcos");
        Player loser =new Player("loser");
        game.addPlayers(Arrays.asList(marcos, loser));

        game.addMove(new Move(gameCode, marcos.getUsername(), Item.PAPER.name()));
        game.addMove(new Move(gameCode, loser.getUsername(), Item.ROCK.name()));

        given(this.repository.findByCode(gameCode)).willReturn(game);

        Player winner = this.service.getWinner(gameCode);

        Assertions.assertEquals(winner.getUsername(), marcos.getUsername());
    }

}
