TP3 ACT
COJEZ Arnaud
LEPRETRE Rémy

1)       +3 
         / \ 
        /   \ 
       +1   -2 
            / \
           /   \
          +1    +1
          |     /
          |    /
          |   /
          |  /
          | /
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
