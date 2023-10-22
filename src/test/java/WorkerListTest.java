import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.time.Month.JANUARY;
import static org.junit.jupiter.api.Assertions.*;

class WorkerListTest {
    private WorkerList list = new WorkerList();
    private final Random random = new Random();

    @AfterEach
    void cleanUp() {
        list = new WorkerList();
    }

    @Test
    void createTest() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    void addTest() {
        Worker worker1 = Worker.createWorker(random.nextInt(0, 1000), "848484848", "Name", LocalDateTime.of(2020, JANUARY, 15, 10, 15));

        assertTrue(list.add(worker1));
        assertEquals(1, list.size());
        assertFalse(list.isEmpty());
    }

    @Test
    void batchAddTest() {
        assertTrue(list.addAll(getWorkerList(12)));
        assertEquals(12, list.size());
    }

    @Test
    void addToIndexTest() {
        Worker worker = Worker.createWorker(random.nextInt(0, 1000), "848484848", "Name", LocalDateTime.of(2020, JANUARY, 15, 10, 15));
        list.add(100, worker);

        assertEquals(worker, list.get(100));
    }

    @Test
    void addBatchToIndex(){
        List<Worker> workerList = getWorkerList(20);
        Worker worker = workerList.get(0);

        list.addAll(100, workerList);
        assertEquals(worker, list.get(100));
    }

    private List<Worker> getWorkerList(int amount) {
        List<Worker> workerList = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            workerList.add(Worker.createWorker(random.nextInt(0, 1000), "848484848", "Name", LocalDateTime.of(2020, JANUARY, 15, 10, 15)));
        }
        return workerList;
    }

    @Test
    void containsTest() {
        List<Worker> workerList = getWorkerList(5);
        Worker worker = workerList.get(1);
        list.addAll(workerList);

        assertTrue(list.contains(worker));
        assertEquals(1, list.indexOf(worker));
    }

    @Test
    void removeTest() {
        List<Worker> workerList = getWorkerList(5);
        Worker worker = workerList.get(1);
        list.addAll(workerList);

        list.remove(worker);
        assertFalse(list.contains(worker));
        assertEquals(4, list.size());
    }

    @Test
    void removeByIndexTest() {
        List<Worker> workerList = getWorkerList(5);
        Worker worker = workerList.get(1);
        list.addAll(workerList);

        list.remove(1);
        assertFalse(list.contains(worker));
        assertEquals(4, list.size());
    }

    @Test
    void setTest() {
        List<Worker> workerList = getWorkerList(5);
        Worker worker = workerList.get(1);
        list.addAll(workerList);

        list.set(1, Worker.createWorker(random.nextInt(0, 1000), "848484848", "Name", LocalDateTime.of(2020, JANUARY, 15, 10, 15)));

        assertNotEquals(worker, list.get(1));
    }

    @Test
    void workingYearsTest(){
        list.addAll(getWorkerList(10));

        assertEquals(10, list.findByWorkingYears(3).size());

        list.get(1).setHireDate(LocalDateTime.now());

        assertEquals(9, list.findByWorkingYears(3).size());
    }

    @Test
    void phoneTest(){
        list.addAll(getWorkerList(10));

        assertEquals(10, list.findByPhone("848484848").size());

        list.get(1).setPhone("444");

        assertEquals(1, list.findByPhone("444").size());
    }

    @Test
    void findByTest(){
        list.addAll(getWorkerList(10));
        Worker item = list.get(1);
        int number = item.getNumber();
        item.setPhone("444");
        item.setHireDate(LocalDateTime.now());

        Predicate<Worker> predicateWorkingYears = (worker) -> worker.getWorkingYears() == 3;
        Predicate<Worker> predicateNumber = (worker) -> worker.getNumber() == number;
        Predicate<Worker> predicatePhone = (worker) -> worker.getPhone().equals("848484848");

        assertEquals(9, list.findBy(predicateWorkingYears).size());
        assertEquals(9, list.findBy(predicatePhone).size());
        assertEquals(1, list.findBy(predicateNumber).size());
    }
}