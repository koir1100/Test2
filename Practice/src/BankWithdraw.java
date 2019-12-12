import java.util.Arrays;

public class BankWithdraw {
	int flagUnit = 0;

	String hangulConvertMoney(int temp, int unitIndex) {
		String[] semiUnit = { "", "��", "��", "��", "õ" };
		int index = (temp % 10);
		String[] hangul = { "��", "��", "��", "��", "��", "��", "��", "ĥ", "��", "��" };
		if (unitIndex < flagUnit)
			flagUnit = 0;
		flagUnit++;
		String result = semiUnit[flagUnit] + hangul[index];
		result = result.replaceAll("[õ�����]��", "");
		result = result.replaceAll("[õ�����]��", semiUnit[flagUnit]);
		result = result.replaceAll("��[�̻�����ĥ�ȱ�]", hangul[index]);
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
			String rawMoney = money[i]; // ���ڷθ� ������ ���� �迭�� ���Ҹ� �ϳ� ������ ��
			String convert = ""; // �� �ڸ��� ��� �����ϱ� ���� �� ���ڿ� ���� �ʱ�ȭ
			for (int j = rawMoney.length() - 1, k = 0; j >= 0; j--, k++) {
				// ���� �迭�� ���̿��� 1�� �� ���� �ʱ갪���� �Ͽ� �� ���̰� 0�� �� ������ �ݺ��� ����
				// ���� k�� ���� �ڸ����� �ǹ��� : 10^k -> (k=0, ���� �ڸ�), (k=1, ���� �ڸ�), (k=2, ���� �ڸ�), ��
				char temp = rawMoney.charAt(j);
				convert += temp;
				if ((k + 1) % 3 == 0)
					if (j - 1 != -1)
						convert += comma;
			}
			convertMoney[i] = bank.reverseString(convert) + "��";
		}
		System.out.println(Arrays.asList(convertMoney));

		String[] unit = { "��", "��", "��" };
		for (int i = 0; i < convertMoney.length; i++) {
			String temp = convertMoney[i];
			String convert = "��";
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
					convert = convert.replaceAll("[���︸]\\s", "");
				}
			}
			convertMoney[i] = bank.reverseString(convert);
		}
		System.out.println(Arrays.asList(convertMoney));
	}
}