/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.charts.screens;

import com.radixpro.enigma.shared.FailFastHandler;
import com.radixpro.enigma.ui.exceptions.InputBlockIncompleteException;
import com.radixpro.enigma.ui.shared.InputStatus;
import com.radixpro.enigma.ui.shared.creators.LabelFactory;
import com.radixpro.enigma.ui.shared.creators.PaneFactory;
import com.radixpro.enigma.ui.shared.screenblocks.DateTimeInput;
import com.radixpro.enigma.ui.shared.screenblocks.LocationInput;
import com.radixpro.enigma.ui.shared.screenblocks.ProgMetaInput;
import com.radixpro.enigma.ui.shared.screens.InputScreen;
import com.radixpro.enigma.xchg.api.ApiFactory;
import com.radixpro.enigma.xchg.api.requests.EphProgCalcRequest;
import com.radixpro.enigma.xchg.api.requests.IProgCalcRequest;
import com.radixpro.enigma.xchg.api.responses.SimpleProgResponse;
import com.radixpro.enigma.xchg.api.settings.ICalcSettings;
import com.radixpro.enigma.xchg.api.settings.ProgSettings;
import com.radixpro.enigma.xchg.domain.*;
import com.radixpro.enigma.xchg.domain.config.Configuration;
import com.radixpro.enigma.xchg.domain.config.ConfiguredCelObject;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.radixpro.enigma.ui.shared.UiDictionary.*;

/**
 * Input screen for an event for which to calculate transits.
 */
public class ChartsTransitsInput extends InputScreen {
   private static final Logger LOG = Logger.getLogger(ChartsTransitsInput.class);
   private static final double HEIGHT = 600.0;
   private static final String STYLE_SUBTITLE_TEXT = "subtitletext";
   private static final String STYLE_SUBTITLE_PANE = "subtitlepane";
   private Label lblPageTitle;
   private Label lblSubTitleDateAndTime;
   private Label lblSubTitleGeneral;
   private Label lblSubTitleLocation;
   private Pane panePageTitle;
   private Pane paneSubTitleDateAndTime;
   private Pane paneSubTitleGeneral;
   private Pane paneSubTitleLocation;
   private InputStatus inputStatus = InputStatus.INCOMPLETE;
   private final ProgMetaInput progMetaInput;
   private final LocationInput locationInput;
   private final DateTimeInput dateTimeInput;

   /**
    * Instantiate via factory.
    *
    * @param progMetaInput Input block for meta info. PRE: not null.
    * @param locationInput Input block for location. PRE: not null.
    * @param dateTimeInput Input block for date and time. PRE: not null.
    * @see ChartsScreensFactory
    */
   public ChartsTransitsInput(final ProgMetaInput progMetaInput, final LocationInput locationInput, final DateTimeInput dateTimeInput) {
      this.progMetaInput = checkNotNull(progMetaInput);
      this.locationInput = checkNotNull(locationInput);
      this.dateTimeInput = checkNotNull(dateTimeInput);
      defineLeafs();
      definePanes();
      defineStructure();
      stage.setScene(new Scene(createVBox()));
      stage.showAndWait();
   }

   private void defineLeafs() {
      lblPageTitle = LabelFactory.createLabel(rosetta.getText("ui.charts.transitsinput.pagetitle"), "titletext", INPUT_WIDTH);
      lblSubTitleGeneral = LabelFactory.createLabel(rosetta.getText("ui.charts.input.subtitle.general"), STYLE_SUBTITLE_TEXT, INPUT_WIDTH);
      lblSubTitleLocation = LabelFactory.createLabel(rosetta.getText("ui.charts.input.subtitle.location"), STYLE_SUBTITLE_TEXT, INPUT_WIDTH);
      lblSubTitleDateAndTime = LabelFactory.createLabel(rosetta.getText("ui.charts.input.subtitle.dateandtime"), STYLE_SUBTITLE_TEXT, INPUT_WIDTH);
   }

   private void definePanes() {
      panePageTitle = PaneFactory.createPane(TITLE_HEIGHT, INPUT_WIDTH, "titlepane");
      paneSubTitleGeneral = PaneFactory.createPane(SUBTITLE_HEIGHT, INPUT_WIDTH, STYLE_SUBTITLE_PANE);
      paneSubTitleLocation = PaneFactory.createPane(SUBTITLE_HEIGHT, INPUT_WIDTH, STYLE_SUBTITLE_PANE);
      paneSubTitleDateAndTime = PaneFactory.createPane(SUBTITLE_HEIGHT, INPUT_WIDTH, STYLE_SUBTITLE_PANE);
   }

   private void defineStructure() {
      panePageTitle.getChildren().add(lblPageTitle);
      paneSubTitleGeneral.getChildren().add(lblSubTitleGeneral);
      paneSubTitleLocation.getChildren().add(lblSubTitleLocation);
      paneSubTitleDateAndTime.getChildren().add(lblSubTitleDateAndTime);
   }

   private VBox createVBox() {
      VBox vBox = new VBox();
      vBox.getStylesheets().add(STYLESHEET);
      vBox.setPrefWidth(INPUT_WIDTH);
      vBox.setPrefHeight(HEIGHT);
      vBox.getChildren().addAll(panePageTitle, paneSubTitleGeneral, progMetaInput.getVBox(), paneSubTitleLocation, createGridPaneLocation(),
            paneSubTitleDateAndTime, dateTimeInput.getGridPane(), createPaneBtnBar());
      return vBox;
   }

   private GridPane createGridPaneLocation() {
      // TODO only show location if config indicates topocentric calculations, otherwise replace with short text
      return locationInput.getGridPane();
   }

   @Override
   protected ButtonBar createBtnBar() {
      ButtonBar buttonBar = new ButtonBar();
      Button calculatebtn = new Button(rosetta.getText("ui.shared.btn.calculate"));
      Button helpBtn = new Button(rosetta.getText("ui.shared.btn.help"));
      Button cancelBtn = new Button(rosetta.getText("ui.shared.btn.cancel"));
      calculatebtn.setOnAction(click -> onCalculate());
      helpBtn.setOnAction(click -> onHelp());
      cancelBtn.setOnAction(click -> onCancel());
      buttonBar.getButtons().addAll(helpBtn, cancelBtn, calculatebtn);
      return buttonBar;
   }

   private void onCalculate() {
      checkStatus();
      if (inputStatus == InputStatus.READY) {
         try {
            String eventDescription = progMetaInput.getEventDescription();
            FullDateTime dateTime = dateTimeInput.getDateTime();
            Location location = locationInput.getLocation();


            // TODO add creation of settings to Config class
            Configuration config = state.getSelectedConfig();
            List<ConfiguredCelObject> confPoints = config.getAstronConfiguration().getCelObjects();
            List<IChartPoints> points = new ArrayList<>();
            for (ConfiguredCelObject cPoint : confPoints) {
               points.add(cPoint.getCelObject());
            }
            ObserverPositions obsPos = config.getAstronConfiguration().getObserverPosition();
            EclipticProjections eclProj = config.getAstronConfiguration().getEclipticProjection();
            Ayanamshas ayanamsha = config.getAstronConfiguration().getAyanamsha();
            ICalcSettings settings = new ProgSettings(points, ayanamsha, eclProj == EclipticProjections.SIDEREAL, obsPos == ObserverPositions.TOPOCENTRIC);
            IProgCalcRequest request = new EphProgCalcRequest(dateTime, location, settings);
            SimpleProgResponse response = ApiFactory.getTransitsApi().calculateTransits(request);
            System.out.print(response.getPositions().size());
            // TODO transfer results of response to screen that shows positions.

         } catch (InputBlockIncompleteException e) {
            // should be impossible unless checkStatus() has not been executed.
            String errorMsg = "Exception when reading from block that does not have InputStatus.READY. Terminating application. Original message : "
                  + e.getMessage();
            LOG.error(errorMsg);
            new FailFastHandler().terminate(errorMsg);
         }
         stage.close();
      } else {
         popupCheckErrors();
      }
   }

   private void onHelp() {

//      new Help(rosetta.getHelpText("help.chartsinput.title"), rosetta.getHelpText("help.chartsinput.content"));
   }

   private void checkStatus() {
      boolean inputOk = (locationInput.getInputStatus() == InputStatus.READY
            && (dateTimeInput.getInputStatus() == InputStatus.READY)
            && (progMetaInput.getInputStatus() == InputStatus.READY));
      if (inputOk) inputStatus = InputStatus.READY;
   }

}
