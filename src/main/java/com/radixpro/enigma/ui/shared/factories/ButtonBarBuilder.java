/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.shared.factories;

import javafx.scene.Node;
import javafx.scene.control.ButtonBar;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Creates a ButtonBar, based on the Builder pattern.
 */
public class ButtonBarBuilder {

   private Node[] buttons;

   public ButtonBarBuilder setButtons(final Node... buttons) {
      this.buttons = checkNotNull(buttons);
      return this;
   }

   public ButtonBar build() {
      ButtonBar buttonBar = new ButtonBar();
      if (null != buttons && buttons.length > 0) buttonBar.getButtons().addAll(buttons);
      return buttonBar;
   }

}
