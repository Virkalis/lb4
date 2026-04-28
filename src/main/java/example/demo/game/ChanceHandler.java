package example.demo.game;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class ChanceHandler extends Handler {
    public static final int CHANCE = 3;

    public ChanceHandler(Handler processor) {
        super(processor);
    }

    public boolean process(Integer request) {
        if (request != CHANCE) {
            return super.process(request);
        } else {
            winAmount = 0;

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Вам выпал шанс!");
            alert.setHeaderText("Но судьба дает Вам шанс сыграть еще раз бесплатно!");

            ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().clear();
            alert.getButtonTypes().add(ok);
            Optional<ButtonType> option = alert.showAndWait();

            return true;  // Бесплатное продолжение
        }
    }
}