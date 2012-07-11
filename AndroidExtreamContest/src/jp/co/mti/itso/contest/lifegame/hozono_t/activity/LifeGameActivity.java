package jp.co.mti.itso.contest.lifegame.hozono_t.activity;

import java.util.List;

import jp.co.mti.itso.contest.lifegame.hozono_t.R;
import jp.co.mti.itso.contest.lifegame.hozono_t.logic.Const.CellState;
import jp.co.mti.itso.contest.lifegame.hozono_t.logic.Const.GameState;
import jp.co.mti.itso.contest.lifegame.hozono_t.logic.LifeGameLogic;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class LifeGameActivity extends Activity implements OnClickListener,
        OnItemClickListener {

	private LifeGameLogic mLogic;

	private GridView mGrid;

	private GameState mGameState;

	private Thread mGameThread;
	private Handler mHandler;
	ArrayAdapter<CellState> mAdapter;

	int x = 15;
	int y = 15;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(this);
		mGameState = GameState.PAUSE;

		mLogic = new LifeGameLogic(x, y);
		mGrid = (GridView) findViewById(R.id.lifeGrid);
		mGrid.setNumColumns(x);
		mLogic.changeState();
		mAdapter = new MyAdapter(getApplicationContext(), R.layout.item,
		        mLogic.getList());
		mGrid.setAdapter(mAdapter);
		mGrid.setOnItemClickListener(this);
	}

	public void invalidateView() {
		mLogic.changeState();
		mAdapter = new MyAdapter(getApplicationContext(), R.layout.item,
		        mLogic.getList());
		mGrid.setAdapter(mAdapter);
	}

	@Override
	public void onClick(View arg0) {
		// TODO GameStateによるstop/start
		update();
	}

	public void update() {
		mHandler = new Handler();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					mHandler.post(new Runnable() {
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
			super(context, layoutId, list);
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
				holder.textView
				        .setBackgroundColor(android.graphics.Color.BLACK);
				break;
			case ALIVE_G:
				holder.textView
				        .setBackgroundColor(android.graphics.Color.GREEN);
				break;
			case ALIVE_R:
				holder.textView.setBackgroundColor(android.graphics.Color.RED);
				break;
			default:
				break;
			}
			return convertView;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
	        long id) {
		int changeX = (position / x) + 1;
		int changeY = (position % x) + 1;
		mLogic.changeCell(changeX, changeY);
		mAdapter = new MyAdapter(getApplicationContext(), R.layout.item,
		        mLogic.getList());
		mGrid.setAdapter(mAdapter);
	}
}