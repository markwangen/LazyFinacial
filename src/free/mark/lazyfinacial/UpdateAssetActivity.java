package free.mark.lazyfinacial;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class UpdateAssetActivity extends Activity {
	OnClickListener listen1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);
		final LinearLayout layout2 = new LinearLayout(this);
		layout2.setOrientation(LinearLayout.VERTICAL);
		setContentView(layout2);
		
		Button btn1 = new Button(this);
		btn1.setText("Button1");
		layout2.addView(btn1);
		
		Button btn2 = new Button(this);
		btn2.setText("Button2");
//		layout2.addView(btn2);
		
		TextView text1 = new TextView(this);
		text1.setText("meme");
		
		Spinner sp = new Spinner(this);
		ArrayAdapter<String> _Adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, new String[] {"aa", "bb"});
		sp.setAdapter(_Adapter);
		
		EditText edit1 = new EditText(this);
		edit1.setWidth(40);
		
		final TableLayout table = new TableLayout(this);
		TableRow row1 = new TableRow(this);
		table.addView(row1);
		row1.addView(btn2);
		row1.addView(text1);
		row1.addView(edit1);
		row1.addView(sp);
		layout2.addView(table);
		
		btn1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setTitle("点击button1 ");
				Button btn3 = new Button(v.getContext());
				layout2.addView(btn3);
				btn3.setText("Button3");

			}
		});
	}
}
