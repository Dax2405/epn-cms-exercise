package rocket;

import java.util.ArrayList;

import rocket.crew.Crew;
import rocket.engine.Engine;
import rocket.fuel.FuelTank;
import rocket.payload.Payload;

public class Rocket {

    // All units in the International Sistem (meters, kilogram)
    private Double initialMass;
    private FuelTank fuelTank;
    private Crew crew;
    private ArrayList<Engine> engines;
    private Double height;
    private Double diameter;
    private Payload payload;
    private Double totalMass = 0.0;
    private Double firstStageDryMass;
    private Double firstStageBurnOutTime;
    private Double secondStageInitalMass;
    private Double secondStageDryMass;
    private Double secondStageBurnOutTime;
    private Double totalThrust;

    public Rocket(Double initialMass, FuelTank fuelTank, Crew crew, ArrayList<Engine> engines,
            Double height, Double diameter, Payload payload, Double firstStageDryMass,
            Double firstStageBurnOutTime, Double secondStageInitalMass, Double secondStageDryMass,
            Double secondStageBurnOutTime) {
        this.initialMass = initialMass;
        this.fuelTank = fuelTank;
        this.crew = crew;
        this.engines = engines;
        this.height = height;
        this.diameter = diameter;
        this.payload = payload;
        this.firstStageDryMass = firstStageDryMass;
        this.firstStageBurnOutTime = firstStageBurnOutTime;
        this.secondStageInitalMass = secondStageInitalMass;
        this.secondStageDryMass = secondStageDryMass;
        this.secondStageBurnOutTime = secondStageBurnOutTime;
        totalMass += initialMass + crew.getTotalMass();
        setNewTotalThrust();

    }

    public Rocket(Double initialMass, FuelTank fuelTank, Crew crew, ArrayList<Engine> engines,
            Double height, Double diameter, Payload payload) {
        this.initialMass = initialMass;
        this.fuelTank = fuelTank;
        this.crew = crew;
        this.engines = engines;
        this.height = height;
        this.diameter = diameter;
        this.payload = payload;
        totalMass += initialMass + crew.getTotalMass();
        setNewTotalThrust();

    }

    public Double getInitialMass() {
        return initialMass;
    }

    public void setInitialMass(Double initialMass) {
        this.initialMass = initialMass;
    }

    public FuelTank getFuelTank() {
        return fuelTank;
    }

    public void setFuelTank(FuelTank fuelTank) {
        this.fuelTank = fuelTank;
    }

    public Crew getCrew() {
        return crew;
    }

    public void setCrew(Crew crew) {
        this.crew = crew;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getDiameter() {
        return diameter;
    }

    public void setDiameter(Double diameter) {
        this.diameter = diameter;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public Double getTotalMass() {
        return totalMass;
    }

    public void setTotalMass(Double totalMass) {
        this.totalMass = totalMass;
    }

    public ArrayList<Engine> getEngines() {
        return engines;
    }

    public void setEngines(ArrayList<Engine> engines) {
        this.engines = engines;
    }

    public void addEngine(Engine engine) {
        engines.add(engine);
    }

    public Double calculateTotalThrust() {
        Double totalThrust = 0.0;
        for (Engine engine : engines) {
            totalThrust += engine.getThrust();
        }
        return totalThrust;
    }

    public void setNewTotalThrust() {
        this.totalThrust = calculateTotalThrust();
    }

    public Double getFirstStageDryMass() {
        return firstStageDryMass;
    }

    public void setFirstStageDryMass(Double firstStageDryMass) {
        this.firstStageDryMass = firstStageDryMass;
    }

    public Double getFirstStageBurnOutTime() {
        return firstStageBurnOutTime;
    }

    public void setFirstStageBurnOutTime(Double firstStageBurnOutTime) {
        this.firstStageBurnOutTime = firstStageBurnOutTime;
    }

    public Double getSecondStageInitalMass() {
        return secondStageInitalMass;
    }

    public void setSecondStageInitalMass(Double secondStageInitalMass) {
        this.secondStageInitalMass = secondStageInitalMass;
    }

    public Double getSecondStageDryMass() {
        return secondStageDryMass;
    }

    public void setSecondStageDryMass(Double secondStageDryMass) {
        this.secondStageDryMass = secondStageDryMass;
    }

    public Double getSecondStageBurnOutTime() {
        return secondStageBurnOutTime;
    }

    public void setSecondStageBurnOutTime(Double secondStageBurnOutTime) {
        this.secondStageBurnOutTime = secondStageBurnOutTime;
    }

    public Double getTotalThrust() {
        return totalThrust;
    }

    public void setTotalThrust(Double totalThrust) {
        this.totalThrust = totalThrust;
    }

    @Override
    public String toString() {
        return "Rocket [crew=" + crew + ", diameter=" + diameter + ", engine total thrust" + totalThrust
                + ", firstStageBurnOutTime="
                + firstStageBurnOutTime + ", firstStageDryMass=" + firstStageDryMass + ", fuelTank=" + fuelTank
                + ", height=" + height + ", initialMass=" + initialMass
                + ", payload=" + payload + ", secondStageBurnOutTime=" + secondStageBurnOutTime
                + ", secondStageDryMass=" + secondStageDryMass + ", secondStageInitalMass=" + secondStageInitalMass
                + ", totalMass=" + totalMass + "]";
    }
}
