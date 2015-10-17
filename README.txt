TP3 ACT
COJEZ Arnaud
LEPRETRE Rémy

1)       +3 
         / \ 
        /   \ 
       +1   -2 
       |    / \
       |   /   \
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
	
si max > 0 && min == 0
    return -max - 1



3) (10,7,7,3) met 17 secondes à s'exécuter
(10,7,5,3) met 14 secondes à s'éxécuter
(je ne sais pas pour la différence entre les deux valeurs)
La compléxité de cette algorithme est exponentielle car a chaque récursion on fait 4 boucles qui exécutent autant de récursion que la taille de leur boucle

4)

(100, 100, 50, 50) = -198
(100, 100, 48, 52) =  191

5)

conf: 127, 127, 126, 63 = 127
conf: 127, 127, 63, 126 = 127
conf: 127, 127, 63, 0 = 127
conf: 127, 127, 0, 63 = 127

6)

7)
