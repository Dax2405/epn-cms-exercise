package rocket;

import java.util.ArrayList;

import rocket.crew.Crew;
import rocket.engine.Engine;
import rocket.fuel.FuelTank;
import rocket.payload.Payload;

public class Falcon9 extends Rocket {

    private static final Double falcon9Mass = 549054.0;
    private static final Double falcon9height = 70.0;
    private static final Double falcon9Diameter = 3.7;
    private static final Double falcon9FirstStageDryMass = 25600.0;
    private static final Double falcon9FirstStageBurnOutTime = 162.0;
    private static final Double falcon9SecondStageInitialMass = 12470.0;
    private static final Double falcon9SecondStageDryMass = 3900.0;
    private static final Double falcon9SecondStageBurnOutTime = 397.0;

    public Falcon9(FuelTank fuelTank, Crew crew, ArrayList<Engine> engines, Payload payload) {

        super(falcon9Mass, fuelTank, crew, engines, falcon9height, falcon9Diameter, payload,
                falcon9FirstStageDryMass,
                falcon9FirstStageBurnOutTime, falcon9SecondStageInitialMass, falcon9SecondStageDryMass,
                falcon9SecondStageBurnOutTime);
    }

    public Double getFalcon9Mass() {
        return falcon9Mass;
    }

}
