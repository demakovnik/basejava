package com.urise.webapp.util;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainConcurrencyStreams {

    public static void main(String[] args) {
        int[] values = {1, 2, 3, 3, 2, 3, 4};
        List<Integer> sourceList = Arrays.stream(values).boxed().collect(Collectors.toList());
        System.out.println(minValue(values));
        System.out.println(oddOrEven(sourceList));
    }

    private static int minValue(int[] values) {
        AtomicInteger size = new AtomicInteger(values.length - 1);
        return Arrays.stream(values).distinct().sorted().map(value -> (int) (value * Math.pow(10, size.decrementAndGet()))).map(v -> v / 100).sum();
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().flatMapToInt(IntStream::of).sum();
        return integers
                .stream()
                .flatMapToInt(IntStream::of)
                .filter(num -> num % 2 != sum % 2)
                .boxed()
                .collect(Collectors.toList());
    }
}
