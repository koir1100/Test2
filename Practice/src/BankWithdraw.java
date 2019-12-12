import java.util.Arrays;

public class BankWithdraw {
	int flagUnit = 0;

	String hangulConvertMoney(int temp, int unitIndex) {
		String[] semiUnit = { "", "일", "십", "백", "천" };
		int index = (temp % 10);
		String[] hangul = { "영", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구" };
		if (unitIndex < flagUnit)
			flagUnit = 0;
		flagUnit++;
		String result = semiUnit[flagUnit] + hangul[index];
		result = result.replaceAll("[천백십일]영", "");
		result = result.replaceAll("[천백십일]일", semiUnit[flagUnit]);
		result = result.replaceAll("일[이삼사오육칠팔구]", hangul[index]);
		return result;
	}

	String reverseString(String convert) {
		String reverseMoney = "";
		for (int j = convert.length() - 1; j >= 0; j--) {
			char temp = convert.charAt(j);
			reverseMoney += temp;
		}
		return reverseMoney;
	}

	public static void main(String[] args) {
		BankWithdraw bank = new BankWithdraw();
		String[] money = { "1", "9005", "80270", "111111", "340205", "6004820", "1234567890", "7001820900710", "100000000000000" };
		String[] convertMoney = new String[money.length];
		final char comma = ',';

		for (int i = 0; i < money.length; i++) {
			String rawMoney = money[i]; // 숫자로만 구성된 숫자 배열의 원소를 하나 가지고 옴
			String convert = ""; // 세 자리씩 끊어서 저장하기 위한 빈 문자열 변수 초기화
			for (int j = rawMoney.length() - 1, k = 0; j >= 0; j--, k++) {
				// 숫자 배열의 길이에서 1을 뺀 값을 초깃값으로 하여 그 길이가 0이 될 때까지 반복문 수행
				// 정수 k는 숫자 자릿수를 의미함 : 10^k -> (k=0, 일의 자리), (k=1, 십의 자리), (k=2, 백의 자리), …
				char temp = rawMoney.charAt(j);
				convert += temp;
				if ((k + 1) % 3 == 0)
					if (j - 1 != -1)
						convert += comma;
			}
			convertMoney[i] = bank.reverseString(convert) + "원";
		}
		System.out.println(Arrays.asList(convertMoney));

		String[] unit = { "만", "억", "조" };
		for (int i = 0; i < convertMoney.length; i++) {
			String temp = convertMoney[i];
			String convert = "원";
			for (int j = temp.length() - 2, k = 0; j >= 0; j--, k++) {
				int semiUnitIndex = k % 5;
				int unitIndex = (k / 5) - 1;
				if (temp.charAt(j) == ',') {
					if (semiUnitIndex == 0) {
						convert = convert + " ";
						convert = convert + unit[unitIndex];
					}
					continue;
				} else {
					int tempNum = Integer.parseInt(String.valueOf(temp.charAt(j)));
					if (semiUnitIndex == 0 && k != 0) {
						convert = convert + " ";
						convert = convert + unit[unitIndex];
					}
					convert += bank.hangulConvertMoney(tempNum, semiUnitIndex);
					convert = convert.replaceAll("[조억만]\\s", "");
				}
			}
			convertMoney[i] = bank.reverseString(convert);
		}
		System.out.println(Arrays.asList(convertMoney));
	}
}