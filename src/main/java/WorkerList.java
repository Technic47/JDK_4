import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class WorkerList implements List<Worker> {
    private static final int DEFAULT_SIZE = 10;
    private Worker[] array;
    private int arrayIndex;
    private boolean empty = true;
    private double loadFactor = 0.75;

    public WorkerList() {
        array = new Worker[DEFAULT_SIZE];
    }

    public WorkerList(int initialCapacity) {
        array = new Worker[initialCapacity];
    }

    public WorkerList(int initialCapacity, double loadFactor) {
        if (loadFactor > 1) {
            throw new RuntimeException("Wrong load factor! Can`t be greater than 1.");
        }
        array = new Worker[initialCapacity];
        this.loadFactor = loadFactor;
    }

    @Override
    public int size() {
        return arrayIndex;
    }

    @Override
    public boolean isEmpty() {
        return empty;
    }

    @Override
    public boolean contains(Object o) {
        for (Worker worker : array) {
            if (worker == null) {
                continue;
            }
            if (worker.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<Worker> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, arrayIndex + 1);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public synchronized boolean add(Worker worker) {
        reCalcSize(arrayIndex);

        array[arrayIndex] = worker;
        arrayIndex++;
        checkEmpty();
        return true;
    }

    @Override
    public synchronized void add(int index, Worker element) {
        reCalcSize(index + 1);

        for (int i = array.length - 1; i > index + 1; i--) {
            array[i] = array[i - 1];
            array[i - 1] = null;
        }

        array[index] = element;
        reCalcIndex(index);
    }

    private synchronized void reCalcSize(int index) {
        while (((double) index) / (double) array.length > loadFactor) {
            increaseSize();
        }
    }

    private synchronized void reCalcIndex(int index) {
        if (index > arrayIndex) {
            arrayIndex = index + 1;
        } else arrayIndex++;
    }

    private synchronized void increaseSize() {
        array = Arrays.copyOf(array, array.length * 2);
    }

    private synchronized void shift(int index) {
        for (int i = index; i < array.length - 1; i++) {
            array[i] = array[i + 1];
        }
    }

    @Override
    public synchronized boolean remove(Object o) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(o)) {
                array[i] = null;
                arrayIndex--;
                shift(i);
                checkEmpty();
                return true;
            }
        }
        return false;
    }

    private void checkEmpty() {
        if (arrayIndex == 0) {
            empty = true;
        } else empty = false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public synchronized boolean addAll(Collection<? extends Worker> c) {
        for (Worker worker : c) {
            add(worker);
        }
        return true;
    }

    @Override
    public synchronized boolean addAll(int index, Collection<? extends Worker> c) {
        int tempIndex = index;
        for (Worker worker : c) {
            add(tempIndex, worker);
            tempIndex++;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object o : c) {
            remove(o);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        array = new Worker[DEFAULT_SIZE];
    }

    @Override
    public Worker get(int index) {
        return array[index];
    }

    @Override
    public Worker set(int index, Worker element) {
        array[index] = element;
        return element;
    }


    @Override
    public synchronized Worker remove(int index) {
        Worker removed = array[index];
        if (removed != null) {
            array[index] = null;
        }
        shift(index);
        checkEmpty();
        arrayIndex--;
        return removed;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(o)) {
                return i;
            }

        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(o)) {
                index = i;
            }
        }
        return index;
    }

    @Override
    public ListIterator<Worker> listIterator() {
        return null;
    }

    @Override
    public ListIterator<Worker> listIterator(int index) {
        return null;
    }

    @Override
    public List<Worker> subList(int fromIndex, int toIndex) {
        return Arrays.stream(Arrays.copyOfRange(array, fromIndex, toIndex)).toList();
    }

    public List<Worker> findByWorkingYears(int workingYears) {
        List<Worker> results = new ArrayList<>();
        for (Worker worker : array) {
            if (worker == null) {
                continue;
            }
            if (worker.getWorkingYears() == workingYears) {
                results.add(worker);
            }
        }
        return results;
    }

    public List<Worker> findByPhone(String phone) {
        List<Worker> results = new ArrayList<>();
        for (Worker worker : array) {
            if (worker == null) {
                continue;
            }
            if (Objects.equals(worker.getPhone(), phone)) {
                results.add(worker);
            }
        }
        return results;
    }

    public List<Worker> findByNumber(int number) {
        List<Worker> results = new ArrayList<>();
        for (Worker worker : array) {
            if (worker == null) {
                continue;
            }
            if (Objects.equals(worker.getNumber(), number)) {
                results.add(worker);
            }
        }
        return results;
    }

    public List<Worker> findBy(Predicate<Worker> predicate) {
        List<Worker> results = new ArrayList<>();
        for (Worker worker : array) {
            if (worker == null) {
                continue;
            }
            if (predicate.test(worker)) {
                results.add(worker);
            }
        }
        return results;
    }
}
