package com.vorobyev.first_web.warehouse;

import com.vorobyev.first_web.entity.User;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class UserWarehouse {
    private List<User> users = new ArrayList<>();

    private static final UserWarehouse instance = new UserWarehouse();

    private UserWarehouse() {
    }

    public static UserWarehouse getInstance() {
        return instance;
    }

    public int size() {
        return users.size();
    }

    public boolean isEmpty() {
        return users.isEmpty();
    }

    public boolean contains(Object o) {
        return users.contains(o);
    }

    public Iterator<User> iterator() {
        return users.iterator();
    }

    public Object[] toArray() {
        return users.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return users.toArray(a);
    }

    public boolean add(User user) {
        return users.add(user);
    }

    public boolean remove(Object o) {
        return users.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return users.containsAll(c);
    }

    public boolean addAll(Collection<? extends User> c) {
        return users.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends User> c) {
        return users.addAll(index, c);
    }

    public boolean removeAll(Collection<?> c) {
        return users.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return users.retainAll(c);
    }

    public void replaceAll(UnaryOperator<User> operator) {
        users.replaceAll(operator);
    }

    public void sort(Comparator<? super User> c) {
        users.sort(c);
    }

    public void clear() {
        users.clear();
    }

    public User get(int index) {
        return users.get(index);
    }

    public User set(int index, User element) {
        return users.set(index, element);
    }

    public void add(int index, User element) {
        users.add(index, element);
    }

    public User remove(int index) {
        return users.remove(index);
    }

    public int indexOf(Object o) {
        return users.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return users.lastIndexOf(o);
    }

    public ListIterator<User> listIterator() {
        return users.listIterator();
    }

    public ListIterator<User> listIterator(int index) {
        return users.listIterator(index);
    }

    public List<User> subList(int fromIndex, int toIndex) {
        return users.subList(fromIndex, toIndex);
    }

    public Spliterator<User> spliterator() {
        return users.spliterator();
    }

    public static <E> List<E> copyOf(Collection<? extends E> coll) {
        return List.copyOf(coll);
    }

    public <T> T[] toArray(IntFunction<T[]> generator) {
        return users.toArray(generator);
    }

    public boolean removeIf(Predicate<? super User> filter) {
        return users.removeIf(filter);
    }

    public Stream<User> stream() {
        return users.stream();
    }

    public Stream<User> parallelStream() {
        return users.parallelStream();
    }

    public void forEach(Consumer<? super User> action) {
        users.forEach(action);
    }
}
