package pavishka.coloring.book.VectorArt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import pavishka.coloring.book.Activities.Screens.ScreenConstable;


public class VectorBasedDrawing extends View {
    Context context;
    ArrayList<Carding> grids = new ArrayList<>();
    ArrayList<Carding> grids1 = new ArrayList<>();
    private Bitmap baseDesign;
    private int cellHeight;
    private int cellWidth;
    private int numColumns;
    private int numRows;
    private Paint blackPaint = new Paint();
    private Paint hintPaint = new Paint();
    private Paint paint = new Paint();

    public VectorBasedDrawing(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.blackPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.paint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.hintPaint.setStyle(Paint.Style.STROKE);
        this.hintPaint.setStrokeWidth(7.0f);
        setNumColumns(10);
        setNumRows(10);
        this.context = context;
    }

    private void calculateDimensions() {
        if (this.numColumns >= 1 && this.numRows >= 1) {
            this.cellWidth = getWidth() / this.numColumns;
            this.cellHeight = getHeight() / this.numRows;
            invalidate();
        }
    }

    private int getIndex(Carding grid) {
        for (int i = 0; i < this.grids.size(); i++) {
            if (this.grids.get(i).getRow() == grid.getRow() && this.grids.get(i).getColumn() == grid.getColumn()) {
                return i;
            }
        }
        return 0;
    }

    private boolean isExist(Carding grid) {
        for (int i = 0; i < this.grids.size(); i++) {
            if (this.grids.get(i).getRow() == grid.getRow() && this.grids.get(i).getColumn() == grid.getColumn()) {
                return true;
            }
        }
        return false;
    }

    public int getNumColumns() {
        return this.numColumns;
    }

    public void setNumColumns(int i) {
        this.numColumns = i;
        calculateDimensions();
    }

    public int getNumRows() {
        return this.numRows;
    }

    public void setNumRows(int i) {
        this.numRows = i;
        calculateDimensions();
    }

    public void initializeBaseDesign(Canvas canvas) {
        Bitmap bitmap = this.baseDesign;
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.paint);
            return;
        }
        int width = getWidth();
        int height = getHeight();
        this.baseDesign = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas2 = new Canvas(this.baseDesign);
        for (int i = 0; i < this.numColumns; i++) {
            if (i == 0) {
                this.blackPaint.setStrokeWidth(7.0f);
            } else {
                this.blackPaint.setStrokeWidth(1.0f);
            }
            float f = this.cellWidth * i;
            canvas2.drawLine(f, 0.0f, f, height, this.blackPaint);
        }
        for (int i2 = 0; i2 < this.numRows; i2++) {
            if (i2 == 0) {
                this.blackPaint.setStrokeWidth(7.0f);
            } else {
                this.blackPaint.setStrokeWidth(1.0f);
            }
            float f2 = this.cellHeight * i2;
            canvas2.drawLine(0.0f, f2, width, f2, this.blackPaint);
        }
        this.blackPaint.setStrokeWidth(7.0f);
        canvas2.drawLine(0.0f, (getNumRows() * this.cellHeight) - 1, width, (getNumRows() * this.cellHeight) - 1, this.blackPaint);
        canvas2.drawLine((getNumColumns() * this.cellWidth) - 1, 0.0f, (getNumColumns() * this.cellWidth) - 1, height, this.blackPaint);
        canvas.drawBitmap(this.baseDesign, 0.0f, 0.0f, this.paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(-1);
        if (!(this.numColumns == 0 || this.numRows == 0)) {
            getWidth();
            getHeight();
            for (int i = 0; i < this.grids.size(); i++) {
                this.paint.setColor(getResources().getColor(ScreenConstable.colorArray[this.grids.get(i).getColor()]));
                canvas.drawRect(this.grids.get(i).getColumn() * this.cellWidth, this.grids.get(i).getRow() * this.cellHeight, (this.grids.get(i).getColumn() + 1) * this.cellWidth, (this.grids.get(i).getRow() + 1) * this.cellHeight, this.paint);
            }
            if (ScreenPixelSkillActivity.aBoolean) {
                for (int i2 = 0; i2 < this.grids1.size(); i2++) {
                    this.hintPaint.setColor(getResources().getColor(ScreenConstable.colorArray[this.grids1.get(i2).getColor()]));
                    canvas.drawRect((this.grids1.get(i2).getColumn() * this.cellWidth) + 4, (this.grids1.get(i2).getRow() * this.cellHeight) + 4, ((this.grids1.get(i2).getColumn() + 1) * this.cellWidth) - 4, ((this.grids1.get(i2).getRow() + 1) * this.cellHeight) - 4, this.hintPaint);
                }
            }
            initializeBaseDesign(canvas);
        }
    }

    @Override
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        calculateDimensions();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 2) {
            invalidate();
        }
        motionEvent.getX();
        motionEvent.getY();
        int action = motionEvent.getAction();
        if (action == 0) {
            int x = (int) (motionEvent.getX() / this.cellWidth);
            int y = (int) (motionEvent.getY() / this.cellHeight);
            Carding grid = new Carding(y, x, ScreenConstable.selected_color);
            if (y < getNumRows() && x < getNumColumns()) {
                if (ScreenConstable.eraser) {
                    if (isExist(grid)) {
                        this.grids.remove(getIndex(grid));
                    }
                } else if (!isExist(grid)) {
                    this.grids.add(grid);
                } else {
                    Carding grid2 = new Carding(y, x, ScreenConstable.selected_color);
                    this.grids.set(getIndex(grid2), grid2);
                }
            }
            invalidate();
            Log.d("DRAW_TEST", y + " : " + x);
        } else if (action == 1) {
            ((ScreenPixelSkillActivity) this.context).checkGrid();
        } else if (action == 2) {
            int x2 = (int) (motionEvent.getX() / this.cellWidth);
            int y2 = (int) (motionEvent.getY() / this.cellHeight);
            Carding grid3 = new Carding(y2, x2, ScreenConstable.selected_color);
            if (y2 < getNumRows() && x2 < getNumColumns()) {
                if (ScreenConstable.eraser) {
                    if (isExist(grid3)) {
                        this.grids.remove(getIndex(grid3));
                    }
                } else if (!isExist(grid3)) {
                    this.grids.add(grid3);
                } else {
                    Carding grid4 = new Carding(y2, x2, ScreenConstable.selected_color);
                    this.grids.set(getIndex(grid4), grid4);
                }
            }
            invalidate();
        }
        return true;
    }

    public void setHintPicture(ArrayList<Carding> arrayList) {
        this.grids1 = arrayList;
        invalidate();
    }

    public void setPicture(ArrayList<Carding> arrayList) {
        this.grids = arrayList;
    }
}
