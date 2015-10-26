CREATE TABLE IF NOT EXISTS `compte` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `TYPE` text NOT NULL,
  `Libelle` text NOT NULL,
  `Solde` int(11) NOT NULL,
  `Numero` text NOT NULL,
  `nom` text NOT NULL,
  `prenom` text NOT NULL,
  `login` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

CREATE TABLE IF NOT EXISTS `client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Login` text NOT NULL,
  `Nom` text NOT NULL,
  `Prenom` text NOT NULL,
  `Mdp` text NOT NULL,
  `Comptes_courant` text,
  `Comptes_epargne` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;



INSERT INTO `compte` (`id`, `TYPE`, `Libelle`, `Solde`, `Numero`, `nom`, `prenom`, `login`) VALUES
(1, 'C', '[paul]_[henri]_COURANT_1', 25, 'CC1', 'paul', 'henri', 'ph'),
(2, 'C', '[paul]_[henri]_COURANT_2', 100, 'CC2', 'paul', 'henri', 'ph'),
(3, 'E', '[paul]_[henri]_EPARGNE_1', 200, 'CE1', 'paul', 'henri', 'ph'),
(4, 'C', '[thomas]_[colm]_COURANT_1', 0, 'CC1', 'thomas', 'colm', 'thco'),
(5, 'E', '[thomas]_[colm]_EPARGNE_1', 0, 'CE1', 'thomas', 'colm', 'thco'),
(6, 'E', '[thomas]_[colm]_EPARGNE_2', 0, 'CE2', 'thomas', 'colm', 'thco');

INSERT INTO `client` (`id`, `Login`, `Nom`, `Prenom`, `Mdp`, `Comptes_courant`, `Comptes_epargne`) VALUES
(1, 'ph', 'paul', 'henri', 'ph', '[paul]_[henri]_COURANT_1,[paul]_[henri]_COURANT_2', '[paul]_[henri]_EPARGNE_1'),
(2, 'thco', 'thomas', 'colm', 'thco', '[thomas]_[colm]_COURANT_1', '[thomas]_[colm]_EPARGNE_1,[thomas]_[colm]_EPARGNE_2');