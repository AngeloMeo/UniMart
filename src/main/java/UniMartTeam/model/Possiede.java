package UniMartTeam.model;

public class Possiede
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

   public float getGiacenza()
   {
      return giacenza;
   }

   public void setGiacenza(float giacenza)
   {
      this.giacenza = giacenza;
   }
}