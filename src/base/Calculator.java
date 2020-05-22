package base;

import static java.lang.StrictMath.*;

public class Calculator {

    // variables used in calculations
    public double Vs;       // starting Voltage
    public double C;        // capacity
    public double L;        // inductance
    public double RR;       // auxiliary resistance
    public double Vc;       // current Voltage
    public double a0;       // starting amplitude
    public double a;        // current amplitude
    public double W0;       // starting frequency
    public double W;        // current frequency
    public double k;        // attenuation Coefficient
    public double R;        // total Resistance
    public double Rc;       // critical (maximal) Resistance
    public double q0;       // starting charge
    public double q;        // current charge
    public double T;        // oscillation period
    public double d;        // logarithmic decrement quotient
    public double B;        // starting Phase
    public double tR;       // time range
    public double tD;       // time step for each calculation
    
    public static double pi = 3.1415926535897932384626433832795;        // pi
    public static double eps = 0.000000000000000000000001;              // error tolerance
    public static double e = 2.7182818284590452353602874713527;         // Euler's number

    // this if for the first line of calculation's table
    public String[] variables = new String[]{"№", "t (s)", "q (C)", "A (m)", "Vc (V)"};
    // each step changing variable data storage
    public String[][] data;
    // amount of steps performed during calculations on whole time range
    public int iterations;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // constructor (object initializer) for Calculator class
    public Calculator(double V, double C, double L, double R, double time, double timeDelta, double B) {
        this.Vs = V;
        this.C = C;
        this.L = L;
        this.RR = R;
        this.tR = time;
        this.tD = timeDelta;
        this.B = B;

        // initialize data table to be represented after the calculations
        data = new String[100100][variables.length];
        int i = 0;

        for (String s : variables)
            data[0][i++] = s;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // calculates constants before embarking on each step charge calculations
    public void calculateConstants() {
        W0 = Math.pow(L * C, -0.5);
        R = W0 * L + Math.pow(W0 * C * 2 * pi, -1) + RR;
        k = R / (2 * L);
        W = sqrt(Math.pow(W0, 2) - Math.pow(k, 2));
        Rc = 2 * sqrt(L / C);
        T = 2 * pi / W;
        q0 = C * Vs;
        a0 = q0;
    }

    // calculates charge & all required to find it
    public void calculateCharge() {
        // ct - current time
        double ct = 0;
        // step counter
        int i = 0;

        // while current time has not reached time range end
        while (ct + eps < tR) {
            d = ((W * ct + B) * 180) / pi;
            a = a0 * Math.pow(e, -k * ct);
            q = q0 * cos(d) * Math.pow(e, -k * ct);
            Vc = Vs * Math.pow(e, -k * ct) * cos(d);

            i++;

            // fill in the data matrix
            // which will be used to display changing variables
            data[i][0] = String.valueOf(i);
            data[i][1] = String.valueOf(ct);
            data[i][2] = String.valueOf(q);
            data[i][3] = String.valueOf(a);
            data[i][4] = String.valueOf(Vc);

            // move time to the next stage
            ct += tD;
        }

        // save total amount of steps performed during calculations
        iterations = i;
    }

    // ---> getters for constant variable output <---

    public double getTotalResistance(){
        return R;
    }

    public double getOscillationPeriod(){
        return T;
    }

    public double getOscillationFrequency(){
        return W;
    }

    public double getAttenuationCoefficient(){
        return k;
    }
}