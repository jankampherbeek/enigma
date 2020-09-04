/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import org.jetbrains.annotations.NotNull;

/**
 * Enables the use of a property and its value in a TableView.
 */
public class PresentableProperty {

   private final String name;
   private final String value;

   public PresentableProperty(@NotNull final String name, @NotNull final String value) {
      this.name = name;
      this.value = value;
   }

   public String getName() {
      return name;
   }

   public String getValue() {
      return value;
   }
}
