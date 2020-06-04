/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.factories;

import javafx.scene.control.Button;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Creates a Button, based on the Builder pattern.
 */
public class ButtonBuilder {

   private final String text;
   private boolean disabled = false;
   private boolean focusTraversable = false;

   public ButtonBuilder(final String text) {
      this.text = checkNotNull(text);
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
      Button button = new Button(text);
      button.setDisable(disabled);
      button.setFocusTraversable(focusTraversable);
      return button;
   }

}
