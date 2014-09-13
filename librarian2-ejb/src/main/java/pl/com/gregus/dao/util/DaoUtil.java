package pl.com.gregus.dao.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import org.primefaces.model.SortOrder;
import pl.com.gregus.dao.utils.AbstractFilterTO;
import pl.com.gregus.dao.utils.FilterAndOrTO;
import pl.com.gregus.dao.utils.FilterBigDecimalTO;
import pl.com.gregus.dao.utils.FilterDateMonthH2TO;
import pl.com.gregus.dao.utils.FilterDateMonthTO;
import pl.com.gregus.dao.utils.FilterDateTO;
import pl.com.gregus.dao.utils.FilterEnumTO;
import pl.com.gregus.dao.utils.FilterGroupOrTO;
import pl.com.gregus.dao.utils.FilterIntegerTO;
import pl.com.gregus.dao.utils.FilterLongListTO;
import pl.com.gregus.dao.utils.FilterLongTO;
import pl.com.gregus.dao.utils.FilterObjectTO;
import pl.com.gregus.dao.utils.FilterSortTO;
import pl.com.gregus.dao.utils.FilterStringListTO;
import pl.com.gregus.dao.utils.FilterStringTO;

/**
 * DaoUtil.
 *
 * @author Grzegorz Guściora
 * @param <T>
 */
public class DaoUtil<T> {

    public CriteriaQuery createWhere(FilterSortTO filters, CriteriaBuilder criteriaBuilder, Class daoClass) {
        CriteriaQuery<Class<T>> criteriaQuery = (CriteriaQuery<Class<T>>) criteriaBuilder.createQuery(daoClass);
        Path<T> from = (Path<T>) criteriaQuery.from(daoClass);
        criteriaQuery.where(getPredicateListExt(filters.getFilters(), criteriaBuilder, daoClass, from));
        if (filters.getPrimaryKey() != null) {
            from = from.get(filters.getPrimaryKey());
        }
        if ((filters.getSortField() != null) && (filters.getSortField().size() > 0)) {
            if (filters.getSortOrder() == SortOrder.ASCENDING && filters.getSortFieldType().equals(FilterSortTO.SortFieldType.NORMAL)) {
                criteriaQuery.orderBy(criteriaBuilder.asc(setFrom(from, filters.getSortField())));
            } else if (filters.getSortOrder() == SortOrder.ASCENDING && filters.getSortFieldType().equals(FilterSortTO.SortFieldType.STRING)) {
                criteriaQuery.orderBy(criteriaBuilder.asc(criteriaBuilder.lower(setFrom(from, filters.getSortField()))));
            } else if (filters.getSortOrder() == SortOrder.ASCENDING && filters.getSortFieldType().equals(FilterSortTO.SortFieldType.CASTSTRINGTOINT)) {
                criteriaQuery.orderBy(criteriaBuilder.asc(criteriaBuilder.function("int", Integer.class, setFrom(from, filters.getSortField()))));
            } else if (filters.getSortOrder() == SortOrder.DESCENDING && filters.getSortFieldType().equals(FilterSortTO.SortFieldType.NORMAL)) {
                criteriaQuery.orderBy(criteriaBuilder.desc(setFrom(from, filters.getSortField())));
            } else if (filters.getSortOrder() == SortOrder.DESCENDING && filters.getSortFieldType().equals(FilterSortTO.SortFieldType.STRING)) {
                criteriaQuery.orderBy(criteriaBuilder.desc(criteriaBuilder.lower(setFrom(from, filters.getSortField()))));
            } else if (filters.getSortOrder() == SortOrder.DESCENDING && filters.getSortFieldType().equals(FilterSortTO.SortFieldType.CASTSTRINGTOINT)) {
                criteriaQuery.orderBy(criteriaBuilder.desc(criteriaBuilder.function("int", Integer.class, setFrom(from, filters.getSortField()))));
            }
        }

        return criteriaQuery;
    }

    public Predicate preparePredicate(AbstractFilterTO filter, CriteriaBuilder criteriaBuilder, Class daoClass, Path<T> fromRoot) {
        Predicate predicate = null;
        Path<T> from;

        from = fromRoot;

        if (filter.getPrimaryKey() != null) {
            from = criteriaBuilder.createQuery(daoClass).from(daoClass).get(filter.getPrimaryKey());
        }
        if ((filter.getField() != null) && (filter.getValue() != null)) {
            // 20.11.2013 Piotr Andrzejewski - dodanie nowego filtru grupującego filtry wg and-ów oraz or-ów
            if (filter instanceof FilterAndOrTO) {
                AbstractFilterTO[] filters = (AbstractFilterTO[]) filter.getValue();
                List<Predicate> predicates = new ArrayList<>();

                for (AbstractFilterTO filtr : filters) {
                    Predicate preparedPredicate = preparePredicate(filtr, criteriaBuilder, daoClass, fromRoot);
                    if (preparedPredicate != null) {
                        predicates.add(preparedPredicate);
                    }
                }

                if (predicates.size() == 1) {
                    return predicates.get(0);
                } else if (predicates.size() > 1) {
                    switch (filter.getComparator()) {
                        case "and":
                            return criteriaBuilder.and((Predicate[]) predicates.toArray(new Predicate[predicates.size()]));
                        case "or":
                            return criteriaBuilder.or((Predicate[]) predicates.toArray(new Predicate[predicates.size()]));
                        default:
                            break;
                    }
                }
            }

            if (filter instanceof FilterGroupOrTO) {
                FilterGroupOrTO instance = (FilterGroupOrTO) filter;
                List<String> values = new ArrayList<>();
                for (AbstractFilterTO item : instance.getGroupFilters()) {
                    values.add((String) item.getValue());
                }
                return setFrom(from, filter.getField()).in(values.toArray(new String[0]));
            }
            if (filter instanceof FilterObjectTO) {
                FilterObjectTO objFilter = (FilterObjectTO) filter;
                Object val = objFilter.getValue();
                if (filter.getComparator().equals("=")) {
                    return criteriaBuilder.equal(setFrom(from, filter.getField()), val);
                }
                if (filter.getComparator().equals("isNotEmpty")) {
                    return criteriaBuilder.isNotNull(setFrom(from, filter.getField()));
                }
                if (filter.getComparator().equals("isNotHidden")) {
                    return criteriaBuilder.isFalse(setFrom(from, filter.getField()));
                }

            }
            if (filter instanceof FilterIntegerTO) {
                FilterIntegerTO intFilter = (FilterIntegerTO) filter;
                Integer val = intFilter.getIntValue();
                if (filter.getComparator().equals("=")) {
                    return criteriaBuilder.equal(setFrom(from, filter.getField()), val);
                } else if (filter.getComparator().equals(">")) {
                    return criteriaBuilder.greaterThan(setFrom(from, filter.getField()).as(Integer.class), val);
                } else if (filter.getComparator().equals(">=")) {
                    return criteriaBuilder.greaterThanOrEqualTo(setFrom(from, filter.getField()).as(Integer.class), val);
                } else if (filter.getComparator().equals("<")) {
                    return criteriaBuilder.lessThan(setFrom(from, filter.getField()).as(Integer.class), val);
                } else if (filter.getComparator().equals("<=")) {
                    return criteriaBuilder.lessThanOrEqualTo(setFrom(from, filter.getField()).as(Integer.class), val);
                }
            }
            if (filter instanceof FilterBigDecimalTO) {
                FilterBigDecimalTO intFilter = (FilterBigDecimalTO) filter;
                BigDecimal val = intFilter.getBigDecimalValue();
                if (filter.getComparator().equals("=")) {
                    return criteriaBuilder.equal(setFrom(from, filter.getField()), val);
                } else if (filter.getComparator().equals(">")) {
                    return criteriaBuilder.greaterThan(setFrom(from, filter.getField()).as(BigDecimal.class), val);
                } else if (filter.getComparator().equals(">=")) {
                    return criteriaBuilder.greaterThanOrEqualTo(setFrom(from, filter.getField()).as(BigDecimal.class), val);
                } else if (filter.getComparator().equals("<")) {
                    return criteriaBuilder.lessThan(setFrom(from, filter.getField()).as(BigDecimal.class), val);
                } else if (filter.getComparator().equals("<=")) {
                    return criteriaBuilder.lessThanOrEqualTo(setFrom(from, filter.getField()).as(BigDecimal.class), val);
                }
            }
            if (filter instanceof FilterLongTO) {
                FilterLongTO intFilter = (FilterLongTO) filter;
                Long val = intFilter.getLongValue();
                if (filter.getComparator().equals("=")) {
                    return criteriaBuilder.equal(setFrom(from, filter.getField()), val);
                } else if (filter.getComparator().equals(">")) {
                    return criteriaBuilder.greaterThan(setFrom(from, filter.getField()).as(Long.class), val);
                } else if (filter.getComparator().equals(">=")) {
                    return criteriaBuilder.greaterThanOrEqualTo(setFrom(from, filter.getField()).as(Long.class), val);
                } else if (filter.getComparator().equals("<")) {
                    return criteriaBuilder.lessThan(setFrom(from, filter.getField()).as(Long.class), val);
                } else if (filter.getComparator().equals("<=")) {
                    return criteriaBuilder.lessThanOrEqualTo(setFrom(from, filter.getField()).as(Long.class), val);
                }
            }
            if (filter instanceof FilterDateTO) {
                if (filter.getValue() != null) {
                    Date val = (Date) filter.getValue();
                    if (filter.getComparator().equals("=")) {
                        return criteriaBuilder.equal(setFrom(from, filter.getField()), val);
                    } else if (filter.getComparator().equals(">")) {
                        return criteriaBuilder.greaterThan(setFrom(from, filter.getField()).as(Date.class), val);
                    } else if (filter.getComparator().equals(">=")) {
                        return criteriaBuilder.greaterThanOrEqualTo(setFrom(from, filter.getField()).as(Date.class), val);
                    } else if (filter.getComparator().equals("<")) {
                        return criteriaBuilder.lessThan(setFrom(from, filter.getField()).as(Date.class), val);
                    } else if (filter.getComparator().equals("<=")) {
                        return criteriaBuilder.lessThanOrEqualTo(setFrom(from, filter.getField()).as(Date.class), val);
                    }
                }
            }
            if (filter instanceof FilterStringTO) {
                if (filter.getValue() != null) {
                    FilterStringTO filterString = (FilterStringTO) filter;
                    String val = (String) filter.getValue();
                    if (val.isEmpty()) {
                        return null;
                    }

                    Expression expression = null;

                    if (filterString.isIgnoreLetterCase()) {
                        expression = criteriaBuilder.upper(setFrom(from, filter.getField()).as(String.class));
                        val = val.toUpperCase();
                    } else {
                        expression = setFrom(from, filter.getField()).as(String.class);
                    }

                    if (filter.getComparator().equals("=")) {
                        return criteriaBuilder.equal(expression, val);
                    } else if (filter.getComparator().equals(">")) {
                        return criteriaBuilder.greaterThan(expression, val);
                    } else if (filter.getComparator().equals(">=")) {
                        return criteriaBuilder.greaterThanOrEqualTo(expression, val);
                    } else if (filter.getComparator().equals("<")) {
                        return criteriaBuilder.lessThan(expression, val);
                    } else if (filter.getComparator().equals("<=")) {
                        return criteriaBuilder.lessThanOrEqualTo(expression, val);
                    } else if (filter.getComparator().equals("%.")) {
                        return criteriaBuilder.like(expression, "%" + val);
                    } else if (filter.getComparator().equals("%.%")) {
                        return criteriaBuilder.like(expression, "%" + val + "%");
                    } else if (filter.getComparator().equals(".%")) {
                        return criteriaBuilder.like(expression, val + "%");
                    } else if (filter.getComparator().equals("like")) {
                        return criteriaBuilder.like(expression, val);
                    }
                }
            }
            /**
             * W razie zmiany w filtrze nalezy naniesc poprawki tez filtr nizej
             * FilterDateMonthH2TO.
             */
            if (filter instanceof FilterDateMonthTO) {
                if (filter.getValue() != null) {
                    Date val = (Date) filter.getValue();
                    Calendar cal = new GregorianCalendar();
                    cal.setTime(val);
                    int yearMonth = cal.get(Calendar.YEAR) * 100 + cal.get(Calendar.MONTH) + 1;
                    if (filter.getComparator().equals("=")) {
                        return criteriaBuilder.equal(criteriaBuilder.function("VARCHAR_FORMAT", Integer.class, setFrom(from, filter.getField()).as(Date.class), criteriaBuilder.literal("YYYYMM")), yearMonth);
                    } else if (filter.getComparator().equals(">")) {
                        return criteriaBuilder.greaterThan(criteriaBuilder.function("VARCHAR_FORMAT", Integer.class, setFrom(from, filter.getField()).as(Date.class), criteriaBuilder.literal("YYYYMM")), yearMonth);
                    } else if (filter.getComparator().equals(">=")) {
                        return criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.function("VARCHAR_FORMAT", Integer.class, setFrom(from, filter.getField()).as(Date.class), criteriaBuilder.literal("YYYYMM")), yearMonth);
                    } else if (filter.getComparator().equals("<")) {
                        return criteriaBuilder.lessThan(criteriaBuilder.function("VARCHAR_FORMAT", Integer.class, setFrom(from, filter.getField()).as(Date.class), criteriaBuilder.literal("YYYYMM")), yearMonth);
                    } else if (filter.getComparator().equals("<=")) {
                        return criteriaBuilder.lessThanOrEqualTo(criteriaBuilder.function("VARCHAR_FORMAT", Integer.class, setFrom(from, filter.getField()).as(Date.class), criteriaBuilder.literal("YYYYMM")), yearMonth);
                    }
                }
            }
            /**
             * Do testowania FilterDateMonthTO, z uwagi na baze H2 w testach.
             */
            if (filter instanceof FilterDateMonthH2TO) {
                if (filter.getValue() != null) {
                    Date val = (Date) filter.getValue();
                    Calendar cal = new GregorianCalendar();
                    cal.setTime(val);
                    int yearMonth = cal.get(Calendar.YEAR) * 100 + cal.get(Calendar.MONTH) + 1;
                    if (filter.getComparator().equals("=")) {
                        return criteriaBuilder.equal(criteriaBuilder.function("FORMATDATETIME", Integer.class, setFrom(from, filter.getField()).as(Date.class), criteriaBuilder.literal("YYYYMM")), yearMonth);
                    } else if (filter.getComparator().equals(">")) {
                        return criteriaBuilder.greaterThan(criteriaBuilder.function("FORMATDATETIME", Integer.class, setFrom(from, filter.getField()).as(Date.class), criteriaBuilder.literal("YYYYMM")), yearMonth);
                    } else if (filter.getComparator().equals(">=")) {
                        return criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.function("FORMATDATETIME", Integer.class, setFrom(from, filter.getField()).as(Date.class), criteriaBuilder.literal("YYYYMM")), yearMonth);
                    } else if (filter.getComparator().equals("<")) {
                        return criteriaBuilder.lessThan(criteriaBuilder.function("FORMATDATETIME", Integer.class, setFrom(from, filter.getField()).as(Date.class), criteriaBuilder.literal("YYYYMM")), yearMonth);
                    } else if (filter.getComparator().equals("<=")) {
                        return criteriaBuilder.lessThanOrEqualTo(criteriaBuilder.function("FORMATDATETIME", Integer.class, setFrom(from, filter.getField()).as(Date.class), criteriaBuilder.literal("YYYYMM")), yearMonth);
                    }
                }
            }
            if (filter instanceof FilterLongListTO) {
                if (filter.getValue() != null) {
                    List val = (List) filter.getValue();
                    if (val.isEmpty()) {
                        return null;
                    }
                    if (filter.getComparator().equals("IN")) {
                        return setFrom(from, filter.getField()).in(val.toArray(new Long[0]));
                    }
                }

            }
            if (filter instanceof FilterStringListTO) {
                if (filter.getValue() != null) {
                    List val = (List) filter.getValue();
                    if (val.isEmpty()) {
                        return null;
                    }
                    if (filter.getComparator().equals("IN")) {
                        return setFrom(from, filter.getField()).in(val.toArray());
                    }
                }
            }
            //filtr dla enumow, or
            if (filter instanceof FilterEnumTO) {
                if (filter.getValue() != null) {
                    if (filter.getField() != null) {
                        List val = (List) filter.getValue();
                        if (val.isEmpty()) {
                            return null;
                        }
                        if (filter.getComparator().equals("IN")) {
                            return setFrom(from, filter.getField()).in(val.toArray(new Enum[val.size()]));
                        }
                    }
                }
            }
        }
        return predicate;
    }

    public Predicate[] getPredicateListExt(List<AbstractFilterTO> filters, CriteriaBuilder criteriaBuilder, Class daoClass, Path<T> fromRoot) {
        List<Predicate> predicateList = new ArrayList<>();

        if (filters != null) {
            for (AbstractFilterTO filter : filters) {
                if (filter == null) {
                    continue;
                }

                // Piotr Andrzejewski 20.11.2013 - przeniosiono wyznaczenia predykatu do funkcji
                Predicate predicate = preparePredicate(filter, criteriaBuilder, daoClass, fromRoot);
                if (predicate != null) {
                    predicateList.add(predicate);
                }
            }
        }
        final Predicate[] predicates = (Predicate[]) predicateList.toArray(new Predicate[0]);
        return predicates;
    }

    public static String prepareParamDoLike(Object parametr) {
        return "%" + parametr + "%";
    }

    private Path setFrom(Path from, final List<String> path) {
        for (String string : path) {
            from = from.get(string);
        }
        return from;
    }
}
