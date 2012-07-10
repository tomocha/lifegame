package jp.co.mti.itso.contest.lifegame.hozono_t.activity;

import jp.co.mti.itso.contest.lifegame.hozono_t.R;
import jp.co.mti.itso.contest.lifegame.hozono_t.logic.Const;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class TopActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.top);

		EditText horizon = (EditText) findViewById(R.id.editHori);
		EditText vertical = (EditText) findViewById(R.id.editVer);

		CharSequence strX = horizon.getText();
		CharSequence strY = vertical.getText();

		final Intent intent = new Intent(TopActivity.this,
		        LifeGameActivity.class);
		intent.putExtra(Const.Xkey, strX);
		intent.putExtra(Const.Ykey, strY);

		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				startActivity(intent);
			}
		});
	}
}
