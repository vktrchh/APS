 public class CallEvent {
    String type; //"И" - источник, "П" - прибор
    int id;
    double time;
    int flag; //по сути всегда должен быть 0

    CallEvent(String type, int id, double time, int flag) {
        this.type = type;
        this.id = id;
        this.time = time;
        this.flag = flag;
    }
}