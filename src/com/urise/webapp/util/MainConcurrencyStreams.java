package com.urise.webapp.util;


import java.util.Arrays;
import java.util.List;

import java.util.stream.Collectors;


public class MainConcurrencyStreams {

    public static void main(String[] args) {
        int[] values = {3,3,2,3,1};
        List<Integer> sourceList = Arrays.stream(values).boxed().collect(Collectors.toList());
        System.out.println(minValue(values));
        System.out.println(oddOrEven(sourceList));
    }


    private static int minValue(int[] values) {
        return Arrays.stream(values).
                distinct().
                sorted().
                reduce(0, (num1, num2) -> num1 * 10 + num2);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().reduce(0, (num1, num2) -> num1 + num2);
        return integers
                .stream()
                .filter(num -> num % 2 != sum % 2)
                .collect(Collectors.toList());
    }
}
