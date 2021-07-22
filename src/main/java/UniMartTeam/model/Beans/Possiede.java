package UniMartTeam.model.Beans;

import java.io.Serializable;
import java.util.Objects;

public class Possiede implements Serializable
{
   private Inventario inventario;
   private Prodotto prodotto;
   private float giacenza;

   public Inventario getInventario()
   {
      return inventario;
   }

   public void setInventario(Inventario inventario)
   {
      this.inventario = inventario;
   }

   public Prodotto getProdotto()
   {
      return prodotto;
   }

   public void setProdotto(Prodotto prodotto)
   {
      this.prodotto = prodotto;
   }

   @Override
   public String toString() {
      return getClass().getName()+":"+"Codice Inventario:"+inventario.getCodiceInventario()+
              " Prodotto:IAN " + prodotto.getCodiceIAN() + " Giacenza: " + getGiacenza();
   }

   @Override
   public boolean equals(Object p){
      return this.toString().equals(p.toString());
   }

   public float getGiacenza()
   {
      return giacenza;
   }

   public void setGiacenza(float giacenza)
   {
      this.giacenza = giacenza;
   }
}