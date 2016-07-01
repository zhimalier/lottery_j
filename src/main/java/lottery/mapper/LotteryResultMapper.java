package lottery.mapper;

import java.util.List;
import java.util.Map;

import lottery.model.LotteryResult;

public interface LotteryResultMapper {
	public LotteryResult getLotteryResult(int lotteryid, int userid);

	public void addLotteryResult(int lotteryid, int userid, int prize);

	public List<Map<String,Object>> getDrawedLottery(int lotteryid);

}
