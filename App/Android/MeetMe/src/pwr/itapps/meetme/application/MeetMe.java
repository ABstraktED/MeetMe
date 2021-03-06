package pwr.itapps.meetme.application;

import pwr.itapps.meetme.R;
import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MeetMe extends Application {

	public static String SERVICE_SERVER_ADDRESS = "http://meetmepwrrest.aws.af.cm/api/";
	
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
