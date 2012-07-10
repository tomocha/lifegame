package jp.co.mti.itso.contest.lifegame.hozono_t.logic;

/**
 * グローバル置き場.
 * 
 * @author $Author: mti_user05 $
 * @version $Revision: 9 $
 */
public class Const {

	/**
	 * セルの状態を表すenum.
	 * 
	 * @author $Author: mti_user05 $
	 * @version $Revision: 9 $
	 */
	public enum CellState {
		DEAD, ALIVE_G, ALIVE_R
	}

	/** 盤面の状態を表すenum. */
	public enum GameState {
		DOING, PAUSE
	}

	public static final String Xkey = "xkey";
	public static final String Ykey = "ykey";

}
