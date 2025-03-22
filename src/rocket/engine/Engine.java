package rocket.engine;

public class Engine {
    private Double thrust;
    private Double SpecificImpulse;

    public Engine(Double thrust, Double specificImpulse) {
        this.thrust = thrust;
        SpecificImpulse = specificImpulse;
    }

    public Double getThrust() {
        return thrust;
    }

    public void setThrust(Double thrust) {
        this.thrust = thrust;
    }

    public Double getSpecificImpulse() {
        return SpecificImpulse;
    }

    public void setSpecificImpulse(Double specificImpulse) {
        SpecificImpulse = specificImpulse;
    }

}
