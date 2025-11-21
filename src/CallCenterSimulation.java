import java.util.*;

public class CallCenterSimulation {

    static class Customer {
        int customerId;
        List<Call> callHistory = new ArrayList<>();

        Customer(int customerId) { this.customerId = customerId; }
        Call makeCall(double startTime, int callId) {
            Call c = new Call(callId, this, startTime);
            callHistory.add(c);
            return c;
        }
    }
     static class SimulationReport {
        int totalCallsGenerated = 0;
        int totalCallsRejected = 0;
        double avgWaitTime = 0;
        void printReport() {
            System.out.printf("Сгенерировано заявок: %d\n", totalCallsGenerated);
            System.out.printf("Отклонено заявок: %d\n", totalCallsRejected);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<CallGenerator> generators = Arrays.asList(new CallGenerator(1), new CallGenerator(2), new CallGenerator(3));
        List<Customer> customers = Arrays.asList(new Customer(1), new Customer(2), new Customer(3));
        CallCenter callCenter = new CallCenter(2, 2);
        PriorityQueue<CallEvent> calendar = new PriorityQueue<>(Comparator.comparingDouble(e -> e.time));
        double curTime = 0;

        for (CallGenerator gen : generators) {
            calendar.add(new CallEvent("И", gen.id, gen.generateCallTime(curTime), 0));
        }

        System.out.println("Календарь событий:");
        System.out.printf("%6s %2s %8s %3s\n", "Тип", "ID", "Время", "Флаг");
        for (CallEvent e : calendar)
            System.out.printf("%6s %2d %8.3f %3d\n", e.type, e.id, e.time, e.flag);

        System.out.println("Пошаговый режим. Enter - шаг, q - выход.");

        while (true) {
            System.out.print("> ");
            String cmd = scanner.nextLine();
            if (cmd.equals("q") || calendar.isEmpty()) break;

            CallEvent event = calendar.poll();
            curTime = event.time;
            System.out.println("Шаг [" + curTime + "]: событие " + event.type + event.id);

            callCenter.processEvent(event, generators, customers, calendar, curTime);

            System.out.println("Календарь событий:");
            System.out.printf("%6s %2s %8s %3s\n", "Тип", "ID", "Время", "Флаг");
            for (CallEvent e : calendar)
                System.out.printf("%6s %2d %8.3f %3d\n", e.type, e.id, e.time, e.flag);
        }
        System.out.println("Моделирование завершено.");
        callCenter.generateReport().printReport();
    }
}
