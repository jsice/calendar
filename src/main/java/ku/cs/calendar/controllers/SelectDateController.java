package ku.cs.calendar.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import ku.cs.calendar.utils.CalendarUtils;

import java.util.GregorianCalendar;

/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public class SelectDateController {

    private MainController mainCtrl;
    private Label[] dateLabels;
    private Label selectedLabel;
    private Color selectedColor = Color.ALICEBLUE;
    @FXML
    private GridPane calendarGrid;

    @FXML
    private void initialize() {
        this.dateLabels = new Label[42];
        for (int i = 0; i < 42; i++) {
            this.dateLabels[i] = new Label();
            this.dateLabels[i].setAlignment(Pos.CENTER);
            this.dateLabels[i].setPrefWidth(78);
            this.dateLabels[i].setPrefHeight(68);
            this.dateLabels[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    String text = ((Label)event.getSource()).getText();
                    if (text.equals("")) return;
                    int d = Integer.parseInt(text);
                    selectDate(d, mainCtrl.getShownMonth(), mainCtrl.getShownYear());
                    mainCtrl.clearDetailPane();
                    mainCtrl.showAppointments();
                }
            });
            this.calendarGrid.add(this.dateLabels[i], i%7, (i/7) + 1);
        }
    }

    protected void setMainCtrl(MainController mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    private void clearSelected() {
        if (this.selectedLabel != null) {
            this.selectedLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }

    public void selectDate(int d, int month, int year) {
        clearSelected();
        mainCtrl.setSelectedDate(d);
        mainCtrl.setSelectedMonth(month);
        mainCtrl.setSelectedYear(year);
        mainCtrl.getSelectedDateLabel().setText(CalendarUtils.getMonthName(mainCtrl.getSelectedMonth()) + " " + mainCtrl.getSelectedDate() + ", " + mainCtrl.getSelectedYear());
        if (month == mainCtrl.getShownMonth() && year == mainCtrl.getShownYear()) {
            for (Label dateLabel:dateLabels) {
                if (dateLabel.getText().equals(String.valueOf(d))) {
                    selectedLabel = dateLabel;
                    selectedLabel.setBackground(new Background(new BackgroundFill(selectedColor, CornerRadii.EMPTY, Insets.EMPTY)));
                    break;
                }
            }
        }
    }

    void setDates() {
        int m = mainCtrl.getShownMonth();
        int y = mainCtrl.getShownYear();
        java.util.Calendar d = new GregorianCalendar(y-543, m-1, 1);
        int day = d.get(java.util.Calendar.DAY_OF_WEEK) - 1;
        int dNum = 1;
        for (int i = 0; i < dateLabels.length; i++) {
            if (i >= day && dNum <= CalendarUtils.getMonthDay(m, y)) {
                dateLabels[i].setText(dNum+"");
                dNum++;
            }
            else {
                dateLabels[i].setText("");
            }
            if (i % 7 == 0) {
                dateLabels[i].setTextFill(Color.RED);
            } else {
                dateLabels[i].setTextFill(Color.BLACK);
            }
        }

        java.util.Calendar today = new GregorianCalendar();
        if (today.get(java.util.Calendar.YEAR) + 543 == y &&
                today.get(java.util.Calendar.MONTH) + 1 == m) {
            dateLabels[day - 1 + today.get(java.util.Calendar.DAY_OF_MONTH)].setTextFill(Color.BLUE);
        }
        if (this.mainCtrl.getSelectedMonth() == m && this.mainCtrl.getSelectedYear() == y) {
            this.selectedLabel = dateLabels[day - 1 + this.mainCtrl.getSelectedDate()];
            this.selectedLabel.setBackground(new Background(new BackgroundFill(selectedColor, CornerRadii.EMPTY, Insets.EMPTY)));
        } else {
            this.clearSelected();
        }
    }


}
