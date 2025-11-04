import java.math.BigInteger;

/**
 * A class representing an RSA key (public or private).
 * @param modulus The modulus n.
 * @param exponent The exponent e or d.
 */
public class Key {
    public BigInteger modulus;
    public BigInteger exponent;

    // Constructor
    public Key(BigInteger modulus, BigInteger exponent) {
        this.modulus = modulus;
        this.exponent = exponent;
    }
}
