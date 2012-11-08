EJB Interceptors
======================

This project is a simple showcase of what can be achieved with EJB interceptors in scope of logging / auditing.

It should be executed on the JBoss AS 7.1 (uses default `DataSource` from JBoss). When deployed, 
you can test the following REST resources:

* `http://HOST:PORT/ejb-interceptors-spike/rest/add` - adds default single row data (`Customer`) to the database,,
* `http://HOST:PORT/ejb-interceptors-spike/rest/addWithoutTx` - tries to add data but is invoked without transaction demarcation,
* `http://HOST:PORT/ejb-interceptors-spike/rest/addWithMethodException` - tries to add data but simulates rollback on the business method side (data input by the interceptor should be rolledback as well),
* `http://HOST:PORT/ejb-interceptors-spike/rest/view` - views current state of the database (`Customer` and `HistoryLog` entries).


The `SpikeInterceptor` adds some arbitrary data to the database when any business method invocation occurs.
You can also find that this application is saving the `TransactionKey` of the interceptor and compares it with the `TransactionKey` on the business method side to confirm that it's spec-compliant, i.e. **transaction is shared between interceptors and EJBs**.

