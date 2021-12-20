package org.shield.mybatis.functional;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;
import tk.mybatis.mapper.entity.Condition;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
public class Conditional<T> {

    private boolean isEmpty;
    private T value;
    private Condition condition;

    private static final Conditional<?> EMPTY = new Conditional<>();

    private Conditional() {
        this.value = null;
    }

    public Conditional(Condition condition) {
        isEmpty = condition.getOredCriteria().isEmpty() || !condition.getOredCriteria().get(0).isValid();
        this.condition = condition;
    }

    public Conditional(Condition condition, T value) {
        isEmpty = condition.getOredCriteria().isEmpty() || !condition.getOredCriteria().get(0).isValid();
        this.condition = condition;
        this.value = value;
    }

    public static <T> Conditional<T> of(Condition condition) {
        return new Conditional<>(condition);
    }

    public static <T> Conditional<T> of(Condition condition, T value) {
        return new Conditional<>(condition, value);
    }

    public boolean isPresent() {
        return !isEmpty;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public static<T> Conditional<T> empty() {
        @SuppressWarnings("unchecked")
        Conditional<T> t = (Conditional<T>) EMPTY;
        return t;
    }

    /**
     * 使用指定的方法执行语句，Condition 为空时，清除criteria中的条件
     *
     * @param supplier
     * @return
     */
    public Conditional<T> apply(Function<Condition, T> function) {
        if (isEmpty && !condition.getOredCriteria().isEmpty()) {
            condition.getOredCriteria().clear();
        }
        value = function.apply(condition);
        return this;
    }

    public <U> Conditional<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent()) {
            return empty();
        } else {
            return Conditional.of(condition, mapper.apply(value));
        }
    }

    /**
     * If a value is present, returns a sequential {@link Stream} containing
     * only that value, otherwise returns an empty {@code Stream}.
     *
     * @apiNote
     * This method can be used to transform a {@code Stream} of optional
     * elements to a {@code Stream} of present value elements:
     * <pre>{@code
     *     Stream<Optional<T>> os = ..
     *     Stream<T> s = os.flatMap(Optional::stream)
     * }</pre>
     *
     * @return the optional value as a {@code Stream}
     * @since 9
     */
    public Stream<T> stream() {
        if (!isPresent()) {
            return Stream.empty();
        } else {
            return Stream.of(value);
        }
    }

    /**
     * If a value is present, returns the value, otherwise throws
     * {@code NoSuchElementException}.
     *
     * @apiNote
     * The preferred alternative to this method is {@link #orElseThrow()}.
     *
     * @return the non-{@code null} value described by this {@code Optional}
     * @throws NoSuchElementException if no value is present
     */
    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }
}
