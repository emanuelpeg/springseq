package com.assembly.springseq.ui;

import com.assembly.springseq.service.GameService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Route("game")
@Component
public class MainView extends VerticalLayout {

    private final GameService gameService;
    private Text label1;
    private Text label2;
    private Text label4;
    private TextField solution;

    @Autowired
    public MainView(GameService gameService) {
        this.gameService = gameService;
        this.init();
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
                event -> onIsOk(gameId)
                );

        add(div1, div2, solution, div4, button);
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
    }

}