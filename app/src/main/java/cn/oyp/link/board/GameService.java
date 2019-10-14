package cn.oyp.link.board;

import cn.oyp.link.utils.LinkInfo;
import cn.oyp.link.view.Piece;

public interface GameService {
    void start();

    Piece[][] getPieces();

    boolean hasPieces();

    Piece findPiece(float touchX, float touchY);

    LinkInfo link(Piece p1, Piece p2);
}
