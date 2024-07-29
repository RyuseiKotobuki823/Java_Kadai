package org.example;

import java.util.ArrayList;
import java.util.List;

public class PrimeNumber {
    public static void main(String[] args) {
        int numberOfPrimes = 20;
        List<Integer> primes = PrimeNumber.generatePrimes(numberOfPrimes);
        for (int i = 0; i < primes.size(); i++) {
            System.out.println(i + 1 + ":" + primes.get(i));
        }
    }

    //public -> 他のクラスからも呼び出せる
    //static -> クラス名.メソッド名の形で呼び出せる
    //List<Integer> -> 整数のリストをreturnで返すよ
    //generatePrimes -> method名
    //int n -> 整数をinputに取るよ
    public static List<Integer> generatePrimes(int n) {
        List<Integer> primes = new ArrayList<>();
        int count = 0;
        int number = 2;
        while (count < n) {
            if (isPrime(number)) {
                primes.add(number);
                count++;
            }
            number++;
        }
        return primes;
    }

    public static int getPrime(int nth)
    {
        return generatePrimes(nth).get(nth - 1);
    }

    public static boolean isPrime(int number) {
        if(number < 1) {
            throw new IllegalArgumentException("入力された数は正でなければなりません。");
        }
        //1は素数ではない
        if (number == 1) {
            return false;
        }
        for (int i = 2; i < number; i++) {
            //もっと計算効率を上げられるが最もわかりやすい処理を書いておく。
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
