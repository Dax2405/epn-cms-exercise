package solver;

import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer;

import rocket.Rocket;

import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.SimpleBounds;
import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;

public class ParabolicSolver implements DifferentialEquationSolver {

    private static final double V_e = 2766.42; // Velocidad de eyección de gases [m/s]
    private static final double G = 9.81; // Aceleración de la gravedad [m/s^2]

    @Override
    public void solve(Rocket rocket, Double initialAltitude, Double orbitAltitude) {
        double x0 = 0.0;
        double h0 = initialAltitude;
        double hf = orbitAltitude;

        // Parámetros del Falcon 9 Stage 1
        double m0_1 = rocket.getTotalMass();
        double m_dry_1 = rocket.getFirstStageDryMass();
        double t_burn_1_nom = rocket.getFirstStageBurnOutTime();
        double dot_m1 = (m0_1 - m_dry_1) / t_burn_1_nom;

        // Parámetros del Falcon 9 Stage 2
        double m0_2 = rocket.getSecondStageInitalMass();
        double m_dry_2 = rocket.getSecondStageDryMass();
        double t_burn_2_nom = rocket.getSecondStageBurnOutTime();
        double dot_m2 = (m0_2 - m_dry_2) / t_burn_2_nom;

        // Definir la función objetivo
        ObjectiveFunction objectiveFunction = new ObjectiveFunction(point -> {
            double theta1 = point[0];
            double theta2 = point[1];
            double t2 = point[2];

            // Obtener condiciones de Stage 1
            double[] stage1Results = stage1(theta1, m0_1, m_dry_1, dot_m1, t_burn_1_nom, x0, h0);
            double x1 = stage1Results[0];
            double h1 = stage1Results[1];
            double Vx1 = stage1Results[2];
            double Vy1 = stage1Results[3];

            // Calcular condiciones de Stage 2 para el tiempo t2 dado
            double[] stage2Results = stage2(theta2, x1, h1, Vx1, Vy1, t2, m0_2, m_dry_2, dot_m2);
            double h2 = stage2Results[0];
            double Vy2 = stage2Results[1];

            // Error: queremos Vy2 = 0 y h2 = hf
            double error = Math.pow(Vy2 - 0.0, 2) + Math.pow(h2 - hf, 2);
            return error;
        });

        // Definir límites de las variables
        SimpleBounds bounds = new SimpleBounds(
                new double[] { 0.0, 0.0, 1.0 },
                new double[] { Math.PI / 2, Math.PI / 2, 1000.0 });

        // Valor inicial de la optimización
        InitialGuess initialGuess = new InitialGuess(new double[] { Math.PI / 4, Math.PI / 4, 200.0 });

        // Configurar el optimizador
        BOBYQAOptimizer optimizer = new BOBYQAOptimizer(7); // Aumentar el número de puntos de interpolación
        PointValuePair result = optimizer.optimize(
                new MaxEval(10000), // Aumentar el número máximo de evaluaciones
                objectiveFunction,
                GoalType.MINIMIZE,
                bounds,
                initialGuess);

        double theta1_opt = result.getPoint()[0];
        double theta2_opt = result.getPoint()[1];
        double t2_opt = result.getPoint()[2];

        // Con los ángulos y t2 óptimos, calculamos las condiciones finales
        double[] stage1Results = stage1(theta1_opt, m0_1, m_dry_1, dot_m1, t_burn_1_nom, x0, h0);
        double x1 = stage1Results[0];
        double h1 = stage1Results[1];
        double Vx1 = stage1Results[2];
        double Vy1 = stage1Results[3];
        double t_burn1 = stage1Results[4];

        double[] stage2Results = stage2(theta2_opt, x1, h1, Vx1, Vy1, t2_opt, m0_2, m_dry_2, dot_m2);
        double h2 = stage2Results[0];
        double Vy2 = stage2Results[1];
        double t2_final = stage2Results[2];

        double total_time = t_burn1 + t2_final;

        // Imprimir resultados
        System.out.println("---- Simulación de Llegada a LEO Parabolic  ----");
        System.out.println("Condiciones iniciales: h0 = " + h0 + " m, x0 = " + x0 + " m");
        System.out.println("Altura objetivo: h_f = " + hf + " m");
        System.out.println("");
        System.out.println("---- Etapa 1 (Stage 1) ----");
        System.out.println("Ángulo óptimo theta1: " + Math.toDegrees(theta1_opt) + "°");
        System.out.println("Tiempo de burnout Stage 1: " + t_burn1 + " s");
        System.out.println("Estado al final de Stage 1:");
        System.out.println("   x1  = " + x1 + " m");
        System.out.println("   h1  = " + h1 + " m");
        System.out.println("   Vx1 = " + Vx1 + " m/s");
        System.out.println("   Vy1 = " + Vy1 + " m/s");
        System.out.println("");
        System.out.println("---- Etapa 2 (Stage 2) ----");
        System.out.println("Ángulo óptimo theta2: " + Math.toDegrees(theta2_opt) + "°");
        System.out.println("Tiempo Stage 2 (para alcanzar condiciones finales): " + t2_final + " s");
        System.out.println("Estado al final de Stage 2:");
        System.out.println("   h2  = " + h2 + " m  (Objetivo: " + hf + " m)");
        System.out.println("   Vy2 = " + Vy2 + " m/s  (Objetivo: 0 m/s)");
        System.out.println("");
        System.out.println("---- Resumen ----");
        System.out.println("Tiempo total de llegada a LEO: " + total_time + " s");
        System.out.println("Error en condiciones finales (objetivo): " + result.getValue());
    }

    private double[] stage1(double theta1, double m0_1, double m_dry_1, double dot_m1, double t_burn_1_nom, double x0,
            double h0) {
        double t_burn_1 = (m0_1 - m_dry_1) / dot_m1;
        double mass_ratio = m0_1 / (m0_1 - dot_m1 * t_burn_1);
        double ln_mass_ratio = Math.log(mass_ratio);

        double Vx1 = V_e * Math.cos(theta1) * ln_mass_ratio;
        double Vy1 = V_e * Math.sin(theta1) * ln_mass_ratio - G * t_burn_1;

        double term1 = Math.log(m0_1) * t_burn_1;
        double m_term = m0_1 - dot_m1 * t_burn_1;
        double term2 = (m_term * Math.log(m_term) - m_term - m0_1 * Math.log(m0_1) + m0_1) / dot_m1;

        double x1 = x0 + 0.0 * t_burn_1 + V_e * Math.cos(theta1) * (term1 + term2);
        double h1 = h0 + 0.0 * t_burn_1 + V_e * Math.sin(theta1) * (term1 + term2) - 0.5 * G * t_burn_1 * t_burn_1;

        return new double[] { x1, h1, Vx1, Vy1, t_burn_1 };
    }

    private double[] stage2(double theta2, double x1, double h1, double Vx1, double Vy1, double t2, double m0_2,
            double m_dry_2, double dot_m2) {
        if (m0_2 - dot_m2 * t2 <= 0) {
            return new double[] { Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY };
        }

        double mass_ratio2 = m0_2 / (m0_2 - dot_m2 * t2);
        double ln_mass_ratio2 = Math.log(mass_ratio2);

        double Vy2 = Vy1 + V_e * Math.sin(theta2) * ln_mass_ratio2 - G * t2;

        double term1_2 = Math.log(m0_2) * t2;
        double m_term2 = m0_2 - dot_m2 * t2;
        double term2_2 = (m_term2 * Math.log(m_term2) - m_term2 - m0_2 * Math.log(m0_2) + m0_2) / dot_m2;

        double h2 = h1 + Vy1 * t2 + V_e * Math.sin(theta2) * (term1_2 + term2_2) - 0.5 * G * t2 * t2;

        return new double[] { h2, Vy2, t2 };
    }
}