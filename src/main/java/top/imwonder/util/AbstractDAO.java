/*
 * @Author: qh 
 * @Date: 2020-05-02 16:27:18 
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-08-24 09:58:24
 */
package top.imwonder.util;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class AbstractDAO<T> {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected JdbcTemplate jt;

    protected String insertSQL;
    protected String deleteSQL;
    protected String updateSQL;
    protected String updateSqlWithPk;
    protected String loadOneSQL;
    protected String loadMoreSQL;
    protected String countBySql;

    protected boolean insertPK = true;

    private final Class<T> domainType;
    private final String tableName;
    private final String[] pkColumns;
    private final String[] ckColumns;

    private Method[] pkGetters;
    private Method[] pkSetters;
    private Method[] ckGetters;
    private Method[] ckSetters;

    public AbstractDAO() {
        this.domainType = getDomainType();
        this.tableName = getTableName();
        this.pkColumns = getPkColumns();
        this.ckColumns = getCkColumns();
        init();
    }

    public JdbcTemplate getJt() {
        return jt;
    }

    protected abstract Class<T> getDomainType();

    protected abstract String getTableName();

    protected abstract String[] getPkColumns();

    protected abstract String[] getCkColumns();

    protected void init() {
        loadColumnsMethod();
        buildSQLs();
    }

    protected void loadColumnsMethod() {
        Method methods[] = domainType.getMethods();
        HashMap<String, Method> dataMap = new HashMap<>();
        for (Method method : methods) {
            if (method.getName().startsWith("set") && method.getParameterTypes().length == 1) {
                dataMap.put(method.getName(), method);
            }
        }
        pkSetters = new Method[pkColumns.length];
        pkGetters = new Method[pkColumns.length];
        initDataMethod(dataMap, pkColumns, pkSetters, pkGetters);
        ckSetters = new Method[ckColumns.length];
        ckGetters = new Method[ckColumns.length];
        initDataMethod(dataMap, ckColumns, ckSetters, ckGetters);
    }

    private void initDataMethod(HashMap<String, Method> settersMap, String[] columns, Method[] setters,
            Method[] getters) {
        try {
            for (int i = 0; i < columns.length; i++) {
                String cccn = StringUtil.toHumpCase(columns[i], "_", true, true);
                String methodName = "get" + cccn;
                try {
                    getters[i] = domainType.getMethod(methodName);
                } catch (NoSuchMethodException e) {
                    methodName = "is" + cccn;
                    getters[i] = domainType.getMethod(methodName);
                }
                methodName = "set" + cccn;
                setters[i] = settersMap.get(methodName);
            }
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException("no such method!", ex);
        }
    }

    protected void buildSQLs() {
        buildInsertSQL();
        buildDeleteSQL();
        buildUpdateSQL(false);
        buildUpdateSQL(true);
        buildLoadOneSQL();
        buildLoadMoreSQL();
        buildCountBySQL();
    }

    protected void buildInsertSQL() {
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("insert into ");
        sqlBuffer.append(tableName);
        sqlBuffer.append("(");
        if (insertPK) {
            for (String pk : pkColumns) {
                sqlBuffer.append(pk);
                sqlBuffer.append(", ");
            }
        }
        for (String ck : ckColumns) {
            sqlBuffer.append(ck);
            sqlBuffer.append(", ");
        }
        sqlBuffer.deleteCharAt(sqlBuffer.length() - 2);
        sqlBuffer.append(") values(");
        int len = insertPK ? pkColumns.length + ckColumns.length : ckColumns.length;
        for (int i = 0; i < len; i++) {
            sqlBuffer.append("?, ");
        }
        sqlBuffer.deleteCharAt(sqlBuffer.length() - 2);
        sqlBuffer.append(")");
        insertSQL = sqlBuffer.toString();
    }

    protected void buildDeleteSQL() {
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("delete from ");
        sqlBuffer.append(tableName);
        buildWhereClause(sqlBuffer);
        deleteSQL = sqlBuffer.toString();
    }

    protected void buildUpdateSQL(boolean updatePk) {
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("update ");
        sqlBuffer.append(tableName);
        sqlBuffer.append(" set ");
        for (String ck : ckColumns) {
            sqlBuffer.append(ck);
            sqlBuffer.append("=?, ");
        }
        if (updatePk) {
            for (String pk : pkColumns) {
                sqlBuffer.append(pk);
                sqlBuffer.append("=?, ");
            }
        }
        sqlBuffer.deleteCharAt(sqlBuffer.length() - 2);
        buildWhereClause(sqlBuffer);
        if (updatePk) {
            updateSqlWithPk = sqlBuffer.toString();
        } else {
            updateSQL = sqlBuffer.toString();
        }
    }

    protected void buildLoadOneSQL() {
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("select ");
        addColumns(sqlBuffer);
        sqlBuffer.append(" from ");
        sqlBuffer.append(tableName);
        buildWhereClause(sqlBuffer);
        loadOneSQL = sqlBuffer.toString();
    }

    protected void buildLoadMoreSQL() {
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("select ");
        addColumns(sqlBuffer);
        sqlBuffer.append(" from ");
        sqlBuffer.append(tableName);
        loadMoreSQL = sqlBuffer.toString();
    }

    protected void buildCountBySQL() {
        countBySql = String.format("select count(%s) from %s %s group by %s", "%s", tableName, "%s", "%s");
    }

    protected void buildWhereClause(StringBuffer buffer) {
        buffer.append(" where ");
        for (int i = 0; i < pkColumns.length; i++) {
            if (i == 0) {
                buffer.append(pkColumns[i]);
                buffer.append("=?");
            } else {
                buffer.append(" and ");
                buffer.append(pkColumns[i]);
                buffer.append("=?");
            }
        }
    }

    private void addColumns(StringBuffer buffer) {
        for (String pk : pkColumns) {
            buffer.append(pk);
            buffer.append(", ");
        }
        for (String ck : ckColumns) {
            buffer.append(ck);
            buffer.append(", ");
        }
        buffer.deleteCharAt(buffer.length() - 2);
    }

    public int insert(T t) {
        return jt.update(insertSQL, getInsertParamValues(t));
    }

    public int delete(Object... params) {
        return jt.update(deleteSQL, params);
    }

    public int update(T t) {
        return jt.update(updateSQL, getUpdateParamValues(t, null));
    }

    public int updateWithPk(T t, Object... oldPks) {
        return jt.update(updateSqlWithPk, getUpdateParamValues(t, oldPks));
    }
    @Cacheable(cacheNames = "comment")
    public ArrayList<T> loadMore(String clause, Object... params) {
        return loadMoreBySQL(appendClause(loadMoreSQL, clause), params);
    }
    @Cacheable(cacheNames = "comment")
    public ArrayList<T> loadMoreBySQL(String sql, Object... params) {
        return jt.query(sql, new ResultSetExtractor<ArrayList<T>>() {
            public ArrayList<T> extractData(ResultSet rs) throws SQLException, DataAccessException {
                ArrayList<T> result = new ArrayList<T>();
                while (rs.next())
                    result.add(wrapResult(rs));
                return result;
            }
        }, params);
    }
    @Cacheable(cacheNames = "comment")
    public T loadOneByPk(Object... params) {
        return jt.query(loadOneSQL, new ResultSetExtractor<T>() {
            public T extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next())
                    return wrapResult(rs);
                return null;
            }

        }, params);
    }

    @Cacheable(cacheNames = "comment")
    public T loadOneByClause(String clause, Object... params) {
        return jt.query(appendClause(loadMoreSQL, clause), new ResultSetExtractor<T>() {
            public T extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    return wrapResult(rs);
                }
                return null;
            }

        }, params);
    }

    public int countBy(String clause, String columns, Object... params) {
        try {
            return jt.queryForObject(String.format(countBySql, columns, clause, columns), Integer.class, params);
        } catch (Exception e) {
            return 0;
        }
    }

    protected Object[] getInsertParamValues(T t) {
        int len = insertPK ? pkColumns.length + ckColumns.length : ckColumns.length;
        Object[] objects = new Object[len];
        try {
            int i = 0;
            if (insertPK) {
                for (Method method : pkGetters) {
                    objects[i++] = method.invoke(t);
                }
            }
            for (Method method : ckGetters) {
                objects[i++] = method.invoke(t);
            }
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred (Insert)", ex);
        }
        return objects;
    }

    protected Object[] getUpdateParamValues(T t, Object[] pks) {
        Object[] objects = new Object[pkColumns.length + ckColumns.length + (pks == null ? 0 : pks.length)];
        try {
            int i = 0;
            for (Method method : ckGetters) {
                objects[i++] = method.invoke(t);
            }
            for (Method method : pkGetters) {
                objects[i++] = method.invoke(t);
            }
            if (pks != null && pks.length > 0) {
                for (Object pk : pks) {
                    objects[i++] = pk;
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred (Update)", ex);
        }
        return objects;
    }

    protected String appendClause(String sql, String clause) {
        if (clause == null)
            return sql;
        return sql + " " + clause.trim();
    }

    protected T wrapResult(ResultSet rs) throws SQLException {
        int startIndex = 1;
        try {
            T obj = domainType.newInstance();
            for (Method method : pkSetters) {
                method.invoke(obj, loadDomainData(method, rs, startIndex++));
            }
            for (Method method : ckSetters) {
                method.invoke(obj, loadDomainData(method, rs, startIndex++));
            }
            return obj;
        } catch (Exception ex) {
            if (ex instanceof SQLException)
                throw (SQLException) ex;
            throw new RuntimeException("An error occurred (LoadData)", ex);
        }
    }

    private Object loadDomainData(Method setter, ResultSet rs, int index) throws SQLException {
        Class<?> type = setter.getParameterTypes()[0];
        Class<?> superType = type.getSuperclass();
        if (superType == null) {// 基本数据类型
            char[] typeName = type.getName().toCharArray();
            typeName[0] = (char) (typeName[0] - 32);
            try {
                return ResultSet.class.getMethod("get" + new String(typeName), int.class).invoke(rs, index);
            } catch (Exception ex) {
                log.info("An error occurred (LoadData - getter: get{})", new String(typeName));
                log.warn(ex.getMessage(), ex);
            }
        } else if (superType == Number.class) {// 基本数据类型的包装类
            String n = type.getSimpleName();
            Object result = null;
            if (n.startsWith("Int")) {
                result = rs.getInt(index);
            } else {
                try {
                    result = ResultSet.class.getMethod("get" + n, int.class).invoke(rs, index);
                } catch (Exception ex) {
                    log.warn(ex.getMessage(), ex);
                }
            }
            return rs.wasNull() ? null : result;
        }
        if (type == Date.class) {
            return rs.getTimestamp(index);
        }
        return rs.getObject(index);
    }
}