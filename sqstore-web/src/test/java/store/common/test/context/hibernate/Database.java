package store.common.test.context.hibernate;

/**
 * @author Vlad Mihalcea
 */
public enum Database {
    HSQLDB(HSQLDBDataSourceProvider.class);

    private Class<? extends DataSourceProvider> dataSourceProviderClass;

    Database(Class<? extends DataSourceProvider> dataSourceProviderClass) {
        this.dataSourceProviderClass = dataSourceProviderClass;
    }

    public DataSourceProvider dataSourceProvider() {
        return ReflectionUtils.newInstance(dataSourceProviderClass.getName());
    }
}
