package com.assembly.springseq.ui;

import com.assembly.springseq.service.GameService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


@Route("game")
public class MainView extends VerticalLayout {

    private static final Logger log = LoggerFactory.getLogger(MainView.class);

    private final GameService gameService;
    private Text label1;
    private Text label2;
    private Text label4;
    private TextField solution;
    private Text labelPoints;

    @Autowired
    public MainView(GameService gameService) {
        this.gameService = gameService;
        callServerMethod(this::init);
    }

    private void callServerMethod(MethodServer fx) {
        try {
            fx.apply();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            Notification.show("Error: " + ex.getMessage());
        }
    }

    public void init() {
        var gameId = this.gameService.startGame();
        var sequence = this.gameService.getSequence(gameId.toString());
        this.label1 = new Text(String.valueOf(sequence.getFirst()));
        this.label2 = new Text(String.valueOf(sequence.getSecond()));

        this.solution = new TextField("Your answer: ");
        this.solution.setRequired(true);

        this.label4 = new Text(String.valueOf(sequence.getFour()));

        var div1 = new Div(this.label1);
        var div2 = new Div(this.label2);
        var div4 = new Div(this.label4);

        div1.getStyle().set("margin-bottom", "10px");
        div2.getStyle().set("margin-bottom", "10px");
        div4.getStyle().set("margin-bottom", "10px");

        Button button = new Button("is ok?",
                event -> callServerMethod(() -> onIsOk(gameId))
        );

        add(div1, div2, solution, div4, button);
        addPoints(gameId.toString());
    }

    private void addPoints(String gameId) {
        var points = this.gameService.getPoints(gameId);
        labelPoints = new Text(String.valueOf(points));
        FlexLayout flexLayout = new FlexLayout();
        flexLayout.setWidthFull();
        flexLayout.setHeightFull();
        add(flexLayout);

        Div box = new Div();
        box.setText("Points: ");
        box.getStyle().set("background-color", "#f0f0f0");
        box.getStyle().set("padding", "5px");
        box.getStyle().set("border", "1px solid #000");
        box.getStyle().set("position", "absolute");
        box.getStyle().set("top", "5px");
        box.getStyle().set("right", "20px");
        box.add(this.labelPoints);

        flexLayout.add(box);
    }

    private void onIsOk(UUID gameId) {
        int nro;
        try {
            nro = Integer.parseInt(this.solution.getValue());
        } catch (Exception ex) {
            Notification.show("Please enter a valid number");
            return;
        }
        var result = this.gameService.isOk(gameId.toString(), nro);
        if (result.getWin()) {
            Notification.show("Won!! Your score is " + result.getPoints());
        } else {
            Notification.show("Lost!! Your score is " + result.getPoints());
        }
        var sequenceAux = this.gameService.getSequence(gameId.toString());
        this.solution.setValue("");
        this.label1.setText(String.valueOf(sequenceAux.getFirst()));
        this.label2.setText(String.valueOf(sequenceAux.getSecond()));
        this.label4.setText(String.valueOf(sequenceAux.getFour()));
        this.labelPoints.setText(String.valueOf(result.getPoints()));
    }

    @FunctionalInterface
    private interface MethodServer {
        void apply();
    }

}