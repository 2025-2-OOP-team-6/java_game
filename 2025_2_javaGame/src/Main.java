import Logic.Core;

public class Main {
    public static void main(String[] args) {

        Core core = new Core();

        core.initCore();
        if(!core.run())
        {
            core.end();
        }
    }
}