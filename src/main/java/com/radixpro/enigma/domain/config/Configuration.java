/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.domain.config;

import static com.google.common.base.Preconditions.checkNotNull;

public class Configuration {

   private int id;
   private int parentId;
   private String name;
   private String description;
   private AstronConfiguration astronConfiguration;
   private DelinConfiguration delinConfiguration;


   public Configuration(final int id, final int parentId, final String name, final String description,
                        final AstronConfiguration astronConfiguration, final DelinConfiguration delinConfiguration) {
      this.id = id;
      this.parentId = parentId;
      this.name = checkNotNull(name);
      this.description = checkNotNull(description);
      this.astronConfiguration = checkNotNull(astronConfiguration);
      this.delinConfiguration = checkNotNull(delinConfiguration);
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public int getParentId() {
      return parentId;
   }

   public void setParentId(int parentId) {
      this.parentId = parentId;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public AstronConfiguration getAstronConfiguration() {
      return astronConfiguration;
   }

   public void setAstronConfiguration(AstronConfiguration astronConfiguration) {
      this.astronConfiguration = astronConfiguration;
   }

   public DelinConfiguration getDelinConfiguration() {
      return delinConfiguration;
   }

   public void setDelinConfiguration(DelinConfiguration delinConfiguration) {
      this.delinConfiguration = delinConfiguration;
   }

}
