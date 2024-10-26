package blockChainTransactionService.constants.ethereum;

public enum BlockParameter {
    LATEST("latest"),
    EARLIEST("earliest"),
    PENDING("pending");

    private final String value;

    BlockParameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}

