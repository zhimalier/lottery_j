package lottery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lottery.mapper.LotteryResultMapper;
import lottery.model.LotteryResult;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.text.SimpleDateFormat;

@RestController
public class LotteryController {

	@Autowired
	private LotteryResultMapper lotteryResultMapper;

	@RequestMapping(value = "/lottery", method = RequestMethod.GET)
	public LotteryLevel getLotteryLevel() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String strDate = sdf.format(date);
		int lotteryid = Integer.parseInt(strDate);
		// System.out.println(lotteryid);

		Random rand = new Random();
		int userid = rand.nextInt(100000);
		// System.out.println(userid);

		// 如果该用户已经抽过该次奖，则本次无效
		LotteryResult lotteryResult = lotteryResultMapper.getLotteryResult(lotteryid, userid);
		// System.out.println(lotteryResult);
		if (lotteryResult != null) {
			return new LotteryLevel(userid, -1);
		}

		// 查询出已抽出奖项列表
		List list = lotteryResultMapper.getDrawedLottery(lotteryid);
		HashMap drawedLottery = new HashMap();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				HashMap map = (HashMap) list.get(i);
				int prize = (Integer) map.get("prize");
				int count = ((Long) map.get("count")).intValue();
				drawedLottery.put(prize, count);
				// System.out.println(map);
				// System.out.println(prize);
				// System.out.println(count);
			}
		}
		//System.out.println(drawedLottery);

		int[] lotteryList = { 1, 1, 2, 3, 10000 };
		if (drawedLottery.size() > 0) {
			for (int i = 0; i < lotteryList.length; i++) {
				if (drawedLottery.get(i) != null) {
					int count = (Integer) drawedLottery.get(i);
					if (lotteryList[i] >= count) {
						lotteryList[i] -= count;
					} else {
						lotteryList[i] = 0;
					}
				}
			}
		}

//		System.out.println("lotteryList:" + lotteryList);
//		for (int i = 0; i < lotteryList.length; i++) {
//			System.out.println(lotteryList[i]);
//		}

		int sum = 0;
		for (int i = 0; i < lotteryList.length; i++) {
			sum += lotteryList[i];
		}
		int prize = 4;

		if (sum <= 0) { // 奖项全部抽完了
			lotteryResultMapper.addLotteryResult(lotteryid, userid, prize);
			return new LotteryLevel(userid, prize);
		}

		int randNum = rand.nextInt(sum); // 第2次随机数作为抽奖数

		for (int i = 0; i < lotteryList.length; i++) {
			if (randNum < lotteryList[i]) {
				prize = i;
				break;
			} else {
				randNum -= lotteryList[i];
			}
		}

		lotteryResultMapper.addLotteryResult(lotteryid, userid, prize);

		return new LotteryLevel(userid, prize);
	}
}
