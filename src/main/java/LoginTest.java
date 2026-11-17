import service.SignInException;
import service.SupaBaseStorageService;
import service.SupabaseAuthService;

import java.io.Console;
import java.nio.file.Path;
import java.util.Arrays;

public class LoginTest {
    public static void main(String[] args) throws Exception {
        Console console = System.console();
        if (console == null) {
            System.out.println("Run LoginTest from a terminal.");
            return;
        }

        String email = console.readLine("Email: ");
        char[] password = console.readPassword("Password: ");
        if (email == null || password == null) {
            return;
        }

        try {
            var session = new SupabaseAuthService()
                    .signIn(email.trim(), new String(password));
            System.out.println("Signed in as " + session.userId() + session.userEmail());

            var storage = new SupaBaseStorageService();
            if (Arrays.asList(args).contains("--list")) {
                var files = storage.listFiles(session.accessToken(), session.userId());
                System.out.println("Files found: " + files.size());
                for (var storedFile : files) {
                    System.out.println(storedFile.objectKey());
                }
                return;
            }

            Path file = Path.of("/Users/travismohlin/output");
            var response = storage.upload(file, session.accessToken(), session.userId());
            System.out.println("Upload HTTP status: " + response.statusCode());
        } catch (SignInException e) {
            System.out.println(e.getMessage());
        } finally {
            Arrays.fill(password, '\0');
        }
    }
}
