package akiyama.mykeep.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.akiyama.data.dbservice.IBaseController;
import com.akiyama.data.db.model.IModel;

/**
 * 插入保存单条数据的Task
 * @author zhiwu_yan
 * @version 1.0
 * @since 2015-08-07  10:43
 */
public abstract class SaveSingleDbTask extends AsyncTask<IModel,Void,Boolean> {

    protected IBaseController mBaseController;
    protected Context mContext;
    protected ProgressDialog mProgressBar;
    protected boolean mIsShowProgressBar;
    public SaveSingleDbTask(Context context,IBaseController baseController,boolean isShowProgressBar){
        this.mContext=context;
        this.mBaseController=baseController;
        this.mIsShowProgressBar = isShowProgressBar;
    }
    @Override
    protected void onPreExecute() {
        savePreExecute();
    }

    @Override
    protected Boolean doInBackground(IModel... params) {
        if(mBaseController.insert(mContext,params[0])!=null){
            return true;
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        savePostExecute(aBoolean);
        if(mProgressBar!=null){
            mProgressBar.dismiss();
        }
    }

    /**
     * 保存数据前执行的操作
     */
    protected void savePreExecute(){
        if(mIsShowProgressBar){
            mProgressBar=new ProgressDialog(mContext);
            mProgressBar.setMessage("正在保存，请稍后......");
            mProgressBar.show();
        }
    }


    /**
     * 保存数据成功后执行的操作
     */
    public abstract void savePostExecute(Boolean aBoolean);
}
