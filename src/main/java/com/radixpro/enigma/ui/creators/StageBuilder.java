/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.creators;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Creates a Stage, based on the Builder pattern.
 */
public class StageBuilder {

   private double minWidth;
   private double minHeight;
   private String title;
   private Modality modality;
   private Scene scene;

   public StageBuilder setMinWidth(final double mindWidth) {
      checkArgument(mindWidth > 0.0);
      this.minWidth = mindWidth;
      return this;
   }

   public StageBuilder setMinHeight(final double minHeight) {
      checkArgument(minHeight > 0.0);
      this.minHeight = minHeight;
      return this;
   }

   public StageBuilder setTitle(@NotNull final String title) {
      checkArgument(!title.isBlank());
      this.title = title;
      return this;
   }

   public StageBuilder setModality(@NotNull final Modality modality) {
      this.modality = modality;
      return this;
   }

   public StageBuilder setScene(@NotNull final Scene scene) {
      this.scene = scene;
      return this;
   }

   public Stage build() {
      Stage stage = new Stage();
      if (minWidth > 0.0) stage.setMinWidth(minWidth);
      if (minHeight > 0.0) stage.setMinHeight(minHeight);
      if (null != title && !title.isEmpty()) stage.setTitle(title);
      if (null != modality) stage.initModality(modality);
      if (null != scene) stage.setScene(scene);
      return stage;
   }


}
