public class Operator {
    int operatorId;
    CallCenterSimulation.Customer currentCustomer = null;
    boolean isBusy = false;

    boolean isFree() { return !isBusy; }
    void assignCustomer(CallCenterSimulation.Customer customer) {
        currentCustomer = customer;
        isBusy = true;
    }
    void completeService() {
        currentCustomer = null;
        isBusy = false;
    }
}