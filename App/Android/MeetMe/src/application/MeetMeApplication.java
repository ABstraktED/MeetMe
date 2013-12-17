package application;

import pwr.itapps.meetme.R;
import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MeetMeApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).build();
		ImageLoader.getInstance().init(config);
	}

	public static DisplayImageOptions OPTIONS = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.loading)
			.showImageForEmptyUri(R.drawable.empty)
			.showImageOnFail(R.drawable.error).cacheInMemory(true)
			.cacheOnDisc(true).build();

	
	
}
