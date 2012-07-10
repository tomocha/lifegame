package jp.co.mti.itso.contest.lifegame.hozono_t.logic;

import java.util.ArrayList;
import java.util.List;

import jp.co.mti.itso.contest.lifegame.hozono_t.logic.Const.CellState;

/**
 * ロジッククラス.
 * 
 * @author $Author: mti_user05 $
 * @version $Revision: 5 $
 */
public class LifeGameLogic {

	// セルの数
	private static int NUMBER_X;
	private static int NUMBER_Y;

	/** 現在のセル. */
	Const.CellState[][] mOldCell;
	/** 世代交代後のセル. */
	Const.CellState[][] mNewCell;

	/**
	 * コンストラクタ.
	 * 
	 * @param x
	 *            xセルの数
	 * @param y
	 *            yセルの数
	 */
	public LifeGameLogic(int x, int y) {
		// 1枠大きく作って、実際の盤面は入力のまま作る
		NUMBER_X = x + 2;
		NUMBER_Y = y + 2;
		mOldCell = new Const.CellState[NUMBER_X][NUMBER_Y];
		mNewCell = new Const.CellState[NUMBER_X][NUMBER_Y];
		init();
	}

	/**
	 * init.
	 */
	private void init() {
		for (int x = 0; x < mOldCell.length; x++) {
			for (int y = 0; y < mOldCell[x].length; y++) {
				// 最初は全部DEAD(盤面のハシも全部DEAD)
				mOldCell[x][y] = Const.CellState.DEAD;
			}
		}
		for (int x = 0; x < mNewCell.length; x++) {
			for (int y = 0; y < mNewCell[x].length; y++) {
				// 最初は全部DEAD(盤面のハシも全部DEAD)
				mNewCell[x][y] = Const.CellState.DEAD;
			}
		}
	}

	public void sumpleInit() {
		changeCell(6, 5);
		changeCell(7, 6);
		changeCell(8, 7);
		changeCell(9, 6);
		changeCell(10, 5);
	}

	/**
	 * 指定されたセルが次の世代で生きているかどうかを返すメソッド.
	 * 
	 * @param x
	 *            x座標
	 * @param y
	 *            y座標
	 * @return deadOrAlive 生きているか死んでいるか
	 */
	protected final CellState deadOrAlive(final int x, final int y) {
		CellState state = CellState.DEAD;
		switch (lifeCount(x, y)) {
		case 3: // 自分含めて周り9マスが3つ生きているときは誕生
			state = CellState.ALIVE_G;
			// TODO 赤緑判定
			break;
		case 2: // 2の場合は変わらず
			state = mOldCell[x][y];
			break;
		default: // それ以外は死ぬ
			break;
		}
		return state;
	}

	/**
	 * セルの状態を変更する.
	 * 
	 * @param x
	 *            x
	 * @param y
	 *            y
	 * @param lifeCount
	 *            lifecount
	 * @return 成功:true
	 */
	public final boolean changeState() {
		for (int x = 1; x < mOldCell.length - 1; x++) {
			for (int y = 1; y < mOldCell[x].length - 1; y++) {
				mNewCell[x][y] = deadOrAlive(x, y);
			}
		}
		// 今の世代を入れ替える
		for (int x = 1; x < mOldCell.length - 1; x++) {
			for (int y = 1; y < mOldCell[x].length - 1; y++) {
				mOldCell[x][y] = mNewCell[x][y];
			}
		}
		return true;
	}

	/**
	 * タップ時に指定したセルの状態を変更する.
	 * 
	 * @param x
	 *            x
	 * @param y
	 *            y
	 * @return 成功:true
	 */
	public final boolean changeCell(final int x, final int y) {
		switch (mOldCell[x][y]) {
		case DEAD:
			mOldCell[x][y] = CellState.ALIVE_G;
			break;
		case ALIVE_G:
			mOldCell[x][y] = CellState.ALIVE_R;
			break;
		case ALIVE_R:
			mOldCell[x][y] = CellState.DEAD;
			break;
		default:
			break;
		}
		return false;
	}

	/**
	 * 現世代の指定座標の状態によりintを返す.
	 * 
	 * @param x
	 *            x
	 * @param y
	 *            y
	 * @return int
	 */
	public int getOldCellInt(int x, int y) {
		if (mOldCell[x][y] != Const.CellState.DEAD) {
			return 1;
		}
		return 0;
	}

	/**
	 * 指定セルの周りの生きているセルの数をカウントするメソッド.
	 * 
	 * @param x
	 *            x
	 * @param y
	 *            y
	 * @return
	 */
	protected int lifeCount(int x, int y) {
		return xDetaction(0, x - 1, y - 1, x, y) - getOldCellInt(x, y);
	}

	/**
	 * 周り8セルのxを走査するメソッド.
	 * 
	 * @param lifeCount
	 * @param j
	 * @param k
	 * @param x
	 * @param y
	 * @return
	 */
	private int xDetaction(int lifeCount, final int j, final int k,
	        final int x, final int y) {
		if (j == x + 2) {
			return lifeCount;
		}
		lifeCount += yDetaction(0, j, k, x, y);
		return xDetaction(lifeCount, j + 1, k, x, y);
	}

	/**
	 * 周り8セルのyを走査するメソッド.
	 * 
	 * @param lifeCount
	 * @param j
	 * @param k
	 * @param x
	 * @param y
	 * @return
	 */
	private int yDetaction(int lifeCount, int j, int k, int x, int y) {
		if (k == y + 2) {
			return lifeCount;
		}
		lifeCount += getOldCellInt(j, k);
		return yDetaction(lifeCount, j, k + 1, x, y);
	}

	/**
	 * 新しい世代の盤面を取得する.
	 * 
	 * @return newcell
	 */
	public Const.CellState[][] getNewCell() {
		return mNewCell;
	}

	/**
	 * 現世代の盤面取得.
	 * 
	 * @return oldCell
	 */
	public CellState[][] getOldCell() {
		return mOldCell;
	}

	/**
	 * gridListView用ListViewを返すメソッド.
	 * 
	 * @return list
	 */
	public List<CellState> getList() {
		List<CellState> result = new ArrayList<CellState>();
		for (int x = 1; x < mOldCell.length - 1; x++) {
			for (int y = 1; y < mOldCell[x].length - 1; y++) {
				result.add(mOldCell[x][y]);
			}
		}
		return result;
	}

}
