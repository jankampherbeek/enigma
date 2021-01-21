/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.ui.creators.ButtonBuilderObs;
import com.radixpro.enigma.ui.creators.LabelBuilderObs;
import com.radixpro.enigma.ui.creators.PaneBuilder;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import static com.radixpro.enigma.ui.shared.UiDictionary.STYLESHEET;

/**
 * Display of help text. Handles all help texts.
 */
public class Help {

   private static final double GAP = 6.0;
   private static final double INNER_WIDTH = 560.0;
   private static final double OUTER_WIDTH = 572.0;
   private static final double TITLE_HEIGHT = 40.0;
   private static final String PREFIX = "<div style=\"font-family: sans-serif, Arial; font-size: 12px; background-color: white;\">";
   private static final String POSTFIX = "</div>";
   private final String title;
   private final String content;
   private Stage stage;

   public Help(@NotNull final String title, @NotNull final String content) {
      this.title = title;
      this.content = content;
      showContent();
   }

   private void showContent() {
      stage = new Stage();
      stage.setWidth(OUTER_WIDTH);
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setTitle(Rosetta.getText("ui.helptitle"));
      ButtonBar buttonBar = createButtonBar();
      Pane titlePane = createTitlePane();
      Pane contentPane = createContentPane();
      BorderPane.setAlignment(titlePane, Pos.CENTER);
      BorderPane.setAlignment(contentPane, Pos.CENTER);
      BorderPane.setAlignment(buttonBar, Pos.CENTER);
      BorderPane borderPane = new BorderPane();
      borderPane.setPrefWidth(OUTER_WIDTH);
      borderPane.setPadding(new Insets(GAP, GAP, GAP, GAP));
      borderPane.getStylesheets().add(STYLESHEET);
      borderPane.getStyleClass().add("helppane");
      borderPane.setTop(titlePane);
      borderPane.setCenter(contentPane);
      borderPane.setBottom(buttonBar);
      stage.setScene(new Scene(borderPane));
      stage.show();
   }

   public void onClose() {
      stage.close();
   }

   private Label createLblHelpTitle() {
      return new LabelBuilderObs("").setText(title).setPrefWidth(INNER_WIDTH).setStyleClass("titletext").build();
   }

   private Pane createTitlePane() {
      return new PaneBuilder().setHeight(TITLE_HEIGHT).setWidth(INNER_WIDTH).setStyleClass("titlepane").setChildren(createLblHelpTitle()).build();
   }

   private Pane createContentPane() {
      return new PaneBuilder().setWidth(INNER_WIDTH).setHeight(400.0).setChildren(createContentWebView()).build();
   }

   private ButtonBar createButtonBar() {
      ButtonBar buttonBar = new ButtonBar();
      buttonBar.getStyleClass().add("helppane");
      buttonBar.getButtons().add(createCloseButton());
      return buttonBar;
   }

   private WebView createContentWebView() {
      WebView webView = new WebView();
      WebEngine webEngine = webView.getEngine();
      String fullContent = PREFIX + content + POSTFIX;
      webView.setPrefWidth(INNER_WIDTH - 12);
      webEngine.loadContent(fullContent, "text/html");
      return webView;
   }

   private Button createCloseButton() {
      Button button = new ButtonBuilderObs("ui.shared.btn.exit").setDisabled(false).build();
      button.setOnAction(click -> onClose());
      return button;
   }

}
