import java.util.ArrayList;

public class RocketBuilder {
    private Double initialMass;
    private FuelTank fuelTank;
    private Crew crew;
    private ArrayList<Engine> engines;
    private Double height;
    private Double diameter;
    private Payload payload;

    public void setInitialMass(Double initialMass) {
        this.initialMass = initialMass;
    }

    public RocketBuilder setFuelTank(FuelTank fuelTank) {
        this.fuelTank = fuelTank;
        return this;
    }

    public RocketBuilder setCrew(Crew crew) {
        this.crew = crew;
        return this;
    }

    public RocketBuilder setEngine(ArrayList<Engine> engines) {
        this.engines = engines;
        return this;
    }

    public RocketBuilder setHeight(Double height) {
        this.height = height;
        return this;
    }

    public RocketBuilder setDiameter(Double diameter) {
        this.diameter = diameter;
        return this;
    }

    public RocketBuilder setPayload(Payload payload) {
        this.payload = payload;
        return this;
    }

    public Rocket build() {
        return new Rocket(initialMass, fuelTank, crew, engines, height, diameter, payload);
    }

    public Falcon9 buildFalcon9() {
        return new Falcon9(fuelTank, crew, engines, payload);
    }

}
