package com.kneelawk.klinesjava.utils;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class CollectionUtils {
    public static <E> void replaceAll(Collection<E> collection, UnaryOperator<E> operator) {
        List<E> newElements = Lists.newArrayList();

        for (Iterator<E> it = collection.iterator(); it.hasNext(); ) {
            E element = it.next();

            it.remove();

            newElements.add(operator.apply(element));
        }

        collection.addAll(newElements);
    }

    public static <E> void replaceIf(Collection<E> collection, Predicate<? super E> condition,
                                     UnaryOperator<E> operator) {
        List<E> newElements = Lists.newArrayList();

        for (Iterator<E> it = collection.iterator(); it.hasNext(); ) {
            E element = it.next();

            if (condition.test(element)) {
                it.remove();
                newElements.add(operator.apply(element));
            }
        }

        collection.addAll(newElements);
    }
}
