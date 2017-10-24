package model.commons;

/**
 A base interface that should be implemented by all classes that is searchable.
 @author Castillo Clarence Fitzgerald Gumtang
 @version 1.0
 @since 2017-10-20
 */
public interface Searchable {

    /**
     * Gets this searchable's search tag.
     * @return this searchable's search tag.
     */
    String[] getSearchTags();
}
