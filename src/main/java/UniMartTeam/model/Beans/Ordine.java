package UniMartTeam.model.Beans;

import UniMartTeam.model.EnumForBeans.StatoOrdine;

import java.time.LocalDate;
import java.util.List;

public class Ordine
{
   private int numeroOrdine;
   private StatoOrdine statoOrdine;
   private String feedback, metodoDiPagamento;
   private LocalDate dataAcquisto;
   private Utente cliente;
   private Coupon coupon;
   private Spedizione spedizione;
   private List<Composto> compostoList;

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

   public void setCompostoList(List<Composto> compostoList)
   {
      this.compostoList = compostoList;
   }

   public void addCompostoList(Composto element)
   {
      if (element != null)
         compostoList.add(element);
   }

   public List<Composto> getCompostoList()
   {
      return compostoList;
   }

   public StatoOrdine getStatoOrdine()
   {
      return statoOrdine;
   }

   public void setStatoOrdine(StatoOrdine statoOrdine)
   {
      this.statoOrdine = statoOrdine;
   }
}
