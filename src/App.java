import java.util.ArrayList;

import rocket.Rocket;
import rocket.RocketBuilder;
import rocket.crew.Astronaut;
import rocket.crew.Crew;
import rocket.engine.Engine;
import rocket.engine.MerlinEngine;
import rocket.fuel.FuelTank;
import rocket.payload.LEOPayload;
import rocket.payload.Payload;
import solver.DifferentialEquationSolver;
import solver.ParabolicSolver;
import solver.VerticalSolver;

public class App {
    public static void main(String[] args) throws Exception {
        Crew crew = createCrew();

        ArrayList<Engine> engines = createEngines();

        FuelTank fuelTank = new FuelTank();

        Payload payload = new LEOPayload();

        Rocket rocket = new RocketBuilder()
                .setCrew(crew)
                .setEngine(engines)
                .setFuelTank(fuelTank)
                .setPayload(payload)
                .buildFalcon9();

        // Altitudes
        Double initialAltitude = 2000.0;
        Double orbitAltitude = 200000.0;

        solveEquations(rocket, initialAltitude, orbitAltitude);
    }

    private static Crew createCrew() {
        Crew crew = new Crew();
        crew.addAstronaut(new Astronaut(70.0));
        crew.addAstronaut(new Astronaut(80.0));
        return crew;
    }

    private static ArrayList<Engine> createEngines() {
        ArrayList<Engine> engines = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            engines.add(new MerlinEngine());
        }
        return engines;
    }

    private static void solveEquations(Rocket rocket, Double initialAltitude, Double orbitAltitude) {
        DifferentialEquationSolver parabolicSolver = new ParabolicSolver();
        parabolicSolver.solve(rocket, initialAltitude, orbitAltitude);

        DifferentialEquationSolver verticalSolver = new VerticalSolver();
        verticalSolver.solve(rocket, initialAltitude, orbitAltitude);
    }
}