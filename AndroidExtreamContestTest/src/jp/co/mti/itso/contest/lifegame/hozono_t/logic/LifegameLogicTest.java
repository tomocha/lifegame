package jp.co.mti.itso.contest.lifegame.hozono_t.logic;

import java.util.List;

import junit.framework.TestCase;

public class LifegameLogicTest extends TestCase {

    LifeGameLogic mLogic;

    protected void setUp() throws Exception {
        super.setUp();
        // 準備
        int mX = 5;
        int mY = 5;
        mLogic = new LifeGameLogic(mX, mY);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * 指定したx,y座標のセルが、次の世代で生きるかどうかの判定メソッドのテスト.<br>
     */
    public void testDeadOrAlive() {
        // 実行確認
        assertEquals(Const.CellState.DEAD, mLogic.deadOrAlive(1, 1));
    }

    /**
     * クリックしたら状態が変わるメソッドのテスト. 1クリック→ALIVE_G(緑). 2クリック→ALIVE_R(赤).
     */
    public void testChangeCell() {
        // 実行
        mLogic.changeCell(1, 1);

        // 確認
        assertTrue(mLogic.getOldCell()[1][1] == Const.CellState.ALIVE_G);

        // 実行
        mLogic.changeCell(1, 1); // 2回押したらRになる
        // 確認
        assertTrue(mLogic.getOldCell()[1][1] == Const.CellState.ALIVE_R);

        // 実行
        mLogic.changeCell(1, 1);// 3回押すとDEAD;
        // 確認
        assertTrue(mLogic.getOldCell()[1][1] == Const.CellState.DEAD);
    }

    /**
     * セルの生き死にでintを返すメソッドのテスト.
     */
    public void testGetCellInt() {
        // 準備
        mLogic.changeCell(1, 2);
        // 確認
        assertEquals(0, mLogic.getOldCellInt(0, 0));
        assertEquals(1, mLogic.getOldCellInt(1, 2));

    }

    /**
     * 指定セルの周りの生きているセルの数をカウントするメソッドのテスト.
     */
    public void testLifeCount() {
        // 準備
        mLogic.changeCell(1, 1);
        mLogic.changeCell(1, 2);
        mLogic.changeCell(1, 3);

        // 確認
        assertEquals(2, mLogic.lifeCount(1, 2));
    }

    /**
     * gridListViewのためにListViewを返すメソッドのテスト.
     */
    public void testGetList() {
        // 準備
        List<Const.CellState> list = mLogic.getList();

        // 確認
        assertEquals(25, list.size());
    }
}