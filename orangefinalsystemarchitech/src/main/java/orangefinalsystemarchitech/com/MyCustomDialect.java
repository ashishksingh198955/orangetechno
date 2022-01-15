package orangefinalsystemarchitech.com;

import org.hibernate.dialect.MySQL55Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;
public class MyCustomDialect extends MySQL55Dialect {
    public MyCustomDialect() {
        super();
        this.registerFunction("group_concat", new SQLFunctionTemplate(StandardBasicTypes.STRING, "group_concat(?1)"));
    }
}
