-- elenco dei post salvati dall'utente con username 'marione'
SELECT
	p.*
FROM
	utente u INNER JOIN postSalvati ps
	ON u.ID = ps.utenteID
	INNER JOIN post p
	ON ps.postID = p.ID
WHERE
	u.username = 'marione';

-- numero totale delle notifiche dell'utente 1
SELECT
	COUNT(*) AS notifiche_tot_utente_1
FROM
	notifica n INNER JOIN utente u
	ON n.utenteID = u.ID
WHERE
	u.ID = 1;

-- prima notifica che ha ricevuto l'utente 1
SELECT
	n.*
FROM
	notifica n INNER JOIN utente u
	ON n.utenteID = u.ID
WHERE
	u.ID = 1
HAVING
	n.`data` = MIN(n.`data`);

-- il post con più like dell'utente 1
SELECT
	p.*
FROM
	post p INNER JOIN utente u
	ON p.utenteID = u.ID
WHERE
	u.ID = 1
HAVING
	p.`like` = MAX(p.`like`);

-- elenco dei post appartenenti al topic '3D'
SELECT
	t.nome AS topic , p.*
FROM
	post p INNER JOIN topic t
	ON p.topic = t.nome
WHERE
	t.nome = '3D';

-- leaderboard degli utenti in base al punteggio
SELECT
	u.username, u.punteggio
FROM
	utente u
ORDER BY
	u.punteggio DESC;

-- la comissione contenente nel titolo '' con il prezzo più basso
SELECT
	*
FROM
	commissione c
WHERE
	LOWER(c.titolo) LIKE '%velociraptor%'
HAVING
	c.`data` = MIN(c.`data`);

-- elenco delle commissioni ordinato dalla meno recente
SELECT
	*
FROM
	commissione c
ORDER BY
	c.`data`;