/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.creators;

import com.radixpro.enigma.Rosetta;
import javafx.scene.control.Button;
import org.jetbrains.annotations.NotNull;

/**
 * Creates a Button, based on the Builder pattern.</br>
 * If a text is entered, this will overwrite the value as indicated with rbKey (key to the resourcebundle).
 */
public class ButtonBuilder {

   private final String rbKey;
   private String text = "";
   private boolean disabled = false;
   private boolean focusTraversable = false;

   public ButtonBuilder(@NotNull final String rbKey) {
      this.rbKey = rbKey;
   }

   public ButtonBuilder setText(@NotNull final String text) {
      this.text = text;
      return this;
   }

   public ButtonBuilder setDisabled(final boolean disabled) {
      this.disabled = disabled;
      return this;
   }

   public ButtonBuilder setFocusTraversable(final boolean focusTraversable) {
      this.focusTraversable = focusTraversable;
      return this;
   }

   public Button build() {
      String btnText = "";
      if (!text.isEmpty()) btnText = text;
      else btnText = (rbKey.isEmpty() ? "" : Rosetta.getText(rbKey));
      Button button = new Button(btnText);
      button.setDisable(disabled);
      button.setFocusTraversable(focusTraversable);
      return button;
   }

}
