# BDD tesztek

## Feladat
BDD tesztek Cucumber-rel

## Megoldás

### Projekt beállítása:
A Java projektben a Cucumber integrálásához hozzáadtuk a szükséges függőségeket a pom.xml fájlhoz, beleértve a cucumber-java és cucumber-junit könyvtárakat.

### Funkcionális követelmények leírása:
A tesztelni kívánt funkciókat Gherkin nyelven írtuk le. A Gherkin nyelv egyszerű szintaxist használ, amely kulcsszavakból (Given, When, Then) áll. Ezek a resources könyvtárban található .feature fájlok.

### Step definíciók implementálása:
A szcenárió lépéseihez Java metódusokat társítottunk. Ezeket a metódusokat annotációkkal láttuk el, amelyek megfelelnek a Gherkin szintaxisának. Ezek a steps könyvtárban található Java osztályok metódusok, itt kötjük össze a teszteseteket a játék mechanikájával.

### Teszt futtatása:
A Cucumber teszteket JUnit futtatóval indítottuk. A teszt eredményeit a JUnit standard kimenetén keresztül értékeltük, amely tartalmazta a sikeres és sikertelen tesztesetek részleteit.

### Eredmények értékelése:
Az összes teszteset sikeresen lefutott, ami azt jelenti, hogy a programunk megfelelt az előzetesen meghatározott üzleti követelményeknek.

