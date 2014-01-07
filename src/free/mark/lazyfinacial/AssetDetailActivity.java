package free.mark.lazyfinacial;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AssetDetailActivity extends Activity {
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.asset_details);
	}
	
	public void updateAsset(View view) {
		Intent intent = new Intent(this, UpdateAssetActivity.class);
		startActivity(intent);
	}
}
