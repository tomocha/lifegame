package jp.co.mti.itso.contest.lifegame.hozono_t.activity;

import jp.co.mti.itso.contest.lifegame.hozono_t.logic.Const;
import jp.co.mti.itso.contest.lifegame.hozono_t.logic.Const.CellState;
import jp.co.mti.itso.contest.lifegame.hozono_t.logic.Const.GameState;
import jp.co.mti.itso.contest.lifegame.hozono_t.logic.LifeGameLogic;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LifeGameActivity extends Activity implements OnClickListener {

	private LifeGameLogic mLogic;

	private GameState mGameState;

	private Handler mHandler;

	private Button mStartBtn;
	int x;
	int y;

	private int mCellPixel;
	private TextView[][] mCellList;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// intentからxy取得処理
		Intent intent = getIntent();
		x = intent.getIntExtra(Const.Xkey, 15);
		y = intent.getIntExtra(Const.Ykey, 15);

		// 画面に合うように1cellを計算する
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Display disp = wm.getDefaultDisplay();
		int cellWidth = disp.getWidth() / y - 1;
		int cellHeight = (disp.getHeight() - 60) / x - 1;
		if (cellWidth > cellHeight) {
			mCellPixel = cellHeight;
		} else {
			mCellPixel = cellWidth;
		}

		mLogic = new LifeGameLogic(x, y);
		mGameState = GameState.PAUSE;
		mCellList = new TextView[x][y];

		// レイアウトを作る
		LinearLayout parentLayout = new LinearLayout(getApplicationContext());
		parentLayout.setOrientation(LinearLayout.VERTICAL);
		parentLayout.setBackgroundColor(android.graphics.Color.WHITE);

		// スタートボタン
		mStartBtn = new Button(getApplicationContext());
		mStartBtn.setOnClickListener(this);
		mStartBtn.setText("START!");
		mStartBtn.setTag("start_stopBtn");
		parentLayout.addView(mStartBtn);

		// 格セルの作成
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
		        LinearLayout.LayoutParams.WRAP_CONTENT,
		        LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(1, 0, 0, 0);
		for (int i = 0; i < x; i++) {
			LinearLayout xLayout = new LinearLayout(getApplicationContext());
			xLayout.setPadding(0, 1, 0, 0);
			for (int j = 0; j < y; j++) {
				final int xCell = i + 1;
				final int yCell = j + 1;
				TextView cell = new TextView(getApplicationContext());
				cell.setWidth(mCellPixel);
				cell.setHeight(mCellPixel);
				cell.setLayoutParams(layoutParams);
				cell.setBackgroundColor(android.graphics.Color.BLACK);
				cell.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						mLogic.changeCell(xCell, yCell);
						Log.v("changeCell", "[" + Integer.toString(xCell) + "]"
						        + "[" + Integer.toString(yCell) + "] is "
						        + mLogic.getOldCell()[xCell][yCell]);
						switch (mLogic.getOldCell()[xCell][yCell]) {
						case ALIVE_G:
							v.setBackgroundColor(android.graphics.Color.GREEN);
							break;
						case ALIVE_R:
							v.setBackgroundColor(android.graphics.Color.RED);
							break;
						default:
							v.setBackgroundColor(android.graphics.Color.BLACK);
							break;
						}
					}
				});
				xLayout.addView(cell);
				mCellList[i][j] = cell;
			}
			parentLayout.addView(xLayout);
		}

		setContentView(parentLayout);
	}

	/**
	 * 表示を更新する
	 */
	public void invalidateView() {
		CellState[][] state = mLogic.getNewCell();
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				int color = android.graphics.Color.BLACK;
				switch (state[i + 1][j + 1]) {
				case ALIVE_G:
					color = android.graphics.Color.GREEN;
					break;
				case ALIVE_R:
					color = android.graphics.Color.RED;
					break;
				default:
					break;
				}
				mCellList[i][j].setBackgroundColor(color);
			}
		}
	}

	@Override
	public void onClick(View v) {
		if (!v.getTag().equals("start_stopBtn")) {
			return;
			// 何もしない
		}
		if (mGameState == GameState.PAUSE) {
			update();
			mGameState = GameState.DOING;
			mStartBtn.setText("STOP");
		} else {
			mGameState = GameState.PAUSE;
			mStartBtn.setText("START");
		}
	}

	/** update用のハンドラー起動 */
	public void update() {
		mHandler = new Handler();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (mGameState == GameState.DOING) {
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
}