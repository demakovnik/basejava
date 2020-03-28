package com.urise.webapp.util;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Comparator.*;

public class MainConcurrencyStreams {

    public static void main(String[] args) {
        int[] values = {1, 2, 3, 3, 2, 3};
        List<Integer> sourceList = Arrays.stream(values).boxed().collect(Collectors.toList());
        System.out.println(minValue(values));
        System.out.println(oddOrEven(sourceList));
    }

    private static int minValue(int[] values) {
        int stepen = 1;
        int sum = 0;
        Integer[] array = IntStream.of(values).distinct().boxed().sorted(reverseOrder()).toArray(Integer[]::new);
        for (int i = 0; i < array.length; i++) {
            sum += stepen * array[i];
            stepen *= 10;
        }
        return sum;
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().flatMapToInt(IntStream::of).sum();
        return sum % 2 == 0 ? integerList(integers, value -> value % 2 != 0) :
                integerList(integers, value -> value % 2 == 0);
    }

    private static List<Integer> integerList(List<Integer> integers, IntPredicate predicate) {
        return integers
                .stream()
                .flatMapToInt(IntStream::of)
                .filter(predicate)
                .boxed()
                .collect(Collectors.toList());
    }
}
