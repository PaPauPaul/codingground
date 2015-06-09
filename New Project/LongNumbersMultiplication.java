import java.util.Arrays;

/**
 * Created by Павел on 08.06.2015.
 */
public class LongNumbersMultiplication
{
    public static void main(String[] args) {
        String number1 = "123456789123456789123456789";
        String[] array = new String[arrayElementsCount(number1)];
        array = stringToArrayOfStringGroups(number1);

        String number2 = "12345671234567899";
        String result = multiplication(array, number2);

        System.out.println(result);

    }

    public static int arrayElementsCount (String factor) {
        // добавляем 0 спереди, если длина числа не кратна 3, возвращаем длину строки, чтобы задать массив длинной (длина строки)/3
        while (factor.length() % 3 != 0) {
            factor = "0" + factor;
        }
        return factor.length() / 3;
    }

    // добавляем 0 спереди, если длина строки с числом не кратна 3
    // Метод разбивает строку на группы из 3-х чисел, 1 группа = 1 элемент в массиве Array[]:
    // Пример: i = 123456789 = 123*1000*1000+456*1000+789*1
    public static String[] stringToArrayOfStringGroups(String factor){
       // добавляем 0 спереди, если длина числа не кратна 3
        while(factor.length()%3 != 0){
            factor = "0" + factor;
        }

        // генерируем массив, длинна которого кратна 3, т.к. добавили незначащие 0, 1 элемент = 1 группа из 3-х чисел
        String[] arr = new String[arrayElementsCount(factor)];
        for (int i = factor.length()-1; i >= 0 ; i--) {
            if ((i+1)%3 == 0){
                String element = "" + factor.charAt(i-2)+ factor.charAt(i-1)+factor.charAt(i);
                int elementNumber = i/3;
                arr[elementNumber] = element;
            }
        }

        // переворачиваем полученный массив, чтобы элементы в нём содержали числа в порядке возрастания их порядка (0 = [123], 1 = [456000], 2 = [789000000],...)
        for (int i = 0; i < arr.length/2; i++) {
            String b;
            b = arr[i];
            arr[i] = arr[(arr.length-1)-i];
            arr[(arr.length-1)-i] = b;
        }
        return arr;
    }
    // метод, который добавляет справа порядковые 0, по 3 ноля для каждого следующего элемента массива после 0-элемента
    public static String addExp3 (String s, int i){
        // i - это степень числа, в которую нужно возвести 1000, получается добавление по 3/6/9 и т.д. нолей...
        double d = Long.parseLong(s)*Math.pow(1000, i);
        long arrayElement = (long) d;
        return String.valueOf(arrayElement);
        // чтобы понять, как работает, посмотреть:
//        for (int i = 0; i <arr.length ; i++)
//        {
//            double d = Integer.parseInt(arr[i])* Math.pow(1000, i);
//            long arrayElement = (long) d;
//            arr[i]=String.valueOf(arrayElement);
//        }
    }
    // метод сложения длинных чисел
    public static String longNumbersComposing (String sn1, String sn2){
        String result = "";
//        if (sn1.equals(""))
//        {
//            sn1 = "0";
//        }
//        if (sn2.equals(""))
//        {
//            sn2 = "0";
//        }

        // проверка на количество разрядов в числах и добавление незначащих 0, если они не совпадают
        int len = sn1.length() - sn2.length();
        if (len != 0 && len > 0) {
            for (int i = 1; i <= len; i++) {
                sn2 = "0" + sn2;
            }
        } else if (len != 0 && len < 0) {
            for (int i = 1; i <= Math.abs(len); i++) {
                sn1 = "0" + sn1;
            }
        }

        // поразрядное сложение

        String sn1CharToStr; // эта переменная нужна для того, чтобы преобразовать элемент строки к символу...
        String sn2CharToStr; // ...чтобы потом его кастовать к числу
        int sum; // сюда помним сумму
        boolean overFlow = false; // флаг переполнения разряда
        for (int i = sn1.length() - 1; i >= 0; i--) {
            // Берём последовательно каждый символ из первого числа
            sn1CharToStr = "" + sn1.charAt(i);
            int n1i = Integer.parseInt(sn1CharToStr);
            // Берём последовательно каждый символ из второго числа
            sn2CharToStr = "" + sn2.charAt(i);
            int n2i = Integer.parseInt(sn2CharToStr);
            if (overFlow) {
                n1i = n1i + 1;
                overFlow = false;
            }
            sum = n1i + n2i;
            if (sum >= 10 && i == 0){
                sum = sum - 10;
                overFlow = true;
                result = "1" + Integer.toString(sum) + result;
            } else if (sum >= 10) {
                sum = sum - 10;
                overFlow = true;
                result = Integer.toString(sum) + result;
            } else {
                result = Integer.toString(sum) + result;
            }
        }
        return  result;
    }

    // метод, который выполняет последовательное умножение элементов массива arr[] на каждый символ строки number2
    public static String multiplication(String array[], String secondFactor) {
        // создаем массив buffer[], копию array[]
        String[] buffer = array.clone();
        // создаем массив result[], в который записываем произведение суммированных элеметов массива arr[] на каждый элемент строки number2
        // длина result[] = длине строки number2
        String[] result = new String[secondFactor.length()];

        for (int i = secondFactor.length()-1; i >= 0; i--) {
            // elem1 - переменная, которая содержит число из строки number2 arr
            String elem1 = "" + secondFactor.charAt(i);
                double dElem1 = Integer.parseInt(elem1) * Math.pow(10, (secondFactor.length()-1) -i);
                long iElem1 = (long) dElem1;
                elem1 = String.valueOf(iElem1);

            for (int j = 0; j < array.length; j++) {
                if (j == 0) {
                    // для первого элемета производим умножение на elem
                    long elem2 = Long.parseLong(array[j]) * Long.parseLong(elem1);
                    // кастинг полученного произведения к строке
                    buffer[j] = String.valueOf(elem2);
                } else {
                    // выполняем умножение для всех последующих элементов массива array
                    long elem2 = Long.parseLong(array[j]) * Long.parseLong(elem1);
                    // кастинг полученного произведения к строке
                    buffer[j] = String.valueOf(elem2);
                    // сложение предыдущего элемента и текущего, к текущему применяем метод addExp3
                    buffer[j] = longNumbersComposing(buffer[j - 1], addExp3(buffer[j], j));
                }
            }
            // записываем результат сложения в массив result[]
            result[(secondFactor.length()-1) -i] = buffer[buffer.length - 1];
        }
        // если массив result[] будет содеоржать 1 элемент (если умножать на число, содержащее только порядок едениц), то он и будет являться искомой суммой
        if (result.length == 1) {
            return result[0];
        } else {
            for (int i = 1; i < result.length; i++) {
                result[i] = longNumbersComposing(result[i - 1], result[i]);
            }
            return result[result.length - 1];
        }
    }

}