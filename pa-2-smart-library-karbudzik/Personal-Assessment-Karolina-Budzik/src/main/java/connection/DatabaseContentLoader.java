package connection;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;

public class DatabaseContentLoader {
    private String dbFilepath = "src/main/resources/setup-library-db.sql";

    public void fillDatabase() {
        try {
            ConnectionInitializer initializer = new ConnectionInitializer();
            Connection connection = initializer.getConnection();
            ScriptRunner sr = new ScriptRunner(connection);
            Reader reader = new BufferedReader(new FileReader(dbFilepath));
            sr.runScript(reader);
            System.out.println("Success! Tables were created!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
