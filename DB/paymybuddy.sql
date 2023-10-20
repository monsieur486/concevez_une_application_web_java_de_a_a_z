-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : db:3306
-- Généré le : ven. 20 oct. 2023 à 13:22
-- Version du serveur : 8.0.33
-- Version de PHP : 8.1.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `paymybuddy`
--

-- --------------------------------------------------------

--
-- Structure de la table `connections`
--

CREATE TABLE `connections` (
  `id` bigint NOT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` bigint DEFAULT NULL,
  `user_connected_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `connections`
--

INSERT INTO `connections` (`id`, `nickname`, `user_id`, `user_connected_id`) VALUES
(1, 'Jean-Claude', 1, 2),
(2, 'Tonton Henry', 1, 3),
(3, 'Mike', 1, 4),
(4, 'John', 1, 5),
(5, 'Fred', 1, 6),
(6, 'Albert', 1, 7),
(7, 'Joe', 1, 8),
(8, 'Nike', 1, 9),
(9, 'Sam', 1, 10),
(10, 'William', 1, 11);

-- --------------------------------------------------------

--
-- Structure de la table `messages`
--

CREATE TABLE `messages` (
  `id` bigint NOT NULL,
  `content` text COLLATE utf8mb4_general_ci,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `persistent_logins`
--

CREATE TABLE `persistent_logins` (
  `series` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `last_used` datetime(6) NOT NULL,
  `token` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `transactions`
--

CREATE TABLE `transactions` (
  `id` bigint NOT NULL,
  `amount` int NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `connection_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `transactions`
--

INSERT INTO `transactions` (`id`, `amount`, `description`, `connection_id`) VALUES
(1, 10, 'Transaction 1', 1),
(2, 20, 'Transaction 2', 1),
(3, 30, 'Transaction 3', 1),
(4, 10, 'Transaction 4', 2),
(5, 20, 'Transaction 5', 2),
(6, 30, 'Transaction 6', 2),
(7, 10, 'Transaction 7', 3),
(8, 20, 'Transaction 8', 3),
(9, 30, 'Transaction 9', 3),
(10, 10, 'Transaction 10', 4),
(11, 20, 'Transaction 11', 4),
(12, 30, 'Transaction 12', 4),
(13, 10, 'Transaction 13', 5),
(14, 20, 'Transaction 14', 5),
(15, 30, 'Transaction 15', 5),
(16, 10, 'Transaction 16', 6),
(17, 20, 'Transaction 17', 6),
(18, 30, 'Transaction 18', 6),
(19, 10, 'Transaction 19', 7),
(20, 20, 'Transaction 20', 7),
(21, 30, 'Transaction 21', 7),
(22, 10, 'Transaction 22', 8),
(23, 20, 'Transaction 23', 8),
(24, 30, 'Transaction 24', 8),
(25, 10, 'Transaction 25', 9),
(26, 20, 'Transaction 26', 9),
(27, 30, 'Transaction 27', 9),
(28, 10, 'Transaction 28', 10),
(29, 20, 'Transaction 29', 10),
(30, 30, 'Transaction 30', 10);

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id` bigint NOT NULL,
  `balance` int DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `balance`, `email`, `password`) VALUES
(1, 45000, 'demo@user.fr', '$2a$10$mqMZSRpFrMzPFOemrYU8a.sViqrGS/af.DFm0AfvY5ukrfwme46lq'),
(2, 0, 'odell.west0573@gmail.com', '$2a$10$11v.CYX7OEbomgEcjW6r6uJYnmC5C7ij96ruHIluyQ3DOFF55GnQ2'),
(3, 0, 'tina.jenkins4372@gmail.com', '$2a$10$wlBlG0KHbJ7s4lKnjeRlzur9awVU/4/yv7KeR6HhVZhiu7Wpe0jTO'),
(4, 0, 'stanton.king5561@gmail.com', '$2a$10$J7gQLjEBF2ORG1LcF.BeheTuskymNmRVumL80Jip.CXU/HDuDK8fC'),
(5, 0, 'dorian.cronin7951@gmail.com', '$2a$10$uIl/UXtNAzbuZbC5nP9v4OP1aC/9lXOG0Vej1RvbZhCJ9TXQvbw7.'),
(6, 0, 'prince.murphy9112@gmail.com', '$2a$10$hBEKQ4DzDYQ/0y33Ir4jHOyYCQcTz1HX6uhC7Jhzycup4kEjJN04q'),
(7, 0, 'leslie.stokes0180@gmail.com', '$2a$10$RzFBm9KmWNE9ljaWLlLvR.uz3Rus5PPjsL2XjEbPJlXEDXhOdp//u'),
(8, 0, 'tianna.olson6670@gmail.com', '$2a$10$abZee7J13CAQE4vBylXbBe5x9veBJi4btCdG2IBEaFExcK7qBFnla'),
(9, 0, 'ligia.harris5271@gmail.com', '$2a$10$RcQXpykhCsoTwbvWVFvohOTtuZUVrrtorQWSNjTlK0lDED9CuaEbW'),
(10, 0, 'king.schinner7238@gmail.com', '$2a$10$6/g290YtOcG1xKIDLu1UeuQfruNdsfpoqVYC526r7CYMY6d.li2MG'),
(11, 0, 'blaine.ryan7747@gmail.com', '$2a$10$aUcMVLHVU/ofbxVfkDCWn.Fm3DIdU8Sj8rv0.m1lrLVD1//NWadvi');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `connections`
--
ALTER TABLE `connections`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKltpo1ymtaafd67hx5tls1db6u` (`user_id`),
  ADD KEY `FK83a1she98c0iwx4bi6qn0wufg` (`user_connected_id`);

--
-- Index pour la table `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `persistent_logins`
--
ALTER TABLE `persistent_logins`
  ADD PRIMARY KEY (`series`);

--
-- Index pour la table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKpsj5jm64merbitelgn5jcdwcl` (`connection_id`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `connections`
--
ALTER TABLE `connections`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT pour la table `messages`
--
ALTER TABLE `messages`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `connections`
--
ALTER TABLE `connections`
  ADD CONSTRAINT `FK83a1she98c0iwx4bi6qn0wufg` FOREIGN KEY (`user_connected_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKltpo1ymtaafd67hx5tls1db6u` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Contraintes pour la table `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `FKpsj5jm64merbitelgn5jcdwcl` FOREIGN KEY (`connection_id`) REFERENCES `connections` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
