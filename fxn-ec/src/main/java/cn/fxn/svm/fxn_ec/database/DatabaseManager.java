package cn.fxn.svm.fxn_ec.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

/**
 * @author:Matthew
 * @date:2018/10/19
 * @email:guocheng0816@163.com
 * @func:
 */
public class DatabaseManager {
    private DaoSession mDaoSession=null;
    private UserProfileDao mDao=null;

    private static final class Holder{
        private static final DatabaseManager INSTANCE=new DatabaseManager();
    }

    private DatabaseManager(){

    }

    public static DatabaseManager getInstance(){
        return Holder.INSTANCE;
    }

    public DatabaseManager init(Context context){
        initDao(context);
        return this;
    }



    private void initDao(Context context){
        final ReleaseOpenHelper helper=new ReleaseOpenHelper(context, "fast_ec.db");
        final Database database=helper.getWritableDb();
        mDaoSession=new DaoMaster(database).newSession();
        mDao=mDaoSession.getUserProfileDao();
    }

    public final UserProfileDao getDao(){
        return mDao;
    }
}
