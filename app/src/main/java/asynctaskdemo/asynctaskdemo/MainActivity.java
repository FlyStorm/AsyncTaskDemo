package asynctaskdemo.asynctaskdemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final String TAG ="MainActivity" ;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //执行异步任务,实现AsyncTask类的调用
        new MyAsyncTask().execute();
    }

    //异步任务
    class MyAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        //调用后立即执行
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(MainActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

            dialog.show();

            Log.e(TAG, "onPreExecute-->线程名称：" + Thread.currentThread().getName());
        }

        //在onPreExecute后执行，用于执行耗时的操作，接收输入参数，返回计算结果
        @Override
        protected Integer doInBackground(Integer... params) {
            for (int i = 1; i < 200; i++) {
                Log.e(TAG, "doInBackground-->线程名称：" + Thread.currentThread().getName());
                //更新进度
                publishProgress(100,i);

                SystemClock.sleep(10);
            }
            return 10;
        }
        //直接将进度信息更新到UI界面上
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.e(TAG, "onProgressUpdate-->线程名称：" + Thread.currentThread().getName()+",values[0]=="+values[0]
            +"---values[1]=="+values[1]);
            dialog.setMax(values[0]);
            dialog.setProgress(values[1]);
        }
        //后台结束时调用的方法，返回结果。
        @Override
        protected void onPostExecute(Integer a) {
            super.onPostExecute(a);
            dialog.dismiss();
            Toast.makeText(MainActivity.this,"更新完成",Toast.LENGTH_SHORT).show();
            Log.e(TAG, "onPostExecute-->" + Thread.currentThread().getName()+"---a=="+a);
        }

    }
}
