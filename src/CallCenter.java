import java.util.*;

public class CallCenter {
    List<Operator> operators = new ArrayList<>();
    Queue<Call> waitingQueue = new LinkedList<>();
    int queueCapacity;
    CallCenterSimulation.SimulationReport report = new CallCenterSimulation.SimulationReport();

    CallCenter(int opCount, int queueCapacity) {
        for (int i = 0; i < opCount; i++) {
            int finalI = i;
            operators.add(new Operator() {{ operatorId = finalI + 1; }});
        }
        this.queueCapacity = queueCapacity;
    }
    void processEvent(CallEvent event, List<CallGenerator> generators, List<CallCenterSimulation.Customer> customers, PriorityQueue<CallEvent> calendar, double curTime) {
        if (event.type.equals("И")) {
            CallCenterSimulation.Customer customer = customers.get(event.id-1);
            report.totalCallsGenerated++;
            Call call = customer.makeCall(curTime, report.totalCallsGenerated);
            boolean assigned = false;
            for (Operator op : operators) {
                if (op.isFree()) {
                    op.assignCustomer(customer);
                    double servTime = curTime + (-Math.log(1 - Math.random()) * 5); // экспонента
                    calendar.add(new CallEvent("П", op.operatorId, servTime, 0));
                    assigned = true;
                    call.setStatus(CallStatus.COMPLETED);
                    break;
                }
            }
            if (!assigned) {
                if (waitingQueue.size() < queueCapacity) {
                    waitingQueue.add(call);
                    call.setStatus(CallStatus.WAITING);
                } else {
                    report.totalCallsRejected++;
                    call.setStatus(CallStatus.REJECTED);
                    rejectCall(call, "Очередь или операторы заняты");
                }
            }
            for (CallGenerator gen : generators) {
                if (gen.id == event.id) {
                    double nextTime = gen.generateCallTime(curTime);
                    calendar.add(new CallEvent("И", gen.id, nextTime, 0));
                }
            }
        }
        else if (event.type.equals("П")) {
            for (Operator op : operators) {
                if (op.operatorId == event.id) {
                    op.completeService();
                    break;
                }
            }
            if (!waitingQueue.isEmpty()) {
                Call call = waitingQueue.poll();
                for (Operator op : operators) {
                    if (op.isFree()) {
                        op.assignCustomer(call.customer);
                        double servTime = curTime + (-Math.log(1 - Math.random()) * 5);
                        calendar.add(new CallEvent("П", op.operatorId, servTime, 0));
                        call.setStatus(CallStatus.COMPLETED);
                        break;
                    }
                }
            }
        }
    }
    CallCenterSimulation.SimulationReport generateReport() { return report; }
    void rejectCall(Call call, String reason) { /* Явная обработка отказа, реализация в автоматическом режиме(наверное, я пока не понимаю) */ }
}