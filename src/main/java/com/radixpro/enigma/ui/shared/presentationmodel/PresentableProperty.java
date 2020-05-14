/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.ui.shared.presentationmodel;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Enables the use of a property and its value in a TableView.
 */
public class PresentableProperty {

   private final String name;
   private final String value;

   /**
    * Constructor defines all members.
    *
    * @param name  Name of the property.
    * @param value Value of the property.
    */
   public PresentableProperty(final String name, final String value) {
      this.name = checkNotNull(name);
      this.value = checkNotNull(value);
   }

   public String getName() {
      return name;
   }

   public String getValue() {
      return value;
   }
}
