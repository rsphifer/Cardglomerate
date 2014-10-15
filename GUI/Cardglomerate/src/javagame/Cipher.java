package javagame;

public class Cipher {
    String key;
    String ciphertextAlphabet;
    final String plaintextAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    Cipher(String key) {
        this.key = key;
        ciphertextAlphabet = initCiphertextAlphabet();
    }

    String initCiphertextAlphabet() {
        key = key.toUpperCase();
        key = removeDups(key);
        ciphertextAlphabet = key;
        for (int i = 0; i < plaintextAlphabet.length(); i++) {
            if (!key.contains(plaintextAlphabet.substring(i, i + 1)))
                ciphertextAlphabet += plaintextAlphabet.substring(i, i + 1);
        }
        return ciphertextAlphabet;
    }

    static String removeDups(String s) {
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            if (!result.contains(s.substring(i, i + 1)))
                result += s.substring(i, i + 1);
        }
        return result;
    }

    String encrypt(String message) {
        message = message.toUpperCase();
        String result = "";
        for (int i = 0; i < message.length(); i++) {
            String current = message.substring(i, i + 1);
            if (!plaintextAlphabet.contains(current))
                result += current;
            else {
                int index = plaintextAlphabet.indexOf(current);
                result += ciphertextAlphabet.substring(index, index + 1);
          }
        }
        return result;
    }

    String decrypt(String ciphertext) {
        ciphertext = ciphertext.toUpperCase();
        String result = "";
        for (int i = 0; i < ciphertext.length(); i++) {
            String current = ciphertext.substring(i, i + 1);
            if (!plaintextAlphabet.contains(current))
                result += current;
            else {
                int index = ciphertextAlphabet.indexOf(current);
                result += plaintextAlphabet.substring(index, index + 1);
            }
        }
        return result;
    }

}