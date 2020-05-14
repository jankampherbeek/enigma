/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */

package com.radixpro.enigma.xchg.domain.config;

import static com.google.common.base.Preconditions.checkNotNull;

public class Configuration {

   private long id;
   private long parentId;
   private String name;
   private String description;
   private AstronConfiguration astronConfiguration;
   private DelinConfiguration delinConfiguration;


   public Configuration(final long id, final long parentId, final String name, final String description,
                        final AstronConfiguration astronConfiguration, final DelinConfiguration delinConfiguration) {
      this.id = id;
      this.parentId = parentId;
      this.name = checkNotNull(name);
      this.description = checkNotNull(description);
      this.astronConfiguration = checkNotNull(astronConfiguration);
      this.delinConfiguration = checkNotNull(delinConfiguration);
   }

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public long getParentId() {
      return parentId;
   }

   public void setParentId(long parentId) {
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

   @Override
   public String toString() {
      return String.format("Configuration(id=%d, parentId=%d, name=%s, description=%s, astronConfiguration=%s, delinConfiguration=%s)",
            id, parentId, name, description, astronConfiguration, delinConfiguration);
   }
}
