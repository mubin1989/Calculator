import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass {
    public static void main(String[] args) {
        int formatType = 0;
        String result;
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Input:");
        String expression = myObj.nextLine();  // Read user input
        formatType=checkFormat(expression);
        if (formatType==1 || formatType==2){
            System.out.println("Output: ");
            result = calc(expression, formatType);
            System.out.println(result);
        }else {
            System.out.println("Output: ");
            System.out.println("Не правильный формат выражения!");
        }
    }

    //Функция для проверки формат выражения - Арабский формат;    - Римский формат;
    static int checkFormat(String input){
        int result;     // 0 - Не правильный формат выражения;  1 - Арабский формат;    2 - Римский формат
        boolean resultSimple;
        boolean resultRoman;
        //Паттерн арифматеческий выражения с арабскими цифрами
        resultSimple = input.matches("\\d{1,}(\\+|\\-|\\*|\\/)\\d{1,}");
        //Паттерн арифматеческий выражения с римскими цифрами
        resultRoman = input.matches("[IVX]{1,}(\\+|\\-|\\*|\\/ )[IVX]{1,}");
        if (resultSimple) {
            result = 1;
        } else if (resultRoman) {
            result = 2;
        } else {
            result = 0;
        }
        return result;
    }
    public static int getArabian(char roman){
        if('I' == roman) return 1;
        else if('V' == roman) return 5;
        else if('X' == roman) return 10;
        else if('L' == roman) return 50;
        else if('C' == roman) return 100;
        else if('D' == roman) return 500;
        else if('M' == roman) return 1000;
        return 0;

    }

    //Функция переобразования из римский в арабский
    public static int romanToInt(String s) {
        int end = s.length()-1;
        char[] arr = s.toCharArray();
        int arabian;
        int result = getArabian(arr[end]);
        for (int i = end-1; i >=0; i--) {
            arabian = getArabian(arr[i]);

            if (arabian < getArabian(arr[i + 1])) {
                result -= arabian;
            } else {
                result += arabian;
            }
        }
        return result;
    }

    //Функция переобразования из арабский в римский
    public static String intToRoman(int number) {
        int[] roman_value_list = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] roman_char_list = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < roman_value_list.length; i += 1) {
            while (number >= roman_value_list[i]){
                number -= roman_value_list[i];
                res.append(roman_char_list[i]);
            }
        }
        return res.toString();
    }

    public static String calc(String input, int formatType){
        int i;
        int result = 0;
        String StrResult = "";
        boolean allowContinue = true;
        String ArabicNumPattern = "(\\d{1,}|\\+|\\-|\\*|\\/)";
        String RomanNumPattern = "[IVX]{1,}|(\\+|\\-|\\*|\\/ )|[IVX]{1,}";
        int Number1 = 0;
        int Number2 = 0;
        String MathAction = "";
        if (formatType==1) {
            Pattern pattern = Pattern.compile(ArabicNumPattern);
            Matcher matcher = pattern.matcher(input);
            i = 1;
            while (matcher.find()) {
                if (i == 1) {
                    Number1 = Integer.parseInt(matcher.group());
                }
                if (i == 2) {
                    MathAction = matcher.group();
                }
                if (i == 3) {
                    Number2 = Integer.parseInt(matcher.group());
                }
                i++;
            }
        }

        if (formatType==2) {
            Pattern pattern = Pattern.compile(RomanNumPattern);
            Matcher matcher = pattern.matcher(input);
            i = 1;
            while (matcher.find()) {
                if (i==1){
                    Number1=romanToInt(matcher.group());
                }
                if (i == 2) {
                    MathAction = matcher.group();
                }
                if (i == 3) {
                    Number2=romanToInt(matcher.group());
                }
                i++;
            }
        }
        if (Number1<=10 && Number2<=10) {
            switch (MathAction) {
                case "+":
                    result = Number1 + Number2;
                    break;
                case "-":
                    result = Number1 - Number2;
                    break;
                case "*":
                    result = Number1 * Number2;
                    break;
                case "/":
                    result = Number1 / Number2;
                    break;
            }

        }else{
            allowContinue = false;
            StrResult = "Калькулятор принимать на вход числа от 1 до 10(включительно)!";
        }

        if (allowContinue){
            if (formatType==1){
                StrResult = String.valueOf(result);
            }
            if (formatType==2 && result>0){
                StrResult = intToRoman(result);
            }
            if (formatType==2 && result<=0){
                StrResult = "Римское число не может быть ноль или минусовым!";
            }
        }
        return StrResult;
    }
}
