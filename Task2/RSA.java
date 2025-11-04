import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * A class to handle RSA encryption and decryption.
 * @param p A prime number.
 * @param q A prime number.
 * @param n The modulus for the public and private keys.
 * @param phi The totient of n.
 * @param e The public exponent.
 * @param d The private exponent.
 * @param privateKey The private key. Not used but here for completeness
 * @param publicKey The public key. Not used but here for completeness
 */
public class RSA {
	private BigInteger p;
	private BigInteger q;
	private BigInteger n;
	private BigInteger phi;
	private BigInteger e;
	private BigInteger d;
	private Key privateKey;
	private Key publicKey; 


	// Constructor
	public RSA() {
		this.p = null;
		this.q = null;
		this.n = null;
		this.phi = null;
		this.e = null;
		this.d = null;
		this.privateKey = null;
		this.publicKey = null;
	}

	/**
	 * Reads two prime numbers from a file named "primes.txt".
	 * Sets the values of p and q.
	 */
	public void primeReader() {
		try {
			File file = new File("primes.txt");
			Scanner sc = new Scanner(file);

			while (sc.hasNextLine()) {
				String line = sc.nextLine().trim();
				if (line.startsWith("p=")) {
					p = new BigInteger(line.substring(2));
				} else if (line.startsWith("q=")) {
					q = new BigInteger(line.substring(2));
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error reading primes from file: " + e.getMessage());
		}
	}

	/**
	 * Generates the RSA keys (public and private) using the values of p and q.
	 * Computes n, phi, e, and d.
	 */
	public void keyGen() {
		try {
			n = p.multiply(q);
			phi = (p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)));

			e = (BigInteger.valueOf(65537));

			if (!e.gcd(phi).equals(BigInteger.ONE)) {
				throw new RuntimeException("e is not coprime");
			}

			d = e.modInverse(phi);

			privateKey = new Key(n, d);
			publicKey = new Key(n, e);

		} catch (RuntimeException e) {
			System.out.println("RSA key generation error: " + e.getMessage());
		}
	}

	/**
	 * Encrypts a message using the public key.
	 * @param message
	 * @return
	 */
	public BigInteger encrypt(BigInteger message) {
		BigInteger c = message.modPow(e, n);
		return c;
	}

	/**
	 * Decrypts a ciphertext using the private key.
	 * @param ciphertext
	 * @return
	 */
	public String decrypt(BigInteger ciphertext) {
		BigInteger c = ciphertext.modPow(d, n);
		byte[] bytes = c.toByteArray();
		String message = new String(bytes);
		return message;
	}


}