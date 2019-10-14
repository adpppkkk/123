package cn.oyp.link.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import cn.oyp.link.board.GameService;
import cn.oyp.link.utils.ImageUtil;
import cn.oyp.link.utils.LinkInfo;

public class GameView extends View {
    private GameService gameService;
    private Piece selectedPiece;
    private LinkInfo linkInfo;
    private Paint paint;
    private Bitmap selectImage;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.paint = new Paint();
        // 设置连接线的颜色
        this.paint.setColor(Color.RED);
        // 设置连接线的粗细
        this.paint.setStrokeWidth(3);
        // 初始化被选中的图片
        this.selectImage = ImageUtil.getSelectImage(context);
    }

    public void setLinkInfo(LinkInfo linkInfo) {
        this.linkInfo = linkInfo;
    }

    public void setSelectedPiece(Piece piece) {
        this.selectedPiece = piece;
    }

    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.gameService == null) {
            return;
        }
        Piece[][] pieces = gameService.getPieces();
        if (pieces != null) {
            for (int i = 0; i < pieces.length; i++) {
                for (int j = 0; j < pieces[i].length; j++) {
                    if (pieces[i][j] != null) {
                        Piece piece = pieces[i][j];
                        if (piece.getPieceImage() != null) {
                            canvas.drawBitmap(piece.getPieceImage().getImage(), piece.getBeginX(), piece.getBeginY(), null);
                        }
                    }
                }
            }
        }
        if (this.linkInfo != null) {
            drawLine(this.linkInfo, canvas);
            this.linkInfo = null;
        }
        if (this.selectedPiece != null) {
            canvas.drawBitmap(this.selectImage, this.selectedPiece.getBeginX(),
                    this.selectedPiece.getBeginY(), null);
        }
    }

    private void drawLine(LinkInfo linkInfo, Canvas canvas) {
        List<Point> points = linkInfo.getLinkPoints();
        for (int i = 0; i < points.size() - 1; i++) {
            Point currentPoint = points.get(i);
            Point nextPoint = points.get(i + 1);
            canvas.drawLine(currentPoint.x, currentPoint.y, nextPoint.x,
                    nextPoint.y, this.paint);
        }
    }

    public void startGame() {
        this.gameService.start();
        this.postInvalidate();
    }
}
