package solver;

import rocket.Rocket;

public interface DifferentialEquationSolver {
    public void solve(Rocket rocket, Double initialAltitude, Double orbitAltitude);

}
