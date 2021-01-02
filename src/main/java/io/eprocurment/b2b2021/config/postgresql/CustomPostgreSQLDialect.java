package io.eprocurment.b2b2021.config.postgresql;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeStringType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.dialect.PostgreSQL10Dialect;

import java.sql.Types;

public class CustomPostgreSQLDialect extends PostgreSQL10Dialect {

    public CustomPostgreSQLDialect() {
        super();
        registerHibernateType(Types.OTHER, StringArrayType.class.getName());
        registerHibernateType(Types.OTHER, ListArrayType.class.getName());
        registerHibernateType(Types.OTHER, IntArrayType.class.getName());

        //   registerHibernateType(Types.OTHER, ContactArrayType.class.getName());

        registerHibernateType(Types.OTHER, JsonStringType.class.getName());
        registerHibernateType(Types.OTHER, JsonBinaryType.class.getName());
        registerHibernateType(Types.OTHER, JsonNodeBinaryType.class.getName());
        registerHibernateType(Types.OTHER, JsonNodeStringType.class.getName());
    }
}
