package example.demo.game;

public abstract class Handler {
    private Handler processor;
    protected int winAmount = 0;  // Решение проблемы - хранение выигрыша

    public Handler(Handler processor) {
        this.processor = processor;
    }

    public boolean process(Integer request) {
        if (processor != null)
            return processor.process(request); // передача по йепочке
        else
            return true;
    }

    public int getWinAmount() {
        return winAmount;
    }
}
