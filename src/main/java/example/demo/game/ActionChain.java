package example.demo.game;

import java.util.Random;

public class ActionChain {
    public Handler chain;
    public static int SUCCESS = 2;
    public static int LOSS = 1;
    public static int CHANCE = 3;

    private Random generate;
    private final int NUMHANDLER = 4;
    private int lastWinAmount = 0;

    public ActionChain() {
        generate = new Random();
        buildChain();
    }

    private void buildChain() {
        chain = new NegativeHandler(new PositiveHandler(new ChanceHandler(null)));
    }

    public boolean process() {
        int type = generate.nextInt(NUMHANDLER);
        return process(type);
    }

    public boolean process(Integer a) {
        int requestType = 1 + a % NUMHANDLER;
        boolean result = chain.process(requestType);
        lastWinAmount = chain.getWinAmount();
        return result;
    }

    public int getLastWinAmount() {
        return lastWinAmount;
    }
}