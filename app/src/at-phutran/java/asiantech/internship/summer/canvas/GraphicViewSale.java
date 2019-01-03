package asiantech.internship.summer.canvas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.List;
import asiantech.internship.summer.R;
import asiantech.internship.summer.model.Money;

public class GraphicViewSale extends View {
    private static final String CONTENT = "Sales and Expenses";
    private static final String SALE = "Sales";
    private static final String EXPENSE = "Expenses";
    private static final String DEFAULT_STRING = "$140,000";
    private static final int DRAG = 1;
    private static final int NONE = 0;
    private static final int ZOOM = 2;
    private int mBeginWidth;
    private int mBeginHeight;
    private boolean mDragged = false;
    private Paint mPaintSale;
    private Paint mPaintExpense;
    private Paint mPaintText;
    private Paint mPaintTextMoney;
    private Paint mPaintTextContent;
    private Paint mPaintLine;
    private Paint mPaintNoteSale;
    private Paint mPaintNoteExpense;
    private Paint mPaintBackground;
    private int mMode;
    private float mStartX = 0f;
    private float mStartY = 0f;
    private float mTranslateX = 0f;
    private float mTranslateY = 0f;
    private float mPreTranslateX = 0f;
    private float mPreTranslateY = 0f;
    private List<Money> mListMoney;
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;

    public GraphicViewSale(Context context) {
        this(context, null);
    }

    public GraphicViewSale(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GraphicViewSale(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initListMoney();
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        getStyleableAttributes(context, attrs);
    }

    private void getStyleableAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GraphicViewSale);
        try {
            int colorOfLine = typedArray.getColor(R.styleable.GraphicViewSale_colorOfLine, 0);
            int colorOfSale = typedArray.getColor(R.styleable.GraphicViewSale_colorOfSale, 0);
            int colorOfExpense = typedArray.getColor(R.styleable.GraphicViewSale_colorOfExpense, 0);
            int colorOfText = typedArray.getColor(R.styleable.GraphicViewSale_colorOfText, 0);
            int colorOfBackground = typedArray.getColor(R.styleable.GraphicViewSale_colorOfBackground, 0);
            float widthOfColumn = typedArray.getDimension(R.styleable.GraphicViewSale_widthOfColumn, 0);
            float widthOfNoteColumn = typedArray.getDimension(R.styleable.GraphicViewSale_widthOfNoteColumn, 0);
            float textNote = typedArray.getDimension(R.styleable.GraphicViewSale_textNote, 0);
            float textContent = typedArray.getDimension(R.styleable.GraphicViewSale_textContent, 0);
            mPaintLine.setColor(colorOfLine);
            mPaintSale.setColor(colorOfSale);
            mPaintSale.setStrokeWidth(widthOfColumn);
            mPaintNoteSale.setColor(colorOfSale);
            mPaintNoteSale.setStrokeWidth(widthOfNoteColumn);
            mPaintExpense.setColor(colorOfExpense);
            mPaintExpense.setStrokeWidth(widthOfColumn);
            mPaintNoteExpense.setColor(colorOfExpense);
            mPaintNoteExpense.setStrokeWidth(widthOfNoteColumn);
            mPaintText.setColor(colorOfText);
            mPaintTextMoney.setColor(colorOfText);
            mPaintTextContent.setColor(colorOfText);
            mPaintBackground.setColor(colorOfBackground);
            mPaintTextMoney.setTextSize(textNote);
            mPaintTextMoney.setTextAlign(Paint.Align.RIGHT);
            mPaintText.setTextSize(textNote);
            mPaintTextContent.setTextSize(textContent);
        } finally {
            typedArray.recycle();
        }
    }

    private void initListMoney() {
        mListMoney = new ArrayList<>();
        //10 000$ = 1/20 height
        mListMoney.add(new Money(getContext().getString(R.string.Jan), 70, 10));
        mListMoney.add(new Money(getContext().getString(R.string.Feb), 80, 15));
        mListMoney.add(new Money(getContext().getString(R.string.Mar), 75, 20));
        mListMoney.add(new Money(getContext().getString(R.string.Apr), 90, 30));
        mListMoney.add(new Money(getContext().getString(R.string.May), 105, 45));
        mListMoney.add(new Money(getContext().getString(R.string.June), 130, 80));
        mListMoney.add(new Money(getContext().getString(R.string.Jul), 115, 50));
        mListMoney.add(new Money(getContext().getString(R.string.Aug), 100, 40));
        mListMoney.add(new Money(getContext().getString(R.string.Sep), 90, 20));
        mListMoney.add(new Money(getContext().getString(R.string.Oct), 80, 20));
        mListMoney.add(new Money(getContext().getString(R.string.Nov), 120, 40));
        mListMoney.add(new Money(getContext().getString(R.string.Dec), 140, 80));
    }

    private int getWidthScreen() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        assert windowManager != null;
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        try {
            display.getRealSize(size);
        } catch (NoSuchMethodError err) {
            display.getSize(size);
        }
        return size.x;
    }

    private int getHeightScreen() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        assert windowManager != null;
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        try {
            display.getRealSize(size);
        } catch (NoSuchMethodError err) {
            display.getSize(size);
        }
        return size.y;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mMode = DRAG;
                mStartX = event.getX() - mPreTranslateX;
                mStartY = event.getY() - mPreTranslateY;
                break;

            case MotionEvent.ACTION_MOVE:
                mTranslateX = event.getX() - mStartX;
                mTranslateY = event.getY() - mStartY;
                double distance = Math.sqrt(Math.pow(event.getX() - (mStartX + mPreTranslateX), 2) +
                        Math.pow(event.getY() - (mStartY + mPreTranslateY), 2));
                if (distance > 0 && mMode == DRAG) {
                    mDragged = true;
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mMode = ZOOM;
                break;

            case MotionEvent.ACTION_UP:
                mMode = NONE;
                mDragged = false;
                mPreTranslateX = mTranslateX;
                mPreTranslateY = mTranslateY;
                break;

            case MotionEvent.ACTION_POINTER_UP:
                mMode = DRAG;
                mPreTranslateX = mTranslateX;
                mPreTranslateY = mTranslateY;
                break;
        }
        setChartSize(getWidth(), getHeight());
        if (mMode == ZOOM) {
            mScaleDetector.onTouchEvent(event);
        }
        if ((mMode == DRAG && mScaleFactor != 1f) || mDragged) {
            invalidate();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBeginWidth = getWidthScreen() / 10;
        mBeginHeight = 4 * getHeightScreen() / 5;
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor, mScaleDetector.getFocusX(), mScaleDetector.getFocusY());
        drawNoteChart(canvas);
        drawLine(canvas);
        canvas.save();
        canvas.translate(mTranslateX / mScaleFactor, 0);
        drawChart(canvas);
        canvas.restore();
        drawTextSale(canvas);
    }

    private void drawNoteChart(Canvas canvas) {
        canvas.drawText(CONTENT, getWidthScreen() / 2 - mPaintTextContent.measureText(CONTENT) / 2, getHeightScreen() / 20, mPaintTextContent);
        canvas.drawLine(getWidthScreen() / 2 - mPaintTextContent.measureText(EXPENSE) - getHeightScreen() / 40, 9 * getHeightScreen() / 10, getWidthScreen() / 2 - mPaintTextContent.measureText(EXPENSE) - getHeightScreen() / 40, 9 * getHeightScreen() / 10 - 30, mPaintNoteSale);
        canvas.drawText(SALE, getWidthScreen() / 2 - mPaintTextContent.measureText(EXPENSE), 9 * getHeightScreen() / 10, mPaintText);
        canvas.drawLine(getWidthScreen() / 2, 9 * getHeightScreen() / 10, getWidthScreen() / 2, 9 * getHeightScreen() / 10 - 30, mPaintNoteExpense);
        canvas.drawText(EXPENSE, getWidthScreen() / 2 + getHeightScreen() / 40, 9 * getHeightScreen() / 10, mPaintText);
    }

    private void drawLine(Canvas canvas) {
        for (int i = 0; i < 8; i++) {
            canvas.drawLine(mBeginWidth - getWidthScreen() / 50, mBeginHeight - getHeightScreen() / 10 * i, getWidthScreen(), mBeginHeight - getHeightScreen() / 10 * i, mPaintLine);
        }
    }

    private void drawTextSale(Canvas canvas) {
        canvas.drawRect(0, 0, mBeginWidth - getWidthScreen() / 50, getHeightScreen(), mPaintBackground);
        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                canvas.drawText(getContext().getString(R.string.zero), mPaintTextMoney.measureText(DEFAULT_STRING), mBeginHeight - getHeightScreen() / 10 * i, mPaintTextMoney);
            } else {
                canvas.drawText(getContext().getString(R.string.dola) + 20 * i + getContext().getString(R.string.thousan), mPaintTextMoney.measureText(DEFAULT_STRING), mBeginHeight - getHeightScreen() / 10 * i, mPaintTextMoney);
            }
        }
    }

    private void drawChart(Canvas canvas) {
        for (int i = 0; i < mListMoney.size(); i++) {
            canvas.drawLine(mBeginWidth + 250 * i + getWidthScreen() / 50, mBeginHeight, mBeginWidth + 250 * i + getWidthScreen() / 50, (mBeginHeight - mListMoney.get(i).getSale() * getHeightScreen() / 200), mPaintSale);
            canvas.drawLine(mBeginWidth + 250 * i + getWidthScreen() / 50 + getWidthScreen() / 30, mBeginHeight, mBeginWidth + 250 * i + getWidthScreen() / 30 + getWidthScreen() / 50, (mBeginHeight - mListMoney.get(i).getExpense() * getHeightScreen() / 200), mPaintExpense);
            canvas.drawText(mListMoney.get(i).getMonth(), mBeginWidth + 250 * i + getWidthScreen() / 50 + getHeightScreen() / 70, mBeginHeight + getHeightScreen() / 40, mPaintText);
        }
    }

    private void setChartSize(int width, int height) {
        if ((mTranslateX * -1) < 0) {
            mTranslateX = 0;
        }
        if ((mTranslateX * -1) >= getMaxChartWidth() - width + width / 8) {
            mTranslateX = -(getMaxChartWidth() - width + width / 8);
        }
        if (mTranslateY * -1 < 0) {
            mTranslateY = 0;
        } else if ((mTranslateY * -1) > (mScaleFactor - 1) * height) {
            mTranslateY = (1 - mScaleFactor) * height;
        }
    }

    private int getMaxChartWidth() {
        return mBeginWidth + 250 * 11 + getWidthScreen() / 50;
    }

    private void initPaint() {
        mPaintSale = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintExpense = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintNoteSale = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintNoteExpense = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTextContent = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTextMoney = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(1.0f, Math.min(mScaleFactor, 5.0f));
            invalidate();
            return true;
        }
    }
}
