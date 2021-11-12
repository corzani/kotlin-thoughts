class QueryBuilder {
    private lateinit var table: String
    private val columns = mutableListOf<String>()
    fun build(): String {
        // TODO look for lateinit that is quite bad
        if (!::table.isInitialized) {
            throw IllegalStateException("Failed to build an sql select - target table is undefined")
        }
        return toString()
    }

    override fun toString(): String {
        val columnsToFetch =
            if (columns.isEmpty()) {
                "*"
            } else {
                columns.joinToString(", ")
            }
        return "select $columnsToFetch from $table"
    }

    fun from(table: String) {
        this.table = table
    }

    fun select(vararg columns: String) {
        if (columns.isEmpty()) {
            throw IllegalArgumentException("At least one column should be defined")
        }
        if (this.columns.isNotEmpty()) {
            throw IllegalStateException(
                "Detected an attempt to re-define columns to fetch. "
                        + "Current columns list: "
                        + "${this.columns}, new columns list: $columns"
            )
        }
        this.columns.addAll(columns)
    }
}

fun query(initializer: QueryBuilder.() -> Unit): QueryBuilder {
    return QueryBuilder().apply(initializer)
}
