TP3 ACT
COJEZ Arnaud
LEPRETRE Rémy

COMPILATION

	"$ javac Main.java"

EXÉCUTION

	"$ java Main [m n i j]"
	où	m = largeur
		n = hauteur
		i = position horizontale du carré de la mort
		j = position verticale du carré de la mort


QUESTIONS :

1)       +3___
         / \   \
        /   \   \
       +1   -2   \
       | \  / \  |
       |  \/   \ |
       |  +1    +1
       |  |     /
       |  |    /
       |  |   /
       |  |  /
        \ | /
          0

2)
si pas de négatif ou 0 dans la liste
	n = le plus grand positif
si pas de négatif mais un 0
	n = 0
si négatif
	n = le plus grand des négatifs

si n = 0
	return 1
si n > 0
	return -n - 1
si n < 0
	return -n + 1



3) 
(10,7,7,3) met 17 secondes à s'éxecuter
(10,7,5,3) met 14 secondes à s'éxecuter
(je ne sais pas pour la différence entre les deux valeurs)
La complexité de cet algorithme est exponentiel car à chaque récursion on fait 4 boucles qui exécutent autant de récursion que la taille de leur boucle

4)
(100, 100, 50, 50) = -198
(100, 100, 48, 52) =  191

5)
conf: 127, 127, 126, 63 = 127
conf: 127, 127, 63, 126 = 127
conf: 127, 127, 63, 0 = 127
conf: 127, 127, 0, 63 = 127

6)
En omettant que la complexité de l'initialisation du tableau est en O(n4), la complexité de l'algorithme dynamique est en O(m^m + n^n)

7)
Toutes ces configurations ont la meme valeur car la superficie du quadrillage est la même qu'il soit à l'horizontale ou à la verticale. De plus la tête de mort se situe toujours à 1 carreau de distance d'un des bords et collé à un bord, ce qui donnera toujours le même résultat.

8)
On passe de 30min à 3min pour résoudre la question 5.
Nous allons donc 10 fois plus vite pour résoudre le problème
