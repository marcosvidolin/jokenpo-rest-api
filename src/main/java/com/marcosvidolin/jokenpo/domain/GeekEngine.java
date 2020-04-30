package com.marcosvidolin.jokenpo.domain;

import com.marcosvidolin.jokenpo.domain.exception.BusinessException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GeekEngine implements GameEngine {

    private Map<String, Move> moves = new HashMap<>();
    private Map<Item, List<Item>> itemHierarchy = new HashMap<>();

    public GeekEngine() {
        this.buildEngine();
    }

    private void buildEngine() {
        this.itemHierarchy.put(Item.SPOCK, Arrays.asList(Item.SCISSORS, Item.ROCK));
        this.itemHierarchy.put(Item.SCISSORS, Arrays.asList(Item.PAPER, Item.LIZARD));
        this.itemHierarchy.put(Item.PAPER, Arrays.asList(Item.ROCK, Item.SPOCK));
        this.itemHierarchy.put(Item.ROCK, Arrays.asList(Item.SCISSORS, Item.LIZARD));
        this.itemHierarchy.put(Item.LIZARD, Arrays.asList(Item.SPOCK, Item.PAPER));
    }

    private boolean isWinner(Move move) throws BusinessException {
        Map<String, Move> movesCopy = new HashMap<>(this.moves);
        movesCopy.remove(move.getPlayerUsername());

        Item currentItem = Item.getItemByNameIgnoreCase(move.getItemValue());

        for (Move m : movesCopy.values()) {
            Item opponentItem = Item.getItemByNameIgnoreCase(m.getItemValue());
            List<Item> opponentWinsItems = this.itemHierarchy.get(opponentItem);
            boolean isTied = move.getItemValue().equalsIgnoreCase(opponentItem.name());
            if (opponentWinsItems.contains(currentItem) || isTied) {
                return false;
            }
        }
        return true;
    }

    /**
     * Add all moves.
     *
     * @param moves
     */
    @Override
    public void addMoves(List<Move> moves) {
        this.moves = moves.stream().collect(
                Collectors.toMap(x -> x.getPlayerUsername(), x -> x));
    }

    /**
     * Get the winner.
     *
     * @return Player
     */
    @Override
    public Move getWinnerMove() throws BusinessException {
        for (Move move : this.moves.values()) {
            if (this.isWinner(move)) {
                return move;
            }
        }
        return null;
    }

}
