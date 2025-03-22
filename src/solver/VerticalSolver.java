package solver;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.solvers.BrentSolver;
import org.apache.commons.math3.analysis.solvers.UnivariateSolver;

import rocket.Rocket;

public class VerticalSolver implements DifferentialEquationSolver {

    private static final double V_e = 2766.42; // Velocidad de eyección de gases [m/s]
    private static final double G = 9.81; // Aceleración de la gravedad [m/s^2]

    @Override
    public void solve(Rocket rocket, Double initialAltitude, Double orbitAltitude) {
        double h0 = initialAltitude;
        double hf = orbitAltitude;

        // Parámetros del cohete
        double m0 = rocket.getTotalMass();
        double m_dry = rocket.getFirstStageDryMass();
        double t_burn_nom = rocket.getFirstStageBurnOutTime();
        double dot_m = (m0 - m_dry) / t_burn_nom;

        // Función de altura en función del tiempo
        UnivariateFunction heightFunction = t -> {
            double mass_t = m0 - dot_m * t;
            if (mass_t <= 0)
                return Double.POSITIVE_INFINITY; // Evita valores inválidos

            return h0 + V_e * (Math.log(m0) * t + (1.0 / dot_m) *
                    ((mass_t * Math.log(mass_t)) - mass_t - (m0 * Math.log(m0)) + m0))
                    - 0.5 * G * t * t - hf;
        };

        // Solver de Brent para encontrar t en el que h(t) = hf
        UnivariateSolver solver = new BrentSolver(1e-6, 1e-12);
        try {
            double t_final = solver.solve(1000, heightFunction, 0, t_burn_nom);
            System.out.println("\n\n\n---- Simulación de Llegada a LEO Vertical  ----");

            System.out.println("Tiempo estimado para alcanzar la altura objetivo: " + t_final + " segundos");
        } catch (Exception e) {
            System.err.println("Error al resolver la ecuación: " + e.getMessage());
        }
    }
}
