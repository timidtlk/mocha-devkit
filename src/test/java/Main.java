import org.mocha.Application;
import org.mocha.annotations.Window;

@Window(width = 640, height = 480, title = "Test")
public class Main extends Application {
    Main() {
        super();
        init();
    }

    public static void main(String[] args) {
        new Main();
    }
}
