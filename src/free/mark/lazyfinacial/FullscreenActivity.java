package free.mark.lazyfinacial;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import free.mark.lazyfinacial.util.SystemUiHider;

public class FullscreenActivity extends Activity {
	private static final boolean TOGGLE_ON_CLICK = true;

	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	private SystemUiHider mSystemUiHider;
	private EditText inputPwdText;
	private EditText inputPwdAgainText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen);

		final View controlsView = findViewById(R.id.fullscreen_content_controls);
		final View contentView = findViewById(R.id.fullscreen_content);
		inputPwdText = (EditText) findViewById(R.id.textPwd);
		inputPwdAgainText = (EditText) findViewById(R.id.textPwdAgain);
		

		mSystemUiHider = SystemUiHider.getInstance(this, contentView,
				HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider
				.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
					// Cached values.
					int mControlsHeight;
					int mShortAnimTime;

					@Override
					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
					public void onVisibilityChange(boolean visible) {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
							if (mControlsHeight == 0) {
								mControlsHeight = controlsView.getHeight();
							}
							if (mShortAnimTime == 0) {
								mShortAnimTime = getResources().getInteger(
										android.R.integer.config_shortAnimTime);
							}
							controlsView
									.animate()
									.translationY(visible ? 0 : mControlsHeight)
									.setDuration(mShortAnimTime);
						} else {
							controlsView.setVisibility(visible ? View.VISIBLE
									: View.GONE);
						}
					}
				});

		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TOGGLE_ON_CLICK) {
					mSystemUiHider.toggle();
				} else {
					mSystemUiHider.show();
				}
			}
		});
		
		setInputPwdAgainAvailable();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		inputPwdText.setText("");
		inputPwdAgainText.setText("");
		setInputPwdAgainAvailable();
	}


	public void setInputPwdAgainAvailable() {
		SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
		String inputPwd = sharedPref.getString("inputPwd", "");
		if(inputPwd != null && !inputPwd.equals("")) {
			findViewById(R.id.inputPwdAgain).setVisibility(View.INVISIBLE);
		}
	}
	
	public void showAssetInformation(View view) {
		// Do something in response to button
		if(inputPwdText.getText().toString().equals("")) {
			new AlertDialog.Builder(this)
			.setTitle(R.string.no_pwd_input_title)
			.setMessage(R.string.no_pwd_input_message)
			.setPositiveButton(R.string.no_pwd_input_confirm, null).show();
			return;
		}
		
		if(findViewById(R.id.inputPwdAgain).getVisibility() == View.VISIBLE &&
				inputPwdAgainText.getText().toString().equals("")) {
			new AlertDialog.Builder(this)
			.setTitle(R.string.no_pwd_input_title)
			.setMessage(R.string.no_pwd_again_input_message)
			.setPositiveButton(R.string.no_pwd_input_confirm, null).show();
			return;
		}
		
		if(findViewById(R.id.inputPwdAgain).getVisibility() == View.VISIBLE && 
				!inputPwdText.getText().toString().equals(inputPwdAgainText.getText().toString())) {
			new AlertDialog.Builder(this)
			.setTitle(R.string.no_pwd_input_title)
			.setMessage(R.string.pwd_not_match_message)
			.setPositiveButton(R.string.no_pwd_input_confirm, null).show();
			inputPwdText.setText("");
			inputPwdAgainText.setText("");
			return;
		}
		
		SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
		String inputPwd = sharedPref.getString("inputPwd", "");
		if(inputPwd.equals("")) {
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putString("inputPwd", inputPwdText.getText().toString());
			editor.commit();
			
		} else if(!inputPwd.equals(inputPwdText.getText().toString())){
			new AlertDialog.Builder(this)
			.setTitle(R.string.no_pwd_input_title)
			.setMessage(R.string.pwd_error)
			.setPositiveButton(R.string.no_pwd_input_confirm, null).show();
			inputPwdText.setText("");
			inputPwdAgainText.setText("");
			
			return;
		}
		
		Intent intent = new Intent(this, AssetDetailActivity.class);
		startActivity(intent);
	}
}
