package jp.co.mti.itso.contest.lifegame.hozono_t.activity;

import java.util.List;

import jp.co.mti.itso.contest.lifegame.hozono_t.R;
import jp.co.mti.itso.contest.lifegame.hozono_t.logic.Const.CellState;
import jp.co.mti.itso.contest.lifegame.hozono_t.logic.LifeGameLogic;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class LifeGameActivity extends Activity {

	private LifeGameLogic mLogic;

	private GridView mGrid;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		int x = 10;
		int y = 10;
		mLogic = new LifeGameLogic(x, y);
		mLogic.sumpleInit();
		mGrid = (GridView) findViewById(R.id.lifeGrid);
		mGrid.setNumColumns(x);
		mLogic.changeState();
		ArrayAdapter<CellState> adapter = new MyAdapter(
		        getApplicationContext(), R.layout.item, mLogic.getList());
		mGrid.setAdapter(adapter);

	}

	public void invalidateView() {
		mLogic.changeState();
		ArrayAdapter<CellState> adapter = new MyAdapter(
		        getApplicationContext(), R.layout.item, mLogic.getList());
		mGrid.setAdapter(adapter);
	}

	public void update(View v) {
		final Handler handler = new Handler();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					handler.post(new Runnable() {
						@Override
						public void run() {
							invalidateView();
						}
					});
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	static class ViewHolder {
		TextView textView;
	}

	public class MyAdapter extends ArrayAdapter<CellState> {
		private LayoutInflater inflater;
		private int layoutId;

		public MyAdapter(Context context, int layoutId, List<CellState> list) {
			super(context, 0, list);
			this.inflater = (LayoutInflater) context
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.layoutId = layoutId;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;

			if (convertView == null) {
				convertView = inflater.inflate(layoutId, parent, false);
				holder = new ViewHolder();
				holder.textView = (TextView) convertView
				        .findViewById(R.id.cellText);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			CellState data = getItem(position);
			switch (data) {
			case DEAD:
				holder.textView.setText("■");
				holder.textView.setTextColor(android.graphics.Color.BLACK);
				break;
			case ALIVE_G:
				holder.textView.setText("■");
				holder.textView.setTextColor(android.graphics.Color.GREEN);
				break;
			case ALIVE_R:
				holder.textView.setText("■");
				holder.textView.setTextColor(android.graphics.Color.RED);
				break;
			default:
				break;
			}
			return convertView;
		}
	}
}