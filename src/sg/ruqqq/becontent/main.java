package sg.ruqqq.becontent;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;

public class main extends Activity {
	int audioCount = 0;
	int imageCount = 0;
	int videoCount = 0;
	double totalSize = 0;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		loadMedia();
		((TextView) findViewById(R.id.tv)).setText("Audio Files: "+audioCount+"\nImage Files: "+imageCount+"\nVideo Files: "+videoCount+"\n\n Total Size: "+(totalSize/1000/1000)+"MB");
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