package sg.ruqqq.becontent;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

public class P11Widget extends AppWidgetProvider {
	private SdReaderSvc mBoundService;
	//private Context context;
	private boolean mIsBound;
	
	/*public void onEnabled(Context context) {
		this.context = context;
		ComponentName thisWidget = new ComponentName(context, P11Widget.class);
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		
		
	}*/
	
	
	
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		int appWidgetId = appWidgetIds[0];
		
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
		
		views.setTextViewText(R.id.tv, "HELLO WORLD");
		appWidgetManager.updateAppWidget(appWidgetId, views);
		
		Intent svc = new Intent(context, SdReaderSvc.class);
		context.startService(svc);
	} 
	
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		
		Log.d(context.getPackageName(), "OnReceive: "+intent.getAction()+" vs "+SdReaderSvc.UPDATEALERT);
		if (intent.getAction().equals(SdReaderSvc.UPDATEALERT)) {
			ComponentName thisWidget = new ComponentName(context, P11Widget.class);
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
			
			Bundle b = intent.getExtras();
			String text = "Audio Files: "+b.getInt("audioCount")+"\n Image Files: "+b.getInt("imageCount")+"\n"+"Video Files: "+b.getInt("videoCount")+"\nTotal Size: "+(b.getDouble("totalSize")/1000/1000)+"MB";
			
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
			
			views.setTextViewText(R.id.tv, text);
			appWidgetManager.updateAppWidget(appWidgetIds[0], views);
			Log.d(context.getPackageName(), "Widget updated");
		}
	}
	
}
