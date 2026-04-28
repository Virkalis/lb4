package example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import example.demo.game.Player;
import example.demo.game.ActionChain;

public class HelloController {

    @FXML
    private Label balanceLabel;

    @FXML
    private ImageView coinImage;

    @FXML
    private Button bag1;

    @FXML
    private Button bag2;

    @FXML
    private Button bag3;

    @FXML
    private Button startButton;

    @FXML
    private Label infoLabel;

    private Player player1;
    private ActionChain action;

    @FXML
    public void initialize() {
        player1 = new Player("Игрок", 3);
        updateBalance();
        setBagsEnabled(false);

        // Простая имитация монетки (цветной прямоугольник)
        coinImage.setStyle("-fx-background-color: #FFD700; -fx-border-color: #DAA520; -fx-border-width: 2px; -fx-border-radius: 50%; -fx-background-radius: 50%;");
        infoLabel.setText("У тебя 3 монеты! Нажми 'Кинуть монетку'");
    }

    private void updateBalance() {
        balanceLabel.setText(String.valueOf(player1.getNumber()));
    }

    private void setBagsEnabled(boolean enabled) {
        bag1.setDisable(!enabled);
        bag2.setDisable(!enabled);
        bag3.setDisable(!enabled);

        if (enabled) {
            bag1.setStyle("-fx-font-size: 16px; -fx-padding: 25px; -fx-background-color: #DEB887; -fx-min-width: 120px; -fx-font-weight: bold;");
            bag2.setStyle("-fx-font-size: 16px; -fx-padding: 25px; -fx-background-color: #DEB887; -fx-min-width: 120px; -fx-font-weight: bold;");
            bag3.setStyle("-fx-font-size: 16px; -fx-padding: 25px; -fx-background-color: #DEB887; -fx-min-width: 120px; -fx-font-weight: bold;");
        } else {
            bag1.setStyle("-fx-font-size: 16px; -fx-padding: 25px; -fx-background-color: #AAAAAA; -fx-min-width: 120px; -fx-font-weight: bold;");
            bag2.setStyle("-fx-font-size: 16px; -fx-padding: 25px; -fx-background-color: #AAAAAA; -fx-min-width: 120px; -fx-font-weight: bold;");
            bag3.setStyle("-fx-font-size: 16px; -fx-padding: 25px; -fx-background-color: #AAAAAA; -fx-min-width: 120px; -fx-font-weight: bold;");
        }
    }

    @FXML
    private void onPay() {
        player1.addNumber(1);
        updateBalance();
        infoLabel.setText("Добавлена монета! Теперь у тебя " + player1.getNumber() + " монет");
    }

    @FXML
    private void onStart() {
        if (action != null) {
            infoLabel.setText("Игра уже идет! Выбери мешочек");
            return;
        }

        if (!init()) {
            return;
        }

        coinImage.setStyle("-fx-background-color: #B8860B; -fx-border-color: #DAA520; -fx-border-width: 2px; -fx-border-radius: 50%; -fx-background-radius: 50%;");
        action = new ActionChain();
        setBagsEnabled(true);
        startButton.setDisable(true);
        startButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-background-color: gray; -fx-text-fill: white; -fx-font-weight: bold; -fx-min-width: 150px;");
        infoLabel.setText("Монетка брошена! Выбери один из мешочков");
    }

    private boolean init() {
        if (!player1.pay(1)) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setHeaderText("Средств на счете недостаточно, еще монетку плисс!");
            alert.show();
            action = null;
            coinImage.setStyle("-fx-background-color: #FFD700; -fx-border-color: #DAA520; -fx-border-width: 2px; -fx-border-radius: 50%; -fx-background-radius: 50%;");
            return false;
        }
        updateBalance();
        return true;
    }

    private void processBagChoice() {
        if (action == null) return;

        boolean result = action.process();

        // Получаем выигрыш через дополнительный метод
        int winAmount = action.getLastWinAmount();

        if (result) {
            // Игрок продолжает
            infoLabel.setText("Продолжаем игру!");
            if (winAmount > 0) {
                player1.addNumber(winAmount);
                updateBalance();
                infoLabel.setText("Ты выиграл " + winAmount + " монет(ы) и продолжаешь играть!");
            } else if (winAmount == -2) {
                infoLabel.setText("Ты выиграл, но продолжаешь играть на выигранное!");
            }
            // Продолжаем игру - мешочки остаются активными
        } else {
            // Игрок заканчивает игру
            if (winAmount > 0) {
                player1.addNumber(winAmount);
                updateBalance();
                infoLabel.setText("ПОЗДРАВЛЯЮ! Ты выиграл " + winAmount + " монет и забрал выигрыш!");
            } else if (winAmount == -2) {
                // Уже обработано в обработчике
            } else {
                infoLabel.setText("Игра окончена. Нажми 'Кинуть монетку' чтобы начать заново");
            }

            action = null;
            setBagsEnabled(false);
            startButton.setDisable(false);
            startButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-min-width: 150px;");
            coinImage.setStyle("-fx-background-color: #FFD700; -fx-border-color: #DAA520; -fx-border-width: 2px; -fx-border-radius: 50%; -fx-background-radius: 50%;");
        }
    }

    @FXML
    private void onBag1Click() {
        if (action != null) {
            infoLabel.setText("Ты выбрал Мешочек 1");
            processBagChoice();
        }
    }

    @FXML
    private void onBag2Click() {
        if (action != null) {
            infoLabel.setText("Ты выбрал Мешочек 2");
            processBagChoice();
        }
    }

    @FXML
    private void onBag3Click() {
        if (action != null) {
            infoLabel.setText("Ты выбрал Мешочек 3");
            processBagChoice();
        }
    }
}