package customexceptions;

import algorithms.TypeDeRechercheVoisinage;

import java.lang.reflect.Type;

public class UnhandledTypeDeRechercheVoisinageException extends Exception
{
    public UnhandledTypeDeRechercheVoisinageException(TypeDeRechercheVoisinage type, Class<?> classeConcernée)
    {
        super("Le type de recherche " + type.toString() + " n'est pas gérée dans la classe " + classeConcernée.toString() + ".");
        this.typeRecherche = type;
        this.classeConcernée = classeConcernée;
    }

    public UnhandledTypeDeRechercheVoisinageException(TypeDeRechercheVoisinage type)
    {
        super("Le type de recherche " + type.toString() + " n'est pas gérée.");
        this.typeRecherche = type;
        this.classeConcernée = null;
    }

    private TypeDeRechercheVoisinage typeRecherche;

    private Class<?> classeConcernée;
}
