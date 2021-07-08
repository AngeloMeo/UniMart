//UTENTI ADMIN

SELECT COUNT(*) AS 'Coupon Totali' FROM coupon;
QueryBuilder query = new QueryBuilder("coupon", "").select("COUNT(*) AS 'Coupon Totali'");

SELECT COUNT(*) AS 'Coupon Riscattati' FROM coupon WHERE coupon.stato = 'Riscattato';
QueryBuilder query = new QueryBuilder("coupon", "").select("COUNT(*) AS 'Coupon Riscattati'").where("stato = 'Riscattato'");

SELECT COUNT(*) AS 'Utenti Totali' FROM utente;
QueryBuilder query = new QueryBuilder("utente", "").select("COUNT(*) AS 'Utenti Totali'");

SELECT COUNT(*) AS 'Clienti' FROM utente WHERE utente.tipo = 'Semplice';
QueryBuilder query = new QueryBuilder("utente", "").select("COUNT(*) AS 'Clienti'").where("tipo = 'Semplice'");

SELECT COUNT(*) AS 'Ordini Evasi' FROM ordine o WHERE o.stato != 'Salvato';
QueryBuilder query = new QueryBuilder("ordine", "").select("COUNT(*) AS 'Ordini Evasi'").where("stato != 'Salvato'");

SELECT COUNT(*) AS 'Numero Inventari' FROM inventario;
QueryBuilder query = new QueryBuilder("inventario", "").select("COUNT(*) AS 'Numero Inventari'");

select sum(op.quantita) AS 'Quantità Prodotti Venduti', count(op.idProdotto) AS 'Prodotti Venduti', cast(SUM(op.prezzoAcquisto) AS DECIMAL(10,2)) AS 'Incasso Ordini'
FROM (prodotto p RIGHT join ordine_prodotto op on p.codiceIAN = op.idProdotto) left join ordine o on op.idOrdine = o.numeroOrdine
where stato != 'Salvato' AND stato != 'Annullato';

QueryBuilder query = new QueryBuilder("prodotto", "p");
query.select("sum(op.quantita) AS 'Quantità Prodotti Venduti', count(op.idProdotto) AS 'Prodotti Venduti', cast(SUM(op.prezzoAcquisto) AS DECIMAL(10,2)) AS 'Incasso Ordini'");
query.outerJoin("ordine_prodotto", "op", 2).on("p.codiceIAN = op.idProdotto").outerJoin("ordine", "o", 1).on("op.idOrdine = o.numeroOrdine");
query.where("stato != 'Salvato' AND stato != 'Annullato'");

//ENTRAMBI

SELECT COUNT(*) AS 'Ordini Salvati' FROM ordine o WHERE o.stato = 'Salvato';
QueryBuilder query = new QueryBuilder("ordine", "").select("COUNT(*) AS 'Ordini Evasi'").where("stato = 'Salvato'");

SELECT * FROM spedizione_preferita;
QueryBuilder query = new QueryBuilder("spedizione_preferita", "").select();

SELECT * FROM prodotto_preferito;
QueryBuilder query = new QueryBuilder("prodotto_preferito", "").select();

SELECT COUNT(*) AS 'Ordini Totali' FROM ordine o;
QueryBuilder query = new QueryBuilder("ordine", "").select("COUNT(*) AS 'Ordini Totali'");