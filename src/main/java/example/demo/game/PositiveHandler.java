package example.demo.game;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class PositiveHandler extends Handler {
    public static final int SUCCESS = 2;
    private static final int WIN_AMOUNT = 3;

    public PositiveHandler(Handler processor) {
        super(processor);
    }

    public boolean process(Integer request) {
        if (request != SUCCESS) {
            return super.process(request);
        } else {
            winAmount = WIN_AMOUNT;

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Поздравляем!");
            alert.setHeaderText("Вы выиграли " + WIN_AMOUNT + " монеты!");
            alert.setContentText("Забрать выигрыш или продолжить играть?");

            ButtonType takeMoney = new ButtonType("Забрать выигрыш", ButtonBar.ButtonData.YES);
            ButtonType continuePlay = new ButtonType("Играть дальше", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(takeMoney, continuePlay);
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().getButtonData() == ButtonBar.ButtonData.YES) {
                return false;  // Забрал выигрыш - завершить
            } else {
                winAmount = -2;  // Специальный код: выиграл но продолжает
                return true;     // Продолжает играть
            }
        }
    }
}