import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NumToRMB {
    public static void main(String[] args) throws IOException {
        NumToRMB.start();

    }

    public static void start() throws IOException {
        Money mon = Money.getMoneyInstance();
        double money_value;
        do {
            System.out.print("请输入人民币的小写数值或输入Q退出：");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String inputValue = br.readLine();
            if (inputValue.matches("q|Q")) {
                System.exit(0);
            }
            if (!inputValue.matches("\\d+|(\\d+).(\\d+)")) {
                System.out.println("格式错误，请重新输入！");
                continue;
            }
            money_value = Double.parseDouble(inputValue);
            if (money_value > 9999_9999_9999.00) {
                System.out.println("数值过大，无法计算！");
                continue;
            } else if (money_value < 0.00) {
                System.out.println("数值不能为负数！");
                continue;
            } else if (money_value == 0.0) {
                System.out.println("零元");
            } else {
                mon.setMoneyValue(money_value);
                mon.upperCase();
            }
        } while (true);
    }
}