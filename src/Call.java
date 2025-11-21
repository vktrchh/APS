public class Call {
    int callId;
    CallCenterSimulation.Customer customer;
    CallStatus status;
    double startTime;
    double endTime;

    Call(int callId, CallCenterSimulation.Customer customer, double startTime) {
        this.callId = callId;
        this.customer = customer;
        this.status = CallStatus.ACTIVE;
        this.startTime = startTime;
    }

    CallStatus getStatus() { return status; }
    void setStatus(CallStatus status) { this.status = status; }
}