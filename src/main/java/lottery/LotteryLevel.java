package lottery;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


public class LotteryLevel {
	private int userid;
	private int prize;

	public LotteryLevel() {

	}

	public LotteryLevel(int userid, int prize) {
		this.userid = userid;
		this.prize = prize;
	}

	public int getUserid() {
		return userid;
	}

	public int getPrize() {
		return prize;
	}
}
