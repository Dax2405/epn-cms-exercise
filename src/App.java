import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        // Crew
        Astronaut astronaut1 = new Astronaut(70.0);
        Astronaut astronaut2 = new Astronaut(80.0);
        Crew crew = new Crew();
        crew.addAstronaut(astronaut1);
        crew.addAstronaut(astronaut2);

        // Engines
        ArrayList<Engine> engines = new ArrayList<Engine>();
        for (int i = 0; i < 9; i++) {
            engines.add(new MerlinEngine());
        }

        // FuelTank
        FuelTank fuelTank = new FuelTank();

        // Payload
        Payload payload = new LEOPayload();

        // Rocket
        RocketBuilder rocketBuilder = new RocketBuilder();
        Rocket rocket = rocketBuilder.setCrew(crew)
                .setEngine(engines)
                .setFuelTank(fuelTank)
                .setPayload(payload).buildFalcon9();

        Double initialAltitude = 20000.0;
        Double orbitAltitude = 200000.0;

        DifferentialEquationSolver parabolicSolver = new ParabolicSolver();
        parabolicSolver.solve(rocket, initialAltitude, orbitAltitude);

        DifferentialEquationSolver verticalSolver = new VerticalSolver();
        verticalSolver.solve(rocket, initialAltitude, orbitAltitude);
    }

}
