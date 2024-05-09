# Build keretrendszer + CI

## Feladat
Build keretrendszer beüzemelése (Maven) + CI beüzemelése(GitHub Actions)

## Megoldás

Github Actionsként létrehoztunk egy maven.yml file-t, ami lebuildeli az alkalmazást, a létrehozott pom.xml maven-build configurációs fájlja alapján.

Ezt hozzárendeltük a main branchre való pull requestekhez és pushokhoz

## Kihívások, Eredmények, Tanulságok

Dependency graph pusholása közben félrekonfiguráltunk valamit. A maven.yml fájl átnézése és megértése után megtaláltul, és eltávolítottuk a probléma forrását.

Ennek eredményeképpen sikeresen lefutott a pipeline.