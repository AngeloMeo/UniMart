SELECT COUNT(*) AS 'Coupon Totali' FROM coupon;
SELECT COUNT(*) AS 'Coupon Riscattati' FROM coupon WHERE coupon.stato = 'Riscattato';

SELECT COUNT(*) AS 'Utenti Totali' FROM utente;
SELECT COUNT(*) AS 'Clienti' FROM utente WHERE utente.tipo = 'Semplice';

select sum(op.quantita) AS 'Quantità Prodotti Venduti', count(op.idProdotto) AS 'Prodotti Venduti', SUM(op.prezzoAcquisto) AS 'Incasso Ordini' 
FROM prodotto p RIGHT join ordine_prodotto op on p.codiceIAN = op.idProdotto;

SELECT COUNT(*) AS 'Ordini Salvati' FROM ordine o WHERE o.stato = 'Salvato';
SELECT COUNT(*) AS 'Ordini Evasi' FROM ordine o WHERE o.stato != 'Salvato';
SELECT COUNT(*) AS 'Ordini Totali' FROM ordine o;

SELECT COUNT(*) AS 'Numero Inventari' FROM inventario;

NOTA:
	per richiamare una VIEW: SELECT * FROM prodotto_preferito;

CREATE VIEW spedizione_preferita as
SELECT metodoSpedizione, MAX(Utilizzi) AS 'Spedizione Scelta Maggiormente' FROM (SELECT o.metodoSpedizione, COUNT(*) AS 'Utilizzi' FROM ordine o GROUP BY o.metodoSpedizione) AS temp;

CREATE VIEW prodotto_preferito as
SELECT idProdotto, MAX(QuantitàTotale) AS 'Prodotto Acquistato Maggiormente' FROM
(SELECT op.idProdotto, sum(op.quantita) AS 'QuantitàTotale' FROM prodotto p RIGHT join ordine_prodotto op on p.codiceIAN = op.idProdotto GROUP BY op.idProdotto) AS temp;

