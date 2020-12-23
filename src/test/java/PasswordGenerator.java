import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String args[]){

        String password = "qwe123";
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        String encondedPassword = bCryptPasswordEncoder.encode(password);

        System.out.println(encondedPassword);

    }
}
