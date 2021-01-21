/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.screens;

import com.radixpro.enigma.Rosetta;
import com.radixpro.enigma.domain.config.Configuration;
import com.radixpro.enigma.domain.config.ConfiguredCelObject;
import com.radixpro.enigma.domain.input.DateTimeJulian;
import com.radixpro.enigma.domain.input.Location;
import com.radixpro.enigma.domain.reqresp.EphProgCalcRequest;
import com.radixpro.enigma.domain.reqresp.IProgCalcRequest;
import com.radixpro.enigma.domain.reqresp.SimpleProgResponse;
import com.radixpro.enigma.references.Ayanamshas;
import com.radixpro.enigma.references.EclipticProjections;
import com.radixpro.enigma.references.InputStatus;
import com.radixpro.enigma.references.ObserverPositions;
import com.radixpro.enigma.shared.FailFastHandler;
import com.radixpro.enigma.shared.exceptions.InputBlockIncompleteException;
import com.radixpro.enigma.ui.creators.LabelBuilderObs;
import com.radixpro.enigma.ui.creators.PaneBuilder;
import com.radixpro.enigma.ui.screens.blocks.DateTimeInputBlock;
import com.radixpro.enigma.ui.screens.blocks.LocationInputBlock;
import com.radixpro.enigma.ui.screens.blocks.ProgMetaInputBlock;
import com.radixpro.enigma.xchg.api.TransitsApi;
import com.radixpro.enigma.xchg.api.settings.ICalcSettings;
import com.radixpro.enigma.xchg.api.settings.ProgSettings;
import com.radixpro.enigma.xchg.domain.IChartPoints;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.radixpro.enigma.ui.shared.UiDictionary.*;

/**
 * Input screen for an event for which to calculate transits.
 */
public class ChartsTransitsInput extends InputScreen {
   private static final Logger LOG = Logger.getLogger(ChartsTransitsInput.class);
   private static final double HEIGHT = 600.0;
   private Label lblPageTitle;
   private Label lblSubTitleDateAndTime;
   private Label lblSubTitleGeneral;
   private Label lblSubTitleLocation;
   private Pane panePageTitle;
   private Pane paneSubTitleDateAndTime;
   private Pane paneSubTitleGeneral;
   private Pane paneSubTitleLocation;
   private InputStatus inputStatus = InputStatus.INCOMPLETE;
   private final ProgMetaInputBlock progMetaInputBlock;
   private final LocationInputBlock locationInputBlock;
   private final DateTimeInputBlock dateTimeInputBlock;
   private final TransitsApi transitsApi;

   /**
    * @param progMetaInputBlock Input block for meta info.
    * @param locationInputBlock Input block for location.
    * @param dateTimeInputBlock Input block for date and time.
    * @param transitsApi        Api for the calculation of transits.
    */
   public ChartsTransitsInput(@NotNull final ProgMetaInputBlock progMetaInputBlock, @NotNull final LocationInputBlock locationInputBlock,
                              @NotNull final DateTimeInputBlock dateTimeInputBlock, @NotNull final TransitsApi transitsApi) {
      this.progMetaInputBlock = progMetaInputBlock;
      this.locationInputBlock = locationInputBlock;
      this.dateTimeInputBlock = dateTimeInputBlock;
      this.transitsApi = transitsApi;
   }

   public void show() {
      defineLeafs();
      definePanes();
      defineStructure();
      stage.setScene(new Scene(createVBox()));
      stage.showAndWait();
   }

   private void defineLeafs() {
      lblPageTitle = new LabelBuilderObs("ui.charts.transitsinput.pagetitle").setPrefWidth(INPUT_WIDTH).setStyleClass("titletext").build();
      lblSubTitleGeneral = new LabelBuilderObs("ui.charts.input.subtitle.general").setPrefWidth(INPUT_WIDTH).setStyleClass(STYLE_SUBTITLE_TEXT).build();
      lblSubTitleLocation = new LabelBuilderObs("ui.charts.input.subtitle.location").setPrefWidth(INPUT_WIDTH).setStyleClass(STYLE_SUBTITLE_TEXT).build();
      lblSubTitleDateAndTime = new LabelBuilderObs("ui.charts.input.subtitle.dateandtime").setPrefWidth(INPUT_WIDTH).setStyleClass(STYLE_SUBTITLE_TEXT).build();
   }

   private void definePanes() {
      panePageTitle = new PaneBuilder().setHeight(TITLE_HEIGHT).setWidth(INPUT_WIDTH).setStyleClass("titlepane").build();
      paneSubTitleGeneral = new PaneBuilder().setHeight(SUBTITLE_HEIGHT).setWidth(INPUT_WIDTH).setStyleClass(STYLE_SUBTITLE_PANE).build();
      paneSubTitleLocation = new PaneBuilder().setHeight(SUBTITLE_HEIGHT).setWidth(INPUT_WIDTH).setStyleClass(STYLE_SUBTITLE_PANE).build();
      paneSubTitleDateAndTime = new PaneBuilder().setHeight(SUBTITLE_HEIGHT).setWidth(INPUT_WIDTH).setStyleClass(STYLE_SUBTITLE_PANE).build();
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
      vBox.getChildren().addAll(panePageTitle, paneSubTitleGeneral, progMetaInputBlock.getVBox(), paneSubTitleLocation, createGridPaneLocation(),
            paneSubTitleDateAndTime, dateTimeInputBlock.getGridPane(), createPaneBtnBar());
      return vBox;
   }

   private GridPane createGridPaneLocation() {
      // TODO only show location if config indicates topocentric calculations, otherwise replace with short text
      return locationInputBlock.getGridPane();
   }

   @Override
   protected ButtonBar createBtnBar() {
      ButtonBar buttonBar = new ButtonBar();
      Button calculatebtn = new Button(Rosetta.getText("ui.shared.btn.calculate"));
      Button helpBtn = new Button(Rosetta.getText("ui.shared.btn.help"));
      Button cancelBtn = new Button(Rosetta.getText("ui.shared.btn.cancel"));
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
            String eventDescription = progMetaInputBlock.getEventDescription();
            DateTimeJulian dateTime = dateTimeInputBlock.getDateTime();
            Location location = locationInputBlock.getLocation();

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
            SimpleProgResponse response = transitsApi.calculateTransits(request);
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
      // TODO create help for input transits
//      new Help(Rosetta.getHelpText("help.chartsinput.title"), Rosetta.getHelpText("help.chartsinput.content"));
   }

   @Override
   public void checkStatus() {
      boolean inputOk = (locationInputBlock.getInputStatus() == InputStatus.READY
            && (dateTimeInputBlock.getInputStatus() == InputStatus.READY)
            && (progMetaInputBlock.getInputStatus() == InputStatus.READY));
      if (inputOk) inputStatus = InputStatus.READY;
   }

}
