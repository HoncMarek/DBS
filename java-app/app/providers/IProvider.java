package semestral_project.app.providers;

import java.util.List;
import java.util.Scanner;


/**
 * Rozhraní pro třídy poskytující přístup k datům v databázi.
 * Toto rozhraní definuje základní (společné) funkce všech "Providerů".
 * @param <TEntity> Třída entity reprezentující záznamy v tabulce, se kterou pracuje Provider.
 */
public interface IProvider<TEntity> {

    /**
     * Vloží záznam do databáze.
     * @param entity Entita reprezentující záznam, který se má uložit do databáze.
     * @return True, pokud se vložení podařilo.
     */
    boolean insert(TEntity entity);

    /**
     * Upraví záznam odpovídající entitě na základě změn provedených na entitě.
     * @param entity Entita reprezentující záznam z DB, který se má upravit.
     * @return True, pokud se upravení záznamu v DB podařilo.
     */
    boolean update(TEntity entity);

    /**
     * Smaže v databázi záznam odpovídající entitě.
     * @param entity Entita jejíž příslužný záznam se má v databázi smazat.
     * @return True, pokud se smazání podařilo.
     */
    boolean delete(TEntity entity);

    /**
     * Vytvoří novou dummy entitu, která není svázána se žádným záznamem v DB (z pravidla se využívá při vytváření nového záznamu).
     */
    TEntity dummy();

    /**
     * Vrací entitu, která odpovídá záznamu v DB s příslušným Id.
     * @param id Id záznamu, který se má z DB dotáhnout.
     * @return Objekt reprezentující záznam v DB.
     */
    TEntity getById(int id);


    /**
     * Vrací všechny záznamy z DB jako List entit (list objektů reprezentující jednotlivé řádky v tabulce).
     */
    List<TEntity> getAll();

    /**
     * Metoda která vyvolá dialog (konzolový), který umožní uživatelovi upravit data v objektu entity.
     * NOTE: Tato metoda sem podle logiky nezapadá, protože patří do UI části. Každopádně, mít jí na tomto místě pro nás
     * znamená značné zjednodušení kódu v UI části. Adekvátní řešení by bylo přidat mezi vrstvu mezi UI a Data layer.
     * Pro potřeby naší aplikace by to ale byl zbytečný overkill.
     * @param entity Entita, která se má naplnit daty výstupu.
     * @param sc Scanner pro získání dat ze vstupu.
     */
    void edit(TEntity entity, Scanner sc);
}
