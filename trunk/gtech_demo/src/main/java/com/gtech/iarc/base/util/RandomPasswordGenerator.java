package com.gtech.iarc.base.util;

import java.security.SecureRandom;
import java.util.Date;

/**
 * This implementation of Password Generator utility class can be used to
 * generate random password with configurable pattern.   
 * 
 */
public class RandomPasswordGenerator {
    public static final String DEFAULT_PATTERN = "LLLLLLLL";

    static int NMIXES = 10*256;     //number of arcfour characters discarded per output character

    private boolean consonantNext = true;
    int i;
    static int ii = 0;
    static int jj = 0;
    
    static int S[] = new int[256];              //arcfour state vector
    private String template; 

    /**
     * Constructor
     */
    public RandomPasswordGenerator() {
        for (i=0; i<256; i++) {S[i] = i;} //initialize arcfour state vector  
    }
     
    /**
     * Generates a random password using the DEFAULT_PATTERN.
     * @return
     */ 
    public String generate(){
       return generate(DEFAULT_PATTERN); 
    }
    /**
     * Generates a random password following the specified pattern.
     * The pattern is a String containing a set of predefined characters. 
     * Each character in the pattern will determine what will be generated<br>
     * <ul>
     * <li>A - Letter, A to Z</li>
     * <li>9 - Digit, 0 to 9</li>
     * <li>C - Alphanumeric, 0 to 9, A to Z</li>
     * <li>L - Alphanumeric, upper and lower case, 0 to 9, A to Z, a to z</li>
     * <li>M - Printable 7-bit ASCII character, sp, 0 to 9, A to Z, a to z, !"#$%&'()*+,-./:;<=>?[\]^_{|}~</li>
     * <li>H - Hexadecimal (base 16) digit, 0 to 9, A to F</li>
     * <li>S - Syllable, alternating consonant and vowel</li>
     * <li>6 - Dice throw, 1 - 6</li>
     * <li>space - The 'space' character 
     * </ul>
     * <b>Note:</b> All the characters except 'S' in the pattern will be 
     *  substituted with exactly one randomly-generated character. An 'S' in the pattern
     *  may be substituted with one or two characters such as 'sh'. Therefore
     *  avoid using random Syllable if the use of fixed-length password 
     *  is important.      
     * @param pattern Pattern to be used for password generation 
     * @return Randomly-generated password
     * The Pattern string contains at
     *  least one character that are not either one of A, C, H, L, M, S, 6, 9, space  
     */
    public String generate(String pattern){
        if ((pattern == null)||(pattern.equals(""))) return pattern;

        this.template = pattern;    
        String validChar = "ACHLMS69 ";
        for (int idx=0; idx < template.length(); idx++){
            char testChar = template.charAt(idx); 
            if (validChar.indexOf(testChar) < 0)
                this.template = DEFAULT_PATTERN;
        }
        

        StringBuffer password = new StringBuffer("");
        
        // Using current time (long milliseconds) as source randomness
        stir(new Date().getTime()); 

        // Instantiate pseudo-random generator
        SecureRandom rand = new SecureRandom();  
        // Begin iteration to generate password of required length and pattern
        for (int ntmpl=0;ntmpl < template.length(); ntmpl++){
            stir(rand.nextLong());
            char tmplChar = template.charAt(ntmpl);
            password.append(addChar(tmplChar));
        }
                
        return password.toString(); 
    }

    /**
     * Maintains pool of randomness using a modified arcfour generator.
     * @param x A random number used to 'stir' the pool
     * @return an integer between 0 - 255
     */
    private int stir (long x) {
        if (x<0) x=-x;
        while (true) {
            ii = (ii+1) % 256;
            jj = (int) ((jj + S[ii] + x) % 256);
            int temp = S[ii];
            S[ii] = S[jj];
            S[jj] = temp;
            x = x/256;
            if (x == 0) break;
            }
        return (S[(S[ii] + S[jj]) % 256]);
    }

    /**
     * Extracts a random value in the range of second argument from the
     * randomness pool.
     * @param r upper bound of the extraction range
     * @return the extracted value from the randomnes pool 
     */
    private int extract (int r) {
        int q = 0;
        ii=jj=0;
        for (int k=0; k<NMIXES; k++) {  // we can afford a lot of mixing
            stir (0);
            }
        while (true) {
            q=stir(0); 
            if (q < r*(256/r)) break; // avoid biased choice
            }
        return (q % r);  
    }
        
    /** pick a random character specified by the template. */
    private String addChar(char tmplChar) {
        int ch = 0;
        if (tmplChar == ' ') {
            ch = ' ';
        }
        else if (tmplChar == 'A') {     //random letter [A-Z]
            ch = extract (26) + (int) 'A';
        }
        else if (tmplChar == 'C') {     //random alphanumeric [0-9,A-Z]
            ch =  extract (36);
            if (ch <10) ch = ch + (int) '0';
            else ch = ch + (int) 'A' - 10;
        }
        else if (tmplChar == 'H') {     //random hex digit [0-9,A-F]
            ch = extract (16);
            if (ch <10) ch = ch + (int) '0';
            else ch = ch + (int) 'A' - 10;
        }
        else if (tmplChar == 'L') {     //random alphanumeric upper or lower case [0-9,A-Z,a-z]
            ch =  extract (62);
            if (ch <10) ch = ch + (int) '0';
            else if (ch <36) ch = ch + (int) 'A' - 10;
            else ch = ch + (int) 'a' - 36;
        }
        else if (tmplChar == 'M') {     //random 7-bit ASCII printable [0-9,A-Z,a-z, !"#$%&'()*+,-./:;<=>?[\]^_{|}~]
            ch =  extract (95);
            ch = ch + (int) ' ';
        }
        else if (tmplChar == 'S') {     //random syllable (see addSyllable below)
            return addSyllable();
        }
        else if (tmplChar == '6') {     //random dice throw [0-6]
            ch = extract (6) + (int) '1';
        }
        else if (tmplChar == '9') {     //random decimal digit [0-9]
            ch = extract (10) + (int) '0';
        }

        consonantNext = true;
        return new Character((char) ch).toString();
    }
    
    /** pick a random syllable. */
    private String addSyllable() {
        String consonants[] =
            {"b","c","d","f","g","h","j","k","l","m",
            "n","p","qu","r","s","t","v","w","x","z",
            "ch","cr","fr","nd","ng","nk","nt","ph","pr","rd",
            "sh","sl","sp","st","th","tr"};
        String vowels[] = {"a","e","i","o","u","y"};
        String syl = "";
        
        if (consonantNext) {
            syl = consonants[extract (consonants.length)];
            if (syl != "qu") consonantNext = false;
        } else {
            syl = vowels[extract (vowels.length)];
            consonantNext = true;
        }
        return syl;
    }   
}
