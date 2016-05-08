package com.example.zsystudio.puzzlehigh.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.example.zsystudio.puzzlehigh.R;
import com.example.zsystudio.puzzlehigh.util.IOUtil;

import java.io.InputStream;
import java.util.LinkedList;

/**
 * Created by Criss on 2016/5/6.
 */
class PuzzleView extends View {
    public static final int GAME_ON = 0;
    public static final int GAME_OVER = 1;

    private int mPiece; //how many pieces the image will be cut
    private Uri mImageUri;

    private int mGameStatus;
    private GameOverCallBack mCallBack; //callback interface handle game over

    private Bitmap a;

    public interface GameOverCallBack {
        void onGameOver();
    }

    public void setGameStatus(int _gameStatus){
        mGameStatus = _gameStatus;
    }

    public PuzzleView(Context context, int _piece, Uri _imageUri, GameOverCallBack _callBack) {
        super(context);
        mPiece = _piece;
        mImageUri = _imageUri;
        mCallBack = _callBack;

        mGameStatus = GAME_ON;

        a = IOUtil.getBitmapFromUri(getContext(), mImageUri);

        b = new Bitmap[mPiece * mPiece];

        link = new boolean[mPiece * mPiece][mPiece * mPiece];
        x = new int[mPiece * mPiece];
        y = new int[mPiece * mPiece];
        f = new boolean[mPiece * mPiece];
        positionx = new int[mPiece * mPiece];
        positiony = new int[mPiece * mPiece];


        width = width - width % mPiece;
        height = height - height % mPiece;
        wid = width / mPiece;
        hei = height / mPiece;
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);
        paint.setTextSize(24);
        a = Bitmap.createScaledBitmap(a, width, height, true);
        iniPath();
        Shader shader = new BitmapShader(a, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint2.setColor(Color.BLUE);
        paint2.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < mPiece; i++) {
            for (int t = 0; t < mPiece; t++) {
                int pw = wid;
                int ph = hei;
                int sw = 0;
                int sh = 0;
                positionx[index] = t * wid + sx;
                positiony[index] = i * hei + sy;
                x[index] = (int) (Math.random() * 0.9 * wwidth);
                y[index] = (int) (Math.random() * 0.8 * wheight + 20);
                f[index] = false;
                list.add(index);
                index++;
            }
        }
        bcakground2 = getResources().openRawResource(R.raw.game_baground);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inTempStorage = new byte[100 * 1024];
        background = BitmapFactory.decodeStream(bcakground2, null, opts);
        background = Bitmap.createScaledBitmap(background, (int) wwidth, (int) wheight, true);
        clockimg = Bitmap.createScaledBitmap(clockimg, (int) (wwidth / 5), (int) (wwidth / 5), true);
        timebackground = Bitmap.createScaledBitmap(timebackground, (int) (wwidth / 5 * 2), (int) (wwidth / 5 / 2), true);
    }

    WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
    float wwidth = wm.getDefaultDisplay().getWidth();
    float wheight = wm.getDefaultDisplay().getHeight();
    int width = (int) (wwidth * 0.9);
    int height = width;
//    Bitmap a = BitmapFactory.decodeResource(this.getResources(), R.drawable.testpic);

//    Bitmap a = BitmapFactory.decodeFile(mImageUri.getPath());
//    Bitmap a = IOUtil.getBitmapFromUri(getContext(),mImageUri);

    Bitmap background;
    InputStream bcakground2;
    Bitmap clockimg = BitmapFactory.decodeResource(this.getResources(), R.drawable.clock);
    Bitmap timebackground = BitmapFactory.decodeResource(this.getResources(), R.drawable.time);
    Bitmap[] b;

    boolean[][] link;
    int[] x;
    int[] y;
    boolean[] f;
    int[] positionx;
    int[] positiony;
    int wid = 0;
    int hei = 0;
    int index = 0;
    double ox = 0, nx = 0, oy = 0, ny = 0;
    int index2 = -1;
    int sx = (int) (wwidth - width) / 2;
    int sy = (int) (wheight - height) / 3;
    Paint paint = new Paint();
    Paint paint2 = new Paint();
    int count = 0;

    private Path createPiecePath(int min, int offX, int offY, PieceEdge eTop, PieceEdge eRight, PieceEdge eBottom, PieceEdge eLeft) {
        Path p = new Path();
        p.moveTo(offX, offY);
        if (eTop == null)
            p.lineTo(offX + min, offY);
        else {
            for (int i = 1; i < 6; i++)
                p.cubicTo(eTop.mEdge[i].ctrlA.x + offX, eTop.mEdge[i].ctrlA.y + offY,
                        eTop.mEdge[i].ctrlB.x + offX, eTop.mEdge[i].ctrlB.y + offY,
                        eTop.mEdge[i].p.x + offX, eTop.mEdge[i].p.y + offY);
        }
        if (eRight == null)
            p.lineTo(offX + min, offY + min);
        else {
            for (int i = 1; i < 6; i++)
                p.cubicTo(eRight.mEdge[i].ctrlA.x + offX + min, eRight.mEdge[i].ctrlA.y + offY,
                        eRight.mEdge[i].ctrlB.x + offX + min, eRight.mEdge[i].ctrlB.y + offY,
                        eRight.mEdge[i].p.x + offX + min, eRight.mEdge[i].p.y + offY);
        }
        if (eBottom == null)
            p.lineTo(offX, offY + min);
        else {
            for (int i = 5; i >= 1; i--)
                p.cubicTo(eBottom.mEdge[i].ctrlB.x + offX, eBottom.mEdge[i].ctrlB.y + offY + min,
                        eBottom.mEdge[i].ctrlA.x + offX, eBottom.mEdge[i].ctrlA.y + offY + min,
                        eBottom.mEdge[i - 1].p.x + offX, eBottom.mEdge[i - 1].p.y + offY + min);
        }
        if (eLeft == null)
            p.lineTo(offX, offY);
        else {
            for (int i = 5; i >= 1; i--)
                p.cubicTo(eLeft.mEdge[i].ctrlB.x + offX, eLeft.mEdge[i].ctrlB.y + offY,
                        eLeft.mEdge[i].ctrlA.x + offX, eLeft.mEdge[i].ctrlA.y + offY,
                        eLeft.mEdge[i - 1].p.x + offX, eLeft.mEdge[i - 1].p.y + offY);
        }
        return p;
    }

    Path table;
    Path[] path;
    PieceEdge[] rawEdge;
    PieceEdge[] colEdge;
    LinkedList<Integer> list = new LinkedList<Integer>();

    private void iniPath() {
        table = new Path();
        path = new Path[mPiece * mPiece];
        rawEdge = new PieceEdge[mPiece * mPiece];
        colEdge = new PieceEdge[mPiece * mPiece];
        PieceEdge e = new PieceEdge();
        for (int i = 0; i < mPiece * mPiece; i++) {
            rawEdge[i] = e.getRawEdge((float) Math.random(), wid);
            colEdge[i] = e.getColEdge((float) Math.random(), wid);
        }

        PieceEdge eTop;
        PieceEdge eRight;
        PieceEdge eBottom;
        PieceEdge eLeft;
        for (int i = 0; i < mPiece; i++) {
            for (int j = 0; j < mPiece; j++) {
//                bmp[i*numOfPiece+j] = createPieceImage(source,tableWidth/numOfPiece,i*tableWidth/numOfPiece,j*tableWidth/numOfPiece);
                eTop = j == 0 ? null : rawEdge[i * mPiece + j - 1];
                eRight = i == (mPiece - 1) ? null : colEdge[i * mPiece + j];
                eBottom = j == (mPiece - 1) ? null : rawEdge[i * mPiece + j];
                eLeft = i == 0 ? null : colEdge[i * mPiece + j - mPiece];
                path[i * mPiece + j] = createPiecePath(wid, i * wid, j * wid, eTop, eRight, eBottom, eLeft);
                table.addPath(path[i * mPiece + j], sx, sy);
            }
        }
    }


    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawBitmap(clockimg, (int) (wwidth / 5), sy - (int) (wwidth / 5) - 10, null);
        canvas.drawBitmap(timebackground, (int) (wwidth / 5 * 2), sy - (int) (wwidth / 5 / 4 * 3) - 10, null);
        canvas.drawPath(table, paint2);
        for (int i = 0; i < list.size(); i++) {
            int t = list.get(i);
            canvas.translate(x[t], y[t]);
            canvas.translate(-t % mPiece * wid, -t / mPiece * hei);
            canvas.drawPath(path[t % mPiece * mPiece + t / mPiece], paint);
            canvas.translate(-x[t], -y[t]);
            canvas.translate(t % mPiece * wid, t / mPiece * hei);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        nx = event.getX();
        ny = event.getY();
        if (mGameStatus != PuzzleView.GAME_ON)
            return false;
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            ox = nx;
            oy = ny;
            index2 = -1;
            for (int i = mPiece * mPiece - 1; i >= 0; i--) {
                int t = list.get(i);
                if (f[t] == false && (nx - x[t]) > 0 && (nx - x[t] < wid) && (ny - y[t]) > 0 && (ny - y[t]) < hei) {
                    index2 = t;
                    break;
                }
            }
            if (index2 < 0)
                return false;
            else {
                list.remove(list.indexOf(index2));
                list.addLast(index2);
            }
        }
        if (action == MotionEvent.ACTION_MOVE) {
            if (index2 < 0)
                return false;
            x[index2] += (int) (nx - ox);
            y[index2] += (int) (ny - oy);
            ox = nx;
            oy = ny;
        }
        if (action == MotionEvent.ACTION_UP) {
            if (index2 < 0)
                return false;
            if (Math.abs(x[index2] - positionx[index2]) <= 25 && Math.abs(y[index2] - positiony[index2]) <= 25) {
                x[index2] = positionx[index2];
                y[index2] = positiony[index2];
                f[index2] = true;
                count++;
                list.remove(list.indexOf(index2));
                list.addFirst(index2);
                if (count == mPiece * mPiece) {
                    mGameStatus = PuzzleView.GAME_OVER;
                    mCallBack.onGameOver();
                }
            }
        }
        invalidate();
        return true;
    }
}

