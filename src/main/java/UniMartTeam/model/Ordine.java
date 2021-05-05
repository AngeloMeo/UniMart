package UniMartTeam.model;

import java.time.LocalDate;

public class Ordine
{
   private int numeroOrdine;
   private StatoOrdine statoOrdine;
   private String feedback, metodoDiPagamento;
   private LocalDate dataAcquisto;
   private Utente cliente;
   private Coupon coupon;
   private Spedizione spedizione;

   public Spedizione getSpedizione()
   {
      return spedizione;
   }

   public void setSpedizione(Spedizione spedizione)
   {
      this.spedizione = spedizione;
   }

   public Coupon getCoupon()
   {
      return coupon;
   }

   public void setCoupon(Coupon coupon)
   {
      this.coupon = coupon;
   }

   public int getNumeroOrdine()
   {
      return numeroOrdine;
   }

   public void setNumeroOrdine(int numeroOrdine)
   {
      this.numeroOrdine = numeroOrdine;
   }

   public StatoOrdine getStato()
   {
      return statoOrdine;
   }

   public void setStato(StatoOrdine statoOrdine)
   {
      this.statoOrdine = statoOrdine;
   }

   public String getFeedback()
   {
      return feedback;
   }

   public void setFeedback(String feedback)
   {
      this.feedback = feedback;
   }

   public String getMetodoDiPagamento()
   {
      return metodoDiPagamento;
   }

   public void setMetodoDiPagamento(String metodoDiPagamento)
   {
      this.metodoDiPagamento = metodoDiPagamento;
   }

   public LocalDate getDataAcquisto()
   {
      return dataAcquisto;
   }

   public void setDataAcquisto(LocalDate dataAcquisto)
   {
      this.dataAcquisto = dataAcquisto;
   }

   public Utente getCliente()
   {
      return cliente;
   }

   public void setCliente(Utente cliente)
   {
      this.cliente = cliente;
   }
}
