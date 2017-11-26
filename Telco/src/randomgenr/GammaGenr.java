package randomgenr;

/**
 * Determines the number of independent events randomly occurring per some
 * unit time. Use the exponential variate generator to calculate time between
 * each event.
 *
 * @author Ryan Owens
 */
public class GammaGenr extends RandomVariateGenr {
    private final double theta = 2.12;
    private final double alpha = 3.75;
    private final double maxHardwareTime = 2;

    /**
     * Default constructor.
     * Default, average event rate is 1.0 per unit time.
     */
    public GammaGenr() {
        super();
    }

    /**
     * Constructor to specify the random seed to use in pseudorandom number
     * generation. Default, average event rate is 1.0 per unit time.
     *
     * @param seed pseudorandom number seed value
     */
    public GammaGenr(long seed) {
        super(seed);
    }

    /**
     * Produces the next random variate from a Gamma Distribution.
     *
     * @return a random variate from a Gamma Distribution
     */
    @Override
    public double nextVariate() {

        final double d = this.alpha - 0.333333333333333333;
        final double c = 1 / (3 * Math.sqrt(d));

        while (true) {
            final double x = this.rand.nextGaussian();
            final double v = Math.pow((1 + c * x), 3);

            if(v <= 0)
            {
                continue;
            }

            final double x2 = x * x;
            final double u = this.rand.nextDouble();

            if(u < 1 - 0.0331 * x2 * x2) {
                return this.theta * d * v;
            }

            if(Math.log(u) < 0.5 * x2 + d * (1 - v + Math.log(v))) {
                return this.theta * d * v;
            }
        }
    }
}