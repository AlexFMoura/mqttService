use scplus;

--DROP TABLE mensagem;
CREATE TABLE Mensagem (
	id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	cliente VARCHAR(50) NULL,
	endpoint VARCHAR(50) NULL,
	gateway VARCHAR(50) NULL,
	sensor VARCHAR(50) NULL,
	data_hora VARCHAR(50) NULL,
	um_medida VARCHAR(10) NULL,
	valor float NULL
);

CREATE TABLE Messages (
	message VARCHAR(300) NULL,
	topic VARCHAR(30) NULL
);