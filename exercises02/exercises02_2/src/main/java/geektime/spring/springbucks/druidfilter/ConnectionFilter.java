package geektime.spring.springbucks.druidfilter;

import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.ConnectionProxy;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class ConnectionFilter extends FilterEventAdapter {
    @Override
    public void connection_connectBefore(FilterChain chain, Properties info) {
        log.info("BEFORE CONNECTION !");
    }

    @Override
    public ResultSetProxy statement_executeQuery(FilterChain chain, StatementProxy statement, String sql) throws SQLException {
            statementExecuteQueryBefore(statement, sql);
            try {
                ResultSetProxy resultSet = super.statement_executeQuery(chain, statement, sql);

                if (resultSet != null) {
                    statementExecuteQueryAfter(statement, sql, resultSet);
                    resultSetOpenAfter(resultSet);
                }

                return resultSet;
            } catch (SQLException | RuntimeException | Error error) {
                statement_executeErrorAfter(statement, sql, error);
                throw error;
            }
    }

    @Override
    public void connection_connectAfter(ConnectionProxy connection) {
        log.info("AFTER CONNECTION !");
    }
}