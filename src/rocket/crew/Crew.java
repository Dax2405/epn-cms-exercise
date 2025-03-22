package rocket.crew;

import java.util.ArrayList;

public class Crew {

    private ArrayList<Astronaut> astronauts = new ArrayList<>();
    private Double totalMass = 0.0;

    public void addAstronaut(Astronaut astronaut) {
        astronauts.add(astronaut);
        totalMass += astronaut.getMass();
    }

    public Double getTotalMass() {
        return totalMass;
    }

    public void setTotalMass(Double totalMass) {
        this.totalMass = totalMass;
    }

}
