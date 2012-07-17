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

public class TopActivity extends Activity implements OnClickListener {

	Intent intent;

	EditText horizon, vertical;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.top);

		horizon = (EditText) findViewById(R.id.editHori);
		vertical = (EditText) findViewById(R.id.editVer);

		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		int yCellLength = 15, xCellLength = 15;
		if (!(horizon.getText().length() == 0)
		        || !(vertical.getText().length() == 0)) {
			yCellLength = Integer.parseInt(horizon.getText().toString());
			xCellLength = Integer.parseInt(vertical.getText().toString());
		}
		intent = new Intent(TopActivity.this, LifeGameActivity.class);
		intent.putExtra(Const.Xkey, xCellLength);
		intent.putExtra(Const.Ykey, yCellLength);

		startActivity(intent);
	}
}
