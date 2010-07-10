package sg.ruqqq.becontent;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

public class SdReaderSvc extends Service {
	int audioCount = 0;
	int imageCount = 0;
	int videoCount = 0;
	double totalSize = 0;
	public static final String UPDATEALERT = "android.appwidget.action.APPWIDGET_UPDATE";
	
	@Override
	public void onCreate() {
		Log.d(getPackageName(), "Service Running");
		loadMedia();
		
		updateWidget();
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void updateWidget() {
		Log.d(getPackageName(), "Updating Widget");
		// Create the IntentFilter
		Intent bIntent = new Intent(UPDATEALERT);
		bIntent.putExtra("audioCount", audioCount);
		bIntent.putExtra("imageCount", imageCount);
		bIntent.putExtra("videoCount", videoCount);
		bIntent.putExtra("totalSize", totalSize);
		
		// Send the Intent Broadcast
		sendBroadcast(bIntent);
		this.stopSelf();
	}
	
	private void loadMedia() {
		ContentResolver contentResolver = getBaseContext().getContentResolver();
		
		Cursor audioCursor = contentResolver.query( MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[] { MediaStore.Audio.AudioColumns.SIZE }, null,null, null);
		audioCount = audioCursor.getCount();
		if (audioCursor.moveToFirst()) {
			do {
				Log.d("Audio Size", ""+audioCursor.getInt(0));
				
				totalSize += audioCursor.getInt(0);
			} while (audioCursor.moveToNext());
		}
		audioCursor.close();
		
		Cursor imageCursor = contentResolver.query( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] { MediaStore.Images.ImageColumns.SIZE }, null,null, null);
		imageCount = imageCursor.getCount();
		if (imageCursor.moveToFirst()) {
			do {
				Log.d("Image Size", ""+imageCursor.getInt(0));
				totalSize += imageCursor.getInt(0);
			} while (imageCursor.moveToNext());
		}
		imageCursor.close();
		
		Cursor videoCursor = contentResolver.query( MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[] { MediaStore.Video.VideoColumns.SIZE }, null,null, null);
		videoCount = videoCursor.getCount();
		if (videoCursor.moveToFirst()) {
			do {
				Log.d("Video Size", ""+videoCursor.getInt(0));
				
				totalSize += videoCursor.getInt(0);
			} while (videoCursor.moveToNext());
		}
		videoCursor.close();
	}

}
