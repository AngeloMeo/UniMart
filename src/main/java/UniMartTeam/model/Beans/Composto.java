package UniMartTeam.model.Beans;

public class Composto
{
   private float prezzo, quantita;
   private Ordine ordine;
   private Prodotto prodotto;

   public float getPrezzo()
   {
      return prezzo;
   }

   public void setPrezzo(float prezzo)
   {
      this.prezzo = Math.abs(prezzo);
   }

   public float getQuantita()
   {
      return quantita;
   }

   public void setQuantita(float quantita)
   {
      this.quantita = quantita;
   }

   public Ordine getOrdine()
   {
      return ordine;
   }

   public void setOrdine(Ordine ordine)
   {
      this.ordine = ordine;
   }

   public Prodotto getProdotto()
   {
      return prodotto;
   }

   public void setProdotto(Prodotto prodotto)
   {
      this.prodotto = prodotto;
   }
}