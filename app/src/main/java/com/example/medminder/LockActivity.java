package com.example.medminder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LockActivity extends AppCompatActivity implements SlidingFinishLayout.OnSlidingFinishListener {
    private TextView tv_word,tv_2;
    private LinearLayout lin_1;
    private ReminderEntryDBHelper mDBHelper;
    private ReminderEntry list;
    public static String ENTRY_ID = "entry id";
    public static String FROM_HISTORY = "from history";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent in = getIntent();
        if (in == null || !Common.JUMP_FROM_SCREEN_LISTENER.equals(in.getStringExtra(Common.JUMP_FROM))) {
            finish();
            return;
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        fullScreen(this);
        setContentView(R.layout.activity_lock);
        mDBHelper = new ReminderEntryDBHelper(this);
        if (mDBHelper != null) list = mDBHelper.fetchEntryByTime();
        initView();
    }
    /**
     * Make the status bar transparent by setting the full screen
     *
     * @param activity
     */
    public static void fullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //X needs to make the color transparent at the beginning
                //otherwise the navigation bar will display the system default light gray
                Window window = activity.getWindow();
                View decorView = window.getDecorView();

                //The two flags are used together
                //indicating that the main content of the application occupies space in the system status bar
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);

            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                attributes.flags |= flagTranslucentStatus;
                window.setAttributes(attributes);
            }
        }
    }


    private void initView() {
        tv_word = findViewById(R.id.tv_word);
        tv_2 = findViewById(R.id.tv_2);
        lin_1 = findViewById(R.id.lin_1);
        SlidingFinishLayout vLockRoot = findViewById(R.id.lock_root);
        TextView tvLockTime = findViewById(R.id.lock_time);
        TextView tvLockDate = findViewById(R.id.lock_date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm-M,dd E", Locale.CHINESE);
        String[] date = simpleDateFormat.format(new Date()).split("-");
        tvLockTime.setText(date[0]);
        tvLockDate.setText(date[1]);
        vLockRoot.setOnSlidingFinishListener(this);
        Log.e("=======",new Gson().toJson(list));
        tv_word.setText(formatFirstLine(list));
        tv_2.setText(formatSecondLine(list));
        lin_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                if (list.getmReminderType() == 0) {
                    intent = new Intent(LockActivity.this, DisplayEntryActivity.class);
                }
//        else intent = new Intent(getActivity(), MapDisplayActivity.class);
                intent.putExtra(ENTRY_ID, list.getId());
                intent.putExtra(FROM_HISTORY,true);
                startActivity(intent);
                finish();
            }
        });
//        try {
//            initErrorWord();
//            playBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String wordName1 = tv_audio_name.getText().toString().trim();
//                    if (StringUtils.isNotEmpty(wordName1)) {
//                        SystemTTS systemTTS = SystemTTS.getInstance(getApplicationContext());
//                        systemTTS.playText(wordName1);
//                    }
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }


    /**
     * 重写物理返回键，使不能回退
     */
    @Override
    public void onBackPressed() {
    }
//    @SuppressLint("MissingPermission")
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (!hasFocus){
//            //在recent按下时 发送一个recent事件 使recent失效
//            Intent intent = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
//            intent.putExtra("reason", "globalactions");
//            sendBroadcast(intent);
//        }
//    }

    /**
     * 滑动销毁锁屏页面
     */
    @Override
    public void onSlidingFinish() {
        Log.e("======","finish");
        finish();
    }

    private String formatFirstLine(ReminderEntry entry) {

        String input = MedicationFragment.INPUT_TO_ID[entry.getmReminderType()];
        String activity = MedicationFragment.ACTIVITY_TO_ID[entry.getmMReminderMedicationType()];
        String dateTime = formatDateTime(entry.getmDateTime());
        return input + ": " + activity + ", " + dateTime;
    }
    public static String formatDateTime(long dateTime) {
        Date date = new Date(dateTime);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss aa MMM dd yyyy");
        return sdf.format(date);
    }

    /**
     * Gets and formats the second line of the history entry
     */
    private String formatSecondLine(ReminderEntry entry) {

        String medicationName = entry.getmMedicationName();
        Log.d("TodayFragment", "medicationName failed?");
        return medicationName;
    }


}
