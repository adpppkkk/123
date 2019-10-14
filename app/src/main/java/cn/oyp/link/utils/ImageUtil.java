package cn.oyp.link.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import cn.oyp.link.R;
import cn.oyp.link.view.PieceImage;

public class ImageUtil {
    private static List<Integer> imageValues = getImageValues();

    public static List<Integer> getImageValues() {
        try {
            Field[] drawableFields = R.drawable.class.getFields();
            List<Integer> resourceValues = new ArrayList<Integer>();
            for (Field field : drawableFields) {
                if (field.getName().indexOf("cell") != -1) {
                    resourceValues.add(field.getInt(R.drawable.class));
                }
            }
            return resourceValues;
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Integer> getRandomValues(List<Integer> sourceValues,
                                                int size) {
        Random random = new Random();
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < size; i++) {
            try {
                int index = random.nextInt(sourceValues.size());
                Integer image = sourceValues.get(index);
                result.add(image);
            } catch (IndexOutOfBoundsException e) {
                return result;
            }
        }
        return result;
    }

    public static List<Integer> getPlayValues(int size) {
        if (size % 2 != 0) {
            size += 1;
        }
        List<Integer> playImageValues = getRandomValues(imageValues, size / 2);
        playImageValues.addAll(playImageValues);
        Collections.shuffle(playImageValues);
        return playImageValues;
    }

    public static List<PieceImage> getPlayImages(Context context, int size) {
        List<Integer> resourceValues = getPlayValues(size);
        List<PieceImage> result = new ArrayList<PieceImage>();
        for (Integer value : resourceValues) {
            Bitmap bm = drawableToBitmap(context.getResources().getDrawable(value));
            PieceImage pieceImage = new PieceImage(bm, value);
            result.add(pieceImage);
        }
        return result;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
//        Bitmap bitmap = Bitmap.createBitmap(
//                drawable.getIntrinsicWidth(),
//                drawable.getIntrinsicHeight(),
//                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
//                        : Bitmap.Config.RGB_565);
//        Canvas canvas = new Canvas(bitmap);
//        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
//                drawable.getIntrinsicHeight());

        Bitmap bitmap = Bitmap.createBitmap(GameConf.PIECE_WIDTH, GameConf.PIECE_HEIGHT, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, GameConf.PIECE_WIDTH, GameConf.PIECE_HEIGHT);
        drawable.draw(canvas);
        return bitmap;
    }


    public static Bitmap getSelectImage(Context context) {
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.selected);
    }
}
