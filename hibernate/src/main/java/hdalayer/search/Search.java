package hdalayer.search;

import model.Tradition;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.errors.EmptyQueryException;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import hdalayer.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Search {

//TODO: move to tests
//    public static void startOracle(String[] args) {
//
//        Locale.setDefault(Locale.ENGLISH);
//        doIndex();
//        List result = searchBy("Germany", false);
//        System.out.println("Results:");
//        for(Object obj : result){
//            System.out.println(obj.toString());
//        }
//        HibernateUtil.getSessionFactory().close();
//    }

    // If we added data without using hibernate we had to run this method
    // (cause hibernate indexes is not matching to db)
    public static void doIndex() throws InterruptedException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(session);
        fullTextSession.createIndexer().startAndWait();
    }

    /**
     * Search by one or two patterns
     *
     * @param pattern      the query string
     * @param boolOperator OR(true); AND(false)
     * @return Search results. Query for more than 2 patterns return NULL
     */
    public static List<Tradition> searchTradition(String pattern, boolean boolOperator) {
        List<Tradition> result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(session);
        Transaction transaction = session.beginTransaction();

        String[] patterns = pattern.split(" ");

        try {
            QueryBuilder builder = fullTextSession.getSearchFactory().
                    buildQueryBuilder().forEntity(Tradition.class).get();

            // Lucene makes all the search
            org.apache.lucene.search.Query luceneQuery;
            if (boolOperator) {
                luceneQuery = getORQuery(builder, patterns);
            } else {
                luceneQuery = getANDQuery(builder, patterns);
            }
            result = getTraditions(fullTextSession, luceneQuery);

            transaction.commit();
        }
        //region Catches
        catch (HibernateException ex) {
            transaction.rollback();
            return new ArrayList<Tradition>();

        } catch (ClassCastException cce) {
            transaction.rollback();
            return new ArrayList<Tradition>();

        } catch (EmptyQueryException ex) {
            transaction.rollback();
            return new ArrayList<Tradition>();
        }
        //endregion
        finally {
            session.close();
        }

        return result;
    }

    /**
     * Search by one pattern
     *
     * @param pattern      the query string
     * @param boolOperator by holiday(true); by country(false)
     * @return Search results. Query for more than 2 patterns return NULL
     */
    public static List<Tradition> searchBy(String pattern, boolean boolOperator) {

        List<Tradition> result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(session);
        Transaction transaction = session.beginTransaction();

        try {
            QueryBuilder builder = fullTextSession.getSearchFactory().
                    buildQueryBuilder().forEntity(Tradition.class).get();

            // Lucene makes all the search
            org.apache.lucene.search.Query luceneQuery;
            if (boolOperator) {
                luceneQuery = getByNameQuery(builder, pattern);
            } else {
                luceneQuery = getByCountryQuery(builder, pattern);
            }
            result = getTraditions(fullTextSession, luceneQuery);

            transaction.commit();
        }
        //region Catches
        catch (HibernateException ex) {
            transaction.rollback();
            return new ArrayList<Tradition>();
        } catch (ClassCastException cce) {
            transaction.rollback();
            return new ArrayList<Tradition>();
        } catch (EmptyQueryException ex) {
            transaction.rollback();
            return new ArrayList<Tradition>();
        }
        //endregion
        finally {
            session.close();
        }

        return result;
    }

    @SuppressWarnings("unchecked") //for unchecked cast List -> List<Tradition>
    private static List<Tradition> getTraditions(FullTextSession fullTextSession
            , org.apache.lucene.search.Query luceneQuery) {
        List result;
        // Get the searching results from lucene query and
        // wrapping it into hibernate to take entities
        org.hibernate.Query hibernateQuery =
                fullTextSession.createFullTextQuery(luceneQuery, Tradition.class);

        result = (List<Tradition>) hibernateQuery.list();
        return result;
    }

    /**
     * Create a Lucene Query for search by two words using OR operator
     *
     * @param patterns Splitted searching string
     * @param builder  QueryBuilder from
     * @return Lucene Query
     */
    private static org.apache.lucene.search.Query getORQuery(QueryBuilder builder, String[] patterns) {

        assert (patterns.length > 0);
        BooleanJunction bool = builder.bool();
        for (String pattern : patterns) {
            bool = bool
                    .should(builder.keyword().onFields("description", "holiday.name", "country.name")
                            .matching(pattern).createQuery());
        }
        return bool.createQuery();
    }

    /**
     * Create a Lucene Query for search by two words using AND operator
     *
     * @param patterns Searching splitted string
     * @param builder  QueryBuilder from
     * @return Lucene Query
     */
    private static org.apache.lucene.search.Query getANDQuery(QueryBuilder builder, String[] patterns) {
        assert (patterns.length > 0);
        BooleanJunction bool = builder.bool();
        for (String pattern : patterns) {
            bool = bool.
                    must(builder.keyword().onFields("description", "holiday.name", "country.name")
                            .matching(pattern).createQuery());
        }
        return bool.createQuery();
    }

    /**
     * Create a Lucene Query for search by holiday name
     *
     * @param name    Holiday name for searching
     * @param builder QueryBuilder from
     * @return Lucene Query
     */
    private static org.apache.lucene.search.Query getByNameQuery(QueryBuilder builder, String name) {


        return builder.phrase()
                .onField("holiday.name")
                .sentence(name)
                .createQuery();
    }

    /**
     * Create a Lucene Query for search by country name
     *
     * @param country Country name for searching
     * @param builder QueryBuilder from
     * @return Lucene Query
     */
    private static org.apache.lucene.search.Query getByCountryQuery(QueryBuilder builder, String country) {

        return builder.phrase()
                .onField("country.name")
                .sentence(country)
                .createQuery();
    }

}
