package lottery.model;

import java.io.Serializable;

public class LotteryResult implements Serializable{

	private static final long serialVersionUID = 316508247411530434L;
	private int id;
	private int lotteryid;
	private int userid;
	private int prize;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(int lotteryid) {
		this.lotteryid = lotteryid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getPrize() {
		return prize;
	}

	public void setPrize(int prize) {
		this.prize = prize;
	}
}
