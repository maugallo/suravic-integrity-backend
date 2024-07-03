package edu.usal.suravicIntegrity.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/* Embeddable representa una tabla intermedia sin necesidad de definir una entidad separada.
   Se utiliza en este caso ya que la tabla intermedia entre Employee y ClothingItem
   tiene un atributo adicional además de los id (size). */

@Embeddable
public class EmployeesClothingItems {

    @Enumerated(EnumType.STRING)
    private ClothingItem clothingItem;

    @NotBlank
    @Size(min = 1, max = 3)
    private String size;

    public ClothingItem getClothingItem() {
        return clothingItem;
    }

    public void setClothingItem(ClothingItem clothingItem) {
        this.clothingItem = clothingItem;
    }

    public @NotBlank @Size(min = 1, max = 3) String getSize() {
        return size;
    }

    public void setSize(@NotBlank @Size(min = 1, max = 3) String size) {
        this.size = size;
    }
}
