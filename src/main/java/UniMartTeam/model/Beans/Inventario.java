package UniMartTeam.model.Beans;

import java.util.ArrayList;
import java.util.List;

public class Inventario
{
   private int codiceInventario;
   private String indirizzo, regione, nome, note;
   private Utente responsabile;
   private List<Possiede> possiedeList;

   public Inventario()
   {
      this.codiceInventario = 0;
      this.indirizzo = null;
      this.regione = null;
      this.nome = null;
      this.note = null;
      this.responsabile = null;
   }

   public int getCodiceInventario()
   {
      return codiceInventario;
   }

   public void setCodiceInventario(int codiceInventario)
   {
      this.codiceInventario = codiceInventario;
   }

   public String getIndirizzo()
   {
      return indirizzo;
   }

   public void setIndirizzo(String indirizzo)
   {
      this.indirizzo = indirizzo;
   }

   public String getRegione()
   {
      return regione;
   }

   public void setRegione(String regione)
   {
      this.regione = regione;
   }

   public String getNome()
   {
      return nome;
   }

   public void setNome(String nome)
   {
      this.nome = nome;
   }

   public String getNote()
   {
      return note;
   }

   public void setNote(String note)
   {
      this.note = note;
   }

   public Utente getResponsabile()
   {
      return responsabile;
   }

   public void setResponsabile(Utente responsabile)
   {
      this.responsabile = responsabile;
   }

   public void setPossiedeList(List<Possiede> possiedeList)
   {
      this.possiedeList = possiedeList;
   }

   @Override
   public String toString() {
      return "Inventario{" +
              "codiceInventario=" + codiceInventario +
              ", indirizzo='" + indirizzo + '\'' +
              ", regione='" + regione + '\'' +
              ", nome='" + nome + '\'' +
              ", note='" + note + '\'' +
              ", responsabile=" + responsabile.getNome() +
              '}';
   }

   public void addPossiedeList(Possiede element)
   {
      if(possiedeList == null)
         possiedeList = new ArrayList<>();

      if (element != null)
         possiedeList.add(element);
   }

   public List<Possiede> getPossiedeList()
   {
      return possiedeList;
   }

   public boolean validateObject()
   {
      //note non viene considerato perchè facoltativo
      return (!indirizzo.isEmpty() && !regione.isEmpty() && !nome.isEmpty() && responsabile != null);
   }
}