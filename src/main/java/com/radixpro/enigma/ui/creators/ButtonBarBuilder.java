/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.ui.creators;

import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import org.jetbrains.annotations.NotNull;


public class ButtonBarBuilder {

   private Node[] buttons;

   public ButtonBarBuilder setButtons(@NotNull final Node... buttons) {
      this.buttons = buttons;
      return this;
   }

   public ButtonBar build() {
      ButtonBar buttonBar = new ButtonBar();
      if (null != buttons && buttons.length > 0) buttonBar.getButtons().addAll(buttons);
      return buttonBar;
   }

}
