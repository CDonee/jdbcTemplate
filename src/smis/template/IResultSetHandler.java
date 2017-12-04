package smis.template;

import java.sql.ResultSet;

public interface IResultSetHandler<T> {

    T handleResult(ResultSet set);
}
