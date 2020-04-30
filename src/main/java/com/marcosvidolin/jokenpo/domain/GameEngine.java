package com.marcosvidolin.jokenpo.domain;

import com.marcosvidolin.jokenpo.domain.exception.BusinessException;

import java.util.List;

public interface GameEngine {
    void addMoves(List<Move> moves);
    Move getWinnerMove() throws BusinessException;
}
