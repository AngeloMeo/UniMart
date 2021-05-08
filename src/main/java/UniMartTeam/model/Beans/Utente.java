package UniMartTeam.model.Beans;

import UniMartTeam.model.EnumForBeans.TipoUtente;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;

public class Utente
{
   private String CF, nome, cognome, viaCivico, fotoProfilo, citta, regione, telefono, email, token,
           username, passwordHash;
   private TipoUtente tipoUtente;
   private LocalDate dataDiNascita;
   private List<Inventario> inventarioList;
   private List<Ordine> ordineList;
   private List<Coupon> couponList;

   public Utente()
   {
      tipoUtente = TipoUtente.Semplice;
   }

   public String getCF()
   {
      return CF;
   }

   public void setCF(String CF)
   {
      this.CF = CF;
   }

   public String getNome()
   {
      return nome;
   }

   public void setNome(String nome)
   {
      this.nome = nome;
   }

   public String getCognome()
   {
      return cognome;
   }

   public void setCognome(String cognome)
   {
      this.cognome = cognome;
   }

   public String getViaCivico()
   {
      return viaCivico;
   }

   public void setViaCivico(String viaCivico)
   {
      this.viaCivico = viaCivico;
   }

   public String getFotoProfilo()
   {
      return fotoProfilo;
   }

   public void setFotoProfilo(String fotoProfilo)
   {
      this.fotoProfilo = fotoProfilo;
   }

   public String getCitta()
   {
      return citta;
   }

   public void setCitta(String citta)
   {
      this.citta = citta;
   }

   public String getRegione()
   {
      return regione;
   }

   public void setRegione(String regione)
   {
      this.regione = regione;
   }

   public String getTelefono()
   {
      return telefono;
   }

   public void setTelefono(String telefono)
   {
      this.telefono = telefono;
   }

   public String getEmail()
   {
      return email;
   }

   public void setEmail(String email)
   {
      this.email = email;
   }

   public String getToken()
   {
      return token;
   }

   public void setToken(String token)
   {
      this.token = token;
   }

   public String getUsername()
   {
      return username;
   }

   public void setUsername(String username)
   {
      this.username = username;
   }

   public String getPasswordHash()
   {
      return passwordHash;
   }

   public void setPasswordHash(String password)
   {
      try
      {
         MessageDigest digest = MessageDigest.getInstance("SHA-1");
         digest.reset();
         digest.update(password.getBytes(StandardCharsets.UTF_8));
         this.passwordHash = String.format("%040x", new BigInteger(1, digest.digest()));
      } catch (NoSuchAlgorithmException e)
      {
         throw new RuntimeException(e);
      }
   }

   public LocalDate getDataDiNascita()
   {
      return dataDiNascita;
   }

   public void setDataDiNascita(LocalDate dataDiNascita)
   {
      this.dataDiNascita = dataDiNascita;
   }

   public void setInventarioList(List<Inventario> list)
   {
      this.inventarioList = list;
   }

   public void addInventarioList(Inventario element)
   {
      if (element != null)
         inventarioList.add(element);
   }

   public List<Inventario> getInventarioList()
   {
      return inventarioList;
   }

   public TipoUtente getTipo()
   {
      return tipoUtente;
   }

   public void setTipo(TipoUtente tipoUtente)
   {
      this.tipoUtente = tipoUtente;
   }

   public void setOrdineList(List<Ordine> list)
   {
      this.ordineList = list;
   }

   public void addOrdineList(Ordine element)
   {
      if (element != null)
         ordineList.add(element);
   }

   public List<Ordine> getOrdineList()
   {
      return ordineList;
   }

   public void setCouponList(List<Coupon> list)
   {
      this.couponList = list;
   }

   public void addCouponList(Coupon element)
   {
      if (element != null)
         couponList.add(element);
   }
}