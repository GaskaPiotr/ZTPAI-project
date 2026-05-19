# Uruchamianie projektu

Projekt składa się z trzech części: **bazy danych** (Docker), **backendu** (Spring Boot) i **frontendu** (Angular).  
Uruchamiaj je **w tej kolejności**.

---
## 1. Plik .env (KLUCZOWE)
Aplikacja nie włączy się bez pliku .env z odpowiednimi zmiennymi

1. W folderze backend znajdź plik rename.env
2. Zmień nazwę pliku z rename.env na .env
3. Zmień wartości zmiennych na bezpieczne

## 2. Baza danych — Docker Compose

Przejdź do folderu backendu i uruchom kontener z bazą danych:

```bash
cd backend
docker compose up -d
```

> Upewnij się, że Docker Desktop jest uruchomiony przed wykonaniem tego polecenia.  
> Flaga `-d` uruchamia kontenery w tle.

Żeby sprawdzić czy kontener działa poprawnie:

```bash
docker ps
```

---

## 3. Backend — Spring Boot (IntelliJ IDEA)

Otwórz projekt w **IntelliJ IDEA**, a następnie uruchom aplikację jednym z poniższych sposobów:

**Opcja A — zielony przycisk (najprościej)**
- Otwórz plik `src/main/java/com/github/GaskaPiotr/spring_boot_boilerplate/SpringBootBoilerplateApplication.java`
- Kliknij zielony trójkąt ▶ przy nazwie klasy lub na górnym pasku

**Opcja B — Maven z terminala** (wymaga zainstalowanego Mavena lub użycia wrappera)
```bash
cd backend
./mvnw spring-boot:run
```

> Backend domyślnie startuje na `http://localhost:8080`.  
> Upewnij się że baza danych (krok 1) jest już uruchomiona, zanim odpalisz backend.

---

## 4. Frontend — Angular

W **osobnym terminalu** przejdź do folderu frontendu i zainstaluj zależności (tylko przy pierwszym uruchomieniu), a następnie uruchom serwer deweloperski:

```bash
cd frontend
npm install        # tylko za pierwszym razem
ng serve
```

> Frontend domyślnie dostępny pod `http://localhost:4200`.  
> Wymaga zainstalowanego [Node.js](https://nodejs.org) oraz Angular CLI (`npm install -g @angular/cli`).

---

## Kolejność uruchamiania — podsumowanie

```
Terminal 1                    IntelliJ                     Terminal 2
──────────────────────────    ─────────────────────────    ──────────────────────
cd backend                    Otwórz projekt               cd frontend
docker compose up -d    →     Kliknij ▶ Run           →    ng serve
(baza danych gotowa)          (backend na :8080)           (frontend na :4200)
```

---

## Wymagania

| Narzędzie       | Minimalna wersja |
|-----------------|------------------|
| Docker Desktop  | 4.x              |
| Java (JDK)      | 21               |
| Node.js         | 18.x             |
| Angular CLI     | 17.x             |
| IntelliJ IDEA   | 2023.x           |
