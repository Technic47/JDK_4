import java.time.LocalDateTime;
import java.util.Objects;

import static java.time.temporal.ChronoField.YEAR;

public class Worker {
    private int number;
    private String phone;
    private String name;
    private LocalDateTime hireDate;

    private Worker(int number,
                   String phone,
                   String name,
                   LocalDateTime hireDate) {
        this.number = number;
        this.phone = phone;
        this.name = name;
        this.hireDate = hireDate;
    }

    public static Worker createWorker(int number, String phone, String name, LocalDateTime hireDate) {
        return new Worker(number, phone, name, hireDate);
    }

    public int getNumber() {
        return number;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getHireDate() {
        return hireDate;
    }

    public int getWorkingYears() {
        return LocalDateTime.now().get(YEAR) - hireDate.get(YEAR);
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHireDate(LocalDateTime hireDate) {
        this.hireDate = hireDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Worker)) return false;
        Worker worker = (Worker) o;
        return number == worker.number && Objects.equals(phone, worker.phone) && Objects.equals(name, worker.name) && Objects.equals(hireDate, worker.hireDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, phone, name, hireDate);
    }
}
