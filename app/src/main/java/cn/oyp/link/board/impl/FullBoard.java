package cn.oyp.link.board.impl;

import java.util.ArrayList;
import java.util.List;

import cn.oyp.link.board.AbstractBoard;
import cn.oyp.link.utils.GameConf;
import cn.oyp.link.view.Piece;

public class FullBoard extends AbstractBoard {
    @Override
    protected List<Piece> createPieces(GameConf config, Piece[][] pieces) {
        List<Piece> notNullPieces = new ArrayList<Piece>();
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                Piece piece = new Piece(i, j);
                notNullPieces.add(piece);
            }
        }
        return notNullPieces;
    }
}
