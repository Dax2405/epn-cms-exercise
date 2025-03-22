package rocket.engine;

public class MerlinEngine extends Engine {
    public static final Double merlinThrust = 845000.0; // Newtons [N]
    public static final Double merlinSpecificImpulse = 282.0; // Seconds [s]

    public MerlinEngine() {
        super(merlinThrust, merlinSpecificImpulse);
    }

}
