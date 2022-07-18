package com.example.medminder;

import android.app.AlarmManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ScreenListenerService extends Service {
    private ReminderEntryDBHelper mDBHelper;
    private List<ReminderEntry> list;
    public ScreenListenerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mDBHelper = new ReminderEntryDBHelper(this);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (mDBHelper != null) list = mDBHelper.fetchAllEntries();
                for (ReminderEntry item:list){
                    Log.e("======",item.getmDateTime()-System.currentTimeMillis()+"");
                    if (item.getmDateTime()-System.currentTimeMillis()<5000 && item.getmDateTime()-System.currentTimeMillis()>0){
                        Log.e("======","PLAY");
                        playvoice();
                    }
                }
            }
        }, 5 * 1000, 5 * 1000);
        registerReceiver(receiver,new IntentFilter(Intent.ACTION_SCREEN_OFF));
        return super.onStartCommand(intent, flags, startId);
    }

    private void playvoice() {
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        MediaPlayer mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(this, alert);
        } catch (IllegalArgumentException e1) {
            e1.printStackTrace();
        } catch (SecurityException e1) {
            e1.printStackTrace();
        } catch (IllegalStateException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
        mMediaPlayer.setLooping(false);
        try {
            mMediaPlayer.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.start();
    }


    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())){
                Intent in = new Intent(context,LockActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putExtra(Common.JUMP_FROM,Common.JUMP_FROM_SCREEN_LISTENER);
                startActivity(in);
            }
        }
    };
}
