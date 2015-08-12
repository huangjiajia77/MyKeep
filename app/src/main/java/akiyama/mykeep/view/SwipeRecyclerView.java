package akiyama.mykeep.view;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import akiyama.mykeep.util.DimUtil;

/**
 * 有侧滑菜单的RecyclerView
 * @author zhiwu_yan
 * @version 1.0
 * @since 2015-08-12  15:03
 */
public class SwipeRecyclerView extends RecyclerView {

    private View mTouchView;
    private float mLastX;
    private float mLastY;
    private int mScreenWidth;
    /**
     * 滑动方向
     */
    enum RemoveDirection {
        RIGTH,
        LEFT,
        CLOSE
    }

    public SwipeRecyclerView(Context context){
        super(context);
        init(context);
    }

    public SwipeRecyclerView(Context context,AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    public SwipeRecyclerView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        init(context);
    }

    private void init(Context context){
        mScreenWidth = DimUtil.getScreenWidth();
    }
    @Override
    public boolean onTouchEvent(MotionEvent e) {

        float x=e.getX();
        float y=e.getY();
        int action=e.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mTouchView= findChildViewUnder(e.getX(),e.getY());
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX=mLastX-x;
                if (deltaX !=0 && mTouchView!=null) {
                    smoothXScrollTo(mTouchView,(int)deltaX);
                }
                break;
        }

        mLastX = x;
        mLastY = y;
        return super.onTouchEvent(e);
    }

    /**
     * 滑动到
     * @param destView
     * @param destX
     */
    private void smoothXScrollTo(View destView, int destX) {
        int scrollX = destView.getScrollX();
        RemoveDirection direction=getScrollDirection(destView);
        if(direction==RemoveDirection.CLOSE){
            smoothXScrollToClose(destView,destX);
        }else if(direction==RemoveDirection.RIGTH){
            smoothXScrollToRight(destView,destX,scrollX);
        }else {
            smoothXScrollToLeft(destView,destX,scrollX);
        }

    }

    private void smoothXScrollToClose(View destView, int destX){
        if(destX > 0){
            smoothXScrollToLeft(destView,destX,0);
        }

    }

    private void smoothXScrollToLeft(View destView, int destX,int scrollX){
        if((destX + scrollX) >= mScreenWidth/3 ){
            destView.scrollTo(mScreenWidth/3,0);
        }else{
            destView.scrollBy(destX,0);
        }
    }

    private void smoothXScrollToRight(View destView, int destX,int scrollX){
        if(destX <= Math.abs(scrollX)){
            destView.scrollTo(destX,0);
        }else{
            destView.scrollTo(scrollX,0);
        }
    }


    private RemoveDirection getScrollDirection(View destView){
        int scrollX = destView.getScrollX();
        if(scrollX==0){
            return RemoveDirection.CLOSE;
        }else if(scrollX < 0){
            return RemoveDirection.RIGTH;
        }else{
            return RemoveDirection.LEFT;
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
    }
}
